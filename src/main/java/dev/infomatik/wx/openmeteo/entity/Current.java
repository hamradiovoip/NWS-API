package dev.infomatik.wx.openmeteo.entity;

import java.util.Objects;

public class Current {
	
	
	/*
	time	"2024-07-28T13:30"
	interval	900
	temperature_2m	85.7
	relative_humidity_2m	36
	apparent_temperature	90.6
	is_day	1
	precipitation	0
	rain	0
	showers	0
	snowfall	0
	weather_code	0
	cloud_cover	0
	pressure_msl	1018.3
	surface_pressure	977.7
	wind_speed_10m	3.4
	wind_direction_10m	113
	wind_gusts_10m	7.4
	*/
	
	
	private String time;   // 	"2024-07-28T13:30"
	private String interval;   // 		900
	private double temperature_2m;   // 		85.7
	private double relative_humidity_2m;   // 		36
	private double apparent_temperature;   // 		90.6
	private int is_day;   // 		1
	private String precipitation;   // 		0
	private String rain;   // 		0
	private String showers;   // 		0
	private String snowfall;   // 		0
	private String weather_code;   // 		0
	private String cloud_cover;   // 		0
	private double pressure_msl;   // 		1018.3
	private double surface_pressure;   // 		977.7
	private double wind_speed_10m;   // 		3.4
	private double wind_direction_10m;   // 		113
	private double wind_gusts_10m;   // 		7.4
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
	public double getTemperature_2m() {
		return temperature_2m;
	}
	/**
	 * @param temperature_2m the temperature_2m to set
	 */
	public void setTemperature_2m(double temperature_2m) {
		this.temperature_2m = temperature_2m;
	}
	/**
	 * @return the relative_humidity_2m
	 */
	public double getRelative_humidity_2m() {
		return relative_humidity_2m;
	}
	/**
	 * @param relative_humidity_2m the relative_humidity_2m to set
	 */
	public void setRelative_humidity_2m(double relative_humidity_2m) {
		this.relative_humidity_2m = relative_humidity_2m;
	}
	/**
	 * @return the apparent_temperature
	 */
	public double getApparent_temperature() {
		return apparent_temperature;
	}
	/**
	 * @param apparent_temperature the apparent_temperature to set
	 */
	public void setApparent_temperature(double apparent_temperature) {
		this.apparent_temperature = apparent_temperature;
	}
	/**
	 * @return the is_day
	 */
	public int getIs_day() {
		return is_day;
	}
	/**
	 * @param is_day the is_day to set
	 */
	public void setIs_day(int is_day) {
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
	public double getPressure_msl() {
		return pressure_msl;
	}
	/**
	 * @param pressure_msl the pressure_msl to set
	 */
	public void setPressure_msl(double pressure_msl) {
		this.pressure_msl = pressure_msl;
	}
	/**
	 * @return the surface_pressure
	 */
	public double getSurface_pressure() {
		return surface_pressure;
	}
	/**
	 * @param surface_pressure the surface_pressure to set
	 */
	public void setSurface_pressure(double surface_pressure) {
		this.surface_pressure = surface_pressure;
	}
	/**
	 * @return the wind_speed_10m
	 */
	public double getWind_speed_10m() {
		return wind_speed_10m;
	}
	/**
	 * @param wind_speed_10m the wind_speed_10m to set
	 */
	public void setWind_speed_10m(double wind_speed_10m) {
		this.wind_speed_10m = wind_speed_10m;
	}
	/**
	 * @return the wind_direction_10m
	 */
	public double getWind_direction_10m() {
		return wind_direction_10m;
	}
	/**
	 * @param wind_direction_10m the wind_direction_10m to set
	 */
	public void setWind_direction_10m(double wind_direction_10m) {
		this.wind_direction_10m = wind_direction_10m;
	}
	/**
	 * @return the wind_gusts_10m
	 */
	public double getWind_gusts_10m() {
		return wind_gusts_10m;
	}
	/**
	 * @param wind_gusts_10m the wind_gusts_10m to set
	 */
	public void setWind_gusts_10m(double wind_gusts_10m) {
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
		Current other = (Current) obj;
		return Double.doubleToLongBits(apparent_temperature) == Double.doubleToLongBits(other.apparent_temperature)
				&& Objects.equals(cloud_cover, other.cloud_cover) && Objects.equals(interval, other.interval)
				&& is_day == other.is_day && Objects.equals(precipitation, other.precipitation)
				&& Double.doubleToLongBits(pressure_msl) == Double.doubleToLongBits(other.pressure_msl)
				&& Objects.equals(rain, other.rain)
				&& Double.doubleToLongBits(relative_humidity_2m) == Double.doubleToLongBits(other.relative_humidity_2m)
				&& Objects.equals(showers, other.showers) && Objects.equals(snowfall, other.snowfall)
				&& Double.doubleToLongBits(surface_pressure) == Double.doubleToLongBits(other.surface_pressure)
				&& Double.doubleToLongBits(temperature_2m) == Double.doubleToLongBits(other.temperature_2m)
				&& Objects.equals(time, other.time) && Objects.equals(weather_code, other.weather_code)
				&& Double.doubleToLongBits(wind_direction_10m) == Double.doubleToLongBits(other.wind_direction_10m)
				&& Double.doubleToLongBits(wind_gusts_10m) == Double.doubleToLongBits(other.wind_gusts_10m)
				&& Double.doubleToLongBits(wind_speed_10m) == Double.doubleToLongBits(other.wind_speed_10m);
	}
	@Override
	public String toString() {
		return "Current [time=" + time + ", interval=" + interval + ", temperature_2m=" + temperature_2m
				+ ", relative_humidity_2m=" + relative_humidity_2m + ", apparent_temperature=" + apparent_temperature
				+ ", is_day=" + is_day + ", precipitation=" + precipitation + ", rain=" + rain + ", showers=" + showers
				+ ", snowfall=" + snowfall + ", weather_code=" + weather_code + ", cloud_cover=" + cloud_cover
				+ ", pressure_msl=" + pressure_msl + ", surface_pressure=" + surface_pressure + ", wind_speed_10m="
				+ wind_speed_10m + ", wind_direction_10m=" + wind_direction_10m + ", wind_gusts_10m=" + wind_gusts_10m
				+ "]";
	}

	
	
	
	
	
}
