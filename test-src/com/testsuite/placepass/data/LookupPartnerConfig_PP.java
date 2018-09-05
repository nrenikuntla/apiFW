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
import com.utilities.APIHelpers;

public class LookupPartnerConfig_PP extends BaseSetup {
	
//	Declaration of respective API Parts instances
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
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_PlacePass_LookupPartnerConfig")
	public void tc_API_001_PlacePass_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields, String title, String subtitle, String env) throws ParseException{
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerConfigHeroParam(title,subtitle);
	}
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_PlacePass_LookupPartnerConfig")
	public void tc_API_002_PlacePass_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields, String env) throws ParseException{
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerConfigFeaturedProductsParam();
	}	
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_PlacePass_LookupPartnerConfig")
	public void tc_API_003_PlacePass_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields, String env) throws ParseException{
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerConfigDestinationsParam();
	}
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_PlacePass_LookupPartnerConfig")
	public void tc_API_004_PlacePass_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields, String title, String subtitle, String env) throws ParseException{
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerConfigNoParam_PP(title,subtitle);
	}	
		
}
