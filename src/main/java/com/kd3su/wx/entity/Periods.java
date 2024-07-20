package com.kd3su.wx.entity;

import java.util.Objects;

public class Periods {
	private static final long serialVersionUID = 1L;

	//    "number": 1,
	//    "name": "Today",
	//    "startTime": "2024-07-02T10:00:00-04:00",
	//    "endTime": "2024-07-02T18:00:00-04:00",
	//    "isDaytime": true,
	//    "temperature": 86,
	//    "temperatureUnit": "F",
	//    "temperatureTrend": "",
	//    "probabilityOfPrecipitation": {
	//        "unitCode": "wmoUnit:percent",
	//        "value": null
	//    },
	//    "windSpeed": "6 mph",
	//    "windDirection": "SE",
	//    "icon": "/icons/land/day/few?size=medium",
	//    "shortForecast": "Sunny",
	//    "detailedForecast": "Sunny, with a high near 86. Southeast wind around 6 mph."



	private int number;
	private String name;//": "Today",
	private String  startTime;//": "2024-07-02T10:00:00-04:00",
	private String endTime; //"2024-07-02T18:00:00-04:00",
	private boolean isDaytime;
	private int temperature; //": 86,
	private String temperatureUnit; //": "F",
	private String temperatureTrend; //  
	private ProbabilityOfPrecipitation probabilityOfPrecipitation;
	private String windSpeed; //: "6 mph",
	private String windDirection; //: "SE",
	private String icon;// ": "/icons/land/day/few?size=medium", // https://forecast.weather.gov/newimages/medium
	private String shortForecast;//": "Sunny",
	private String detailedForecast; //": "Sunny, with a high near 86. Southeast wind around 6 mph.
	
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public boolean isDaytime() {
		return isDaytime;
	}
	public void setDaytime(boolean isDaytime) {
		this.isDaytime = isDaytime;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public String getTemperatureUnit() {
		return temperatureUnit;
	}
	public void setTemperatureUnit(String temperatureUnit) {
		this.temperatureUnit = temperatureUnit;
	}
	public String getTemperatureTrend() {
		return temperatureTrend;
	}
	public void setTemperatureTrend(String temperatureTrend) {
		this.temperatureTrend = temperatureTrend;
	}
	public ProbabilityOfPrecipitation getProbabilityOfPrecipitation() {
		return probabilityOfPrecipitation;
	}
	public void setProbabilityOfPrecipitation(ProbabilityOfPrecipitation probabilityOfPrecipitation) {
		this.probabilityOfPrecipitation = probabilityOfPrecipitation;
	}
	public String getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getShortForecast() {
		return shortForecast;
	}
	public void setShortForecast(String shortForecast) {
		this.shortForecast = shortForecast;
	}
	public String getDetailedForecast() {
		return detailedForecast;
	}
	public void setDetailedForecast(String detailedForecast) {
		this.detailedForecast = detailedForecast;
	}
	@Override
	public int hashCode() {
		return Objects.hash(detailedForecast, endTime, icon, isDaytime, name, number, probabilityOfPrecipitation,
				shortForecast, startTime, temperature, temperatureTrend, temperatureUnit, windDirection, windSpeed);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Periods other = (Periods) obj;
		return Objects.equals(detailedForecast, other.detailedForecast) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(icon, other.icon) && isDaytime == other.isDaytime && Objects.equals(name, other.name)
				&& number == other.number && Objects.equals(probabilityOfPrecipitation, other.probabilityOfPrecipitation)
				&& Objects.equals(shortForecast, other.shortForecast) && Objects.equals(startTime, other.startTime)
				&& temperature == other.temperature && Objects.equals(temperatureTrend, other.temperatureTrend)
				&& Objects.equals(temperatureUnit, other.temperatureUnit)
				&& Objects.equals(windDirection, other.windDirection) && Objects.equals(windSpeed, other.windSpeed);
	}
	@Override
	public String toString() {
		return "Period [number=" + number + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", isDaytime=" + isDaytime + ", temperature=" + temperature + ", temperatureUnit=" + temperatureUnit
				+ ", temperatureTrend=" + temperatureTrend + ", probabilityOfPrecipitation=" + probabilityOfPrecipitation
				+ ", windSpeed=" + windSpeed + ", windDirection=" + windDirection + ", icon=" + icon + ", shortForecast="
				+ shortForecast + ", detailedForecast=" + detailedForecast + "]";
	}


}
