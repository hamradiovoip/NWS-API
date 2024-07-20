package com.kd3su.wx.entity.points;

import java.util.Objects;

public class Location {

	private String type; //": "Feature",
	private Geometry   geometry;
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
	@Override
	public int hashCode() {
		return Objects.hash(geometry, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(geometry, other.geometry) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "Location [type=" + type + ", geometry=" + geometry + "]";
	}
	
	
	
}
