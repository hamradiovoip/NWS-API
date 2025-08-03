package dev.infomatik.wx.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import dev.infomatik.wx.service.WeatherService;



/**
 * Controller for web based weather pages using NWS API and/or Meteo API.
 */


@Controller
@JsonIgnoreProperties
@RequestMapping("/")
public class WeatherController {

	/** NWS base url TODO put in prop file */
	final String baseUrl = "https://api.weather.gov";

	/**
	 * 
	 */
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

	@Autowired
	private WeatherService service;

	/**
	 * Weather Field Offices
	 */
	private WFOIdentifiers wfo;

	@Autowired
	private ResourceLoader resourceLoader;



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
	@RequestMapping("/robots.txt")
	@ResponseBody
	public String robots() {
		return "User-agent: *\n" +
				"Disallow: /";
	}


	/**
	 * Show all alerts from US state.
	 * @param model
	 * @param session
	 * @param state State to display alerts from.
	 * @return String for html template name ie wx.html
	 * 	// https://api.weather.gov/alerts/active?area=MD  
	 *	// https://api.weather.gov/alerts/active?area={state}
	 */
	@GetMapping("/alert")
	public String alert(Model model,
			@RequestParam(value = "state", required = false) String state) {


		//		WFOIdentifiers wfo = new WFOIdentifiers();
		//		try {
		//			wfo.readAndSetData();
		//		} catch (FileNotFoundException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}


		//TODO validate state

		//String strUrl = "https://api.weather.gov/alerts/active?area="+ state;
		List<AlertProperties> alertProp = new ArrayList<AlertProperties>();
		List <Features> features = new ArrayList<Features>();

		try {

			features = service.getAlertData(state);

		} catch (IOException e) {

			e.printStackTrace();
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
	@GetMapping("/wx")
	public String wx(Model model) {
		String strUrl = "https://api.weather.gov/gridpoints/LWX/71,94/forecast";

		List<Periods> periods = new ArrayList<Periods>();

		try {
			periods = service.getForecastData(strUrl);
		} catch (IOException e) {

			e.printStackTrace();
			model.addAttribute("error", "Problem with getting forecast, try reloading");		
			return "err";
		}

		String dateTime = service.getLocalDateTime(); 

		model.addAttribute("time", dateTime);	
		model.addAttribute("mapzoom", "6");
		model.addAttribute("periods", periods);

		model.addAttribute("weathermsg", "Weather Forecast Default Home");

		return "wx";
	}


	/**
	 * Main weather display for forecast based on GPS coords
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
	public String wx2(Model model, 
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon)
					throws IOException { 

		String newUrl  = null;
		//	Observation currentObs = new Observation();

		try {
			newUrl = service.getGridPtsUrl(lat, lon); // https://api.weather.gov/points/"+ lat + ","+ lon;
		}
		catch (IOException e) {

			e.printStackTrace();
			model.addAttribute("error", "Problem with getting forecast data, try reloading");		
			return "err";
		}

		PointProperties properties = service.getPointsData(lat, lon);
		List<Periods> periods = new ArrayList<Periods>();
		//List<Stations> stations = service.getStations(lat, lon);

		Properties gridProp ;
		String elevation = "unk";

		try {
			gridProp = service.getGridProperties(lat, lon);
			Elevation elevat = gridProp.getElevation();


			double elevationDouble = service.meterToFeet(elevat.getValue());					
			DecimalFormat df = new DecimalFormat("###,###,###");
			elevation = df.format(elevationDouble) + " ft";

		} catch (IOException e) {
			System.out.println("Problem getting grid properties, try reloading");			
		}		

		String elevationStr = "Area Ave. Elevation = "+ elevation; // grid elevation not exact gps

		try {
			periods = service.getForecastData(newUrl);
		} catch (IOException e) {

			e.printStackTrace();
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

		String dateTime = service.getLocalDateTime(); 

		model.addAttribute("time", dateTime);	

		//model.addAttribute("currentObs", currentObs);	
		model.addAttribute("periods", periods);
		model.addAttribute("properties", properties);
		model.addAttribute("radarUrl", radarImgUrl);
		model.addAttribute("radarLink", radarLink);
		model.addAttribute("satLink", satLink);

		model.addAttribute("lat", lat);
		model.addAttribute("lon", lon);
		model.addAttribute("mapzoom", "9");

		model.addAttribute("name", officeName);

		model.addAttribute("elevation", elevationStr);
		model.addAttribute("location", location);
		model.addAttribute("weathermsg", "Weather Forecast for " + location);	
		model.addAttribute("url", newUrl);
		model.addAttribute("cwa", "Forecast Office: " + cwa);
		model.addAttribute("link", detailslink);
		model.addAttribute("detailstext", "Area Forecast Discussion");	

		return "wx";
	}


	/**
	 * Display NWS observation for closest station to GPS points.
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
	public String obs(Model model, 
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon)
					throws IOException { 

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
	 * Show all observation stations in cwa area. Ignore ones that have connection issue like 
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
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon)
					throws IOException { 

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
			@RequestParam(value = "station", required = false) String station)
					throws IOException { 

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
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon)
					throws IOException { 

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
		model.addAttribute("mapzoom", "10");
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
	public String stations(Model model)throws IOException, ParseException {
		//String ip = request.getHeader("Proxy-Client-IP");
		//System.out.println("ip = "+ ip);

		//session.getServletContext().getAuthentication().getDetails()).getRemoteAddress();

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
	public String forecast(Model model) throws IOException { 

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
	public String alerts(Model model) throws IOException { 

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
	public String liststations(Model model) throws IOException { 

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
	public String about(Model model) throws IOException { 
		return "menu/about";

	}


	/**
	 * Display a page with links
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/links/")
	public String links(Model model) throws IOException { 
		return "menu/links";

	}

	@GetMapping("/maplist/")
	public String maplist(Model model) throws IOException { 


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
				System.out.println("Problem getting data from station: " + station + "contining...");
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
				System.out.println("Problem getting data from station: " + station + "contining...");
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
		model.addAttribute("zoom", "8");

		return "map";

	}


	/**
	 * Display a map with favorites markers
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
		model.addAttribute("zoom", "3");

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
	 * Display a page with links
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
	 * 
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
		System.out.println("calling boot");
		String dateTime = service.getLocalDateTime(); 
		Resource fileResource = resourceLoader.getResource("classpath:data/us/place-city.ndjson");
		String s =fileResource.getFilename();
		//		System.out.println("filename=" + s);
		model.addAttribute("time", dateTime);	
		return "test";
	}

}
