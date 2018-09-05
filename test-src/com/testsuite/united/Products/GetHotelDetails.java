package com.testsuite.united.Products;
	import java.lang.reflect.Method;
	import java.util.ArrayList;
	import java.util.List;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Test;
	import com.api.category.UnitedProduct;
	import com.base.BaseSetup;
	import com.datamanager.ConfigManager;
	import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitedUnitTests_TestData_Provider;


	public class GetHotelDetails extends BaseSetup{
		
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
		 * 
		 */
		@Test(dataProviderClass=UnitedUnitTests_TestData_Provider.class, dataProvider="TC001_getHotelDetails")
		public void tc_API_001_getHotelDetailsUsingGetMethod(String endPoint, String partnerId, String productId){
			
			unitedproduct.requestGetHotelDetails(currentTestName, endPoint, partnerId);
			
			unitedproduct.verifyHotelDetails("hotelDetails", productId);
		}
		
	


}


