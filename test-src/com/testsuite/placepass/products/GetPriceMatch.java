package com.testsuite.placepass.products;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Product;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class GetPriceMatch extends BaseSetup{
	
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
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_getPriceMatch")
	public void tc_API_001_getPriceMatchUsingPostMethod(String endPoint, String partnerId, String requestJSON, 
			String adultQty, String adultPrice, String childQty, String childPrice, String infantQty, String infantPrice) throws ParseException{
		
		product.requestGetPriceMatch(currentTestName, endPoint, partnerId, requestJSON);
		
//		Verify response code and message
		product.verifyStatusCode("200");
		product.verifyStatusMessage("OK");
		
		product.verifyPriceMatch(adultQty, adultPrice, childQty, childPrice, infantQty, infantPrice);
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id (abc)
	 * Without quantities
	 * Empty quantities
	 * Null quantities
	 * Without quantity
	 * Empty quantity
	 * Invalid quantity
	 * Without (quantities) ageBandId
	 * Empty (quantities) ageBandId
	 * Invalid (quantities) ageBandId
	 * Without (quantities) agebandLabel
	 * Empty (quantities) ageBandLabel
	 * Invalid (quantities) ageBandLabel
	 * Without (prices) ageBandId
	 * Empty (prices) ageBandId
	 * Invalid (prices) ageBandId
	 * Without (prices) priceGroupSortOrder
	 * Empty (prices) priceGroupSortOrder
	 * Invalid (prices) priceGroupSortOrder
	 * Without (prices) currencyCode
	 * Empty (prices) currencyCode
	 * Invalid (prices) currencyCode
	 * Without (prices) retailPrice
	 * Empty (prices) retailPrice
	 * Invalid (prices) retailPrice
	 * Without (prices) finalPrice
	 * Empty (prices) finalPrice
	 * Invalid (prices) finalPrice
	 * 1 Adult
	 * 1 Child
	 * 1 Adult 1 Infant
	 * 3 Adult 1 Child
	 * 4 Adult
	 * 2 Adult 1 Child
	 * 3 Adult
	 * 2 Adult 8 Child
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_getPriceMatch")
	public void tc_API_002_getPriceMatchUsingPostMethod(String endPoint, String partnerId, String requestJSON, 
			String status, String message, String ppcode, String ppmessage){
		
		product.requestGetPriceMatch(currentTestName, endPoint, partnerId, requestJSON);
		
//		Verify response code and message
		product.verifyStatusCode(status);
		product.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			product.verifyPPCodePPMessage(ppcode, ppmessage);
	}

}
