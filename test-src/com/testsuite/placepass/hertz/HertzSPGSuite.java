package com.testsuite.placepass.hertz;

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

import com.api.category.Hertz;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class HertzSPGSuite extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Hertz hertz;
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
		hertz = new Hertz();
		currentTestName = method.getName();
		
	}
	
	/**
	 * Reservation for 2 days - Verify Starpoints = 200 & EarnType is SPG
	 * Reservation for 4 days - Verify Starpoints = 1000 & EarnType is SPG
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_HertzSPGSuite")
	public void tc_API_001_HertzSPGSuite(String endPoint, String partnerId, String availabilityJSON, String vehicleCode,
			String reservationJSON, String firstName, String email, String reservationRetrievalJSON, String cancellationJSON,
			String reservationDays, String corpDiscount, String promoCode) throws ParseException{
		
	//	Availability	
		HashMap<String,String> response = hertz.requestHertzGetVehicleAvailRate(currentTestName, endPoint, partnerId, availabilityJSON, "prod");
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
		
		hertz.verifyVehicleAvailabilities(vehicleCode);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		JSONArray availabilities = (JSONArray) object.get("Availabilities");
		
		Iterator<JSONObject> i = availabilities.iterator();
		
		while (i.hasNext())
		{
			JSONObject availabilityObject = i.next();
			
			String earnType = availabilityObject.get("EarnType").toString();
			Assert.assertEquals(earnType, "SPG", "Earn Type "+earnType+" doesn't match with expected SPG");
			System.out.println("Earn Type "+earnType+" matches with expected SPG");
			
			String rewardPoints = availabilityObject.get("RewardPoints").toString();
			if(Integer.valueOf(reservationDays)>=1 && Integer.valueOf(reservationDays)<=2)
				Assert.assertEquals(rewardPoints,"200","SPG Points = "+rewardPoints+" instead of 200 for "+reservationDays+" reserved days");				
			else if(Integer.valueOf(reservationDays)>=3)
				Assert.assertEquals(rewardPoints,"1000","SPG Points = "+rewardPoints+" instead of 1000 for "+reservationDays+" reserved days");			
			System.out.println("SPG Points = "+rewardPoints+" for "+reservationDays+" reserved days as expected");
		}	
		
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
		
		response = hertz.requestHertzGetVehicleRes(currentTestName, endPoint, partnerId, requestBody, reservationJSON, "prod");
				
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
				
		Object ID = hertz.verifyVehicleReservation(firstName,email);
		
		responseString = response.get("response body");
		
		//parser = new JSONParser();
		object = (JSONObject) parser.parse(responseString);
		
		JSONObject vehicleDetail = (JSONObject) object.get("VehicleDetail");
		
		String rewardPoints = vehicleDetail.get("RewardPoints").toString();
		if(Integer.valueOf(reservationDays)>=1 && Integer.valueOf(reservationDays)<=2)
			Assert.assertEquals(rewardPoints,"200","SPG Points = "+rewardPoints+" instead of 200 for "+reservationDays+" reserved days");				
		else if(Integer.valueOf(reservationDays)>=3)
			Assert.assertEquals(rewardPoints,"1000","SPG Points = "+rewardPoints+" instead of 1000 for "+reservationDays+" reserved days");			
		System.out.println("SPG Points = "+rewardPoints+" for "+reservationDays+" reserved days as expected");
		
		String earnType =  vehicleDetail.get("EarnType").toString();
		Assert.assertEquals(earnType, "SPG", "Earn Type "+earnType+" doesn't match with expected SPG");
		System.out.println("Earn Type "+earnType+" matches with expected SPG");
		
	//	Retrieval		
	//	Update JSON with above unique booking id
		requestJSON = System.getProperty("user.dir") + reservationRetrievalJSON;
		requestBody = jsonObject.readJSONContentToString(requestJSON);
		
		object = (JSONObject) parser.parse(requestBody);
		
		Object uniqueId = (Object)object.get("UniqueId");
		String uniqueIdJSON = uniqueId.toString();
		uniqueIdJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(uniqueIdJSON, "ID", (String)ID);
		requestBody = jsonObject.updateJSONValueInJsonKey(requestBody, "UniqueId", uniqueIdJSON);
		
		endPoint = "/cars/api/hertz/retrieval";
		hertz.requestHertzGetVehicleRetRes(currentTestName, endPoint, partnerId, requestBody, reservationRetrievalJSON, "prod");
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");		
		
		hertz.verifyVehicleReservationRetrieval(ID,firstName,email);

	//	Cancellation	
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
		hertz.requestHertzGetVehicleCancel(currentTestName, endPoint, partnerId, requestBody, cancellationJSON, "prod");
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
		
		hertz.verifyVehicleCancellation(ID,firstName,email);
	}

}
