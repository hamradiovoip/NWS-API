package com.kd3su.wx.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kd3su.wx.entity.Elevation;
import com.kd3su.wx.entity.Periods;
import com.kd3su.wx.entity.Properties;
import com.kd3su.wx.entity.MapPoints;
import com.kd3su.wx.entity.alerts.AlertProperties;
import com.kd3su.wx.entity.alerts.Features;
import com.kd3su.wx.entity.gridpoints.Geometry;
import com.kd3su.wx.entity.gridpoints.Stations;
import com.kd3su.wx.entity.points.LocationProperties;
import com.kd3su.wx.entity.points.PointProperties;
import com.kd3su.wx.entity.points.RelativeLocation;
import com.kd3su.wx.entity.stations.Observation;
import com.kd3su.wx.entity.stations.StationProperties;
import com.kd3su.wx.service.WeatherService;
import jakarta.servlet.http.HttpSession;


/**
 * Controller for web based weather pages.
 */



@Controller
@JsonIgnoreProperties
@RequestMapping("/")
public class WeatherController {
	
	/** NWS base url TODO in prop file */
	final String baseUrl = "https://api.weather.gov";
	
	/**
	 * 
	 */
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

	@Autowired
	private WeatherService service;

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
	public String alert(Model model, HttpSession session,
			@RequestParam(value = "state", required = false) String state) {

		//TODO validate state

		String strUrl = "https://api.weather.gov/alerts/active?area="+ state;

		List<AlertProperties> alertProp = new ArrayList<AlertProperties>();

		List <Features> features = new ArrayList<Features>();

		try {
			features = service.getAlertData(strUrl);
		} catch (IOException e) {

			e.printStackTrace();
			return "error";			
		}

		for (Features element : features) {

			AlertProperties ap = element.getProperties();
			if(!ap.getEvent().equals("Test Message"))						
				alertProp.add(element.getProperties());
		}	

		//alertProp.forEach(System.out::println);

		String dateTime = service.getLocalDateTime();

		model.addAttribute("time", dateTime);	

		String title = "Weather Alerts for " + state;
		model.addAttribute("title", title);		
		model.addAttribute("alert", alertProp);
		model.addAttribute("weathermsg", "Weather Alerts for " + state);

		System.out.println("return alert");
		return "alert";
	}


