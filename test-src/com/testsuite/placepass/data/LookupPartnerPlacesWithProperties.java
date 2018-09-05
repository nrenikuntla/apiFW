package com.testsuite.placepass.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Data;
import com.base.BaseSetup;
import com.testng.Assert;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;


public class LookupPartnerPlacesWithProperties extends BaseSetup {

//	Declaration of respective API Parts instances
	Data data;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		data = new Data();
		currentTestName = method.getName();		
	}
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_LookupPartnerPlaceWithProperties")
	public void tc_API_001_LookupPartnerPlacePropertiesUsingGetMethod(String endPoint, String partnerId, String count, String query, String region) throws ParseException{
		
		data.requestLookupPartnerPlaceWithProperties(currentTestName, endPoint, partnerId, count, query);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlacePropertiesCountQueryRegion(count,query,region);
	}
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_LookupPartnerPlaceWithProperties")
	public void tc_API_002_LookupPartnerPlacePropertiesUsingGetMethod(String endPoint, String partnerId, String count, String query,String PropertyName,String city,String region) throws ParseException{
	  
		HashMap<String,String> response = data.requestLookupPartnerPlaceWithProperties(currentTestName, endPoint, partnerId, count, query);
	  
	// 	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
	  
		String[] hotelGeoData = data.verifyPartnerPlacePropertiesCountQueryProperty(query,PropertyName,city,region);
			
		if(hotelGeoData[0]!=null){
			endPoint = "/data-api/partners/places/config";
			
		//	Lookup Partner Place Config	
			response = data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, hotelGeoData[0], "1", null, null, environment);
			
		//	Verify response code and message
			data.verifyStatusCode("200");
			data.verifyStatusMessage("OK");
			
			String responseString = response.get("response body");
			JSONParser parser = new JSONParser();
		 
			JSONObject Object=(JSONObject) parser.parse(responseString);
		  
			JSONObject location = (JSONObject) Object.get("Location");
			JSONObject geoloc = (JSONObject) location.get("_geoloc");
			
			Object locationLat = geoloc.get("lat");
			Object locationLong = geoloc.get("lng");
			
			Double distance = data.distance(Double.parseDouble(hotelGeoData[1]), Double.parseDouble(hotelGeoData[2]), Double.parseDouble(locationLat.toString()), Double.parseDouble(locationLong.toString()));
			
			Assert.assertTrue(distance<=90,query+" ["+PropertyName+"] and its nearby location with webid ["+hotelGeoData[0]+"] are actually ["+distance+" km] far away");
			System.out.println(query+" ["+PropertyName+"] and its nearby location with webid ["+hotelGeoData[0]+"] are only ["+distance+" km] away");
			System.out.println("---------------------- MARSHA "+query+"-------------------------");
		}		
	 }
	
}
