package com.kd3su.wx.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	
	public Utils() {}
	
	

	
	//time convert  2024-07-14T12:47:00+00:00
	public String localTime(String date) throws ParseException {
		SimpleDateFormat datetimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssX");
		Date formatDate = datetimeFormatter.parse(date);
		
		String local = formatDate.toLocaleString();
		//System.out.println("date :" + formatDate);
		//System.out.println("date local:" + local);
		
		//Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());
		return local;
	}
	
	
	
}
