package com.api.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.JSONManager;
import com.testng.Assert;
import com.utilities.APIHelpers;

public class Data extends APIHelpers{
	
//	Declaration of respective API Parts instances
	public ConfigManager api = new ConfigManager("Api");
	JSONManager jsonObject = new JSONManager();
	List<String> headers = new ArrayList<String>();
	HashMap<String, String> response;
	
	
	public HashMap<String, String> requestGetAllCountryTypePlaces(String current_TestName, String endPoint, String partnerId){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}	
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(GetAllCountryTypePlaces) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(GetAllCountryTypePlaces) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	
	public void verifyStatusCode(String statusCode){
		verifyStatusCode(response.get("status code"), statusCode);
	}
	
	public void verifyStatusMessage(String statusMessage){
		verifyStatusMessage(response.get("status message"), statusMessage);
	}
	
	public void verifyCountriesReturned(String expectedCountries,String... key_List){
		
		Set<String> placeIds = new HashSet<String>(jsonObject.getValueFromArrayJsonKeyWithoutParentNode(response.get("response body"), key_List));
				
		Assert.assertTrue(placeIds.size()>Integer.valueOf(expectedCountries), "Less than "+expectedCountries+" countries have been returned");
		System.out.println(placeIds.size()+" countries have been returned");
		System.out.println("--------Place Ids---------");
		for(String placeId:placeIds){
			System.out.println(placeId);
		}
		System.out.println("--------------------------");
	}
	
	
	public HashMap<String, String> requestGetTopDestinations(String current_TestName, String endPoint, String partnerId){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		//Force env Stage1 since API not ready on other envs
		String env = "stage1";
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}	
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(GetTopDestinations) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(GetTopDestinations) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	
	
	public HashMap<String, String> requestGetPlaceByLatLng(String current_TestName, String endPoint, String partnerId, String lat, String lng){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env=BaseSetup.environment;
		String parameters=null;
		
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") ;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") ;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		if(lat!=null&&lng!=null ){
			parameters = "?lat="+lat+"&lng="+lng ;
		}
		
		if(parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
		System.out.println("In GET(GetPlaceByLatLng) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(GetPlaceByLatLng) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	
	/**
	 * 1. Verify ShortName matches corresponding location
 	 * 2. Verify LongName matches corresponding location
 	 * @throws ParseException 
	 */
	public void verifyPlaces(String longName, String shortName) throws ParseException
	{
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String obtainedLongName = (String) object.get("LongName");
		String obtainedShortName=(String) object.get("ShortName");
		
		Assert.assertEquals(longName,obtainedLongName,"Expected LongName "+longName+" but got"+obtainedLongName);
		System.out.println("Obtained longName "+obtainedLongName+" is as expected");
		
		Assert.assertEquals(shortName,obtainedShortName,"Expected shortName "+shortName+" but got"+obtainedShortName);
		System.out.println("Obtained shortName "+obtainedShortName+" is as expected");				
	}

	
	/**
	 * 1. Check eight placepass regions are returned
 	 * 2. Check atleast one destination is returned per region
 	 * 3. Check City/Region/Country keys hold valid data
	 * @throws ParseException 
	 */
	public void verifyTopDestinations(String pNode, String key, String minRegions, String regions) throws ParseException{
		
		List<String> topDestinations = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, key);
		
		String[] expectedRegions = regions.split(",");
		
		System.out.println("*****************************************************");
		int count = 0;
		for(String expectedRegion: expectedRegions){
			if(topDestinations.contains(expectedRegion.trim())){
				count++;
				System.out.println("PlacePass region "+expectedRegion.trim()+" is returned in response JSON");
			}
		}
		System.out.println("*****************************************************");
		try{
			Assert.assertEquals(topDestinations.size(), count, "Out of "+topDestinations.size()+" region(s), "+(topDestinations.size() - count)+" region(s) don't match");
			System.out.println(count+" out of "+topDestinations.size()+" regions are returned in response JSON");
			System.out.println("*****************************************************");
		}catch(Throwable t){
			Assert.fail("Out of "+topDestinations.size()+" region(s), "+(topDestinations.size() - count)+" region(s) don't match");
		}
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray regionArray = (JSONArray) object.get("top_destinations");
		Iterator<JSONObject> i = regionArray.iterator();
		
		while (i.hasNext())
		{
			JSONObject destinationsObject = i.next();
		    JSONArray destinations = (JSONArray) destinationsObject.get("Destinations");
		    Iterator<JSONObject> j = destinations.iterator();
		    		    
		    Assert.assertTrue(destinations.size()>0,"Region "+destinationsObject.get("PlacePassRegion")+" does not have any destinations");
		    System.out.println("Region "+destinationsObject.get("PlacePassRegion")+" has "+destinations.size()+" destinations");
		    System.out.println("---------------------");
		    
		    while (j.hasNext())
		    {
		    	JSONObject destination = j.next();		    	
		    	Object country = destination.get("Country");
		    	Assert.assertNotNull(country,"Invalid country name 'null' returned");
		    	System.out.println(country);		    	
		    }
		    System.out.println("---------------------");
		}			
	}
	
	
	public HashMap<String, String> requestLookupPartnerConfig(String current_TestName, String endPoint, String partnerId, String fields, String env){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		// Get env from test method until API is ready on other environments
		if(env==null)
			env = BaseSetup.environment;
		String parameters = null;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		if(fields!=null){
			parameters = "?fields="+ fields;
		}
		
		if(parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(LookupPartnerConfig) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(LookupPartnerConfig) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	
	/**
	 * 1. Check default hero image is linked
	 * 2. Check title says "Stay in the"
	 * 3. Check subtitle says "moment"
	 * 4. Check Featured Products & Destinations keys are nulls 
	 * @throws ParseException 
	 */
	public void verifyPartnerConfigHeroParam(String title, String subtitle) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONObject hero = (JSONObject) object.get("Hero");
		
		Object image = hero.get("Image");		
		Assert.assertTrue(image.toString().toLowerCase().contains("hero"),"Instead of default hero.png, "+image.toString()+" is linked");
		System.out.println("Default hero.png is linked");

		Object obtainedTitle = hero.get("Title");
		Assert.assertTrue(obtainedTitle.toString().contains(title),"Instead of default title 'Discover the World', "+obtainedTitle.toString()+" has loaded");
		System.out.println("Default title 'Discover the World' has loaded");
		
		Object obtainedSubtitle = hero.get("Subtitle");
		Assert.assertTrue(obtainedSubtitle.toString().contains(subtitle),"Instead of default title 'WITH MARRIOTT'S COLLECTION OF AMAZING TRAVEL EXPERIENCES', "+obtainedSubtitle.toString()+" has loaded");
		System.out.println("Default subtitle 'WITH MARRIOTT'S COLLECTION OF AMAZING TRAVEL EXPERIENCES' has loaded");
		
		Object featuredProducts = object.get("FeaturedProducts");
		Assert.assertTrue(featuredProducts==null,"FeaturedProducts key doesn't hold null as expected");
		System.out.println("FeaturedProducts key holds null as expected");
		
		Object destinations = object.get("Destinations");
		Assert.assertTrue(destinations==null,"Destinations key doesn't hold null as expected");
		System.out.println("Destinations key holds null as expected");
	}
	
	
	/**
	 * 1. Check atleast one product is available under Featured Products
	 * 2. Check Hero & Destinations keys are nulls  
	 * @throws ParseException 
	 */
	public void verifyPartnerConfigFeaturedProductsParam() throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray featuredProducts = (JSONArray) object.get("FeaturedProducts");
		
		Assert.assertTrue(featuredProducts.size()>0,"FeaturedProducts key doesn't hold any products");
		System.out.println("FeaturedProducts key holds "+featuredProducts.size()+" products");
		
		Iterator<JSONObject> i = featuredProducts.iterator();
		System.out.println("-------------");
		while (i.hasNext()){			
			JSONObject product = i.next();		    	
	    	Object objectID = product.get("objectID");
	    	System.out.println(objectID);
		}
		System.out.println("-------------");
		
		Object hero = object.get("Hero");
		Assert.assertTrue(hero==null,"Hero key doesn't hold null as expected");
		System.out.println("Hero key holds null as expected");
		
		Object destinations = object.get("Destinations");
		Assert.assertTrue(destinations==null,"Destinations key doesn't hold null as expected");
		System.out.println("Destinations key holds null as expected");
	}
	
	/**
	 * 1. Check atleast one destination is available under Destinations
	 * 2. Check Hero & FeaturedProducts keys are nulls  
	 * @throws ParseException 
	 */
	public void verifyPartnerConfigDestinationsParam() throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONArray destinations = (JSONArray) object.get("Destinations");
		
		Assert.assertTrue(destinations.size()>0,"Destinations key doesn't hold any destinations");
		System.out.println("Destinations key holds "+destinations.size()+" destinations");
		
		Iterator<JSONObject> i = destinations.iterator();
		System.out.println("-------------");
		while (i.hasNext()){			
			JSONObject destinationObj = i.next();		    	
	    	Object destination = destinationObj.get("Title");
	    	System.out.println(destination);
		}
		System.out.println("-------------");
		
		Object hero = object.get("Hero");
		Assert.assertTrue(hero==null,"Hero key doesn't hold null as expected");
		System.out.println("Hero key holds null as expected");
		
		Object featuredProducts = object.get("FeaturedProducts");
		Assert.assertTrue(featuredProducts==null,"FeaturedProducts key doesn't hold null as expected");
		System.out.println("FeaturedProducts key holds null as expected");
	}	
	
