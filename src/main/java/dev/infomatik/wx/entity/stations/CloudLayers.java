package dev.infomatik.wx.entity.stations;

import java.util.Objects;

public class CloudLayers {
	
	private Base base;
	private  String amount;
	/**
	 * @return the base
	 */
	public Base getBase() {
		return base;
	}
	/**
	 * @param base the base to set
	 */
	public void setBase(Base base) {
		this.base = base;
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
		return Objects.hash(amount, base);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CloudLayers other = (CloudLayers) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(base, other.base);
	}
	@Override
	public String toString() {
		return "CloudLayers [base=" + base + ", amount=" + amount + "]";
	}
	
	
	
	
}
