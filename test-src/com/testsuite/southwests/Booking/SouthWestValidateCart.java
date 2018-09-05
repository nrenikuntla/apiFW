package  com.testsuite.southwests.Booking;

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

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class SouthWestValidateCart extends BaseSetup{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	public ConfigManager api;
	JSONManager jsonObject = new JSONManager();
	
	List<String> headers = new ArrayList<String>();
	public String current_TestName;
	
	Booking booking;
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		api = new ConfigManager("Api");
		current_TestName = method.getName();
		booking = new Booking();
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="TC001_ValidateShoppingCart")
	public void tc_API_001_ValidateShoppingCart(String endPoint, String partnerId, String requestJSON, String validateCartJSON) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(current_TestName, endPoint, partnerId, requestJSON);
		
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
		response = booking.requestGetBookingQuestions(current_TestName, endPoint, partnerId);
						
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		JSONArray JsonArray = (JSONArray) object.get("bookingQuestions");
		int questions = JsonArray.size();
		System.out.println("---- No of JSONs in the array is " + questions);
		
		String validateCartEndPoint = "/bookings/carts/"+cartId+"/validate";
				
		booking.requestValidateShoppingCart(current_TestName, validateCartEndPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyValidateCart(questions);
		
	}
}	