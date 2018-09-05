package com.testsuite.placepass.booking;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class ValidateShoppingCart extends BaseSetup{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	public ConfigManager api;
	JSONManager jsonObject = new JSONManager();
	
	List<String> headers = new ArrayList<String>();
	public String current_TestName;
	
	Booking booking;
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		api = new ConfigManager("Api");
		current_TestName = method.getName();
		booking = new Booking();
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_ValidateShoppingCart")
	public void tc_API_001_ValidateShoppingCart(String endPoint, String partnerId, String requestJSON, String validateCartJSON) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
	//	Get Booking Questions		
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(current_TestName, endPoint, partnerId);
						
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray JsonArray = (JSONArray) object.get("bookingQuestions");
		int questions = JsonArray.size();
		System.out.println("---- No of JSONs in the array is " + questions);
		
		String validateCartEndPoint = "/bookings/carts/"+cartId+"/validate";
				
		booking.requestValidateShoppingCart(current_TestName, validateCartEndPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyValidateCart(questions);
		
	}
	
	
	/**
	 * Remove booking option 
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_ValidateShoppingCart")
	public void tc_API_002_ValidateShoppingCart(String endPoint, String partnerId, String createCartJSON, String validateCartJSON,
			String bookingOptionId, String status, String message, String validateMessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
		endPoint = "/bookings/carts/"+cartId+"/bookingoptions/"+bookingOptionId;
		
		response = booking.requestRemoveItemsFromCart(current_TestName, endPoint, partnerId);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyBookingOptionsIsNull(response);
		
		String validateCartEndPoint = "/bookings/carts/"+cartId+"/validate";
				
		response = booking.requestValidateShoppingCart(current_TestName, validateCartEndPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains(validateMessage),actualMessages.get(0).get(0)+" is displayed instead of '"+validateMessage+"'. Cart ["+cartId+"] validation failed");
		System.out.println("Cart ["+cartId+"] with booking option removed when validated returned ["+validateMessage+"]");
		
	}
	
	
	/**
	 * Add booking option 
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_ValidateShoppingCart")
	public void tc_API_003_ValidateShoppingCart(String endPoint, String partnerId, String createCartJSON,
			String addBookingOptionsJSON, String validateCartJSON, String status, String message) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
	//	Add Booking Options
		endPoint = "/bookings/carts/"+cartId+"/bookingoptions";	
			
		booking.requestAddBookingOptionsToCart(current_TestName, endPoint, partnerId, addBookingOptionsJSON);	
			
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Validate Cart	
		String validateCartEndPoint = "/bookings/carts/"+cartId+"/validate";
				
		response = booking.requestValidateShoppingCart(current_TestName, validateCartEndPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyValidateCart();		
	}
	
	
	/**
	 * Update traveler details 
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_ValidateShoppingCart")
	public void tc_API_004_ValidateShoppingCart(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String validateCartJSON, String status, String message, String validateMessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
	//	Update traveler details
		endPoint = "/bookings/carts/"+cartId+"/travelers";	
		response = booking.requestUpdateTravelerDetails(current_TestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Validate Cart	
		String validateCartEndPoint = "/bookings/carts/"+cartId+"/validate";
				
		response = booking.requestValidateShoppingCart(current_TestName, validateCartEndPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		for(int i=0;i<actualMessages.get(0).size();i++)
			Assert.assertFalse(actualMessages.get(0).get(i).contains(validateMessage),actualMessages.get(0).get(i)+" should not be displayed since traveler details have been updated. Cart ["+cartId+"] validation failed");			
		System.out.println("Cart ["+cartId+"] with traveler details updated did not return ["+validateMessage+"]. Cart validation succeeded");
	}
	
	
	/**
	 * Update booker details 
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_ValidateShoppingCart")
	public void tc_API_005_ValidateShoppingCart(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String validateCartJSON, String status, String message, String validateMessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
	//	Update Booker details
		endPoint = "/bookings/carts/"+cartId+"/bookerdetails";	
		response = booking.requestUpdateBookerDetails(current_TestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
	
	//	Validate Cart	
		String validateCartEndPoint = "/bookings/carts/"+cartId+"/validate";
				
		response = booking.requestValidateShoppingCart(current_TestName, validateCartEndPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		for(int i=0;i<actualMessages.get(0).size();i++)
			Assert.assertFalse(actualMessages.get(0).get(i).contains(validateMessage),actualMessages.get(0).get(i)+" should not be displayed since booker details have been updated. Cart ["+cartId+"] validation failed");			
		System.out.println("Cart ["+cartId+"] with booker details updated did not return ["+validateMessage+"]. Cart validation succeeded");
	}
	
	
	/**
	 * Update booking answers 
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC006_ValidateShoppingCart")
	public void tc_API_006_ValidateShoppingCart(String endPoint, String partnerId, String createCartJSON, String updateBookingAnswersJSON,
			String validateCartJSON, String status, String message, String validateMessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
	//	Get Booking Questions	
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(current_TestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
			
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray questionsArray = (JSONArray) object.get("bookingQuestions");
			
		System.out.println("---- No of booking questions is "+questionsArray.size());
		
	//	Update booking answers		
		endPoint = "/bookings/carts/"+cartId+"/bookinganswers";
		
		booking.requestUpdateBookingAnswers(current_TestName, endPoint, partnerId, updateBookingAnswersJSON);		
	
	//	Validate Cart	
		String validateCartEndPoint = "/bookings/carts/"+cartId+"/validate";
				
		response = booking.requestValidateShoppingCart(current_TestName, validateCartEndPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		for(int i=0;i<actualMessages.get(0).size();i++)
			Assert.assertFalse(actualMessages.get(0).get(i).contains(validateMessage),actualMessages.get(0).get(i)+" should not be displayed since booking answer(s) have been updated. Cart ["+cartId+"] validation failed");			
		System.out.println("Cart ["+cartId+"] with booking answer(s) added did not return ["+validateMessage+"]. Cart validation succeeded");
	}

}
