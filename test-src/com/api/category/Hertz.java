package com.api.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.utilities.APIHelpers;

public class Hertz extends APIHelpers{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	public ConfigManager api = new ConfigManager("Api");
	JSONManager jsonObject = new JSONManager();
	List<String> headers = new ArrayList<String>();
	HashMap<String, String> response;
	
	
	public void verifyStatusCode(String statusCode){
		verifyStatusCode(response.get("status code"), statusCode);
	}
	
	public void verifyStatusMessage(String statusMessage){
		verifyStatusMessage(response.get("status message"), statusMessage);
	}
	

	public HashMap<String, String> requestHertzGetVehicleLocations(String current_TestName, String endPoint, String partnerId, String query, String marshaCode){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String parameters = null;
		String env = BaseSetup.environment;

		String requestURL; String subscriptionKey;
		
		if(query!=null)
			parameters = "?query="+query;
		else if(marshaCode!=null)
			parameters = "?marshaCode="+marshaCode;
			
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint + parameters;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint + parameters;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		
		
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
		System.out.println("In Get(Hertz Get Vehicle Locations) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Get(Hertz Get Vehicle Locations) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Get(Hertz Get Vehicle Locations) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	
	public void verifyVehicleLocations(String query){
		
		List<ArrayList<String>> airport = jsonObject.getListOfValuesFromSimpleJsonKey1(response.get("response body"), "Airport");
		List<ArrayList<String>> city = jsonObject.getListOfValuesFromSimpleJsonKey1(response.get("response body"), "City");
		List<ArrayList<String>> hotel = jsonObject.getListOfValuesFromSimpleJsonKey1(response.get("response body"), "Hotel");
		
		if(airport.get(0)!=null){
			Assert.assertTrue(airport.get(0).size()>0,"Not a single airport location has been returned");
			System.out.println("["+airport.get(0).size()+"] Airport location(s) have been returned for query ["+query+"]");
		}else{
			System.out.println("No airport locations have been returned for query ["+query+"]");
		}
		if(city.get(0)!=null){
			Assert.assertTrue(city.get(0).size()>0,"Not a single city location has been returned");
			System.out.println("["+city.get(0).size()+"] City location(s) have been returned for query ["+query+"]");
		}else{
			System.out.println("No city locations have been returned for query ["+query+"]");
		}
		if(hotel.get(0)!=null){
			System.out.println("["+hotel.get(0).size()+"] Hotel location(s) have been returned for query ["+query+"]");
		}else{
			System.out.println("No hotel locations have been returned for query ["+query+"]");
		}
	}
	
	
	public HashMap<String, String> requestHertzGetVehicleAvailRate(String current_TestName, String endPoint, String partnerId, String requestJSON, String... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];

		String requestURL; String subscriptionKey;
				
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
				
		System.out.println("requestURL is -- "+requestURL);
		
		requestJSON = System.getProperty("user.dir") + requestJSON;
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, false, "", requestJSON);
		System.out.println("In Post(Hertz Get Vehicle Avail Rate) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Hertz Get Vehicle Avail Rate) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Hertz Get Vehicle Avail Rate) API response, the response body is -- \n"+response.get("response body"));
		
		return response;		
	}
	
	
	@SuppressWarnings("unchecked")
	public void verifyVehicleAvailabilities(String expectedVehicleCode){
		
		List<ArrayList<String>> availabilities = jsonObject.getListOfValuesFromSimpleJsonKey1(response.get("response body"), "Availabilities");
		Assert.assertTrue(availabilities.get(0).size()>0,"Not a single vehicle has been returned");
		System.out.println("["+availabilities.get(0).size()+"] vehicle(s) have been returned");
		
		List<JSONObject> vehicleCode = (List<JSONObject>)(List<?>) jsonObject.getValueAsObjectFromArrayJsonKey(response.get("response body"), "Availabilities", "VehicleInfo");
		for(int i=0; i<vehicleCode.size();i++){
			Object obtainedvehicleCode = (Object) vehicleCode.get(i).get("VehicleCode");
			Assert.assertEquals(obtainedvehicleCode,expectedVehicleCode,"Vehicle code returned ["+obtainedvehicleCode+"] doesn't match with expected ["+expectedVehicleCode+"]");
			System.out.println("("+(i+1)+") Vehicle code returned ["+obtainedvehicleCode+"] matches with expected");
		}		
	}	
	

	public void verifyVehicleEarnType(String expectedEarnType) throws ParseException
	{
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object=(JSONObject) parser.parse(responseString);
		JSONArray availabilities= (JSONArray) object.get("Availabilities");
		Iterator<JSONObject> i = availabilities.iterator();
				
		while (i.hasNext()){			
			
			JSONObject availability = i.next();
			String earntype=(String) availability.get("EarnType");
			Assert.assertEquals(earntype, expectedEarnType,"Obtained earnType "+earntype+" doesn't match with expected "+expectedEarnType);
			System.out.println("Obtained earnType "+earntype+" matches with expected");
		}
	}			
	
	
	public HashMap<String, String> requestHertzGetVehicleRes(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String...environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];

