package dev.infomatik.wx.openmeteo.entity;

import java.util.Objects;

public class CurrentUnits {

	

	/*
current_units	
time	"iso8601"
interval	"seconds"
temperature_2m	"°F"
relative_humidity_2m	"%"
apparent_temperature	"°F"
is_day	""
precipitation	"inch"
rain	"inch"
showers	"inch"
snowfall	"inch"
weather_code	"wmo code"
cloud_cover	"%"
pressure_msl	"hPa"
surface_pressure	"hPa"
wind_speed_10m	"mp/h"
wind_direction_10m	"°"
wind_gusts_10m	"mp/h"
current	
*/
	
	private String time;  //	"iso8601"
	private String interval;  //		"seconds"
	private String temperature_2m;  //		"°F"
	private String relative_humidity_2m	;  //	"%"
	private String apparent_temperature;  //		"°F"
	private String is_day;  //		""
	private String precipitation;  //		"inch"
	private String rain;  //		"inch";  //	
	private String showers;  //		"inch";  //	
	private String snowfall	;  //	"inch"
	private String weather_code;  //		"wmo code"
	private String cloud_cover;  //		"%"
	private String pressure_msl;  //		"hPa"
	private String surface_pressure;  //		"hPa"
	private String wind_speed_10m;  //		"mp/h"
	private String wind_direction_10m;  //		"°"
	private String wind_gusts_10m;  //		"mp/h"
	//current	
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
	 * @return the interval
	 */
	public String getInterval() {
		return interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(String interval) {
		this.interval = interval;
	}
	/**
	 * @return the temperature_2m
	 */
	public String getTemperature_2m() {
		return temperature_2m;
	}
	/**
	 * @param temperature_2m the temperature_2m to set
	 */
	public void setTemperature_2m(String temperature_2m) {
		this.temperature_2m = temperature_2m;
	}
	/**
	 * @return the relative_humidity_2m
	 */
	public String getRelative_humidity_2m() {
		return relative_humidity_2m;
	}
	/**
	 * @param relative_humidity_2m the relative_humidity_2m to set
	 */
	public void setRelative_humidity_2m(String relative_humidity_2m) {
		this.relative_humidity_2m = relative_humidity_2m;
	}
	/**
	 * @return the apparent_temperature
	 */
	public String getApparent_temperature() {
		return apparent_temperature;
	}
	/**
	 * @param apparent_temperature the apparent_temperature to set
	 */
	public void setApparent_temperature(String apparent_temperature) {
		this.apparent_temperature = apparent_temperature;
	}
	/**
	 * @return the is_day
	 */
	public String getIs_day() {
		return is_day;
	}
	/**
	 * @param is_day the is_day to set
	 */
	public void setIs_day(String is_day) {
		this.is_day = is_day;
	}
	/**
	 * @return the precipitation
	 */
	public String getPrecipitation() {
		return precipitation;
	}
	/**
	 * @param precipitation the precipitation to set
	 */
	public void setPrecipitation(String precipitation) {
		this.precipitation = precipitation;
	}
	/**
	 * @return the rain
	 */
	public String getRain() {
		return rain;
	}
	/**
	 * @param rain the rain to set
	 */
	public void setRain(String rain) {
		this.rain = rain;
	}
	/**
	 * @return the showers
	 */
	public String getShowers() {
		return showers;
	}
	/**
	 * @param showers the showers to set
	 */
	public void setShowers(String showers) {
		this.showers = showers;
	}
	/**
	 * @return the snowfall
	 */
	public String getSnowfall() {
		return snowfall;
	}
	/**
	 * @param snowfall the snowfall to set
	 */
	public void setSnowfall(String snowfall) {
		this.snowfall = snowfall;
	}
	/**
	 * @return the weather_code
	 */
	public String getWeather_code() {
		return weather_code;
	}
	/**
	 * @param weather_code the weather_code to set
	 */
	public void setWeather_code(String weather_code) {
		this.weather_code = weather_code;
	}
	/**
	 * @return the cloud_cover
	 */
	public String getCloud_cover() {
		return cloud_cover;
	}
	/**
	 * @param cloud_cover the cloud_cover to set
	 */
	public void setCloud_cover(String cloud_cover) {
		this.cloud_cover = cloud_cover;
	}
	/**
	 * @return the pressure_msl
	 */
	public String getPressure_msl() {
		return pressure_msl;
	}
	/**
	 * @param pressure_msl the pressure_msl to set
	 */
	public void setPressure_msl(String pressure_msl) {
		this.pressure_msl = pressure_msl;
	}
	/**
	 * @return the surface_pressure
	 */
	public String getSurface_pressure() {
		return surface_pressure;
	}
	/**
	 * @param surface_pressure the surface_pressure to set
	 */
	public void setSurface_pressure(String surface_pressure) {
		this.surface_pressure = surface_pressure;
	}
	/**
	 * @return the wind_speed_10m
	 */
	public String getWind_speed_10m() {
		return wind_speed_10m;
	}
	/**
	 * @param wind_speed_10m the wind_speed_10m to set
	 */
	public void setWind_speed_10m(String wind_speed_10m) {
		this.wind_speed_10m = wind_speed_10m;
	}
	/**
	 * @return the wind_direction_10m
	 */
	public String getWind_direction_10m() {
		return wind_direction_10m;
	}
	/**
	 * @param wind_direction_10m the wind_direction_10m to set
	 */
	public void setWind_direction_10m(String wind_direction_10m) {
		this.wind_direction_10m = wind_direction_10m;
	}
	/**
	 * @return the wind_gusts_10m
	 */
	public String getWind_gusts_10m() {
		return wind_gusts_10m;
	}
	/**
	 * @param wind_gusts_10m the wind_gusts_10m to set
	 */
	public void setWind_gusts_10m(String wind_gusts_10m) {
		this.wind_gusts_10m = wind_gusts_10m;
	}
	@Override
	public int hashCode() {
		return Objects.hash(apparent_temperature, cloud_cover, interval, is_day, precipitation, pressure_msl, rain,
				relative_humidity_2m, showers, snowfall, surface_pressure, temperature_2m, time, weather_code,
				wind_direction_10m, wind_gusts_10m, wind_speed_10m);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrentUnits other = (CurrentUnits) obj;
		return Objects.equals(apparent_temperature, other.apparent_temperature)
				&& Objects.equals(cloud_cover, other.cloud_cover) && Objects.equals(interval, other.interval)
				&& Objects.equals(is_day, other.is_day) && Objects.equals(precipitation, other.precipitation)
				&& Objects.equals(pressure_msl, other.pressure_msl) && Objects.equals(rain, other.rain)
				&& Objects.equals(relative_humidity_2m, other.relative_humidity_2m)
				&& Objects.equals(showers, other.showers) && Objects.equals(snowfall, other.snowfall)
				&& Objects.equals(surface_pressure, other.surface_pressure)
				&& Objects.equals(temperature_2m, other.temperature_2m) && Objects.equals(time, other.time)
				&& Objects.equals(weather_code, other.weather_code)
				&& Objects.equals(wind_direction_10m, other.wind_direction_10m)
				&& Objects.equals(wind_gusts_10m, other.wind_gusts_10m)
				&& Objects.equals(wind_speed_10m, other.wind_speed_10m);
	}
	@Override
	public String toString() {
		return "CurrentUnits [time=" + time + ", interval=" + interval + ", temperature_2m=" + temperature_2m
				+ ", relative_humidity_2m=" + relative_humidity_2m + ", apparent_temperature=" + apparent_temperature
				+ ", is_day=" + is_day + ", precipitation=" + precipitation + ", rain=" + rain + ", showers=" + showers
				+ ", snowfall=" + snowfall + ", weather_code=" + weather_code + ", cloud_cover=" + cloud_cover
				+ ", pressure_msl=" + pressure_msl + ", surface_pressure=" + surface_pressure + ", wind_speed_10m="
				+ wind_speed_10m + ", wind_direction_10m=" + wind_direction_10m + ", wind_gusts_10m=" + wind_gusts_10m
				+ "]";
	}
	
	
	
	
	
	
}
