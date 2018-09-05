package com.testsuite.southwests.Booking;

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
import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class SouthWestAddBookingOptions extends BaseSetup {
	
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
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="TC001_AddBookingOptionsToCart")
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
	}
	