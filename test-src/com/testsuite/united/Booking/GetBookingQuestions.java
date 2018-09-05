package com.testsuite.united.Booking;


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

	import com.testsuite.dataprovider.UnitedUnitTests_TestData_Provider;


	public class GetBookingQuestions extends BaseSetup {
		
//		Declaration of respective API Parts instances
		ExcelManager excel_Manager;
		UnitedBooking booking;
		public ConfigManager api;
		
		public String currentTestName;
		List<String> headers = new ArrayList<String>();
		
		/**
		 * Purpose - Initializes the API parts instances
		 */
		@BeforeMethod(alwaysRun = true)
		public void SetUp(Method method) {
			
			api = new ConfigManager("Api");
			booking = new UnitedBooking();
			currentTestName = method.getName();
			
		}
		
		/**
		 * @throws ParseException 
		 * 
		 */
		@Test(dataProviderClass=UnitedUnitTests_TestData_Provider.class, dataProvider="TC001_GetBookingQuestions")
		public void tc_API_001_GetBookingQuestionsUsingGetMethod(String endPoint, String partnerId, String createCartJSON) throws ParseException {
			
			HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
			
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("cartId is - "+cartId);
			
		//	Get Booking Questions		
			endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
			response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
							
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(responseString);
			JSONArray JsonArray = (JSONArray) object.get("bookingQuestions");
			
			System.out.println("---- No of JSONs in the array is " + JsonArray.size());
			
			booking.verifyNoOfReturnedBookingQuestions(JsonArray);
			
			String bookingAnswer = (String) ((JSONObject)(JsonArray.get(0))).get("bookingAnswer");
			
			System.out.println("Value of bookingAnswer is " + bookingAnswer);
			
			booking.verifyBookingAnswer(bookingAnswer);
			
			
		}
	}




