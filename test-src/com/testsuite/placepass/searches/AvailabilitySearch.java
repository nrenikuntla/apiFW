package com.testsuite.placepass.searches;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BaseSetup;
import com.api.category.Product;
import com.api.category.Search;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class AvailabilitySearch extends BaseSetup {
	
	public String current_TestName;
	Search search;
	Product product;
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		current_TestName = method.getName();
		search = new Search();
		product = new Product();		
	}
	
	/**
	 * 1.availableDates only, hitsPerPage = 20
	 * 2.availableDates only, hitsPerPage = 50
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_AvailabilitySearch")
	public void tc_API_001_availabilitySearchUsingPostMethod(String endPoint, String partnerId, String requestJSON, String hits, String month, String year) throws ParseException {
		
		HashMap<String, String> response = search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
	
	//	Verify hitsPerPage	
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message
			product.verifyDateRange("availabilityList", "date", month, year);
		}		
	}
	
	
	/**
	 * 1.availableDates with [In the Air] category filter
	 * 2.availableDates with [On the Water] category filter
	 * 3.availableDates with [Amusement Parks] category filter
	 * 4.availableDates with [Culinary Tours, Nature & Adventure] categories filter
	 * 5.availableDates with [On the Water] category filter, hitsPerPage=1 & pageNumber=2
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_AvailabilitySearchByCategory")
	public void tc_API_002_availabilitySearchByCategory(String endPoint, String partnerId, String requestJSON, String category, String hits, String expectedPage, String month, String year) throws ParseException {
		
		HashMap<String, String> response =search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
	
	//	Verify category filter	
		search.verifyCategory("hits", "Category", category);
	//	Verify hitsPerPage	
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	//	Verify pageNumber	
		if(expectedPage!=null)
			search.verifyPageNumber("page", expectedPage);
				
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
				
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);
		}
	}
	
	
	/**
	 * 1.availableDays with [60-90] duration filter
	 * 2.availableDays with [0-60] duration filter
	 * 3.availableDays with [0-90] duration filter
	 * 4.availableDays with [0-1440] duration filter, [200-500] price filter, [4,5] ratings filter & hitsPerPage = 50
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_AvailabilitySearchByDuration")
	public void tc_API_003_availabilitySearchByDuration(String endPoint, String partnerId, String requestJSON, String min, String max,
			String hits, String minPrice, String maxPrice, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
	//	Verify duration filter	
		search.verifyDuration("hits", "DurationInMinutes", min, max);
	//	Verify hitsPerPage	
		if(hits!=null)
			search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	//	Verify price filter	
		if(minPrice!=null || maxPrice!=null)
			search.verifyPrice("hits", "Price", null, minPrice, maxPrice);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
				
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message			
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDates with [Hawaii, US] location
	 * 2.availableDates with [Amsterdam, Netherlands] location
	 * 3.availableDates with [Florence, Italy] location
	 * 4.availableDates with [Hyderabad, India] location, isBookable=true, hitsPerPage = 10 & pageNumber = 2
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_AvailabilitySearchByAddress")
	public void tc_API_004_availabilitySearchByAddress(String endPoint, String partnerId, String requestJSON, String address,
			String hits, String expectedPage, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyAddress("hits", "formattedAddress", address);
	//	Verify hitsPerPage	
		if(hits!=null)
			search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
	//	Verify pageNumber	
		if(expectedPage!=null)
			search.verifyPageNumber("page", expectedPage);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
	
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);
		}
	}
	
	
	/**
	 * 1.availableDays with [10-60] price filter & query [Hyderabad]
	 * 2.availableDays with [60-100] price filter & query [Hyderabad]
	 * 3.availableDays with [100-200] price filter & query [Hyderabad]
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_AvailabilitySearch_QueryProductsByPrice")
	public void tc_API_005_availabilitySearch_QueryProductsByPrice(String endPoint, String partnerId, String requestJSON,
			String query, String min, String max, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
	//	Verify price filter		
		search.verifyPrice("hits", "Price", query, min, max);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
				
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDays with [5] ratings filter & query [Hyderabad]
	 * 2.availableDays with [1,2,3] ratings filter & hitsPerPage = 50
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC006_AvailabilitySearch_QueryProductsByRating")
	public void tc_API_006_availabilitySearch_QueryProductsByRating(String endPoint, String partnerId, String requestJSON,
			String query, String rating, String hits, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyRating("hits", "AvgRating", query, rating);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDays with [5-10] reviews filter & query [Hyderabad]
	 * 2.availableDays with [500-1000] reviews filter & query [New York]
	 * 3.availableDays with [150-250] reviews filter & query [Dubai]
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC007_AvailabilitySearch_QueryProductsByReviews")
	public void tc_API_007_availabilitySearch_QueryProductsByReviews(String endPoint, String partnerId, String requestJSON,
			String query, String min, String max, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyReviews("hits", "Reviews", query, min, max);		
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result	
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message		
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDates with query [New York] & triedAndTrue [true]
	 * 2.availableDates with query [Dubai] & triedAndTrue [true]
	 * 3.availableDates with query [New York] & triedAndTrue [false]
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC008_AvailabilitySearch_QueryProductsByTriedandTrue")
	public void tc_API_008_availabilitySearch_QueryProductsByTriedandTrue(String endPoint, String partnerId, String requestJSON,
			String query, String triedAndTrue, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifyTriedandTrue("hits", "TriedAndTrueGuarantee", query, triedAndTrue);
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result		
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);
		}
	}
	
		
	/**
	 * 1.availableDates with location (kl) & hitsPerPage = 10
	 * 2.availableDates with location (93), pageNumber = 1 & hitsPerPage = 15
	 * 3.availableDates with location (g2) & hitsPerPage = 50
	 * 4.availableDates with location (w1) & hitsPerPage = 50
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC009_AvailabilitySearch_SearchProductsByWebId")
	public void tc_API_009_availabilitySearch_SearchProductsByWebId(String endPoint, String partnerId, String requestJSON,
			String hits, String expectedPage, String location, String webId, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySearchByWebId("hits", "formattedAddress", location, webId);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		if(expectedPage!=null)
			search.verifyPageNumber("page", expectedPage);
				
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
				
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result	
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDays with location (lat & long) & hitsPerPage = 25
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC010_AvailabilitySearch_SearchProductsByLatLong")
	public void tc_API_010_availabilitySearch_SearchProductsByLatLong(String endPoint, String partnerId, String requestJSON,
			String hits, String location, String latLong, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
				
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result	
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDates with sortBy (pricelowhigh), hitsPerPage = 50 & pageNumber = 1
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC011_AvailabilitySearch_SortProductsByPriceLowHigh")
	public void tc_API_011_availabilitySearchsortProductsByPriceLowHigh(String endPoint, String partnerId, String requestJSON,
			String hits, String expectedPage, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
			search.verifyPageNumber("page", expectedPage);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result		
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message		
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDates with sortBy (pricehighlow)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC012_AvailabilitySearch_SortProductsByPriceHighLow")
	public void tc_API_012_availabilitySearch_SortProductsByPriceHighLow(String endPoint, String partnerId, String requestJSON,
			String hits, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
				
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			if(!productid.startsWith("AHFvOF")){
				System.out.println("--------------------------------------------------------------------------------");
				System.out.println(endPoint="/products/"+productid+"/availability");
			
			//	Get Availability of each product from search result	
				product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
			//	Verify response code and message		
				product.verifyDateRange("availabilityList", "date", month, year);
			}	
		}
	}
	
	
	/**
	 * 1.availableDates with sortBy (popularity)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC013_AvailabilitySearch_SortProductsByPopularity")
	public void tc_API_013_availabilitySearch_SortProductsByPopularity(String endPoint, String partnerId, String requestJSON,
			String hits, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByPopularity("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDates with sortBy (recommended)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC014_AvailabilitySearch_SortProductsByRecommended")
	public void tc_API_014_availabilitySearch_SortProductsByRecommended(String endPoint, String partnerId, String requestJSON,
			String hits, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByRecommended("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
				
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message
			product.verifyDateRange("availabilityList", "date", month, year);		
		}		
	}
	
	
	/**
	 * 1.availableDates with sortBy (topRated)
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC015_AvailabilitySearch_SortProductsByTopRated")
	public void tc_API_015_availabilitySearch_SortProductsByTopRated(String endPoint, String partnerId, String requestJSON,
			String hits, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
			
		search.verifySortByTopRated("hits", "Special");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);		
		}		
	}
	
	
	/**
	 * 1.availableDates with sortBy (pricelowhigh), (60-120) price filter & hitsPerPage = 50
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC016_AvailabilitySearch_SortProductsByPriceLowHighFilteredByPriceRange")
	public void tc_API_016_availabilitySearch_sortProductsByPriceLowHighFilteredByPriceRange(String endPoint, String partnerId, String requestJSON, String hits, String min, String max,String month,String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyPrice("hits", "Price", null, min, max);
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);		
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDates with sortBy (pricehighlow) & hitsPerPage = 50
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC017_AvailabilitySearch_SortProductsByPriceHighLowForcedHitsPerPage")
	public void tc_API_017_AvailabilitySearch_SortProductsByPriceHighLowForcedHitsPerPage(String endPoint, String partnerId, String requestJSON, String hits,String month,String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
				
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			if(!productid.startsWith("AHFvOF")){
				System.out.println("--------------------------------------------------------------------------------");
				System.out.println(endPoint="/products/"+productid+"/availability");
			
			//	Get Availability of each product from search result
				product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
			//	Verify response code and message
				product.verifyDateRange("availabilityList", "date", month, year);
			}	
		}
	}
	
	
	/**
	 * 1.availableDates with sortBy (pricehighlow) & location (lat & long) 
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC018_AvailabilitySearch_SortProductsByPriceHighLowFilteredByLatLong")
	public void tc_API_018_availabilitySearch_SortProductsByPriceHighLowFilteredByLatLong(String endPoint, String partnerId, String requestJSON,
			String hits, String location, String latLong, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifySearchByLatLong("hits", "formattedAddress", location, latLong);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message
			product.verifyDateRange("availabilityList", "date", month, year);		
		}	
	}
	
	
	/**
	 * 1.availableDates with sortBy (pricehighlow), location (webid=93), hitsPerPage = 15 & pageNumber = 1
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC019_AvailabilitySearch_SortProductsByPriceHighLowFilteredByWebId")
	public void tc_API_019_availabilitySearch_SortProductsByPriceHighLowFilteredByWebId(String endPoint, String partnerId, String requestJSON,
			String hits, String expectedPage, String location, String WebId, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceHighLow("hits", "Price");
		search.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
			search.verifyPageNumber("page", expectedPage);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			if(!productid.startsWith("AHFvOF")){
				System.out.println("--------------------------------------------------------------------------------");
				System.out.println(endPoint="/products/"+productid+"/availability");
			
			//	Get Availability of each product from search result
				product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
			//	Verify response code and message
				product.verifyDateRange("availabilityList", "date", month, year);
			}	
		}
	}
	
	
	/**
	 * 1.availableDates with sortBy (pricelowhigh), location (webid=q), price filter (80-10000), query = art, hitsPerPage = 20 & pageNumber = 0
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC020_AvailabilitySearch_SortProductsByPriceLowHighFilteredByWebIdQueryPrice")
	public void tc_API_020_availabilitySearch_SortProductsByPriceLowHighFilteredByWebIdQueryPrice(String endPoint, String partnerId, String requestJSON,
			String hits, String expectedPage, String location, String WebId, String query, String min, String max, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearch(current_TestName, endPoint, partnerId, requestJSON);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifySortByPriceLowHigh("hits", "Price");
		search.verifySearchByWebId("hits", "formattedAddress", location, WebId);
		search.verifyPrice("hits", "price", query, min, max);
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		if(expectedPage!=null)
			search.verifyPageNumber("page", expectedPage);
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			response = product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message	
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
	
	/**
	 * 1.availableDates with vendor-id header (Viator)
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC021_AvailabilitySearch_FilterProductsByVendorId")
	public void tc_API_021_availabilitySearch_FilterProductsByVendorId(String endPoint, String partnerId, String requestJSON,
			String vendorIdHeader, String vendor, String hits, String month, String year) throws ParseException {
		
		HashMap<String, String> response=search.requestCreateProductSearchWithVendor(current_TestName, endPoint, partnerId, requestJSON, vendorIdHeader);
		
	//	Verify response code and message
		search.verifyStatusCode("200");
		search.verifyStatusMessage("OK");
		
		search.verifyNoOfObjecIdsInResponse("hits", "objectID", hits);
		search.verifyProductVendor("hits", "objectID", vendor);	
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsKey = (JSONArray) object.get("hits");
		for(int i=0;i<hitsKey.size();i++)
		{
			JSONObject productObj = (JSONObject) hitsKey.get(i);
			String productid = productObj.get("objectID").toString();
			
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(endPoint="/products/"+productid+"/availability");
		
		//	Get Availability of each product from search result
			product.requestGetAvailability(current_TestName, endPoint, partnerId, month, year);
		//	Verify response code and message		
			product.verifyDateRange("availabilityList", "date", month, year);		
		}
	}
	
}
