package com.api.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testng.Assert;
import com.utilities.APIHelpers;

public class Search extends APIHelpers {

//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	public ConfigManager api = new ConfigManager("Api");
	JSONManager jsonObject = new JSONManager();
	List<String> headers = new ArrayList<String>();
	HashMap<String, String> response;

	public HashMap<String, String> requestGetRelatedProducts(String current_TestName, String endPoint, String partnerId,
			String productId, String hitsPerPage, String pageNumber) {

		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
		String parameters = null;

		endPoint = endPoint + "/" + productId;

		if (hitsPerPage != null && pageNumber != null)
			parameters = "?hitsPerPage=" + hitsPerPage + "&pageNumber=" + pageNumber;
		else if (hitsPerPage != null && pageNumber == null)
			parameters = "?hitsPerPage=" + hitsPerPage;
		else if (hitsPerPage == null && pageNumber != null)
			parameters = "?pageNumber=" + pageNumber;

		String requestURL;
		String subscriptionKey;

		if (!(env.equals("prod") || env.equals("preprod"))) {
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		} else {
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}

		if (parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;

		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- " + requestURL);

		if (headers != null) {
			headers.clear();
			if (partnerId != null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if (!(env.equals("prod"))) {
				headers.add("env:" + env);
			}
		}

		// execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName,
				false, "");

		System.out.println("In GET(getRelatedProducts) API response, Status code is -- " + response.get("status code"));
		System.out.println(
				"In GET(getRelatedProducts) API response, Status message is -- " + response.get("status message"));

		return response;
	}

	public void verifyStatusCode(String statusCode) {
		verifyStatusCode(response.get("status code"), statusCode);
	}

	public void verifyStatusMessage(String statusMessage) {
		verifyStatusMessage(response.get("status message"), statusMessage);
	}

	public void verifyFormattedAddress(String pNode, String key, String address) {

		List<ArrayList<String>> values = jsonObject.getListOfValuesFromArrayJsonKey(response.get("response body"),
				pNode, key);

		System.out.println("*****************************************************");

		int count = 0;

		for (int i = 0; i < values.size(); i++) {
			System.out.println("============= Array No " + (i + 1) + " is ====================");
			for (int j = 0; j < values.get(i).size(); j++) {
				System.out.println(values.get(i).get(j));
				if (values.get(i).get(j).contains(address)) {
					count++;
				}
			}
		}

		try {
			Assert.assertEquals(values.size(), count, "Out of " + values.size() + " product(s), "
					+ (values.size() - count) + " product(s) don't have correct address");
		} catch (Throwable t) {
			Assert.fail("Out of " + values.size() + " product(s), " + (values.size() - count)
					+ " product(s) don't have correct address");

		}

	}

	public void verifyHitsPerPage(String hitsPerPage) {

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "hits", "objectID");

		System.out.println("*****************************************************");

		for (int i = 0; i < objectIds.size(); i++) {

			System.out.println("Product Id " + (i + 1) + " is -- " + objectIds.get(i));

		}

		if (hitsPerPage != null) {
			try {
				Assert.assertTrue(objectIds.size() <= Integer.parseInt(hitsPerPage), "HitsPerPage is given as "
						+ hitsPerPage + ", but no of product ids returned is " + objectIds.size());
			} catch (Throwable t) {
				Assert.fail("HitsPerPage is given as " + hitsPerPage + ", but no of product ids returned is "
						+ objectIds.size());
			}
		} else {
			try {
				Assert.assertTrue(objectIds.size() <= 20, "HitsPerPage is given as " + hitsPerPage
						+ ", but no of product ids returned is " + objectIds.size());
			} catch (Throwable t) {
				Assert.fail("HitsPerPage is given as " + hitsPerPage + ", but no of product ids returned is "
						+ objectIds.size());
			}
		}
	}

