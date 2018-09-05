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

public class PartnerPlaceSitemap extends BaseSetup {
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_PartnerPlaceSitemap")
	public void tc_API_001_PartnerPlaceSitemapUsingGetMethod(String endPoint, String partnerId, String query, String topDestination, String count, String page) throws ParseException{
		
		data.requestPartnerPlaceSitemap(currentTestName, endPoint, partnerId, query, topDestination, count, page);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlaceSitemapParam(query,count,page);
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id (abc)
	 * topDestination = true
	 * Without count
	 * Invalid count (3422352411)
	 * Invalid count (fdsg)
	 * Invalid page (346524635476542734234)
	 * Invalid page (dasd)
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_PartnerPlaceSitemap")
	public void tc_API_002_PartnerPlaceSitemapUsingGetMethod(String endPoint, String partnerId, String query, String topDestination,
			String count, String page, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		data.requestPartnerPlaceSitemap(currentTestName, endPoint, partnerId, query, topDestination, count, page);
		
	//	Verify response code and message
		data.verifyStatusCode(status);
		data.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			data.verifyPPCodePPMessage(ppcode, ppmessage);
		else
			data.verifyPartnerPlaceSitemapParam(query,count,page);
	}
	
}
