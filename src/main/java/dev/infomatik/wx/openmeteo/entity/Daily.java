package dev.infomatik.wx.openmeteo.entity;

import java.util.List;
import java.util.Objects;

public class Daily {


	private List<String> time; 
	private List<String> temperature_2m_max;
	private List<String> temperature_2m_min;
	private List<String> sunrise;
	private List<String> sunset;
	private List<Double> daylight_duration;
	private List<Double> sunshine_duration;
	private List<Double> precipitation_sum;
	/**
	 * @return the time
	 */
	public List<String> getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(List<String> time) {
		this.time = time;
	}
	/**
	 * @return the temperature_2m_max
	 */
	public List<String> getTemperature_2m_max() {
		return temperature_2m_max;
	}
	/**
	 * @param temperature_2m_max the temperature_2m_max to set
	 */
	public void setTemperature_2m_max(List<String> temperature_2m_max) {
		this.temperature_2m_max = temperature_2m_max;
	}
	/**
	 * @return the temperature_2m_min
	 */
	public List<String> getTemperature_2m_min() {
		return temperature_2m_min;
	}
	/**
	 * @param temperature_2m_min the temperature_2m_min to set
	 */
	public void setTemperature_2m_min(List<String> temperature_2m_min) {
		this.temperature_2m_min = temperature_2m_min;
	}
	/**
	 * @return the sunrise
	 */
	public List<String> getSunrise() {
		return sunrise;
	}
	/**
	 * @param sunrise the sunrise to set
	 */
	public void setSunrise(List<String> sunrise) {
		this.sunrise = sunrise;
	}
	/**
	 * @return the sunset
	 */
	public List<String> getSunset() {
		return sunset;
	}
	/**
	 * @param sunset the sunset to set
	 */
	public void setSunset(List<String> sunset) {
		this.sunset = sunset;
	}
	/**
	 * @return the daylight_duration
	 */
	public List<Double> getDaylight_duration() {
		return daylight_duration;
	}
	/**
	 * @param daylight_duration the daylight_duration to set
	 */
	public void setDaylight_duration(List<Double> daylight_duration) {
		this.daylight_duration = daylight_duration;
	}
	/**
	 * @return the sunshine_duration
	 */
	public List<Double> getSunshine_duration() {
		return sunshine_duration;
	}
	/**
	 * @param sunshine_duration the sunshine_duration to set
	 */
	public void setSunshine_duration(List<Double> sunshine_duration) {
		this.sunshine_duration = sunshine_duration;
	}
	/**
	 * @return the precipitation_sum
	 */
	public List<Double> getPrecipitation_sum() {
		return precipitation_sum;
	}
	/**
	 * @param precipitation_sum the precipitation_sum to set
	 */
	public void setPrecipitation_sum(List<Double> precipitation_sum) {
		this.precipitation_sum = precipitation_sum;
	}
	@Override
	public int hashCode() {
		return Objects.hash(daylight_duration, precipitation_sum, sunrise, sunset, sunshine_duration,
				temperature_2m_max, temperature_2m_min, time);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Daily other = (Daily) obj;
		return Objects.equals(daylight_duration, other.daylight_duration)
				&& Objects.equals(precipitation_sum, other.precipitation_sum) && Objects.equals(sunrise, other.sunrise)
				&& Objects.equals(sunset, other.sunset) && Objects.equals(sunshine_duration, other.sunshine_duration)
				&& Objects.equals(temperature_2m_max, other.temperature_2m_max)
				&& Objects.equals(temperature_2m_min, other.temperature_2m_min) && Objects.equals(time, other.time);
	}
	@Override
	public String toString() {
		return "Daily [time=" + time + ", temperature_2m_max=" + temperature_2m_max + ", temperature_2m_min="
				+ temperature_2m_min + ", sunrise=" + sunrise + ", sunset=" + sunset + ", daylight_duration="
				+ daylight_duration + ", sunshine_duration=" + sunshine_duration + ", precipitation_sum="
				+ precipitation_sum + "]";
	}



	
	
}