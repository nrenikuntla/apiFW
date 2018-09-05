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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class ProcessPaymentAndCreateBooking extends BaseSetup {
	
//	Declaration of respective API Parts instances
	Booking booking;
	private APIHelpers apiHelpers;
	JSONManager jsonObject;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		apiHelpers = new APIHelpers();
		jsonObject = new JSONManager();
		booking = new Booking();
		currentTestName = method.getName();
		
	}
	
		
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_ProcessPaymentAndCreateBooking")
	public void tc_API_001_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON, String updateBookingAnswersJSON, String updateTravelerDetailsJSON, String validateCartJSON, String createBookingJSON) throws ParseException, IOException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
			String bookerEmail = (String)bookerObject.get("email");
			String expectedPhoneNumber = (String)bookerObject.get("phoneNumber");
			String expectedCountryISOCode = (String)bookerObject.get("countryISOCode");
			String expectedDateOfBirth = (String)bookerObject.get("dateOfBirth");
			
			System.out.println("expectedTitle is \""+expectedTitle+"\", whereas actualTitle is \""+actualTitle+"\"");
			System.out.println("expectedFirstName is \""+expectedFirstName+"\", whereas actualFirstName is \""+actualFirstName+"\"");
			System.out.println("expectedLastName is \""+expectedLastName+"\", whereas actualLastName is \""+actualLastName+"\"");
			System.out.println("expectedDateOfBirth is \""+expectedDateOfBirth+"\", whereas actualDateOfBirth is \""+actualDateOfBirth+"\"");
			System.out.println("expectedEmail is \""+bookerEmail+"\", whereas actualEmail is \""+actualEmail+"\"");
			System.out.println("expectedPhoneNumber is \""+expectedPhoneNumber+"\", whereas actualPhoneNumber is \""+actualPhoneNumber+"\"");
			System.out.println("expectedCountryISOCode is \""+expectedCountryISOCode+"\", whereas actualCountryISOCode is \""+actualCountryISOCode+"\"");
			
			Assert.assertEquals(actualTitle, expectedTitle, "Actual Title in response "+actualTitle+" is not matching with the expected title "+expectedTitle);
			Assert.assertEquals(actualFirstName, expectedFirstName, "Actual Title in response "+actualFirstName+" is not matching with the expected title "+expectedFirstName);
			Assert.assertEquals(actualLastName, expectedLastName, "Actual Title in response "+actualLastName+" is not matching with the expected title "+expectedLastName);
			Assert.assertEquals(actualDateOfBirth, expectedDateOfBirth, "Actual Title in response "+actualDateOfBirth+" is not matching with the expected title "+expectedDateOfBirth);
			Assert.assertEquals(actualEmail, bookerEmail, "Actual Title in response "+actualEmail+" is not matching with the expected title "+bookerEmail);
			Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber, "Actual Title in response "+actualPhoneNumber+" is not matching with the expected title "+expectedPhoneNumber);
			Assert.assertEquals(actualCountryISOCode, expectedCountryISOCode, "Actual Title in response "+actualCountryISOCode+" is not matching with the expected title "+expectedCountryISOCode);
			
	//	Get Booking Questions
		
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
		
