package com.testsuite.placepass.products;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Product;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class GetProductReviews extends BaseSetup{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Product product;
	public ConfigManager api;
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		api = new ConfigManager("Api");
		product = new Product();
		currentTestName = method.getName();
		
	}
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_getProductReviews")
	public void tc_API_001_getProductReviewsUsingGetMethod(String endPoint, String partnerId, String hitsPerPage, String pageNumber, String language, String productId){
		
		product.requestGetProductReviews(currentTestName, endPoint, partnerId, hitsPerPage, pageNumber, language);
		
//		Verify response code and message
		product.verifyStatusCode("200");
		product.verifyStatusMessage("OK");
		
		product.verifyProductReviews("reviews", hitsPerPage, pageNumber, language, productId);
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id (abc)
	 * Empty productId ({productid})
	 * Invalid productId (V28H)
	 * Empty hitsperpage ({hitsperpage})
	 * Zero hitsperpage
	 * Invalid hitsperpage (a)
	 * Empty pagenumber ({pagenumber})
	 * Zero pagenumber
	 * Invalid pagenumber
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_getProductReviews")
	public void tc_API_002_getProductReviewsUsingGetMethod(String endPoint, String partnerId, String hitsPerPage,
			String pageNumber, String language, String status, String message, String ppcode, String ppmessage){
		
		product.requestGetProductReviews(currentTestName, endPoint, partnerId, hitsPerPage, pageNumber, language);
		
//		Verify response code and message
		product.verifyStatusCode(status);
		product.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			product.verifyPPCodePPMessage(ppcode, ppmessage);
	}

}
