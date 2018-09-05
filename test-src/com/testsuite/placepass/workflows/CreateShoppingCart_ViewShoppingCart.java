package com.testsuite.placepass.workflows;

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
import com.testsuite.dataprovider.WorkFlows_TestData_Provider;
import com.utilities.APIHelpers;

public class CreateShoppingCart_ViewShoppingCart extends BaseSetup{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	private APIHelpers apiHelpers;
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
		apiHelpers = new APIHelpers();
		api = new ConfigManager("Api");
		current_TestName = method.getName();
		booking = new Booking();
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=WorkFlows_TestData_Provider.class, dataProvider="TC001_CreateAndViewCart")
	public void tc_API_001_CreateShoppingCartAndViewCart(String endPoint, String partnerId, String requestJSON, String expectedProductId, String expectedProductOptionId) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
		String validateCartEndPoint = "/bookings/carts/"+cartId;
				
		booking.requestViewShoppingCart(current_TestName, validateCartEndPoint, partnerId);
		
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyViewCart(expectedProductId,expectedProductOptionId);
		
	}
	
	
	/**
	 * 1. Without partner-id
	 * 2. Invalid partner-id
	 * 3. Invalid cart id
	 * @throws ParseException
	 */
	@Test(dataProviderClass=WorkFlows_TestData_Provider.class, dataProvider="TC002_CreateAndViewCart")
	public void tc_API_002_CreateShoppingCartAndViewCart(String endPoint, String createCartPartnerId, String requestJSON,
			String viewCartPartnerId, String cartId, String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		if(cartId==null){
			HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, createCartPartnerId, requestJSON);
			
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			cartId = (String) object.get("cartId");
		}
		
		System.out.println("cartId is - "+cartId);
		String validateCartEndPoint = "/bookings/carts/"+cartId;
		
		booking.requestViewShoppingCart(current_TestName, validateCartEndPoint, viewCartPartnerId);
		
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);		
	}

}
