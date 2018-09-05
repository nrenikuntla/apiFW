package com.testsuite.searchmigration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Search;
import com.api.category.Product;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class SearchMigration extends BaseSetup{
	
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Search search;
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
		search = new Search();
		product = new Product();
		currentTestName = method.getName();		
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc001_searchProducts")
	public void tc_API_001_searchProductsUsingPostMethod(String endPoint, String partnerId, String requestJSON, String hits) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
				
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS",(o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}	
		

	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc002_searchProductsByCategory")
	public void tc_API_002_searchProductsByCategory(String endPoint, String partnerId, String requestJSON, String category, String hits, String expectedPage) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyCategory("hits", "Category", category);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyCategory("hits", "Category", category);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
               new Customization("nbHits", (o1, o2) -> true)));        
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc003_searchProductsByDuration")
	public void tc_API_003_searchProductsByDuration(String endPoint, String partnerId, String requestJSON, String min, String max) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyDuration("hits", "DurationInMinutes", min, max);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyDuration("hits", "DurationInMinutes", min, max);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc004_searchProductsByAddress")
	public void tc_API_004_searchProductsByAddress(String endPoint, String partnerId, String requestJSON, String Address) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyAddress("hits", "FormattedAddress", Address);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyAddress("hits", "formattedAddress", Address);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc005_searchProductsByAddress")
	public void tc_API_005_searchProductsByAddress(String endPoint, String partnerId, String requestJSON, String Address, String hits, String expectedPage) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyAddress("hits", "FormattedAddress", Address);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyAddress("hits", "formattedAddress", Address);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc006_queryProductsByPrice")
	public void tc_API_006_queryProductsByPrice(String endPoint, String partnerId, String requestJSON, String query, String min, String max) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyPrice("hits", "Price", query, min, max);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyPrice("hits", "Price", query, min, max);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc007_queryProductsByRating")
	public void tc_API_007_queryProductsByRating(String endPoint, String partnerId, String requestJSON, String query, String rating, String hits) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyRating("hits", "AvgRating", query, rating);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyRating("hits", "AvgRating", query, rating);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc008_queryProductsByReviews")
	public void tc_API_008_queryProductsByReviews(String endPoint, String partnerId, String requestJSON, String query, String min, String max) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyReviews("hits", "Reviews", query, min, max);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyReviews("hits", "Reviews", query, min, max);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc009_queryProductsByTriedandTrue")
	public void tc_API_009_queryProductsByTriedandTrue(String endPoint, String partnerId, String requestJSON, String query, String triedAndTrue) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyTriedandTrue("hits", "TriedAndTrueGuarantee", query, triedAndTrue);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyTriedandTrue("hits", "TriedAndTrueGuarantee", query, triedAndTrue);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc010_searchProductsByWebId")
	public void tc_API_010_searchProductsByWebId(String endPoint, String partnerId, String requestJSON, String hits, String location, String webId) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySearchByWebId("hits", "formattedAddress", location, webId);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySearchByWebId("hits", "formattedAddress", location, webId);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc011_searchProductsByLatLong")
	public void tc_API_011_searchProductsByLatLong(String endPoint, String partnerId, String requestJSON, String hits, String location, String latLong) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc012_sortProductsByPriceLowHigh")
	public void tc_API_012_sortProductsByPriceLowHigh(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc013_sortProductsByPriceHighLow")
	public void tc_API_013_sortProductsByPriceHighLow(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc014_sortProductsByPopularity")
	public void tc_API_014_sortProductsByPopularity(String endPoint, String partnerId, String requestJSON, String hits) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPopularity("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPopularity("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc015_sortProductsByRecommended")
	public void tc_API_015_sortProductsByRecommended(String endPoint, String partnerId, String requestJSON, String hits) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByRecommended("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByRecommended("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc016_sortProductsByTopRated")
	public void tc_API_016_sortProductsByTopRated(String endPoint, String partnerId, String requestJSON, String hits) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByTopRated("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByTopRated("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc017_sortProductsByPriceLowHighFilteredByPriceRange")
	public void tc_API_017_sortProductsByPriceLowHighFilteredByPriceRange(String endPoint, String partnerId, String requestJSON, String hits, String min, String max) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyPrice("hits", "Price", null, min, max);
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyPrice("hits", "Price", null, min, max);
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc018_sortProductsByPriceHighLowForcedHitsPerPage")
	public void tc_API_018_sortProductsByPriceHighLowForcedHitsPerPage(String endPoint, String partnerId, String requestJSON, String hits) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);		
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc019_sortProductsByPriceHighLowFilteredByLatLong")
	public void tc_API_019_sortProductsByPriceHighLowFilteredByLatLong(String endPoint, String partnerId, String requestJSON, String hits, String location, String latLong) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc020_sortProductsByPriceHighLowFilteredByWebId")
	public void tc_API_020_sortProductsByPriceHighLowFilteredByWebId(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage, String location, String WebId) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyPageNumber("page", expectedPage);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyPageNumber("page", expectedPage);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc021_sortProductsByPriceLowHighFilteredByWebIdQueryPrice")
	public void tc_API_021_sortProductsByPriceLowHighFilteredByWebIdQueryPrice(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage, String location, String WebId, String query, String min, String max) throws JSONException, ParseException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		search.verifyPrice("hits", "price", query, min, max);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyPageNumber("page", expectedPage);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearch(currentTestName, endPoint, partnerId, requestJSON, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		search.verifyPrice("hits", "price", query, min, max);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyPageNumber("page", expectedPage);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc022_filterProductsByTags")
	public void tc_API_022_filterProductsByTags(String endPoint, String partnerId, String requestJSON, String tagsHeader, String tags) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearchWithTags(currentTestName, endPoint, partnerId, requestJSON, tagsHeader, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyTagsInProducts("hits", "_tags", tags);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearchWithTags(currentTestName, endPoint, partnerId, requestJSON, tagsHeader, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyTagsInProducts("hits", "_tags", tags);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * @throws JSONException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc023_filterProductsByVendorId")
	public void tc_API_023_filterProductsByVendorId(String endPoint, String partnerId, String requestJSON, String vendorIdHeader, String vendors, String hits) throws JSONException {
		
		HashMap<String,String> algoliaResponse = search.requestCreateProductSearchWithVendor(currentTestName, endPoint, partnerId, requestJSON, vendorIdHeader, "prod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyProductVendor("hits", "objectID", vendors);
		
		HashMap<String,String> elasticResponse = search.requestCreateProductSearchWithVendor(currentTestName, endPoint, partnerId, requestJSON, vendorIdHeader, "preprod");
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyProductVendor("hits", "objectID", vendors);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
	/**
	 * Compare Get product details endpoint
	 * @throws JSONException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_getProductsDetails")
	public void tc_API_001_getProductsDetailsUsingGetMethod(String endPoint, String partnerId, String language, String productid) throws JSONException {
		
		HashMap<String,String> algoliaResponse = product.requestGetProductDetails(currentTestName, endPoint, partnerId, language, "prod");
		
//		Verify response code and message
		product.verifyStatusCode("200");
		product.verifyStatusMessage("OK");
		
		product.verifyProductDetails("productId", "retailPrice", "finalPrice", "webLocation", language, productid);
		
		HashMap<String,String> elasticResponse = product.requestGetProductDetails(currentTestName, endPoint, partnerId, language, "preprod");
		
//		Verify response code and message
		product.verifyStatusCode("200");
		product.verifyStatusMessage("OK");
		
		product.verifyProductDetails("productId", "retailPrice", "finalPrice", "webLocation", language, productid);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));			
	}
	
	
	/**
	 * Compare Get hotel details endpoint
	 * @throws JSONException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_getHotelDetails")
	public void tc_API_001_getHotelDetailsUsingGetMethod(String endPoint, String partnerId, String productId) throws JSONException{
		
		HashMap<String,String> algoliaResponse = product.requestGetHotelDetails(currentTestName, endPoint, partnerId, "prod");
		
//		Verify response code and message
		product.verifyStatusCode("200");
		product.verifyStatusMessage("OK");		
		
		product.verifyHotelDetails("hotelDetails", productId);
		
		
		HashMap<String,String> elasticResponse = product.requestGetHotelDetails(currentTestName, endPoint, partnerId, "preprod");
		
//		Verify response code and message
		product.verifyStatusCode("200");
		product.verifyStatusMessage("OK");		
		
		product.verifyHotelDetails("hotelDetails", productId);
		
		JSONAssert.assertEquals(algoliaResponse.get("response body"),elasticResponse.get("response body"),new CustomComparator(JSONCompareMode.LENIENT,
	               new Customization("params", (o1, o2) -> true), new Customization("processingTimeMS", (o1,o2) -> true),
	               new Customization("exhaustiveFacetsCount", (o1, o2) -> true), new Customization("facets", (o1, o2) -> true),
	               new Customization("exhaustiveNbHits", (o1, o2) -> true), new Customization("**.UpdateOn", (o1, o2) -> true),
	               new Customization("nbHits", (o1, o2) -> true)));
	}
	
	
}