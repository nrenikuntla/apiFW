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
	import org.testng.Assert;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Test;

	import com.api.category.UnitedBooking;
	import com.base.BaseSetup;
	import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitedUnitTests_TestData_Provider;


	public class UpdateBookingAnswers extends BaseSetup {
		
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
		@Test(dataProviderClass=UnitedUnitTests_TestData_Provider.class, dataProvider="TC001_UpdateBookingAnswers")
		public void tc_API_001_UpdateBookingAnswersUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateAnswersJSON) throws ParseException, FileNotFoundException, IOException {
			
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
				
		//	Update booking answers		
			
			endPoint = "/bookings/carts/"+cartId+"/bookinganswers";
			response = booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateAnswersJSON);	
			
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(responseString);
			
			Object actualBookerDetails = (Object)object.get("bookerDetails");
			
			JsonArray = (JSONArray) object.get("bookingOptions");
			
			Object actualTravelerDetails = (Object)((JSONObject)JsonArray.get(0)).get("traverlerDetails");
			
			
			JsonArray = (JSONArray) ((JSONObject)JsonArray.get(0)).get("bookingQuestions");
			
			System.out.println("---- No of JSONs in the array is " + JsonArray.size());
			
			String actualBookingAnswer = (String)((JSONObject)((JSONObject)JsonArray.get(0)).get("bookingAnswer")).get("answer");
			String actualBookingQuestionId = (String)((JSONObject)JsonArray.get(0)).get("questionId");
			
			updateAnswersJSON = System.getProperty("user.dir") + updateAnswersJSON;
			object = (JSONObject) parser.parse(new FileReader(new File(updateAnswersJSON)));
			JsonArray = (JSONArray)object.get("questionAnswers");
			
			String expectedBookingAnswer = (String)((JSONObject)JsonArray.get(0)).get("answer");
			String expectedBookingQuestionId = (String)((JSONObject)JsonArray.get(0)).get("bookingQuestionId");
			
			System.out.println("----------------- expectedBookingAnswer is "+expectedBookingAnswer);
			System.out.println("----------------- actualBookingAnswer is "+actualBookingAnswer);
			System.out.println("----------------- expectedBookingQuestionId is "+expectedBookingQuestionId);
			System.out.println("----------------- actualBookingQuestionId is "+actualBookingQuestionId);
			
			Assert.assertEquals(actualBookingAnswer, expectedBookingAnswer, "Booking Answer has not updated successfully. i.e actual booking answer "+actualBookingAnswer+" is not matching with expected booking answer "+expectedBookingAnswer);
			Assert.assertEquals(actualBookingQuestionId, expectedBookingQuestionId, "Expected booking question id "+expectedBookingQuestionId+" is not matching with actual booking question id "+actualBookingQuestionId);
			Assert.assertTrue(actualBookerDetails == null, "Actual Booker Details is not null, hence failed");
			Assert.assertTrue(actualTravelerDetails == null, "Actual Traveller Details is not null, hence failed");
			System.out.println("Booker Details and Traveler Details are null");
		}
	}	

