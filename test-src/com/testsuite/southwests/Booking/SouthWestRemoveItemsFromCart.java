package com.testsuite.southwests.Booking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class SouthWestRemoveItemsFromCart extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
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
		booking = new Booking();
		currentTestName = method.getName();
		
	}
	
	/**
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="TC001_RemoveItemsFromShoppingCart")
	public void tc_API_001_RemoveItemsFromShoppingCartUsingDeleteMethod(String endPoint, String partnerId, String requestJSON, String validateCartJSON, String bookingoptionid) throws ParseException, FileNotFoundException, IOException {
		
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
		
		String validateCartEndPoint = "/bookings/carts/"+cartId;
		
		booking.requestViewShoppingCart(currentTestName, validateCartEndPoint, partnerId);
		
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyBookingOptionsIsNotNullInViewCart();
		
		endPoint = "/bookings/carts/"+cartId+"/bookingoptions/"+bookingoptionid;
		
		response = booking.requestRemoveItemsFromCart(currentTestName, endPoint, partnerId);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyBookingOptionsIsNull(response);
		
	}
}	
