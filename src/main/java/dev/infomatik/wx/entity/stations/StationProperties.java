package dev.infomatik.wx.entity.stations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class StationProperties {
    // generated from : https://api.weather.gov/stations/KFDK/observations/latest
	
	@JsonProperty("@id")
	private String id;// ": "https://api.weather.gov/stations/KFDK/observations/2024-07-11T22:47:00+00:00",
	@JsonProperty("@type")
	private String type; //": "wx:ObservationStation",
	private StationUnits elevation;
	private String  station; //": "https://api.weather.gov/stations/KFDK",
	private String  timestamp; //": "2024-07-11T22:47:00+00:00",
	private String  rawMessage; //": "KFDK 112247Z 35008KT 10SM BKN055 31/17 A3005",
	private String  textDescription;//": "Mostly Cloudy",
	private String  icon; //": "https://api.weather.gov/icons/land/day/bkn?size=medium",	
	@JsonIgnore
	private List<String> presentWeather; //": [],
	private StationUnits temperature;
	private StationUnits dewpoint;
	private StationUnits windDirection;
	private StationUnits windSpeed;
	private StationUnits windGust;
	private StationUnits barometricPressure;
	private StationUnits seaLevelPressure;
	private StationUnits visibility;
	private StationUnits maxTemperatureLast24Hours;
	private StationUnits minTemperatureLast24Hours;
	private StationUnits precipitationLastHour;
	private StationUnits precipitationLast3Hours;
	private StationUnits precipitationLast6Hours;
	private StationUnits relativeHumidity;
	private StationUnits windChill;
	private StationUnits heatIndex;
	private List<CloudLayers> cloudLayers; //": [
	
	/** Data comes from code not API */
	@JsonIgnore
	private double distance;
	/** Data comes from code not API */
	@JsonIgnore
	private String name;
	
	
	//time convert  2024-07-14T12:47:00+00:00
	/**
	 * Get localt time as string.
	 * @param date
	 * @return 
	 * @throws ParseException
	 */
	public String localTime(String date) throws ParseException {
		SimpleDateFormat datetimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssX");
		Date formatDate = datetimeFormatter.parse(date);
		
		String local = formatDate.toLocaleString();
		//System.out.println("date :" + formatDate);
		//System.out.println("date local:" + local);		
		//Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());
		return local;
	}

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
	public StationUnits getElevation() {
		return elevation;
	}

	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(StationUnits elevation) {
		this.elevation = elevation;
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
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		
		String time = timestamp;
		try {
			time = localTime(timestamp);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		this.timestamp = time;
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the presentWeather
	 */
	public List<String> getPresentWeather() {
		return presentWeather;
	}

	/**
	 * @param presentWeather the presentWeather to set
	 */
	public void setPresentWeather(List<String> presentWeather) {
		this.presentWeather = presentWeather;
	}

	/**
	 * @return the temperature
	 */
	public StationUnits getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(StationUnits temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the dewpoint
	 */
	public StationUnits getDewpoint() {
		return dewpoint;
	}

	/**
	 * @param dewpoint the dewpoint to set
	 */
	public void setDewpoint(StationUnits dewpoint) {
		this.dewpoint = dewpoint;
	}

	/**
	 * @return the windDirection
	 */
	public StationUnits getWindDirection() {
		return windDirection;
	}

	/**
	 * @param windDirection the windDirection to set
	 */
	public void setWindDirection(StationUnits windDirection) {
		this.windDirection = windDirection;
	}

	/**
	 * @return the windSpeed
	 */
	public StationUnits getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(StationUnits windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	 * @return the windGust
	 */
	public StationUnits getWindGust() {
		return windGust;
	}

	/**
	 * @param windGust the windGust to set
	 */
	public void setWindGust(StationUnits windGust) {
		this.windGust = windGust;
	}

	/**
	 * @return the barometricPressure
	 */
	public StationUnits getBarometricPressure() {
		return barometricPressure;
	}

	/**
	 * @param barometricPressure the barometricPressure to set
	 */
	public void setBarometricPressure(StationUnits barometricPressure) {
		this.barometricPressure = barometricPressure;
	}

	/**
	 * @return the seaLevelPressure
	 */
	public StationUnits getSeaLevelPressure() {
		return seaLevelPressure;
	}

	/**
	 * @param seaLevelPressure the seaLevelPressure to set
	 */
	public void setSeaLevelPressure(StationUnits seaLevelPressure) {
		this.seaLevelPressure = seaLevelPressure;
	}

	/**
	 * @return the visibility
	 */
	public StationUnits getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(StationUnits visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the maxTemperatureLast24Hours
	 */
	public StationUnits getMaxTemperatureLast24Hours() {
		return maxTemperatureLast24Hours;
	}

	/**
	 * @param maxTemperatureLast24Hours the maxTemperatureLast24Hours to set
	 */
	public void setMaxTemperatureLast24Hours(StationUnits maxTemperatureLast24Hours) {
		this.maxTemperatureLast24Hours = maxTemperatureLast24Hours;
	}

	/**
	 * @return the minTemperatureLast24Hours
	 */
	public StationUnits getMinTemperatureLast24Hours() {
		return minTemperatureLast24Hours;
	}

	/**
	 * @param minTemperatureLast24Hours the minTemperatureLast24Hours to set
	 */
	public void setMinTemperatureLast24Hours(StationUnits minTemperatureLast24Hours) {
		this.minTemperatureLast24Hours = minTemperatureLast24Hours;
	}

	/**
	 * @return the precipitationLastHour
	 */
	public StationUnits getPrecipitationLastHour() {
		return precipitationLastHour;
	}

	/**
	 * @param precipitationLastHour the precipitationLastHour to set
	 */
	public void setPrecipitationLastHour(StationUnits precipitationLastHour) {
		this.precipitationLastHour = precipitationLastHour;
	}

	/**
	 * @return the precipitationLast3Hours
	 */
	public StationUnits getPrecipitationLast3Hours() {
		return precipitationLast3Hours;
	}

	/**
	 * @param precipitationLast3Hours the precipitationLast3Hours to set
	 */
	public void setPrecipitationLast3Hours(StationUnits precipitationLast3Hours) {
		this.precipitationLast3Hours = precipitationLast3Hours;
	}

	/**
	 * @return the precipitationLast6Hours
	 */
	public StationUnits getPrecipitationLast6Hours() {
		return precipitationLast6Hours;
	}

	/**
	 * @param precipitationLast6Hours the precipitationLast6Hours to set
	 */
	public void setPrecipitationLast6Hours(StationUnits precipitationLast6Hours) {
		this.precipitationLast6Hours = precipitationLast6Hours;
	}

	/**
	 * @return the relativeHumidity
	 */
	public StationUnits getRelativeHumidity() {
		return relativeHumidity;
	}

	/**
	 * @param relativeHumidity the relativeHumidity to set
	 */
	public void setRelativeHumidity(StationUnits relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}

	/**
	 * @return the windChill
	 */
	public StationUnits getWindChill() {
		return windChill;
	}

	/**
	 * @param windChill the windChill to set
	 */
	public void setWindChill(StationUnits windChill) {
		this.windChill = windChill;
	}

	/**
	 * @return the heatIndex
	 */
	public StationUnits getHeatIndex() {
		return heatIndex;
	}

	/**
	 * @param heatIndex the heatIndex to set
	 */
	public void setHeatIndex(StationUnits heatIndex) {
		this.heatIndex = heatIndex;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the cloudLayers
	 */
	public List<CloudLayers> getCloudLayers() {
		return cloudLayers;
	}

	/**
	 * @param cloudLayers the cloudLayers to set
	 */
	public void setCloudLayers(List<CloudLayers> cloudLayers) {
		this.cloudLayers = cloudLayers;
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
		return Objects.hash(barometricPressure, cloudLayers, dewpoint, distance, elevation, heatIndex, icon, id,
				maxTemperatureLast24Hours, minTemperatureLast24Hours, name, precipitationLast3Hours,
				precipitationLast6Hours, precipitationLastHour, presentWeather, rawMessage, relativeHumidity,
				seaLevelPressure, station, temperature, textDescription, timestamp, type, visibility, windChill,
				windDirection, windGust, windSpeed);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StationProperties other = (StationProperties) obj;
		return Objects.equals(barometricPressure, other.barometricPressure)
				&& Objects.equals(cloudLayers, other.cloudLayers) && Objects.equals(dewpoint, other.dewpoint)
				&& Double.doubleToLongBits(distance) == Double.doubleToLongBits(other.distance)
				&& Objects.equals(elevation, other.elevation) && Objects.equals(heatIndex, other.heatIndex)
				&& Objects.equals(icon, other.icon) && Objects.equals(id, other.id)
				&& Objects.equals(maxTemperatureLast24Hours, other.maxTemperatureLast24Hours)
				&& Objects.equals(minTemperatureLast24Hours, other.minTemperatureLast24Hours)
				&& Objects.equals(name, other.name)
				&& Objects.equals(precipitationLast3Hours, other.precipitationLast3Hours)
				&& Objects.equals(precipitationLast6Hours, other.precipitationLast6Hours)
				&& Objects.equals(precipitationLastHour, other.precipitationLastHour)
				&& Objects.equals(presentWeather, other.presentWeather) && Objects.equals(rawMessage, other.rawMessage)
				&& Objects.equals(relativeHumidity, other.relativeHumidity)
				&& Objects.equals(seaLevelPressure, other.seaLevelPressure) && Objects.equals(station, other.station)
				&& Objects.equals(temperature, other.temperature)
				&& Objects.equals(textDescription, other.textDescription) && Objects.equals(timestamp, other.timestamp)
				&& Objects.equals(type, other.type) && Objects.equals(visibility, other.visibility)
				&& Objects.equals(windChill, other.windChill) && Objects.equals(windDirection, other.windDirection)
				&& Objects.equals(windGust, other.windGust) && Objects.equals(windSpeed, other.windSpeed);
	}

	@Override
	public String toString() {
		return "StationProperties [id=" + id + ", type=" + type + ", elevation=" + elevation + ", station=" + station
				+ ", timestamp=" + timestamp + ", rawMessage=" + rawMessage + ", textDescription=" + textDescription
				+ ", icon=" + icon + ", presentWeather=" + presentWeather + ", temperature=" + temperature
				+ ", dewpoint=" + dewpoint + ", windDirection=" + windDirection + ", windSpeed=" + windSpeed
				+ ", windGust=" + windGust + ", barometricPressure=" + barometricPressure + ", seaLevelPressure="
				+ seaLevelPressure + ", visibility=" + visibility + ", maxTemperatureLast24Hours="
				+ maxTemperatureLast24Hours + ", minTemperatureLast24Hours=" + minTemperatureLast24Hours
				+ ", precipitationLastHour=" + precipitationLastHour + ", precipitationLast3Hours="
				+ precipitationLast3Hours + ", precipitationLast6Hours=" + precipitationLast6Hours
				+ ", relativeHumidity=" + relativeHumidity + ", windChill=" + windChill + ", heatIndex=" + heatIndex
				+ ", distance=" + distance + ", name=" + name + ", cloudLayers=" + cloudLayers + "]";
	}


}
