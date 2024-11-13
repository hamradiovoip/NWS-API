package dev.infomatik.wx.entity.stations;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Weather observation
 */
public class Observation {

		
	private double temperature;
	private double dewpoint;
	private double windDirection;
	private double windSpeed;
	private double windGust;
	private double barometricPressure;
	private double seaLevelPressure;
	private double visibility;
	private double maxTemperatureLast24Hours;
	private double minTemperatureLast24Hours;
	private double precipitationLastHour;
	private double precipitationLast3Hours;
	private double precipitationLast6Hours;
	private double relativeHumidity;
	private double windChill;
	private double heatIndex;
	private String timestamp; //": "2024-07-11T22:47:00+00:00",
	private String rawMessage; //": "KFDK 112247Z 35008KT 10SM BKN055 31/17 A3005",
	private String textDescription;//": "Mostly Cloudy",
	private String location;
	private String station; //": "https://api.weather.gov/stations/KFDK",
	private String name;
	private String stationIdentifier;
	@JsonIgnore
	private String cloudCover;
	@JsonIgnore
	private String precipitation;
	
	
	
	/**
	 * @return the precipitation
	 */
	public String getPrecipitation() {
		return precipitation;
	}
	/**
	 * @param precipitation the precipitation to set
	 */
	public void setPrecipitation(String precipitation) {
		this.precipitation = precipitation;
	}
	/**
	 * @return the cloudCover
	 */
	public String getCloudCover() {
		return cloudCover;
	}
	/**
	 * @param cloudCover the cloudCover to set
	 */
	public void setCloudCover(String cloudCover) {
		this.cloudCover = cloudCover;
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
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the dewpoint
	 */
	public double getDewpoint() {
		return dewpoint;
	}
	/**
	 * @param dewpoint the dewpoint to set
	 */
	public void setDewpoint(double dewpoint) {
		this.dewpoint = dewpoint;
	}
	/**
	 * @return the windDirection
	 */
	public double getWindDirection() {
		return windDirection;
	}
	/**
	 * @param windDirection the windDirection to set
	 */
	public void setWindDirection(double windDirection) {
		this.windDirection = windDirection;
	}
	/**
	 * @return the windSpeed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}
	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	/**
	 * @return the windGust
	 */
	public double getWindGust() {
		return windGust;
	}
	/**
	 * @param windGust the windGust to set
	 */
	public void setWindGust(double windGust) {
		this.windGust = windGust;
	}
	/**
	 * @return the barometricPressure
	 */
	public double getBarometricPressure() {
		return barometricPressure;
	}
	/**
	 * @param barometricPressure the barometricPressure to set
	 */
	public void setBarometricPressure(double barometricPressure) {
		this.barometricPressure = barometricPressure;
	}
	/**
	 * @return the seaLevelPressure
	 */
	public double getSeaLevelPressure() {
		return seaLevelPressure;
	}
	/**
	 * @param seaLevelPressure the seaLevelPressure to set
	 */
	public void setSeaLevelPressure(double seaLevelPressure) {
		this.seaLevelPressure = seaLevelPressure;
	}
	/**
	 * @return the visibility
	 */
	public double getVisibility() {
		return visibility;
	}
	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(double visibility) {
		this.visibility = visibility;
	}
	/**
	 * @return the maxTemperatureLast24Hours
	 */
	public double getMaxTemperatureLast24Hours() {
		return maxTemperatureLast24Hours;
	}
	/**
	 * @param maxTemperatureLast24Hours the maxTemperatureLast24Hours to set
	 */
	public void setMaxTemperatureLast24Hours(double maxTemperatureLast24Hours) {
		this.maxTemperatureLast24Hours = maxTemperatureLast24Hours;
	}
	/**
	 * @return the minTemperatureLast24Hours
	 */
	public double getMinTemperatureLast24Hours() {
		return minTemperatureLast24Hours;
	}
	/**
	 * @param minTemperatureLast24Hours the minTemperatureLast24Hours to set
	 */
	public void setMinTemperatureLast24Hours(double minTemperatureLast24Hours) {
		this.minTemperatureLast24Hours = minTemperatureLast24Hours;
	}
	/**
	 * @return the precipitationLastHour
	 */
	public double getPrecipitationLastHour() {
		return precipitationLastHour;
	}
	/**
	 * @param precipitationLastHour the precipitationLastHour to set
	 */
	public void setPrecipitationLastHour(double precipitationLastHour) {
		this.precipitationLastHour = precipitationLastHour;
	}
	/**
	 * @return the precipitationLast3Hours
	 */
	public double getPrecipitationLast3Hours() {
		return precipitationLast3Hours;
	}
	/**
	 * @param precipitationLast3Hours the precipitationLast3Hours to set
	 */
	public void setPrecipitationLast3Hours(double precipitationLast3Hours) {
		this.precipitationLast3Hours = precipitationLast3Hours;
	}
	/**
	 * @return the precipitationLast6Hours
	 */
	public double getPrecipitationLast6Hours() {
		return precipitationLast6Hours;
	}
	/**
	 * @param precipitationLast6Hours the precipitationLast6Hours to set
	 */
	public void setPrecipitationLast6Hours(double precipitationLast6Hours) {
		this.precipitationLast6Hours = precipitationLast6Hours;
	}
	/**
	 * @return the relativeHumidity
	 */
	public double getRelativeHumidity() {
		return relativeHumidity;
	}
	/**
	 * @param relativeHumidity the relativeHumidity to set
	 */
	public void setRelativeHumidity(double relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}
	/**
	 * @return the windChill
	 */
	public double getWindChill() {
		return windChill;
	}
	/**
	 * @param windChill the windChill to set
	 */
	public void setWindChill(double windChill) {
		this.windChill = windChill;
	}
	/**
	 * @return the heatIndex
	 */
	public double getHeatIndex() {
		return heatIndex;
	}
	/**
	 * @param heatIndex the heatIndex to set
	 */
	public void setHeatIndex(double heatIndex) {
		this.heatIndex = heatIndex;
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the rawMessage
	 */
	public String getRawMessage() {
		return rawMessage;
	}
	/**
	 * @param rawMessage the rawMessage to set
	 */
	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}
	/**
	 * @return the textDescription
	 */
	public String getTextDescription() {
		return textDescription;
	}
	/**
	 * @param textDescription the textDescription to set
	 */
	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}
	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
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
	@Override
	public int hashCode() {
		return Objects.hash(barometricPressure, cloudCover, dewpoint, heatIndex, location, maxTemperatureLast24Hours,
				minTemperatureLast24Hours, name, precipitationLast3Hours, precipitationLast6Hours,
				precipitationLastHour, rawMessage, relativeHumidity, seaLevelPressure, station, stationIdentifier,
				temperature, textDescription, timestamp, visibility, windChill, windDirection, windGust, windSpeed);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Observation other = (Observation) obj;
		return Double.doubleToLongBits(barometricPressure) == Double.doubleToLongBits(other.barometricPressure)
				&& Objects.equals(cloudCover, other.cloudCover)
				&& Double.doubleToLongBits(dewpoint) == Double.doubleToLongBits(other.dewpoint)
				&& Double.doubleToLongBits(heatIndex) == Double.doubleToLongBits(other.heatIndex)
				&& Objects.equals(location, other.location)
				&& Double.doubleToLongBits(maxTemperatureLast24Hours) == Double
						.doubleToLongBits(other.maxTemperatureLast24Hours)
				&& Double.doubleToLongBits(minTemperatureLast24Hours) == Double
						.doubleToLongBits(other.minTemperatureLast24Hours)
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(precipitationLast3Hours) == Double
						.doubleToLongBits(other.precipitationLast3Hours)
				&& Double.doubleToLongBits(precipitationLast6Hours) == Double
						.doubleToLongBits(other.precipitationLast6Hours)
				&& Double.doubleToLongBits(precipitationLastHour) == Double
						.doubleToLongBits(other.precipitationLastHour)
				&& Objects.equals(rawMessage, other.rawMessage)
				&& Double.doubleToLongBits(relativeHumidity) == Double.doubleToLongBits(other.relativeHumidity)
				&& Double.doubleToLongBits(seaLevelPressure) == Double.doubleToLongBits(other.seaLevelPressure)
				&& Objects.equals(station, other.station) && Objects.equals(stationIdentifier, other.stationIdentifier)
				&& Double.doubleToLongBits(temperature) == Double.doubleToLongBits(other.temperature)
				&& Objects.equals(textDescription, other.textDescription) && Objects.equals(timestamp, other.timestamp)
				&& Double.doubleToLongBits(visibility) == Double.doubleToLongBits(other.visibility)
				&& Double.doubleToLongBits(windChill) == Double.doubleToLongBits(other.windChill)
				&& Double.doubleToLongBits(windDirection) == Double.doubleToLongBits(other.windDirection)
				&& Double.doubleToLongBits(windGust) == Double.doubleToLongBits(other.windGust)
				&& Double.doubleToLongBits(windSpeed) == Double.doubleToLongBits(other.windSpeed);
	}
	@Override
	public String toString() {
		return "Observation [temperature=" + temperature + ", dewpoint=" + dewpoint + ", windDirection=" + windDirection
				+ ", windSpeed=" + windSpeed + ", windGust=" + windGust + ", barometricPressure=" + barometricPressure
				+ ", seaLevelPressure=" + seaLevelPressure + ", visibility=" + visibility
				+ ", maxTemperatureLast24Hours=" + maxTemperatureLast24Hours + ", minTemperatureLast24Hours="
				+ minTemperatureLast24Hours + ", precipitationLastHour=" + precipitationLastHour
				+ ", precipitationLast3Hours=" + precipitationLast3Hours + ", precipitationLast6Hours="
				+ precipitationLast6Hours + ", relativeHumidity=" + relativeHumidity + ", windChill=" + windChill
				+ ", heatIndex=" + heatIndex + ", timestamp=" + timestamp + ", rawMessage=" + rawMessage
				+ ", textDescription=" + textDescription + ", location=" + location + ", station=" + station + ", name="
				+ name + ", stationIdentifier=" + stationIdentifier + ", cloudCover=" + cloudCover + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
