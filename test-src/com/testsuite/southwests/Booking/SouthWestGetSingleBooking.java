package com.testsuite.southwests.Booking;

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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class SouthWestGetSingleBooking extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Booking booking;
	private APIHelpers apiHelpers;
	public ConfigManager api;
	JSONManager jsonObject;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		apiHelpers = new APIHelpers();
		api = new ConfigManager("Api");
		jsonObject = new JSONManager();
		booking = new Booking();
		currentTestName = method.getName();
		
	}
	
	
	public String getTokenizedNumber() throws Exception{
		
		String driverPath = System.getProperty("user.dir")+"\\Executables\\geckodriver.exe";
		System.setProperty("webdriver.chrome.driver",driverPath);
		String stripeToken = System.getProperty("user.dir")+"\\Data\\TOKEN.html";
		String cardNumber = "5555 5555 5555 4444";
		String expiration = "10/20";
		String security = "111";
		String zipcode = "98122";
		String token =  null;
		
		WebDriver driver = new ChromeDriver();
		
		driver.get(stripeToken);
		Thread.sleep(5000);
		driver.navigate().refresh();
		
		driver.switchTo().frame(driver.findElement(By.id("6bdd6e82-acd0-4999-9887-b524423e4145")));
		
		// Card number
		driver.switchTo().frame("__privateStripeFrame3");
		WebElement el_cardNumber = driver.findElement(By.xpath("//input[@name='cardnumber']"));
		el_cardNumber.sendKeys(cardNumber);
				
		// Expiration
		WebElement el_expiration = driver.findElement(By.xpath("//input[@name='exp-date']"));
		el_expiration.sendKeys(expiration);
					
		// Security
		WebElement el_security = driver.findElement(By.xpath("//input[@name='cvc']"));
		el_security.sendKeys(security);
				
		// Zipcode
		WebElement el_zipcode = driver.findElement(By.xpath("//input[@name='postal']"));
		el_zipcode.sendKeys(zipcode);
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame(driver.findElement(By.id("6bdd6e82-acd0-4999-9887-b524423e4145")));
		WebElement el_submit = driver.findElement(By.xpath("//*[@id='payment-form']/button"));
		el_submit.click();
		Thread.sleep(3000);
		
		WebElement el_token = driver.findElement(By.xpath("//*[@id='stripe-token-handler']/span"));
		token = el_token.getText();
		
		driver.quit();
		return token;
	}
	
	
	/**
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="TC001_GetSingleBooking")
	public void tc_API_001_GetSingleBookingUsingGetMethod(String endPoint, String partnerId, String createCartJSON, String updateBookerDetailsJSON, String updateBookingAnswersJSON, String updateTravelerDetailsJSON, String validateCartJSON, String createBookingJSON) throws ParseException, FileNotFoundException, IOException {
		
		
		
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
			expectedEmail = (String)((JSONObject)JsonArray.get(0)).get("email");
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
		String token = "tok_visa";
		/*
		try {
			token = getTokenizedNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "cartId", cartId);
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(requestBody);
		
		Object paymentObject = (Object)object.get("payment");
		String paymentJSON = paymentObject.toString();
		paymentJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(paymentJSON, "tokenizedNumber", token);
		requestBody = jsonObject.updateJSONValueInJsonKey(requestBody, "payment", paymentJSON);
		
		booking.requestProcessPaymentAndCreateBooking(currentTestName, endPoint, partnerId, requestBody, createBookingJSON);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		Object bookingId = booking.verifyCreateBooking();
		
		//****************** Get Single Booking *********************************
		
		endPoint = "/bookings/bookings/" + bookingId;
		
		response = booking.requestGetSingleBooking(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyBookingId(response, (String)bookingId);		
	}

}