	/**
	 * Fixed location one for Greg
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/wx")
	public String wx(Model model, HttpSession session) {
		String strUrl = "https://api.weather.gov/gridpoints/LWX/71,94/forecast";

		List<Periods> periods = new ArrayList<Periods>();

		try {
			periods = service.getForecastData(strUrl);
		} catch (IOException e) {

			e.printStackTrace();
			return "error";
		}


		String dateTime = service.getLocalDateTime(); 


		model.addAttribute("time", dateTime);	

		model.addAttribute("periods", periods);

		model.addAttribute("weathermsg", "Weather Forecast Default Home");

		System.out.println("return wx");
		return "wx";
	}


	/**
	 * Fixed location one for testing
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/boot")
	public String boot(Model model, HttpSession session) {
		System.out.println("calling boot");
		String dateTime = service.getLocalDateTime(); 

		model.addAttribute("time", dateTime);	

		System.out.println("return boot");
		return "boot";
	}


	/**
	 * Main weather display
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * // http://localhost:8080/wx2/?lat=39&lon=-77
	 */
	@GetMapping("/wx/")
	public String wx2(Model model, HttpSession session,
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon)
					throws IOException { 


		//	Observation currentObs = new Observation();

		String newUrl = service.getGridPtsUrl(lat, lon); // https://api.weather.gov/points/"+ lat + ","+ lon;
		PointProperties properties = service.getPointsData(lat, lon);
		//List <Stations> stations = getStations(lat, lon);


		List<Periods> periods = new ArrayList<Periods>();
		//List<Stations> stations = service.getStations(lat, lon);

		Properties gridProp = service.getGridProperties(lat, lon);
		// TODO greidpro can be null
		Elevation elevation = gridProp.getElevation();
		double ele = service.meterToFeet(elevation.getValue());
		double eleRd = service.round(ele, 0);
		String elevationStr = "Elevation = "+ eleRd + " ft";

		try {
			periods = service.getForecastData(newUrl);
		} catch (IOException e) {

			e.printStackTrace();
			return "error";
		}

		//currentObs = getClosestObs(lat,  lon,  properties, stations);

		String radarStation = properties.getRadarStation();// <img src="https://radar.weather.gov/ridge/standard/KLWX_loop.gif">
		String radarUrl = "https://radar.weather.gov/ridge/standard/"+ radarStation + "_loop.gif";
		String cwa =  properties.getCwa();		

		RelativeLocation rl = properties.getRelativeLocation();

		LocationProperties  lp = rl.getProperties();

		String city = lp.getCity();
		String state = lp.getState();
		String location = city + ", " + state;


		String officeName = "Closest Weather office: " + service.getOfficeName(cwa);

		String dateTime = service.getLocalDateTime(); 

		model.addAttribute("time", dateTime);	

		//model.addAttribute("currentObs", currentObs);	
		model.addAttribute("periods", periods);
		model.addAttribute("properties", properties);
		model.addAttribute("radarUrl", radarUrl);
		model.addAttribute("name", officeName);

		model.addAttribute("elevation", elevationStr);
		model.addAttribute("location", location);
		model.addAttribute("weathermsg", "Weather Forecast for " + location);		

		model.addAttribute("url", newUrl);

		System.out.println("return wx");
		return "wx";

	}



