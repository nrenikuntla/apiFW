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

public class LookupPartnerPlaces extends BaseSetup {
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_LookupPartnerPlaces")
	public void tc_API_001_LookupPartnerPlacesUsingGetMethod(String endPoint, String partnerId, String count, String query, String region) throws ParseException{
		
		data.requestLookupPartnerPlaces(currentTestName, endPoint, partnerId, count, query);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlacePropertiesCountQueryRegion(count,query,region);
	}	
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_LookupPartnerPlaces")
	public void tc_API_002_LookupPartnerPlacesUsingGetMethod(String endPoint, String partnerId, String count, String query, String ppcode, String ppmessage){
		
		data.requestLookupPartnerPlaces(currentTestName, endPoint, partnerId, count, query);
		
	//	Verify response code and message
		data.verifyStatusCode("400");
		data.verifyStatusMessage("Bad Request");
		
		data.verifyPartnerPlacesNoParam(ppcode,ppmessage);
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id (abc) 
	 * Large count (453543444343244)
	 * Invalid count (dfsfds#WWR)
	 * Long q (gfgdgfdgfhfsjhgfsjhgjshgkjhgkjhgkjdhgdkjg)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_LookupPartnerPlaces")
	public void tc_API_003_LookupPartnerPlacesUsingGetMethod(String endPoint, String partnerId, String count, String query,
			String region, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		data.requestLookupPartnerPlaces(currentTestName, endPoint, partnerId, count, query);
		
	//	Verify response code and message
		data.verifyStatusCode(status);
		data.verifyStatusMessage(message);
		
		if(ppcode!=null & ppmessage!=null)
			data.verifyPPCodePPMessage(ppcode, ppmessage);
		else
			data.verifyPartnerPlacePropertiesCountQueryRegion(count,query,region);
	}

}
