package com.kd3su.wx.entity.points;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PointProperties {
	/*
	 "properties": {
	        "@id": "https://api.weather.gov/points/36.414,-120.892",
	        "@type": "wx:Point",
	        "cwa": "MTR",
	        "forecastOffice": "https://api.weather.gov/offices/MTR",
	        "gridId": "MTR",
	        "gridX": 126,
	        "gridY": 35,
	        "forecast": "https://api.weather.gov/gridpoints/MTR/126,35/forecast",
	        "forecastHourly": "https://api.weather.gov/gridpoints/MTR/126,35/forecast/hourly",
	        "forecastGridData": "https://api.weather.gov/gridpoints/MTR/126,35",
	        "observationStations": "https://api.weather.gov/gridpoints/MTR/126,35/stations",
	        "relativeLocation": {
	            "type": "Feature",
	            "geometry": {
	                "type": "Point",
	                "coordinates": [
	                    -121.131962,
	                    36.216282999999997
	                ]
	            },
	            "properties": {
	                "city": "King City",
	                "state": "CA",
	                "distance": {
	                    "unitCode": "wmoUnit:m",
	                    "value": 30750.598272064999
	                },
	                "bearing": {
	                    "unitCode": "wmoUnit:degree_(angle)",
	                    "value": 44
	                }
	            }
	        },
	        "forecastZone": "https://api.weather.gov/zones/forecast/CAZ518",
	        "county": "https://api.weather.gov/zones/county/CAC069",
	        "fireWeatherZone": "https://api.weather.gov/zones/fire/CAZ518",
	        "timeZone": "America/Los_Angeles",
	        "radarStation": "KHNX"
	    }
	 */

	//"properties": {

	@JsonProperty("@id")
	private String id;
	@JsonProperty("@type")
	private String type;
	private String cwa;  //"MTR",
	private String forecastOffice;  //: "https://api.weather.gov/offices/MTR",
	private String gridId;  // "MTR",
	private String gridX;  // 126,
	private String gridY;  //": 35,
	private String forecast;  //": "https://api.weather.gov/gridpoints/MTR/126,35/forecast",
	private String forecastHourly;  //": "https://api.weather.gov/gridpoints/MTR/126,35/forecast/hourly",
	private String forecastGridData;  //": "https://api.weather.gov/gridpoints/MTR/126,35",
	private String observationStations; //": "https://api.weather.gov/gridpoints/MTR/126,35/stations",
	@JsonProperty("relativeLocation")
	private RelativeLocation relativeLocation;  //": {
	
//	@JsonProperty("properties")
//	private LocationProperties	properties;

	private String forecastZone;  //": "https://api.weather.gov/zones/forecast/CAZ518",
	private String county;  //": "https://api.weather.gov/zones/county/CAC069",
	private String fireWeatherZone;  //": "https://api.weather.gov/zones/fire/CAZ518",
	private String timeZone;  //": "America/Los_Angeles",
	private String radarStation;  //": "KHNX"
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
	 * @return the cwa
	 */
	public String getCwa() {
		return cwa;
	}
	/**
	 * @param cwa the cwa to set
	 */
	public void setCwa(String cwa) {
		this.cwa = cwa;
	}
	/**
	 * @return the forecastOffice
	 */
	public String getForecastOffice() {
		return forecastOffice;
	}
	/**
	 * @param forecastOffice the forecastOffice to set
	 */
	public void setForecastOffice(String forecastOffice) {
		this.forecastOffice = forecastOffice;
	}
	/**
	 * @return the gridId
	 */
	public String getGridId() {
		return gridId;
	}
	/**
	 * @param gridId the gridId to set
	 */
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}
	/**
	 * @return the gridX
	 */
	public String getGridX() {
		return gridX;
	}
	/**
	 * @param gridX the gridX to set
	 */
	public void setGridX(String gridX) {
		this.gridX = gridX;
	}
	/**
	 * @return the gridY
	 */
	public String getGridY() {
		return gridY;
	}
	/**
	 * @param gridY the gridY to set
	 */
	public void setGridY(String gridY) {
		this.gridY = gridY;
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
	 * @return the forecastHourly
	 */
	public String getForecastHourly() {
		return forecastHourly;
	}
	/**
	 * @param forecastHourly the forecastHourly to set
	 */
	public void setForecastHourly(String forecastHourly) {
		this.forecastHourly = forecastHourly;
	}
	/**
	 * @return the forecastGridData
	 */
	public String getForecastGridData() {
		return forecastGridData;
	}
	/**
	 * @param forecastGridData the forecastGridData to set
	 */
	public void setForecastGridData(String forecastGridData) {
		this.forecastGridData = forecastGridData;
	}
	/**
	 * @return the observationStations
	 */
	public String getObservationStations() {
		return observationStations;
	}
	/**
	 * @param observationStations the observationStations to set
	 */
	public void setObservationStations(String observationStations) {
		this.observationStations = observationStations;
	}
	/**
	 * @return the relativeLocation
	 */
	public RelativeLocation getRelativeLocation() {
		return relativeLocation;
	}
	/**
	 * @param relativeLocation the relativeLocation to set
	 */
	public void setRelativeLocation(RelativeLocation relativeLocation) {
		this.relativeLocation = relativeLocation;
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
		return Objects.hash(county, cwa, fireWeatherZone, forecast, forecastGridData, forecastHourly, forecastOffice,
				forecastZone, gridId, gridX, gridY, id, observationStations, radarStation, relativeLocation, timeZone,
				type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointProperties other = (PointProperties) obj;
		return Objects.equals(county, other.county) && Objects.equals(cwa, other.cwa)
				&& Objects.equals(fireWeatherZone, other.fireWeatherZone) && Objects.equals(forecast, other.forecast)
				&& Objects.equals(forecastGridData, other.forecastGridData)
				&& Objects.equals(forecastHourly, other.forecastHourly)
				&& Objects.equals(forecastOffice, other.forecastOffice)
				&& Objects.equals(forecastZone, other.forecastZone) && Objects.equals(gridId, other.gridId)
				&& Objects.equals(gridX, other.gridX) && Objects.equals(gridY, other.gridY)
				&& Objects.equals(id, other.id) && Objects.equals(observationStations, other.observationStations)
				&& Objects.equals(radarStation, other.radarStation)
				&& Objects.equals(relativeLocation, other.relativeLocation) && Objects.equals(timeZone, other.timeZone)
				&& Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "PointProperties [id=" + id + ", type=" + type + ", cwa=" + cwa + ", forecastOffice=" + forecastOffice
				+ ", gridId=" + gridId + ", gridX=" + gridX + ", gridY=" + gridY + ", forecast=" + forecast
				+ ", forecastHourly=" + forecastHourly + ", forecastGridData=" + forecastGridData
				+ ", observationStations=" + observationStations + ", relativeLocation=" + relativeLocation
				+ ", forecastZone=" + forecastZone + ", county=" + county + ", fireWeatherZone=" + fireWeatherZone
				+ ", timeZone=" + timeZone + ", radarStation=" + radarStation + "]";
	}
	
	
	


}