	/**
	 * Display observation for closest station to GPS points.
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * // http://localhost:8080/wx2/?lat=39&lon=-77
	 */
	@GetMapping("/obs/")
	public String obs(Model model, HttpSession session,
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

		System.out.println("return obs");
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
	public String obsall(Model model, HttpSession session,
			@RequestParam(value = "lat", required = false) double lat,
			@RequestParam(value = "lon", required = false) double lon)
					throws IOException { 

		Observation currentObs = new Observation();
		List<StationProperties> prop = new ArrayList<StationProperties>();			
		List<Observation> obsList = new ArrayList<Observation>();	
		prop = service.getStationProperties(lat,lon);	
		PointProperties properties = service.getPointsData(lat, lon);	
		List <Stations> stations = service.getStations(lat, lon);	  
		List<String>  listStationStr = new ArrayList<String>(); // for maps

		int maxSize = prop.size();
		
		if (maxSize > 15) {
			maxSize = 15; // get the closest 15 stations
		}
		
		for(int i = 0; i < maxSize; i++){

			Observation obs = new Observation();

			StationProperties stationProp = prop.get(i);			  
			Stations station = stations.get(i);

			com.kd3su.wx.entity.gridpoints.Properties p = station.getProperties(); 
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
		// get above List<String>  listStationStr = service.getFavoriteStations();
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
				e.printStackTrace();
				continue;				
			}	
			
			Stations station = stations.get(i);
			com.kd3su.wx.entity.gridpoints.Properties p = station.getProperties(); 
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
			
			point.setStationIdentifier(name);
			
			
			pointList.add(point);
			
			i++;
		}
		
		model.addAttribute("pointList", pointList);
		
		model.addAttribute("lat", lat);
		model.addAttribute("lon", lon);
		
		System.out.println("lat = " + lat + " " + "lon= " + lon);
		
		
		
// end for maps

		//System.out.println(properties);	
		//System.out.println(stations);



		// get closest station with obs
		currentObs = service.getClosestObs(lat,  lon,  properties, stations);


		String radarStation = properties.getRadarStation();// <img src="https://radar.weather.gov/ridge/standard/KLWX_loop.gif">
		String radarUrl = "https://radar.weather.gov/ridge/standard/"+ radarStation + "_loop.gif";
		String cwa = properties.getCwa();
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
	 * Display some observations from a station base on station name ie KFDK.
	 * @param model
	 * @param session
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * // http://localhost:8080/wx2/?lat=39&lon=-77
	 */
	@GetMapping("/sta/")
	public String sta(Model model, HttpSession session,			
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

		//String s = coord.get(0) ;


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

		System.out.println("return obs");
		return "station";

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
	public String statations(Model model, HttpSession session)throws IOException, ParseException { 

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

			com.kd3su.wx.entity.gridpoints.Properties p = station.getProperties(); 

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

			//System.out.println(" stationProp= " + stationProp);
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
		//System.out.println("return stations" + obsList);

		return "menu/stations";

	}


	/**
	 * Display a static page with a links to forecasts.
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/forecast/")
	public String forecast(Model model, HttpSession session) throws IOException { 


		System.out.println("return forecast");
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
	public String alerts(Model model, HttpSession session) throws IOException { 

		System.out.println("return alerts");
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
	public String liststations(Model model, HttpSession session) throws IOException { 


		System.out.println("return station list");
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
	public String about(Model model, HttpSession session) throws IOException { 

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
	public String links(Model model, HttpSession session) throws IOException { 

		return "menu/links";

	}
	
	/**
	 * Display a map with favorites markers
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/map/")
	public String map(Model model, HttpSession session) throws IOException { 
		
		List <Stations> stationList = new ArrayList<Stations>();
		List<String>  listStationStr = service.getFavoriteStations();
		List <Geometry> geoList = new ArrayList<Geometry>();
		List <MapPoints> pointList = new ArrayList<MapPoints>();	
		
		
		Stations station1;
		  int i=0;

		for (String element : listStationStr) {
			StationProperties  sp = service.getObsProperties(element);
			station1 = service.getStation(element);
			geoList.add(station1.getGeometry());			
			stationList.add(station1);		
			
			List <String> coord = station1.getGeometry().getCoordinates();
//			Stations station = stations.get(i);
//			com.kd3su.wx.entity.gridpoints.Properties p = station.getProperties(); 
//			String name = p.getName();
						
			double lon = service.round(Double.parseDouble(coord.get(0)),4);
			double lat = service.round(Double.parseDouble(coord.get(1)),4);
			
			double tempCel = sp.getTemperature().getValue();
			double temp = service.round(service.tempConvert(tempCel), 1);			

			MapPoints point = new MapPoints();
			point.setLat(lat);
			point.setLon(lon);
			point.setStationName(element);
			point.setTemperature(temp);
			point.setTextDescription(sp.getTextDescription());
			
			pointList.add(point);
			

		}
		
		model.addAttribute("pointList", pointList);
		
		System.out.println("return map");
		
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
	public String cwamap(Model model, HttpSession session) throws IOException { 
		
		List <Stations> stationList = new ArrayList<Stations>();
		List<String>  listStationStr = service.getFavoriteStations();
		List <Geometry> geoList = new ArrayList<Geometry>();
		List <MapPoints> pointList = new ArrayList<MapPoints>();		
		
		Stations station1;

		for (String element : listStationStr) {
			StationProperties  sp = service.getObsProperties(element);
			station1 = service.getStation(element);
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
			point.setStationName(element);
			point.setTemperature(temp);
			point.setTextDescription(sp.getTextDescription());
			
			pointList.add(point);
			

		}
		
		model.addAttribute("pointList", pointList);
		
		System.out.println("return map");
		
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
	public String space(Model model, HttpSession session) throws IOException { 

		return "menu/space";

	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */

	@GetMapping("/test/")
	public String test(Model model, HttpSession session) throws IOException { 
		
		
		List<String>  listStationStr = service.getFavoriteStations();
		model.addAttribute("listStationStr", listStationStr);	
		
		 model.addAttribute("unit", "TEST");

		return "test";

	}
	

}
