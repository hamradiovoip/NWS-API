package dev.infomatik.wx.entity.offices;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * Weather Field Offices (WFO)
 */
public class WFOIdentifiers {

	/** 3 char ID */
	List <String> wfoID;

	/** WFO Names, human readable */
	List <String> wfoName;

	// TODO: HashMap map;
	
	
	/**
	 * 
	 */

	public WFOIdentifiers() {
		
		// read file and save data
		wfoID = new ArrayList<String>();
		wfoName = new ArrayList<String>();
		
		
		try {
			readAndSetData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	
	public void readAndSetData() throws FileNotFoundException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is  = classloader.getResourceAsStream("/data/offices.txt");
				
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		// read and parse from file
		//BufferedReader br = new BufferedReader(new FileReader("file.txt"));
		String currentLine;
		try {
			while ((currentLine = br.readLine()) != null) {
			  String[] strArgs = currentLine.split(";"); 
			  System.out.println("strArgs[0] =" + strArgs[0]);	
			  this.wfoID.add(strArgs[0]);
			  this.wfoName.add(strArgs[1]);			  
			  
			  //Use HashMap to enter key Value pair.??
			 
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * @return the wfoID
	 */
	public List<String> getWfoID() {
		return wfoID;
	}

	/**
	 * @param wfoID the wfoID to set
	 */
	public void setWfoID(List<String> wfoID) {
		this.wfoID = wfoID;
	}

	/**
	 * @return the wfoName
	 */
	public List<String> getWfoName() {
		return wfoName;
	}

	/**
	 * @param wfoName the wfoName to set
	 */
	public void setWfoName(List<String> wfoName) {
		this.wfoName = wfoName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(wfoID, wfoName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WFOIdentifiers other = (WFOIdentifiers) obj;
		return Objects.equals(wfoID, other.wfoID) && Objects.equals(wfoName, other.wfoName);
	}

	@Override
	public String toString() {
		return "WFOIdentifiers [wfoID=" + wfoID + ", wfoName=" + wfoName + "]";
	}

}
