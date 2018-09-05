package com.testsuite.placepass.searches;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Search;
import com.base.BaseSetup;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class GetRelatedProducts extends BaseSetup {
	
	public String current_TestName;
	Search search;
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		current_TestName = method.getName();
		search = new Search();
	}
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_GetRelatedProducts")
	public void tc_API_001_getRelatedProductsUsingGetMethod(String endPoint, String partnerId, String productId, String hitsPerPage, String pageNumber, String address) {
		
		search.requestGetRelatedProducts(current_TestName, endPoint, partnerId, productId, hitsPerPage, pageNumber);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyFormattedAddress("hits", "FormattedAddress", address);
		search.verifyHitsPerPage(hitsPerPage);		
	}
	
	/**
	 * Without partner-id
	 * Invalid partner-id
	 * Invalid productId
	 * Empty hitsPerPage
	 * Invalid hitsPerPage (-1)
	 * Invalid hitsPerPage (a)
	 * Zero hitsPerPage
	 * Empty pageNumber
	 * Invalid pageNumber (-1)
	 * Invalid pageNumber (a)
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_GetRelatedProducts")
	public void tc_API_002_getRelatedProductsUsingGetMethod(String endPoint, String partnerId, String productId,
			String hitsPerPage, String pageNumber, String status, String message, String ppcode, String ppmessage) {
		
		search.requestGetRelatedProducts(current_TestName, endPoint, partnerId, productId, hitsPerPage, pageNumber);
		
	//	Verify response code and message
		search.verifyStatusCode(status);
		search.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			search.verifyPPCodePPMessage(ppcode, ppmessage);
		else
			search.verifyHitsPerPage(hitsPerPage);		
	}
	
	
	
}
