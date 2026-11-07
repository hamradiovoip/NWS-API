package dev.infomatik.wx.entity.alerts;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.infomatik.wx.entity.Geocode;


/*
 * Entity class for alert data 
 */
public class AlertProperties {
	// https://api.weather.gov/alerts/active?area=MD  
	// https://api.weather.gov/alerts/active?area={state}

	
	// @JsonProperty("Node_ID")
	/*
	properties	
	@id	"https://api.weather.gov/…e65508f2b6ace93d78.001.1"
	@type	"wx:Alert"
	id	"urn:oid:2.49.0.1.840.0.4…65508f2b6ace93d78.001.1"
	areaDesc	"Dorchester; Wicomico; Somerset"
	geocode	{…}
	affectedZones	[…]
	references	[]
	sent	"2024-07-10T04:50:00-04:00"
	effective	"2024-07-10T04:50:00-04:00"
	onset	"2024-07-10T11:00:00-04:00"
	expires	"2024-07-10T16:15:00-04:00"
	ends	"2024-07-10T20:00:00-04:00"
	status	"Actual"
	messageType	"Alert"
	category	"Met"
	severity	"Moderate"
	certainty	"Likely"
	urgency	"Expected"
	event	"Heat Advisory"
	sender	"w-nws.webmaster@noaa.gov"
	senderName	"NWS Wakefield VA"
	headline	"Heat Advisory issued July 10 at 4:50AM EDT until July 10 at 8:00PM EDT by NWS Wakefield VA"
	description	"* WHAT...Heat index values of 105 to 109 degrees are expected.\n\n* WHERE...Dorchester, Somerset, and Wicomico Counties.\n\n* WHEN...From 11 AM this morning to 8 PM EDT this evening.\n\n* IMPACTS...Hot temperatures and high humidity may cause heat\nillnesses."
	instruction	"Drink plenty of fluids, …elatives and neighbors."
	response	"Execute"
	parameters	{…}
	title	"Current watches, warnings, and advisories for Maryland"
	updated	"2024-07-10T14:40:13+00:00"
*/
	@JsonIgnore
	@JsonProperty("@id")
	String id1; //	"https://api.weather.gov/…e65508f2b6ace93d78.001.1"
	@JsonIgnore
	@JsonProperty("@type")
	String type; //	"wx:Alert"
	@JsonProperty("id")
	String id; //	"urn:oid:2.49.0.1.840.0.4…65508f2b6ace93d78.001.1"
	String areaDesc; //	"Dorchester; Wicomico; Somerset"
	@JsonIgnore
	@JsonProperty("geocode")
	Geocode geocode; //	{…}
	@JsonIgnore
	List <String> affectedZones; //	[…]
	@JsonIgnore
	List <String>  references; //	[]
	String sent; //	"2024-07-10T04:50:00-04:00"
	String effective; //	"2024-07-10T04:50:00-04:00"
	String onset; //	"2024-07-10T11:00:00-04:00"
	String expires; //	"2024-07-10T16:15:00-04:00"
	String ends; //	"2024-07-10T20:00:00-04:00"
	String status; //	"Actual"
	String messageType; //	"Alert"
	String category; //	"Met"
	String severity; //	"Moderate"
	String certainty; //	"Likely"
	String urgency; //	"Expected"
	String event; //	"Heat Advisory"
	String sender; //	"w-nws.webmaster@noaa.gov"
	String senderName; //	"NWS Wakefield VA"
	String headline; //	"Heat Advisory issued July 10 at 4:50AM EDT until July 10 at 8:00PM EDT by NWS Wakefield VA"
	String description; //	"* WHAT...Heat index values of 105 to 109 degrees are expected.\n\n* WHERE...Dorchester, Somerset, and Wicomico Counties.\n\n* WHEN...From 11 AM this morning to 8 PM EDT this evening.\n\n* IMPACTS...Hot temperatures and high humidity may cause heat\nillnesses."
	String instruction; //	"Drink plenty of fluids, …elatives and neighbors."
	String response; //	"Execute"
	@JsonIgnore
	List <String> parameters; //	{…}
	String title; //	"Current watches, warnings, and advisories for Maryland"
	String updated; //	"2024-07-10T14:40:13+00:00"
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
	 * @return the areaDesc
	 */
	public String getAreaDesc() {
		return areaDesc;
	}
	/**
	 * @param areaDesc the areaDesc to set
	 */
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	/**
	 * @return the geocode
	 */
	public Geocode getGeocode() {
		return geocode;
	}
	/**
	 * @param geocode the geocode to set
	 */
	public void setGeocode(Geocode geocode) {
		this.geocode = geocode;
	}
	/**
	 * @return the affectedZones
	 */
	public List<String> getAffectedZones() {
		return affectedZones;
	}
	/**
	 * @param affectedZones the affectedZones to set
	 */
	public void setAffectedZones(List<String> affectedZones) {
		this.affectedZones = affectedZones;
	}
	/**
	 * @return the references
	 */
	public List<String> getReferences() {
		return references;
	}
	/**
	 * @param references the references to set
	 */
	public void setReferences(List<String> references) {
		this.references = references;
	}
	/**
	 * @return the sent
	 */
	public String getSent() {
		return sent;
	}
	/**
	 * @param sent the sent to set
	 */
	public void setSent(String sent) {
		this.sent = sent;
	}
	/**
	 * @return the effective
	 */
	public String getEffective() {
		return effective;
	}
	/**
	 * @param effective the effective to set
	 */
	public void setEffective(String effective) {
		this.effective = effective;
	}
	/**
	 * @return the onset
	 */
	public String getOnset() {
		return onset;
	}
	/**
	 * @param onset the onset to set
	 */
	public void setOnset(String onset) {
		this.onset = onset;
	}
	/**
	 * @return the expires
	 */
	public String getExpires() {
		return expires;
	}
	/**
	 * @param expires the expires to set
	 */
	public void setExpires(String expires) {
		this.expires = expires;
	}
	/**
	 * @return the ends
	 */
	public String getEnds() {
		return ends;
	}
	/**
	 * @param ends the ends to set
	 */
	public void setEnds(String ends) {
		this.ends = ends;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	/**
	 * @return the certainty
	 */
	public String getCertainty() {
		return certainty;
	}
	/**
	 * @param certainty the certainty to set
	 */
	public void setCertainty(String certainty) {
		this.certainty = certainty;
	}
	/**
	 * @return the urgency
	 */
	public String getUrgency() {
		return urgency;
	}
	/**
	 * @param urgency the urgency to set
	 */
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/**
	 * @return the headline
	 */
	public String getHeadline() {
		return headline;
	}
	/**
	 * @param headline the headline to set
	 */
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the instruction
	 */
	public String getInstruction() {
		return instruction;
	}
	/**
	 * @param instruction the instruction to set
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	/**
	 * @return the parameters
	 */
	public List<String> getParameters() {
		return parameters;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}
	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	@Override
	public int hashCode() {
		return Objects.hash(affectedZones, areaDesc, category, certainty, description, effective, ends, event, expires,
				geocode, headline, id, id1, instruction, messageType, onset, parameters, references, response, sender,
				senderName, sent, severity, status, title, type, updated, urgency);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlertProperties other = (AlertProperties) obj;
		return Objects.equals(affectedZones, other.affectedZones) && Objects.equals(areaDesc, other.areaDesc)
				&& Objects.equals(category, other.category) && Objects.equals(certainty, other.certainty)
				&& Objects.equals(description, other.description) && Objects.equals(effective, other.effective)
				&& Objects.equals(ends, other.ends) && Objects.equals(event, other.event)
				&& Objects.equals(expires, other.expires) && Objects.equals(geocode, other.geocode)
				&& Objects.equals(headline, other.headline) && Objects.equals(id, other.id)
				&& Objects.equals(id1, other.id1) && Objects.equals(instruction, other.instruction)
				&& Objects.equals(messageType, other.messageType) && Objects.equals(onset, other.onset)
				&& Objects.equals(parameters, other.parameters) && Objects.equals(references, other.references)
				&& Objects.equals(response, other.response) && Objects.equals(sender, other.sender)
				&& Objects.equals(senderName, other.senderName) && Objects.equals(sent, other.sent)
				&& Objects.equals(severity, other.severity) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title) && Objects.equals(type, other.type)
				&& Objects.equals(updated, other.updated) && Objects.equals(urgency, other.urgency);
	}
	@Override
	public String toString() {
		return "AlertProperties [id1=" + id1 + ", type=" + type + ", id=" + id + ", areaDesc=" + areaDesc + ", geocode="
				+ geocode + ", affectedZones=" + affectedZones + ", references=" + references + ", sent=" + sent
				+ ", effective=" + effective + ", onset=" + onset + ", expires=" + expires + ", ends=" + ends
				+ ", status=" + status + ", messageType=" + messageType + ", category=" + category + ", severity="
				+ severity + ", certainty=" + certainty + ", urgency=" + urgency + ", event=" + event + ", sender="
				+ sender + ", senderName=" + senderName + ", headline=" + headline + ", description=" + description
				+ ", instruction=" + instruction + ", response=" + response + ", parameters=" + parameters + ", title="
				+ title + ", updated=" + updated + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
