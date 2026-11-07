package dev.infomatik.wx.openmeteo;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.infomatik.wx.openmeteo.entity.Current;
import dev.infomatik.wx.openmeteo.entity.Daily;
import dev.infomatik.wx.openmeteo.entity.OpenMeteoEntity;

/*
 * For calling Meteo API and getting and extracting json data.
 */
public class OpenMeteoService {
	
	//https://api.open-meteo.com/v1/forecast?latitude=39.482&longitude=-77.633&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=16
	
	String wxurl = "https://api.open-meteo.com/v1/forecast?latitude=39.482&longitude=-77.633&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=16";


	/**
	 * 
	 * @param strUrl
	 * @return
	 * @throws IOException
	 */
	public OpenMeteoEntity getData(double lat, double lon) throws IOException{
		
		OpenMeteoEntity ome = new OpenMeteoEntity();
		
		String urlMeteo = "https://api.open-meteo.com/v1/forecast?latitude=" +
		lat+"&longitude="+ lon +
		"&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,daylight_duration,sunshine_duration,precipitation_sum,rain_sum&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=16";

		URL url = new URL(urlMeteo);

		JsonNode jn = get(url); // TODO catch here?

		JsonNode jn2 = jn.get("current");
		
		JsonNode dailyJN = jn.get("daily");		

		ObjectMapper jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Current current = jsonObjectMapper.treeToValue(jn2, Current.class);				
		Daily daily =  jsonObjectMapper.treeToValue(dailyJN, Daily.class);				
		ome.setCurrent(current);
		ome.setDaily(daily);	
		
		return ome;
	}

	
	/**
	 * Make call to url and return JsonNode. 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static JsonNode get(URL url) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode jn = null;

		try {
			jn = mapper.readTree(url);
		} catch (IOException e) {

			e.printStackTrace();
		
			return null;
		}		
		
		return jn;
	}
	
	
}
