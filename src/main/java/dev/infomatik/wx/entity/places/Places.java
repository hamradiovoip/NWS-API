package dev.infomatik.wx.entity.places;

import java.util.List;

import in.abilng.ndjson.NdJsonObjectMapper;


// TODO might not need

public class Places {

	private String name;
	private String display_name;
	private String osm_type;
	private String osm_id;
	private String type;
	private List<String> location;
	private List<String>  bbox;
	private String population;
	
	
	
}


//{
// "name":"Zittlestown",
// "display_name":"Zittlestown, Washington County, Maryland, United States",
// "address":   {
//              "hamlet":"Zittlestown",
//              "county":"Washington County",
//              "state":"Maryland",
//              "ISO3166-2-lvl4":"US-MD",
//              "country":"United States",
//              "country_code":"us"
//            },
// "osm_type":"node",
// "osm_id":158654062,
// "type":"hamlet",
// "location":[-77.6238788,39.4853781],
// "bbox":[-77.6438788,39.4653781,-77.6038788,39.5053781]
//
//}