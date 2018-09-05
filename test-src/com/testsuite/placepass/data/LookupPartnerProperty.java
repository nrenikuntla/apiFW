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

public class LookupPartnerProperty extends BaseSetup {
	
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
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_LookupPartnerProperty")
	public void tc_API_001_LookupPartnerPropertyUsingGetMethod(String endPoint, String partnerId, String propertyCode, String carLocationCode) throws ParseException{
		
		data.requestLookupPartnerProperty(currentTestName, endPoint, partnerId, propertyCode);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPropertyParam(propertyCode,carLocationCode);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_LookupPartnerProperty")
	public void tc_API_002_LookupPartnerPropertyUsingGetMethod(String endPoint, String partnerId, String propertyCode, String ppcode, String ppmessage){
		
		data.requestLookupPartnerProperty(currentTestName, endPoint, partnerId, propertyCode);
		
	//	Verify response code and message
		data.verifyStatusCode("404");
		data.verifyStatusMessage("Not Found");
		
		data.verifyPartnerPropertyNoParam(ppcode,ppmessage);
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id (abc)
	 * Empty code ({code})
	 * Invalid code (ABCDE)
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_LookupPartnerProperty")
	public void tc_API_003_LookupPartnerPropertyUsingGetMethod(String endPoint, String partnerId, String propertyCode,
			String status, String message, String ppcode, String ppmessage){
		
		data.requestLookupPartnerProperty(currentTestName, endPoint, partnerId, propertyCode);
		
	//	Verify response code and message
		data.verifyStatusCode(status);
		data.verifyStatusMessage(message);
		
		data.verifyPPCodePPMessage(ppcode,ppmessage);
	}
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_LookupPartnerProperty")
	public void tc_API_004_LookupPartnerPropertyUsingGetMethod(String endPoint, String partnerId, String guestCount,String propertyCode,
			String propertyName,String cityName) throws ParseException{
		
		data.requestLookupPartnerProperty(currentTestName, endPoint, partnerId, propertyCode);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		data.verifyPartnerPropertyCodes(propertyName,cityName);
		
		
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_LookupPartnerProperty")
	public void tc_API_005_LookupPartnerPropertyUsingGetMethod(String endPoint, String partnerId, String propertyCode, String ppcode, String ppmessage){
		
		data.requestLookupPartnerProperty(currentTestName, endPoint, partnerId, propertyCode);
		
	//	Verify response code and message
		data.verifyStatusCode("404");
		data.verifyStatusMessage("Not Found");
		
		data.verifyPartnerPropertyNoParam(ppcode,ppmessage);
	}
}
