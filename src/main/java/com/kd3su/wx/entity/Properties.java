package com.kd3su.wx.entity;

import java.util.List;
import java.util.Objects;

// forecast properties

public class Properties {

	private static final long serialVersionUID = 1L;
	//    "properties": {
	//        "units": "us",
	//        "forecastGenerator": "BaselineForecastGenerator",
	//        "generatedAt": "2024-07-02T14:05:19+00:00",
	//        "updateTime": "2024-07-02T11:29:48+00:00",
	//        "validTimes": "2024-07-02T05:00:00+00:00/P7DT20H",
	//        "elevation": {
	//            "unitCode": "wmoUnit:m",
	//            "value": 141.1224
	//        },


	private List <Periods> periods;
	
	private String units; //": "us",
	private String forecastGenerator; //": "BaselineForecastGenerator",
	private String generatedAt; //": "2024-07-02T14:05:19+00:00",
	private String updateTime; //": "2024-07-02T11:29:48+00:00";
	private String validTimes; //": "2024-07-02T05:00:00+00:00/P7DT20H";
	private Elevation elevation;

	/**
	 * @return the elevation
	 */
	public Elevation getElevation() {
		return elevation;
	}
	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(Elevation elevation) {
		this.elevation = elevation;
	}
	/**
	 * @return the periods
	 */
	public List<Periods> getPeriods() {
		return periods;
	}
	/**
	 * @param periods the periods to set
	 */
	public void setPeriods(List<Periods> periods) {
		this.periods = periods;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}
	/**
	 * @param units the units to set
	 */
	public void setUnits(String units) {
		this.units = units;
	}
	/**
	 * @return the forecastGenerator
	 */
	public String getForecastGenerator() {
		return forecastGenerator;
	}
	/**
	 * @param forecastGenerator the forecastGenerator to set
	 */
	public void setForecastGenerator(String forecastGenerator) {
		this.forecastGenerator = forecastGenerator;
	}
	/**
	 * @return the generatedAt
	 */
	public String getGeneratedAt() {
		return generatedAt;
	}
	/**
	 * @param generatedAt the generatedAt to set
	 */
	public void setGeneratedAt(String generatedAt) {
		this.generatedAt = generatedAt;
	}
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the validTimes
	 */
	public String getValidTimes() {
		return validTimes;
	}
	/**
	 * @param validTimes the validTimes to set
	 */
	public void setValidTimes(String validTimes) {
		this.validTimes = validTimes;
	}
	@Override
	public int hashCode() {
		return Objects.hash(elevation, forecastGenerator, generatedAt, periods, units, updateTime, validTimes);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Properties other = (Properties) obj;
		return Objects.equals(elevation, other.elevation) && Objects.equals(forecastGenerator, other.forecastGenerator)
				&& Objects.equals(generatedAt, other.generatedAt) && Objects.equals(periods, other.periods)
				&& Objects.equals(units, other.units) && Objects.equals(updateTime, other.updateTime)
				&& Objects.equals(validTimes, other.validTimes);
	}
	@Override
	public String toString() {
		return "Properties [periods=" + periods + ", units=" + units + ", forecastGenerator=" + forecastGenerator
				+ ", generatedAt=" + generatedAt + ", updateTime=" + updateTime + ", validTimes=" + validTimes
				+ ", elevation=" + elevation + "]";
	}
	
	
	
	
	


}
