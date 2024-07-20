package com.kd3su.wx.entity;

import java.util.Objects;

public class ProbabilityOfPrecipitation {
	private static final long serialVersionUID = 1L;

	String  unitCode; //": "wmoUnit:percent",
	String value; //": null
	
	
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
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
		ProbabilityOfPrecipitation other = (ProbabilityOfPrecipitation) obj;
		return Objects.equals(unitCode, other.unitCode) && Objects.equals(value, other.value);
	}
	@Override
	public String toString() {
		return "ProbabilityOfPrecipitation [unitCode=" + unitCode + ", value=" + value + "]";
	}





}