	/**
	 * 1. Check default hero image is linked
	 * 2. Check title says "Stay in the"
	 * 3. Check subtitle says "moment"
	 * 4. Check atleast one product is available under Featured Products
	 * 5. Check atleast one destination is available under Destinations
	 * 6. If extendedAttributes is true & shelves contains atleast 1 product, 
	 * 		check if Marriott or SPG or a mix of those products are displayed
	 * 7. Check atleast 1 shelftype is StubHub
	 * 8. Check each StubHub shelf has atleast 1 product
	 * 9. Verify 'PLACEPASSS' text is present in affiliate id (gcid)  
	 * @throws ParseException 
	 */
	public void verifyPartnerConfigNoParam(String title, String subtitle) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONObject hero = (JSONObject) object.get("Hero");
		
		Object image = hero.get("Image");		
		Assert.assertTrue(image.toString().toLowerCase().contains("hero"),"Instead of default hero, "+image.toString()+" is linked");
		System.out.println("Default hero image is linked");

		Object obtainedTitle = hero.get("Title");
		Assert.assertTrue(obtainedTitle.toString().contains(title),"Instead of default title 'Discover the World', "+obtainedTitle.toString()+" has loaded");
		System.out.println("Default title 'Discover the World' has loaded");
		
		Object obtainedSubtitle = hero.get("Subtitle");
		Assert.assertTrue(obtainedSubtitle.toString().contains(subtitle),"Instead of default title 'WITH MARRIOTT'S COLLECTION OF AMAZING TRAVEL EXPERIENCES', "+obtainedSubtitle.toString()+" has loaded");
		System.out.println("Default subtitle 'WITH MARRIOTT'S COLLECTION OF AMAZING TRAVEL EXPERIENCES' has loaded");
		
		JSONArray featuredProducts = (JSONArray) object.get("FeaturedProducts");
		
		Assert.assertTrue(featuredProducts.size()>0,"FeaturedProducts key doesn't hold any products");
		System.out.println("FeaturedProducts key holds "+featuredProducts.size()+" products");
		
		Iterator<JSONObject> i = featuredProducts.iterator();
		System.out.println("-------------");
		while (i.hasNext()){			
			JSONObject product = i.next();		    	
	    	Object objectID = product.get("objectID");
	    	System.out.println(objectID);
		}
		System.out.println("-------------");
		
		JSONArray destinations = (JSONArray) object.get("Destinations");
		
		Assert.assertTrue(destinations.size()>0,"Destinations key doesn't hold any destinations");
		System.out.println("Destinations key holds "+destinations.size()+" destinations");
		
		Iterator<JSONObject> j = destinations.iterator();
		System.out.println("-------------");
		while (j.hasNext()){			
			JSONObject destinationObj = j.next();		    	
	    	Object destination = destinationObj.get("Title");
	    	System.out.println(destination);
		}
		System.out.println("-------------");	
		
		JSONObject extendedAttributes = (JSONObject) object.get("extendedAttributes");
		JSONArray shelves = (JSONArray) extendedAttributes.get("shelves");
		
		Assert.assertTrue(shelves.size()>0,"Shelves key doesn't hold any shelves");
		System.out.println("Shelves key holds "+shelves.size()+" shelves");
		
