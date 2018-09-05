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
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class AddBookingOptionsToCart extends BaseSetup {
	
//	Declaration of respective API Parts instances
	Booking booking;
	private APIHelpers apiHelpers;
	public ConfigManager api;
	JSONManager jsonObject;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		apiHelpers = new APIHelpers();
		api = new ConfigManager("Api");
		jsonObject = new JSONManager();
		booking = new Booking();
		currentTestName = method.getName();
		
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_AddBookingOptionsToCart")
	public void tc_API_001_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String AddBookingOptionsToCartJSON,
			String productId, String productOptionId, String bookingDate, String quantity) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
		
		booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, AddBookingOptionsToCartJSON);	
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
        booking.verifyAddBookingOptionsToCart(productId,productOptionId,bookingDate,quantity);
			
	}
	
	
	/**
	 * 1. Without partner-id
	 * 2. Invalid partner-id
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_AddBookingOptionsToCart")
	public void tc_API_002_AddBookingOptionsToCartUsingPutMethod(String endPoint, String createCartPartnerId, String createCartJSON, String addBookingOptionsPartnerId,
			String addBookingOptionsToCartJSON,	String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, createCartPartnerId, createCartJSON);
		
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
		
		booking.requestAddBookingOptionsToCart(currentTestName, endPoint, addBookingOptionsPartnerId, addBookingOptionsToCartJSON);	
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
        booking.verifyPPCodePPMessage(ppcode, ppmessage);
			
	}

	
	/**
	 * 1. Without product id key
	 * 2. Empty product id
	 * 3. Invalid product id
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_AddBookingOptionsToCart")
	public void tc_API_003_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String addBookingOptionsToCartJSON,
			String productId, String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
	
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
		
		if(productId!=null){
			//	Update JSON with productId
			String requestJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject product = (JSONObject) bookingOptions.get(0);
			product.put("productId", productId);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting productId===========\n" + requestBody);
			
			addBookingOptionsToCartJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			
			booking.requestAddBookingOptionsDynamic(currentTestName, endPoint, partnerId, requestBody, addBookingOptionsToCartJSON);
		
		}else{		
			booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);
		}		
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
        booking.verifyPPCodePPMessage(ppcode, ppmessage);			
	}
	
	
	/**
	 * 1. Without productOptionId key
	 * 2. Empty productOptionId
	 * 3. Invalid productOptionId
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_AddBookingOptionsToCart")
	public void tc_API_004_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String addBookingOptionsToCartJSON,
			String productOptionId, String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
	
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
		
		if(productOptionId!=null){
			//	Update JSON with productOptionId
			String requestJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject product = (JSONObject) bookingOptions.get(0);
			product.put("productOptionId", productOptionId);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting productOptionId===========\n" + requestBody);
			
			addBookingOptionsToCartJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			
			booking.requestAddBookingOptionsDynamic(currentTestName, endPoint, partnerId, requestBody, addBookingOptionsToCartJSON);
		
		}else{		
			booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);
		}		
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
        booking.verifyPPCodePPMessage(ppcode, ppmessage);			
	}
	
	
	/**
	 * 1. Without bookingDate key
	 * 2. Empty bookingDate
	 * 3. Invalid bookingDate 
	 * 4. Past bookingDate
	 * 5. Future bookingDate
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_AddBookingOptionsToCart")
	public void tc_API_005_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String addBookingOptionsToCartJSON,
			String bookingDate, String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
	
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
		
		if(bookingDate!=null){
			//	Update JSON with bookingDate
			String requestJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject product = (JSONObject) bookingOptions.get(0);
			product.put("bookingDate", bookingDate);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting bookingDate===========\n" + requestBody);
			
			addBookingOptionsToCartJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			
			booking.requestAddBookingOptionsDynamic(currentTestName, endPoint, partnerId, requestBody, addBookingOptionsToCartJSON);
		
		}else{		
			booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);
		}		
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
        booking.verifyPPCodePPMessage(ppcode, ppmessage);			
	}
	
	
	/**
	 * 1. Without quantities key
	 * 2. Empty quantities key
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC006_AddBookingOptionsToCart")
	public void tc_API_006_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String addBookingOptionsToCartJSON, String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
	
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
		
		booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);			
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
        booking.verifyPPCodePPMessage(ppcode, ppmessage);			
	}
	
	
	/**
	 * 1. Without quantity key
	 * 2. Empty quantity
	 * 3. Invalid quantity 
	 * 4. Quantity = 0
	 * 5. Quantity less than minimum
	 * 6. Quantity meeting minimum
	 * 7. Quantity = 8
	 * 8. Quantity = 10
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC007_AddBookingOptionsToCart")
	public void tc_API_007_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String addBookingOptionsToCartJSON, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
		
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
		
		booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);			
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}
	
	
	/**
	 * 1. Without ageBandId key
	 * 2. Empty AgeBandId
	 * 3. Invalid AgeBandId (-1)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC008_AddBookingOptionsToCart")
	public void tc_API_008_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String addBookingOptionsToCartJSON, String ageBandId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
		
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
		
		if(ageBandId!=null){
			//	Update JSON with ageBandId
			String requestJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			JSONArray bookingOptions = (JSONArray) object.get("bookingOptions");
			JSONObject option = (JSONObject) bookingOptions.get(0);
			JSONArray quantities = (JSONArray) option.get("quantities");
			JSONObject ageBand = (JSONObject) quantities.get(0);
			ageBand.put("ageBandId", ageBandId);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting ageBandId===========\n" + requestBody);
			
			addBookingOptionsToCartJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			
			booking.requestAddBookingOptionsDynamic(currentTestName, endPoint, partnerId, requestBody, addBookingOptionsToCartJSON);
		}
		else{
			booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);
		}	
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);		
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC009_AddBookingOptionsToCart")
	public void tc_API_009_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String addBookingOptionsToCartJSON,
			String id, String locationName, String isHotelPickUpEligible, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
		
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
	
		if(id!=null || locationName!=null){
			//	Update JSON with id
			String requestJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
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
			
			addBookingOptionsToCartJSON = System.getProperty("user.dir") + addBookingOptionsToCartJSON;
			
			response = booking.requestAddBookingOptionsDynamic(currentTestName, endPoint, partnerId, requestBody, addBookingOptionsToCartJSON);
		
		}else{		
			response = booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);
		}
		
		/***********/
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null){
			booking.verifyPPCodePPMessage(ppcode,ppmessage);
		}	
		else{
			//	View shopping cart
			endPoint = "/bookings/carts/"+cartId;
			
			response = booking.requestViewShoppingCart(currentTestName, endPoint, partnerId);
			
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC010_AddBookingOptionsToCart")
	public void tc_API_010_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String addBookingOptionsToCartJSON, String adult, String senior, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
		
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
			
		booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null){
			booking.verifyPPCodePPMessage(ppcode,ppmessage);
		}		
	}
	
	
	/**
	 * 1. Create empty cart and add two booking options via request
	 * 2. Create cart with one booking option and add second option via request
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC011_AddBookingOptionsToCart")
	public void tc_API_011_AddBookingOptionsToCartUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String AddBookingOptionsToCartJSON,	String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
		
		booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, AddBookingOptionsToCartJSON);	
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);
	}
}
