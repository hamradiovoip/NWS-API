package dev.infomatik.wx.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.infomatik.wx.entity.Periods;
import dev.infomatik.wx.entity.Properties;
import dev.infomatik.wx.entity.alerts.Features;
import dev.infomatik.wx.entity.gridpoints.Geometry;
import dev.infomatik.wx.entity.gridpoints.Stations;
import dev.infomatik.wx.entity.points.LocationProperties;
import dev.infomatik.wx.entity.points.PointProperties;
import dev.infomatik.wx.entity.stations.Observation;
import dev.infomatik.wx.entity.stations.StationProperties;
import in.abilng.ndjson.NdJsonObjectMapper;

@Service
public class WeatherService {

	final String baseUrl = "https://api.weather.gov";	
	String alertUrl = "https://api.weather.gov/alerts/active?area=";

	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

	public double meterConvert = 3.28084;
	
	@Value("${stations}")
	private List<String> favoriteStations;

	@Value("${maxmap}")
	private int maxMarkersDisplayed;

	/** */
	//	@Autowired
	//	private WFOIdentifiers wfo;


	/** */

	public WeatherService() {

		//		wfo = new WFOIdentifiers();
		//		try {
		//			wfo.readAndSetData();
		//		} catch (FileNotFoundException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}


	/**
	 * @return the maxMarkersDisplayed
	 */
	public int getMaxMarkersDisplayed() {
		return maxMarkersDisplayed;
	}

	/**
	 * @param maxMarkersDisplayed the maxMarkersDisplayed to set
	 */
	public void setMaxMarkersDisplayed(int maxMarkersDisplayed) {
		this.maxMarkersDisplayed = maxMarkersDisplayed;
	}

	/**
	 * @return the favoriteStations
	 */
	public List<String> getFavoriteStations() {
		return favoriteStations;
	}

	/**
	 * @param favoriteStations the favoriteStations to set
	 */
	public void setFavoriteStations(List<String> favoriteStations) {
		this.favoriteStations = favoriteStations;
	}


	/**
	 * Get observation for closet station.
	 * 
	 * @param lat
	 * @param lon
	 * @param properties
	 * @param stations
	 * @return
	 * @throws IOException
	 */
	public Observation getClosestObs(double lat, double lon, PointProperties properties,
			List <Stations> stations) throws IOException {

		List<StationProperties> prop = new ArrayList<StationProperties>();	
		Observation currentObs = new Observation();		
		StationProperties sp;

		//		go through and find closest station 
		for (Stations element : stations) {		

			String station = element.getProperties().getStationIdentifier();

			//System.out.println("station= "+ station);

			// if there is a problem with one of the airports/stations skip it
			try {
				sp = getObsProperties(station);
			}
			catch (IOException e) {
				e.printStackTrace();
				continue;				
			}			

			// calc  distance and store separately
			Geometry g = element.getGeometry();
			List <String> s = g.getCoordinates();

			double lat1 = Double.parseDouble(s.get(1));
			double lon1 = Double.parseDouble(s.get(0));		
			//	System.out.println("lat1= "+lat1 + " lon1="+ lon1 + " lat=" + lat +" lon = "+ lon);

			double dist = distanceTo( lat1,  lon1, lat, lon);
			sp.setDistance(dist);

			sp.setName(element.getProperties().getName());

			//System.out.println("distance =" + dist);			

			prop.add(sp);
		}

		double minDist = 100000;
		StationProperties closestStation = null; 
		String stationID = "";

		// find min distance station 
		for (StationProperties element : prop) {

			double min1 = element.getDistance();
			if(min1 < minDist) {
				minDist = min1;
				closestStation = element;

			}
			String stationUrl = closestStation.getStation(); 			
			//	System.out.println("stationUrl = "+ stationUrl);

			//TODO better way?
			stationID = stationUrl.substring(stationUrl.length()-4);
		}

		//set values for Thymeleaf obs 
		currentObs.setTemperature(tempConvert(closestStation.getTemperature().getValue()));
		currentObs.setDewpoint(tempConvert(closestStation.getDewpoint().getValue()));
		currentObs.setBarometricPressure(closestStation.getBarometricPressure().getValue() * 0.000296133971008484); // 1 pascal [Pa] = 0.000296133971008484 in
		currentObs.setStation(stationID);
		currentObs.setTimestamp(closestStation.getTimestamp());
		currentObs.setName(closestStation.getName());
		currentObs.setHeatIndex(tempConvert(closestStation.getHeatIndex().getValue()));
		currentObs.setRelativeHumidity(closestStation.getRelativeHumidity().getValue());
		currentObs.setWindSpeed(kmToMiles(closestStation.getWindSpeed().getValue()));
		currentObs.setWindDirection(closestStation.getWindDirection().getValue());

		currentObs.setWindGust(closestStation.getWindGust().getValue());
		currentObs.setVisibility(closestStation.getVisibility().getValue() * meterConvert);
		currentObs.setTextDescription(closestStation.getTextDescription());		

		return currentObs;		

	}


	/**
	 * testing for reading in location data from json
	 */
	public void readTest() {

		NdJsonObjectMapper ndJsonObjectMapper = new NdJsonObjectMapper();
		//InputStream is = ....;
		//  Stream<Car> readValue = ndJsonObjectMapper.readValue(is, Places.class);

		//   InputStream in = new InputStream(new FileInputStream(file));
	}


	/**
	 * Get a list of station properties.
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 */

	public List <StationProperties> getStationProperties(double lat, double lon) throws IOException {

		List<StationProperties> prop = new ArrayList<StationProperties>();	
		List <Stations> stations = getStations(lat, lon);
		StationProperties sp;

		for (Stations element : stations) {		

			String station = element.getProperties().getStationIdentifier();

			// if there is a problem with one of the airports/stations skip it
			try {
				sp = getObsProperties(station);
			}
			catch (IOException e) {
				System.out.println("problem with finding station, skiping.");
				//e.printStackTrace();
				continue;				
			}			

			// calc  distance and store separately
			Geometry g = element.getGeometry();
			List <String> s = g.getCoordinates();

			double lat1 = Double.parseDouble(s.get(1));
			double lon1 = Double.parseDouble(s.get(0));		
			//	System.out.println("lat1= "+lat1 + " lon1="+ lon1 + " lat=" + lat +" lon = "+ lon);

			double dist = distanceTo( lat1,  lon1, lat, lon);
			sp.setDistance(dist);

			sp.setName(element.getProperties().getName());					

			prop.add(sp);

		}

		return prop;
	}
	

	/**
	 * Get LocationProperties from GPS coords.
	 * @return
	 */
	public LocationProperties getLocationData(double lat, double lon) {

		PointProperties pp = null;
		LocationProperties localProp = null;
		try {
			pp = getPointsData(lat,lon);
			localProp = pp.getRelativeLocation().getProperties();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return localProp;
	}


	/**
	 * Get office name based on NWS API office code.
	 * 
	 * @return Office name
	 * @throws IOException 
	 */
	public String getOfficeName(String officeCode) throws IOException {

		String sUrl = null;

		String code = officeCode.toUpperCase(); // UC
		// validate office TODO list?

		sUrl = baseUrl +"/offices/" + code; 

		URL url = new URL(sUrl);
		JsonNode jn = null;

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem getting office");
			e.printStackTrace();
			throw new IOException();
		}

		String officeName = jn.get("name").asText();

		return officeName;

	}
	

	/**
	 * Get NWS API weather data based on coords
	 * 
	 *   - Latitudes should be stored as numeric values with units of decimal degrees on the domain -90:90 with negative values
	 *   in the Southern hemisphere.
	 *   - Longitudes should be stored as numeric values with units of decimal degrees on the domain -180:180 with negative
	 *   values in the Western hemisphere.
	 *  	//https://api.weather.gov/points/38.8894,-77.0352
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException 
	 */
	public PointProperties getPointsData(double lat, double lon) throws IOException {


		String sUrl = null;
		if ( (lat <= 90 && lat > -90) && (lon > -180  && lon <= 180) )
		{
			sUrl = baseUrl +"/points/"+ lat + ","+ lon;
		}
		else {
			sUrl = baseUrl +"/points/"+ 38.89 + ","+ -77.04;
		}

		URL url = new URL(sUrl);
		JsonNode jn = null;

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem getting points data");
			throw new IOException();
		}

		JsonNode jn2 = jn.get("properties");

		ObjectMapper jsonObjectMapper = new ObjectMapper();

		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		PointProperties properties = jsonObjectMapper.treeToValue(jn2, PointProperties.class);

		return properties;

	}

