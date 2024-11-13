package dev.infomatik.wx.entity.points;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelativeLocation {


	private String type;
	private Geometry geometry;
	
	@JsonProperty("properties")
	private LocationProperties properties;

	private String forecastZone; //": "https://api.weather.gov/zones/forecast/DCZ001",
	private String county; //": "https://api.weather.gov/zones/county/DCC001",
	private String fireWeatherZone; //": "https://api.weather.gov/zones/fire/DCZ001",
	private String timeZone; //: "America/New_York",
	private String radarStation; //": "KLWX"
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
	 * @return the geometry
	 */
	public Geometry getGeometry() {
		return geometry;
	}
	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	/**
	 * @return the properties
	 */
	public LocationProperties getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(LocationProperties properties) {
		this.properties = properties;
	}
	/**
	 * @return the forecastZone
	 */
	public String getForecastZone() {
		return forecastZone;
	}
	/**
	 * @param forecastZone the forecastZone to set
	 */
	public void setForecastZone(String forecastZone) {
		this.forecastZone = forecastZone;
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
	 * @return the radarStation
	 */
	public String getRadarStation() {
		return radarStation;
	}
	/**
	 * @param radarStation the radarStation to set
	 */
	public void setRadarStation(String radarStation) {
		this.radarStation = radarStation;
	}
	@Override
	public int hashCode() {
		return Objects.hash(county, fireWeatherZone, forecastZone, geometry, properties, radarStation, timeZone, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelativeLocation other = (RelativeLocation) obj;
		return Objects.equals(county, other.county) && Objects.equals(fireWeatherZone, other.fireWeatherZone)
				&& Objects.equals(forecastZone, other.forecastZone) && Objects.equals(geometry, other.geometry)
				&& Objects.equals(properties, other.properties) && Objects.equals(radarStation, other.radarStation)
				&& Objects.equals(timeZone, other.timeZone) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "RelativeLocation [type=" + type + ", geometry=" + geometry + ", properties=" + properties
				+ ", forecastZone=" + forecastZone + ", county=" + county + ", fireWeatherZone=" + fireWeatherZone
				+ ", timeZone=" + timeZone + ", radarStation=" + radarStation + "]";
	}



}