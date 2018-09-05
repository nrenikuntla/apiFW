package com.testsuite.placepass.hertz;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.json.simple.parser.ParseException;

import com.api.category.Hertz;
import com.base.BaseSetup;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class HertzGetVehicleAvailRate extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Hertz hertz;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		hertz = new Hertz();
		currentTestName = method.getName();
		
	}
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_HertzGetVehicleAvailRate")
	public void tc_API_001_HertzGetVehicleAvailRateUsingPostMethod(String endPoint, String partnerId, String requestJSON, String expectedVehicleCode){
		
		hertz.requestHertzGetVehicleAvailRate(currentTestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
		
		hertz.verifyVehicleAvailabilities(expectedVehicleCode);
	}
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_HertzGetVehicleAvailRate")
	public void tc_API_002_HertzGetVehicleAvailRateUsingPostMethod(String endPoint, String partnerId, String requestJSON,
			String expectedVehicleCode, String earnType) throws ParseException{
		
		hertz.requestHertzGetVehicleAvailRate(currentTestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		hertz.verifyStatusCode("200");
		hertz.verifyStatusMessage("OK");
		
		hertz.verifyVehicleAvailabilities(expectedVehicleCode);
		hertz.verifyVehicleEarnType(earnType);		
	}

}
