package dev.infomatik.wx.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.constraints.Size;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import dev.infomatik.wx.entity.Elevation;
import dev.infomatik.wx.entity.LocationMenuProperties;
import dev.infomatik.wx.entity.MapPoints;
import dev.infomatik.wx.entity.Periods;
import dev.infomatik.wx.entity.Properties;
import dev.infomatik.wx.entity.alerts.AlertProperties;
import dev.infomatik.wx.entity.alerts.Features;
import dev.infomatik.wx.entity.gridpoints.Geometry;
import dev.infomatik.wx.entity.gridpoints.Stations;
import dev.infomatik.wx.entity.offices.WFOIdentifiers;
import dev.infomatik.wx.entity.points.LocationProperties;
import dev.infomatik.wx.entity.points.PointProperties;
import dev.infomatik.wx.entity.points.RelativeLocation;
import dev.infomatik.wx.entity.stations.Observation;
import dev.infomatik.wx.entity.stations.StationProperties;
import dev.infomatik.wx.openmeteo.OpenMeteoService;
import dev.infomatik.wx.openmeteo.entity.Current;
import dev.infomatik.wx.openmeteo.entity.Daily;
import dev.infomatik.wx.openmeteo.entity.OpenMeteoEntity;
import dev.infomatik.wx.service.TimezoneMapper;
import dev.infomatik.wx.service.WeatherService;
import dev.infomatik.wx.sun.LocationSunCalc;
import dev.infomatik.wx.sun.SolarEventCalculator;
import dev.infomatik.wx.sun.SunriseSunsetCalculator;
import dev.infomatik.wx.sun.SunriseSunsetCalculator;



/**
 * Controller for web based weather pages using NWS API and/or Meteo API.
 */


@Controller
@Validated // Required for method parameter validation
@JsonIgnoreProperties
@RequestMapping("/")
public class WeatherController {

	/** NWS base url TODO put in prop file */
	final String baseUrl = "https://api.weather.gov";

	private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

	/**
	 * 
	 */
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

	private static String latLonErrorStr = "Error: lat or long out of range";

	@Autowired
	private WeatherService service;

	/**
	 * Weather Field Offices
	 */
	private WFOIdentifiers wfo;

	@Autowired
	private ResourceLoader resourceLoader;

	// List of extension footprints used by scanners
	private static final List<String> BLACKLISTED_EXTENSIONS = List.of(
			".php", ".env", ".git", ".yaml", ".yml", ".bak", ".ini", ".conf", ".sql"
			);