	/**
	 * Get observation data based on NWS station name.
	 * @return
	 * @throws IOException 
	 */
	public StationProperties getObsProperties(String station) throws IOException {

		String sUrl = baseUrl +"/stations/" + station.toUpperCase() + "/observations/latest"; 
		JsonNode jn  = null;

		URL url = new URL(sUrl);		

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem getting data from station: " + station);
			throw new IOException(); //  probably not found in api
		}	

		JsonNode jn2 = jn.get("properties");

		ObjectMapper jsonObjectMapper = new ObjectMapper();

		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		StationProperties properties = jsonObjectMapper.treeToValue(jn2, StationProperties.class);

		return properties;
	}
	

	/**
	 * Get geo data based on NWS station name.
	 * 	 ie https://api.weather.gov/stations/KHHF
	 * @return
	 * @throws IOException 
	 */
	public Geometry getCoords(String station) throws IOException {

		String sUrl = baseUrl + "/stations/" + station.toUpperCase(); 
		JsonNode jn  = null;

		URL url = new URL(sUrl);		


		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Prblem with coords from station:"+ station);
			throw new IOException();
		}

		JsonNode jn2 = jn.get("geometry");

		ObjectMapper jsonObjectMapper = new ObjectMapper();

		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		Geometry geo = jsonObjectMapper.treeToValue(jn2, Geometry.class);

		return geo;
	}


	/**
	 * Get the 2 grid points based on std GPS coords. Each National Weather Service forecast office 
	 * issues numerical forecasts on a 2.5-kilometer grid across their entire forecast area. 
	 * Each gridpoint is one of these 2.5km squares.
	 * 
	 *   - Latitudes should be stored as numeric values with units of decimal degrees on the domain -90:90 with negative values
	 *   in the Southern hemisphere.
	 *   - Longitudes should be stored as numeric values with units of decimal degrees on the domain -180:180 with negative
	 *   values in the Western hemisphere.
	 * 	
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 *  
	 *  https://api.weather.gov/points/38.8894,-77.0352
	 */
	public String getGridPtsUrl(double lat, double lon) throws IOException {


		String sUrl = null;
		if ( (lat <= 90 && lat > -90) && (lon > -180  && lon <= 180) )
		{
			sUrl = baseUrl + "/points/"+ lat + ","+ lon;
		}
		else {
			sUrl =  baseUrl + "/points/"+ 38.89 + ","+ -77.04;
		}

		URL url = new URL(sUrl);
		JsonNode jn = null;

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem getting grid url");
			throw new IOException();
		}

		JsonNode jn2 = jn.get("properties");
		String gridPtUrl = jn2.get("forecast").asText();

		return gridPtUrl;
	}


	/**
	 * Get weather alert data, based on state.
	 * @param strUrl Full url to get to alert data using NWS API
	 * @return
	 * @throws IOException
	 */
	public List <Features>  getAlertData(String state) throws IOException{

		String strUrl = alertUrl + state.toUpperCase();		

		URL url = new URL(strUrl);

		JsonNode jn = null;

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem with getting alert data.");
			throw new IOException();
		}

		JsonNode jn2 = jn.get("features"); 

		// JsonNode jn3 = jn2.get("properties"); 

		ObjectMapper jsonObjectMapper = new ObjectMapper();

		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		List <Features> newJsonNode = jsonObjectMapper.convertValue(jn2, 
				new TypeReference<List<Features>>() {});

		// List <AlertProperties> p = newJsonNode.getProperties();

		return newJsonNode;
	}


	/**
	 * GetForecast several days of data. 
	 * @param strUrl
	 * @return List of periods that covers several days of forecast data.
	 * @throws IOException
	 */
	public List <Periods> getForecastData(String strUrl) throws IOException{

		URL url = new URL(strUrl);

		//String json = stream(url);
		//JSONObject jsonObj = new JSONObject(json);
		//Map map = jsonObj.toMap();

		JsonNode jn = null;

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem getting forecast data");
			throw new IOException();
		}

		JsonNode jn2 = jn.get("properties"); 

		ObjectMapper jsonObjectMapper = new ObjectMapper();

		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		Properties newJsonNode = jsonObjectMapper.treeToValue(jn2, Properties.class);

		List <Periods> p = newJsonNode.getPeriods();

		return p;
	}

	/**
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 */

	public Properties getGridProperties(double lat, double lon) throws IOException {

		String sUrl = null;
		if ( (lat <= 90 && lat > -90) && (lon > -180  && lon <= 180) )
		{
			sUrl = baseUrl + "/points/"+ lat + ","+ lon;
		}
		else {
			sUrl =  baseUrl + "/points/"+ 38.89 + ","+ -77.04;
		}

		URL url = new URL(sUrl);
		JsonNode jn = null;

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem getting grid properties");
			throw new IOException();
		}

		JsonNode jn2 = jn.get("properties");

		ObjectMapper jsonObjectMapper = new ObjectMapper();

		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		PointProperties pointProperties = jsonObjectMapper.treeToValue(jn2, PointProperties.class);

		//		get grid points and cws
		//String cws = pointProperties.getCwa();
		String gridX = pointProperties.getGridX();
		String gridY = pointProperties.getGridY();
		String gridId = pointProperties.getGridId();

		sUrl = baseUrl + "/gridpoints/"+ gridId +"/" + gridX + "," + gridY ;
		// 97 76  LWX
		url = new URL(sUrl);

		JsonNode content = null;


		try {
			content = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem with grid url, getting data" + sUrl);
			e.printStackTrace();
			return null;
		}

		JsonNode properties = content.get("properties");

		ObjectMapper jsonObjectMapper2 = new ObjectMapper();
		jsonObjectMapper2.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		Properties prop = jsonObjectMapper2.treeToValue(properties, Properties.class);

		// get gird coords

		// look up local stations, closest? Seems to be listed by distance

		return prop;
	}


	/**
	 * Get object station from station name.
	 * 
	 * @param station
	 * @return
	 * @throws JsonProcessingException
	 * @throws IllegalArgumentException
	 */
	public Stations getStation(String station) throws JsonProcessingException, IllegalArgumentException {

		String sUrl = baseUrl + "/stations/" + station;		

		URL url = null;
		JsonNode jn = null;

		try {
			url = new URL(sUrl);
		} catch (MalformedURLException e) {
			System.out.println("Problem getting station" + station);
			e.printStackTrace();
		}	

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem getting getJsonNode()");
			e.printStackTrace();
			return null;
		}
		ObjectMapper jsonObjectMapper = new ObjectMapper();

		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Stations stations = jsonObjectMapper.treeToValue(jn, Stations.class);

		return stations;		
	}



	/**
	 * Get a list of stations in the area of coords.
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 */

	public List <Stations> getStations(double lat, double lon) throws IOException {

		String sUrl = null;
		if ( (lat <= 90 && lat > -90) && (lon > -180  && lon <= 180) )
		{
			sUrl = baseUrl + "/points/"+ lat + ","+ lon;
		}
		else {
			sUrl = baseUrl + "/points/"+ 38.89 + ","+ -77.04;
		}

		URL url = new URL(sUrl);
		JsonNode jn = null;

		try {
			jn = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("Problem getting staion from coords");
			e.printStackTrace();
			return null;
		}

		JsonNode jn2 = jn.get("properties");

		ObjectMapper jsonObjectMapper = new ObjectMapper();

		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		PointProperties pointProperties = jsonObjectMapper.treeToValue(jn2, PointProperties.class);

		//		get grid points and cws
		//String cws = pointProperties.getCwa();
		String gridX = pointProperties.getGridX();
		String gridY = pointProperties.getGridY();
		String gridId = pointProperties.getGridId();

		sUrl = baseUrl + "/gridpoints/"+ gridId +"/" + gridX + "," + gridY  + "/stations";
		// 97 76  LWX
		url = new URL(sUrl);

		JsonNode content = null;


		try {
			content = getJsonNode(url);
		} catch (IOException e) {
			System.out.println("getJsonNode error");
			e.printStackTrace();
			return null;
		}

		JsonNode features = content.get("features");

		ObjectMapper jsonObjectMapper2 = new ObjectMapper();
		jsonObjectMapper2.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		List <Stations> stations= jsonObjectMapper2.convertValue(features, 
				new TypeReference<List<Stations>>() {});

		// get gird coords

		// look up local stations, closest?

		return stations;
	}

	/**
	 * Get json node
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static JsonNode getJsonNode(URL url) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode jn = null;

		int i = 0;
		do {
			i++;
			if(i >= 3)
				throw new IOException(); // tried 3 times and failed
			try {
				jn = mapper.readTree(url);
			} catch (IOException e){
				try {
					// to sleep 3 seconds
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// recommended because catching InterruptedException clears interrupt flag
					Thread.currentThread().interrupt();
					// you probably want to quit if the thread is interrupted

					return null;
				}
				System.out.println("WeatherService: with url '" + url +  "' read problem: " + i);	
				continue; // go again
			}
			break;
		} while (i <= 3);

		return jn;
	}
	

	/**
	 * Read URL (JSON data) and return as string.
	 * @param url
	 * @return
	 */
	public static String stream(URL url) {

		try (InputStream input = url.openStream()) {
			InputStreamReader isr = new InputStreamReader(input);
			BufferedReader reader = new BufferedReader(isr);
			StringBuilder json = new StringBuilder();
			int c;
			while ((c = reader.read()) != -1) {
				json.append((char) c);
			}
			return json.toString();
		} catch (IOException e) {
			System.out.println("WeatherService: stream/read problem");		
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * for testing
	 */
	public String test() throws IOException {
		return "test";
	}


	/**
	 * Calc distance between 2 pts.
	 * 
	 * @param userLat
	 * @param userLng
	 * @param venueLat
	 * @param venueLng
	 * @return
	 */	

	public int calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

		double latDistance = Math.toRadians(userLat - venueLat);
		double lngDistance = Math.toRadians(userLng - venueLng);

		double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
				(Math.cos(Math.toRadians(userLat))) *
				(Math.cos(Math.toRadians(venueLat))) *
				(Math.sin(lngDistance / 2)) *
				(Math.sin(lngDistance / 2));

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));

	}


	/**
	 * return distance between this location and that location
	 * measured in statute miles
	 * @param latitude
	 * @param longitude
	 * @param latitude2
	 * @param longitude2
	 * @return distance between this location and that location
	 */

	public double distanceTo(double latitude, double longitude, double latitude2, double longitude2) {
		double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
		double lat1 = Math.toRadians(latitude);
		double lon1 = Math.toRadians(longitude);
		double lat2 = Math.toRadians(latitude2);
		double lon2 = Math.toRadians(longitude2);

		// great circle distance in radians, using law of cosines formula
		double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		// each degree on a great circle of Earth is 60 nautical miles
		double nauticalMiles = 60 * Math.toDegrees(angle);
		double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
		return statuteMiles;
	}

	/**
	 * celsius to fahrenheit.
	 * @param celsius
	 * @return
	 */
	public double tempConvert(double celsius) {

		double fahrenheit = (celsius * 1.8) + 32;
		return fahrenheit; 
	}


	/**
	 * km to miles.
	 * @param km
	 * @return
	 */
	public double kmToMiles(double km) {
		double miles = km / 1.6;
		return miles;
	}


	/**
	 * For standard date timestamp string.
	 * @return
	 */
	public String getLocalDateTime() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLL dd, yyyy HH:mm");
		String text = date.format(formatter);		

		return text;
	}

	/**
	 * 1m ==  3.28084 ft
	 * @param meter
	 * @return
	 */
	public double meterToFeet(double meters) {
		double miles = meters *  3.28084;
		return miles;
	}

	/**
	 * 
	 * @param value
	 * @param places
	 * @return
	 */
	public double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	//time convert  2024-07-14T12:47:00+00:00
	public String localTime(String date) throws ParseException {
		SimpleDateFormat datetimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssX");
		Date formatDate = datetimeFormatter.parse(date);

		String local = formatDate.toLocaleString(); // TODO deprecated

		return local;
	}


	/**
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	public  TimeZone getTimeZoneFromPoints(double lat, double lon) {

		PointProperties properties;
		String timezone = "";
		int offset = 0;

		try {
			properties = getPointsData(lat, lon);
			timezone = properties.getTimeZone();		

		} catch (IOException e) {

			e.printStackTrace();
		}		

		TimeZone localTime = TimeZone.getTimeZone(timezone);

		return localTime;
	}


}
