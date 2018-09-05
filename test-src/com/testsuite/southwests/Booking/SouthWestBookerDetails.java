package com.testsuite.southwests.Booking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import com.testng.Assert;
import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class SouthWestBookerDetails extends BaseSetup {
	
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="TC001_UpdateBookerDetails")
	public void tc_API_001_UpdateBookerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON) throws ParseException, FileNotFoundException, IOException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
		endPoint = "/bookings/carts/"+cartId+"/bookerdetails";
		response = booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		
		JSONObject bookerDetailsObject = (JSONObject)object.get("bookerDetails");
		
		String actualTitle = (String)bookerDetailsObject.get("title");
		String actualFirstName = (String)bookerDetailsObject.get("firstName");
		String actualLastName = (String)bookerDetailsObject.get("lastName");
		String actualEmail = (String)bookerDetailsObject.get("email");
		String actualPhoneNumber = (String)bookerDetailsObject.get("phoneNumber");
		String actualCountryISOCode = (String)bookerDetailsObject.get("countryISOCode");
		String actualDateOfBirth = (String)bookerDetailsObject.get("dateOfBirth");		
		
		updateBookerDetailsJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
		object = (JSONObject) parser.parse(new FileReader(new File(updateBookerDetailsJSON)));
		JSONObject bookerObject = (JSONObject)object.get("bookerRequest");
		
		String expectedTitle = (String)bookerObject.get("title");
		String expectedFirstName = (String)bookerObject.get("firstName");
		String expectedLastName = (String)bookerObject.get("lastName");
		String expectedEmail = (String)bookerObject.get("email");
		String expectedPhoneNumber = (String)bookerObject.get("phoneNumber");
		String expectedCountryISOCode = (String)bookerObject.get("countryISOCode");
		String expectedDateOfBirth = (String)bookerObject.get("dateOfBirth");
		
		System.out.println("expectedTitle is \""+expectedTitle+"\", whereas actualTitle is \""+actualTitle+"\"");
		System.out.println("expectedFirstName is \""+expectedFirstName+"\", whereas actualFirstName is \""+actualFirstName+"\"");
		System.out.println("expectedLastName is \""+expectedLastName+"\", whereas actualLastName is \""+actualLastName+"\"");
		System.out.println("expectedDateOfBirth is \""+expectedDateOfBirth+"\", whereas actualDateOfBirth is \""+actualDateOfBirth+"\"");
		System.out.println("expectedEmail is \""+expectedEmail+"\", whereas actualEmail is \""+actualEmail+"\"");
		System.out.println("expectedPhoneNumber is \""+expectedPhoneNumber+"\", whereas actualPhoneNumber is \""+actualPhoneNumber+"\"");
		System.out.println("expectedCountryISOCode is \""+expectedCountryISOCode+"\", whereas actualCountryISOCode is \""+actualCountryISOCode+"\"");
		
		Assert.assertEquals(actualTitle, expectedTitle, "Actual Title in response "+actualTitle+" is not matching with the expected title "+expectedTitle);
		Assert.assertEquals(actualFirstName, expectedFirstName, "Actual Title in response "+actualFirstName+" is not matching with the expected title "+expectedFirstName);
		Assert.assertEquals(actualLastName, expectedLastName, "Actual Title in response "+actualLastName+" is not matching with the expected title "+expectedLastName);
		Assert.assertEquals(actualDateOfBirth, expectedDateOfBirth, "Actual Title in response "+actualDateOfBirth+" is not matching with the expected title "+expectedDateOfBirth);
		Assert.assertEquals(actualEmail, expectedEmail, "Actual Title in response "+actualEmail+" is not matching with the expected title "+expectedEmail);
		Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber, "Actual Title in response "+actualPhoneNumber+" is not matching with the expected title "+expectedPhoneNumber);
		Assert.assertEquals(actualCountryISOCode, expectedCountryISOCode, "Actual Title in response "+actualCountryISOCode+" is not matching with the expected title "+expectedCountryISOCode);
				
	}
}