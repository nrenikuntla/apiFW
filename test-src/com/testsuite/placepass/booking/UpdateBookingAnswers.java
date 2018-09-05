package com.testsuite.placepass.booking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class UpdateBookingAnswers extends BaseSetup {
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_UpdateBookingAnswers")
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
	
	
	/**
	 * Without productId 
	 * Empty productId 
	 * Invalid productId (abc) 
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_UpdateBookingAnswers")
	public void tc_API_002_UpdateBookingAnswersUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateAnswersJSON,
			String productId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
	
//		Get Booking Questions	
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
			
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray questionsArray = (JSONArray) object.get("bookingQuestions");
			
		System.out.println("---- No of booking questions is "+questionsArray.size());
		
	//	Update booking answers		
		endPoint = "/bookings/carts/"+cartId+"/bookinganswers";			
		
		if(productId!=null){
	//		Update JSON with productId
			String requestJSON = System.getProperty("user.dir") + updateAnswersJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			JSONArray questionAnswersArray = (JSONArray)object.get("questionAnswers");
			
			Iterator<JSONObject> i = questionAnswersArray.iterator();
			while (i.hasNext())
			{
				JSONObject questionAnswer = i.next();
				questionAnswer.put("productId",productId);
			}
			requestBody = object.toJSONString();
			System.out.println("==============After inserting productId===========\n" + requestBody);
			
			updateAnswersJSON = System.getProperty("user.dir") + updateAnswersJSON;
			
			booking.requestUpdateBookingAnswersDynamic(currentTestName, endPoint, partnerId, requestBody, updateAnswersJSON);
			
		}
		else{			
			booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateAnswersJSON);
		}
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without productOptionId 
	 * Empty productOptionId 
	 * Invalid productId (abc)
	 * ProductId & ProductOptionId mismatch
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_UpdateBookingAnswers")
	public void tc_API_003_UpdateBookingAnswersUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateAnswersJSON,
			String productOptionId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
	
//		Get Booking Questions	
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
			
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray questionsArray = (JSONArray) object.get("bookingQuestions");
			
		System.out.println("---- No of booking questions is "+questionsArray.size());
		
	//	Update booking answers		
		endPoint = "/bookings/carts/"+cartId+"/bookinganswers";			
		
		if(productOptionId!=null){
	//		Update JSON with productOptionId
			String requestJSON = System.getProperty("user.dir") + updateAnswersJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			JSONArray questionAnswersArray = (JSONArray)object.get("questionAnswers");
			
			Iterator<JSONObject> i = questionAnswersArray.iterator();
			while (i.hasNext())
			{
				JSONObject questionAnswer = i.next();
				questionAnswer.put("productOptionId",productOptionId);
			}
			requestBody = object.toJSONString();
			System.out.println("==============After inserting productOptionId===========\n" + requestBody);
			
			updateAnswersJSON = System.getProperty("user.dir") + updateAnswersJSON;
			
			booking.requestUpdateBookingAnswersDynamic(currentTestName, endPoint, partnerId, requestBody, updateAnswersJSON);
			
		}
		else{			
			booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateAnswersJSON);
		}
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without questionId 
	 * Empty questionId 
	 * Invalid questionId (6)
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_UpdateBookingAnswers")
	public void tc_API_004_UpdateBookingAnswersUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateAnswersJSON,
			String bookingQuestionId, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
	
//		Get Booking Questions	
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
			
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray questionsArray = (JSONArray) object.get("bookingQuestions");
			
		System.out.println("---- No of booking questions is "+questionsArray.size());
		
	//	Update booking answers		
		endPoint = "/bookings/carts/"+cartId+"/bookinganswers";			
		
		if(bookingQuestionId!=null){
	//		Update JSON with bookingQuestionId
			String requestJSON = System.getProperty("user.dir") + updateAnswersJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			object = (JSONObject) parser.parse(requestBody);
			
			JSONArray questionAnswersArray = (JSONArray)object.get("questionAnswers");
			
			Iterator<JSONObject> i = questionAnswersArray.iterator();
			while (i.hasNext())
			{
				JSONObject questionAnswer = i.next();
				questionAnswer.put("bookingQuestionId",bookingQuestionId);
			}
			requestBody = object.toJSONString();
			System.out.println("==============After inserting bookingQuestionId===========\n" + requestBody);
			
			updateAnswersJSON = System.getProperty("user.dir") + updateAnswersJSON;
			
			booking.requestUpdateBookingAnswersDynamic(currentTestName, endPoint, partnerId, requestBody, updateAnswersJSON);
			
		}
		else{			
			booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateAnswersJSON);
		}
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	/**
	 * Without answer 
	 * Empty answer 
	 * 4 Questions 3 Answers
	 * 4 Questions 4 Answers
	 * Product with no booking questions
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_UpdateBookingAnswers")
	public void tc_API_005_UpdateBookingAnswersUsingPutMethod(String endPoint, String partnerId, String createCartJSON, String updateAnswersJSON,
			String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
	
//		Get Booking Questions	
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
		
//		Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
			
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray questionsArray = (JSONArray) object.get("bookingQuestions");
			
		System.out.println("---- No of booking questions is "+questionsArray.size());
		
	//	Update booking answers		
		endPoint = "/bookings/carts/"+cartId+"/bookinganswers";			
		booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateAnswersJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
	}

}
