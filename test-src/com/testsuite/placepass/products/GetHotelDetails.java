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

public class GetHotelDetails extends BaseSetup{
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_getHotelDetails")
	public void tc_API_001_getHotelDetailsUsingGetMethod(String endPoint, String partnerId, String productId){
		
		product.requestGetHotelDetails(currentTestName, endPoint, partnerId);
		
		product.verifyHotelDetails("hotelDetails", productId);
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id (abc)
	 * Empty productId ({productid})
	 * Invalid productId (V28H)
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_getHotelDetails")
	public void tc_API_002_getHotelDetailsUsingGetMethod(String endPoint, String partnerId,
			String status, String message, String ppcode, String ppmessage){
		
		product.requestGetHotelDetails(currentTestName, endPoint, partnerId);
		
//		Verify response code and message
		product.verifyStatusCode(status);
		product.verifyStatusMessage(message);
		
		product.verifyPPCodePPMessage(ppcode, ppmessage);
	}

}