		Iterator<JSONObject> k = shelves.iterator();
		int stubhubs = 0;
		while(k.hasNext()){
			JSONObject shelfObj = k.next();
			JSONArray productIdArray = (JSONArray) shelfObj.get("productIds");
			Object[] productIds;
			Object shelfType = shelfObj.get("shelfType");
			Object shelfTitle = shelfObj.get("title");
			
			if(productIdArray!=null)
				productIds = (Object[]) productIdArray.toArray();
			else
				productIds = new Object[0];
			
			if(!shelfType.toString().equals("Car Rental")){
				// Excluding Helicopter tours shelf per ENG-2278
				if(!shelfTitle.toString().startsWith("Helicopter")){
					Assert.assertTrue(productIds.length>0,"ProductIds key in '"+shelfTitle+"' of type '"+shelfType+"' doesn't hold any products");
					System.out.println("ProductIds key in '"+shelfTitle+"' of type '"+shelfType+"' holds "+productIds.length+" products");
				}
			}
			else{
				Assert.assertTrue(productIds.length==0,"ProductIds key in '"+shelfTitle+"' of type '"+shelfType+"' holds "+productIds.length+" products");
				System.out.println("ProductIds key in '"+shelfTitle+"' of type '"+shelfType+"' doesn't hold any products");
			}				
					
			if(shelfTitle.toString().contains("SPG")){
				for(int l=0; l<productIds.length;l++){
					Assert.assertTrue(productIds[l].toString().startsWith("U833cT")||productIds[l].toString().startsWith("Apo3cT"),"Shelf '"+shelfTitle+"' doesn't contain Marriott or SPG products");
					System.out.println("Shelf '"+shelfTitle+"' contains Marriott/SPG product "+productIds[l]);
				}
			}
			if(shelfType.toString().toLowerCase().contains("stubhub")){
				stubhubs++;
				Object link = shelfObj.get("link");
				Assert.assertTrue(link.toString().toLowerCase().contains("placepass"),shelfType+" shelf link key doesn't contain placepass affiliation");
				System.out.println(shelfType+" shelf link key contains placepass affiliation");
			}
		}
		Assert.assertTrue(stubhubs>0,"No StubHub shelves are displayed");
		System.out.println(stubhubs+" shelves are of type StubHub");		
	}
	
	
	public void verifyPartnerConfigNoParam_PP(String title, String subtitle) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		JSONObject hero = (JSONObject) object.get("Hero");
		
		Object image = hero.get("Image");		
		Assert.assertTrue(image.toString().toLowerCase().contains("hero"),"Instead of default hero, "+image.toString()+" is linked");
		System.out.println("Default hero image is linked");

		Object obtainedTitle = hero.get("Title");
		Assert.assertTrue(obtainedTitle.toString().contains(title),"Instead of default title 'Discover the World', "+obtainedTitle.toString()+" has loaded");
		System.out.println("Default title 'Discover the World' has loaded");
		
		Object obtainedSubtitle = hero.get("Subtitle");
		Assert.assertTrue(obtainedSubtitle.toString().contains(subtitle),"Instead of default title 'Discover the World', "+obtainedSubtitle.toString()+" has loaded");
		System.out.println("Default subtitle 'Discover the World' has loaded");
		
		JSONArray featuredProducts = (JSONArray) object.get("FeaturedProducts");
		
		Assert.assertTrue(featuredProducts.size()>0,"FeaturedProducts key doesn't hold any products");
		System.out.println("FeaturedProducts key holds "+featuredProducts.size()+" products");
		
		Iterator<JSONObject> i = featuredProducts.iterator();
		System.out.println("-------------");
		while (i.hasNext()){			
			JSONObject product = i.next();		    	
	    	Object objectID = product.get("objectID");
	    	System.out.println(objectID);
		}
		System.out.println("-------------");
		
		JSONArray destinations = (JSONArray) object.get("Destinations");
		
		Assert.assertTrue(destinations.size()>0,"Destinations key doesn't hold any destinations");
		System.out.println("Destinations key holds "+destinations.size()+" destinations");
		
		Iterator<JSONObject> j = destinations.iterator();
		System.out.println("-------------");
		while (j.hasNext()){			
			JSONObject destinationObj = j.next();		    	
	    	Object destination = destinationObj.get("Title");
	    	System.out.println(destination);
		}
		System.out.println("-------------");	
	}
	
	/*
	public void verifyPartnerPlacePropertiesCountQueryRegion(String count, String query, String region) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
	
		JSONObject Object=(JSONObject) parser.parse(responseString);
		
		JSONArray places = (JSONArray) Object.get("places");
		
		Iterator<JSONObject> i = places.iterator();
		System.out.println("-------------");
		
		while (i.hasNext()){			
			
			JSONObject place = i.next();
			
			Object location = place.get("Location");
			Assert.assertTrue(location.toString().toLowerCase().contains(query.replace("%20"," ")), "Location "+location+" doesn't contain query "+query);
			System.out.println("Location "+location+" contains query "+query+" as expected");
			
			Object regions =  place.get("Region");	        
			Assert.assertEquals(regions,region,"Expected "+regions+" but got "+ region);
		    System.out.println("Got "+regions+" as expected");
		}
		System.out.println("-------------");
	}		
	*/
	public HashMap<String, String> requestLookupPartnerPlaceConfig(String current_TestName, String endPoint, String partnerId, String webid, String guidecount, String fields, String id, String... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];
		String parameters = null;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		if(webid!=null && guidecount!=null && fields!=null){
			parameters = "?webid="+ webid + "&guidecount=" + guidecount + "&fields=" + fields;				
		}
		else if(webid!=null && guidecount!=null && id!=null){
			parameters = "?id="+ id + "&webid=" + webid + "&guidecount=" + guidecount;
		}
		else if(webid!=null && guidecount!=null){
			parameters = "?webid=" + webid + "&guidecount=" + guidecount;
		}
		else if(id!=null && guidecount!=null){
			parameters = "?id="+ id + "&guidecount=" + guidecount;
		}
		else if(fields==null && webid!=null && id!=null && guidecount!=null){
			parameters = "?webid="+ webid + "&id="+ id + "&guidecount=" + guidecount;
		}
		
		if(parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(LookupPartnerPlaceConfig) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(LookupPartnerPlaceConfig) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	
	/**
	 * 1. Check Webid
	 * 2. Check all other keys are nulls/empty  
	 * @throws ParseException 
	 */
	public void verifyPartnerPlaceConfigCityguidesParam(String webid) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object id = object.get("Id");
		Assert.assertEquals(id,webid,"Obtained web id "+id+" and supplied web id "+webid+" don't match");
		System.out.println("Obtained web id "+id+" and supplied web id "+webid+" are a match");
		
		Object cityGuideLocations = object.get("cityGuideLocations");
		Assert.assertTrue(cityGuideLocations==null,"cityGuideLocations key doesn't hold null as expected");
		System.out.println("cityGuideLocations key holds null as expected");
		
		Object extendedAttributes = object.get("extendedAttributes");
		Assert.assertTrue(extendedAttributes==null,"extendedAttributes key doesn't hold null as expected");
		System.out.println("extendedAttributes key holds null as expected");
		
		Object hero = object.get("Hero");
		Assert.assertTrue(hero==null,"Hero key doesn't hold null as expected");
		System.out.println("Hero key holds null as expected");
		
		Object location = object.get("Location");
		Assert.assertTrue(location==null,"Location key doesn't hold null as expected");
		System.out.println("Location key holds null as expected");
		
		JSONArray personalizedCollections = (JSONArray) object.get("PersonalizedCollections");
		Assert.assertTrue(personalizedCollections.size()==0,"PersonalizedCollections key is NOT empty");
		System.out.println("PersonalizedCollections key is empty");
		
		JSONArray topTips = (JSONArray) object.get("TopTips");
		Assert.assertTrue(topTips.size()==0,"TopTips key is NOT empty");
		System.out.println("TopTips key is empty");
		
		Object getToKnow = (Object) object.get("GetToKnow");
		Assert.assertEquals(getToKnow.toString(),"{}","GetToKnow key is NOT empty");
		System.out.println("GetToKnow key is empty");
	}
	
	/**
	 * 1. Check Webid
	 * 2. Check Hero image is linked
	 * 2. Check Title says Boston  
	 * @throws ParseException 
	 */
	public void verifyPartnerPlaceConfigHeroParam(String webid, String title, String hero) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object id = object.get("Id");
		Assert.assertEquals(id,webid,"Obtained web id "+id+" and supplied web id "+webid+" don't match");
		System.out.println("Obtained web id "+id+" and supplied web id "+webid+" are a match");
		
		JSONObject heroObj = (JSONObject) object.get("Hero");
		Object image = heroObj.get("Image");
		Assert.assertTrue(image.toString().toLowerCase().contains(hero),"Instead of default hero, "+image.toString()+" is linked");
		System.out.println("Default hero image "+image+" is linked");		
		
		Object titleObtained = heroObj.get("Title");
		Assert.assertEquals(titleObtained.toString(),title,"Expected title '"+title+"' but got '"+titleObtained+"'");
		System.out.println("Title '"+title+"' is as expected");		
	}	
	
	/**
	 * 1. Check Webid
	 * 2. Check Locations is 'Boston, MA'
	 * 3. Check ActivitiesCount > 0
	 * @throws ParseException 
	 */
	public void verifyPartnerPlaceConfigLocationParam(String webid, String location) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object id = object.get("Id");
		Assert.assertEquals(id,webid,"Obtained web id "+id+" and supplied web id "+webid+" don't match");
		System.out.println("Obtained web id "+id+" and supplied web id "+webid+" are a match");
		
		JSONObject locationObj = (JSONObject) object.get("Location");
		Object locationObtained = locationObj.get("Location");
		Assert.assertEquals(locationObtained.toString(),location,"Expected location '"+location+"' but got '"+locationObtained+"'");
		System.out.println("Location '"+location+"' is as expected");
		
		Object acitivitiesCount = locationObj.get("ActivitiesCount");
		Assert.assertTrue(Integer.valueOf(acitivitiesCount.toString())>0,"ActivitiesCount key holds zero");
		System.out.println("Got "+acitivitiesCount+" activities in "+location);		
	}
	
	
	/**
	 * 1. Check Webid
	 * 2. Check PersonalizedCollections key has atleast one collection 
	 * @throws ParseException 
	 */
	public void verifyPartnerPlaceConfigPersonalizedCollectionsParam(String webid) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object id = object.get("Id");
		Assert.assertEquals(id,webid,"Obtained web id "+id+" and supplied web id "+webid+" don't match");
		System.out.println("Obtained web id "+id+" and supplied web id "+webid+" are a match");
		
		JSONArray personalizedCollections = (JSONArray) object.get("PersonalizedCollections");
		Assert.assertTrue(personalizedCollections.size()>0,"PersonalizedCollections key is empty");
		System.out.println("PersonalizedCollections key has "+personalizedCollections.size()+" collections");
		
		Iterator<JSONObject> i = personalizedCollections.iterator();
		System.out.println("-------------");
		while(i.hasNext()){
			JSONObject personalizedCollectionsObj = i.next();
			Object title = personalizedCollectionsObj.get("Title");
			System.out.println(title);
		}
		System.out.println("-------------");				
	}
	
	
	/**
	 * 1. Check Webid
	 * 2. Check TopTips key has atleast one tip 
	 * @throws ParseException 
	 */
	public void verifyPartnerPlaceConfigTopTipsParam(String webid) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object id = object.get("Id");
		Assert.assertEquals(id,webid,"Obtained web id "+id+" and supplied web id "+webid+" don't match");
		System.out.println("Obtained web id "+id+" and supplied web id "+webid+" are a match");
		
		JSONArray topTips = (JSONArray) object.get("TopTips");
		Assert.assertTrue(topTips.size()>0,"TopTips key is empty");
		System.out.println("TopTips key has "+topTips.size()+" tips");
		
		Iterator<JSONObject> i = topTips.iterator();
		System.out.println("-------------");
		while(i.hasNext()){
			JSONObject topTipsObj = i.next();
			Object title = topTipsObj.get("Title");
			System.out.println(title);
		}
		System.out.println("-------------");				
	}
	
	
	/**
	 * 1. Check atleast ten shelf types are available under shelves key
	 * 2. Check each shelf has atleast 4 products
	 * 3. Check Webid
	 * 4. Check Hero image is linked
	 * 5. Check Title says Boston
	 * 6. Check Location is 'Boston, MA'
	 * 7. Check ActivitiesCount > 0
	 * 8. Check PersonalizedCollections key has atleast one collection
	 * 9. Check TopTips key has atleast one tip
	 * 10.Hit the endpoint in 'link' key and check it does not take user to 'Not your moment' page
	 * 11.Check atleast 2 shelftypes are StubHub
	 * 12.Check each StubHub shelf has atleast 1 product
	 * 13.Verify 'PLACEPASSS' text is present in affiliate id (gcid)
	 * @throws ParseException 
	 */
	public List<String> verifyPartnerPlaceConfigWebIdGuideCountParam(String webid, String title, String location, String carLocationCode) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object id = object.get("Id");
		Assert.assertEquals(id,webid,"Obtained web id "+id+" and supplied web id "+webid+" don't match");
		System.out.println("Obtained web id "+id+" and supplied web id "+webid+" are a match");
		
		JSONObject extendedAttributes = (JSONObject) object.get("extendedAttributes");
		JSONArray shelves = (JSONArray) extendedAttributes.get("shelves");
		
		Assert.assertTrue(shelves.size()>10,"Shelves key holds less than 10 shelves");
		System.out.println("Shelves key holds "+shelves.size()+" shelves");
				
		Iterator<JSONObject> i = shelves.iterator();
		int stubhubs = 0;
		List<String> links = new ArrayList<String>();
		while(i.hasNext()){
			JSONObject shelfObj = i.next();
			JSONArray productIdArray = (JSONArray) shelfObj.get("productIds");
			Object[] productIds = new Object[0];
			if(productIdArray!=null){
				productIds = (Object[]) productIdArray.toArray();
			}
			Object shelfType = shelfObj.get("shelfType");
			Object shelfTitle = shelfObj.get("title");
			Object active = shelfObj.get("active");
			if((boolean) active){
				if(shelfType.toString().toLowerCase().startsWith("car")){
					Object obtainedCarLocationCode = shelfObj.get("carLocationCode");
					Assert.assertEquals(obtainedCarLocationCode,carLocationCode,"Obtained CarLocationCode "+obtainedCarLocationCode+" in shelf '"+shelfTitle+"' of type '"+shelfType+"' doesn't match expected CarLocationCode "+carLocationCode);
					System.out.println("Obtained CarLocationCode "+obtainedCarLocationCode+" in shelf '"+shelfTitle+"' of type '"+shelfType+"' is as expected");
				}
				else{
					Assert.assertTrue(productIds.length>0,"ProductIds key in shelf '"+shelfTitle+"' of type '"+shelfType+"' holds less than 4 products");
					System.out.println("ProductIds key in shelf '"+shelfTitle+"' of type '"+shelfType+"' holds "+productIds.length+" products");
				}	
			}
			
			if(shelfType.toString().toLowerCase().contains("stubhub")){
				stubhubs++;
				Object link = shelfObj.get("link");
				Assert.assertTrue(link.toString().toLowerCase().contains("placepass"),shelfType+" shelf link key doesn't contain placepass affiliation");
				System.out.println(shelfType+" shelf link key contains placepass affiliation");
			}
			Object lnk = shelfObj.get("link");
			links.add(lnk.toString());
		}
		Assert.assertTrue(stubhubs>1,"2 StubHub shelves are NOT displayed");
		System.out.println(stubhubs+" shelves are of type StubHub");
		
		JSONObject hero = (JSONObject) object.get("Hero");
		Object image = hero.get("Image");
		Assert.assertTrue(image.toString().toLowerCase().contains("marriott"),"Instead of default hero, "+image.toString()+" is linked");
		System.out.println("Default hero image "+image+" is linked");
		
		Object obtainedTitle = hero.get("Title");
		Assert.assertTrue(obtainedTitle.toString().contains(title),"Instead of Title: "+title+", "+obtainedTitle+" has loaded");
		System.out.println("Default title "+title+" has loaded");
		
		JSONObject locationObj = (JSONObject) object.get("Location");
		
		Object locationObtained = locationObj.get("Location");
		Assert.assertEquals(locationObtained.toString(),location,"Expected location '"+location+"' but got '"+locationObtained+"'");
		System.out.println("Location '"+location+"' is as expected");
		
		Object acitivitiesCount = locationObj.get("ActivitiesCount");
		Assert.assertTrue(Integer.valueOf(acitivitiesCount.toString())>0,"ActivitiesCount key holds zero");
		System.out.println("Got "+acitivitiesCount+" activities in "+location);	
				
		JSONArray personalizedCollections = (JSONArray) object.get("PersonalizedCollections");
		Assert.assertTrue(personalizedCollections.size()>0,"PersonalizedCollections key is empty");
		System.out.println("PersonalizedCollections key has "+personalizedCollections.size()+" collections");
		
		Iterator<JSONObject> j = personalizedCollections.iterator();
		System.out.println("-------------");
		while(j.hasNext()){
			JSONObject personalizedCollectionsObj = j.next();
			Object collection = personalizedCollectionsObj.get("Title");
			System.out.println(collection);
		}
		System.out.println("-------------");
		
		JSONArray topTips = (JSONArray) object.get("TopTips");
		Assert.assertTrue(topTips.size()>0,"TopTips key is empty");
		System.out.println("TopTips key has "+topTips.size()+" tips");
		
		Iterator<JSONObject> k = topTips.iterator();
		System.out.println("-------------");
		while(k.hasNext()){
			JSONObject topTipsObj = k.next();
			Object tip = topTipsObj.get("Title");
			System.out.println(tip);
		}
		System.out.println("-------------");
		
		return links;
	}
	
	
	public HashMap<String, String> requestShelflink(String endPoint){
		
		String requestURL = null;
		String env = BaseSetup.environment;
		List<String> headers = new ArrayList<String>();
		HashMap<String, String> responseMap;
				
		if(env.equals("stage1")){
			requestURL = "https://pp-marriott:dinosaur-wheat-car@marriott-stage.placepass.com";			
		}
		else if(env.equals("stage2")){
			requestURL = "https://pp-marriott:dinosaur-wheat-car@marriott-beta.placepass.com";
		}
		else{
			requestURL = "https://moments.marriott.com";
		}
		
		requestURL = requestURL + endPoint;
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			headers.add("Content-Type:text/html");
			headers.add("Accept:text/html");
		}	
			
	//	execute API request
		responseMap = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, "", false, "");
	
		System.out.println("In GET("+endPoint+") API response, Status code is -- " + responseMap.get("status code"));
				
		return responseMap;
	}
	
	
	/**
	 * 1. Check atleast ten shelf types are available under shelves key
	 * 2. Check each shelf has atleast 1 product
	 * 3. Check Location>ObjectId is ChIJGzE9DS1l44kRoOhiASS_fHg
	 * 4. Check Hero image is linked
	 * 5. Check Title says Boston
	 * 6. Check Location is 'Boston, MA'
	 * 7. Check ActivitiesCount > 0
	 * 8. Check PersonalizedCollections key has atleast one collection
	 * 9. Check TopTips key has atleast one tip
	 * 10.Check atleast 2 shelftypes are StubHub
	 * 11.Check each StubHub shelf has atleast 1 product
	 * 12.Verify 'PLACEPASSS' text is present in affiliate id (gcid)
	 * @throws ParseException 
	 */
	public void verifyPartnerPlaceConfigIdGuideCountParam(String id, String title, String location, String carLocationCode) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		JSONObject extendedAttributes = (JSONObject) object.get("extendedAttributes");
		JSONArray shelves = (JSONArray) extendedAttributes.get("shelves");
		
		Assert.assertTrue(shelves.size()>10,"Shelves key holds less than 10 shelves");
		System.out.println("Shelves key holds "+shelves.size()+" shelves");
				
		Iterator<JSONObject> i = shelves.iterator();
		int stubhubs = 0;
		while(i.hasNext()){
			JSONObject shelfObj = i.next();
			JSONArray productIdArray = (JSONArray) shelfObj.get("productIds");
			Object[] productIds = new Object[0];
			if(productIdArray!=null){
				productIds = (Object[]) productIdArray.toArray();
			}
			Object shelfType = shelfObj.get("shelfType");
			Object shelfTitle = shelfObj.get("title");
			Object active = shelfObj.get("active");
			if((boolean) active){
				if(shelfType.toString().toLowerCase().startsWith("car")){
					Object obtainedCarLocationCode = shelfObj.get("carLocationCode");
					Assert.assertEquals(obtainedCarLocationCode,carLocationCode,"Obtained CarLocationCode "+obtainedCarLocationCode+" in shelf '"+shelfTitle+"' of type '"+shelfType+"' doesn't match expected CarLocationCode "+carLocationCode);
					System.out.println("Obtained CarLocationCode "+obtainedCarLocationCode+" in shelf '"+shelfTitle+"' of type '"+shelfType+"' is as expected");
				}
				else{
				Assert.assertTrue(productIds.length>0,"ProductIds key in shelf '"+shelfTitle+"' of type '"+shelfType+"' holds less than 4 products");
				System.out.println("ProductIds key in shelf '"+shelfTitle+"' of type '"+shelfType+"' holds "+productIds.length+" products");
				}	
			}	
			
			if(shelfType.toString().toLowerCase().contains("stubhub")){
				stubhubs++;
				Object link = shelfObj.get("link");
				Assert.assertTrue(link.toString().toLowerCase().contains("placepass"),shelfType+" shelf link key doesn't contain placepass affiliation");
				System.out.println(shelfType+" shelf link key contains placepass affiliation");
			}			
		}
		Assert.assertTrue(stubhubs>1,"2 StubHub shelves are NOT displayed");
		System.out.println(stubhubs+" shelves are of type StubHub");
		
		JSONObject hero = (JSONObject) object.get("Hero");
		Object image = hero.get("Image");
		Assert.assertTrue(image.toString().toLowerCase().contains("marriott"),"Instead of default hero, "+image.toString()+" is linked");
		System.out.println("Default hero image "+image+" is linked");
		
		Object obtainedTitle = hero.get("Title");
		Assert.assertTrue(obtainedTitle.toString().contains(title),"Instead of Title: "+title+", "+obtainedTitle+" has loaded");
		System.out.println("Default title "+title+" has loaded");
		
		JSONObject locationObj = (JSONObject) object.get("Location");
		
		Object objectIdObtained = locationObj.get("objectID");
		Assert.assertEquals(objectIdObtained.toString(),id,"Obtained location id "+objectIdObtained+" and supplied location id "+id+" don't match");
		System.out.println("Obtained location id "+objectIdObtained+" and supplied location id "+id+" are a match");
		
		Object locationObtained = locationObj.get("Location");
		Assert.assertEquals(locationObtained.toString(),location,"Expected location '"+location+"' but got '"+locationObtained+"'");
		System.out.println("Location '"+location+"' is as expected");
		
		Object acitivitiesCount = locationObj.get("ActivitiesCount");
		Assert.assertTrue(Integer.valueOf(acitivitiesCount.toString())>0,"ActivitiesCount key holds zero");
		System.out.println("Got "+acitivitiesCount+" activities in "+location);	
				
		JSONArray personalizedCollections = (JSONArray) object.get("PersonalizedCollections");
		Assert.assertTrue(personalizedCollections.size()>0,"PersonalizedCollections key is empty");
		System.out.println("PersonalizedCollections key has "+personalizedCollections.size()+" collections");
		
		Iterator<JSONObject> j = personalizedCollections.iterator();
		System.out.println("-------------");
		while(j.hasNext()){
			JSONObject personalizedCollectionsObj = j.next();
			Object collection = personalizedCollectionsObj.get("Title");
			System.out.println(collection);
		}
		System.out.println("-------------");
		
		JSONArray topTips = (JSONArray) object.get("TopTips");
		Assert.assertTrue(topTips.size()>0,"TopTips key is empty");
		System.out.println("TopTips key has "+topTips.size()+" tips");
		
		Iterator<JSONObject> k = topTips.iterator();
		System.out.println("-------------");
		while(k.hasNext()){
			JSONObject topTipsObj = k.next();
			Object tip = topTipsObj.get("Title");
			System.out.println(tip);
		}
		System.out.println("-------------");		
	}
	
	/**
	 * 1. Check pp-code = ONLY_ID_OR_WEBID_REQUIRED
	 * 2. Check pp-message = Either id or webid can be presented at a time 
	 */
	public void verifyPartnerPlaceConfigIdWebidGuideCountParam(String code, String message){
		
		String ppcode = response.get("pp-code");		
		Assert.assertEquals(code, ppcode,"Expected code "+code+" and obtained code "+ppcode+" doesn't match");
		System.out.println("Obtained code "+code+" is as expected");
		
		String ppmessage = response.get("pp-message");
		Assert.assertEquals(message, ppmessage,"Expected message "+message+" and obtained message "+ppmessage+" doesn't match");
		System.out.println("Obtained message "+message+" is as expected");
	}
	
	
	public void verifyPartnerPlaceConfigCityGuide(String webid) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object id = object.get("Id");
		Assert.assertEquals(id,webid,"Obtained web id "+id+" and supplied web id "+webid+" don't match");
		System.out.println("Obtained web id "+id+" and supplied web id "+webid+" are a match");
		
		JSONArray CityGuideLocations = (JSONArray) object.get("cityGuideLocations");
		
		Assert.assertTrue(CityGuideLocations.size()>0,"CityGuideLocations key doesn't hold any CityGuideLocations");
		System.out.println("CityGuideLocations key holds "+CityGuideLocations.size()+" locations");
		
		Object extendedAttributes = object.get("extendedAttributes");
		Assert.assertTrue(extendedAttributes==null,"extendedAttributes key doesn't hold null as expected");
		System.out.println("extendedAttributes key holds null as expected");
		
		Object hero = object.get("Hero");
		Assert.assertTrue(hero==null,"Hero key doesn't hold null as expected");
		System.out.println("Hero key holds null as expected");
		
		Object location = object.get("Location");
		Assert.assertTrue(location==null,"Location key doesn't hold null as expected");
		System.out.println("Location key holds null as expected");
		
		JSONArray personalizedCollections = (JSONArray) object.get("PersonalizedCollections");
		Assert.assertTrue(personalizedCollections.size()==0,"PersonalizedCollections key is NOT empty");
		System.out.println("PersonalizedCollections key is empty");
		
		JSONArray CityGuideLocation = (JSONArray) object.get("cityGuideLocations");
		
		
		Iterator<JSONObject> j = CityGuideLocation.iterator();
		System.out.println("-------------");
		while (j.hasNext()){			
			JSONObject Guide = j.next();		    	
		    JSONArray TopTips = (JSONArray) Guide.get("TopTips");
		    
		    Object locationObj = Guide.get("Location");
		    
		    Assert.assertTrue(TopTips.size()>0,"TopTips key doesn't hold any tips for "+locationObj+" location");
		    System.out.println("TopTips key holds "+TopTips.size()+" tips for "+locationObj+" location");   
		}				
	}
	
	
	public void verifyPartnerPlaceConfigPersonalizedCollectionsPar_PP(String webid) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object id = object.get("Id");
		Assert.assertEquals(id,webid,"Obtained web id "+id+" and supplied web id "+webid+" don't match");
		System.out.println("Obtained web id "+id+" and supplied web id "+webid+" are a match");
		
		JSONArray personalizedCollections = (JSONArray) object.get("PersonalizedCollections");
		Assert.assertTrue(personalizedCollections.size()==0,"PersonalizedCollections key is not empty");
		System.out.println("PersonalizedCollections key is Empty");
	}
	
	
	/**
	 * 1. Check pp-code = ONLY_ID_OR_WEBID_REQUIRED
	 * 2. Check pp-message = Either id or webid can be presented at a time 
	 */
	public void verifyPartnerPlaceConfigNoParam(String code, String message){
		
		String ppcode = response.get("pp-code");		
		Assert.assertEquals(code, ppcode,"Expected code "+code+" and obtained code "+ppcode+" doesn't match");
		System.out.println("Obtained code "+code+" is as expected");
		
		String ppmessage = response.get("pp-message");
		Assert.assertEquals(message, ppmessage,"Expected message "+message+" and obtained message "+ppmessage+" doesn't match");
		System.out.println("Obtained message "+message+" is as expected");
	}
	
	
	public HashMap<String, String> requestLookupPartnerPlaces(String current_TestName, String endPoint, String partnerId, String count, String query){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
		String parameters = null;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		if(count!=null && query!=null){
			parameters = "?count="+ count + "&q=" + query;				
		}
		else if(query!=null){
			parameters = "?q="+ query;
		}
		
		if(parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(LookupPartnerPlaces) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(LookupPartnerPlaces) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	
	/**
	 * 1. Check returned collection of locations contains the string passed as query
	 * 2. Check returned collection is in descending order of ActivitiesCount
	 * 3. Check returned locations are less than or equal to passed count
	 * 4. Check ActivitiesCount > 0  
	 * @throws ParseException 
	 */
	public void verifyPartnerPlacesCountQueryParam(String count, String query) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONArray locationsArray = (JSONArray) parser.parse(responseString);
		
		Iterator<JSONObject> i = locationsArray.iterator();
		List<Integer> activities = new ArrayList<Integer>();
		
		System.out.println("--------------");
		while (i.hasNext())
		{
			JSONObject locationObject = i.next();
			Object webid = locationObject.get("WebId");
			
			Object obtainedLocation = locationObject.get("Location");
			Assert.assertTrue(obtainedLocation.toString().toLowerCase().contains(query),"Location '"+obtainedLocation+"' ["+webid+"] doesn't contain passed query '"+query+"'");
			System.out.println("Location '"+obtainedLocation+"' ["+webid+"] contains passed query '"+query+"'");
			
			Object activitiesCount = locationObject.get("ActivitiesCount");
			Assert.assertTrue(Integer.valueOf(activitiesCount.toString())>0,"Location '"+obtainedLocation+"' ["+webid+"] doesn't have atleast one activity");
			activities.add(Integer.valueOf(activitiesCount.toString()));
			System.out.println("Location '"+obtainedLocation+"' ["+webid+"] has "+activitiesCount+" activities");
			System.out.println("--------------");
		}
		
		if(count!=null){
			Assert.assertTrue(locationsArray.size()<=Integer.valueOf(count),"Number of returned locations ["+locationsArray.size()+"] is greater than passed count "+count);
			System.out.println("Number of returned locations ["+count+"] is less or same as passed count "+count);
		}else if(locationsArray.size()==0){
			System.out.println("No locations returned since there is no location matching query "+query);
		}
		else{
			System.out.println("Default number of locations returned ["+locationsArray.size()+"] since no limit has been set (count=null)");
		}
		
		List temp = new ArrayList(activities);
		Collections.sort(temp);
		Collections.reverse(temp);
		
		try{
			Assert.assertTrue(temp.equals(activities),"");   
		}	
		catch(Throwable t){
			Assert.fail("Locations are NOT returned in descending order of activity count");
		}
		System.out.println("Locations are returned in descending order of activity count");
	}
	
	
	public HashMap<String, String> requestLookupPartnerPlaceWithProperties(String current_TestName, String endPoint, String partnerId, String count, String query){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
		String parameters = null;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		if(count!=null && query!=null){
			parameters = "?count="+ count + "&q=" + query;				
		}
		else if(query!=null){
			parameters = "?q="+ query;
		}
		
		if(parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(LookupPartnerPlaceWithProperties) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(LookupPartnerPlaceWithProperties) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	

	/**
	 * 1. Verify propertyName key contains query
	 * 2. Verify Address (if available) contains stateProvince holding corresponding name of State (relevant to query)
	 */
	public void verifyPartnerPlacePropertiesCountQueryRegion(String count, String query,String region) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
	
		JSONObject Object=(JSONObject) parser.parse(responseString);
		
		JSONArray properties = (JSONArray) Object.get("properties");
		
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> j = properties.iterator();
		System.out.println("-------------");
		Assert.assertTrue(properties.size()<=Integer.valueOf(count),"Number of properties returned ["+properties.size()+"] exceeded specified limit of "+count);
		System.out.println("Number of properties returned ["+properties.size()+"] are within specified limit of "+count);
		
		JSONArray places = (JSONArray) Object.get("places");
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> i = places.iterator();
		System.out.println("-------------");
	
		if(region!=null)
		{
			
			while (i.hasNext())
			{			
				
				JSONObject place = i.next();
				Object location = place.get("Location");
				Assert.assertTrue(location.toString().toLowerCase().contains(query.replace("%20"," ")), "Location "+location+" doesn't contain query "+query);
				System.out.println("Location "+location+" contains query "+query+" as expected");
				
				Object regions =  place.get("Region");	        
				Assert.assertEquals(regions,region,"Expected "+regions+" but got "+ region);
			    System.out.println("Got "+regions+" as expected");
			    
			    while (j.hasNext())
			    {			
				
			    JSONObject property = j.next();
				
			    	Object propertyName = property.get("PropertyName");
			    	Assert.assertTrue(propertyName.toString().toLowerCase().contains(query.replace("%20"," ")), "PropertyName "+propertyName+" doesn't contain query "+query);
			    	System.out.println("PropertyName "+propertyName+" contains query "+query+" as expected");
				
			    	JSONObject address = (JSONObject) property.get("Address");
		        
					if(address!=null)
					{
			       	 String state = (String)address.get("stateProvince");
			       	 Assert.assertEquals(state,region,"Expected "+region+" but got "+state);
			    	 System.out.println("Got "+region+" as expected");
					}
					
			    System.out.println("-------------");
			    }		
			}	
		}
		else
		{
			while (j.hasNext())
		    {	
				JSONObject hotel=j.next();
				Object propertyName=(String) hotel.get("PropertyName");
				Assert.assertTrue(propertyName.toString().toLowerCase().contains(query), "PropertyName "+propertyName+" doesnt contain query "+query);
				System.out.println("PropertyName "+propertyName+" contains query "+query+" as expected");
				
				JSONObject hotellocation = (JSONObject) hotel.get("NearestLocation");
				Object id=hotellocation.get("Id");
				Assert.assertNotNull(id, "The nearestlocation is null for PropertyName "+propertyName);
				System.out.println("PropertyName "+propertyName+" has nearest location with id "+id);
			 }
		}		    
	}		
	
		
	public String[] verifyPartnerPlacePropertiesCountQueryProperty(String query,String PropertyName,String city ,String region) throws ParseException{
		  
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
	 
		JSONObject Object=(JSONObject) parser.parse(responseString);
	  
		JSONArray properties = (JSONArray) Object.get("properties");
	  
		System.out.println("---------------------- MARSHA "+query+"-------------------------");
		
		String geoData[] = new String[3];  
		if(!(properties.size()>0)){
			System.out.println("Marsha code "+query+" doesnt resolve to any location");			
		}
		else{
		   JSONObject Hotel=(JSONObject) properties.get(0);
		   String obtainedpropertyName=(String) Hotel.get("PropertyName");
		   System.out.println("Marsha code "+query+" resolved to "+obtainedpropertyName);
		   Assert.assertTrue(obtainedpropertyName.contains(PropertyName.replace("™","")),"Obtained hotel "+obtainedpropertyName+" does not match with expected "+PropertyName+" for Marsha "+query);
		   System.out.println("Obtained hotel "+obtainedpropertyName+" is as expected for Marsha "+query);
		   
		   JSONObject nearestLocation = (JSONObject) Hotel.get("NearestLocation");
		   if(nearestLocation!=null)
		   {
		   
		   geoData[0] = nearestLocation.get("Id").toString();
		   
		   JSONObject geoLocation = (JSONObject) Hotel.get("GeoLocation");
		   geoData[1] = geoLocation.get("lat").toString();
		   geoData[2] = geoLocation.get("lon").toString(); 
		   
		   JSONObject address = (JSONObject) Hotel.get("Address");
			         
		   if(city!=null)
		   {
			   String obtainedCity= (String)address.get("city");
			   Assert.assertEquals(obtainedCity,city,"Obtained city "+obtainedCity+" doesn't match with expected "+city+" for Marsha "+query);
		       System.out.println("Obtained city "+obtainedCity+" is as expected for Marsha "+query);   
		   }
		   String obtainedCountry= (String)address.get("country");
		   Assert.assertEquals(obtainedCountry,region,"Obtained country "+obtainedCountry+" doesn't match with expected "+region+" for Marsha "+query);
		   System.out.println("Obtained country "+obtainedCountry+" is as expected for Marsha "+query);		   
		}
		 else	
		{
			System.out.println("The Nearest location key holds null");
		}
		
	 }
		return geoData;
	}	
	/**
	 * Calculate distance between two geo locations
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public double distance(double lat1, double lon1, double lat2, double lon2) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515 * 1.609344;		  
		  return (dist);
	}
	
	
	/**
	 * Converts decimal degrees to radians
	 * @param deg
	 * @return
	 */
	public double deg2rad(double deg) {
	  return (deg * Math.PI / 180.0);
	}
	
	
	/**
	 * Convert radians to decimal degrees
	 * @param rad
	 * @return
	 */
	public double rad2deg(double rad) {
	  return (rad * 180.0 / Math.PI);
	}
	
	
	/**
	 * 1. Check pp-code = REQUIRED_URL_PARAM_NOT_FOUND
	 * 2. Check pp-message = Required parameter q is not specified in the request. 
	 * 	  Please give any location name query as the q parameter value 
	 */
	public void verifyPartnerPlacesNoParam(String code, String message){
		
		String ppcode = response.get("pp-code");		
		Assert.assertEquals(code, ppcode,"Expected code "+code+" and obtained code "+ppcode+" doesn't match");
		System.out.println("Obtained code "+code+" is as expected");
		
		String ppmessage = response.get("pp-message");
		Assert.assertEquals(message, ppmessage,"Expected message "+message+" and obtained message "+ppmessage+" doesn't match");
		System.out.println("Obtained message "+message+" is as expected");
	}
	
	
	public HashMap<String, String> requestLookupPartnerProperty(String current_TestName, String endPoint, String partnerId, String propertyCode){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
		String parameters = null;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		if(propertyCode!=null)
			parameters = "/"+ propertyCode;				
				
		if(parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(LookupPartnerProperty) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(LookupPartnerProperty) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	
	
	/**
	 * 1. Check PropertyCode key holds property code passed in endpoint
	 * 2. Check NearestLocation key holds some data
	 * 3. Check ActivitiesCount > 0
	 * -------------------------------------
	 * If HasExtendedAttributes key holds true, verify below
	 * -------------------------------------
	 * 4. Atleast 1 FEATURED_ONSITE row type
	 * 5. Atleast 1 LARGE row type
	 * 6. Atleast 1 FILTER row type
	 * 7. Atleast 1 CAR_RENTAL row type
	 * 8. Atleast 2 STUBHUB row types
	 * 9. Verify 'PLACEPASSS' text is present in affiliate id (gcid)
	 * 10.Atleast 1 FEATURED_EXPERIENCE row type
	 * 11.If active is true for rows except CAR_RENTAL, FEATURED_ONSITE, FILTER productIds should hold atleast 4 products
	 * 12.If active is true for CAR_RENTAL row, carLocationCode should hold relevant code     
	 * @throws ParseException 
	 */
	public void verifyPartnerPropertyParam(String propertyCode, String carLocationCode) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		Object obtainedPropertyCode = object.get("PropertyCode");
		Assert.assertEquals(obtainedPropertyCode,propertyCode,"Obtained property code ["+obtainedPropertyCode+"] doesn't match with expected ["+propertyCode+"]");
		System.out.println("Obtained property code ["+obtainedPropertyCode+"] is as expected");
		
		Object nearestLocation = object.get("NearestLocation");
		Assert.assertNotNull(nearestLocation,"NearestLocation key holds null");
		System.out.println("NearestLocation key holds some data");
		
		Object activitiesCount = ((JSONObject)nearestLocation).get("ActivitiesCount");
		Assert.assertTrue(Integer.valueOf(activitiesCount.toString())>0,"There are no activities near to property "+propertyCode);
		System.out.println(activitiesCount+" activities available near to property "+propertyCode);
		
		Object hasExtendedAttributes = ((JSONObject)nearestLocation).get("HasExtendedAttributes");
		
		Object extendedAttributes = object.get("ExtendedAttributes");
		
		if(hasExtendedAttributes.toString().equals("true")){
			Assert.assertNotNull(extendedAttributes,"ExtendedAttributes holds null even though HasExtendedAttributes key is true");
			
			JSONObject rows = (JSONObject) ((JSONObject)extendedAttributes).get("rows");
			JSONArray genericRows = (JSONArray) rows.get("genericRows");
			
			Iterator<JSONObject> i = genericRows.iterator();
			
			int featuredOnsite = 0;	int large = 0; int filter = 0;
			int carRental = 0; int stubhub = 0; int featuredExperience = 0;
			List<String> links = new ArrayList<String>();
			
			while(i.hasNext()){
				
				JSONObject rowObject = i.next();
				Object rowType = rowObject.get("rowType");			
				Object active = rowObject.get("active");
				Object title = rowObject.get("title");
				
				JSONArray productIdArray = (JSONArray) rowObject.get("productIds");
				Object[] productIds = new Object[0];
				if(productIdArray!=null){
					productIds = (Object[]) productIdArray.toArray();
				}
				
				if(rowType.toString().contains("FEATURED_ONSITE")){
					featuredOnsite++;				
				}else if(rowType.toString().contains("LARGE")){
					if((boolean)active){
						Assert.assertTrue(productIds.length>=4,"ProductIds in row '"+title+"' of type '"+rowType+"' holds less than 4 products");
						System.out.println("ProductIds in row '"+title+"' of type '"+rowType+"' holds "+productIds.length+" products");
					}
					large++;
				}else if(rowType.toString().contains("FILTER")){
					filter++;
				}else if(rowType.toString().contains("CAR_RENTAL")){
					Object obtainedCarLocationCode = rowObject.get("carLocationCode");
					Assert.assertEquals(obtainedCarLocationCode,carLocationCode,"Obtained CarLocationCode "+obtainedCarLocationCode+" in row type '"+rowType+"' doesn't match expected CarLocationCode "+carLocationCode);
					System.out.println("Obtained CarLocationCode "+obtainedCarLocationCode+" in row type '"+rowType+"' is as expected");	
					carRental++;
				}else if(rowType.toString().contains("STUBHUB")){
					if((boolean)active){
						Assert.assertTrue(productIds.length>=4,"ProductIds in row '"+title+"' of type '"+rowType+"' holds less than 4 products");
						System.out.println("ProductIds key in row '"+title+"' of type '"+rowType+"' holds "+productIds.length+" products");
					}
					Object link = rowObject.get("link");
					Assert.assertTrue(link.toString().toLowerCase().contains("placepass"),title+" "+rowType+" row link "+link+" doesn't contain placepass affiliation");
					System.out.println(title+" "+rowType+" row link "+link+" contains placepass affiliation");
					stubhub++;				
				}else if(rowType.toString().contains("FEATURED_EXPERIENCE")){
					if((boolean)active){
						Assert.assertTrue(productIds.length>=4,"ProductIdsin row '"+title+"' of type '"+rowType+"' holds less than 4 products");
						System.out.println("ProductIds in row '"+title+"' of type '"+rowType+"' holds "+productIds.length+" products");
					}
					featuredExperience++;
				}		
			}
				
			Assert.assertTrue(featuredOnsite>0,"Not even single row of type FEATURED_ONSITE has populated");
			System.out.println(featuredOnsite+" row(s) of type FEATURED_ONSITE have populated");
			
			Assert.assertTrue(large>0,"Not even single row of type LARGE has populated");
			System.out.println(large+" row(s) of type LARGE have populated");
			
			Assert.assertTrue(filter>0,"Not even single row of type FILTER has populated");
			System.out.println(filter+" row(s) of type FILTER have populated");
			
			Assert.assertTrue(carRental>0,"Not even single row of type CAR_RENTAL has populated");
			System.out.println(carRental+" row(s) of type CAR_RENTAL have populated");
			
			Assert.assertTrue(stubhub>1,"Two rows of type STUBHUB have not populated");
			System.out.println(stubhub+" row(s) of type STUBHUB have populated");
			
			//Assert.assertTrue(featuredExperience>0,"Not even single row of type FEATURED_EXPERIENCE has populated");
			System.out.println(featuredExperience+" row(s) of type FEATURED_EXPERIENCE have populated");
			
		}
		else{
			Assert.assertEquals(extendedAttributes,null,"ExtendedAttributes doesn't hold null even though HasExtendedAttributes key is false");
			System.out.println("ExtendedAttributes holds null since HasExtendedAttributes key is false");
		}
	}
	
	
	/**
	 * 1. Check pp-code = PARTNER_PROPERTY_NOT_FOUND_BY_CODE
	 * 2. Check pp-message = Partner property not found for the given code 
	 */
	public void verifyPartnerPropertyNoParam(String code, String message){
		
		String ppcode = response.get("pp-code");		
		Assert.assertEquals(code, ppcode,"Expected code "+code+" and obtained code "+ppcode+" doesn't match");
		System.out.println("Obtained code "+code+" is as expected");
		
		String ppmessage = response.get("pp-message");
		Assert.assertEquals(message, ppmessage,"Expected message "+message+" and obtained message "+ppmessage+" doesn't match");
		System.out.println("Obtained message "+message+" is as expected");
	}
	
	
	public HashMap<String, String> requestPartnerPlaceSitemap(String current_TestName, String endPoint, String partnerId, String query, String topDestination, String count, String page){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = BaseSetup.environment;
		String parameters = null;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}
		
		if(query!=null && topDestination!=null && count!=null)
			parameters = "?q="+ query + "&topDestination=" + topDestination + "&count=" + count;
		else if(page!=null && count!=null){
			parameters = "?page="+ page + "&count=" + count;
		}
				
		if(parameters != null)
			requestURL = requestURL + endPoint + parameters;
		else
			requestURL = requestURL + endPoint;
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod"))){
				headers.add("env:"+env);
			}
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(PartnerPlaceSitemap) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(PartnerPlaceSitemap) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	
	
	/**
	 * 1. Check locations returned are <= count passed
	 * 2. Check all locations contain string in passed query
	 * 3. Check totalHits > 100 if query param is not passed
	 * 4. Check default page = 0 if page param is not passed
	 * @throws ParseException 
	 */
	public void verifyPartnerPlaceSitemapParam(String query, String count, String page) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		JSONArray locations = (JSONArray) object.get("locations");
		JSONObject pageObject = (JSONObject) object.get("page");
		Object pageNum = pageObject.get("page");
		Object hits = pageObject.get("totalHits");
		
		System.out.println("--------------------------");
		if(count!=null && query!=null && page==null){
			Assert.assertTrue(pageNum.toString().equals("0"),"Page param not passed but instead of default page [0], ["+pageNum+"] returned");
			System.out.println("Page param not passed so default page ["+pageNum+"] returned");
			
			Assert.assertTrue(locations.size()<=Integer.valueOf(count),"Locations returned on page ["+page+"] are greater than supplied count "+count);
			System.out.println(locations.size()+" locations returned for page ["+pageNum+"] are as expected");			
		}else if(query==null && page!=null && count!=null){
			Assert.assertTrue(Integer.valueOf(hits.toString())>100,"Total hits returned are less than 100");
			System.out.println("Total hits "+hits+" returned for page ["+page+"] are as expected");
			
			Assert.assertTrue(pageNum.toString().equals(page),"Page param passed but instead of expected page ["+page+"], ["+pageNum+"] returned");
			System.out.println("Page param passed and returned page ["+pageNum+"] is as expected");
			
			Assert.assertTrue(locations.size()<=Integer.valueOf(count),"Locations returned on page ["+page+"] are greater than supplied count "+count);
			System.out.println(locations.size()+" locations returned for page ["+page+"] are as expected");			
		}else if(count==null && query==null && page==null){
			Assert.assertTrue(pageNum.toString().equals("0"),"Page param not passed but instead of default page [0], ["+pageNum+"] returned");
			System.out.println("Page param not passed so default page ["+pageNum+"] returned");
			
			Assert.assertTrue(Integer.valueOf(hits.toString())>100,"Total hits returned are less than 100");
			System.out.println("Total hits "+hits+" returned for page ["+pageNum+"] are as expected");
			
			Assert.assertTrue(locations.size()==10,"Locations returned on page ["+pageNum+"] are ["+locations.size()+"] instead of expected default [10]");
			System.out.println(locations.size()+" locations returned for page ["+pageNum+"] are as expected");
		}
		
		Iterator<JSONObject> i = locations.iterator();
			
		while(i.hasNext()){
			JSONObject locationObject = i.next();
			
			Object location = locationObject.get("Location");
			Object id = locationObject.get("Id");
			
			if(query!=null){
				Assert.assertTrue(location.toString().toLowerCase().contains(query),"Returned location "+location+" with id ["+id+"] doesn't contain passed query "+query);
				System.out.println("Returned location "+location+" with id ["+id+"] contains passed query "+query);
			}
			else{
				System.out.println("Returned location "+location+" with id ["+id+"]");
			}
		}		
	}
	
	
	public Object[] getLatLong() throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		 
		JSONObject Object=(JSONObject) parser.parse(responseString);
	  
		JSONObject location = (JSONObject) Object.get("Location");
		JSONObject geoloc = (JSONObject) location.get("_geoloc");
		
		Object geoData[] = new Object[2];
		geoData[0] = geoloc.get("lat");
		geoData[1] = geoloc.get("lng");
		
		return geoData;
	}
	
	public void verifyNearness(Object[] geoLocation1, Object[] geoLocation2, String webId, String city, String nearbyWebId, String nearbyCity) throws ParseException{	
		
		Double distance = distance(Double.parseDouble(geoLocation1[0].toString()), Double.parseDouble(geoLocation1[1].toString()), Double.parseDouble(geoLocation2[0].toString()), Double.parseDouble(geoLocation2[1].toString()));
		
		Assert.assertTrue((distance * 0.621371) <= 30,"["+city+" - "+webId+"] and its nearby city ["+nearbyCity+" - "+nearbyWebId+"] are actually ["+(distance * 0.621371)+" miles] far away");
		System.out.println("["+city+" - "+webId+"] and its nearby city ["+nearbyCity+" - "+nearbyWebId+"] are only ["+(distance * 0.621371)+" miles] away");		
	}
	
	
	public void verifyPartnerPropertyCodes(String ExpectedPropertyName, String ExpectedCityName) throws ParseException
	{
		String responseString=response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object=(JSONObject) parser.parse(responseString);
		String MarshaCode =(String) object.get("PropertyCode");
		String PropertyName=(String) object.get("PropertyName");
		JSONObject address=(JSONObject) object.get("Address");
		String CityName =(String) address.get("city");
		Assert.assertEquals(ExpectedPropertyName, PropertyName.trim(),"The "+PropertyName+" doesn't match with expected "+ExpectedPropertyName);
		System.out.println("The "+PropertyName+" Matches with expected "+MarshaCode);
		Assert.assertEquals(ExpectedCityName, CityName.trim(),"The "+CityName+" doesn't match with expected "+ExpectedCityName);
		System.out.println("The "+CityName.trim()+" Matches with expected "+MarshaCode);

		
	}
	/**
	 * 1. Check pp-code
	 * 2. Check pp-message 
	 */
	public void verifyPPCodePPMessage(String code, String message){
	
		String ppcode = response.get("pp-code");		
		Assert.assertEquals(code, ppcode,"Expected code '"+code+"' and obtained code '"+ppcode+"' doesn't match");
		System.out.println("Obtained code '"+code+"' is as expected");
		
		String ppmessage = response.get("pp-message");
		Assert.assertEquals(message, ppmessage,"Expected message '"+message+"' and obtained message '"+ppmessage+"' doesn't match");
		System.out.println("Obtained message '"+message+"' is as expected");
	}
}
