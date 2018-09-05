package com.testsuite.placepass.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Data;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class GetAllCountryTypePlaces extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Data data;
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
		data = new Data();
		currentTestName = method.getName();		
	}
	
	/**
	 * Marriott Partner - Min 100 countries should be returned
	 * PlacePass Partner - Min 200 countries should be returned
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_GetAllCountryTypePlaces")
	public void tc_API_001_GetAllCountryTypePlacesUsingGetMethod(String endPoint, String partnerId, String minCountries){
		
		data.requestGetAllCountryTypePlaces(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyCountriesReturned(minCountries,"PlaceId");
	}

}