	/**
	 * Check if lat and lon are valid and not large numbers, buffer overflow etc.
	 * Checking for potential buffer overflows when dealing with double values in Java involves ensuring that the data being handled does not exceed the allocated space.
	 *  Since a double occupies a fixed size of 64 bits (8 bytes), 
	 *  buffer overflows are typically not a concern in the same way as with character arrays or strings. 
	 * @return
	 */
	private double validate(double number) {
		DecimalFormat df = new DecimalFormat("#.#######");
		String formatted = df.format(number);
		double truncatedNumber = Double.parseDouble(formatted);		

		return truncatedNumber;

	}


	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}


	/**
	 * 
	 * @return
	 */
	@GetMapping("/robots.txt")
	@ResponseBody
	public String robots(HttpServletResponse response,HttpServletRequest request) {
		log.info("robots");

		System.out.println("robots remote ip address "+ request.getRemoteAddr());	
		log.info("robots remote ip address "+ request.getRemoteAddr());
		return "User-agent: *\n" +
		"Disallow: /";
	}

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(HttpServletRequest request, Model model, HttpServletResponse httpResponse) {


		String userAgent = request.getHeader("User-Agent");

		// 1. Drop requests missing a User-Agent entirely
		if (userAgent == null || userAgent.isBlank()) {


			log.warn ("Possible spammers: User-Agent empty");
			//httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			//return "";
		}    	    	

		log.info("Index remote ip address: " + request.getRemoteAddr() + " Header User-Agent = " + userAgent);

		String dateTime = service.getLocalDateTime();
		model.addAttribute("time", dateTime);	
		return "index";
	}

	
	/**
	 * Handle bad pages/requests
	 * @param request
	 * @return
	 */
	/*
	 * // @GetMapping("/error")
	 * 
	 * @RequestMapping(value = {"/error", "/**"}) public ResponseEntity<Map<String,
	 * Object>> handleError(HttpServletRequest request) { // Retrieve the path that
	 * the user actually attempted to visit String requestedUri = (String)
	 * request.getAttribute("jakarta.servlet.error.request_uri");
	 * 
	 * // Check for specific malicious probes (like scanning for configuration
	 * files) if (requestedUri != null && (requestedUri.contains(".env") ||
	 * requestedUri.contains(".git") || requestedUri.contains("test") ||
	 * requestedUri.contains(".wp") )) {
	 * 
	 * // Log the bad actor IP and the path they were probing
	 * System.err.println("ALERT: Security probe detected! IP: " +
	 * request.getRemoteAddr() + " targeted: " + requestedUri); String userAgent =
	 * request.getHeader("User-Agent");
	 * log.warn("ALERT: Security probe detected! IP: " + request.getRemoteAddr() +
	 * " targeted: " + requestedUri);
	 * 
	 * 
	 * // You can immediately drop them with a 400 Bad Request or 403 Forbidden to
	 * deter them return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); }
	 * 
	 * // Standard fallback structure for normal 404 errors (or 500 errors)
	 * Map<String, Object> errorBody = new HashMap<>(); errorBody.put("status",
	 * HttpStatus.NOT_FOUND.value()); errorBody.put("error", "Not Found");
	 * errorBody.put("message", "The requested page or endpoint does not exist.");
	 * 
	 * return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND); }
	 */

	/**
	 * Show all alerts from US state.
	 * @param model
	 * @param session
	 * @param state USA State to display alerts from.
	 * @return String for html template name ie wx.html
	 * 	// https://api.weather.gov/alerts/active?area=MD  
	 *	// https://api.weather.gov/alerts/active?area={state}
	 */
	@GetMapping("/alert")
	public String alert(Model model,// @Size(max = 2, message = "State must be equal to 2 characters")
			@RequestParam(value = "state", required = true) String state) {

		//validate state		
		if(state.length() > 2) {			
			model.addAttribute("error", "State must be equal to 2 characters");		

			return "err";
		}

		//String strUrl = "https://api.weather.gov/alerts/active?area="+ state;
		List<AlertProperties> alertProp = new ArrayList<AlertProperties>();
		List <Features> features = new ArrayList<Features>();

		try {

			features = service.getAlertData(state);

		} catch (IOException e) {

			e.printStackTrace();
			log.error("Problem with getting alerts", e.getMessage());

			model.addAttribute("error", "Problem with getting alerts, try reloading");		
			return "err";

		}

		for (Features element : features) {

			AlertProperties ap = element.getProperties();
			if(!ap.getEvent().equals("Test Message"))						
				alertProp.add(element.getProperties());
		}	

		String dateTime = service.getLocalDateTime();

		model.addAttribute("time", dateTime);	

		String title = "Weather Alerts for " + state;
		model.addAttribute("title", title);		
		model.addAttribute("alert", alertProp);
		model.addAttribute("weathermsg", "Weather Alerts for " + state);

		return "alert";
	}


	/**
	 * Fixed location one for Greg TODO remove
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/wx22")
	public String wx22(Model model) {
		String strUrl = "https://api.weather.gov/gridpoints/LWX/71,94/forecast";

		List<Periods> periods = new ArrayList<Periods>();

		try {
			periods = service.getForecastData(strUrl);
		} catch (IOException e) {

			e.printStackTrace();
			log.error("Problem with getting forecast", e.getMessage());
			model.addAttribute("error", "Problem with getting forecast, try reloading");		
			return "err";
		}

		String dateTime = service.getLocalDateTime(); 

		model.addAttribute("time", dateTime);	
		model.addAttribute("mapzoom", 6);
		model.addAttribute("periods", periods);

		model.addAttribute("weathermsg", "Weather Forecast Default Home");

		return "wx";
	}


	/**
	 * Main weather display for forecast based on GPS location
	 * 
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * // http://localhost:8080/wx2/?lat=39&lon=-77
	 */
	@GetMapping("/wx/")
	public String wx(HttpServletResponse response, Model model, 
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon,
			HttpServletRequest request)
					throws IOException { 

		response.addHeader("Referrer-Policy", "same-origin");			
		response.addHeader("User-Agent", "MySpringWeatherApp/1.0 (greg@infomatik.dev) MyAppUsingRestTemplate");	


		String clientIp = request.getHeader("X-Forwarded-For");
		if (clientIp == null || clientIp.isEmpty()) {
			clientIp = request.getRemoteAddr();
		} else {
			// X-Forwarded-For can be a comma-separated list: "client_ip, proxy1_ip"
			clientIp = clientIp.split(",")[0].trim();
		}

		System.out.println("remote ip address clientIp "+ clientIp);


		System.out.println("remote ip address "+ request.getRemoteAddr());	
		log.info("remote ip address "+ request.getRemoteAddr()  + "  " + clientIp);


		if(!validateCoords(lat,lon) ) {
			model.addAttribute("error", latLonErrorStr);		
			return "err";
		}

		String newUrl  = null;
		//	Observation currentObs = new Observation();

		try {
			newUrl = service.getGridPtsUrl(lat, lon); // https://api.weather.gov/points/"+ lat + ","+ lon;
		}
		catch (IOException e) {

			e.printStackTrace();
			log.error("Problem with getting forecast data", e.getMessage());
			model.addAttribute("error", "Problem with getting forecast data, try reloading");		
			return "err";
		}

		PointProperties properties = service.getPointsData(lat, lon);
		List<Periods> periods = new ArrayList<Periods>();
		//List<Stations> stations = service.getStations(lat, lon);

		Properties gridProp;
		String elevation = "unk";

		try {
			gridProp = service.getGridProperties(lat, lon);
			Elevation elevat = gridProp.getElevation();


			double elevationDouble = service.meterToFeet(elevat.getValue());					
			DecimalFormat df = new DecimalFormat("###,###,###");
			elevation = df.format(elevationDouble) + " ft";

		} catch (IOException e) {
			log.error("Problem with getting grid properties", e.getMessage());
			System.out.println("Problem getting grid properties, try reloading");			
		}		

		String elevationStr = "Area Ave. Elevation = "+ elevation; // grid elevation not exact gps

		try {
			periods = service.getForecastData(newUrl);
		} catch (IOException e) {

			e.printStackTrace();
			log.error("Problem with getting forecast data", e.getMessage());
			model.addAttribute("error", "Problem with getting forecast data, try reloading");		
			return "err";
		}

		//currentObs = getClosestObs(lat,  lon,  properties, stations);

		String radarStation = properties.getRadarStation();// <img src="https://radar.weather.gov/ridge/standard/KLWX_loop.gif">
		String radarImgUrl = "https://radar.weather.gov/ridge/standard/"+ radarStation + "_loop.gif";
		String radarLink = "https://radar.weather.gov/station/"+ radarStation +"/standard";

		//https://cdn.star.nesdis.noaa.gov/GOES19/ABI/SECTOR/eus/GEOCOLOR/2000x2000.jpg east coast
		String satLink = "https://cdn.star.nesdis.noaa.gov/GOES19/ABI/CONUS/GEOCOLOR/1250x750.jpg"; //conus

		String cwa =  properties.getCwa();		

		RelativeLocation rl = properties.getRelativeLocation();

		LocationProperties  lp = rl.getProperties();

		String city = lp.getCity();
		String state = lp.getState();
		String location = city + ", " + state;

		String officeName = "Closest Weather office: " + service.getOfficeName(cwa);
		String detailslink = "https://forecast.weather.gov/product.php?site=" + cwa + "&issuedby=" + cwa + "&product=AFD&format=CI&version=1&glossary=1";
		String officeLink = "https://www.weather.gov/"+ cwa;

		//https://www.star.nesdis.noaa.gov/GOES/sector.php?sat=G16&sector=ne
		String dateTime = service.getLocalDateTime(); 
		SunData sd = sunData(lat,lon);


		String sunrise = sd.getSunrise();
		String sunset = sd.getSunset();	
		String timeZone = sd.getTzId();
		String dayTime = sd.getTotalDayLight();


		model.addAttribute("pagetitle", "Weather Forecast for " + location);	
		model.addAttribute("time", dateTime);	

		//model.addAttribute("currentObs", currentObs);	
		model.addAttribute("sunrise", sunrise);
		model.addAttribute("sunset",  sunset);
		model.addAttribute("timezone","Timezone " + timeZone);
		model.addAttribute("dayTime",  dayTime);
		model.addAttribute("solarnoon", sd.getSolarnoon());
		model.addAttribute("astrorise", sd.getAstroSunrise());
		model.addAttribute("astroset", sd.getAstroSunset());

		model.addAttribute("civilrise", sd.getCivilSunrise());
		model.addAttribute("civilset", sd.getCivilSunset());		


		model.addAttribute("periods", periods);
		model.addAttribute("properties", properties);
		model.addAttribute("radarUrl", radarImgUrl);
		model.addAttribute("radarLink", radarLink);
		model.addAttribute("satLink", satLink);

		model.addAttribute("lat", lat);
		model.addAttribute("lon", lon);
		model.addAttribute("mapzoom", 9);

		model.addAttribute("name", officeName);

		model.addAttribute("elevation", elevationStr);
		model.addAttribute("location", location);
		model.addAttribute("weathermsg", "Weather Forecast for " + location);	
		model.addAttribute("url", newUrl);
		model.addAttribute("office", "Forecast Office Code: " + cwa);
		model.addAttribute("cwa", "Forecast Office: " + cwa);

		model.addAttribute("officeLink", officeLink);		

		model.addAttribute("link", detailslink);
		model.addAttribute("detailstext", "Area Forecast Discussion");	

		return "wx";
	}


	/**
	 * Display recent NWS observations for closest station to GPS points.
	 * 
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * // http://localhost:8080/wx2/?lat=39&lon=-77
	 */
	@GetMapping("/obs/")
	public String obs(Model model,  HttpServletResponse response,
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon)
					throws IOException { 


		if(!validateCoords(lat,lon) ) {
			model.addAttribute("error", latLonErrorStr);		
			log.error("Problem with lat long");
			return "err";
		}

		List<StationProperties> prop = new ArrayList<StationProperties>();	
		Observation currentObs = new Observation();

		PointProperties properties = service.getPointsData(lat, lon);		
		List <Stations> stations = service.getStations(lat, lon);

		// Find closest station with obs TODO: some return null so need error handling
		currentObs = service.getClosestObs(lat,  lon,  properties, stations);

		String radarStation = properties.getRadarStation(); // <img src="https://radar.weather.gov/ridge/standard/KLWX_loop.gif">
		String radarUrl = "https://radar.weather.gov/ridge/standard/"+ radarStation + "_loop.gif";
		String cwa =  properties.getCwa();
		String name = "Closest Weather office: " + service.getOfficeName(cwa);

		String dateTime = service.getLocalDateTime(); 

		model.addAttribute("time", dateTime);	
		model.addAttribute("currentObs", currentObs);	
		model.addAttribute("prop", prop);		
		model.addAttribute("stations", stations);
		model.addAttribute("properties", properties);
		model.addAttribute("radarUrl", radarUrl);
		model.addAttribute("name", name);	
		model.addAttribute("weathermsg", "Observations");

		return "obs";

	}

	/**
	 * Show all observation stations in cwa area. Ignore ones that have connection issues like 
	 * file not found.
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * // http://localhost:8080/wx2/?lat=39&lon=-77
	 */
	@GetMapping("/obsall/")
	public String obsall(Model model,
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon)
					throws IOException { 

		if(!validateCoords(lat,lon) ) {
			model.addAttribute("error", latLonErrorStr);		
			log.error("Problem with lat long");
			return "err";
		}

		int maxMarkersDisplayed = service.getMaxMarkersDisplayed();
		Observation currentObs = new Observation();
		List<StationProperties> prop = new ArrayList<StationProperties>();			
		List<Observation> obsList = new ArrayList<Observation>();	
		prop = service.getStationProperties(lat,lon);	
		PointProperties properties = service.getPointsData(lat, lon);	
		List <Stations> stations = service.getStations(lat, lon);	  
		List<String>  listStationStr = new ArrayList<String>(); // for maps
		String cwa =  properties.getCwa();	// for maps
		String officeName = "Weather office: " + service.getOfficeName(cwa);// for maps

		int maxSize = prop.size();

		if (maxSize > maxMarkersDisplayed) {
			maxSize = maxMarkersDisplayed; // get the closest X stations
		}

		for(int i = 0; i < maxSize; i++){

			Observation obs = new Observation();

			StationProperties stationProp = prop.get(i);			  
			Stations station = stations.get(i);

			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 
			obs.setName(p.getName()); // readable name
			obs.setTemperature(service.round( service.tempConvert(stationProp.getTemperature().getValue()), 1));
			obs.setTextDescription(stationProp.getTextDescription());
			//stationIdentifier
			obs.setStation(stationProp.getStation());
			obs.setStationIdentifier(p.getStationIdentifier());

			listStationStr.add(p.getStationIdentifier()); // for map

			currentObs.setWindSpeed(service.kmToMiles(stationProp.getWindSpeed().getValue()));
			obsList.add(obs);
		}

		//For map
		List <Stations> stationList = new ArrayList<Stations>();

		List <Geometry> geoList = new ArrayList<Geometry>();
		List <MapPoints> pointList = new ArrayList<MapPoints>();		

		Stations station1;
		int i=0;
		for (String element : listStationStr) {

			StationProperties  sp = null;
			try {
				sp = service.getObsProperties(element);
			}
			catch (IOException e) {
				e.printStackTrace(); // ignore and move on, bad data from API
				log.error("Bad data from API, ignoring", e.getMessage()); 
				continue;				
			}	

			Stations station = stations.get(i);
			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 
			String name = p.getName();

			station1 = service.getStation(element);
			geoList.add(station1.getGeometry());			
			stationList.add(station1);		

			List <String> coord = station1.getGeometry().getCoordinates();

			double lon1 = service.round(Double.parseDouble(coord.get(0)),4);
			double lat1 = service.round(Double.parseDouble(coord.get(1)),4);

			double tempCel = sp.getTemperature().getValue();
			double temp = service.round(service.tempConvert(tempCel), 1);			

			MapPoints point = new MapPoints();
			point.setLat(lat1);
			point.setLon(lon1);
			point.setStationName(element);
			point.setTemperature(temp);
			point.setTextDescription(sp.getTextDescription());

			point.setStationIdentifier(name); // "English" name

			pointList.add(point);

			i++;
		}

		model.addAttribute("pointList", pointList);
		model.addAttribute("officeName", officeName);
		model.addAttribute("lat", lat);
		model.addAttribute("lon", lon);

		// end for maps

		// get closest station with obs
		currentObs = service.getClosestObs(lat,  lon,  properties, stations);

		String radarStation = properties.getRadarStation();// <img src="https://radar.weather.gov/ridge/standard/KLWX_loop.gif">
		String radarUrl = "https://radar.weather.gov/ridge/standard/"+ radarStation + "_loop.gif";

		String name = "Closest Weather office: " + service.getOfficeName(cwa);

		String dateTime = service.getLocalDateTime();

		model.addAttribute("time", dateTime);	

		model.addAttribute("currentObs", currentObs);	
		model.addAttribute("obsList", obsList);	

		model.addAttribute("prop", prop);		
		model.addAttribute("stations", stations);
		model.addAttribute("properties", properties);
		model.addAttribute("radarUrl", radarUrl);
		model.addAttribute("name", name);	
		model.addAttribute("weathermsg", "Observations");

		//	System.out.println("return all obs, office name = "+ name +" grid id = " + properties.getGridId());
		return "obsall";
	}


	/**
	 * Display wx observations from a station based on station name ie KFDK.
	 * 
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * // http://localhost:8080/wx2/?lat=39&lon=-77
	 */
	@GetMapping("/sta/")
	public String sta(Model model,			
			@RequestParam(value = "station", required = true) String station, HttpServletRequest request)
					throws IOException { 


		if(station == null || station.length() > 9)
		{
			model.addAttribute("error", "Problem with station ID");		
			return "err";

		}

		log.info("remote ip address "+ request.getRemoteAddr());

		// TODO try catch
		StationProperties sp = service.getObsProperties(station);	

		Observation currentObs = new Observation();		

		//set values for Thymeleaf obs 
		currentObs.setTemperature(service.tempConvert(sp.getTemperature().getValue()));
		currentObs.setDewpoint(service.tempConvert(sp.getDewpoint().getValue()));
		currentObs.setBarometricPressure(sp.getBarometricPressure().getValue() * 0.000296133971008484); // 1 pascal [Pa] = 0.000296133971008484 in
		currentObs.setStation(station);
		currentObs.setTimestamp(sp.getTimestamp());
		currentObs.setName(sp.getName());
		currentObs.setHeatIndex(service.tempConvert(sp.getHeatIndex().getValue()));
		currentObs.setRelativeHumidity(sp.getRelativeHumidity().getValue());
		currentObs.setWindSpeed(service.kmToMiles(sp.getWindSpeed().getValue()));
		currentObs.setWindDirection(sp.getWindDirection().getValue());

		currentObs.setWindGust(sp.getWindGust().getValue());			

		Geometry g = service.getCoords(station);
		List<String> coord = g.getCoordinates();		

		double lon = Double.parseDouble(coord.get(0));
		double lat = Double.parseDouble(coord.get(1));

		///station  https://api.weather.gov/stations/KHHF
		//sp.getId();
		PointProperties properties = service.getPointsData(lat, lon);		

		String radarStation = properties.getRadarStation();// <img src="https://radar.weather.gov/ridge/standard/KLWX_loop.gif">
		String radarUrl = "https://radar.weather.gov/ridge/standard/"+ radarStation + "_loop.gif";

		model.addAttribute("radarUrl", radarUrl);
		model.addAttribute("currentObs", currentObs);			
		model.addAttribute("weathermsg", "Observations");

		return "station";
	}


	/**
	 * Display some observations, radar and map from a station 
	 * based on coords from Meteo and NWS.
	 * 
	 *  /osbmeteo/?lat=39&lon=-77
	 *  
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * 
	 */
	@GetMapping("/meteo/")
	public String obsMeteo(Model model, 
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon,
			HttpServletRequest request)
					throws IOException { 


		if(!validateCoords(lat,lon) ) {
			model.addAttribute("error", latLonErrorStr);		
			return "err";
		}

		log.info("meteo: Remote ip address "+ request.getRemoteAddr());

		Properties gridProp ;
		String elevation = "unk";
		boolean US_flag = true;

		try {
			gridProp = service.getGridProperties(lat, lon);

			if(gridProp == null) {

				model.addAttribute("error", "Problem with getting grid data, try reloading");		
				return "err";
			}
			Elevation elevat = gridProp.getElevation();
			double elevationDouble = service.meterToFeet(elevat.getValue());
			DecimalFormat df = new DecimalFormat("###,###,###");
			elevation = df.format(elevationDouble) + " ft";

		} catch (IOException e) {
			System.out.println("Problem getting grid properties, try reloading");
			log.error("Problem getting grid properties, try reloading", e.getMessage()); 
			US_flag = false;
		}



		//System.out.println("calling meteo for data");

		OpenMeteoService meteo = new OpenMeteoService();		
		String locationStr = "local";

		if(US_flag) {
			LocationProperties locationProp = service.getLocationData(lat, lon);

			if (locationProp !=null) {
				//double meters = locationProp.getDistance().getValue();
				//double distance = service.meterToFeet(meters);

				locationStr = "Closest town: "+ locationProp.getCity() + ", " +
						locationProp.getState();		

			}
		}
		else {
			locationStr = "outside US";
		}

		OpenMeteoEntity ome  = meteo.getData(lat,lon); 

		Current cur = ome.getCurrent();
		Daily daily = ome.getDaily();

		List <String> sunRise = daily.getSunrise();
		List <String> sunSet = daily.getSunset();
		String sr = sunRise.get(0);
		String ss = sunSet.get(0);

		List <Double> daylightList = daily.getDaylight_duration();
		List <Double> sunDurationList = daily.getSunshine_duration();
		Double daylightDouble = daylightList.get(0)/60/60;
		Double sunlightDouble = sunDurationList.get(0)/60/60;

		DecimalFormat df = new DecimalFormat("###,###,###.##");
		String daylight = df.format(daylightDouble) + " hours";
		String sunlight = df.format(sunlightDouble) + " hrs";


		/* ==========  Time   =============== */
		//String datetime = "2021-12-16T16:22:34";
		LocalDateTime srTime = LocalDateTime.parse(sr,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		LocalDateTime ssTime = LocalDateTime.parse(ss,DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		// adjust to local time for sunset etc
		String timezone = "";
		PointProperties properties = service.getPointsData(lat, lon);	
		if(US_flag) {

			timezone = properties.getTimeZone();
		}
		else {

			//todo
			timezone =  "unk";			
		}


		TimeZone mapTimezone = TimeZone.getTimeZone(timezone);	//TODO might not need		
		TimeZone local= TimeZone.getDefault();	//TODO	might not need

		TimeZone localTime = TimeZone.getTimeZone(timezone);

		String tz = localTime.getID();

		int i = localTime.getRawOffset();

		int utcOffset = 5; // TODO DST vs Std Time

		int offsetSr = ((i/1000/60/60)* -1) - utcOffset;
		LocalDateTime correctedSrTime  = srTime.minusHours(offsetSr);

		int offsetSs = ((i/1000/60/60)* -1) - utcOffset;
		LocalDateTime correctedSsTime  = ssTime.minusHours(offsetSs);

		// desired output format
		// String AUTH_DATE_PATTERN = "MM/dd/yyyy HH:mm:ss";
		String AUTH_DATE_PATTERN = " HH:mm:ss";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AUTH_DATE_PATTERN);
		String formatedSr = correctedSrTime.format(formatter); 					
		String formatedSs = correctedSsTime.format(formatter);

		//TimeZone tz = service.getTimeZoneFromPoints(lat,  lon);  //TODO	might not need

		ZoneId z = ZoneId.of(timezone) ;
		ZonedDateTime zdt = ZonedDateTime.now( z ) ;  // Capture the current moment as seen in the wall-clock time used by the people of a partic

		//System.out.println("zdt = "+ zdt);


		//	System.out.println("obs = "+ cur);

		// TODO try catch

		Observation currentObs = new Observation();		

		//  Td = T - ((100 - RH)/5.)  

		double tempF = cur.getTemperature_2m(); 

		double tempC = (tempF - 32) * (5/9);
		double dewPtC = tempC - (100 - cur.getRelative_humidity_2m())/5; // not accurate
		double dewPtF = (9/5) * (dewPtC + 32);

		//set values for Thymeleaf obs 
		currentObs.setTemperature(cur.getTemperature_2m());
		currentObs.setDewpoint(dewPtF); // cloud_cover
		currentObs.setCloudCover(cur.getCloud_cover());		
		currentObs.setBarometricPressure(cur.getPressure_msl() * 0.0295301); // 1 pascal [Pa] = 0.000296133971008484 in

		currentObs.setTimestamp(cur.getTime());
		//	currentObs.setName(locationStr);
		currentObs.setHeatIndex(cur.getApparent_temperature());
		currentObs.setRelativeHumidity(cur.getRelative_humidity_2m());
		currentObs.setWindSpeed(cur.getWind_speed_10m());
		currentObs.setWindDirection(cur.getWind_direction_10m());
		currentObs.setPrecipitation(cur.getPrecipitation());
		currentObs.setWindGust(cur.getWind_gusts_10m());

		String radarStation = properties.getRadarStation();// <img src="https://radar.weather.gov/ridge/standard/KLWX_loop.gif">
		String radarUrl = "https://radar.weather.gov/ridge/standard/"+ radarStation + "_loop.gif";

		model.addAttribute("lat", lat);
		model.addAttribute("lon", lon);
		model.addAttribute("mapzoom", 10);
		model.addAttribute("radarUrl", radarUrl);
		model.addAttribute("currentObs", currentObs);			
		model.addAttribute("weathermsg", "Meteo Observations");
		model.addAttribute("elevation", elevation);
		model.addAttribute("sunset",  formatedSs);
		model.addAttribute("sunrise",  formatedSr);
		model.addAttribute("daylight", daylight);
		model.addAttribute("sunlight", sunlight);
		model.addAttribute("timezone", tz);	

		return "obsMeteo";
	}


	/**
	 * Display a list of of favorite stations that are in the applicatons.prop file
	 *  
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * 
	 * @throws ParseException 
	 */
	@GetMapping("/stations/")
	public String stations(Model model, HttpServletRequest request) throws IOException, ParseException {
		//String ip = request.getHeader("Proxy-Client-IP");
		//System.out.println("ip = "+ ip);
		log.info("stations: remote ip address "+ request.getRemoteAddr());


		//	System.out.println("start stations");

		List<StationProperties> prop = new ArrayList<StationProperties>();			
		List<Observation> obsList = new ArrayList<Observation>();	
		List <Stations> stationList = new ArrayList<Stations>();

		List<String>  listStationStr = service.getFavoriteStations();
		Stations station1;

		for (String element : listStationStr) {
			StationProperties  sp = service.getObsProperties(element);
			station1 = service.getStation(element);
			stationList.add(station1);
			prop.add(sp);  
		}

		//PointProperties properties = service.getPointsData(lat, lon);	

		for(int i = 0; i < prop.size(); i++){

			Observation obs = new Observation();

			StationProperties stationProp = prop.get(i);			  
			Stations station = stationList.get(i);

			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 

			obs.setTemperature(service.round( service.tempConvert(stationProp.getTemperature().getValue()), 1));
			obs.setTextDescription(stationProp.getTextDescription());
			obs.setWindSpeed(service.round(service.kmToMiles(stationProp.getWindSpeed().getValue()),1));
			//barometricPressure
			obs.setDewpoint(service.round(service.tempConvert(stationProp.getDewpoint().getValue()),1));
			obs.setBarometricPressure(service.round(stationProp.getBarometricPressure().getValue() * 0.000296133971008484, 2)); // 1 pascal [Pa] = 0.000296133971008484 in
			obs.setHeatIndex(service.round(service.tempConvert(stationProp.getHeatIndex().getValue()), 1));
			//obs.setRelativeHumidity(stationProp.getRelativeHumidity().getValue());

			obs.setRelativeHumidity(service.round(stationProp.getRelativeHumidity().getValue(), 1));

			// String localtime = service.localTime(stationProp.getTimestamp()); 

			obs.setTimestamp(stationProp.getTimestamp());


			obs.setStation(stationProp.getStation());
			obs.setStationIdentifier(p.getStationIdentifier());

			obs.setName(p.getName());

			obs.setStationIdentifier(p.getStationIdentifier());		

			obsList.add(obs);

			//	System.out.println(" stationProp= " + stationProp);
		}


		//System.out.println(properties);	

		// get closest station with obs
		//	currentObs = service.getClosestObs(lat,  lon,  properties, stations);
		//String radarStation = properties.getRadarStation();// <img src="https://radar.weather.gov/ridge/standard/KLWX_loop.gif">
		//	String radarUrl = "https://radar.weather.gov/ridge/standard/"+ radarStation + "_loop.gif";
		//	String cwa = properties.getCwa();
		//	String name = "Closest Weather office: " + service.getOfficeName(cwa);

		String dateTime = service.getLocalDateTime();

		model.addAttribute("time", dateTime);		
		model.addAttribute("obsList", obsList);	

		//	model.addAttribute("prop", prop);		
		//model.addAttribute("stations", stations);

		//model.addAttribute("properties", properties);
		//model.addAttribute("radarUrl", radarUrl);
		//model.addAttribute("name", name);	
		model.addAttribute("weathermsg", "List of Favorite Stations");

		return "menu/stations";
	}


	/**
	 * Display a static page with links to hardwired forecasts.
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/forecast/")
	public String forecast(Model model, HttpServletRequest request) throws IOException { 

		log.info("forecast: Remote ip address: " + request.getRemoteAddr());
		List <LocationMenuProperties> prop = service.readLocationData();
		model.addAttribute("title", "Custom Forecast Locations");			
		model.addAttribute("prop", prop);		

		return "menu/forecasts";

	}

	/**
	 * Display a static alerts page.
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/alerts/")
	public String alerts(Model model, HttpServletRequest request) throws IOException { 

		log.info("alerts: Remote ip address: " + request.getRemoteAddr());
		List <LocationMenuProperties> prop = service.readLocationData();
		model.addAttribute("title", "Custom Forecast Location List");			
		model.addAttribute("states", service.alertList);	

		return "menu/alerts";

	}

	/**
	 * Display a page with a static list of stations.
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/liststations/")
	public String liststations(Model model, HttpServletRequest request) throws IOException { 

		log.info("liststations: Remote ip address: " + request.getRemoteAddr());
		List <LocationMenuProperties> prop = service.readLocationData();
		model.addAttribute("title", "Custom Forecast Location List");			
		model.addAttribute("prop", prop);	


		return "menu/liststations";

	}


	/**
	 * Display a about page
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/about/")
	public String about(Model model, HttpServletRequest request) throws IOException { 

		log.info("about: Remote ip address: " + request.getRemoteAddr());
		return "menu/about";

	}


	/**
	 * Display a page with links to external websites
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/links/")
	public String links(Model model, HttpServletRequest request) throws IOException { 

		log.info("links: Remote ip address: " + request.getRemoteAddr());
		return "menu/links";

	}

	/**
	 * Display a list of favorite cities to display maps  with drop pins with data
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/maplist/")
	public String maplist(Model model, HttpServletRequest request) throws IOException { 

		log.info("maplist: Remote ip address: " + request.getRemoteAddr());
		List <LocationMenuProperties> prop = service.readLocationData();
		model.addAttribute("title", "Custom Map List");			
		model.addAttribute("prop", prop);	

		return "menu/maplist";

	}

	/**
	 * Display a map with favorites markers OLDER ONE 
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/map2/")
	public String map2(Model model, 
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon) throws IOException { 

		if(!validateCoords(lat,lon) ) {
			model.addAttribute("error", latLonErrorStr);		
			return "err";
		}


		int maxMarkersDisplayed = service.getMaxMarkersDisplayed();
		List <Stations> stationList = new ArrayList<Stations>();
		List<String> listStationStr = new ArrayList<String>();

		List <Geometry> geoList = new ArrayList<Geometry>();
		List <MapPoints> pointList = new ArrayList<MapPoints>();	
		List <Stations> stations = service.getStations(lat, lon);	  
		List<StationProperties> prop = new ArrayList<StationProperties>();		
		prop = service.getStationProperties(lat,lon);	
		PointProperties properties = service.getPointsData(lat, lon);
		String cwa =  properties.getCwa();	
		String officeName = "Weather office: " + service.getOfficeName(cwa);

		int maxSize = prop.size();

		if (maxSize > maxMarkersDisplayed) {
			maxSize = maxMarkersDisplayed; // get the closest X stations
		}

		for(int i = 0; i < maxSize; i++){		
			//StationProperties stationProp = prop.get(i);			  
			Stations station = stations.get(i);
			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 			
			listStationStr.add(p.getStationIdentifier()); // for map			

		}

		Stations station1;
		int i = 0;

		for (String element : listStationStr) {
			Stations station = stations.get(i);
			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 
			String name = p.getName();
			StationProperties  sp;

			try {
				sp = service.getObsProperties(element);
			} catch (IOException e) {
				System.out.println("Problem getting data from station: " + station + "continuing...");
				log.error("Problem getting data from station:"  + station + "continuing...", e.getMessage()); 
				continue; //  probably not found in api
			}		


			station1 = service.getStation(element);
			geoList.add(station1.getGeometry());			
			stationList.add(station1);		

			List <String> coord = station1.getGeometry().getCoordinates();
			//			Stations station = stations.get(i);
			//			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 
			//			String name = p.getName();

			double lon1 = service.round(Double.parseDouble(coord.get(0)),4);
			double lat1 = service.round(Double.parseDouble(coord.get(1)),4);

			double tempCel = sp.getTemperature().getValue();
			double temp = service.round(service.tempConvert(tempCel), 1);			

			MapPoints point = new MapPoints();
			point.setLat(lat1);
			point.setLon(lon1);
			point.setStationName(element);
			point.setTemperature(temp);
			point.setTextDescription(sp.getTextDescription());

			point.setStationIdentifier(name); // "English" name

			pointList.add(point);

			i++;
		}

		model.addAttribute("pointList", pointList);
		model.addAttribute("lat", lat);
		model.addAttribute("lon", lon);
		model.addAttribute("officeName", officeName);
		return "map2";

	}


	/**
	 * Display a map with favorites markers & clickable links
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/map/")
	public String map(Model model, 
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon) throws IOException { 

		// Might not need userAgent
		String url = "https://openstreetmap.org";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("User-Agent", "MySpringWeatherApp/1.0 (greg@infomatik.dev) MyAppUsingRestTemplate");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		String customUserAgent = "\"MySpringWeatherApp/1.0 (greg@infomatik.dev) MyAppUsingRestTemplate\"";
		model.addAttribute("userAgentId", customUserAgent);		  		  
		model.addAttribute("User-Agent", response.getBody());

		if(!validateCoords(lat,lon) ) {
			model.addAttribute("error", latLonErrorStr);		
			return "err";
		}

		int maxMarkersDisplayed = service.getMaxMarkersDisplayed();
		List <Stations> stationList = new ArrayList<Stations>();
		List<String> listStationStr = new ArrayList<String>();

		List <Geometry> geoList = new ArrayList<Geometry>();
		List <MapPoints> pointList = new ArrayList<MapPoints>();	
		List <Stations> stations = service.getStations(lat, lon);	  
		List<StationProperties> prop = new ArrayList<StationProperties>();		
		prop = service.getStationProperties(lat,lon);	
		PointProperties properties = service.getPointsData(lat, lon);
		String cwa =  properties.getCwa();	
		String officeName = "Weather office: " + service.getOfficeName(cwa);


		int maxSize = prop.size();

		if (maxSize > maxMarkersDisplayed) {
			maxSize = maxMarkersDisplayed; // get the closest X stations
		}

		for(int i = 0; i < maxSize; i++){		

			//StationProperties stationProp = prop.get(i);			  
			Stations station = stations.get(i);
			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 			
			listStationStr.add(p.getStationIdentifier()); // for map	
		}

		Stations station1;
		int i = 0;

		for (String element : listStationStr) {

			Stations station = stations.get(i);
			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 
			String name = p.getName();

			StationProperties  sp;	 


			try {
				sp = service.getObsProperties(element);
			} catch (IOException e) {
				System.out.println("Problem getting data from station: " + station + "continuing...");
				log.error("Problem getting data from station:"  + station + "continuing...", e.getMessage()); 
				continue; //  probably not found in api
			}	

			station1 = service.getStation(element);
			geoList.add(station1.getGeometry());			
			stationList.add(station1);		

			List <String> coord = station1.getGeometry().getCoordinates();
			//			Stations station = stations.get(i);
			//			dev.infomatik.wx.entity.gridpoints.Properties p = station.getProperties(); 
			//			String name = p.getName();

			double lon1 = service.round(Double.parseDouble(coord.get(0)),4);
			double lat1 = service.round(Double.parseDouble(coord.get(1)),4);

			double tempCel = sp.getTemperature().getValue();
			double temp = service.round(service.tempConvert(tempCel), 1);			

			MapPoints point = new MapPoints();
			point.setLat(lat1);
			point.setLon(lon1);
			point.setStationName(element);
			point.setTemperature(temp);
			point.setTextDescription(sp.getTextDescription());

			point.setStationIdentifier(name); // "English" name
			pointList.add(point);
			i++;
		}

		model.addAttribute("pointList", pointList);
		model.addAttribute("lat", lat);
		model.addAttribute("lon", lon);
		model.addAttribute("officeName", officeName);
		model.addAttribute("size", "width: 1000px; height: 800px;");	
		model.addAttribute("zoom", 8);

		return "map";

	}


	/**
	 * Display a map with favorites markers [TODO]
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/cwamap/")
	public String cwamap(Model model) throws IOException { 

		List <Stations> stationList = new ArrayList<Stations>();
		List<String>  listStationStr = service.getFavoriteStations();
		List <Geometry> geoList = new ArrayList<Geometry>();
		List <MapPoints> pointList = new ArrayList<MapPoints>();		


		Stations station1;

		for (String station : listStationStr) {
			StationProperties  sp = service.getObsProperties(station);

			station1 = service.getStation(station);
			geoList.add(station1.getGeometry());			
			stationList.add(station1);		

			List <String> coord = station1.getGeometry().getCoordinates();

			double lon = service.round(Double.parseDouble(coord.get(0)),4);
			double lat = service.round(Double.parseDouble(coord.get(1)),4);

			double tempCel = sp.getTemperature().getValue();
			double temp = service.round(service.tempConvert(tempCel), 1);			

			MapPoints point = new MapPoints();
			point.setLat(lat);
			point.setLon(lon);
			point.setStationName(station);
			point.setTemperature(temp);
			point.setTextDescription(sp.getTextDescription());
			//	point.setStationIdentifier(name); // "English" name

			pointList.add(point);


		}

		model.addAttribute("lat", "40");
		model.addAttribute("lon", "-99");
		model.addAttribute("officeName", " ");
		model.addAttribute("pointList", pointList);
		model.addAttribute("size", "width: 1200px; height: 1000px;");
		model.addAttribute("zoom", 3);

		return "map";

	}

	/**
	 * Display a page with links
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/space/")
	public String space(Model model) throws IOException { 

		return "menu/space";

	}


	/**
	 * Display a page with links to favorite cities for Meteo data.
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/meteolist/")
	public String meteo(Model model) throws IOException { 

		List <LocationMenuProperties> prop = service.readLocationData();
		model.addAttribute("title", "Custom Meteo Locations");			
		model.addAttribute("prop", prop);	

		return "menu/meteolist";

	}


	/**
	 * For errors
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/error/")
	public String error(Model model) throws IOException { 

		model.addAttribute("title", "ERROR PAGE");
		String error = "ERROR!";
		String dateTime = service.getLocalDateTime(); 
		model.addAttribute("time", dateTime);	
		model.addAttribute("error", error);		
		return "err";

	}



	/**
	 * Fixed location one for testing
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/boot")
	public String boot(Model model) {

		//System.out.println("calling boot");
		String dateTime = service.getLocalDateTime(); 
		Resource fileResource = resourceLoader.getResource("classpath:data/us/place-city.ndjson");

		String s =fileResource.getFilename();
		//		System.out.println("filename=" + s);
		model.addAttribute("time", dateTime);	
		return "test";
	}

	/**
	 * Check if coords are within bounds.
	 * @param lat latitude, double
	 * @param lon longitude, double
	 * @return boolean, if within bounds
	 */
	private boolean validateCoords(double lat, double lon) {

		if((lat <= 90.0 && lat >= -90.0) && ( lon >= -180.0 && lon <= 180.0 )) {
			return true;
		}
		else
		{
			return false;
		}
	}

	private SunData sunData(double lat, double lon) {

		LocationSunCalc location = new LocationSunCalc(lat, lon);

		TimezoneMapper tzm = new TimezoneMapper();
		String tzId  = tzm.latLngToTimezoneString( lat, lon);


		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, tzId); // "America/New_York"	

		String officialSunrise = calculator.getOfficialSunriseForDate(Calendar.getInstance());
		String officialSunset = calculator.getOfficialSunsetForDate(Calendar.getInstance());

		String astroSunrise = calculator.getAstronomicalSunriseForDate(Calendar.getInstance());
		String astroSunset = calculator.getAstronomicalSunsetForDate(Calendar.getInstance());
		String civilSunrise = calculator.getCivilSunriseForDate(Calendar.getInstance());
		String civilSunset = calculator.getCivilSunsetForDate(Calendar.getInstance());


		BigDecimal officialSunsetInt = calculator.getOfficialSunsetForDateCal(Calendar.getInstance());
		//System.out.println("officialSunsetInt:" +officialSunsetInt);

		BigDecimal officialSunriseInt = calculator.getOfficialSunriseForDateCal(Calendar.getInstance());
		//System.out.println("officialSunriseInt:" +officialSunriseInt);

		BigDecimal bd = officialSunsetInt.subtract(officialSunriseInt); // - officialSunriseInt;

		double d =	bd.doubleValue();
		double d2 = d/2;


		BigDecimal bigDecimalValue = BigDecimal.valueOf(d2);


		BigDecimal solarnoon = officialSunriseInt.add(bigDecimalValue);


		SolarEventCalculator sc = new SolarEventCalculator();

		String totalTime = sc.getLocalTimeAsString(bd);
		String solarNoonStr = sc.getLocalTimeAsString(solarnoon);

		//Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());


		SunData  sd = new SunData();
		sd.setSunrise(officialSunrise);
		sd.setSunset(officialSunset);
		sd.setTzId(tzId);
		sd.setTotalDayLight(totalTime);
		sd.setSolarnoon(solarNoonStr);
		sd.setAstroSunrise(astroSunrise);
		sd.setAstroSunset(astroSunset);
		sd.setCivilSunrise(civilSunrise);
		sd.setCivilSunset(civilSunset);
		return sd;

	}
	/**
	 * 
	 */
	public	class SunData{
		String sunrise;
		String sunset;
		String totalDayLight;
		String tzId;
		String solarnoon;
		String astroSunrise;
		String astroSunset;
		String civilSunrise;
		String civilSunset;



		/**
		 * @return the civilSunrise
		 */
		public String getCivilSunrise() {
			return civilSunrise;
		}
		/**
		 * @param civilSunrise the civilSunrise to set
		 */
		public void setCivilSunrise(String civilSunrise) {
			this.civilSunrise = civilSunrise;
		}
		/**
		 * @return the civilSunset
		 */
		public String getCivilSunset() {
			return civilSunset;
		}
		/**
		 * @param civilSunset the civilSunset to set
		 */
		public void setCivilSunset(String civilSunset) {
			this.civilSunset = civilSunset;
		}
		/**
		 * @return the sunrise
		 */
		public String getSunrise() {
			return sunrise;
		}
		/**
		 * @param sunrise the sunrise to set
		 */
		public void setSunrise(String sunrise) {
			this.sunrise = sunrise;
		}
		/**
		 * @return the sunset
		 */
		public String getSunset() {
			return sunset;
		}
		/**
		 * @param sunset the sunset to set
		 */
		public void setSunset(String sunset) {
			this.sunset = sunset;
		}
		/**
		 * @return the totalDayLight
		 */
		public String getTotalDayLight() {
			return totalDayLight;
		}
		/**
		 * @param totalDayLight the totalDayLight to set
		 */
		public void setTotalDayLight(String totalDayLight) {
			this.totalDayLight = totalDayLight;
		}
		/**
		 * @return the tzId
		 */
		public String getTzId() {
			return tzId;
		}
		/**
		 * @param tzId the tzId to set
		 */
		public void setTzId(String tzId) {
			this.tzId = tzId;
		}
		/**
		 * @return the solarnoon
		 */
		public String getSolarnoon() {
			return solarnoon;
		}
		/**
		 * @param solarnoon the solarnoon to set
		 */
		public void setSolarnoon(String solarnoon) {
			this.solarnoon = solarnoon;
		}
		/**
		 * @return the astroSunrise
		 */
		public String getAstroSunrise() {
			return astroSunrise;
		}
		/**
		 * @param astroSunrise the astroSunrise to set
		 */
		public void setAstroSunrise(String astroSunrise) {
			this.astroSunrise = astroSunrise;
		}
		/**
		 * @return the astroSunset
		 */
		public String getAstroSunset() {
			return astroSunset;
		}
		/**
		 * @param astroSunset the astroSunset to set
		 */
		public void setAstroSunset(String astroSunset) {
			this.astroSunset = astroSunset;
		}

	}

}// end class