	public HashMap<String, String> requestCreateProductSearch(String current_TestName, String endPoint,
			String partnerId, String requestJSON, String... environment) {

		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if (environment.length == 0)
			env = BaseSetup.environment;
		else
			env = environment[0];

		String requestURL;
		String subscriptionKey;

		if (!(env.equals("prod") || env.equals("preprod"))) {
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		} else {
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		requestJSON = System.getProperty("user.dir") + requestJSON;

		if (headers != null) {
			headers.clear();
			if (partnerId != null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if (!(env.equals("prod")))
				headers.add("env:" + env);
		}

//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName,
				false, "", requestJSON);
		System.out.println(
				"In Post(Create Product Search) API response, Status code is -- " + response.get("status code"));
		System.out.println(
				"In Post(Create Product Search) API response, Status message is -- " + response.get("status message"));

		return response;

	}

	public HashMap<String, String> requestCreateProductSearchWithTags(String current_TestName, String endPoint,
			String partnerId, String requestJSON, String tags, String... environment) {

		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if (environment.length == 0)
			env = BaseSetup.environment;
		else
			env = environment[0];

		String requestURL;
		String subscriptionKey;

		if (!(env.equals("prod") || env.equals("preprod"))) {
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		} else {
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}

		requestJSON = System.getProperty("user.dir") + requestJSON;

		if (headers != null) {
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			headers.add(tags);
			if (!(env.equals("prod")))
				headers.add("env:" + env);
		}

//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName,
				false, "", requestJSON);
		System.out.println(
				"In Post(Create Product Search) API response, Status code is -- " + response.get("status code"));
		System.out.println(
				"In Post(Create Product Search) API response, Status message is -- " + response.get("status message"));

		return response;

	}

	public HashMap<String, String> requestCreateProductSearchWithVendor(String current_TestName, String endPoint,
			String partnerId, String requestJSON, String vendors, String... environment) {

		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if (environment.length == 0)
			env = BaseSetup.environment;
		else
			env = environment[0];

		String requestURL;
		String subscriptionKey;

		if (!(env.equals("prod") || env.equals("preprod"))) {
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		} else {
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}

		requestJSON = System.getProperty("user.dir") + requestJSON;

		if (headers != null) {
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			headers.add(vendors);
			if (!(env.equals("prod")))
				headers.add("env:" + env);
		}

//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName,
				false, "", requestJSON);
		System.out.println(
				"In Post(Create Product Search) API response, Status code is -- " + response.get("status code"));
		System.out.println(
				"In Post(Create Product Search) API response, Status message is -- " + response.get("status message"));

