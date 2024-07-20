package com.kd3su.wx.entity.alerts;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Features {
	
	
	@JsonProperty("id")
	String id;
	@JsonProperty("type")
	String type;
	@JsonIgnore
	@JsonProperty("geometry")
	String geometry;
	
	@JsonProperty("properties")
	AlertProperties properties;

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
	public String getGeometry() {
		return geometry;
	}

	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	/**
	 * @return the properties
	 */
	public AlertProperties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(AlertProperties properties) {
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
		Features other = (Features) obj;
		return Objects.equals(geometry, other.geometry) && Objects.equals(id, other.id)
				&& Objects.equals(properties, other.properties) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Features [id=" + id + ", type=" + type + ", geometry=" + geometry + ", properties=" + properties + "]";
	}

	
	
}
