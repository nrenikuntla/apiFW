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

public class CreateShoppingCart extends BaseSetup{
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_CreateShoppingCart")
	public void tc_API_001_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String validateCartJSON, String expectedError) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
		//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("CartId is - "+cartId);
		
		//	Validate cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains(expectedError),actualMessages.get(0).get(0)+" is displayed instead of "+expectedError+". Cart ["+cartId+"] validation failed");
		System.out.println("Cart ["+cartId+"] successfully validated");
					
	}
	
	
	/**
	 * 1. Without partner-id header
	 * 2. Invalid partner-id header 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_CreateShoppingCart")
	public void tc_API_002_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String status, String message, String ppcode, String ppmessage){
		
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);					
	}
	
	
	/**
	 * 1. Without productId key
	 * 2. Empty productId
	 * 3. Invalid productId
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_CreateShoppingCart")
	public void tc_API_003_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String productId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		if(productId!=null){
			//	Update JSON with productId
			String requestJSON = System.getProperty("user.dir") + createCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject product = (JSONObject) bookingOptions.get(0);
			product.put("productId", productId);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting productId===========\n" + requestBody);
			
			createCartJSON = System.getProperty("user.dir") + createCartJSON;
			
			booking.requestCreateCartDynamic(currentTestName, endPoint, partnerId, requestBody, createCartJSON);
		
		}else{		
			booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		}
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);					
	}
	
	
	/**
	 * 1. Without productOptionId key
	 * 2. Empty productOptionId
	 * 3. Invalid productOptionId 
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_CreateShoppingCart")
	public void tc_API_004_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String productOptionId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		if(productOptionId!=null){
			//	Update JSON with productOptionId
			String requestJSON = System.getProperty("user.dir") + createCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject option = (JSONObject) bookingOptions.get(0);
			option.put("productOptionId", productOptionId);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting productOptionId===========\n" + requestBody);
			
			createCartJSON = System.getProperty("user.dir") + createCartJSON;
			
			booking.requestCreateCartDynamic(currentTestName, endPoint, partnerId, requestBody, createCartJSON);
		
		}else{		
			booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		}
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);					
	}
	
	
	/**
	 * 1. Without bookingDate key
	 * 2. Empty bookingDate
	 * 3. Invalid bookingDate (day) 
	 * 4. Invalid bookingDate (month)
	 * 5. Invalid bookingDate (year)
	 * 6. Past bookingDate
	 * 7. Future bookingDate
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_CreateShoppingCart")
	public void tc_API_005_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String bookingDate, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		if(bookingDate!=null){
			//	Update JSON with bookingDate
			String requestJSON = System.getProperty("user.dir") + createCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject date = (JSONObject) bookingOptions.get(0);
			date.put("bookingDate", bookingDate);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting bookingDate===========\n" + requestBody);
			
			createCartJSON = System.getProperty("user.dir") + createCartJSON;
			
			booking.requestCreateCartDynamic(currentTestName, endPoint, partnerId, requestBody, createCartJSON);
		
		}else{		
			booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		}
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);					
	}
	
	
	/**
	 * 1. Without quantities key
	 * 2. Empty quantities key
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC006_CreateShoppingCart")
	public void tc_API_006_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String status, String message, String ppcode, String ppmessage){
		
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);					
	}
	
	
	/**
	 * 1. Without quantity key
	 * 2. Empty quantity
	 * 3. Invalid quantity 
	 * 4. Quantity = 0
	 * 5. Quantity less than minimum
	 * 6. Quantity meeting minimum
	 * 7. Quantity = 8
	 * 8. Quantity = 16
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC007_CreateShoppingCart")
	public void tc_API_007_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String validateCartJSON, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null){
			booking.verifyPPCodePPMessage(ppcode,ppmessage);
		}	
		else{
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("CartId is - "+cartId);
			
			//	Validate cart
			endPoint = "/bookings/carts/"+cartId+"/validate";
			
			response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
			
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
			Assert.assertTrue(actualMessages.get(0).get(0).contains("Booker details is mandatory"),actualMessages.get(0).get(0)+" is displayed instead of 'Booker details is mandatory'. Cart ["+cartId+"] validation failed");
			System.out.println("Cart ["+cartId+"] successfully validated");
		}	
	}
	
	
	/**
	 * 1. Without ageBandId key
	 * 2. Empty AgeBandId
	 * 3. Invalid AgeBandId (7) 
	 * 4. Invalid AgeBandId (-1)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC008_CreateShoppingCart")
	public void tc_API_008_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String ageBandId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		if(ageBandId!=null){
			//	Update JSON with ageBandId
			String requestJSON = System.getProperty("user.dir") + createCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject option = (JSONObject) bookingOptions.get(0);
			JSONArray quantities = (JSONArray) option.get("quantities");
			JSONObject ageBand = (JSONObject) quantities.get(0);
			ageBand.put("ageBandId", ageBandId);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting ageBandId===========\n" + requestBody);
			
			createCartJSON = System.getProperty("user.dir") + createCartJSON;
			
			booking.requestCreateCartDynamic(currentTestName, endPoint, partnerId, requestBody, createCartJSON);
		
		}else{		
			booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		}
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);					
	}
	
	
	/**
	 * 1. isHotelPickUpEligible: true
	 * 2. pickupLocation = null
	 * 3. pickupLocation is empty
	 * 4. Without id key
	 * 5. Empty id
	 * 6. Invalid id
	 * 7. Without locationName key
	 * 8. Empty locationName
	 * 9. Invalid locationName
	 * 10.isHotelPickupEligible: false
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC009_CreateShoppingCart")
	public void tc_API_009_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String id,
			String locationName, String isHotelPickUpEligible, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String,String> response = new HashMap<String,String>();
		
		if(id!=null || locationName!=null){
			//	Update JSON with id
			String requestJSON = System.getProperty("user.dir") + createCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject option = (JSONObject) bookingOptions.get(0);
			JSONObject pickupLocation = (JSONObject) option.get("pickupLocation");
			
			if(id!=null){
				pickupLocation.put("id", id);				
			}
			if(locationName!=null){
				pickupLocation.put("locationName", locationName);
			}
			requestBody = object.toJSONString();
			System.out.println("==============After inserting id/locationName===========\n" + requestBody);
			
			createCartJSON = System.getProperty("user.dir") + createCartJSON;
			
			response = booking.requestCreateCartDynamic(currentTestName, endPoint, partnerId, requestBody, createCartJSON);
		
		}else{		
			response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		}
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null){
			booking.verifyPPCodePPMessage(ppcode,ppmessage);
		}	
		else{
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("CartId is - "+cartId);
			
			//	View shopping cart
			endPoint = "/bookings/carts/"+cartId;
			
			response = booking.requestViewShoppingCart(currentTestName, endPoint, partnerId);
			
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(responseString);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject option = (JSONObject) bookingOptions.get(0);
			JSONObject pickupLocation = (JSONObject) option.get("pickupLocation");
			 
			if(pickupLocation!=null){
				Object obtainedId = pickupLocation.get("id");
				if(obtainedId!=null)
					Assert.assertEquals(obtainedId.toString(), id, "Obtained id ["+obtainedId+"] from cart ["+cartId+"] does not match with expected ["+id+"]");
				else
					Assert.assertEquals(obtainedId, id, "Obtained id ["+obtainedId+"] from cart ["+cartId+"] does not match with expected ["+id+"]");
				System.out.println("Obtained id ["+obtainedId+"] from cart ["+cartId+"] is as expected");
				
				Object obtainedLocationName = pickupLocation.get("locationName");
				if(obtainedLocationName!=null)
					Assert.assertEquals(obtainedLocationName.toString(), locationName, "Obtained location ["+obtainedLocationName+"] from cart ["+cartId+"] does not match with expected ["+locationName+"]");
				else
					Assert.assertEquals(obtainedLocationName, locationName, "Obtained location ["+obtainedLocationName+"] from cart ["+cartId+"] does not match with expected ["+locationName+"]");
				System.out.println("Obtained location ["+obtainedLocationName+"] from cart ["+cartId+"] is as expected");
			}
			else{
				System.out.println("pickupLocation is null as expected");
			}
			
			Object obtainedisHotelPickUpEligible = option.get("isHotelPickUpEligible");
			Assert.assertEquals(obtainedisHotelPickUpEligible.toString(), isHotelPickUpEligible.trim(), "Obtained isHotelPickUpEligible attrib ["+obtainedisHotelPickUpEligible+"] doesnt match with expected ["+isHotelPickUpEligible+"]");
			System.out.println("Obtained isHotelPickUpEligible attrib ["+obtainedisHotelPickUpEligible+"] is as expected");
		}					
	}
	
	
	/**
	 * 1. 1 Adult only
	 * 2. 1 Senior only
	 * 3. 1 Child only 
	 * 4. 1 Infant only
	 * 5. 1 Youth only
	 * 6. All bands except Adult
	 * 7. All bands except Senior
	 * 8. All bands except Child
	 * 9. All bands except Infant
	 * 10.All bands except Youth
	 * 11.Senior not in allowed ageband
	 * 12.Child not in allowed ageband
	 * 13.Infant not in allowed ageband
	 * 14.Youth not in allowed ageband
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC010_CreateShoppingCart")
	public void tc_API_010_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String validateCartJSON, String adult, String senior, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null){
			booking.verifyPPCodePPMessage(ppcode,ppmessage);
		}	
		else{
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("CartId is - "+cartId);
			
			//	Validate cart
			endPoint = "/bookings/carts/"+cartId+"/validate";
			
			response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
			
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
			Assert.assertTrue(actualMessages.get(0).get(0).contains("Booker details is mandatory"),actualMessages.get(0).get(0)+" is displayed instead of 'Booker details is mandatory'. Cart ["+cartId+"] validation failed");
			System.out.println("Cart ["+cartId+"] successfully validated");
		}	
	}

}
