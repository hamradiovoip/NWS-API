package dev.infomatik.wx.openmeteo.entity;

import java.util.Objects;

public class OpenMeteoEntity {

	
	private Daily daily; 
	private Current current;
	/**
	 * @return the daily
	 */
	public Daily getDaily() {
		return daily;
	}
	/**
	 * @param daily the daily to set
	 */
	public void setDaily(Daily daily) {
		this.daily = daily;
	}
	/**
	 * @return the current
	 */
	public Current getCurrent() {
		return current;
	}
	/**
	 * @param current the current to set
	 */
	public void setCurrent(Current current) {
		this.current = current;
	}
	@Override
	public int hashCode() {
		return Objects.hash(current, daily);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenMeteoEntity other = (OpenMeteoEntity) obj;
		return Objects.equals(current, other.current) && Objects.equals(daily, other.daily);
	}
	@Override
	public String toString() {
		return "OpenMeteoEntity [daily=" + daily + ", current=" + current + "]";
	}
	
	
	
} // OpenMeteoEntity class




 













