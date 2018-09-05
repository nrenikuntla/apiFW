package com.testsuite.placepass.booking;

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
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class UpdateBookerDetails extends BaseSetup {
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_UpdateBookerDetails")
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
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_UpdateBookerDetails")
	public void tc_API_002_UpdateBookerDetailsUsingPutMethod(String endPoint, String createCartPartnerId, String createCartJSON, String updateBookerDetailsPartnerId,
			String updateBookerDetailsJSON, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, createCartPartnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
		
		endPoint = "/bookings/carts/"+cartId+"/bookerdetails";	
		response = booking.requestUpdateBookerDetails(currentTestName, endPoint, updateBookerDetailsPartnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
		
	}
	
	
	/**
	 * Without title
	 * Empty title
	 * Invalid title
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_UpdateBookerDetails")
	public void tc_API_003_UpdateBookerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String updateBookerDetailsJSON,	String title, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
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
		
		if(title!=null){
			//	Update JSON with title
			String requestJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			JSONObject bookerRequest = (JSONObject) object.get("bookerRequest");
			bookerRequest.put("title", title);
			
			requestBody = object.toJSONString();
			System.out.println("==============After inserting title===========\n" + requestBody);
			
			updateBookerDetailsJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
			
			booking.requestUpdateBookerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateBookerDetailsJSON);
		
		}else{		
			booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		}		
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}
	
	
	/**
	 * Without firstName
	 * Empty firstName
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_UpdateBookerDetails")
	public void tc_API_004_UpdateBookerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String updateBookerDetailsJSON,	String status, String message, String ppcode, String ppmessage) throws ParseException{
		
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
		
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}
	
	
	/**
	 * Without lastName
	 * Empty lastName
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_UpdateBookerDetails")
	public void tc_API_005_UpdateBookerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String updateBookerDetailsJSON,	String status, String message, String ppcode, String ppmessage) throws ParseException{
		
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
		
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}
	
	
	/**
	 * Without email
	 * Empty email
	 * Invalid email (abc.com)
	 * Email with capital (Test@placepass.com)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC006_UpdateBookerDetails")
	public void tc_API_006_UpdateBookerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String email, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
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
		
		if(email!=null){
			//	Update JSON with email
			String requestJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			JSONObject bookerRequest = (JSONObject) object.get("bookerRequest");
			bookerRequest.put("email", email);
			
			requestBody = object.toJSONString();
			System.out.println("==============After inserting email===========\n" + requestBody);
			
			updateBookerDetailsJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
			
			booking.requestUpdateBookerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateBookerDetailsJSON);
		
		}else{		
			booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		}
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}
	
	
	/**
	 * Without phone
	 * Empty phone
	 * Invalid phone (077123542a)
	 * Phone with spaces (603 345 9039)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC007_UpdateBookerDetails")
	public void tc_API_007_UpdateBookerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String phone, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
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
		
		if(phone!=null){
			//	Update JSON with phone
			String requestJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			JSONObject bookerRequest = (JSONObject) object.get("bookerRequest");
			bookerRequest.put("phoneNumber", phone);
			
			requestBody = object.toJSONString();
			System.out.println("==============After inserting phone===========\n" + requestBody);
			
			updateBookerDetailsJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
			
			booking.requestUpdateBookerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateBookerDetailsJSON);
		
		}else{		
			booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		}
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}
	
	
	/**
	 * Without country
	 * Empty country 
	 * Invalid country (94)
	 * Invalid country (I)
	 * Invalid country (INN)
	 * Invalid country (ZZ)
	 * Valid country (US) valid phone of different country (0771235423)
	 * Valid country (US) valid phone no isd code (6033459039)
	 * Valid country (US) valid phone US isd code (+16033459039)
	 * Valid country (JP) valid phone JP isd code (+81345782777)
	 * Valid country (LK) valid phone different country isd code (+81345782777) 
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC008_UpdateBookerDetails")
	public void tc_API_008_UpdateBookerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String countryCode, String phone, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
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
		
		//	Update JSON with Country/Phone
		String requestJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		JSONObject bookerRequest = (JSONObject) object.get("bookerRequest");
		
		if(countryCode!=null)
			bookerRequest.put("countryISOCode", countryCode);
		if(phone!=null)
			bookerRequest.put("phoneNumber", phone);
		
		requestBody = object.toJSONString();
		
		if(countryCode!=null || phone!=null)
			System.out.println("==============After inserting Country/Phone=============\n" + requestBody);
					
		updateBookerDetailsJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
		
		booking.requestUpdateBookerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}
	
	
	/**
	 * Without dateOfBirth
	 * Empty dateOfBirth
	 * Future dateOfBirth
	 * Invalid dateOfBirth
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC009_UpdateBookerDetails")
	public void tc_API_009_UpdateBookerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String dateOfBirth, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
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
		
		if(dateOfBirth!=null){
			//	Update JSON with dateOfBirth
			String requestJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			JSONObject bookerRequest = (JSONObject) object.get("bookerRequest");
			bookerRequest.put("dateOfBirth", dateOfBirth);
			
			requestBody = object.toJSONString();
			System.out.println("==============After inserting dateOfBirth===========\n" + requestBody);
			
			updateBookerDetailsJSON = System.getProperty("user.dir") + updateBookerDetailsJSON;
			
			booking.requestUpdateBookerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateBookerDetailsJSON);
		
		}else{		
			booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		}
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}

}
