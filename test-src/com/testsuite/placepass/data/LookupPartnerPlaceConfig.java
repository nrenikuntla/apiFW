package com.testsuite.placepass.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Data;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;
import com.utilities.APIHelpers;

public class LookupPartnerPlaceConfig extends BaseSetup {
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_LookupPartnerPlaceConfig")
	public void tc_API_001_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid, String guidecount, String fields, String id) throws ParseException{
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlaceConfigCityguidesParam(webid);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_LookupPartnerPlaceConfig")
	public void tc_API_002_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid, String guidecount, String fields, String id, String title, String hero, String env) throws ParseException{
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id, env);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlaceConfigHeroParam(webid,title,hero);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_LookupPartnerPlaceConfig")
	public void tc_API_003_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid, String guidecount, String fields, String id, String location) throws ParseException{
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlaceConfigLocationParam(webid,location);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_LookupPartnerPlaceConfig")
	public void tc_API_004_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid, String guidecount, String fields, String id) throws ParseException{
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlaceConfigPersonalizedCollectionsParam(webid);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_LookupPartnerPlaceConfig")
	public void tc_API_005_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid, String guidecount, String fields, String id) throws ParseException{
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlaceConfigTopTipsParam(webid);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC007_LookupPartnerPlaceConfig")
	public void tc_API_007_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid,
			String guidecount, String fields, String id, String title, String location, String carLocationCode) throws ParseException{
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
	
	//	Verify links returned in shelves
		List<String> links = data.verifyPartnerPlaceConfigWebIdGuideCountParam(webid, title, location, carLocationCode);
		HashMap<String, String> response = null;
		for(int i=0; i<links.size();i++){
			response = data.requestShelflink(links.get(i));
			String obtainedCode = response.get("status code");
			Assert.assertEquals(obtainedCode, "200", "Obtained response code ["+obtainedCode+"] for link '"+links.get(i)+"' doesn't match with expected [200]");
			System.out.println("Obtained response code ["+obtainedCode+"] for link '"+links.get(i)+"' is as expected");
		}	
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC008_LookupPartnerPlaceConfig")
	public void tc_API_008_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid,
			String guidecount, String fields, String id, String title, String location, String carLocationCode) throws ParseException{
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");
		
		data.verifyPartnerPlaceConfigIdGuideCountParam(id, title, location, carLocationCode);	
	}
	
	
	/**
	 *  
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC009_LookupPartnerPlaceConfig")
	public void tc_API_009_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid,
			String guidecount, String fields, String id, String ppcode, String ppmessage){
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode("400");
		data.verifyStatusMessage("Bad Request");
		
		data.verifyPartnerPlaceConfigIdWebidGuideCountParam(ppcode,ppmessage);	
	}
	
	
	/**
	 *  
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC010_LookupPartnerPlaceConfig")
	public void tc_API_010_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid,
			String guidecount, String fields, String id, String ppcode, String ppmessage){
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode("400");
		data.verifyStatusMessage("Bad Request");
		
		data.verifyPartnerPlaceConfigNoParam(ppcode,ppmessage);	
	}
	
	
	/**
	 * Without partner-id 
	 * Invalid partner-id (abc)
	 * Invalid webid (abcdef)
	 * Invalid guidecount (a)
	 * guidecount = 0
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC011_LookupPartnerPlaceConfig")
	public void tc_API_011_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId, String webid,
			String guidecount, String fields, String id, String status, String message, String ppcode, String ppmessage){
		
		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);
		
	//	Verify response code and message
		data.verifyStatusCode(status);
		data.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			data.verifyPPCodePPMessage(ppcode,ppmessage);	
	}

}
