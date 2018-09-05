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

public class LookupPartnerPlaceConfig_PP extends BaseSetup {

//	Declaration of respective API Parts instances
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

	@Test(dataProviderClass = UnitTests_TestData_Provider.class, dataProvider = "TC001_PlacePass_LookupPartnerPlaceConfig")
	public void tc_API_001_PlacePass_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId,
			String webid, String guidecount, String fields, String id, String env) throws ParseException {

		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id, env);

		// Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");

		data.verifyPartnerPlaceConfigCityGuide(webid);
	}

	@Test(dataProviderClass = UnitTests_TestData_Provider.class, dataProvider = "TC002_PlacePass_LookupPartnerPlaceConfig")
	public void tc_API_002_PlacePass_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId,
			String webid, String guidecount, String fields, String id, String title, String hero, String env)
			throws ParseException {

		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id, env);

		// Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");

		data.verifyPartnerPlaceConfigHeroParam(webid, title, hero);
	}

	@Test(dataProviderClass = UnitTests_TestData_Provider.class, dataProvider = "TC003_PlacePass_LookupPartnerPlaceConfig")
	public void tc_API_003_PlacePass_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId,
			String webid, String guidecount, String fields, String id, String location) throws ParseException {

		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);

		// Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");

		data.verifyPartnerPlaceConfigLocationParam(webid, location);
	}

	@Test(dataProviderClass = UnitTests_TestData_Provider.class, dataProvider = "TC004_PlacePass_LookupPartnerPlaceConfig")
	public void tc_API_004_PlacePass_LookupPartnerPlaceConfigUsingGetMethod(String endPoint, String partnerId,
			String webid, String guidecount, String fields, String id) throws ParseException {

		data.requestLookupPartnerPlaceConfig(currentTestName, endPoint, partnerId, webid, guidecount, fields, id);

		// Verify response code and message
		data.verifyStatusCode("200");
		data.verifyStatusMessage("OK");

		data.verifyPartnerPlaceConfigPersonalizedCollectionsPar_PP(webid);
	}

}