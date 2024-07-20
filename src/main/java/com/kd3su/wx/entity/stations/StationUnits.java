package com.kd3su.wx.entity.stations;

import java.util.Objects;

public class StationUnits {
	
	
	private String unitCode;//: "wmoUnit:degC",
	private double value;///: 31,
	private String qualityControl;//": "V"
	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}
	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * @return the qualityControl
	 */
	public String getQualityControl() {
		return qualityControl;
	}
	/**
	 * @param qualityControl the qualityControl to set
	 */
	public void setQualityControl(String qualityControl) {
		this.qualityControl = qualityControl;
	}
	@Override
	public int hashCode() {
		return Objects.hash(qualityControl, unitCode, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StationUnits other = (StationUnits) obj;
		return Objects.equals(qualityControl, other.qualityControl) && Objects.equals(unitCode, other.unitCode)
				&& Objects.equals(value, other.value);
	}
	@Override
	public String toString() {
		return "StationUnits [unitCode=" + unitCode + ", value=" + value + ", qualityControl=" + qualityControl + "]";
	}
	
	
	
	
}