		String requestURL; String subscriptionKey;
				
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
				
		System.out.println("requestURL is -- "+requestURL);
		
		requestJSON = System.getProperty("user.dir") + requestJSON;
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, true, requestBody, requestJSON);
		System.out.println("In Post(Hertz Get Vehicle Res) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Hertz Get Vehicle Res) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Hertz Get Vehicle Res) API response, the response body is -- \n"+response.get("response body"));
		
		return response;		
	}
	
	
	public Object verifyVehicleReservation (String firstName, String email) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		
		Object status = (Object)object.get("Status");
		Assert.assertEquals("Confirmed",status,"Booking status returned ["+status+"] instead of 'Confirmed'");
		System.out.println("Booking status Confirmed");
		
		JSONObject confirmation = (JSONObject)object.get("Confirmation");
		JSONObject uniqueId = (JSONObject)confirmation.get("UniqueId");
		Object ID = (Object)uniqueId.get("ID");
		
		JSONObject customer = (JSONObject)object.get("Customer");
		Object obtainedfirstName = (Object)customer.get("FirstName");
		Object obtainedemail = (Object)customer.get("Email");
		
		Assert.assertNotNull(ID,"Booking ID is null");
		System.out.println("Booking ID generated ["+ID+"]");
		
		Assert.assertEquals(obtainedfirstName.toString().toLowerCase(),firstName.toLowerCase(),"First Name ["+obtainedfirstName+"] used for booking doesn't match with expected ["+firstName+"]");
		System.out.println("First Name ["+obtainedfirstName+"] used for booking matches with expected");
		
		Assert.assertEquals(obtainedemail.toString().toLowerCase(),email.toLowerCase(),"Email ["+obtainedemail+"] used for booking doesn't match with expected ["+email+"]");
		System.out.println("Email ["+obtainedemail+"] used for booking matches with expected");
		
		return ID;
	}
	
	
	public HashMap<String, String> requestHertzGetVehicleRetRes(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];

		String requestURL; String subscriptionKey;
				
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
				
		System.out.println("requestURL is -- "+requestURL);
		
		requestJSON = System.getProperty("user.dir") + requestJSON;
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, true, requestBody, requestJSON);
		System.out.println("In Post(Hertz Get Vehicle Ret Res) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Hertz Get Vehicle Ret Res) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Hertz Get Vehicle Ret Res) API response, the response body is -- \n"+response.get("response body"));
		
		return response;		
	}
	
	
	public void verifyVehicleReservationRetrieval(Object ID, String firstName, String email) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		
		Object status = (Object)object.get("Status");
		Assert.assertEquals("Confirmed",status,"Booking status returned ["+status+"] instead of 'Confirmed'");
		System.out.println("Booking status Confirmed");
		
		JSONObject confirmation = (JSONObject)object.get("Confirmation");
		JSONObject uniqueId = (JSONObject)confirmation.get("UniqueId");
		Object obtainedID = (Object)uniqueId.get("ID");
		
		JSONObject customer = (JSONObject)object.get("Customer");
		Object obtainedfirstName = (Object)customer.get("FirstName");
		Object obtainedemail = (Object)customer.get("Email");
		
		Assert.assertEquals(obtainedID,ID,"Booking ID generated after reservation ["+ID+"] does not match with ID displayed during retrieval ["+obtainedID+"]");
		System.out.println("Booking ID generated after reservation ["+ID+"] matches with ID displayed during retrieval");
		
		Assert.assertEquals(obtainedfirstName.toString().toLowerCase(),firstName.toLowerCase(),"First Name ["+obtainedfirstName+"] used for booking doesn't match with First Name displayed during retrieval ["+firstName+"]");
		System.out.println("First Name ["+obtainedfirstName+"] used for booking matches with First Name displayed during retrieval");
		
		Assert.assertEquals(obtainedemail.toString().toLowerCase(),email.toLowerCase(),"Email ["+obtainedemail+"] used for booking doesn't match with Email displayed during retrieval ["+email+"]");
		System.out.println("Email ["+obtainedemail+"] used for booking matches with Email displayed during retrieval");
		
	}
	
	
	public HashMap<String, String> requestHertzGetVehicleCancel(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];

		String requestURL; String subscriptionKey;
				
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
				
		System.out.println("requestURL is -- "+requestURL);
		
		requestJSON = System.getProperty("user.dir") + requestJSON;
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, true, requestBody, requestJSON);
		System.out.println("In Post(Hertz Get Vehicle Cancel) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Hertz Get Vehicle Cancel) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Hertz Get Vehicle Cancel) API response, the response body is -- \n"+response.get("response body"));
		
		return response;		
	}
	
	
	public void verifyVehicleCancellation(Object ID, String firstName, String email) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		
		Object status = (Object)object.get("Status");
		Assert.assertEquals("Cancelled",status,"Booking status returned ["+status+"] instead of 'Cancelled'");
		System.out.println("Booking status Cancelled");
		
		JSONObject confirmation = (JSONObject)object.get("Confirmation");
		JSONObject uniqueId = (JSONObject)confirmation.get("UniqueId");
		Object obtainedID = (Object)uniqueId.get("ID");
		
		JSONObject customer = (JSONObject)object.get("Customer");
		Object obtainedfirstName = (Object)customer.get("FirstName");
		Object obtainedemail = (Object)customer.get("Email");
		
		Assert.assertEquals(obtainedID,ID,"Booking ID generated after reservation ["+ID+"] does not match with ID displayed after cancellation ["+obtainedID+"]");
		System.out.println("Booking ID generated after reservation ["+ID+"] matches with ID displayed after cancellation");
		
		Assert.assertEquals(obtainedfirstName.toString().toLowerCase(),firstName.toLowerCase(),"First Name ["+obtainedfirstName+"] used for booking doesn't match with First Name displayed after cancellation ["+firstName+"]");
		System.out.println("First Name ["+obtainedfirstName+"] used for booking matches with First Name displayed after cancellation");
		
		Assert.assertEquals(obtainedemail.toString().toLowerCase(),email.toLowerCase(),"Email ["+obtainedemail+"] used for booking doesn't match with Email displayed after cancellation ["+email+"]");
		System.out.println("Email ["+obtainedemail+"] used for booking matches with Email displayed after cancellation");
		
	}
	
	
	public HashMap<String, String> requestHertzGetVehicleLocationDetails(String current_TestName, String endPoint, String partnerId, String requestJSON){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;

		String requestURL; String subscriptionKey;
				
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
				
		System.out.println("requestURL is -- "+requestURL);
		
		requestJSON = System.getProperty("user.dir") + requestJSON;
		
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, false, "", requestJSON);
		System.out.println("In Post(Hertz Get Vehicle Location Details) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Hertz Get Vehicle Location Details) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Hertz Get Vehicle Location Details) API response, the response body is -- \n"+response.get("response body"));
		
		return response;		
	}
	
	
	@SuppressWarnings("unchecked")
	public void verifyVehicleLocationDetails(String expectedLocationCode) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		
		JSONObject locationInfo = (JSONObject)object.get("LocationInfo");
		Object locationCode = (Object)locationInfo.get("Code");
				
		Assert.assertEquals(locationCode, expectedLocationCode, "Returned location code ["+locationCode+"] doesn't match with expected ["+expectedLocationCode+"]");
		System.out.println("Returned location code ["+locationCode+"] matches with expected");
		
		JSONArray vehicleCodes = (JSONArray)locationInfo.get("LocationVehicles");
		Assert.assertTrue(vehicleCodes.size()>0,"Not a single vehicle type been returned");
		System.out.println("["+vehicleCodes.size()+"] vehicle type(s) have been returned");
		
		for(int i=0; i<vehicleCodes.size();i++){
			JSONObject obtainedvehicleCode = (JSONObject) vehicleCodes.get(i);
			System.out.println((i+1)+". "+obtainedvehicleCode.get("Code"));			
		}
		
	}
	
	
	public HashMap<String, String> requestHertzGetVehicleAvailRateOptions(String current_TestName, String endPoint, String partnerId, String requestJSON){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;

		String requestURL; String subscriptionKey;
				
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
				
		System.out.println("requestURL is -- "+requestURL);
		
		requestJSON = System.getProperty("user.dir") + requestJSON;
		
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, false, "", requestJSON);
		System.out.println("In Post(Hertz Get Vehicle Avail Rate Options) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Hertz Get Vehicle Avail Rate Options) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Hertz Get Vehicle Avail Rate Options) API response, the response body is -- \n"+response.get("response body"));
		
		return response;		
	}
	
	public void verifyVehicleOptions(String expectedVehicleCode) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		
		Object vehicleCode = (Object)object.get("VehicleCode");
						
		Assert.assertEquals(vehicleCode, expectedVehicleCode, "Returned vehicle code ["+vehicleCode+"] doesn't match with expected ["+expectedVehicleCode+"]");
		System.out.println("Returned vehicle code ["+vehicleCode+"] matches with expected");
		
		JSONArray vehicleOptions = (JSONArray)object.get("EquipmentPrices");
		System.out.println("["+vehicleOptions.size()+"] vehicle option(s) have been returned");
		for(int i=0; i<vehicleOptions.size();i++){
			JSONObject equipmentDetail = (JSONObject)vehicleOptions.get(i);
			JSONObject option = (JSONObject)equipmentDetail.get("EquipmentDetail");
			System.out.println((i+1)+". "+option.get("EquipType"));
		}	
	}

}
