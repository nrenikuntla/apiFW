package com.testsuite.placepass.searches;

import java.lang.reflect.Method;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Data;
import com.api.category.Search;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.JSONManager;
import com.testng.Assert;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class SearchProducts extends BaseSetup {
	
	public String current_TestName;
	Search search;
	Data data;
	public ConfigManager api;
	
	JSONManager jsonObject;
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		current_TestName = method.getName();
		search = new Search();
		jsonObject = new JSONManager();
		data = new Data();
	}
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc001_searchProducts")
	public void tc_API_001_searchProductsUsingPostMethod(String endPoint, String partnerId, String requestJSON, String hits) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
	}
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc002_searchProductsByCategory")
	public void tc_API_002_searchProductsByCategory(String endPoint, String partnerId, String requestJSON, String category, String hits, String expectedPage) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyCategory("hits", "Category", category);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc003_searchProductsByDuration")
	public void tc_API_003_searchProductsByDuration(String endPoint, String partnerId, String requestJSON, String min, String max) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyDuration("hits", "DurationInMinutes", min, max);
		
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc004_searchProductsByAddress")
	public void tc_API_004_searchProductsByAddress(String endPoint, String partnerId, String requestJSON, String Address) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyAddress("hits", "formattedAddress", Address);
		
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc005_searchProductsByAddress")
	public void tc_API_005_searchProductsByAddress(String endPoint, String partnerId, String requestJSON, String Address, String hits, String expectedPage) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyAddress("hits", "formattedAddress", Address);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
		
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc006_queryProductsByPrice")
	public void tc_API_006_queryProductsByPrice(String endPoint, String partnerId, String requestJSON, String query, String min, String max) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyPrice("hits", "Price", query, min, max);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc007_queryProductsByRating")
	public void tc_API_007_queryProductsByRating(String endPoint, String partnerId, String requestJSON, String query, String rating, String hits) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyRating("hits", "AvgRating", query, rating);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc008_queryProductsByReviews")
	public void tc_API_008_queryProductsByReviews(String endPoint, String partnerId, String requestJSON, String query, String min, String max) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyReviews("hits", "Reviews", query, min, max);		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc009_queryProductsByTriedandTrue")
	public void tc_API_009_queryProductsByTriedandTrue(String endPoint, String partnerId, String requestJSON, String query, String triedAndTrue) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyTriedandTrue("hits", "TriedAndTrueGuarantee", query, triedAndTrue);		
	}
	
		
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc010_searchProductsByWebId")
	public void tc_API_010_searchProductsByWebId(String endPoint, String partnerId, String requestJSON, String hits, String location, String webId) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySearchByWebId("hits", "formattedAddress", location, webId);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc011_searchProductsByLatLong")
	public void tc_API_011_searchProductsByLatLong(String endPoint, String partnerId, String requestJSON, String hits, String location, String latLong) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc012_sortProductsByPriceLowHigh")
	public void tc_API_012_sortProductsByPriceLowHigh(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc013_sortProductsByPriceHighLow")
	public void tc_API_013_sortProductsByPriceHighLow(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
		search.verifyPageNumber("page", expectedPage);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc014_sortProductsByPopularity")
	public void tc_API_014_sortProductsByPopularity(String endPoint, String partnerId, String requestJSON, String hits) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPopularity("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc015_sortProductsByRecommended")
	public void tc_API_015_sortProductsByRecommended(String endPoint, String partnerId, String requestJSON, String hits) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByRecommended("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc016_sortProductsByTopRated")
	public void tc_API_016_sortProductsByTopRated(String endPoint, String partnerId, String requestJSON, String hits) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByTopRated("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc017_sortProductsByPriceLowHighFilteredByPriceRange")
	public void tc_API_017_sortProductsByPriceLowHighFilteredByPriceRange(String endPoint, String partnerId, String requestJSON, String hits, String min, String max) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc018_sortProductsByPriceHighLowForcedHitsPerPage")
	public void tc_API_018_sortProductsByPriceHighLowForcedHitsPerPage(String endPoint, String partnerId, String requestJSON, String hits) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);		
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc019_sortProductsByPriceHighLowFilteredByLatLong")
	public void tc_API_019_sortProductsByPriceHighLowFilteredByLatLong(String endPoint, String partnerId, String requestJSON, String hits, String location, String latLong) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);		
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc020_sortProductsByPriceHighLowFilteredByWebId")
	public void tc_API_020_sortProductsByPriceHighLowFilteredByWebId(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage, String location, String WebId) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyPageNumber("page", expectedPage);
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc021_sortProductsByPriceLowHighFilteredByWebIdQueryPrice")
	public void tc_API_021_sortProductsByPriceLowHighFilteredByWebIdQueryPrice(String endPoint, String partnerId, String requestJSON, String hits, String expectedPage, String location, String WebId, String query, String min, String max) throws ParseException {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		search.verifyPrice("hits", "price", query, min, max);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyPageNumber("page", expectedPage);
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc022_filterProductsByTags")
	public void tc_API_022_filterProductsByTags(String endPoint, String partnerId, String requestJSON, String tagsHeader, String tags) {
		
		search.requestCreateProductSearchWithTags(current_TestName, endPoint, partnerId, requestJSON, tagsHeader);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyTagsInProducts("hits", "_tags", tags);		
	}
	
	
	/**
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc023_filterProductsByVendorId")
	public void tc_API_023_filterProductsByVendorId(String endPoint, String partnerId, String requestJSON, String vendorIdHeader, String vendors, String hits) {
		
		search.requestCreateProductSearchWithVendor(current_TestName, endPoint, partnerId, requestJSON, vendorIdHeader);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyProductVendor("hits", "objectID", vendors);		
	}
	
	
	/**
	 * Without partner-id
	 * Invalid partner-id
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc024_queryWithoutAndInvalidPartnerId")
	public void tc_API_024_queryWithoutAndInvalidPartnerId(String endPoint, String partnerId,
			String requestJSON, String status, String message, String ppcode, String ppmessage) {
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode(status);
		search.verifyStatusMessage(message);
		
		search.verifyPPCodePPMessage(ppcode, ppmessage);
	}
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc025_searchProductsByProductId")
	public void tc_API_025_searchProductsByProductId(String endPoint, String partnerId,	String requestJSON, String expectedProductIds) throws ParseException{
		
		search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyProductsByIds(endPoint, expectedProductIds, "hits", "objectID");
	}
	
	
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc026_searchProductsForNearby")
	public void tc_API_026_searchProductsByWebId(String endPoint, String partnerId, String requestJSON,String webId, String city) throws ParseException{
		
		String RequestJSON = System.getProperty("user.dir") + requestJSON;
		String requestBody = jsonObject.readJSONContentToString(RequestJSON);
		
	//	Update JSON with current webId 
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(requestBody);
		
		Object location = (Object)object.get("location");
		String locationJSON = location.toString();
		locationJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(locationJSON, "webId", webId);
		requestBody = jsonObject.updateJSONValueInJsonKey(requestBody, "location", locationJSON);
	    
		search.requestCreateProductSearchWebId(current_TestName, endPoint, partnerId,requestBody,requestJSON);
	  
	// 	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
	  
		String[] nearbyCity = search.getNearbyCity();
		
		if(nearbyCity[0]!=null){
		
		//	Lookup Partner Place Config
			endPoint = "/data-api/partners/places/config";
			
			data.requestLookupPartnerPlaceConfig(current_TestName, endPoint, partnerId, webId, "1", null, null);
				
		//	Verify response code and message
			data.verifyStatusCode("200");
			data.verifyStatusMessage("OK");
			
			Object bostongeoLocation[] = data.getLatLong();
			
			data.requestLookupPartnerPlaceConfig(current_TestName, endPoint, partnerId, nearbyCity[0], "1", null, null);
			
		//	Verify response code and message
			data.verifyStatusCode("200");
			data.verifyStatusMessage("OK");
				
			Object cambridgegeoLocation[] = data.getLatLong();
				
			data.verifyNearness(bostongeoLocation,cambridgegeoLocation, webId, city, nearbyCity[0], nearbyCity[1]);
		}
		else{
			System.out.println("No nearby destination from destination ["+city+" - "+webId+"]"); 
		}
	}	
	

	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="tc027_searchProductsForNearbyLatLng")
	public void tc_API_027_searchProductsByLatLng(String endPoint, String partnerId, String requestJSON,String latitude,String longitude,String webId, String city) throws ParseException{
		
		String RequestJSON = System.getProperty("user.dir") + requestJSON;
		String requestBody = jsonObject.readJSONContentToString(RequestJSON);
		
	//	Update JSON with lat long 
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(requestBody);
		
		Object location = (Object)object.get("location");
		String locationJSON = location.toString();
		locationJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(locationJSON, "latitude",latitude);
		locationJSON  = jsonObject.updateListOfValuesInSimpleJsonKey(locationJSON, "longitude",longitude);
		requestBody = jsonObject.updateJSONValueInJsonKey(requestBody, "location", locationJSON);
	   
	    search.requestCreateProductSearchWebId(current_TestName, endPoint, partnerId,requestBody,requestJSON);
	  
	// 	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		String[] nearbyCity = search.getNearbyCity();
		
		if(nearbyCity[0]!=null){
		
		//	Lookup Partner Place Config
			endPoint = "/data-api/partners/places/config";
			
			data.requestLookupPartnerPlaceConfig(current_TestName, endPoint, partnerId, webId, "1", null, null);
				
		//	Verify response code and message
			data.verifyStatusCode("200");
			data.verifyStatusMessage("OK");
			
			Object bostongeoLocation[] = data.getLatLong();
			
			data.requestLookupPartnerPlaceConfig(current_TestName, endPoint, partnerId, nearbyCity[0], "1", null, null);
			
		//	Verify response code and message
			data.verifyStatusCode("200");
			data.verifyStatusMessage("OK");
				
			Object cambridgegeoLocation[] = data.getLatLong();
				
			data.verifyNearness(bostongeoLocation,cambridgegeoLocation, webId, city, nearbyCity[0], nearbyCity[1]);
		}
		else{
			System.out.println("No nearby destination from destination ["+city+" - "+webId+"]"); 
		}
	}	
}