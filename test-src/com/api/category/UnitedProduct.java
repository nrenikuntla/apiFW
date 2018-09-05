package com.api.category;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testng.Assert;
import com.utilities.APIHelpers;

public class UnitedProduct extends APIHelpers{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	public ConfigManager api = new ConfigManager("Api");
	JSONManager jsonObject = new JSONManager();
	List<String> headers = new ArrayList<String>();
	HashMap<String, String> response;
	
	
	public HashMap<String, String> requestGetAvailability(String current_TestName, String endPoint, String partnerId, String month, String year,String... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env=null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];
		String parameters = null;
		
		String requestURL; String subscriptionKey;
		
		if(month != null && year != null)
			parameters = "?month="+month+"&year="+year;
		else if(month != null && year == null)
			parameters = "?month="+month;
		else if(month == null && year != null)
			parameters = "?year="+year;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
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
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Availability) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Availability) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}

	public void verifyStatusCode(String statusCode){
		verifyStatusCode(response.get("status code"), statusCode);
	}
	
	public void verifyStatusMessage(String statusMessage){
		verifyStatusMessage(response.get("status message"), statusMessage);
	}
	
	public void verifyDateRange(String pNode, String key, String month, String year){
		
		// Using Calendar - calculating number of months between two dates  
        Calendar futureDay = new GregorianCalendar(Integer.valueOf(year), Integer.valueOf(month)-1, Calendar.DAY_OF_MONTH);
        Calendar today = new GregorianCalendar();
        today.setTime(new Date());
        
        int yearsInBetween = futureDay.get(Calendar.YEAR) - today.get(Calendar.YEAR);
        int monthsDiff = futureDay.get(Calendar.MONTH) - today.get(Calendar.MONTH);
        int totalDiffInMonths = yearsInBetween*12 + monthsDiff;
                
        if (totalDiffInMonths >= 0 && totalDiffInMonths <= 9){
        	
        //	Verify response code and message for valid month/year combination
    		verifyStatusCode("200");
    		verifyStatusMessage("OK");
    		
    		List<String> dates = jsonObject.getValueFromArrayJsonKey(response.get("response body"), pNode, key);
    		
    		System.out.println("*****************************************************");
        
           	Calendar cal = Calendar.getInstance();
	    	cal.set(Calendar.MONTH, Integer.valueOf(month)-1);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        cal.set(Calendar.YEAR, Integer.valueOf(year));
	        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
	        	
	     // Create list of valid dates for given month 
	        ArrayList<String> validDates = new ArrayList<String>(); 
	        for (int i = 0; i < maxDay; i++) 
	        {
	        	cal.set(Calendar.DAY_OF_MONTH, i+1);
	        	validDates.add(df.format(cal.getTime()));        	
	        }
	       
	        int count = 0;
	     // Verify displayed dates are within given date range for that month  
			for(int i=0; i < dates.size(); i++){
				if(validDates.contains(dates.get(i))){
					System.out.println("Displayed Availability Date No "+(i+1)+": "+dates.get(i)+" is within given date range "+month+"/"+year);
					count++;
				}
				Assert.assertTrue(validDates.contains(dates.get(i)),"Displayed Availability Date: "+dates.get(i)+" is outside range");
			}
			Assert.assertEquals(dates.size(),count, "Out of "+dates.size()+" date(s), "+(dates.size() - count)+" date(s) are outside given date range");
		}
        else{
        	System.out.println("*****************************************************");
        //	Verify response code and message for invalid month/year combination
    		System.out.println("Given combination "+month+"/"+year+" is outside acceptable range for API");
        	verifyStatusCode("400");
    		verifyStatusMessage("Bad Request");
        }
	}


		
	public HashMap<String, String> requestGetProductDetails(String current_TestName, String endPoint, String partnerId, String language, String... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String parameters = null;
		String env = null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];
		
		if(language != null)
			parameters = "?language="+language;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL");
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL");
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
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
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Product Details) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Product Details) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}
	

	public void verifyProductDetails(String productIdKey, String retailPrice, String finalPrice, String webLocation, String language, String productid){
		
		System.out.println("**************************************************************");
		
	//  Get product Id
		//List<String> productids = jsonObject.getValueFromArrayJsonKeyWithoutParentNode(response.get("response body"), productIdKey);
		List<String> productids = jsonObject.getValueFromSimpleJsonKey(response.get("response body"), productIdKey);
		Assert.assertEquals(productid,productids.get(0),"Product Id: "+productids.get(0)+" doesn't match given Product Id: "+productid);
		System.out.println("Product Id: "+productids.get(0)+" matched given Product Id: "+productid);
		
	//  Get retail price 	
		List<Double> retailprices = jsonObject.getValueFromArrayJsonKey3(response.get("response body"), "prices", retailPrice);
		Assert.assertNotEquals(retailprices.get(0),"null","Retail price is null");
		Assert.assertTrue(retailprices.get(0) > 0,"Product Id: "+productids.get(0)+" retail price is "+retailprices.get(0)+" NOT greater than zero");
		System.out.println("Product Id: "+productids.get(0)+" retail price is "+retailprices.get(0)+" (greater than zero)");

	//  Get final price	
		List<Double> finalprices = jsonObject.getValueFromArrayJsonKey3(response.get("response body"), "prices", finalPrice);
		Assert.assertNotEquals(finalprices.get(0),"null","Final price is null");
		Assert.assertTrue(finalprices.get(0) > 0,"Product Id: "+productids.get(0)+" final price is "+finalprices.get(0)+" NOT greater than zero");
		System.out.println("Product Id: "+productids.get(0)+" final price is "+finalprices.get(0)+" (greater than zero)");
				
	//  Get webLocation
		List<String> weblocationContent = jsonObject.getValueFromSimpleJsonKey(response.get("response body"), webLocation);
		Assert.assertNotEquals(weblocationContent.get(0),"null","webLocation content is null");
		List<String> placeId = null;
		try{
			placeId = jsonObject.getValueFromSimpleJsonKey(weblocationContent.get(0), "placeId");			
		}
		catch (Exception e){
			Assert.fail("Could not find 'placeId' key inside webLocation key for productId "+productid);
		}
		System.out.println("Able to find 'placeId':"+placeId.get(0)+" inside webLocation key for productId "+productid);
				
	//  Get languageCode
		if (language != null){
			List<String> langCode = jsonObject.getValueFromSimpleJsonKey(response.get("response body"), "languageCode");
			Assert.assertEquals(language,langCode.get(0),"Language: "+langCode.get(0)+" doesn't match given language: "+language);
			System.out.println("Language: "+langCode.get(0)+" matched given language: "+language);
		}
	}
	
	
	public HashMap<String, String> requestGetProductOptions(String current_TestName, String endPoint, String partnerId, String date,String ... environment){
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String parameters = "?date="+date;
		String env=null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];
		
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint + parameters;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint + parameters;
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
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Product Options) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Product Options) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}

	public List<String> verifyProductOptions(String date, String productOptionListKey, String retailPrice, String finalPrice, String productId) throws java.text.ParseException{
		
		System.out.println("**************************************************************");
		
	//  Get productOptionList content
		List<String> productOptionList = jsonObject.getValueFromSimpleJsonKey(response.get("response body"), productOptionListKey);
		List<String> productOptionIds = jsonObject.getValueFromArrayJsonKeyWithoutParentNode(productOptionList.get(0), "productOptionId");
		String s="";
		@SuppressWarnings("unchecked")
		List<String> emptyString=new ArrayList(Arrays.asList(s));
		if(productId!=null&&productOptionIds!=emptyString)
		{
	//	If date is in past, verify there are no product options	
		
		if (new SimpleDateFormat("yyyy-MM-dd").parse(date).before(new Date()))
			{
				Assert.assertEquals(0,productOptionIds.size(),"Product - ["+productId+"] has ["+productOptionIds+"] options available for past date "+date);
				System.out.println("Product - ["+productId+"] has no options available for past date "+date);
				System.out.println("**************************************************************");
			}
		else
			{
				Assert.assertTrue(productOptionIds.size()>=0,"Product - ["+productId+"] has no options available for future date "+date);
				System.out.println("Product - ["+productId+"] has ["+productOptionIds.size()+"] options available for future date "+date);
				for(int i=0;i<productOptionIds.size();i++){
					System.out.println("Product Option Ids: "+(i+1)+" - "+productOptionIds.get(i));
				}
				System.out.println("**************************************************************");
				
			//  Get prices JSON array 	
				HashMap<String,String> pricesjson = jsonObject.getValuesFromInnerJsonKey(response.get("response body"), productOptionListKey, "prices","retailPrice","finalPrice");
				if (pricesjson.size()==0){
					System.out.println("Product - ["+productId+"] has no pricing data available for future date "+date);
					System.out.println("**************************************************************");
				}
				
			//	Validate retail and final prices are not null
				for(int j = 0; j < pricesjson.size()/2; j++){
					Assert.assertNotNull(pricesjson.get("retailPrice_"+(j+1)),"Retail price is null");
					Assert.assertNotNull(pricesjson.get("finalPrice_"+(j+1)),"Final price is null");
					System.out.println("Retail price is $"+pricesjson.get("retailPrice_"+(j+1)));
					System.out.println("Final Price is $"+pricesjson.get("finalPrice_"+(j+1)));
					System.out.println("***************************************");	
			}
		
			}
		}
		else
		{
			System.out.println("The "+productId+" Doesn't have productOptions");
		}
		
		return productOptionIds;
				
	}

	public HashMap<String, String> requestGetProductReviews(String current_TestName, String endPoint, String partnerId, String hitsPerPage, String pageNumber, String language){
	
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String parameters = "?hitsperpage="+hitsPerPage+"&pagenumber="+pageNumber;
		String env = BaseSetup.environment;
		
		if(language != null)
			parameters = parameters+"&language="+language;
				
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint + parameters;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint + parameters;
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
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
	
			
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Product Reviews) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Product Reviews) API response, Status message is -- " + response.get("status message"));
		
		return response;
		}


	@SuppressWarnings("unchecked")
	public void verifyProductReviews(String reviewsKey, String hitsPerPage, String pageNumber, String language, String productId){
	
		System.out.println("**************************************************************");
		List<Long> reviewIds;
		
		JSONParser parser = new JSONParser();
		JSONObject object = null;
		try{
			object = (JSONObject) parser.parse(response.get("response body"));
		}catch(ParseException e){
			Assert.fail("Encountered Parse Exception!");
		}		
		JSONArray reviews = (JSONArray)object.get("reviews");
		
		if(reviews==null){
			reviewIds = new ArrayList<Long>();
		}	
		else{
			reviewIds = (List<Long>)(List<?>)jsonObject.getValueAsObjectFromArrayJsonKey(response.get("response body"), reviewsKey, "reviewId");
		}		
		Assert.assertTrue(reviewIds.size()<=Integer.valueOf(hitsPerPage),"Product ["+productId+"] has ["+reviewIds.size()+"] reviews in page ["+Integer.valueOf(pageNumber)+1+"] which is more than the number requested per page "+hitsPerPage);
		System.out.println("Product ["+productId+"] has ["+reviewIds.size()+"] reviews in page ["+Integer.valueOf(pageNumber)+1+"] which is less than or equal to the number requested per page "+hitsPerPage);
		System.out.println("**********************");
		System.out.println("Review IDs in page "+Integer.valueOf(pageNumber)+1);
		System.out.println("**********************");
		
		for(int i=0; i<reviewIds.size(); i++){
			System.out.println(reviewIds.get(i));
		}		
	}


	public HashMap<String, String> requestGetHotelDetails(String current_TestName, String endPoint, String partnerId, String... environment){
	
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String env = null;
		if(environment.length==0)
			env = BaseSetup.environment;
		else
			env = environment[0];
		
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
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}
	
			
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Hotel Details) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Hotel Details) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}

	@SuppressWarnings("unchecked")
	public void verifyHotelDetails(String hotelDetailsKey, String productId){
		System.out.println("**************************************************************");
		
		if(!response.get("status code").equals("200")){
			
	//		Verify response code and message
			verifyStatusCode("404");
			verifyStatusMessage("Not Found");
			System.out.println("Hotel pickup unavailable for given product "+productId);
		}
		else{
			
	//		Verify response code and message
			verifyStatusCode("200");
			verifyStatusMessage("OK");
			System.out.println("**************************************************************");
			
			List<String> hotelIds = (List<String>)(List<?>)jsonObject.getValueAsObjectFromArrayJsonKey(response.get("response body"), hotelDetailsKey, "id");
			List<String> destinationIds = (List<String>)(List<?>)jsonObject.getValueAsObjectFromArrayJsonKey(response.get("response body"), hotelDetailsKey, "destinationId");
			
			System.out.println("Product ["+productId+"] has hotel pickup at ["+hotelIds.size()+"] hotels");	
			for(int i=0,j=0; i<hotelIds.size() && j<destinationIds.size();i++,j++){
				Assert.assertNotNull(hotelIds.get(i),"Product ["+productId+"] has null Hotel ID(s)");
				System.out.println("Hotel ID "+hotelIds.get(i)+": "+destinationIds.get(i));
				Assert.assertEquals(destinationIds.get(i), destinationIds.get(0), "Destination Id "+destinationIds.get(i)+" is NOT consistent across various Hotel IDs");			
			}
			System.out.println("Destination ID "+destinationIds.get(0)+" is consistent across "+hotelIds.size()+" Hotel IDs");
		}
		
		System.out.println("**********************************************************");
	
	}

	public HashMap<String, String> requestGetPriceMatch(String current_TestName, String endPoint, String partnerId, String requestJSON){
	
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
		
		requestJSON = System.getProperty("user.dir") + requestJSON;
		System.out.println(requestJSON);
			
		if(headers != null){
			headers.clear();
			if(partnerId!=null)
				headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);			
		}			
					
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, false, "", requestJSON);
	
		System.out.println("In POST(Get Price Match) API response, Status code is -- " + response.get("status code"));
		System.out.println("In POST(Get Price Match) API response, Status message is -- " + response.get("status message"));
		
		return response;
	}


	@SuppressWarnings("unused")
	public void verifyPriceMatch(String adultQty, String adultPrice, String childQty, String childPrice, String infantQty, String infantPrice) throws ParseException{
		
		Double adultRetailTotal = null;
		Double childRetailTotal = null;
		Double infantRetailTotal = null;
		
		if ((adultPrice!=null) && (adultQty != null)){ 
			adultRetailTotal = Double.parseDouble(adultPrice) * Double.parseDouble(adultQty);  
			System.out.println("Adult retail total "+adultRetailTotal);
		}
		if ((childPrice!=null) && (childQty != null)){ 
			childRetailTotal = Double.parseDouble(childPrice) * Double.parseDouble(childQty);
			System.out.println("Child retail total "+childRetailTotal);
		}
		if ((infantPrice!=null) && (infantQty != null)){ 
			infantRetailTotal = Double.parseDouble(infantPrice) * Double.parseDouble(infantQty);
			System.out.println("Infant retail total "+infantRetailTotal);
		}
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(response.get("response body"));
		
		JSONArray priceBreakdowns = (JSONArray)object.get("priceBreakdowns");
		
		Object obtainedAdultRetailTotal = null;
		Object obtainedChildRetailTotal = null;
		Object obtainedInfantRetailTotal = null;
		
		for(int i=0; i<priceBreakdowns.size();i++){
			JSONObject totalPricePerAgeBand = (JSONObject)priceBreakdowns.get(i);
			JSONObject retailPrice = (JSONObject)totalPricePerAgeBand.get("totalPricePerAgeBand");
			Object priceType = retailPrice.get("priceType");
			if(priceType.toString().contains("ADULT")){
				obtainedAdultRetailTotal = retailPrice.get("retailPrice");
				System.out.println((i+1)+". Adult retail total from API "+obtainedAdultRetailTotal);
				Assert.assertEquals(obtainedAdultRetailTotal, adultRetailTotal,"Obtained Adult retail total ["+obtainedAdultRetailTotal+"] doesn't match with expected ["+adultRetailTotal+"]");
				System.out.println("Obtained Adult retail total ["+obtainedAdultRetailTotal+"] matches with expected");
			}	
			else if(priceType.toString().contains("CHILD")){
				obtainedChildRetailTotal = retailPrice.get("retailPrice");
				System.out.println((i+1)+". Child retail total from API "+obtainedChildRetailTotal);
				Assert.assertEquals(obtainedChildRetailTotal, childRetailTotal,"Obtained Child retail total ["+obtainedChildRetailTotal+"] doesn't match with expected ["+childRetailTotal+"]");
				System.out.println("Obtained Child retail total ["+obtainedChildRetailTotal+"] matches with expected");
			}	
			else if(priceType.toString().contains("INFANT")){
				obtainedInfantRetailTotal = retailPrice.get("retailPrice");
				System.out.println((i+1)+". Infant retail total from API "+obtainedInfantRetailTotal);
				Assert.assertEquals(obtainedInfantRetailTotal, infantRetailTotal,"Obtained Infant retail total ["+obtainedInfantRetailTotal+"] doesn't match with expected ["+infantRetailTotal+"]");
				System.out.println("Obtained Infant retail total ["+obtainedInfantRetailTotal+"] matches with expected");
			}
		}	
	}

	public HashMap<String, String> requestGetProductLanguageOptions(String current_TestName, String endPoint, String partnerId, String bookingOptionId) {
		
		String userName = api.getProperty("Api.userName");
		String password = api.getProperty("Api.password");
		String parameters = "?bookingOptionId="+bookingOptionId;
		String env = BaseSetup.environment;
		
		String requestURL; String subscriptionKey;
		
		if(!(env.equals("prod") || env.equals("preprod"))){
			requestURL = api.getProperty("Api.placepass_base_URL") + endPoint + parameters;
			subscriptionKey = api.getProperty("Api.subscription_key");
		}
		else{
			requestURL = api.getProperty("Api.production_base_URL") + endPoint + parameters;
			subscriptionKey = api.getProperty("Api.prod_subscription_key");
		}		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("requestURL is -- "+requestURL);
		
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}		
				
	//	execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "GET", false, "", false, current_TestName, false, "");
	
		System.out.println("In GET(Get Product Language Options) API response, Status code is -- " + response.get("status code"));
		System.out.println("In GET(Get Product Language Options) API response, Status message is -- " + response.get("status message"));
		
		return response;
	
	}

	public void verifyProductLanguageOptions(String bookingOptionId, String languageServices, String Value, String productId) {
	
       System.out.println("**************************************************************");
	
	//  Get productOptionList content
		List<String> languageServicesList = jsonObject.getValueFromSimpleJsonKey(response.get("response body"), languageServices);
		
		List<String> languageServicesIds = jsonObject.getValueFromArrayJsonKeyWithoutParentNode(languageServicesList.get(0), "Value");
		
		for(int i=0;i<languageServicesIds.size();i++){
			System.out.println("Product Language option ["+(i+1)+"] - "+languageServicesIds.get(i));
		}
		
	//	Check atleast one language is returned in response JSON	
		Assert.assertTrue(languageServicesIds.size()>0, "Not even single language option has been returned for product "+productId);
		System.out.println(languageServicesIds.size() +" language options returned for product "+productId);
		System.out.println("**************************************************************");		
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

