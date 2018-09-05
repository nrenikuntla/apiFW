package com.testsuite.united.Booking;


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
	import com.api.category.UnitedBooking;
	import com.base.BaseSetup;
	import com.datamanager.ConfigManager;
	import com.datamanager.ExcelManager;
	import com.datamanager.JSONManager;
	import com.testng.Assert;
	import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;
import com.testsuite.dataprovider.UnitedUnitTests_TestData_Provider;

	public class UpdateTravelerDetails extends BaseSetup {
		
//		Declaration of respective API Parts instances
		ExcelManager excel_Manager;
		UnitedBooking booking;
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
			booking = new UnitedBooking();
			currentTestName = method.getName();
			
		}
		
		/**
		 * @throws ParseException 
		 * @throws IOException 
		 * @throws FileNotFoundException 
		 * 
		 */
		@Test(dataProviderClass=UnitedUnitTests_TestData_Provider.class, dataProvider="TC001_UpdateTravelerDetails")
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
	}	


