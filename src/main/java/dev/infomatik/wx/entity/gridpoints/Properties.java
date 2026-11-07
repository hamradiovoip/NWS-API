package dev.infomatik.wx.entity.gridpoints;

/* For grid points properties */

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.infomatik.wx.entity.Elevation;


/**
 * For station properties
 */
public class Properties {
	
	
//	"properties": {
//        "@id": "https://api.weather.gov/stations/KDCA",
//        "@type": "wx:ObservationStation",
//        "elevation": {
//            "unitCode": "wmoUnit:m",
//            "value": 3.9624000000000001
//        },
//        "stationIdentifier": "KDCA",
//        "name": "Washington/Reagan National Airport, DC",
//        "timeZone": "America/New_York",
//        "forecast": "https://api.weather.gov/zones/forecast/VAZ054",
//        "county": "https://api.weather.gov/zones/county/VAC013",
//        "fireWeatherZone": "https://api.weather.gov/zones/fire/VAZ054"
//    }
	
	
	@JsonProperty("@id")
	private String id;
	@JsonProperty("@type")
	private String type;
	private Elevation elevation;
	private String stationIdentifier;	
	private String name;	
	private String timeZone;	
	private String forecast;	
	private String county;
	private String fireWeatherZone;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the elevation
	 */
	public Elevation getElevation() {
		return elevation;
	}
	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(Elevation elevation) {
		this.elevation = elevation;
	}
	/**
	 * @return the stationIdentifier
	 */
	public String getStationIdentifier() {
		return stationIdentifier;
	}
	/**
	 * @param stationIdentifier the stationIdentifier to set
	 */
	public void setStationIdentifier(String stationIdentifier) {
		this.stationIdentifier = stationIdentifier;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	/**
	 * @return the forecast
	 */
	public String getForecast() {
		return forecast;
	}
	/**
	 * @param forecast the forecast to set
	 */
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}
	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}
	/**
	 * @return the fireWeatherZone
	 */
	public String getFireWeatherZone() {
		return fireWeatherZone;
	}
	/**
	 * @param fireWeatherZone the fireWeatherZone to set
	 */
	public void setFireWeatherZone(String fireWeatherZone) {
		this.fireWeatherZone = fireWeatherZone;
	}
	@Override
	public int hashCode() {
		return Objects.hash(county, elevation, fireWeatherZone, forecast, id, name, stationIdentifier, timeZone, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Properties other = (Properties) obj;
		return Objects.equals(county, other.county) && Objects.equals(elevation, other.elevation)
				&& Objects.equals(fireWeatherZone, other.fireWeatherZone) && Objects.equals(forecast, other.forecast)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(stationIdentifier, other.stationIdentifier)
				&& Objects.equals(timeZone, other.timeZone) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "Properties [id=" + id + ", type=" + type + ", elevation=" + elevation + ", stationIdentifier="
				+ stationIdentifier + ", name=" + name + ", timeZone=" + timeZone + ", forecast=" + forecast
				+ ", county=" + county + ", fireWeatherZone=" + fireWeatherZone + "]";
	}
	
	
	

}
