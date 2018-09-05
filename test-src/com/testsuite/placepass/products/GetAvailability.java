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

public class GetAvailability extends BaseSetup {
	
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
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_getProductAvailability")
	public void tc_API_001_getProductAvailabilityUsingGetMethod(String endPoint, String partnerId, String month, String year) {
		
		product.requestGetAvailability(currentTestName, endPoint, partnerId, month, year);
		
		product.verifyDateRange("availabilityList", "date", month, year);
		
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id (abc)
	 * Empty productId ({productid})
	 * Invalid productId (V28H)
	 * Month empty ({month})
	 * Invalid month (15)
	 * Invalid month (a)
	 * Invalid month (122)
	 * Year empty ({year})
	 * Past year
	 * Future year
	 * Invalid year (201h)
	 * Invalid year (20188)
	 * Invalid month & year (a)
	 * Empty month & year
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_getProductAvailability")
	public void tc_API_002_getProductAvailabilityUsingGetMethod(String endPoint, String partnerId,
			String month, String year, String status, String message, String ppcode, String ppmessage) {
		
		product.requestGetAvailability(currentTestName, endPoint, partnerId, month, year);
		
		product.verifyStatusCode(status);
		product.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			product.verifyPPCodePPMessage(ppcode, ppmessage);
		
	}

}
