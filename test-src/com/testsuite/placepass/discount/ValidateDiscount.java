package com.testsuite.placepass.discount;

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
import com.api.category.Discount;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class ValidateDiscount extends BaseSetup {
	
//	Declaration of respective API Parts instances
	Booking booking;
	Discount discount;
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
		discount = new Discount();
		currentTestName = method.getName();
	}
	
	
	/**
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_ValidateDiscount")
	public void tc_API_001_ValidateDiscountUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String updateBookingAnswersJSON, String updateTravelerDetailsJSON, String validateCartJSON,	String validateDiscountJSON, String discountCode,
			String expired, String discountType, String discountValue, String minSpend, String maxSpend, String minDiscount, String maxDiscount,
			String status, String message, String ppcode, String ppmessage, String env) throws ParseException, FileNotFoundException, IOException {
		
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON, env);
		
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("cartId is - "+cartId);			
		
	//	Update Booker Details
		endPoint = "/bookings/carts/"+cartId+"/bookerdetails";
		
		response = booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON, env);
		
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
			
	//	Get Booking Questions
		
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId, env);
		
//		Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(responseString);
			JSONArray questionsArray = (JSONArray) object.get("bookingQuestions");
			
			System.out.println("---- No of booking questions is "+questionsArray.size());
			
//		*************************************************************************
		if(questionsArray.size()>0){	
			booking.verifyNoOfReturnedBookingQuestions(questionsArray);
			
			String bookingAnswer = (String) ((JSONObject)(questionsArray.get(0))).get("bookingAnswer");
			
			System.out.println("Value of bookingAnswer is " + bookingAnswer);
			
			booking.verifyBookingAnswer(bookingAnswer);			
			
			//	Update Booking Answers
			endPoint = "/bookings/carts/"+cartId+"/bookinganswers";
			
			response = booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateBookingAnswersJSON, env);
			
			//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			object = (JSONObject) parser.parse(responseString);
			
			questionsArray = (JSONArray) object.get("bookingOptions");
			
			questionsArray = (JSONArray) ((JSONObject)questionsArray.get(0)).get("bookingQuestions");
			
			System.out.println("---- No of JSONs in the array is " + questionsArray.size());
			
			String actualBookingAnswer = (String)((JSONObject)((JSONObject)questionsArray.get(0)).get("bookingAnswer")).get("answer");
			String actualBookingQuestionId = (String)((JSONObject)questionsArray.get(0)).get("questionId");
			
			updateBookingAnswersJSON = System.getProperty("user.dir") + updateBookingAnswersJSON;
			object = (JSONObject) parser.parse(new FileReader(new File(updateBookingAnswersJSON)));
			questionsArray = (JSONArray)object.get("questionAnswers");
			
			String expectedBookingAnswer = (String)((JSONObject)questionsArray.get(0)).get("answer");
			String expectedBookingQuestionId = (String)((JSONObject)questionsArray.get(0)).get("bookingQuestionId");
			
			System.out.println("----------------- expectedBookingAnswer is "+expectedBookingAnswer);
			System.out.println("----------------- actualBookingAnswer is "+actualBookingAnswer);
			System.out.println("----------------- expectedBookingQuestionId is "+expectedBookingQuestionId);
			System.out.println("----------------- actualBookingQuestionId is "+actualBookingQuestionId);
			
			Assert.assertEquals(actualBookingAnswer, expectedBookingAnswer, "Booking Answer has not updated successfully. i.e actual booking answer "+actualBookingAnswer+" is not matching with expected booking answer "+expectedBookingAnswer);
			Assert.assertEquals(actualBookingQuestionId, expectedBookingQuestionId, "Expected booking question id "+expectedBookingQuestionId+" is not matching with actual booking question id "+actualBookingQuestionId);
		}
//			*************************************************************************
		//	Update Traveler Details
		
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		response = booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON, env);
		
//		Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(responseString);
			
			Object actualBookerDetails = (Object)object.get("bookerDetails");
			
			questionsArray = (JSONArray) object.get("bookingOptions");
			questionsArray = (JSONArray) ((JSONObject)questionsArray.get(0)).get("traverlerDetails");
			
			actualTitle = (String)((JSONObject)questionsArray.get(0)).get("title");
			actualFirstName = (String)((JSONObject)questionsArray.get(0)).get("firstName");
			actualLastName = (String)((JSONObject)questionsArray.get(0)).get("lastName");
			actualDateOfBirth = (String)((JSONObject)questionsArray.get(0)).get("dateOfBirth");
			actualEmail = (String)((JSONObject)questionsArray.get(0)).get("email");
			actualPhoneNumber = (String)((JSONObject)questionsArray.get(0)).get("phoneNumber");
			actualCountryISOCode = (String)((JSONObject)questionsArray.get(0)).get("countryISOCode");
			long actualAgeBandId = (long) ((JSONObject)questionsArray.get(0)).get("ageBandId");
			boolean actualLeadTraveler = (boolean)((JSONObject)questionsArray.get(0)).get("leadTraveler");
			
			
			updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
			object = (JSONObject) parser.parse(new FileReader(new File(updateTravelerDetailsJSON)));
			questionsArray = (JSONArray)object.get("travelers");
			
			expectedTitle = (String)((JSONObject)questionsArray.get(0)).get("title");
			expectedFirstName = (String)((JSONObject)questionsArray.get(0)).get("firstName");
			expectedLastName = (String)((JSONObject)questionsArray.get(0)).get("lastName");
			expectedDateOfBirth = (String)((JSONObject)questionsArray.get(0)).get("dateOfBirth");
			expectedEmail = (String)((JSONObject)questionsArray.get(0)).get("email");
			expectedPhoneNumber = (String)((JSONObject)questionsArray.get(0)).get("phoneNumber");
			expectedCountryISOCode = (String)((JSONObject)questionsArray.get(0)).get("countryISOCode");
			long expectedAgeBandId = (long)((JSONObject)questionsArray.get(0)).get("ageBandId");
			boolean expectedLeadTraveler = (boolean)((JSONObject)questionsArray.get(0)).get("leadTraveler");
			
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
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate"; 
			
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON, env);
			
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
			Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
			System.out.println("Cart ["+cartId+"] successfully validated");
	
	//	Validate Discount
		endPoint = "/api/ui/discounts/"+cartId+"/validate";
		
	//	Update JSON with discount code
		String requestJSON = System.getProperty("user.dir") + validateDiscountJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(requestBody);
		
		JSONArray discounts = (JSONArray) object.get("discounts");
		JSONObject discountCodeObj = (JSONObject) discounts.get(0);
		discountCodeObj.put("discountCode", discountCode);
		requestBody = object.toJSONString();
		System.out.println("==============After inserting discount code===========\n" + requestBody);
		
		validateDiscountJSON = System.getProperty("user.dir") + validateDiscountJSON;
		
		discount.requestValidateDiscount(currentTestName, endPoint, partnerId, requestBody, validateDiscountJSON, env);
		
	//	Verify response code and message
		discount.verifyStatusCode(status);
		discount.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			discount.verifyPPCodePPMessage(ppcode,ppmessage);
		else
			discount.verifyDiscountCode(discountCode,expired,discountType,discountValue,minSpend,maxSpend,minDiscount,maxDiscount,status,message,ppcode,ppmessage);
		
	}
	
	
	/**
	 * 
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_ValidateDiscount")
	public void tc_API_002_ValidateDiscountUsingPostMethod(String endPoint, String partnerId, String createCartJSON,String validateDiscountJSON, String discountCode,
			String multiplierMAR, String multiplierSPG, String status, String message, String ppcode, String ppmessage, String env) throws ParseException{
		
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON, env);
		
		//	Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			String cartId = (String) object.get("cartId");
			
			System.out.println("cartId is - "+cartId);
			
			
		//	Validate Discount
			endPoint = "/api/ui/discounts/"+cartId+"/validate";
			
		//	Update JSON with discount code
			String requestJSON = System.getProperty("user.dir") + validateDiscountJSON;
			String requestBody = jsonObject.readJSONContentToString(requestJSON);
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(requestBody);
			
			JSONArray discounts = (JSONArray) object.get("discounts");
			JSONObject discountCodeObj = (JSONObject) discounts.get(0);
			discountCodeObj.put("discountCode", discountCode);
			requestBody = object.toJSONString();
			System.out.println("==============After inserting discount code===========\n" + requestBody);
			
			validateDiscountJSON = System.getProperty("user.dir") + validateDiscountJSON;
			
			response = discount.requestValidateDiscount(currentTestName, endPoint, partnerId, requestBody, validateDiscountJSON, env);
			
		//	Verify response code and message
			discount.verifyStatusCode(status);
			discount.verifyStatusMessage(message);
			
			if(ppcode!=null && ppmessage!=null){
				discount.verifyPPCodePPMessage(ppcode,ppmessage);
			}	
			else{
				responseString = response.get("response body");
				object = (JSONObject) parser.parse(responseString);
				
				JSONObject priceSummaryObject = (JSONObject) object.get("priceSummary");
				
				int totalPrice = (int)Math.ceil((double)priceSummaryObject.get("totalPrice"));
				
				int rewardPointsMAR = totalPrice * Integer.valueOf(multiplierMAR);
				
				int rewardPointsSPG = totalPrice * Integer.valueOf(multiplierSPG);
				
				JSONArray loyalty = (JSONArray) object.get("loyalty");
				
				Iterator<JSONObject> i = loyalty.iterator();
				
				while(i.hasNext()){
					
					JSONObject loyaltyObject = i.next();
					Object loyaltyId = loyaltyObject.get("loyaltyProgramId");
					Object loyaltyPoints = loyaltyObject.get("loyaltyPoints");
					
					if(loyaltyId.equals("MAR")){
						Assert.assertEquals(Integer.parseInt(loyaltyPoints.toString()),rewardPointsMAR,"Obtained reward points "+loyaltyPoints+" for "+loyaltyId+" loyalty program did not match expected "+rewardPointsMAR);						
					}else{
						Assert.assertEquals(Integer.parseInt(loyaltyPoints.toString()),rewardPointsSPG,"Obtained reward points "+loyaltyPoints+" for "+loyaltyId+" loyalty program did not match expected "+rewardPointsSPG);						
					}
					System.out.println("["+discountCode+"]"+" Obtained reward points "+loyaltyPoints+" for "+loyaltyId+" loyalty program matched expectation");					
				}				
			}	
	}		

}