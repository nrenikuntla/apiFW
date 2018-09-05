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

public class GetTopDestinations extends BaseSetup {
	
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
	 * Marriott Partner
	 * PlacePass Partner
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_GetTopDestinations")
	public void tc_API_001_GetTopDestinationsUsingGetMethod(String endPoint, String partnerId, String minRegions, String regions) throws ParseException{
		
		data.requestGetTopDestinations(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyTopDestinations("top_destinations","PlacePassRegion",minRegions,regions);
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id (abc)
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_GetTopDestinations")
	public void tc_API_002_GetTopDestinationsUsingGetMethod(String endPoint, String partnerId,
			String status, String message, String ppcode, String ppmessage){
		
		data.requestGetTopDestinations(currentTestName, endPoint, partnerId);
		
	//	Verify response code and message
		data.verifyStatusCode(status);
		data.verifyStatusMessage(message);
		
		data.verifyPPCodePPMessage(ppcode, ppmessage);
	}

}
