package com.testsuite.united.Products;

	import java.lang.reflect.Method;
	import java.util.ArrayList;
	import java.util.List;
	import org.json.simple.parser.ParseException;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Test;
	import com.api.category.UnitedProduct;
	import com.base.BaseSetup;
	import com.datamanager.ConfigManager;
	import com.datamanager.ExcelManager;
	
import com.testsuite.dataprovider.UnitedUnitTests_TestData_Provider;


	public class GetPriceMatch extends BaseSetup{
		
//		Declaration of respective API Parts instances
		ExcelManager excel_Manager;
		UnitedProduct unitedproduct;
		public ConfigManager api;
		
		public String currentTestName;
		List<String> headers = new ArrayList<String>();
		
		
		/**
		 * Purpose - Initializes the API parts instances
		 */
		@BeforeMethod(alwaysRun = true)
		public void SetUp(Method method) {
			
			api = new ConfigManager("Api");
			unitedproduct = new UnitedProduct();
			currentTestName = method.getName();
			
		}
		
		/**
		 * @throws ParseException 
		 * 
		 */
		@Test(dataProviderClass=UnitedUnitTests_TestData_Provider.class, dataProvider="TC001_getPriceMatch")
		public void tc_API_001_getPriceMatchUsingPostMethod(String endPoint, String partnerId, String requestJSON, 
				String adultQty, String adultPrice, String childQty, String childPrice, String infantQty, String infantPrice) throws ParseException{
			
			unitedproduct.requestGetPriceMatch(currentTestName, endPoint, partnerId, requestJSON);
			
//			Verify response code and message
			unitedproduct.verifyStatusCode("200");
			unitedproduct.verifyStatusMessage("OK");
			
			unitedproduct.verifyPriceMatch(adultQty, adultPrice, childQty, childPrice, infantQty, infantPrice);
		}
		
		
		

}






