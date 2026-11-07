package dev.infomatik.wx.entity;

import java.util.Objects;

/*
 * Map coords and other info.
 */
public class MapPoints {

	private double lat;
	private double lon;
	private Elevation elevation;
	private String stationName;
	private double temperature;
	private String  textDescription;
	private String stationIdentifier; //TODO
		
	
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
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}
	/**
	 * @param lon the lon to set
	 */
	public void setLon(double lon) {
		this.lon = lon;
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
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(elevation, lat, lon, stationName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapPoints other = (MapPoints) obj;
		return Objects.equals(elevation, other.elevation)
				&& Double.doubleToLongBits(lat) == Double.doubleToLongBits(other.lat)
				&& Double.doubleToLongBits(lon) == Double.doubleToLongBits(other.lon)
				&& Objects.equals(stationName, other.stationName);
	}
	@Override
	public String toString() {
		return "MapPoints [lat=" + lat + ", lon=" + lon + ", elevation=" + elevation + ", stationName=" + stationName
				+ "]";
	}
	
	
}
