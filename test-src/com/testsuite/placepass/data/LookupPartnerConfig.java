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

public class LookupPartnerConfig extends BaseSetup {
	
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
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_LookupPartnerConfig")
	public void tc_API_001_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields, String title, String subtitle, String env) throws ParseException{
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerConfigHeroParam(title,subtitle);
	}	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_LookupPartnerConfig")
	public void tc_API_002_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields, String env) throws ParseException{
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerConfigFeaturedProductsParam();
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_LookupPartnerConfig")
	public void tc_API_003_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields, String env) throws ParseException{
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerConfigDestinationsParam();
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_LookupPartnerConfig")
	public void tc_API_004_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields, String title, String subtitle, String env) throws ParseException{
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerConfigNoParam(title,subtitle);
	}
	
	
	/**
	 * Without partner-id 
	 * Invalid partner-id (abc)
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_LookupPartnerConfig")
	public void tc_API_005_LookupPartnerConfigUsingGetMethod(String endPoint, String partnerId, String fields,
			String env, String status, String message, String ppcode, String ppmessage){
		
		data.requestLookupPartnerConfig(currentTestName, endPoint, partnerId, fields, env);
		
	//	Verify response code and message
		data.verifyStatusCode(status);
		data.verifyStatusMessage(message);
		
		data.verifyPPCodePPMessage(ppcode, ppmessage);
	}

}
