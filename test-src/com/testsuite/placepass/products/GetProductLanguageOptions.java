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

public class GetProductLanguageOptions extends BaseSetup {
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="getProductLanguageOptions_TestData")
	public void tc_API_getProductLanguageOptionsUsingGetMethod(String endPoint, String partnerId, String bookingOptionId, String productId){
		
		product.requestGetProductLanguageOptions(currentTestName, endPoint, partnerId, bookingOptionId);
		
//		Verify response code and message
		product.verifyStatusCode("200");
		product.verifyStatusMessage("OK");
		
		product.verifyProductLanguageOptions(bookingOptionId, "languageServices", "Value", productId);
	}
		
}
