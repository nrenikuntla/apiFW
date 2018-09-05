package com.testsuite.placepass.hertz;

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

import com.api.category.Hertz;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class HertzGetVehicleRetRes extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Hertz hertz;
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
		hertz = new Hertz();
		currentTestName = method.getName();
		
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_HertzGetVehicleRetRes")
	public void tc_API_001_HertzGetVehicleRetResUsingPostMethod(String endPoint, String partnerId, String availabilityJSON, String vehicleCode,
			String reservationJSON, String firstName, String email, String reservationRetrievalJSON, String cancellationJSON) throws ParseException{
		
	//	Availability	
		HashMap<String,String> response = hertz.requestHertzGetVehicleAvailRate(currentTestName, endPoint, partnerId, availabilityJSON);
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
		
		hertz.verifyVehicleAvailabilities(vehicleCode);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		JSONArray availabilities = (JSONArray) object.get("Availabilities");
		JSONObject availability = (JSONObject) availabilities.get(0);
		JSONObject reference = (JSONObject) availability.get("Reference");
		String referenceId = reference.get("Id").toString();
		
	//	Update JSON with Reference id	
		String requestJSON = System.getProperty("user.dir") + reservationJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		object.put("ReferenceId",referenceId);
		requestBody = object.toJSONString();
		System.out.println("==============After inserting ReferenceID===========\n" + requestBody);
		
	//	Reservation	
		endPoint = "/cars/api/hertz/booking";
		
		hertz.requestHertzGetVehicleRes(currentTestName, endPoint, partnerId, requestBody, reservationJSON);
				
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
				
		Object ID = hertz.verifyVehicleReservation(firstName,email);
		
	//	Cancellation		
	//	Update JSON with above unique booking id
		requestJSON = System.getProperty("user.dir") + reservationRetrievalJSON;
		requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		Object uniqueId = (Object)object.get("UniqueId");
		String uniqueIdJSON = uniqueId.toString();
		uniqueIdJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(uniqueIdJSON, "ID", (String)ID);
		requestBody = jsonObject.updateJSONValueInJsonKey(requestBody, "UniqueId", uniqueIdJSON);
		
		endPoint = "/cars/api/hertz/retrieval";
		hertz.requestHertzGetVehicleRetRes(currentTestName, endPoint, partnerId, requestBody, reservationRetrievalJSON);
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");		
		
		hertz.verifyVehicleReservationRetrieval(ID,firstName,email);
		
	//	Update JSON with above unique booking id
		requestJSON = System.getProperty("user.dir") + cancellationJSON;
		requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		parser = new JSONParser();
		object = (JSONObject) parser.parse(requestBody);
		
		uniqueId = (Object)object.get("UniqueId");
		uniqueIdJSON = uniqueId.toString();
		uniqueIdJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(uniqueIdJSON, "ID", (String)ID);
		requestBody = jsonObject.updateJSONValueInJsonKey(requestBody, "UniqueId", uniqueIdJSON);
		
		endPoint = "/cars/api/hertz/cancel";
		hertz.requestHertzGetVehicleCancel(currentTestName, endPoint, partnerId, requestBody, cancellationJSON);
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
		
		hertz.verifyVehicleCancellation(ID,firstName,email);
	}

}
