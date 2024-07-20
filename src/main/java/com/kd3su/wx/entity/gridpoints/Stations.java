package com.kd3su.wx.entity.gridpoints;


import java.util.Objects;

public class Stations {

	
//id	"https://api.weather.gov/stations/KADW"
//type	"Feature"
//geometry	{…}
//properties	{…}
	
	
	private String id;
	private String type;
	private Geometry geometry;	
	private Properties properties;
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
	public Properties getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	@Override
	public int hashCode() {
		return Objects.hash(geometry, id, properties, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stations other = (Stations) obj;
		return Objects.equals(geometry, other.geometry) && Objects.equals(id, other.id)
				&& Objects.equals(properties, other.properties) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "Stations [id=" + id + ", type=" + type + ", geometry=" + geometry + ", properties=" + properties + "]";
	} 
	
	
	
	
	
	
}
