package dev.infomatik.wx.entity.points;

import java.util.Objects;

public class Units {

	
	private String unitCode; //": "wmoUnit:m",
	private double value; //": 30750.598272064999
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
	@Override
	public int hashCode() {
		return Objects.hash(unitCode, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Units other = (Units) obj;
		return Objects.equals(unitCode, other.unitCode) && Objects.equals(value, other.value);
	}
	@Override
	public String toString() {
		return "Units [unitCode=" + unitCode + ", value=" + value + "]";
	}
	
	
	
}
