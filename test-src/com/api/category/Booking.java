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

public class Booking extends APIHelpers{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	public ConfigManager api = new ConfigManager("Api");
	ConfigManager sys = new ConfigManager();
	JSONManager jsonObject = new JSONManager();
	List<String> headers = new ArrayList<String>();
	HashMap<String, String> response;
	
	
	public void verifyStatusCode(String statusCode){
		verifyStatusCode(response.get("status code"), statusCode);
	}
	
	public void verifyStatusMessage(String statusMessage){
		verifyStatusMessage(response.get("status message"), statusMessage);
	}
	
	
	public HashMap<String, String> requestCreateShoppingCart(String current_TestName, String endPoint, String partnerId, String requestJSON, String... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env=null;
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
		System.out.println("In Post(Create Shopping Cart) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Create Shopping Cart) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Create Shopping Cart) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	
public HashMap<String, String> requestCreateCartDynamic(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env=null;
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
		System.out.println("In Post(Create Shopping Cart) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Create Shopping Cart) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Create Shopping Cart) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	public HashMap<String, String> requestValidateShoppingCart(String current_TestName, String endPoint, String partnerId, String requestJSON, String... environment){
		
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
		System.out.println("In Post(Validate Shopping Cart) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Validate Shopping Cart) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(Validate Shopping Cart) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	public void verifyValidateCart(int... questionsCount){
		
		ArrayList<String> expectedMessages = new ArrayList<String>();
		
		expectedMessages.add("Booker details is mandatory");
		if(questionsCount.length==0)
			expectedMessages.add("This product has questions.Please provide the answers");
		expectedMessages.add("Need one minimum Traverler");
		
		String responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		
		for(int i = 0; i < actualMessages.get(0).size(); i++){
			System.out.println("--------- " +actualMessages.get(0).get(i));
		}
		
		for(int i = 0; i < actualMessages.size(); i++){
			for(int j = 0; j < expectedMessages.size(); j++)
				Assert.assertTrue(actualMessages.get(i).contains(expectedMessages.get(j)), "Actual Validation Messages list does not contain "+expectedMessages.get(j)+", hence failed");
		}
		
	}
	
	
	/**
	 * 1. Check pp-code
	 * 2. Check pp-message 
	 */
	public void verifyPPCodePPMessage(String code, String message){
		
		String ppcode = response.get("pp-code");		
		Assert.assertEquals(code, ppcode,"Expected code '"+code+"' and obtained code '"+ppcode+"' doesn't match");
		System.out.println("Obtained code '"+code+"' is as expected");
		
		String ppmessage = response.get("pp-message");
		Assert.assertEquals(message, ppmessage,"Expected message '"+message+"' and obtained message '"+ppmessage+"' doesn't match");
		System.out.println("Obtained message '"+message+"' is as expected");
	}
	
	
	public HashMap<String, String> requestGetBookingQuestions(String current_TestName, String endPoint, String partnerId, String... environment){
		
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
		
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Booking Questions) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Booking Questions) API response, Status message is -- " + response.get("status message"));
		System.out.println("In GET(Get Booking Questions) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
	}
	
	public void verifyNoOfReturnedBookingQuestions(JSONArray JsonArray){
		
		Assert.assertTrue(JsonArray.size() > 0, "Response does not consist of booking questions, hence failed.");
		
		
	}
	
	public void verifyBookingAnswer(String bookingAnswer){
		
		Assert.assertTrue(bookingAnswer == null, "Value of Booking answer in the response is not null, hence failed.");
	}
	
	
	public HashMap<String, String> requestUpdateBookingAnswers(String current_TestName, String endPoint, String partnerId, String requestJSON, String... environment){
		
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
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, false, "", requestJSON);
		System.out.println("In Put(Update Booking Answers) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Put(Update Booking Answers) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Put(Update Booking Answers) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	
	public HashMap<String, String> requestUpdateBookingAnswersDynamic(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String... environment){
		
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
		response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, true, requestBody, requestJSON);
		System.out.println("In Put(Update Booking Answers) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Put(Update Booking Answers) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Put(Update Booking Answers) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}

	
	public HashMap<String, String> requestUpdateTravelerDetails(String current_TestName, String endPoint, String partnerId, String requestJSON, String... environment){
		
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
		response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, false, "", requestJSON);
		System.out.println("In Put(Update Traveler Details) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Put(Update Traveler Details) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Put(Update Traveler Details) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	
public HashMap<String, String> requestUpdateTravelerDetailsDynamic(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String... environment){
		
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
		response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, true, requestBody, requestJSON);
		System.out.println("In Put(Update Traveler Details) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Put(Update Traveler Details) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Put(Update Traveler Details) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	
	public HashMap<String, String> requestUpdateBookerDetails(String current_TestName, String endPoint, String partnerId, String requestJSON, String... environment){
		
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
		response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, false, "", requestJSON);
		System.out.println("In Put(Update Booker Details) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Put(Update Booker Details) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Put(Update Booker Details) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	
	public HashMap<String, String> requestUpdateBookerDetailsDynamic(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String... environment){
		
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
		response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, true, requestBody, requestJSON);
		System.out.println("In Put(Update Booker Details) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Put(Update Booker Details) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Put(Update Booker Details) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}

	
	public HashMap<String, String> requestProcessPaymentAndCreateBooking(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String... environment){
		
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
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, true, requestBody, requestJSON);
		System.out.println("In Post(ProcessPayment and CreateBooking) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(ProcessPayment and CreateBooking) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Post(ProcessPayment and CreateBooking) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
	}
	
	public HashMap<String, String> requestGetSingleBooking(String current_TestName, String endPoint, String partnerId, String... environment){
		
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
		
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Single Booking) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Single Booking) API response, Status message is -- " + response.get("status message"));
		System.out.println("In GET(Get Single Booking) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
	}
	
	
	public HashMap<String, String> requestGetSingleBookingForCustomer(String current_TestName, String endPoint, String partnerId, String... environment){
		
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
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Single Booking For Customer) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Single Booking For Customer) API response, Status message is -- " + response.get("status message"));
		System.out.println("In GET(Get Single Booking For Customer) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
	}
	
	
	public HashMap<String, String> requestViewShoppingCart(String current_TestName, String endPoint, String partnerId, String... environment){
		
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
						
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
		System.out.println("In GET(View Shopping Cart) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(View Shopping Cart) API response, Status message is -- " + response.get("status message"));
		System.out.println("In GET(View Shopping Cart) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	@SuppressWarnings("unchecked")
	public void verifyViewCart(String expectedProductId, String expectedProductOptionId){
		
		List<String> productIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingOptions", "productId");
		List<String> productOptionIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingOptions", "productOptionId");
		List<String> quantities = (List<String>)(List<?>)jsonObject.getValueAsObjectFromArrayJsonKey(response.get("response body"), "bookingOptions", "quantities");
		
		System.out.println("*****************************************************");
		Assert.assertEquals(productIds.get(0), expectedProductId,"Obtained Product Id ["+productIds.get(0)+"] doesn't match with expected ["+expectedProductId+"] ");
		System.out.println("Obtained Product Id ["+productIds.get(0)+"] matches with expected");
		Assert.assertEquals(productOptionIds.get(0), expectedProductOptionId,"Obtained Product Id ["+productOptionIds.get(0)+"] doesn't match with expected ["+expectedProductOptionId+"] ");
		System.out.println("Obtained Product Option Id ["+productOptionIds.get(0)+"] matches with expected");
		Assert.assertNotNull(quantities.get(0),"Quantities key is null");
		System.out.println("Quantities is NOT null");
	}
	
	
	public HashMap<String, String> requestViewBookingsHistory(String current_TestName, String endPoint,
			String partnerId, String customerId, String hitsPerPage, String pageNumber){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
		String parameters = null;
		
		if(hitsPerPage != null && pageNumber != null)
			parameters = "?hitsperpage="+hitsPerPage+"&pagenumber="+pageNumber;
		else if(hitsPerPage != null && pageNumber == null)
			parameters = "?hitsperpage="+hitsPerPage;
		else if(hitsPerPage == null && pageNumber != null)
			parameters = "?pagenumber="+pageNumber;
		
		if(customerId == null)
			customerId = "";
		
		endPoint = endPoint+"/"+customerId+"/bookings";
		
		String requestURL; String subscriptionKey;
				
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		if(parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
		System.out.println("In GET(View Bookings History) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(View Bookings History) API response, Status message is -- " + response.get("status message"));
		System.out.println("In GET(View Bookings History) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	public void verifyBookingsHistory(String hitsPerPage, String customerid, String pageNumber) throws ParseException{
		
		List<String> bookingIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingsHistory", "bookingId");
		List<String> bookingReferences = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingsHistory", "bookingReference");
		List<String> customerIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingsHistory", "customerId");
		
		System.out.println("*****************************************************");
		
		for(int i=0; i<bookingIds.size();i++){
			Assert.assertNotNull(bookingIds.get(i),"Obtained Booking Id ["+bookingIds.get(i)+"] is null");
			Assert.assertNotNull(bookingReferences.get(i),"Obtained Booking Reference ["+bookingReferences.get(i)+"] is null");
			Assert.assertEquals(customerid, customerIds.get(i),"Obtained booking history of different customer ["+customerIds.get(i)+"] than supplied ["+customerid+"]");
			System.out.println("Obtained booking history of Customer Id ["+customerid+"] with Booking Id ["+bookingIds.get(i)+"] and Booking Ref ["+bookingReferences.get(i)+"]");
		}
		System.out.println("Booking history contains total of ["+bookingIds.size()+"] bookings");
		
		long ihitsPerPage=Integer.valueOf(hitsPerPage);
		  
		String expectedhits;
	  
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		long expectedHits = Long.valueOf(object.get("totalRecords").toString());
	  
		int ipageNumber=Integer.valueOf(pageNumber);
	 
		if(ihitsPerPage>Long.valueOf(expectedHits))   
		{	expectedhits = Long.toString(expectedHits);
		}   
		else
		{
			for(int j=0;j<ipageNumber;j++)
			{
				expectedHits = expectedHits-ihitsPerPage;
	      
				if(expectedHits<ihitsPerPage)
					break;
			}
			if(expectedHits>ihitsPerPage)
			{
				expectedHits=Long.valueOf(ihitsPerPage);
			}
		}
		expectedhits = Long.toString(expectedHits);
	   
		Assert.assertTrue(Long.valueOf(expectedhits)==bookingIds.size(),"Booking history contains ["+bookingIds.size()+"] bookings in page ["+pageNumber+"] while expected is "+expectedHits);
		System.out.println("Booking history contains expected ["+expectedHits+"] bookings in page ["+pageNumber+"]");   
	}
	
	
	public HashMap<String, String> requestFindPastBooking(String current_TestName, String endPoint, String partnerId, String bookerEmail, String bookingReference){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
		String parameters = null;
		
		if(bookerEmail != null && bookingReference != null)
			parameters = "?bookeremail="+bookerEmail+"&bookingreference="+bookingReference;
		else if(bookerEmail != null && bookingReference == null)
			parameters = "?bookeremail="+bookerEmail;
		else if(bookerEmail == null && bookingReference != null)
			parameters = "?bookingreference="+bookingReference;

		String requestURL; String subscriptionKey;
				
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint + parameters;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint + parameters;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}		
				
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
		System.out.println("In GET(Find Past Booking) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Find Past Booking) API response, Status message is -- " + response.get("status message"));
		System.out.println("In GET(Find Past Booking) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	public void verifyPastBooking(String bookerEmail, String bookingReference) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		JSONObject bookingObject = (JSONObject)object.get("booking");
		Object obtainedBookingReference = (Object)bookingObject.get("bookingReference");
		
		JSONObject bookerDetails = (JSONObject)bookingObject.get("bookerDetails");
		Object obtainedEmail = (Object)bookerDetails.get("email");
				
		Assert.assertEquals(bookerEmail, obtainedEmail, "Obtained Booker Email ["+obtainedEmail+"] doesn't match with expected ["+bookerEmail+"]");
		System.out.println("Booker Email ["+bookerEmail+"] matched with expected");
		
		Assert.assertEquals(bookingReference, obtainedBookingReference, "Obtained Booking Reference ["+obtainedBookingReference+"] doesn't match with expected ["+bookingReference+"]");
		System.out.println("Booking Reference ["+bookingReference+"] matched with expected");
	}
	
	
	public HashMap<String, String> requestUpdateLanguageOptionCode(String current_TestName, String endPoint, String partnerId, String requestJSON){
		
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
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, false, "", requestJSON);
		System.out.println("In Put(Update Language Option Code) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Put(Update Language Option Code) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Put(Update Language Option Code) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}

	public void verifyLanguageOption(String languageCode){
	
		List<String> language = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingOptions", "languageOptionCode");
		
		Assert.assertEquals(language.get(0), languageCode,"Obtained language code ["+language.get(0)+"] doesn't match with expected ["+languageCode+"]");
		System.out.println("Obtained language code ["+language.get(0)+"] matches with expected");
	}
	
	public Object verifyCreateBooking(String... expectedBookerEmail) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		Object bookingId = (Object)object.get("bookingId");
		Object bookingReference = (Object)object.get("bookingReference");
		if(expectedBookerEmail.length!=0){
			Object bookerEmail = (Object)object.get("bookerEmail");
			Assert.assertNotNull(bookerEmail,"Booker Email is null");
			Assert.assertEquals(bookerEmail.toString(), expectedBookerEmail[0],"Instead of "+expectedBookerEmail[0]+", "+bookerEmail+" is returned in booking confirmation");
			System.out.println("Booker email "+expectedBookerEmail[0]+" returned in booking confirmation as expected");
		}	
		Assert.assertNotNull(bookingId,"Booking Id is null");
		System.out.println("Booking Id is "+bookingId);
		Assert.assertNotNull(bookingReference,"Booking Reference is null");
		System.out.println("Booking Reference is "+bookingReference);
		return bookingId;
	}
	
	public void verifyCancellation() throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		
		JSONObject resultType = (JSONObject)object.get("resultType");
		Object message = resultType.get("message");
		Assert.assertEquals(message, "Success","Cancellation message expected 'Success' but got "+message);
		System.out.println("Cancellation succeeded");
	}
	
	
	public void verifyLoyaltyDetails(String loyaltyId) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		JSONObject booking = (JSONObject)object.get("booking");
		JSONObject loyaltyAccount = (JSONObject)booking.get("loyaltyAccount");
		Object loyaltyAccountId = loyaltyAccount.get("loyaltyAccountId");
		Assert.assertNotEquals(loyaltyId, loyaltyAccountId.toString(),"Loyalty details aren't hashed!");
		System.out.println("Loyalty details are hashed. LoyaltyId = "+loyaltyAccountId);
	}
	
	public void verifyHotelPickup() throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		JSONObject booking = (JSONObject)object.get("booking");
		JSONArray bookingOptions = (JSONArray)booking.get("bookingOptions");
		JSONObject option = (JSONObject) bookingOptions.get(0);
		Object isHotelPickupEligible = option.get("isHotelPickUpEligible");
		Assert.assertTrue((boolean)isHotelPickupEligible,"isHotelPickupEligible is not true!");
		System.out.println("isHotelPickupEligible is true");
	}
	
	
	public void verifyLanguageOptionCode(String language) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		JSONObject booking = (JSONObject)object.get("booking");
		JSONArray bookingOptions = (JSONArray)booking.get("bookingOptions");
		JSONObject option = (JSONObject) bookingOptions.get(0);
		Object languageOptionCode = option.get("languageOptionCode");
		Assert.assertEquals(languageOptionCode.toString(),language,"languageOptionCode returned "+languageOptionCode+" instead of "+language+"!");
		System.out.println("languageOptionCode returned "+languageOptionCode+" is as expected");
	}
	
	
	public void verifyDiscountApplied() throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		JSONObject booking = (JSONObject)object.get("booking");
		JSONObject total = (JSONObject)booking.get("total");
		Object discountAmount = total.get("discountAmount");
		Assert.assertTrue(Float.parseFloat(discountAmount.toString())>0,"Discount amount is not greater than zero");
		System.out.println("Applied discount is $"+discountAmount);
	}
	
	public void verifyPointsApplied(String description) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		JSONArray discounts = (JSONArray)object.get("discounts");
		
		Iterator<JSONObject> i = discounts.iterator();
		System.out.println("-------------");
		
		while (i.hasNext()){			
			
			JSONObject discount = i.next();
			
			Object descriptionObtained = discount.get("description");
			Object discountCode = discount.get("discountCode");
			Assert.assertTrue(descriptionObtained.toString().equals(description),"Obtained description ["+descriptionObtained+"] doesn't match with expected "+description+" for discount code "+discountCode);
			System.out.println("Obtained description ["+descriptionObtained+"] is as expected for discount code "+discountCode);
		}	
	}
	
	
	public void verifyGetSingleBooking(String customerId, String bookingId) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		JSONObject booking = (JSONObject)object.get("booking");
		Object obtainedBookingId = (Object)booking.get("bookingId");
		Object obtainedCustomerId = (Object)booking.get("customerId");
		Object bookingReference = (Object)booking.get("bookingReference");
		Object partnerId = (Object)booking.get("partnerId");
		Assert.assertEquals(bookingId,obtainedBookingId.toString(),"Obtained Booking Id ["+obtainedBookingId+"] does not match with expected "+bookingId);
		System.out.println("Obtained Booking Id ["+obtainedBookingId+"] is as expected");
		Assert.assertEquals(customerId,obtainedCustomerId.toString(),"Obtained Customer Id ["+obtainedCustomerId+"] does not match with expected "+customerId);
		System.out.println("Obtained Customer Id ["+obtainedCustomerId+"] is as expected");
		Assert.assertNotNull(bookingReference,"Booking Reference is null");
		System.out.println("Booking Reference is "+bookingReference);
		Assert.assertNotNull(partnerId,"Partner-id is null");
		System.out.println("Partner-id is "+partnerId);		
	}
	
	public HashMap<String, String> requestUpdateHotelPickupLocation(String current_TestName, String endPoint, String partnerId, String requestJSON){
		
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
		response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, false, "", requestJSON);
		System.out.println("In Put(Update Hotel Pickup Location) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Put(Update Hotel Pickup Location) API response, Status message is -- " + response.get("status message"));
		System.out.println("In Put(Update Hotel Pickup Location) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public void verifyHotelPickupLocation(String productOptionId, String id, String locationName) throws ParseException{
		
		List<String> productOptionIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingOptions", "productOptionId");
		List<JSONObject> pickupLocation = (List<JSONObject>)(List<?>)jsonObject.getValueAsObjectFromArrayJsonKey(response.get("response body"), "bookingOptions", "pickupLocation");
		
		Object obtainedId = (Object) pickupLocation.get(0).get("id");
		Object obtainedlocationName = (Object) pickupLocation.get(0).get("locationName");
		
		Assert.assertEquals(obtainedId, id, "Obtained hotel id ["+obtainedId+"] doesn't match with expected ["+id+"]");
		System.out.println("Obtained hotel id ["+obtainedId+"] matches with expected");
		Assert.assertEquals(obtainedlocationName, locationName, "Obtained location ["+obtainedlocationName+"] doesn't match with expected ["+obtainedlocationName+"]");
		System.out.println("Obtained location ["+obtainedlocationName+"] matches with expected");		
	}
	
	public HashMap<String, String> requestRemoveItemsFromCart(String current_TestName, String endPoint, String partnerId){
		
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
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "DELETE", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Remove Items From Cart) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Remove Items From Cart) API response, Status message is -- " + response.get("status message"));
		System.out.println("In GET(Get Remove Items From Cart) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
	}
	
	public void verifyBookingId(HashMap<String, String> response, String expBookingId) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		JSONObject booking = (JSONObject)object.get("booking");
		Object bookingId = (Object)booking.get("bookingId");
			
		System.out.println("Actual Booking Id is "+bookingId.toString());
		
		Assert.assertEquals(bookingId.toString(), expBookingId, "Actual Booking Id "+bookingId.toString()+" in response is not matching with expected bookingid "+expBookingId);
		
		
		
	}
	
	public HashMap<String, String> requestViewShoppingCart(String current_TestName, String endPoint, String partnerId){
		
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
						
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
		System.out.println("In GET(View Shopping Cart) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(View Shopping Cart) API response, Status message is -- " + response.get("status message"));
		System.out.println("In GET(View Shopping Cart) API response, the response body is -- \n"+response.get("response body"));
		
		return response;
		
	}
	
	public void verifyBookingOptionsIsNotNullInViewCart(){
		
		List<String> productIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingOptions", "productId");
		List<String> productOptionIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "bookingOptions", "productOptionId");
		List<String> quantities = (List<String>)(List<?>)jsonObject.getValueAsObjectFromArrayJsonKey(response.get("response body"), "bookingOptions", "quantities");
		
		System.out.println("*****************************************************");
		System.out.println("Obtained Product Id ["+productIds.get(0)+"] matches with expected");
		System.out.println("Obtained Product Option Id ["+productOptionIds.get(0)+"] matches with expected");
		Assert.assertTrue(productIds.size() > 0, "Response does not consist of product ids in bookingOptions Key");
		Assert.assertTrue(productOptionIds.size() > 0, "Response does not consist of productOptionIds in bookingOptions Key");
		Assert.assertTrue(quantities.size() > 0, "Response does not consist of productOptionIds in bookingOptions Key");
		
	}
	
	public void verifyBookingOptionsIsNull(HashMap<String, String> response) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		Object bookingOptionId = (Object)object.get("bookingOptions");
			
		System.out.println("Actual bookingOptionId is "+bookingOptionId);
		
		Assert.assertNull(bookingOptionId, "Booking Option Id in the response is "+bookingOptionId+", it is not null");
		
		
	}
	
