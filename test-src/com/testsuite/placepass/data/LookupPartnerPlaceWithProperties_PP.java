package com.testsuite.placepass.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Data;
import com.base.BaseSetup;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;


public class LookupPartnerPlaceWithProperties_PP extends BaseSetup{
	
	Data data;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		data = new Data();
		currentTestName = method.getName();		
	}
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_PlacePass_LookupPartnerPlaceWithProperties")
	public void tc_API_001_PlacePass_LookupPartnerPlacePropertiesUsingGetMethod(String endPoint, String partnerId, String count, String query, String region) throws ParseException{
		
		data.requestLookupPartnerPlaceWithProperties(currentTestName, endPoint, partnerId, count, query);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlacePropertiesCountQueryRegion(count,query,region);		
	}	

}
