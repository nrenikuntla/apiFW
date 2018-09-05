package com.testsuite.placepass.workflows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Search;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.WorkFlows_TestData_Provider;
import com.utilities.APIHelpers;

public class ProductSearch_GetRelatedProducts extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	private APIHelpers apiHelpers;
	public ConfigManager api;
	JSONManager jsonObject = new JSONManager();
	
	List<String> headers = new ArrayList<String>();
	public String current_TestName;
	
	Search search;
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		apiHelpers = new APIHelpers();
		api = new ConfigManager("Api");
		current_TestName = method.getName();
		search = new Search();
	}
	
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=WorkFlows_TestData_Provider.class, dataProvider="ProductSearch_getRelatedProducts_TestData")
	public void tc_API_001_ProductSearch_GetRelatedProducts(String endPoint, String partnerId, String requestJSON, String hitsPerPage, String pageNumber, String address) {
		
		
		HashMap<String, String> response = search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
		String productId = null, parameters = null;
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		List<String> values = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "hits", "objectID");
		
		for(int i = 0; i < values.size(); i++){
			System.out.println("In Post(ProductSearch API) response, Product Id "+(i+1)+" is -- "+values.get(i));
		}
		try{
			productId = values.get(0);
		}catch(IndexOutOfBoundsException e){
			Assert.fail("No products have been returned by Search API for given Address "+address);
		}
				
		response = search.requestGetRelatedProducts(current_TestName, endPoint, partnerId, productId, hitsPerPage, pageNumber);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyFormattedAddress("hits", "FormattedAddress", address);
		search.verifyHitsPerPage(hitsPerPage);
		
	}
		
		
	
}
