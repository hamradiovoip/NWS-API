package com.kd3su.wx.entity.offices;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

	@JsonProperty("@type")
	String type;//": "PostalAddress",
	String streetAddress;//": "21 Grace Hopper Ave, Stop 5",
	String addressLocality;//": "Monterey",
	String addressRegion;//": "CA",
	String postalCode; //": "93943-5505",
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}
	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	/**
	 * @return the addressLocality
	 */
	public String getAddressLocality() {
		return addressLocality;
	}
	/**
	 * @param addressLocality the addressLocality to set
	 */
	public void setAddressLocality(String addressLocality) {
		this.addressLocality = addressLocality;
	}
	/**
	 * @return the addressRegion
	 */
	public String getAddressRegion() {
		return addressRegion;
	}
	/**
	 * @param addressRegion the addressRegion to set
	 */
	public void setAddressRegion(String addressRegion) {
		this.addressRegion = addressRegion;
	}
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	@Override
	public int hashCode() {
		return Objects.hash(addressLocality, addressRegion, postalCode, streetAddress, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(addressLocality, other.addressLocality)
				&& Objects.equals(addressRegion, other.addressRegion) && Objects.equals(postalCode, other.postalCode)
				&& Objects.equals(streetAddress, other.streetAddress) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "Address [type=" + type + ", streetAddress=" + streetAddress + ", addressLocality=" + addressLocality
				+ ", addressRegion=" + addressRegion + ", postalCode=" + postalCode + "]";
	}


}
