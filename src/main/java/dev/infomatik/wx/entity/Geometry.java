package dev.infomatik.wx.entity;

import java.util.Arrays;
import java.util.Objects;

public class Geometry {
	private static final long serialVersionUID = 1L;
//	"geometry": {
//  "type": "Polygon",
//  "coordinates": [
//      [
//          [
//              -77.687396000000007,
//              39.479677500000001
//          ],
//          [
//              -77.691038900000009,
//              39.457773100000004
//          ],
//          [
//              -77.662662400000016,
//              39.454958200000007
//          ],
//          [
//              -77.659013700000017,
//              39.476862300000008
//          ],
//          [
//              -77.687396000000007,
//              39.479677500000001
//          ]
//      ]
//  ]
//},

	String type;
	String[] coordinates;
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
	 * @return the coordinates
	 */
	public String[] getCoordinates() {
		return coordinates;
	}
	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(String[] coordinates) {
		this.coordinates = coordinates;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coordinates);
		result = prime * result + Objects.hash(type);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geometry other = (Geometry) obj;
		return Arrays.equals(coordinates, other.coordinates) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "Geometry [type=" + type + ", coordinates=" + Arrays.toString(coordinates) + "]";
	} 
	

	
	
}
