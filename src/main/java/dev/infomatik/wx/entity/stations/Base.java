package dev.infomatik.wx.entity.stations;

import java.util.Objects;

public class Base {
	
	String unitCode;// 	"wmoUnit:m"
	private String value; //	610
	private String amount;
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
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, unitCode, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Base other = (Base) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(unitCode, other.unitCode)
				&& Objects.equals(value, other.value);
	}
	@Override
	public String toString() {
		return "Base [unitCode=" + unitCode + ", value=" + value + ", amount=" + amount + "]";
	}
	
	
	

}
