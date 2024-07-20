package com.kd3su.wx.entity.points;

import java.util.Objects;

public class LocationProperties {

	private String city; //": "King City",
	private String state; //": "CA",
	private Units distance;//": {
	//"unitCode": "wmoUnit:m",
	//"value": 30750.598272064999

	private Units bearing; //": {
	//	"unitCode": "wmoUnit:degree_(angle)",
	//	"value": 44

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the distance
	 */
	public Units getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Units distance) {
		this.distance = distance;
	}

	/**
	 * @return the bearing
	 */
	public Units getBearing() {
		return bearing;
	}

	/**
	 * @param bearing the bearing to set
	 */
	public void setBearing(Units bearing) {
		this.bearing = bearing;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bearing, city, distance, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationProperties other = (LocationProperties) obj;
		return Objects.equals(bearing, other.bearing) && Objects.equals(city, other.city)
				&& Objects.equals(distance, other.distance) && Objects.equals(state, other.state);
	}

	@Override
	public String toString() {
		return "LocationProperties [city=" + city + ", state=" + state + ", distance=" + distance + ", bearing="
				+ bearing + "]";
	}




}