//		Verify response code and message
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
			
	//	Update Booking Answers
		endPoint = "/bookings/carts/"+cartId+"/bookinganswers";
		
		response = booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateBookingAnswersJSON);
		
		//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		object = (JSONObject) parser.parse(responseString);
		
		//Object actualBookerDetails = (Object)object.get("bookerDetails");
		
		JsonArray = (JSONArray) object.get("bookingOptions");
		
		//Object actualTravelerDetails = (Object)((JSONObject)JsonArray.get(0)).get("traverlerDetails");
		
		JsonArray = (JSONArray) ((JSONObject)JsonArray.get(0)).get("bookingQuestions");
		
		System.out.println("---- No of JSONs in the array is " + JsonArray.size());
		
		String actualBookingAnswer = (String)((JSONObject)((JSONObject)JsonArray.get(0)).get("bookingAnswer")).get("answer");
		String actualBookingQuestionId = (String)((JSONObject)JsonArray.get(0)).get("questionId");
		
		updateBookingAnswersJSON = System.getProperty("user.dir") + updateBookingAnswersJSON;
		object = (JSONObject) parser.parse(new FileReader(new File(updateBookingAnswersJSON)));
		JsonArray = (JSONArray)object.get("questionAnswers");
		
		String expectedBookingAnswer = (String)((JSONObject)JsonArray.get(0)).get("answer");
		String expectedBookingQuestionId = (String)((JSONObject)JsonArray.get(0)).get("bookingQuestionId");
		
		System.out.println("----------------- expectedBookingAnswer is "+expectedBookingAnswer);
		System.out.println("----------------- actualBookingAnswer is "+actualBookingAnswer);
		System.out.println("----------------- expectedBookingQuestionId is "+expectedBookingQuestionId);
		System.out.println("----------------- actualBookingQuestionId is "+actualBookingQuestionId);
		
		Assert.assertEquals(actualBookingAnswer, expectedBookingAnswer, "Booking Answer has not updated successfully. i.e actual booking answer "+actualBookingAnswer+" is not matching with expected booking answer "+expectedBookingAnswer);
		Assert.assertEquals(actualBookingQuestionId, expectedBookingQuestionId, "Expected booking question id "+expectedBookingQuestionId+" is not matching with actual booking question id "+actualBookingQuestionId);
		
	//	Update Traveler Details
		
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		response = booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
//		Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(responseString);
			
			Object actualBookerDetails = (Object)object.get("bookerDetails");
			
			JsonArray = (JSONArray) object.get("bookingOptions");
			JsonArray = (JSONArray) ((JSONObject)JsonArray.get(0)).get("traverlerDetails");
			
			actualTitle = (String)((JSONObject)JsonArray.get(0)).get("title");
			actualFirstName = (String)((JSONObject)JsonArray.get(0)).get("firstName");
			actualLastName = (String)((JSONObject)JsonArray.get(0)).get("lastName");
			actualDateOfBirth = (String)((JSONObject)JsonArray.get(0)).get("dateOfBirth");
			actualEmail = (String)((JSONObject)JsonArray.get(0)).get("email");
			actualPhoneNumber = (String)((JSONObject)JsonArray.get(0)).get("phoneNumber");
			actualCountryISOCode = (String)((JSONObject)JsonArray.get(0)).get("countryISOCode");
			long actualAgeBandId = (long) ((JSONObject)JsonArray.get(0)).get("ageBandId");
			boolean actualLeadTraveler = (boolean)((JSONObject)JsonArray.get(0)).get("leadTraveler");
			
			
			updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
			object = (JSONObject) parser.parse(new FileReader(new File(updateTravelerDetailsJSON)));
			JsonArray = (JSONArray)object.get("travelers");
			
			expectedTitle = (String)((JSONObject)JsonArray.get(0)).get("title");
			expectedFirstName = (String)((JSONObject)JsonArray.get(0)).get("firstName");
			expectedLastName = (String)((JSONObject)JsonArray.get(0)).get("lastName");
			expectedDateOfBirth = (String)((JSONObject)JsonArray.get(0)).get("dateOfBirth");
			String expectedEmail = (String)((JSONObject)JsonArray.get(0)).get("email");
			expectedPhoneNumber = (String)((JSONObject)JsonArray.get(0)).get("phoneNumber");
			expectedCountryISOCode = (String)((JSONObject)JsonArray.get(0)).get("countryISOCode");
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
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
			
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
			
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
			Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
			System.out.println("Cart ["+cartId+"] successfully validated");
	
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
		
	//	Update JSON with current cart id & tokenizedNumber
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(requestBody);
		
		Object paymentObject = (Object)object.get("payment");
		String paymentJSON = paymentObject.toString();
		String current_TestName=currentTestName;
		String cardNumber="5555555555554444";
		String exp_month="10";
		String exp_year="20";
		String cvc="111";
		String address="98122";
		
		response=booking.requestForTokenizedNumber(current_TestName,cardNumber,exp_month,exp_year,cvc,address);
		responseString = response.get("response body");
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		String	tokenId =(String) object.get("id");
		
		
		paymentJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(paymentJSON, "tokenizedNumber", tokenId);
		requestBody = jsonObject.updateJSONValueInJsonKey(requestBody, "payment", paymentJSON);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyCreateBooking(bookerEmail);
		
	}
	
	
	/**
	 * Without tokenizedNumber
	 * Empty tokenizedNumber
	 * Invalid tokenizedNumber (tok_visa111)
	 * Visa Token (tok_visa)
	 * Token (tok_amex)
	 * Token (tok_mastercard)
 	 * Token (tok_chargeDeclinedProcessingError)
 	 * Token (tok_chargeDeclinedExpiredCard)
 	 * Token (tok_chargeDeclinedIncorrectCvc)
 	 * Token (tok_chargeDeclinedFraudulent)
 	 * Token (tok_chargeDeclined)
 	 * Token (tok_chargeCustomerFail)
 	 * Token (tok_cvcCheckFail)
 	 * Token (tok_avsZipFail)
 	 * Token (tok_avsFail)
 	 * Token (tok_riskLevelElevated)
 	 * Token (tok_avsUnchecked)
 	 * Token (tok_avsLine1Fail)
 	 * Token (tok_domesticPricing)
 	 * Token (tok_bypassPending)
 	 * Token (tok_createDispute)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_ProcessPaymentAndCreateBooking")
	public void tc_API_002_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON, String updateTravelerDetailsJSON,
			String validateCartJSON, String createBookingJSON, String tokenizedNumber, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id & tokenizedNumber
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		if(tokenizedNumber!=null){
			object = (JSONObject) parser.parse(requestBody);
			
			Object paymentObject = (Object)object.get("payment");
			String paymentJSON = paymentObject.toString();
			paymentJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(paymentJSON, "tokenizedNumber", tokenizedNumber);
			requestBody = jsonObject.updateJSONValueInJsonKey(requestBody, "payment", paymentJSON);
		}
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
		else
			booking.verifyCreateBooking();	
	}
	
	
	/**
	 * With loyalty details MARRIOTT
	 * With loyalty details SPG
	 * loyaltyAccount = null
	 * Empty loyaltyAccount
	 * Without loyaltyAccountId
	 * Empty loyaltyAccountId
	 * loyaltyAccountId = null
	 * Without loyaltyProgramId
	 * Empty loyaltyProgramId
	 * loyaltyProgramId = null
	 * Without extendedAttributes
	 * Empty extendedAttributes
	 * extendedAttributes = null
	 * Empty LOY_POINTS
	 * Invalid LOY_POINTS (ab)
	 * LOY_POINTS = null
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_ProcessPaymentAndCreateBooking")
	public void tc_API_003_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON, String updateTravelerDetailsJSON,
			String validateCartJSON, String createBookingJSON, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
		else
			booking.verifyCreateBooking();	
	}
	
	
	/**
	 * Verify loyalty details are hashed 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_ProcessPaymentAndCreateBooking")
	public void tc_API_004_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String updateTravelerDetailsJSON, String validateCartJSON, String createBookingJSON, String customerId, String loyaltyId) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		Object bookingId = booking.verifyCreateBooking();		
		
	//	Get Single Booking for Customer
		endPoint = "/bookings/customers/"+customerId+"/bookings/"+bookingId.toString();
			
		booking.requestGetSingleBookingForCustomer(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Verify loyalty details are hashed
		booking.verifyLoyaltyDetails(loyaltyId);
	}
	
	
	/**
	 * Verify product booking with multiple questions
	 * Booker email has capital letters 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_ProcessPaymentAndCreateBooking")
	public void tc_API_005_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String updateAnswersJSON, String updateTravelerDetailsJSON, String validateCartJSON, String createBookingJSON) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Get Booking Questions	
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
			
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray questionsArray = (JSONArray) object.get("bookingQuestions");
			
		System.out.println("---- No of booking questions is "+questionsArray.size());
		
	//	Update multiple booking answers		
		endPoint = "/bookings/carts/"+cartId+"/bookinganswers";			
		booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateAnswersJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");		
		
	//	Verify booking succeeded
		booking.verifyCreateBooking();
	}
	
	
	/**
	 * isHotelPickUpEligible: true
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC006_ProcessPaymentAndCreateBooking")
	public void tc_API_006_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String addBookingOptionsToCartJSON, String updateBookerDetailsJSON,
			 String updateTravelerDetailsJSON, String validateCartJSON, String createBookingJSON, String customerId) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);	
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("cartId is - "+cartId);
	
	//	Add Booking Options		
		endPoint = "/bookings/carts/"+cartId+"/bookingoptions";	
	
		booking.requestAddBookingOptionsToCart(currentTestName, endPoint, partnerId, addBookingOptionsToCartJSON);
				
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Update Booker Details
		endPoint = "/bookings/carts/"+cartId+"/bookerdetails";		
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Get Booking Questions	
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
			
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray questionsArray = (JSONArray) object.get("bookingQuestions");
			
		System.out.println("---- No of booking questions is "+questionsArray.size());
		
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");		
		
		Object bookingId = booking.verifyCreateBooking();		
		
	//	Get Single Booking for Customer
		endPoint = "/bookings/customers/"+customerId+"/bookings/"+bookingId.toString();
			
		response=booking.requestGetSingleBookingForCustomer(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		
		responseString = response.get("response body");
		
		parser = new JSONParser();
		JSONObject object1 = (JSONObject) parser.parse(responseString);
		JSONObject bookingJSON=(JSONObject) object1.get("booking");
		JSONArray bookingOptions =(JSONArray) bookingJSON.get("bookingOptions");
		JSONObject product=(JSONObject) bookingOptions.get(0);
		String productId=(String) product.get("productId");
		String hotelpickup =(String) bookingJSON.get("isHotelPickUpEligible");
		if(hotelpickup!=null)
		{
//			Verify booking is hotel pickup eligible
		
			booking.verifyHotelPickup();
		
		}
		else
		{
			System.out.println(" No Hotel Pickup is Eligible for this productId - "+productId);
		}
	}
	
	
	
	/**
	 * LanguageOptionCode = en
	 * LanguageOptionCode = fr
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC007_ProcessPaymentAndCreateBooking")
	public void tc_API_007_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON, String updateTravelerDetailsJSON,
			String updateLanguageOptionJSON, String validateCartJSON, String createBookingJSON, String productOptionId, String customerId, String language) throws ParseException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Update language option
		endPoint = "/bookings/carts/"+cartId+"/bookingoptions/"+productOptionId+"/languageoptioncode";
		
		booking.requestUpdateLanguageOptionCode(currentTestName, endPoint, partnerId, updateLanguageOptionJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		Object bookingId = booking.verifyCreateBooking();		
		
	//	Get Single Booking for Customer
		endPoint = "/bookings/customers/"+customerId+"/bookings/"+bookingId.toString();
			
		booking.requestGetSingleBookingForCustomer(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Verify language option code matches updated code
		booking.verifyLanguageOptionCode(language);
	}
	
	
	/**
	 * Without discount
	 * Discount = null
	 * Discount for 1 person
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC008_ProcessPaymentAndCreateBooking")
	public void tc_API_008_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String updateTravelerDetailsJSON, String validateCartJSON, String createBookingJSON, String customerId, String discountCode, String env) throws ParseException{
		
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
		booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON, env);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON, env);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON, env);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON, env);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		Object bookingId = booking.verifyCreateBooking();		
		
	//	Get Single Booking for Customer
		endPoint = "/bookings/customers/"+customerId+"/bookings/"+bookingId.toString();
			
		booking.requestGetSingleBookingForCustomer(currentTestName, endPoint, partnerId, env);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
	//	Verify discount amount if applicable
		if(discountCode!=null)
			booking.verifyDiscountApplied();
		else
			System.out.println("Discount code not applied");
	}
	
	/**
	 * With discount code that fetches loyalty points
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC009_ProcessPaymentAndCreateBooking")
	public void tc_API_009_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String updateBookerDetailsJSON, String updateBookingAnswersJSON, String updateTravelerDetailsJSON, String validateCartJSON,
			String validateDiscountJSON, String createBookingJSON, String discountCode, String description) throws ParseException, IOException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
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
			String bookerEmail = (String)bookerObject.get("email");
			String expectedPhoneNumber = (String)bookerObject.get("phoneNumber");
			String expectedCountryISOCode = (String)bookerObject.get("countryISOCode");
			String expectedDateOfBirth = (String)bookerObject.get("dateOfBirth");
			
			System.out.println("expectedTitle is \""+expectedTitle+"\", whereas actualTitle is \""+actualTitle+"\"");
			System.out.println("expectedFirstName is \""+expectedFirstName+"\", whereas actualFirstName is \""+actualFirstName+"\"");
			System.out.println("expectedLastName is \""+expectedLastName+"\", whereas actualLastName is \""+actualLastName+"\"");
			System.out.println("expectedDateOfBirth is \""+expectedDateOfBirth+"\", whereas actualDateOfBirth is \""+actualDateOfBirth+"\"");
			System.out.println("expectedEmail is \""+bookerEmail+"\", whereas actualEmail is \""+actualEmail+"\"");
			System.out.println("expectedPhoneNumber is \""+expectedPhoneNumber+"\", whereas actualPhoneNumber is \""+actualPhoneNumber+"\"");
			System.out.println("expectedCountryISOCode is \""+expectedCountryISOCode+"\", whereas actualCountryISOCode is \""+actualCountryISOCode+"\"");
			
			Assert.assertEquals(actualTitle, expectedTitle, "Actual Title in response "+actualTitle+" is not matching with the expected title "+expectedTitle);
			Assert.assertEquals(actualFirstName, expectedFirstName, "Actual Title in response "+actualFirstName+" is not matching with the expected title "+expectedFirstName);
			Assert.assertEquals(actualLastName, expectedLastName, "Actual Title in response "+actualLastName+" is not matching with the expected title "+expectedLastName);
			Assert.assertEquals(actualDateOfBirth, expectedDateOfBirth, "Actual Title in response "+actualDateOfBirth+" is not matching with the expected title "+expectedDateOfBirth);
			Assert.assertEquals(actualEmail, bookerEmail, "Actual Title in response "+actualEmail+" is not matching with the expected title "+bookerEmail);
			Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber, "Actual Title in response "+actualPhoneNumber+" is not matching with the expected title "+expectedPhoneNumber);
			Assert.assertEquals(actualCountryISOCode, expectedCountryISOCode, "Actual Title in response "+actualCountryISOCode+" is not matching with the expected title "+expectedCountryISOCode);
			
	//	Get Booking Questions
		
		endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
		response = booking.requestGetBookingQuestions(currentTestName, endPoint, partnerId);
		
//		Verify response code and message
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
			
	//	Update Booking Answers
		endPoint = "/bookings/carts/"+cartId+"/bookinganswers";
		
		response = booking.requestUpdateBookingAnswers(currentTestName, endPoint, partnerId, updateBookingAnswersJSON);
		
		//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		object = (JSONObject) parser.parse(responseString);
		
		//Object actualBookerDetails = (Object)object.get("bookerDetails");
		
		JsonArray = (JSONArray) object.get("bookingOptions");
		
		//Object actualTravelerDetails = (Object)((JSONObject)JsonArray.get(0)).get("traverlerDetails");
		
		JsonArray = (JSONArray) ((JSONObject)JsonArray.get(0)).get("bookingQuestions");
		
		System.out.println("---- No of JSONs in the array is " + JsonArray.size());
		
		String actualBookingAnswer = (String)((JSONObject)((JSONObject)JsonArray.get(0)).get("bookingAnswer")).get("answer");
		String actualBookingQuestionId = (String)((JSONObject)JsonArray.get(0)).get("questionId");
		
		updateBookingAnswersJSON = System.getProperty("user.dir") + updateBookingAnswersJSON;
		object = (JSONObject) parser.parse(new FileReader(new File(updateBookingAnswersJSON)));
		JsonArray = (JSONArray)object.get("questionAnswers");
		
		String expectedBookingAnswer = (String)((JSONObject)JsonArray.get(0)).get("answer");
		String expectedBookingQuestionId = (String)((JSONObject)JsonArray.get(0)).get("bookingQuestionId");
		
		System.out.println("----------------- expectedBookingAnswer is "+expectedBookingAnswer);
		System.out.println("----------------- actualBookingAnswer is "+actualBookingAnswer);
		System.out.println("----------------- expectedBookingQuestionId is "+expectedBookingQuestionId);
		System.out.println("----------------- actualBookingQuestionId is "+actualBookingQuestionId);
		
		Assert.assertEquals(actualBookingAnswer, expectedBookingAnswer, "Booking Answer has not updated successfully. i.e actual booking answer "+actualBookingAnswer+" is not matching with expected booking answer "+expectedBookingAnswer);
		Assert.assertEquals(actualBookingQuestionId, expectedBookingQuestionId, "Expected booking question id "+expectedBookingQuestionId+" is not matching with actual booking question id "+actualBookingQuestionId);
		
	//	Update Traveler Details
		
		endPoint = "/bookings/carts/"+cartId+"/travelers";
		
		response = booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON);
		
//		Verify response code and message
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			parser = new JSONParser();
			object = (JSONObject) parser.parse(responseString);
			
			Object actualBookerDetails = (Object)object.get("bookerDetails");
			
			JsonArray = (JSONArray) object.get("bookingOptions");
			JsonArray = (JSONArray) ((JSONObject)JsonArray.get(0)).get("traverlerDetails");
			
			actualTitle = (String)((JSONObject)JsonArray.get(0)).get("title");
			actualFirstName = (String)((JSONObject)JsonArray.get(0)).get("firstName");
			actualLastName = (String)((JSONObject)JsonArray.get(0)).get("lastName");
			actualDateOfBirth = (String)((JSONObject)JsonArray.get(0)).get("dateOfBirth");
			actualEmail = (String)((JSONObject)JsonArray.get(0)).get("email");
			actualPhoneNumber = (String)((JSONObject)JsonArray.get(0)).get("phoneNumber");
			actualCountryISOCode = (String)((JSONObject)JsonArray.get(0)).get("countryISOCode");
			long actualAgeBandId = (long) ((JSONObject)JsonArray.get(0)).get("ageBandId");
			boolean actualLeadTraveler = (boolean)((JSONObject)JsonArray.get(0)).get("leadTraveler");
			
			
			updateTravelerDetailsJSON = System.getProperty("user.dir") + updateTravelerDetailsJSON;
			object = (JSONObject) parser.parse(new FileReader(new File(updateTravelerDetailsJSON)));
			JsonArray = (JSONArray)object.get("travelers");
			
			expectedTitle = (String)((JSONObject)JsonArray.get(0)).get("title");
			expectedFirstName = (String)((JSONObject)JsonArray.get(0)).get("firstName");
			expectedLastName = (String)((JSONObject)JsonArray.get(0)).get("lastName");
			expectedDateOfBirth = (String)((JSONObject)JsonArray.get(0)).get("dateOfBirth");
			String expectedEmail = (String)((JSONObject)JsonArray.get(0)).get("email");
			expectedPhoneNumber = (String)((JSONObject)JsonArray.get(0)).get("phoneNumber");
			expectedCountryISOCode = (String)((JSONObject)JsonArray.get(0)).get("countryISOCode");
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
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
			
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
			
			booking.verifyStatusCode("200");
			booking.verifyStatusMessage("OK");
			
			responseString = response.get("response body");
			
			List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
			Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
			System.out.println("Cart ["+cartId+"] successfully validated");
		
	
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
		
	//	Update JSON with current cart id & tokenizedNumber
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		requestBody = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(requestBody);
		
		Object paymentObject = (Object)object.get("payment");
		String paymentJSON = paymentObject.toString();
		String current_TestName=currentTestName;
		String cardNumber="5555555555554444";
		String exp_month="10";
		String exp_year="20";
		String cvc="111";
		String address="98122";
		
		response=booking.requestForTokenizedNumber(current_TestName,cardNumber,exp_month,exp_year,cvc,address);
		responseString = response.get("response body");
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		String	tokenId =(String) object.get("id");
		
		paymentJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(paymentJSON, "tokenizedNumber", tokenId);
		requestBody= jsonObject.updateJSONValueInJsonKey(requestBody, "payment", paymentJSON);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyCreateBooking(bookerEmail);	
		booking.verifyPointsApplied(description);
	}
	/**
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC011_ProcessPaymentAndCreateBooking")
	public void tc_API_011_ProcessPaymentAndCreateBookingUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON,
			String updateTravelerDetailsJSON, String validateCartJSON, String createBookingJSON, String cancelBookingJSON,String env) throws ParseException, FileNotFoundException, IOException{
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON,env);
		
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
		response = booking.requestUpdateBookerDetails(currentTestName, endPoint, partnerId, updateBookerDetailsJSON,env);
		
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
		String bookerEmail = (String)bookerObject.get("email");
		String expectedPhoneNumber = (String)bookerObject.get("phoneNumber");
		String expectedCountryISOCode = (String)bookerObject.get("countryISOCode");
		String expectedDateOfBirth = (String)bookerObject.get("dateOfBirth");
		
		System.out.println("expectedTitle is \""+expectedTitle+"\", whereas actualTitle is \""+actualTitle+"\"");
		System.out.println("expectedFirstName is \""+expectedFirstName+"\", whereas actualFirstName is \""+actualFirstName+"\"");
		System.out.println("expectedLastName is \""+expectedLastName+"\", whereas actualLastName is \""+actualLastName+"\"");
		System.out.println("expectedDateOfBirth is \""+expectedDateOfBirth+"\", whereas actualDateOfBirth is \""+actualDateOfBirth+"\"");
		System.out.println("expectedEmail is \""+bookerEmail+"\", whereas actualEmail is \""+actualEmail+"\"");
		System.out.println("expectedPhoneNumber is \""+expectedPhoneNumber+"\", whereas actualPhoneNumber is \""+actualPhoneNumber+"\"");
		System.out.println("expectedCountryISOCode is \""+expectedCountryISOCode+"\", whereas actualCountryISOCode is \""+actualCountryISOCode+"\"");
		
		Assert.assertEquals(actualTitle, expectedTitle, "Actual Title in response "+actualTitle+" is not matching with the expected title "+expectedTitle);
		Assert.assertEquals(actualFirstName, expectedFirstName, "Actual Title in response "+actualFirstName+" is not matching with the expected title "+expectedFirstName);
		Assert.assertEquals(actualLastName, expectedLastName, "Actual Title in response "+actualLastName+" is not matching with the expected title "+expectedLastName);
		Assert.assertEquals(actualDateOfBirth, expectedDateOfBirth, "Actual Title in response "+actualDateOfBirth+" is not matching with the expected title "+expectedDateOfBirth);
		Assert.assertEquals(actualEmail, bookerEmail, "Actual Title in response "+actualEmail+" is not matching with the expected title "+bookerEmail);
		Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber, "Actual Title in response "+actualPhoneNumber+" is not matching with the expected title "+expectedPhoneNumber);
		Assert.assertEquals(actualCountryISOCode, expectedCountryISOCode, "Actual Title in response "+actualCountryISOCode+" is not matching with the expected title "+expectedCountryISOCode);
		
	
	//	Update Traveler Details		
		endPoint = "/bookings/carts/"+cartId+"/travelers";		
		booking.requestUpdateTravelerDetails(currentTestName, endPoint, partnerId, updateTravelerDetailsJSON,env);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
	
		
		
	//	Validate Cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON,env);
				
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
		System.out.println("Cart ["+cartId+"] successfully validated");
		
	//	Process Payment and Create Booking
		endPoint = "/bookings/carts/"+cartId+"/book";
			
	//	Update JSON with cart id
		String requestJSON = System.getProperty("user.dir") + createBookingJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		response =booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON,env);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		Object bookingId=booking.verifyCreateBooking(bookerEmail);

		//****************** Get Single Booking *********************************
		
				endPoint = "/bookings/bookings/" + bookingId;
				
				response = booking.requestGetSingleBooking(currentTestName, endPoint, partnerId,env);
				
			//	Verify response code and message
				booking.verifyStatusCode("200");
				booking.verifyStatusMessage("OK");
				
				booking.verifyBookingId(response, (String)bookingId);		
	
	}
	
	
	
	
}
