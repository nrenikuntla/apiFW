package com.testsuite.placepass.booking;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class GetSingleBookingforCustomer extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Booking booking;
	public ConfigManager api;
	JSONManager jsonObject;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		api = new ConfigManager("Api");
		jsonObject = new JSONManager();
		booking = new Booking();
		currentTestName = method.getName();
		
	}
	
	/**
	 * @throws ParseException
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_GetSingleBookingForCustomer")
	public void tc_API_001_GetSingleBookingUsingGetMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String updateTravelerDetailsJSON, String validateCartJSON, String createBookingJSON, String customerId) throws ParseException {
				
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);			
		
	//	Update Booker Details
		endPoint = "/bookings/carts/"+cartId+"/bookerdetails";		
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		Object bookingId = booking.verifyCreateBooking();		
				
	//	Get Single Booking for Customer
		endPoint = "/bookings/customers/"+customerId+"/bookings/"+bookingId;
		
		booking.requestGetSingleBookingForCustomer(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");	
		
		booking.verifyGetSingleBooking(customerId, bookingId.toString());
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_GetSingleBookingForCustomer")
	public void tc_API_002_GetSingleBookingUsingGetMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON, String updateTravelerDetailsJSON,
			String validateCartJSON, String createBookingJSON, String GetSingleBookingPartnerId, String customerId, String status, String message, String ppcode, String ppmessage) throws ParseException {
				
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);			
		
	//	Update Booker Details
		endPoint = "/bookings/carts/"+cartId+"/bookerdetails";		
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		Object bookingId = booking.verifyCreateBooking();		
				
	//	Get Single Booking for Customer
		endPoint = "/bookings/customers/"+customerId+"/bookings/"+bookingId;
		
		booking.requestGetSingleBookingForCustomer(currentTestName, endPoint, GetSingleBookingPartnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);	
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Invalid customerId
	 * Invalid bookingId
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_GetSingleBookingForCustomer")
	public void tc_API_003_GetSingleBookingUsingGetMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON, String updateTravelerDetailsJSON,
			String validateCartJSON, String createBookingJSON, String customerId, String bookingId, String status, String message, String ppcode, String ppmessage) throws ParseException {
				
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);			
		
	//	Update Booker Details
		endPoint = "/bookings/carts/"+cartId+"/bookerdetails";		
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		if(bookingId==null)
			bookingId = (String) booking.verifyCreateBooking();		
				
	//	Get Single Booking for Customer
		endPoint = "/bookings/customers/"+customerId+"/bookings/"+bookingId;
		
		booking.requestGetSingleBookingForCustomer(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);	
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}

}
