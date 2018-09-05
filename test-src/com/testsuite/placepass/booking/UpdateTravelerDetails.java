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
import com.datamanager.JSONManager;
import com.testng.Assert;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class UpdateTravelerDetails extends BaseSetup {
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_UpdateTravelerDetails")
	public void tc_API_001_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON) throws ParseException, FileNotFoundException, IOException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("cartId is - "+cartId);
		
		endPoint = "/bookings/carts/"+cartId+"/travelers";	
		response = booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(responseString);
			
			Object actualBookerDetails = (Object)object.get("bookerDetails");
			
			JSONArray JsonArray = (JSONArray) object.get("bookingOptions");
			JsonArray = (JSONArray) ((JSONObject)JsonArray.get(0)).get("traverlerDetails");
			
			String actualTitle = (String)((JSONObject)JsonArray.get(0)).get("title");
			String actualFirstName = (String)((JSONObject)JsonArray.get(0)).get("firstName");
			String actualLastName = (String)((JSONObject)JsonArray.get(0)).get("lastName");
			String actualDateOfBirth = (String)((JSONObject)JsonArray.get(0)).get("dateOfBirth");
			String actualEmail = (String)((JSONObject)JsonArray.get(0)).get("email");
			String actualPhoneNumber = (String)((JSONObject)JsonArray.get(0)).get("phoneNumber");
			String actualCountryISOCode = (String)((JSONObject)JsonArray.get(0)).get("countryISOCode");
			long actualAgeBandId = (long) ((JSONObject)JsonArray.get(0)).get("ageBandId");
			boolean actualLeadTraveler = (boolean)((JSONObject)JsonArray.get(0)).get("leadTraveler");
			
			
			updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
			object = (JSONObject) parser.parse(new FileReader(new File(updateTravelerDetailsJSON)));
			JsonArray = (JSONArray)object.get("travelers");
		
			String expectedTitle = (String)((JSONObject)JsonArray.get(0)).get("title");
			String expectedFirstName = (String)((JSONObject)JsonArray.get(0)).get("firstName");
			String expectedLastName = (String)((JSONObject)JsonArray.get(0)).get("lastName");
			String expectedDateOfBirth = (String)((JSONObject)JsonArray.get(0)).get("dateOfBirth");
			String expectedEmail = (String)((JSONObject)JsonArray.get(0)).get("email");
			String expectedPhoneNumber = (String)((JSONObject)JsonArray.get(0)).get("phoneNumber");
			String expectedCountryISOCode = (String)((JSONObject)JsonArray.get(0)).get("countryISOCode");
			long expectedAgeBandId = (long)((JSONObject)JsonArray.get(0)).get("ageBandId");
			boolean expectedLeadTraveler = (boolean)((JSONObject)JsonArray.get(0)).get("leadTraveler");
			
			System.out.println("expectedTitle is \""+expectedTitle+"\", whereas actualTitle is \""+actualTitle+"\"");
			System.out.println("expectedFirstName is \""+expectedFirstName+"\", whereas actualFirstName is \""+actualFirstName+"\"");
			System.out.println("expectedLastName is \""+expectedLastName+"\", whereas actualLastName is \""+actualLastName+"\"");
			System.out.println("expectedDateOfBirth is \""+expectedDateOfBirth+"\", whereas actualDateOfBirth is \""+actualDateOfBirth+"\"");
			System.out.println("expectedEmail is \""+expectedEmail+"\", whereas actualEmail is \""+actualEmail+"\"");
			System.out.println("expectedPhoneNumber is \""+expectedPhoneNumber+"\", whereas actualPhoneNumber is \""+actualPhoneNumber+"\"");
			System.out.println("expectedCountryISOCode is \""+expectedCountryISOCode+"\", whereas actualCountryISOCode is \""+actualCountryISOCode+"\"");
			System.out.println("expectedAgeBandId is \""+expectedAgeBandId+"\", whereas actualAgeBandId is \""+actualAgeBandId+"\"");
			System.out.println("expectedLeadTraveler is \""+expectedLeadTraveler+"\", whereas actualLeadTraveler is \""+actualLeadTraveler+"\"");
			
			System.out.println("actualBookerDetails is "+actualBookerDetails);
			
			Assert.assertEquals(actualTitle, expectedTitle, "Actual Title in response "+actualTitle+" is not matching with the expected title "+expectedTitle);
			Assert.assertEquals(actualFirstName, expectedFirstName, "Actual Title in response "+actualFirstName+" is not matching with the expected title "+expectedFirstName);
			Assert.assertEquals(actualLastName, expectedLastName, "Actual Title in response "+actualLastName+" is not matching with the expected title "+expectedLastName);
			Assert.assertEquals(actualDateOfBirth, expectedDateOfBirth, "Actual Title in response "+actualDateOfBirth+" is not matching with the expected title "+expectedDateOfBirth);
			Assert.assertEquals(actualEmail, expectedEmail, "Actual Title in response "+actualEmail+" is not matching with the expected title "+expectedEmail);
			Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber, "Actual Title in response "+actualPhoneNumber+" is not matching with the expected title "+expectedPhoneNumber);
			Assert.assertEquals(actualCountryISOCode, expectedCountryISOCode, "Actual Title in response "+actualCountryISOCode+" is not matching with the expected title "+expectedCountryISOCode);
			Assert.assertEquals(actualAgeBandId, expectedAgeBandId, "Actual Title in response "+actualAgeBandId+" is not matching with the expected title "+expectedAgeBandId);
			Assert.assertEquals(actualLeadTraveler, expectedLeadTraveler, "Actual Title in response "+actualLeadTraveler+" is not matching with the expected title "+expectedLeadTraveler);
		
			Assert.assertTrue(actualBookerDetails == null, "Actual Booker Details is not null, hence failed");
		
	}
	
	
	/**
	 * 1 traveler - leadTraveler = false 
	 * 2 travelers - leadTraveler both false  
	 * 2 travelers - leadTraveler both true
	 * 2 travelers - leadTraveler 1 true & 1 false 
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_UpdateTravelerDetails")
	public void tc_API_002_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String traveler,String leadTraveler1, String traveler2, String leadTraveler2, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		response = booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null){
			System.out.println("Traveler1 = "+traveler+ " leadTraveler = "+leadTraveler1);			
			if(traveler2!=null)
				System.out.println("Traveler2 = "+traveler2+ " leadTraveler = "+leadTraveler2);
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
		}
		else{
			responseString = response.get("response body");
			
			object = (JSONObject) parser.parse(responseString);
			
			JSONArray bookingOptions = (JSONArray)object.get("bookingOptions");
			JSONObject bookingOption = (JSONObject) bookingOptions.get(0);
			
			JSONArray travelerDetails = (JSONArray) bookingOption.get("traverlerDetails");
			for(int i=0; i<travelerDetails.size(); i++)
			{
				JSONObject travelerObj = (JSONObject) travelerDetails.get(i);
				Object obtainedTraveler = travelerObj.get("firstName");
				Object obtainedleadTraveler = travelerObj.get("leadTraveler");
				
				if(travelerDetails.size()>1 && i==1){
					Assert.assertEquals(obtainedTraveler, traveler2, "Traveler "+(i+1)+" name "+obtainedTraveler+" doesn't match with expected "+traveler2);
					System.out.println("Traveler "+(i+1)+" name "+obtainedTraveler+" is as expected");
					
					Assert.assertEquals(obtainedleadTraveler.toString(), leadTraveler2.trim(), "Traveler "+(i+1)+" "+obtainedTraveler+" leadTraveler status changed from "+leadTraveler2+" to "+obtainedleadTraveler);
					System.out.println("Traveler "+(i+1)+" "+obtainedTraveler+" retained original status leadTraveler="+obtainedleadTraveler);
				}
				else{
					Assert.assertEquals(obtainedTraveler, traveler, "Traveler "+(i+1)+" name "+obtainedTraveler+" doesn't match with expected "+traveler);
					System.out.println("Traveler "+(i+1)+" name "+obtainedTraveler+" is as expected");
					
					Assert.assertEquals(obtainedleadTraveler.toString(), "true", "Traveler "+(i+1)+" "+obtainedTraveler+" did not become leadTraveler");
					System.out.println("Traveler "+(i+1)+" "+obtainedTraveler+" became leadTraveler by default");
				}
					
			}	
		}	
	}
	
	
	/**
	 * Without partner-id 
	 * Invalid partner-id
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_UpdateTravelerDetails")
	public void tc_API_003_UpdateTravelerDetailsUsingPutMethod(String endPoint, String createCartPartnerId, String createCartJSON, String updateTravelersPartnerId,
			String updateTravelerDetailsJSON, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, createCartPartnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		response = booking.requestUpdateTravelerDetails(currentTestName, endPoint, updateTravelersPartnerId, updateTravelerDetailsJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);		
	}
	
	
	/**
	 * Without productOptionId key 
	 * Empty productOptionId  
	 * Invalid productOptionId
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_UpdateTravelerDetails")
	public void tc_API_004_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String productOptionId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		if(productOptionId!=null){
			//	Update JSON with productOptionId
			String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			object.put("productOptionId", productOptionId);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting productOptionId===========\n" + requestBody);
			
			updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
			
			booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);
		
		}else{		
			booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		}		
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without title key 
	 * Empty title  
	 * Invalid title
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_UpdateTravelerDetails")
	public void tc_API_005_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String title, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		if(title!=null){
			//	Update JSON with title
			String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			object.put("title", title);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting title===========\n" + requestBody);
			
			updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
			
			booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);
		
		}else{		
			booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		}		
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without firstName key - leadTraveler=true 
	 * Empty firstName - leadTraveler=true  
	 * Without firstName key - leadTraveler=false 
	 * Empty firstName - leadTraveler=false
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC006_UpdateTravelerDetails")
	public void tc_API_006_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String leadTraveler, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		
//		Update JSON with leadTraveler
		String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		JSONArray travelers = (JSONArray) object.get("travelers");
		JSONObject traveler = (JSONObject) travelers.get(0); 
		traveler.put("leadTraveler", leadTraveler.trim());
		requestBody = object.toJSONString();
		System.out.println("==============After inserting leadTraveler===========\n" + requestBody);
		
		updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		
		booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);				
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without lastName key - leadTraveler=true 
	 * Empty lastName - leadTraveler=true  
	 * Without lastName key - leadTraveler=false 
	 * Empty lastName - leadTraveler=false
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC007_UpdateTravelerDetails")
	public void tc_API_007_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String leadTraveler, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		
//		Update JSON with leadTraveler
		String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		JSONArray travelers = (JSONArray) object.get("travelers");
		JSONObject traveler = (JSONObject) travelers.get(0); 
		traveler.put("leadTraveler", leadTraveler.trim());
		requestBody = object.toJSONString();
		System.out.println("==============After inserting leadTraveler===========\n" + requestBody);
		
		updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		
		booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without email key - leadTraveler=true 
	 * Empty email - leadTraveler=true
	 * Invalid email - leadTraveler=true (abc.com)  
	 * Invalid email - leadTraveler=true (Chiranjeevi.alti@zenq.com)
	 * Without email key - leadTraveler=false 
	 * Empty email - leadTraveler=false
	 * Invalid email - leadTraveler=false (abc.com)
	 * Invalid email - leadTraveler=false (Chiranjeevi.alti@zenq.com)
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC008_UpdateTravelerDetails")
	public void tc_API_008_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String email, String leadTraveler, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
	
//		Update JSON with leadTraveler
		String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		JSONArray travelers = (JSONArray) object.get("travelers");
		JSONObject traveler;
		if(leadTraveler.trim().equals("true"))
			traveler = (JSONObject) travelers.get(0);
		else
			traveler = (JSONObject) travelers.get(1);
		if(email!=null)
			traveler.put("email", email);
		traveler.put("leadTraveler", leadTraveler.trim());
		requestBody = object.toJSONString();
		System.out.println("==============After inserting email & leadTraveler===========\n" + requestBody);
		
		updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		
		booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);	
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without phone key - leadTraveler=true 
	 * Empty phone - leadTraveler=true
	 * Invalid phone - leadTraveler=true (077123542a)  
	 * Phone with spaces - leadTraveler=true (603 345 9039)
	 * Without phone key - leadTraveler=false 
	 * Empty phone - leadTraveler=false
	 * Invalid phone - leadTraveler=false (+94771235423a)
	 * Phone with spaces - leadTraveler=false (603 345 9039)
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC009_UpdateTravelerDetails")
	public void tc_API_009_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String phone, String leadTraveler, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
	
//		Update JSON with leadTraveler
		String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		JSONArray travelers = (JSONArray) object.get("travelers");
		JSONObject traveler;
		if(leadTraveler.trim().equals("true"))
			traveler = (JSONObject) travelers.get(0);
		else
			traveler = (JSONObject) travelers.get(1);
		if(phone!=null)
			traveler.put("phoneNumber", phone);
		traveler.put("leadTraveler", leadTraveler.trim());
		requestBody = object.toJSONString();
		System.out.println("==============After inserting phone & leadTraveler===========\n" + requestBody);
		
		updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		
		booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);	
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without country leadTraveler = true
	 * Empty country leadTraveler = true 
	 * Invalid country leadTraveler = true (IN6)
	 * Invalid country leadTraveler = true (I)
	 * Invalid country leadTraveler = true (INN)
	 * Invalid country leadTraveler = true (ZZ)
	 * Invalid country leadTraveler = true (94)
	 * Valid country invalid phone leadTraveler = true
	 * Valid country valid phone leadTraveler = true (no isd code)
	 * Valid country valid phone leadTraveler = true (US isd code)
	 * Valid country valid phone leadTraveler = true (JP isd code)
	 * Without country leadTraveler = false   
	 * Empty country leadTraveler = false
	 * Without phone & country leadTraveler = false 
	 * Empty phone & Without country leadTraveler = false
	 * Invalid country leadTraveler = false (IN6)
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC010_UpdateTravelerDetails")
	public void tc_API_010_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String countryCode, String phone, String leadTraveler, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
	
//		Update JSON with leadTraveler
		String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		JSONArray travelers = (JSONArray) object.get("travelers");
		JSONObject traveler;
		if(leadTraveler.trim().equals("true"))
			traveler = (JSONObject) travelers.get(0);
		else
			traveler = (JSONObject) travelers.get(1);
		if(countryCode!=null)
			traveler.put("countryISOCode", countryCode);
		if(phone!=null)
			traveler.put("phoneNumber", phone);
		traveler.put("leadTraveler", leadTraveler.trim());
		requestBody = object.toJSONString();
		
		if(countryCode!=null || phone!=null)
			System.out.println("==============After inserting Country/Phone & leadTraveler===========\n" + requestBody);
		
		updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		
		booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);	
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without date of birth leadTraveler = true
	 * Empty date of birth leadTraveler = true 
	 * Future date of birth leadTraveler = true (2019-09-08)
	 * Invalid date of birth leadTraveler = true (08-09-1990)
	 * Without date of birth leadTraveler = false   
	 * Empty date of birth leadTraveler = false
	 * Invalid date of birth leadTraveler = false (09-08-1990)
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC011_UpdateTravelerDetails")
	public void tc_API_011_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String dateOfBirth, String leadTraveler, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
	
//		Update JSON with leadTraveler
		String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		JSONArray travelers = (JSONArray) object.get("travelers");
		JSONObject traveler;
		if(leadTraveler.trim().equals("true"))
			traveler = (JSONObject) travelers.get(0);
		else
			traveler = (JSONObject) travelers.get(1);
		if(dateOfBirth!=null)
			traveler.put("dateOfBirth", dateOfBirth);
		traveler.put("leadTraveler", leadTraveler.trim());
		requestBody = object.toJSONString();
		
		System.out.println("==============After inserting Date of Birth & leadTraveler===========\n" + requestBody);
		
		updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		
		booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);	
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without ageBandId key leadTraveler = true
	 * Empty ageBandId  leadTraveler = true 
	 * Invalid ageBandId  leadTraveler = true (ageBandId = 7) 
	 * Invalid ageBandId  leadTraveler = true (ageBandId = -1)
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC012_UpdateTravelerDetails")
	public void tc_API_012_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateTravelerDetailsJSON,
			String ageBandId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
	
//		Update JSON with leadTraveler
		String requestJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		JSONArray travelers = (JSONArray) object.get("travelers");
		JSONObject traveler;
		traveler = (JSONObject) travelers.get(0);
		if(ageBandId!=null){
			traveler.put("ageBandId", ageBandId);			
		}	
		requestBody = object.toJSONString();
		if(ageBandId!=null){
			System.out.println("==============After inserting ageBandId===========\n" + requestBody);
		}
		
		updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
		
		booking.requestUpdateTravelerDetailsDynamic(currentTestName, endPoint, partnerId, requestBody, updateTravelerDetailsJSON);	
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without leadTtraveler key
	 * Empty leadTraveler 
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC013_UpdateTravelerDetails")
	public void tc_API_013_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String updateTravelerDetailsJSON, String status, String message) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);	
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);		
	}
	
	
	/**
	 * Empty travelers list
	 * Travelers less than minimum
	 * Travelers more than supported
	 * Max head count check ageband 
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC014_UpdateTravelerDetails")
	public void tc_API_014_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String updateTravelerDetailsJSON, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);	
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * ADULT leadTraveler
	 * SENIOR leadTraveler
	 * CHILD leadTraveler
	 * YOUTH leadTraveler
	 * INFANT leadTraveler 
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC015_UpdateTravelerDetails")
	public void tc_API_015_UpdateTravelerDetailsUsingPutMethod(String endPoint, String partnerId, String createCartJSON,
			String updateTravelerDetailsJSON, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
//		Create shopping cart		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);

//		Update traveler details	
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);	
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
}
