package com.testsuite.placepass.bookingflow;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BaseSetup;
import com.datamanager.JSONManager;
import com.testng.Assert;
import com.api.category.Booking;
import com.api.category.Product;
import com.api.category.Search;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class BookingFlow extends BaseSetup{
	
	public String current_TestName;
	Search search;
	Product product;
	Booking booking;
	JSONManager jsonObject;
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		current_TestName = method.getName();
		search = new Search();
		product = new Product();
		jsonObject = new JSONManager();
		booking = new Booking();
	}
	
	/**
	 *
	 * @throws ParseException
	 * @throws java.text.ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_Bookingflow")
	public void tc_API_001_availabilitySearchWithPrice(String endPoint, String partnerId, String requestJSON,
			String query, String max, String month, String year,String env,String min,String date,String createCartJSON ,String BookerDetailsJSON , String UpdateAnswersJSON ,String TravelerDetailsJSON, String ValidateCartJSON ,String CreateBookingJSON) throws ParseException, java.text.ParseException, FileNotFoundException, IOException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
	//	Verify price filter		
		search.verifyProductPrice("hits", "Price", query, max, min);
		
		String responseString = response.get("response body");
		
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
				
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year,env);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);		
		
		}
		JSONArray hitsArray=(JSONArray) object.get("hits");
		for(int i=0;i<hitsArray.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsArray.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println(endPoint="/products/"+productid+"/bookingoptions");	
			//Get Product Options for each product
			product.requestGetProductOptions(current_TestName, endPoint, partnerId, date);
			
		
           //Verify response code and message
			product.verifyStatusCode("200");
			product.verifyStatusMessage("OK");
		
			 List<String> productOptionlistKey = product.verifyProductOptions(date, "productOptionList", "retailPrice", "finalPrice",productid);
			 for(int j=0;j<productOptionlistKey.size();j++)
			 {
				 System.out.println(productOptionlistKey.get(j));
			 }
			   //Create Shopping Cart
			 	System.out.println(endPoint="/bookings/carts");
			 
			 	String ShoppingCartJSON = System.getProperty("user.dir") + createCartJSON;
			 
			 	String requestBody = jsonObject.readJSONContentToString(ShoppingCartJSON);
				
				JSONParser parser1 = new JSONParser();
				JSONObject object1 = (JSONObject) parser1.parse(requestBody);
				
				JSONArray bookingoptions=(JSONArray) object1.get("bookingOptions");
				String bookingProductJSON  = jsonObject.updateListOfValuesInSimpleJsonKeys(bookingoptions, "productId",  productid);
				requestBody = jsonObject.updateJSONValueInJsonKeys(requestBody, "bookingOptions", bookingoptions);
				String	bookingOptionJSON  = jsonObject.updateListOfValuesInSimpleJsonKeys(bookingoptions, "productOptionId",productOptionlistKey.get(0) );
				requestBody = jsonObject.updateJSONValueInJsonKeys(requestBody, "bookingOptions", bookingoptions);
			
					
				 HashMap<String, String> responsee = booking.requestCreateCartDynamic(current_TestName, endPoint, partnerId,requestBody,createCartJSON);
				
				 String responseeString = responsee.get("response body");
					
					JSONParser parser2 = new JSONParser();
					JSONObject object2 = (JSONObject) parser2.parse(responseeString);
					String cartId = (String) object2.get("cartId");
				
					System.out.println("CartId is - "+cartId);
					
					//Get Booking Questions		
					endPoint = "/bookings/carts/"+cartId+"/bookingquestions";	
					responsee = booking.requestGetBookingQuestions(current_TestName, endPoint, partnerId);
									
				//	Verify response code and message
					booking.verifyStatusCode("200");
					booking.verifyStatusMessage("OK");
					
					responseeString = responsee.get("response body");
					
					parser = new JSONParser();
					object = (JSONObject) parser2.parse(responseeString);
					JSONArray JsonArray = (JSONArray) object.get("bookingQuestions");
					
					System.out.println("---- No of JSONs in the array is " + JsonArray.size());
					if(JsonArray.size()>0)
					{
					
			
						String actualBookingQuestionId = (String)((JSONObject)JsonArray.get(0)).get("questionId");
						
						//Update booking answers		
						endPoint = "/bookings/carts/"+cartId+"/bookinganswers";			
						
						if(productid!=null)
						{
					//		Update JSON with productId
							String requestAnswerJSON = System.getProperty("user.dir") + UpdateAnswersJSON;
							String requestAnswerBody = jsonObject.readJSONContentToString(requestAnswerJSON);
							
							object = (JSONObject) parser.parse(requestAnswerBody);
							
							JSONArray questionAnswersArray = (JSONArray)object.get("questionAnswers");
							
							Iterator<JSONObject> k = questionAnswersArray.iterator();
							while (k.hasNext())
							{
								JSONObject questionAnswer = k.next();
								questionAnswer.put("productId",productid);
								questionAnswer.put("productOptionId", productOptionlistKey.get(0));
								questionAnswer.put("bookingQuestionId", actualBookingQuestionId);
						
							}
							requestBody = object.toJSONString();
							System.out.println("==============After inserting productId===========\n" + requestBody);
							
							
							booking.requestUpdateBookingAnswersDynamic(current_TestName, endPoint, partnerId, requestBody, UpdateAnswersJSON);
						}
					
					}
					
					
					//Update Booker details
					endPoint = "/bookings/carts/"+cartId+"/bookerdetails";
					String updateBookerDetailsJSON = System.getProperty("user.dir") + BookerDetailsJSON;
					 
					String bookerRequestBody = jsonObject.readJSONContentToString(updateBookerDetailsJSON);
					responsee = booking.requestUpdateBookerDetailsDynamic(current_TestName, endPoint, partnerId,bookerRequestBody ,BookerDetailsJSON);
					
				//	Verify response code and message
					booking.verifyStatusCode("200");
					booking.verifyStatusMessage("OK");
					
					responseeString = responsee.get("response body");
					
					parser = new JSONParser();
					object = (JSONObject) parser2.parse(responseeString);
					
					JSONObject bookerDetailsObject = (JSONObject)object.get("bookerDetails");
					
					String actualTitle = (String)bookerDetailsObject.get("title");
					String actualFirstName = (String)bookerDetailsObject.get("firstName");
					String actualLastName = (String)bookerDetailsObject.get("lastName");
					String actualEmail = (String)bookerDetailsObject.get("email");
					String actualPhoneNumber = (String)bookerDetailsObject.get("phoneNumber");
					String actualCountryISOCode = (String)bookerDetailsObject.get("countryISOCode");
					String actualDateOfBirth = (String)bookerDetailsObject.get("dateOfBirth");		
					
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
					
					//Update Traveler details
					endPoint = "/bookings/carts/"+cartId+"/travelers";	
					
					if(productOptionlistKey!=null){
					//	Update JSON with productOptionId
					String updateTravelerDetailsJSON = System.getProperty("user.dir") +TravelerDetailsJSON;
					String TravelerDetails = jsonObject.readJSONContentToString(updateTravelerDetailsJSON);
					
					object = (JSONObject) parser2.parse(TravelerDetails);
					
					object.put("productOptionId", productOptionlistKey.get(0));
					requestBody = object.toJSONString();
					System.out.println("==============After inserting productOptionId===========\n" + requestBody);
					
					responsee=booking.requestUpdateTravelerDetailsDynamic(current_TestName, endPoint, partnerId, requestBody, TravelerDetailsJSON);
					//Verify response code and message
					booking.verifyStatusCode("200");
					booking.verifyStatusMessage("OK");
					
					responseeString = responsee.get("response body");
					
					parser = new JSONParser();
					object = (JSONObject) parser2.parse(responseeString);
					
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
						
					expectedTitle = (String)((JSONObject)JsonArray.get(0)).get("title");
					expectedFirstName = (String)((JSONObject)JsonArray.get(0)).get("firstName");
					expectedLastName = (String)((JSONObject)JsonArray.get(0)).get("lastName");
					expectedDateOfBirth = (String)((JSONObject)JsonArray.get(0)).get("dateOfBirth");
					String ExpectedEmail = (String)((JSONObject)JsonArray.get(0)).get("email");
					expectedPhoneNumber = (String)((JSONObject)JsonArray.get(0)).get("phoneNumber");
					expectedCountryISOCode = (String)((JSONObject)JsonArray.get(0)).get("countryISOCode");
					long expectedAgeBandId = (long)((JSONObject)JsonArray.get(0)).get("ageBandId");
					
					System.out.println("expectedTitle is \""+expectedTitle+"\", whereas actualTitle is \""+actualTitle+"\"");
					System.out.println("expectedFirstName is \""+expectedFirstName+"\", whereas actualFirstName is \""+actualFirstName+"\"");
					System.out.println("expectedLastName is \""+expectedLastName+"\", whereas actualLastName is \""+actualLastName+"\"");
					System.out.println("expectedDateOfBirth is \""+expectedDateOfBirth+"\", whereas actualDateOfBirth is \""+actualDateOfBirth+"\"");
					System.out.println("expectedEmail is \""+ExpectedEmail+"\", whereas actualEmail is \""+actualEmail+"\"");
					System.out.println("expectedPhoneNumber is \""+expectedPhoneNumber+"\", whereas actualPhoneNumber is \""+actualPhoneNumber+"\"");
					System.out.println("expectedCountryISOCode is \""+expectedCountryISOCode+"\", whereas actualCountryISOCode is \""+actualCountryISOCode+"\"");
					System.out.println("expectedAgeBandId is \""+expectedAgeBandId+"\", whereas actualAgeBandId is \""+actualAgeBandId+"\"");
					
					System.out.println("actualBookerDetails is "+actualBookerDetails);
					
					Assert.assertEquals(actualTitle, expectedTitle, "Actual Title in response "+actualTitle+" is not matching with the expected title "+expectedTitle);
					Assert.assertEquals(actualFirstName, expectedFirstName, "Actual Title in response "+actualFirstName+" is not matching with the expected title "+expectedFirstName);
					Assert.assertEquals(actualLastName, expectedLastName, "Actual Title in response "+actualLastName+" is not matching with the expected title "+expectedLastName);
					Assert.assertEquals(actualDateOfBirth, expectedDateOfBirth, "Actual Title in response "+actualDateOfBirth+" is not matching with the expected title "+expectedDateOfBirth);
					Assert.assertEquals(actualEmail, ExpectedEmail, "Actual Title in response "+actualEmail+" is not matching with the expected title "+ExpectedEmail);
					Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber, "Actual Title in response "+actualPhoneNumber+" is not matching with the expected title "+expectedPhoneNumber);
					Assert.assertEquals(actualCountryISOCode, expectedCountryISOCode, "Actual Title in response "+actualCountryISOCode+" is not matching with the expected title "+expectedCountryISOCode);
					Assert.assertEquals(actualAgeBandId, expectedAgeBandId, "Actual Title in response "+actualAgeBandId+" is not matching with the expected title "+expectedAgeBandId);
					//Validate cart
					String validateCartEndPoint = "/bookings/carts/"+cartId+"/validate";
					
					responsee = booking.requestValidateShoppingCart(current_TestName, validateCartEndPoint, partnerId, ValidateCartJSON);
					booking.verifyStatusCode("200");
					booking.verifyStatusMessage("OK");
					
					responseeString = responsee.get("response body");
					
					List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseeString, "validateMessages");
					Assert.assertTrue(actualMessages.get(0).get(0).contains("success"),"Cart ["+cartId+"] validation unsuccessful. Got "+actualMessages.get(0).get(0)+" instead of success");
					System.out.println("Cart ["+cartId+"] successfully validated");
			
					//Process Payment and Create Booking
					endPoint = "/bookings/carts/"+cartId+"/book";
					
					//Update JSON with current cart id & tokenizedNumber
					String BookingJSON = System.getProperty("user.dir") + CreateBookingJSON;
					String paymentRequestBody = jsonObject.readJSONContentToString(BookingJSON);
					String token = "tok_visa";
					/*try {
						token = getTokenizedNumber();
					} catch (Exception e) {
						e.printStackTrace();
					}*/
					
					paymentRequestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(paymentRequestBody, "cartId", cartId);
					
					parser = new JSONParser();
					object = (JSONObject) parser.parse(paymentRequestBody);
					
					Object paymentObject = (Object)object.get("payment");
					String paymentJSON = paymentObject.toString();
					paymentJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(paymentJSON, "tokenizedNumber", token);
					paymentRequestBody = jsonObject.updateJSONValueInJsonKey(paymentRequestBody, "payment", paymentJSON);
					
					responsee=booking.requestProcessPaymentAndCreateBooking(current_TestName, endPoint, partnerId, paymentRequestBody, CreateBookingJSON);
							
//					Verify response code and message
					booking.verifyStatusCode("200");
					booking.verifyStatusMessage("OK");
					
					booking.verifyCreateBooking(bookerEmail);		
				}
		   }
      }
}

