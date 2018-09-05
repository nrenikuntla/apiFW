package com.testsuite.placepass.booking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testng.Assert;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class UpdateLanguageOptionCode extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Booking booking;
	private APIHelpers apiHelpers;
	public ConfigManager api;
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_UpdateLanguageOptionCode")
	public void tc_API_001_UpdateLanguageOptionCodeUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String UpdateLanguageOptionCodeJSON, String productOptionId, String languageCode) throws ParseException, FileNotFoundException, IOException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("cartId is - "+cartId);
		
		endPoint = "/bookings/carts/"+cartId+"/bookingoptions/"+productOptionId+"/languageoptioncode";	
		response = booking.requestUpdateLanguageOptionCode(currentTestName, endPoint, partnerId, UpdateLanguageOptionCodeJSON);
		
	//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			booking.verifyLanguageOption(languageCode);		
	}
	
		
	/**
	 * 1. Without partner-id
	 * 2. Invalid partner-id
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_UpdateLanguageOptionCode")
	public void tc_API_002_UpdateLanguageOptionCodeUsingPutMethod(String endPoint, String createCartPartnerId, String createCartJSON, String updateLanguagePartnerId,
			String UpdateLanguageOptionCodeJSON, String productOptionId, String languageCode, String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, createCartPartnerId, createCartJSON);
		
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("cartId is - "+cartId);
		
		endPoint = "/bookings/carts/"+cartId+"/bookingoptions/"+productOptionId+"/languageoptioncode";	
		
		booking.requestUpdateLanguageOptionCode(currentTestName, endPoint, updateLanguagePartnerId, UpdateLanguageOptionCodeJSON);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);			
	}
}
