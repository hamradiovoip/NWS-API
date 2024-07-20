package com.kd3su.wx.entity.points;

import java.util.List;
import java.util.Objects;

public class Geometry {


	private String type; //": "Point",
	private List <String> coordinates;
	//": [
	//	                    -121.131962,
	//	                    36.216282999999997
	//	                ]
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
	 * @return the coordinates
	 */
	public List<String> getCoordinates() {
		return coordinates;
	}
	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}
	@Override
	public int hashCode() {
		return Objects.hash(coordinates, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geometry other = (Geometry) obj;
		return Objects.equals(coordinates, other.coordinates) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "Geometry [type=" + type + ", coordinates=" + coordinates + "]";
	}


}