	public HashMap<String, String> requestAddBookingOptionsToCart(String current_TestName, String endPoint, String partnerId, String requestJSON){
		  
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
		   if(partnerId!=null)
			   headers.add(partnerId);
		   headers.add(api.getProperty("Api.Header.contentType"));
		   headers.add(api.getProperty("Api.Header.accept"));
		   headers.add(subscriptionKey);
		   if(!(env.equals("prod")))
		    headers.add("env:"+env);
		  }
		  
		//  execute API request
		  response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, false, "", requestJSON);
		  System.out.println("In Put(AddBookingOptionsToCart) API response, Status code is -- " + response.get("status code"));
		  System.out.println("In Put(AddBookingOptionsToCart) API response, Status message is -- " + response.get("status message"));
		  System.out.println("In Put(AddBookingOptionsToCart) API response, the response body is -- \n"+response.get("response body"));
		  
		  return response;
		  
	}
	
	
	public HashMap<String, String> requestAddBookingOptionsDynamic(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON){
		  
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
		  
		//  execute API request
		  response = executeRequestMethod(null, null, requestURL, headers, "PUT", false, "", false, current_TestName, true, requestBody, requestJSON);
		  System.out.println("In Put(AddBookingOptionsToCart) API response, Status code is -- " + response.get("status code"));
		  System.out.println("In Put(AddBookingOptionsToCart) API response, Status message is -- " + response.get("status message"));
		  System.out.println("In Put(AddBookingOptionsToCart) API response, the response body is -- \n"+response.get("response body"));
		  
		  return response;
		  
	}
	
	public void verifyAddBookingOptionsToCart(String productId,String productOptionId,String bookingDate,String quantity) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		JSONArray bookingOptionsarray = (JSONArray) object.get("bookingOptions");
		Iterator<JSONObject> i = bookingOptionsarray.iterator();
		System.out.println("-------------");
		
		while (i.hasNext()){			
			
			JSONObject bookingOption = i.next();
			
	    	Object obtainedProductId = bookingOption.get("productId");
	    	Assert.assertEquals(obtainedProductId.toString(), productId,"Product Id in cart "+obtainedProductId+" doesn't match with expected "+productId);
	    	System.out.println("Product Id in cart "+obtainedProductId+" is as expected");
	    	
	    	Object obtainedProductOptionId = bookingOption.get("productOptionId");
	    	Assert.assertEquals(obtainedProductOptionId.toString(), productOptionId,"Product Option Id in cart "+obtainedProductOptionId+" doesn't match with expected "+productOptionId);
	    	System.out.println("Product Option Id in cart "+obtainedProductOptionId+" is as expected");
	    	
	    	Object obtainedBookingDate = bookingOption.get("bookingDate");
	    	Assert.assertEquals(obtainedBookingDate.toString(), bookingDate,"Booking date in cart "+obtainedBookingDate+" doesn't match with expected "+bookingDate);
	    	System.out.println("Booking date in cart "+obtainedBookingDate+" is as expected");
	    	
	    	JSONArray quantitiesarray = (JSONArray) bookingOption.get("quantities");
			Iterator<JSONObject> j = quantitiesarray.iterator();
						
			while (j.hasNext()){			
				JSONObject quantityObj = j.next();		    	
		    	Object obtainedQuantity = quantityObj.get("quantity");
		    	Assert.assertEquals(obtainedQuantity.toString(), quantity,"Quantity in cart "+obtainedQuantity+" doesn't match with expected "+quantity);
		    	System.out.println("Quantity in cart "+obtainedQuantity+" is as expected");		    	
			}
			System.out.println("-------------");
		}
	}
	
	
	public HashMap<String, String> requestGetVoucherDetails(String current_TestName, String endPoint, String partnerId){
		  
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
		  
		  if(headers != null){
		   headers.clear();
		   headers.add(partnerId);
		   headers.add(api.getProperty("Api.Header.contentType"));
		   headers.add(api.getProperty("Api.Header.accept"));
		   headers.add(subscriptionKey);
		   if(!(env.equals("prod")))
		    headers.add("env:"+env);
		  }
		  
		//  execute API request
		  response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
		  System.out.println("In Get(GetVoucherDetails) API response, Status code is -- " + response.get("status code"));
		  System.out.println("In Get(GetVoucherDetails) API response, Status message is -- " + response.get("status message"));
		  System.out.println("In Get(GetVoucherDetails) API response, the response body is -- \n"+response.get("response body"));
		  
		  return response;
		  
	}
	
	
	public HashMap<String, String> requestVoucherURL(String voucherUrl){
		
		String requestURL = null;
		List<String> headers = new ArrayList<String>();
		HashMap<String, String> responseMap;
				
		requestURL = voucherUrl;			
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			headers.add("Content-Type:text/html");
			headers.add("Accept:text/html");
		}	
			
	//	execute API request
		responseMap = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, "", false, "");
	
		System.out.println("In GET("+voucherUrl+") API response, Status code is -- " + responseMap.get("status code"));
				
		return responseMap;
	}
	
	
	public HashMap<String,String> requestCancelBooking(String current_TestName, String endPoint,String partnerId,String requestJSON)
	{
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
	
		String requestURL; String subscriptionKey;
		    
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL")+endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL")+endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}  
		   
		requestJSON = System.getProperty("user.dir") + requestJSON;
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
		  
		//  execute API request
		  response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, false,"",requestJSON);
		  System.out.println("In POST(RequestCancelBooking) API response, Status code is -- " + response.get("status code"));
		  System.out.println("In POST(RequestcancelBooking) API response, Status message is -- " + response.get("status message"));
		  System.out.println("In POST(RequestCancelBooking) API response, the response body is -- \n"+response.get("response body"));
		  
		  return response;		  
	}
	
	
	public HashMap<String,String> requestGroupBooking(String current_TestName,String endPoint,String partnerId,String requestJSON)
	{
		
		String userName=api.getProperty("Api.userName");
		String password=api.getProperty("Api.password");
		
		String env=BaseSetup.environment;
		
		String requestURL;String subscriptionKey;
		
		if(!(env.equals("prod")||(env.equals("preprod"))))
		{
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else
		{			
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");			
		}
		
		requestJSON = System.getProperty("user.dir") + requestJSON;
		
		System.out.println(" requestURL is------"+requestURL);
		
		if(headers!=null)
		{
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);			
		}
	//	execute API request
		
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, false,"",requestJSON);
		System.out.println("In POST(RequestGroupBooking) API response, Status code is -- " + response.get("status code"));
		System.out.println("In POST(RequestGroupBooking) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	public HashMap<String,String> requestForTokenizedNumber(String current_TestName,String cardNumber,String exp_month,String exp_year,String cvc,String address)
	{
		
		String stripeRequestJSON = sys.getProperty("StripeJSON");
		String stripeRequestBody = jsonObject.readJSONContentToString(stripeRequestJSON);
	
		String userName=api.getProperty("Api.userName");
		String password=api.getProperty("Api.password");
		
		String env=BaseSetup.environment;
		
		String requestURL="https://api.stripe.com/v1/tokens?";
			String parameters="card[number]="+cardNumber+"&card[exp_month]="+exp_month+"&card[exp_year]="+exp_year+"&card[cvc]="+cvc+"&card[address_zip]="+address;
			requestURL=requestURL+parameters;
		System.out.println(" requestURL is------"+requestURL);
		
		if(headers!=null)
		{
			headers.clear();
			
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(api.getProperty("Api.Header.authorization"));
			
		}
		
	//	execute API request
		
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "",false, current_TestName, true,stripeRequestBody,stripeRequestJSON);
		
		
		return response;
	}
	 
	
	/**
	 * 1. Verify voucher id & vendor reference are returned
	 * 2. Call GET(voucherUrl) using url from response JSON and verify status code is 200 OK
	 * 3. Verify following keys from above response
	 *   a. bookingDate
	 *   b. productId
	 *   c. productOptionId
	 *   d. firstName
	 *   e. quantity
	 *   f. voucherType
	 *   g. htmlContent
	 * @throws ParseException 
	 */
	public List<String> verifyVoucherDetails(String voucherId, String vendorReference, String htmlContent, String voucherType,
			String productId, String productOptionId, String bookingDate, String quantity, String firstName) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object obtainedVoucherId = object.get("id");
		Assert.assertNotNull(obtainedVoucherId,"Obtained Voucher Id is null");
		Assert.assertEquals(voucherId, obtainedVoucherId.toString(),"Obtained voucher id "+obtainedVoucherId+" and expected Voucher Id "+voucherId+" do not match");
		System.out.println("Obtained voucher id "+obtainedVoucherId+" is as expected");
		
		Object obtainedVendorReference = object.get("vendorReference");
		Assert.assertNotNull(obtainedVendorReference,"Obtained vendor reference is null");
		Assert.assertEquals(vendorReference, obtainedVendorReference.toString(),"Obtained vendor reference "+obtainedVendorReference+" and expected vendor reference "+vendorReference+" do not match");
		System.out.println("Obtained vendor reference "+obtainedVendorReference+" is as expected");
		
		Object obtainedHtmlContent = object.get("htmlContent");
		Assert.assertEquals(obtainedHtmlContent,(Object)htmlContent,"Obtained HTML Content "+obtainedHtmlContent+" doesn't match with expected "+htmlContent);
		System.out.println("Obtained HTML Content "+obtainedHtmlContent+" is as expected");
		
		Object obtainedVoucherType = object.get("voucherType");
		Assert.assertEquals(obtainedVoucherType.toString(),voucherType,"Obtained Voucher type "+obtainedVoucherType+" doesn't match with expected "+voucherType);
		System.out.println("Obtained Voucher type "+obtainedVoucherType+" is as expected");
		
		JSONArray bookingOptionsArray = (JSONArray) object.get("bookingOptions");
		Iterator<JSONObject> i = bookingOptionsArray.iterator();
		System.out.println("-------------");
		
		while (i.hasNext()){			
			
			JSONObject bookingOption = i.next();
			
	    	Object obtainedProductId = bookingOption.get("productId");
	    	Assert.assertEquals(obtainedProductId.toString(), productId,"Product Id in voucher "+obtainedProductId+" doesn't match with expected "+productId);
	    	System.out.println("Product Id in voucher "+obtainedProductId+" is as expected");
	    	
	    	Object obtainedProductOptionId = bookingOption.get("productOptionId");
	    	Assert.assertEquals(obtainedProductOptionId.toString(), productOptionId,"Product Option Id in voucher "+obtainedProductOptionId+" doesn't match with expected "+productOptionId);
	    	System.out.println("Product Option Id in voucher "+obtainedProductOptionId+" is as expected");
	    	
	    	Object obtainedBookingDate = bookingOption.get("bookingDate");
	    	Assert.assertEquals(obtainedBookingDate.toString(), bookingDate,"Booking date in voucher "+obtainedBookingDate+" doesn't match with expected "+bookingDate);
	    	System.out.println("Booking date in voucher "+obtainedBookingDate+" is as expected");
	    	
	    	JSONArray quantitiesarray = (JSONArray) bookingOption.get("quantities");
			Iterator<JSONObject> j = quantitiesarray.iterator();
						
			while (j.hasNext()){			
				JSONObject quantityObj = j.next();		    	
		    	Object obtainedQuantity = quantityObj.get("quantity");
		    	Assert.assertEquals(obtainedQuantity.toString(), quantity,"Quantity in voucher "+obtainedQuantity+" doesn't match with expected "+quantity);
		    	System.out.println("Quantity in voucher "+obtainedQuantity+" is as expected");		    	
			}
			
			JSONArray travelerDetailsArray = (JSONArray) bookingOption.get("traverlerDetails");
			Iterator<JSONObject> k = travelerDetailsArray.iterator();
						
			while (k.hasNext()){			
				JSONObject firstNameObj = k.next();		    	
		    	Object obtainedFirstName = firstNameObj.get("firstName");
		    	Assert.assertEquals(obtainedFirstName.toString(), firstName,"Quantity in voucher "+obtainedFirstName+" doesn't match with expected "+firstName);
		    	System.out.println("First name in voucher "+obtainedFirstName+" is as expected");		    	
			}
		}
		
		JSONArray urlJSONArray = (JSONArray) object.get("urls");
		List<String> urls = new ArrayList<String>();
		
		for(int l=0; l<urlJSONArray.size(); l++){
			Object url = urlJSONArray.get(l);
			urls.add(url.toString());
		}								
		return urls;
	}
	
}
