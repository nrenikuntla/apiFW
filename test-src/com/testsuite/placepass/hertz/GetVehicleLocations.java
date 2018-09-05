package com.testsuite.placepass.hertz;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Hertz;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class GetVehicleLocations extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Hertz hertz;
	private APIHelpers apiHelpers;
	public ConfigManager api;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		apiHelpers = new APIHelpers();
		api = new ConfigManager("Api");
		hertz = new Hertz();
		currentTestName = method.getName();
		
	}
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_HertzGetVehicleLocations")
	public void tc_API_001_HertzGetVehicleLocationsUsingGetMethod(String endPoint, String partnerId, String query, String marshaCode){
		
		hertz.requestHertzGetVehicleLocations(currentTestName, endPoint, partnerId, query, marshaCode);
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
		
		if(query!=null)
			hertz.verifyVehicleLocations(query);
		else
			hertz.verifyVehicleLocations(marshaCode);
	}
	
	
}
