package com.testsuite.southwests.Searches;
import java.lang.reflect.Method;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.api.category.Data;
import com.api.category.SouthwestSearch;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;

public class SouthWestSearchProducts extends BaseSetup {
	
	public String current_TestName;
	SouthwestSearch SouthwestSearch;
	Data data;

	public ConfigManager api;
	JSONManager jsonObject;
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		current_TestName = method.getName();
		SouthwestSearch = new SouthwestSearch();
		jsonObject = new JSONManager();
		data = new Data();
	}
	
	/**
	 * 
	 */
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc001_searchProducts")
	public void tc_API_001_searchProductsUsingPostMethod(String endPoint, String partnerId, String requestJSON, String hits) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
	}
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc002_searchProductsByCategory")
	public void tc_API_002_searchProductsByCategory(String endPoint, String partnerId, String requestJSON, String category, String hits, String expectedPage) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
		
		SouthwestSearch.verifyCategory("hits", "Category", category);
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		SouthwestSearch.verifyPageNumber("page", expectedPage);
	}
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc003_searchProductsByDuration")
	public void tc_API_003_searchProductsByDuration(String endPoint, String partnerId, String requestJSON, String min, String max) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
		
		SouthwestSearch.verifyDuration("hits", "DurationInMinutes", min, max);
		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc004_searchProductsByAddress")
	public void tc_API_004_searchProductsByAddress(String endPoint, String partnerId, String requestJSON, String Address) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifyAddress("hits", "FormattedAddress", Address);
		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc005_searchProductsByAddress")
	public void tc_API_005_searchProductsByAddress(String endPoint, String partnerId, String requestJSON, String Address, String hits, String expectedPage) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifyAddress("hits", "FormattedAddress", Address);
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		SouthwestSearch.verifyPageNumber("page", expectedPage);
		
	}
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc006_queryProductsByPrice")
	public void tc_API_006_queryProductsByPrice(String endPoint, String partnerId, String requestJSON, String query, String min, String max) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifyPrice("hits", "Price", query, min, max);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc007_queryProductsByRating")
	public void tc_API_007_queryProductsByRating(String endPoint, String partnerId, String requestJSON, String query, String rating, String hits) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifyRating("hits", "AvgRating", query, rating);
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc008_queryProductsByReviews")
	public void tc_API_008_queryProductsByReviews(String endPoint, String partnerId, String requestJSON, String query, String min, String max) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifyReviews("hits", "Reviews", query, min, max);		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc009_queryProductsByTriedandTrue")
	public void tc_API_009_queryProductsByTriedandTrue(String endPoint, String partnerId, String requestJSON, String query, String triedAndTrue) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifyTriedandTrue("hits", "TriedAndTrueGuarantee", query, triedAndTrue);		
	}
	
		
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc010_searchProductsByWebId")
	public void tc_API_010_searchProductsByWebId(String endPoint, String partnerId, String requestJSON, String hits, String location, String webId) throws ParseException {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifySearchByWebId("hits", "formattedAddress", location, webId);
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc011_searchProductsByLatLong")
	public void tc_API_011_searchProductsByLatLong(String endPoint, String partnerId, String requestJSON, String hits, String location, String latLong) throws ParseException {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc012_sortProductsByPriceLowHigh")
	public void tc_API_012_sortProductsByPriceLowHigh(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifySortByPriceLowHigh("hits", "Price");
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		SouthwestSearch.verifyPageNumber("page", expectedPage);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc013_sortProductsByPriceHighLow")
	public void tc_API_013_sortProductsByPriceHighLow(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifySortByPriceHighLow("hits", "Price");
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		SouthwestSearch.verifyPageNumber("page", expectedPage);
	}
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc014_sortProductsByPopularity")
	public void tc_API_014_sortProductsByPopularity(String endPoint, String partnerId, String requestJSON, String hits) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifySortByPopularity("hits", "Special");
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
	}
	

	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc015_sortProductsByRecommended")
	public void tc_API_015_sortProductsByRecommended(String endPoint, String partnerId, String requestJSON, String hits) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifySortByRecommended("hits", "Special");
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc016_sortProductsByTopRated")
	public void tc_API_016_sortProductsByTopRated(String endPoint, String partnerId, String requestJSON, String hits) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
			
		SouthwestSearch.verifySortByTopRated("hits", "Special");
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc017_sortProductsByPriceLowHighFilteredByPriceRange")
	public void tc_API_017_sortProductsByPriceLowHighFilteredByPriceRange(String endPoint, String partnerId, String requestJSON, String hits, String min, String max) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
		
		SouthwestSearch.verifyPrice("hits", "Price", null, min, max);
		SouthwestSearch.verifySortByPriceLowHigh("hits", "Price");
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc018_sortProductsByPriceHighLowForcedHitsPerPage")
	public void tc_API_018_sortProductsByPriceHighLowForcedHitsPerPage(String endPoint, String partnerId, String requestJSON, String hits) {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
		
		SouthwestSearch.verifySortByPriceHighLow("hits", "Price");
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);		
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc019_sortProductsByPriceHighLowFilteredByLatLong")
	public void tc_API_019_sortProductsByPriceHighLowFilteredByLatLong(String endPoint, String partnerId, String requestJSON, String hits, String location, String latLong) throws ParseException {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
		
		SouthwestSearch.verifySortByPriceHighLow("hits", "Price");
		SouthwestSearch.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);		
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc020_sortProductsByPriceHighLowFilteredByWebId")
	public void tc_API_020_sortProductsByPriceHighLowFilteredByWebId(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage, String location, String WebId) throws ParseException {
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
		
		SouthwestSearch.verifySortByPriceHighLow("hits", "Price");
		SouthwestSearch.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		SouthwestSearch.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		SouthwestSearch.verifyPageNumber("page", expectedPage);
	}
		
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="tc025_searchProductsByProductId")
	public void tc_API_025_searchProductsByProductId(String endPoint, String partnerId,	String requestJSON, String expectedProductIds) throws ParseException{
		
		SouthwestSearch.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		SouthwestSearch.verifyStatusCode("200");
		SouthwestSearch.verifyStatusMessage("OK");
		
		SouthwestSearch.verifyProductsByIds(endPoint, expectedProductIds, "hits", "objectID");
	}
}	
	