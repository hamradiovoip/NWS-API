package dev.infomatik.wx.openmeteo.entity;
import java.util.Objects;
// open meteo
public class DailyUnits {

	
	
	
	/*
	daily_units	
	time	"iso8601"
	temperature_2m_max	"°F"
	temperature_2m_min	"°F"
	sunrise	"iso8601"
	sunset	"iso8601"
	*/

	private String time;    // 	"iso8601"
	private double temperature_2m_max;    // 		"°F"
	private double temperature_2m_min;    // 		"°F"
	private String sunrise;    // 		"iso8601"
	private String sunset;    // 		"iso8601"
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the temperature_2m_max
	 */
	public double getTemperature_2m_max() {
		return temperature_2m_max;
	}
	/**
	 * @param temperature_2m_max the temperature_2m_max to set
	 */
	public void setTemperature_2m_max(double temperature_2m_max) {
		this.temperature_2m_max = temperature_2m_max;
	}
	/**
	 * @return the temperature_2m_min
	 */
	public double getTemperature_2m_min() {
		return temperature_2m_min;
	}
	/**
	 * @param temperature_2m_min the temperature_2m_min to set
	 */
	public void setTemperature_2m_min(double temperature_2m_min) {
		this.temperature_2m_min = temperature_2m_min;
	}
	/**
	 * @return the sunrise
	 */
	public String getSunrise() {
		return sunrise;
	}
	/**
	 * @param sunrise the sunrise to set
	 */
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}
	/**
	 * @return the sunset
	 */
	public String getSunset() {
		return sunset;
	}
	/**
	 * @param sunset the sunset to set
	 */
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}
	@Override
	public int hashCode() {
		return Objects.hash(sunrise, sunset, temperature_2m_max, temperature_2m_min, time);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DailyUnits other = (DailyUnits) obj;
		return Objects.equals(sunrise, other.sunrise) && Objects.equals(sunset, other.sunset)
				&& Double.doubleToLongBits(temperature_2m_max) == Double.doubleToLongBits(other.temperature_2m_max)
				&& Double.doubleToLongBits(temperature_2m_min) == Double.doubleToLongBits(other.temperature_2m_min)
				&& Objects.equals(time, other.time);
	}
	@Override
	public String toString() {
		return "DailyUnits [time=" + time + ", temperature_2m_max=" + temperature_2m_max + ", temperature_2m_min="
				+ temperature_2m_min + ", sunrise=" + sunrise + ", sunset=" + sunset + "]";
	}
	
	
}