		return response;

	}

	public void verifyProductVendor(String pNode, String key, String vendors) {

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		int count = 0;
		System.out.println("============= Filter Product Search by Vendor Id [" + vendors + "] ====================");
		String[] expectedVendors = vendors.split(",");
		for (int i = 0; i < objectIds.size(); i++) {
			for (String vendor : expectedVendors) {
				if (objectIds.get(i).contains(vendor.trim())) {
					System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i)
							+ " has matching Vendor Id [" + vendor + "]");
					count++;
					break;
				}
			}
		}
		try {
			Assert.assertEquals(objectIds.size(), count);
		} catch (Throwable t) {
			Assert.fail(
					"Out of " + objectIds.size() + ", " + (objectIds.size() - count) + " don't have matching vendor");
		}
		System.out.println("*****************************************************");

	}

	public void verifyTagsInProducts(String pNode, String key, String tags) {

		List<ArrayList<String>> tagList = jsonObject.getValueFromArrayJsonKey2(response.get("response body"), pNode,
				key);

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		int count = 0;
		System.out.println("============= Filter Product Search by Tags [" + tags + "] ====================");
		String[] expectedTags = tags.split(",");
		try {
			tagList.get(0).size();
		} catch (NullPointerException e) {
			Assert.fail("_tags key holding null!");
		}

		for (int i = 0; i < objectIds.size(); i++) {
			productLevel: for (int j = 0; j < tagList.get(i).size(); j++) {
				for (String tag : expectedTags) {
					if (tagList.get(i).get(j).contains(tag.trim())) {
						System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i) + " has matching tag ["
								+ tag + "]");
						count++;
						break productLevel;
					}
				}
			}
		}
		try {
			Assert.assertEquals(tagList.size(), count);
		} catch (Throwable t) {
			Assert.fail("Out of " + tagList.size() + ", " + (tagList.size() - count) + " don't have matching tags");
		}
		System.out.println("*****************************************************");
	}

	public void verifyNoOfObjecIdsInResponse(String pNode, String key, String count) {

		List<String> values = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, key);

		System.out.println("*****************************************************");

		for (int i = 0; i < values.size(); i++) {

			System.out.println(
					"In Post(Create Product Search) API response, Product Id " + (i + 1) + " is -- " + values.get(i));

		}
		try {
			Assert.assertTrue(values.size() <= Integer.valueOf(count),
					"We've got " + values.size() + " product ids in the response");
		} catch (Throwable t) {
			Assert.fail("We've got " + values.size() + " product ids in the response");

		}

	}

	public void verifyCategory(String pNode, String key, String category) {

		List<ArrayList<String>> values = jsonObject.getListOfValuesFromArrayJsonKey(response.get("response body"),
				pNode, key);

		System.out.println("*****************************************************");

		int count = 0;

		for (int i = 0; i < values.size(); i++) {
			System.out.println("============= Array No " + (i + 1) + " is ====================");
			boolean categoryPresent = false;
			for (int j = 0; j < values.get(i).size(); j++) {
				System.out.println(values.get(i).get(j));
				for (int k = 0; k < category.split(",").length; k++) {
					if (values.get(i).contains(category.split(",")[k].trim())) {
						categoryPresent = true;
						break;
					}
				}
			}
			if (categoryPresent) {
				count++;
			}
		}

		Assert.assertEquals(values.size(), count,
				"Out of " + values.size() + ", " + (values.size() - count) + " don't have correct category");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		System.out.println("*****************************************************");

		for (int i = 0; i < objectIds.size(); i++) {

			System.out.println("Product Id " + (i + 1) + " is -- " + objectIds.get(i));

		}

	}

	public void verifyPageNumber(String key, String expectedPage) {

		List<String> obtainedPage = jsonObject.getValueFromSimpleJsonKey(response.get("response body"), key);

		Assert.assertEquals(obtainedPage.get(0), expectedPage,
				"Page [" + obtainedPage.get(0) + "] displayed instead of page [" + expectedPage + "]");
		System.out.println("Page [" + obtainedPage.get(0) + "] displayed as expected");
	}

	public void verifyDuration(String pNode, String key, String min, String max) {

		List<Double> durations = jsonObject.getValueFromArrayJsonKey3(response.get("response body"), "hits",
				"DurationInMinutes");

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "hits", "objectID");

		int count = 0;

		for (int i = 0, j = 0; i < durations.size() && j < objectIds.size(); i++, j++) {
			System.out.println("============= Create Product Search by Duration min: " + min + " max: " + max
					+ " Result " + (i + 1) + " ====================");
			System.out.println(
					"Product " + (i + 1) + " with Id " + objectIds.get(i) + " has a duration of " + durations.get(i));
			if (durations.get(i) >= Double.valueOf(min) && durations.get(i) <= Double.valueOf(max)) {
				count++;
			}
		}
		try {
			Assert.assertEquals(durations.size(), count);
		} catch (Throwable t) {
			Assert.fail("Out of " + durations.size() + ", " + (durations.size() - count)
					+ " products don't have duration within specified range");
		}
		System.out.println("*****************************************************");
	}

	public void verifyAddress(String pNode, String key, String Address) throws ParseException {

		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsArray = (JSONArray) object.get("hits");
		Iterator<JSONObject> i = hitsArray.iterator();
		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		for (int j = 0; j < objectIds.size(); j++) {
			while (i.hasNext()) {
				JSONObject locationObject = i.next();
				JSONObject pplocation = (JSONObject) locationObject.get("ppLocation");
				String address = (String) pplocation.get("formattedAddress");

				System.out.println("============= Create Product Search by " + address + " Result " + (j + 1)
						+ " ====================");

				Assert.assertNotNull(address, "formattedAddress key holds null");

				System.out.println(
						"Product " + (j + 1) + " with Id " + objectIds.get(j) + " has matching address " + address);

				j++;

			}
		}

	}

	public void verifyPrice(String pNode, String key, String query, String min, String max) throws ParseException {

		List<Double> prices = jsonObject.getValueFromArrayJsonKey3(response.get("response body"), "hits", "Price");

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "hits", "objectID");
		List<String> productTexts = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode,
				"ProductText");

		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsArray = (JSONArray) object.get("hits");
		Iterator<JSONObject> k = hitsArray.iterator();
		int count = 0;

		for (int i = 0, j = 0; i < prices.size() - 1 && j < objectIds.size() - 1;) {

			while (k.hasNext()) {
				JSONObject locationObject = k.next();
				JSONObject pplocation = (JSONObject) locationObject.get("ppLocation");
				String address = (String) pplocation.get("formattedAddress");

				if (query != null) {
					System.out.println("============= Create Product Search by query: " + query + " Price min: " + min
							+ " max: " + max + " Result " + (i + 1) + " ====================");
					try {
						Assert.assertNotNull(address, "FormattedAddress key holds null");
					} catch (Throwable t) {
						Assert.fail("Product " + (i + 1) + " with Id " + objectIds.get(i)
								+ " does not have address matching in given query: " + query);
					}

					System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i)
							+ " has address/product description matching given query: " + query + " with price "
							+ prices.get(i));
					i++;
					j++;
				}

			}

		}
	}

	public void verifyRating(String pNode, String key, String query, String rating) {

		List<Object> ratings = jsonObject.getValueAsObjectFromArrayJsonKey(response.get("response body"), "hits",
				"AvgRating");

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "hits", "objectID");
		List<ArrayList<String>> addresses = jsonObject.getValueFromArrayJsonKey2(response.get("response body"), "hits",
				"FormattedAddress");

		int count = 0;

		for (int i = 0, j = 0; i < ratings.size() && j < objectIds.size(); i++, j++) {
			System.out.println("============= Create Product Search by query: " + query + " Rating : " + rating
					+ " Result " + (i + 1) + " ====================");
			if (query != null) {
				try {
					Assert.assertTrue(addresses.get(i).get(0).contains(query));
				} catch (Throwable t) {
					Assert.fail("Product " + (i + 1) + " with Id " + objectIds.get(i)
							+ " does not have address matching in given query: " + query);
				}
				System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i)
						+ " has address matching given query: " + query + " with rating " + ratings.get(i));
			}
			if (rating.contains(String.format("%.0f", ratings.get(i)))) {
				System.out.println(
						"Product " + (i + 1) + " with Id " + objectIds.get(i) + " has rating " + ratings.get(i));
				count++;
			}
		}
		try {
			Assert.assertEquals(ratings.size(), count);
		} catch (Throwable t) {
			Assert.fail("Out of " + ratings.size() + ", " + (ratings.size() - count)
					+ " products don't have rating within specified range");
		}
		System.out.println("*****************************************************");
	}

	public void verifyReviews(String pNode, String key, String query, String min, String max) throws ParseException {

		List<Double> reviews = jsonObject.getValueFromArrayJsonKey3(response.get("response body"), pNode, key);

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");
		List<String> productTexts = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode,
				"ProductText");
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsArray = (JSONArray) object.get("hits");
		Iterator<JSONObject> k = hitsArray.iterator();
		int count = 0;
		System.out.println("*****************************************************");

		for (int i = 0, j = 0; i < reviews.size() && j < objectIds.size();) {

			while (k.hasNext()) {
				JSONObject locationObject = k.next();
				JSONObject pplocation = (JSONObject) locationObject.get("ppLocation");
				String address = (String) pplocation.get("formattedAddress");

				System.out.println("============= Create Product Search by query: " + query + " Reviews min: " + min
						+ " max: " + max + " Result " + (i + 1) + " ====================");
				try {
					Assert.assertNotNull(address, "FormattedAddress key holds null");
				} catch (Throwable t) {//

					Assert.fail("Product " + (i + 1) + " with Id " + objectIds.get(i)
							+ " does not have address/product description matching given query: " + query);
				}
				System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i)
						+ " has address/product description matching given query: " + query + " with price "
						+ reviews.get(i));
				i++;
				j++;
			}
			System.out.println("*****************************************************");
		}
	}

	public void verifyTriedandTrue(String pNode, String key, String query, String triedAndTrue) {

		@SuppressWarnings("unchecked")
		List<Boolean> triedAndTrues = (List<Boolean>) (List<?>) jsonObject
				.getValueAsObjectFromArrayJsonKey(response.get("response body"), pNode, key);

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");
		List<String> productTexts = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode,
				"ProductText");
		List<ArrayList<String>> addresses = jsonObject.getValueFromArrayJsonKey2(response.get("response body"), "hits",
				"FormattedAddress");

		int count = 0;
		String[] queryArray = null;
		if (query.contains(" ")) {
			queryArray = query.split(" ");
		}
		boolean specialQuery = false;
		for (int i = 0, j = 0; i < triedAndTrues.size() && j < objectIds.size(); i++, j++) {
			System.out.println("============= Create Product Search by query: " + query + " Tried & True : "
					+ triedAndTrue + " Result " + (i + 1) + " ====================");
			try {
				Assert.assertTrue(addresses.get(i).get(0).contains(query) || productTexts.get(j).contains(query));
			} catch (Throwable t) {
				if (query.contains(" ")) {
					for (int k = 0; k < queryArray.length; k++) {
						if (addresses.get(i).get(0).contains(queryArray[k])
								|| productTexts.get(j).contains(queryArray[k])) {
							specialQuery = true;
							System.out.println("'" + queryArray[k] + "' found in Address/Product Description");
							break;
						}
					}
					Assert.assertTrue(specialQuery);
				} else {
					Assert.fail("Product " + (i + 1) + " with Id " + objectIds.get(i)
							+ " does not have address/product description matching given query: " + query);
				}
			}
			System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i)
					+ " has address/product description matching given query: " + query + " with Tried & True = "
					+ triedAndTrues.get(i).toString());
			if (triedAndTrues.get(i) == Boolean.parseBoolean(triedAndTrue)) {
				count++;
			}
		}
		try {
			Assert.assertEquals(triedAndTrues.size(), count);
		} catch (Throwable t) {
			Assert.fail("Out of " + triedAndTrues.size() + ", " + (triedAndTrues.size() - count)
					+ " products don't have matching Tried & True Guarantee criteria");
		}
		System.out.println("*****************************************************");
	}

	public void verifySearchByWebId(String pNode, String key, String location, String webId) throws ParseException {

		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsArray = (JSONArray) object.get("hits");
		Iterator<JSONObject> i = hitsArray.iterator();

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		for (int j = 0; j < objectIds.size(); j++) {
			while (i.hasNext()) {
				JSONObject locationObject = i.next();
				JSONObject pplocation = (JSONObject) locationObject.get("ppLocation");
				String address = (String) pplocation.get("formattedAddress");

				System.out.println("============= Create Product Search by WebId [" + webId + "](" + location
						+ ") Result " + (j + 1) + " ====================");

				Assert.assertNotNull(address, "formattedAddress key holds null");

				System.out.println("Product " + (j + 1) + " with Id " + objectIds.get(j) + " has matching webId ["
						+ webId + "](" + address + ")");
				j++;
			}
		}
		System.out.println("*****************************************************");
	}

	public void verifySearchByLatLong(String pNode, String key, String location, String latLong) throws ParseException {

		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray hitsArray = (JSONArray) object.get("hits");
		Iterator<JSONObject> i = hitsArray.iterator();

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		for (int j = 0; j < objectIds.size(); j++) {
			while (i.hasNext()) {
				JSONObject locationObject = i.next();
				JSONObject pplocation = (JSONObject) locationObject.get("ppLocation");
				String address = (String) pplocation.get("formattedAddress");

				System.out.println("============= Create Product Search by WebId [" + latLong + "](" + location
						+ ") Result " + (j + 1) + " ====================");

				Assert.assertNotNull(address, "formattedAddress key holds null");

				System.out.println("Product " + (j + 1) + " with Id " + objectIds.get(j) + " has matching webId ["
						+ latLong + "](" + address + ")");
				j++;
			}
		}

		System.out.println("*****************************************************");

	}

	@SuppressWarnings("unchecked")
	public void verifySortByPriceLowHigh(String pNode, String key) {

		List<Double> prices = jsonObject.getValueFromArrayJsonKey3(response.get("response body"), pNode, key);

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		@SuppressWarnings("rawtypes")
		List temp = new ArrayList(prices);
		Collections.sort(temp);

		for (int i = 0; i < objectIds.size(); i++) {
			System.out.println(
					"============= Sort Products by Price Low-High: Result " + (i + 1) + " ====================");
			System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i) + " has price $" + prices.get(i));
		}
		try {
			Assert.assertTrue(temp.equals(prices), "");
		} catch (Throwable t) {
			Assert.fail("Products are NOT returned in Prices Low-High sorted order");
		}
		System.out.println("*****************************************************");
		System.out.println("Products are returned in Prices Low-High sorted order");

	}

	@SuppressWarnings("unchecked")
	public void verifySortByPriceHighLow(String pNode, String key) {

		List<Double> prices = jsonObject.getValueFromArrayJsonKey3(response.get("response body"), pNode, key);

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		@SuppressWarnings("rawtypes")
		List temp = new ArrayList(prices);
		Collections.reverse(temp);
		Collections.sort(temp);
		Collections.reverse(temp);

		for (int i = 0; i < objectIds.size(); i++) {
			System.out.println(
					"============= Sort Products by Price High-Low: Result " + (i + 1) + " ====================");
			System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i) + " has price $" + prices.get(i));
		}
		try {
			Assert.assertTrue(temp.equals(prices), "");
		} catch (Throwable t) {
			Assert.fail("Products are NOT returned in Prices High-Low sorted order");
		}
		System.out.println("*****************************************************");
		System.out.println("Products are returned in Prices High-Low sorted order");

	}

	@SuppressWarnings("unchecked")
	public void verifySortByPopularity(String pNode, String key) {

		List<Long> special = (List<Long>) (List<?>) jsonObject
				.getValueAsObjectFromArrayJsonKey(response.get("response body"), pNode, key);

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		@SuppressWarnings("rawtypes")
		List temp = new ArrayList(special);
		Collections.reverse(temp);
		try {
			Collections.sort(temp);
			Collections.reverse(temp);
		} catch (NullPointerException e) {
			Assert.fail("Sorted by the Special key returns null");
		}

		for (int i = 0; i < objectIds.size(); i++) {
			System.out
					.println("============= Sort Products by Popularity: Result " + (i + 1) + " ====================");
			System.out.println(
					"Product " + (i + 1) + " with Id " + objectIds.get(i) + " has Special attribute " + special.get(i));
		}
		System.out.println("*****************************************************");
		System.out.println("Products are sorted by Popularity");

	}

	@SuppressWarnings("unchecked")
	public void verifySortByRecommended(String pNode, String key) {

		List<Long> special = (List<Long>) (List<?>) jsonObject
				.getValueAsObjectFromArrayJsonKey(response.get("response body"), pNode, key);

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		@SuppressWarnings("rawtypes")
		List temp = new ArrayList(special);
		Collections.reverse(temp);
		Collections.sort(temp);
		Collections.reverse(temp);

		for (int i = 0; i < objectIds.size(); i++) {
			System.out
					.println("============= Sort Products by Recommended: Result " + (i + 1) + " ====================");
			System.out.println(
					"Product " + (i + 1) + " with Id " + objectIds.get(i) + " has Special attribute " + special.get(i));
		}
		try {
			Assert.assertTrue(temp.equals(special), "");
		} catch (Throwable t) {
			Assert.fail("Products are NOT sorted by Recommended");
		}
		System.out.println("*****************************************************");
		System.out.println("Products are sorted by Recommended");

	}

	@SuppressWarnings("unchecked")
	public void verifySortByTopRated(String pNode, String key) {

		List<Long> special = (List<Long>) (List<?>) jsonObject
				.getValueAsObjectFromArrayJsonKey(response.get("response body"), pNode, key);

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, "objectID");

		@SuppressWarnings("rawtypes")
		List temp = new ArrayList(special);
		Collections.reverse(temp);
		Collections.sort(temp);
		Collections.reverse(temp);

		for (int i = 0; i < objectIds.size(); i++) {
			System.out.println("============= Sort Products by Top Rated: Result " + (i + 1) + " ====================");
			System.out.println(
					"Product " + (i + 1) + " with Id " + objectIds.get(i) + " has Special attribute " + special.get(i));
		}
		try {
			Assert.assertTrue(temp.equals(special), "");
		} catch (Throwable t) {
			Assert.fail("Products are NOT sorted by Top Rated");
		}
		System.out.println("*****************************************************");
		System.out.println("Products are sorted by Top Rated");

	}

	public void verifyProductIds(String[] expectedProductIds, String... key_List) {

		if (expectedProductIds != null) {
			for (int i = 0; i < expectedProductIds.length; i++) {
				System.out.println("Expected Object Id " + (i + 1) + " is " + expectedProductIds[i]);
			}
		}

		verifyStatusCode("200");
		verifyStatusMessage("OK");

		if (expectedProductIds != null) {

			List<String> objectIds = jsonObject.getValueFromArrayJsonKeyWithoutParentNode(response.get("response body"),
					key_List);

			for (int i = 0; i < objectIds.size(); i++) {
				System.out.println("Actual Object Id " + (i + 1) + " is " + objectIds.get(i));
			}

			Assert.assertEquals(objectIds.size(), expectedProductIds.length,
					"Actual no of product ids in the response " + objectIds.size()
							+ " is not matching with the expected no of product ids " + expectedProductIds.length);

			for (int i = 0; i < expectedProductIds.length; i++) {
				Assert.assertTrue(objectIds.contains(expectedProductIds[i].trim()),
						"Actual list of product ids don't contain the productid \"" + expectedProductIds[i]
								+ "\" given in the request JSON.");
			}
		} else {
			System.out.println("There are no product ids in the request JSON");
		}
	}

	public void requestGetProducts(String current_TestName, String endPoint, String partnerId, String requestJSON,
			String expectedProductIds) {

		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;

		String requestURL;
		String subscriptionKey;

		if (!(env.equals("prod") || env.equals("preprod"))) {
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		} else {
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}

		requestJSON = System.getProperty("user.dir") + requestJSON;
		String requestBody = jsonObject.readJSONContentToString(requestJSON);

		String[] productIds = null;
		if (expectedProductIds != null)
			productIds = expectedProductIds.split(",");

		requestBody = jsonObject.updateListOfValuesInArrayJsonKey(requestBody, "productIds", productIds);

		if (headers != null) {
			headers.clear();
			if (partnerId != null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if (!(env.equals("prod")))
				headers.add("env:" + env);
		}

//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName,
				true, requestBody, requestJSON);
		System.out.println("In Post(Get Products) API response, Status code is -- " + response.get("status code"));
		System.out
				.println("In Post(Get Products) API response, Status message is -- " + response.get("status message"));
	}

	public HashMap<String, String> requestProductsByIds(String current_TestName, String endPoint, String partnerId) {

		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;

		String requestURL;
		String subscriptionKey;

		if (!(env.equals("prod") || env.equals("preprod"))) {
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		} else {
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}

		if (headers != null) {
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if (!(env.equals("prod")))
				headers.add("env:" + env);
		}

//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName,
				false, "");
		System.out.println("In GET(Products by Ids) API response, Status code is -- " + response.get("status code"));
		System.out.println(
				"In GET(Products by Ids) API response, Status message is -- " + response.get("status message"));

		return response;
	}

	public HashMap<String, String> requestCreateProductSearchWebId(String current_TestName, String endPoint,
			String partnerId, String requestBody, String requestJSON, String... environment) {

		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if (environment.length == 0)
			env = BaseSetup.environment;
		else
			env = environment[0];

		String requestURL;
		String subscriptionKey;

		if (!(env.equals("prod") || env.equals("preprod"))) {
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		} else {
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		requestJSON = System.getProperty("user.dir") + requestJSON;

		if (headers != null) {
			headers.clear();
			if (partnerId != null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if (!(env.equals("prod")))
				headers.add("env:" + env);
		}

//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName,
				true, requestBody, requestJSON);
		System.out.println(
				"In Post(Create Product Search) API response, Status code is -- " + response.get("status code"));
		System.out.println(
				"In Post(Create Product Search) API response, Status message is -- " + response.get("status message"));

		return response;

	}

	public void verifyProductsByIds(String endPoint, String expectedProductIds, String pNode, String key)
			throws ParseException {

		String[] productIds = null;

		if (expectedProductIds != null) {

			productIds = expectedProductIds.split(",");

			List<String> products = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, key);

			System.out.println("*****************************************************");

			int count = 0;

			for (String productId : productIds) {
				if (products.contains(productId.trim())) {
					count++;
					System.out.println("ProductId " + productId.trim() + " is returned in response JSON");
				}
			}

			try {
				Assert.assertEquals(products.size(), count, "Out of " + products.size() + " product(s), "
						+ (products.size() - count) + " product Id(s) don't match");
				System.out.println(count + " out of " + products.size() + " products are returned in response JSON");
				System.out.println("*****************************************************");
			} catch (Throwable t) {
				Assert.fail("Out of " + products.size() + " product(s), " + (products.size() - count)
						+ " product Id(s) don't match");
			}
		} else {
			String responseString = response.get("response body");
			if (responseString != null) {
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(responseString);
				Object product = (Object) object.get("products");

				try {
					Assert.assertEquals(product, null, "When invalid products are passed, response is NOT null");
					System.out.println("Response is null since invalid products are passed");
					System.out.println("*****************************************************");
				} catch (Throwable t) {
					Assert.fail("When invalid products are passed, response is NOT null");
				}
			} else {
				System.out.println("Response is null since zero products are passed");
				System.out.println("*****************************************************");
			}
		}
	}

	/**
	 * Return nearby webid & city
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String[] getNearbyCity() throws ParseException {
		String responseString = response.get("response body");

		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);

		JSONArray shelves = (JSONArray) object.get("shelves");
		String nearByObject[] = new String[2];

		if (shelves != null) {

			if (shelves.size() == 2) {
				JSONObject nearbyCity = (JSONObject) shelves.get(1);
				String link = nearbyCity.get("link").toString();
				nearByObject[0] = link.split("/destination/")[1];
				nearByObject[1] = nearbyCity.get("title").toString().split(" ")[2];
			} else {
				for (int i = 0; i < shelves.size(); i++) {
					JSONObject nearbyCity = (JSONObject) shelves.get(i);
					String link = nearbyCity.get("link").toString();
					nearByObject[0] = link.split("/destination/")[1];
					nearByObject[1] = nearbyCity.get("title").toString().split(" ")[2];
				}
			}
		}
		return nearByObject;
	}

	public void verifyProductPrice(String pNode, String key, String query, String max, String min) {

		List<Double> prices = jsonObject.getValueFromArrayJsonKey3(response.get("response body"), "hits", "Price");

		System.out.println("*****************************************************");

		List<String> objectIds = jsonObject.getValueFromArrayJsonKey(response.get("response body"), "hits", "objectID");
		List<String> productTexts = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode,
				"ProductText");
		List<ArrayList<String>> addresses = jsonObject.getValueFromArrayJsonKey2(response.get("response body"), "hits",
				"FormattedAddress");

		int count = 0;

		for (int i = 0, j = 0; i < prices.size() && j < objectIds.size(); i++, j++) {
			if (query != null) {
				System.out.println("============= Create Product Search by query: " + query + " Price min: " + min
						+ " max: " + max + " Result " + (i + 1) + " ====================");
				try {
					Assert.assertTrue(addresses.get(i).get(0).contains(query)
							|| productTexts.get(j).toLowerCase().contains(query.toLowerCase()));
				} catch (Throwable t) {
					Assert.fail("Product " + (i + 1) + " with Id " + objectIds.get(i)
							+ " does not have address matching in given query: " + query);
				}
				System.out.println("Product " + (i + 1) + " with Id " + objectIds.get(i)
						+ " has address/product description matching given query: " + query + " with price "
						+ prices.get(i));
			}
			System.out.println("*****************************************************");
		}
	}

	/**
	 * 1. Check pp-code 2. Check pp-message
	 */
	public void verifyPPCodePPMessage(String code, String message) {

		String ppcode = response.get("pp-code");
		Assert.assertEquals(code, ppcode,
				"Expected code '" + code + "' and obtained code '" + ppcode + "' doesn't match");
		System.out.println("Obtained code '" + code + "' is as expected");

		String ppmessage = response.get("pp-message");
		Assert.assertEquals(message, ppmessage,
				"Expected message '" + message + "' and obtained message '" + ppmessage + "' doesn't match");
		System.out.println("Obtained message '" + message + "' is as expected");
	}
}
