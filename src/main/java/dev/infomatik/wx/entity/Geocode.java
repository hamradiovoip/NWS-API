package dev.infomatik.wx.entity;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geocode {
    /*"geocode": {
        "SAME": [
            "024031"
        ],
        "UGC": [
            "MDC031"
        ]
    },
	*/
	
	@JsonProperty("SAME")
	List <String> same;
	@JsonProperty("UGC")
	List <String> ugc;
	
	
	/**
	 * @return the same
	 */
	public List<String> getSame() {
		return same;
	}
	/**
	 * @param same the same to set
	 */
	public void setSame(List<String> same) {
		this.same = same;
	}
	/**
	 * @return the ugc
	 */
	public List<String> getUgc() {
		return ugc;
	}
	/**
	 * @param ugc the ugc to set
	 */
	public void setUgc(List<String> ugc) {
		this.ugc = ugc;
	}
	@Override
	public int hashCode() {
		return Objects.hash(same, ugc);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geocode other = (Geocode) obj;
		return Objects.equals(same, other.same) && Objects.equals(ugc, other.ugc);
	}
	@Override
	public String toString() {
		return "Geocode [same=" + same + ", ugc=" + ugc + "]";
	}	
	
	
}
