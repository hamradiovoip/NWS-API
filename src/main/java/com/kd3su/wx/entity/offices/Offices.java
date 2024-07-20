package com.kd3su.wx.entity.offices;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Offices {

	//	
	//	"@context": {
	//        "@version": "1.1",
	//        "@vocab": "https://schema.org/"
	//    },
	
	
	
	@JsonProperty("@type")
	String type; //": "GovernmentOrganization",
	@JsonProperty("@id")
	String id1;  // : "https://api.weather.gov/offices/MTR",

	String id;//": "MTR",
	String name;//": "San Francisco Bay Area, CA",
	Address address;//": {


	String telephone;//": " (831) 656-1725",
	String faxNumber;//": "",
	String email;//": "w-mtr.webmaster@noaa.gov",
	String sameAs;//": "https://www.weather.gov/mtr",
	String nwsRegion;//": "wr",
	String parentOrganization;//": "https://api.weather.gov/offices/WRH",
	List <String> responsibleCounties; //": [   ],
	List <String> responsibleForecastZones; //": [    ],
	List <String> responsibleFireZones;
	List <String> approvedObservationStations;
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
	 * @return the id1
	 */
	public String getId1() {
		return id1;
	}
	/**
	 * @param id1 the id1 to set
	 */
	public void setId1(String id1) {
		this.id1 = id1;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}
	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the sameAs
	 */
	public String getSameAs() {
		return sameAs;
	}
	/**
	 * @param sameAs the sameAs to set
	 */
	public void setSameAs(String sameAs) {
		this.sameAs = sameAs;
	}
	/**
	 * @return the nwsRegion
	 */
	public String getNwsRegion() {
		return nwsRegion;
	}
	/**
	 * @param nwsRegion the nwsRegion to set
	 */
	public void setNwsRegion(String nwsRegion) {
		this.nwsRegion = nwsRegion;
	}
	/**
	 * @return the parentOrganization
	 */
	public String getParentOrganization() {
		return parentOrganization;
	}
	/**
	 * @param parentOrganization the parentOrganization to set
	 */
	public void setParentOrganization(String parentOrganization) {
		this.parentOrganization = parentOrganization;
	}
	/**
	 * @return the responsibleCounties
	 */
	public List<String> getResponsibleCounties() {
		return responsibleCounties;
	}
	/**
	 * @param responsibleCounties the responsibleCounties to set
	 */
	public void setResponsibleCounties(List<String> responsibleCounties) {
		this.responsibleCounties = responsibleCounties;
	}
	/**
	 * @return the responsibleForecastZones
	 */
	public List<String> getResponsibleForecastZones() {
		return responsibleForecastZones;
	}
	/**
	 * @param responsibleForecastZones the responsibleForecastZones to set
	 */
	public void setResponsibleForecastZones(List<String> responsibleForecastZones) {
		this.responsibleForecastZones = responsibleForecastZones;
	}
	/**
	 * @return the responsibleFireZones
	 */
	public List<String> getResponsibleFireZones() {
		return responsibleFireZones;
	}
	/**
	 * @param responsibleFireZones the responsibleFireZones to set
	 */
	public void setResponsibleFireZones(List<String> responsibleFireZones) {
		this.responsibleFireZones = responsibleFireZones;
	}
	/**
	 * @return the approvedObservationStations
	 */
	public List<String> getApprovedObservationStations() {
		return approvedObservationStations;
	}
	/**
	 * @param approvedObservationStations the approvedObservationStations to set
	 */
	public void setApprovedObservationStations(List<String> approvedObservationStations) {
		this.approvedObservationStations = approvedObservationStations;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, approvedObservationStations, email, faxNumber, id, id1, name, nwsRegion,
				parentOrganization, responsibleCounties, responsibleFireZones, responsibleForecastZones, sameAs,
				telephone, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offices other = (Offices) obj;
		return Objects.equals(address, other.address)
				&& Objects.equals(approvedObservationStations, other.approvedObservationStations)
				&& Objects.equals(email, other.email) && Objects.equals(faxNumber, other.faxNumber)
				&& Objects.equals(id, other.id) && Objects.equals(id1, other.id1) && Objects.equals(name, other.name)
				&& Objects.equals(nwsRegion, other.nwsRegion)
				&& Objects.equals(parentOrganization, other.parentOrganization)
				&& Objects.equals(responsibleCounties, other.responsibleCounties)
				&& Objects.equals(responsibleFireZones, other.responsibleFireZones)
				&& Objects.equals(responsibleForecastZones, other.responsibleForecastZones)
				&& Objects.equals(sameAs, other.sameAs) && Objects.equals(telephone, other.telephone)
				&& Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "Offices [type=" + type + ", id1=" + id1 + ", id=" + id + ", name=" + name + ", address=" + address
				+ ", telephone=" + telephone + ", faxNumber=" + faxNumber + ", email=" + email + ", sameAs=" + sameAs
				+ ", nwsRegion=" + nwsRegion + ", parentOrganization=" + parentOrganization + ", responsibleCounties="
				+ responsibleCounties + ", responsibleForecastZones=" + responsibleForecastZones
				+ ", responsibleFireZones=" + responsibleFireZones + ", approvedObservationStations="
				+ approvedObservationStations + "]";
	}

}
