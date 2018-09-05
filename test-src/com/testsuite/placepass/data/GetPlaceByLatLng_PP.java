package com.testsuite.placepass.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Data;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class GetPlaceByLatLng_PP extends BaseSetup {

	
//	Declaration of respective API Parts instances
	Data data;
	public ConfigManager api;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		api = new ConfigManager("Api");
		data = new Data();
		currentTestName = method.getName();		
	}
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_PlacePass_GetPlaceByLatLng")
	public void tc_API_001_placePass_GetPlaceByLatLngUsingGetMethod(String endPoint, String partnerId, String latitude,
			String longitude, String longName, String shortName) throws ParseException{
		
		data.requestGetPlaceByLatLng(currentTestName, endPoint, partnerId, latitude, longitude);
		
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPlaces(longName,shortName);
	}
	
}