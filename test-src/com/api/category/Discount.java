package com.api.category;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

public class Discount extends APIHelpers{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	public ConfigManager api = new ConfigManager("Api");
	JSONManager jsonObject = new JSONManager();
	List<String> headers = new ArrayList<String>();
	HashMap<String, String> response;
	
	
	public HashMap<String,String> requestValidateDiscount(String current_TestName, String endPoint, String partnerId, String requestBody, String requestJSON, String... environment){
		
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
		
		if(headers != null){
			headers.clear();
			headers.add(partnerId);
			headers.add(api.getProperty("Api.Header.contentType"));
			headers.add(api.getProperty("Api.Header.accept"));
			headers.add(subscriptionKey);
			if(!(env.equals("prod")))
				headers.add("env:"+env);
		}		
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, current_TestName, true, requestBody, requestJSON);
		System.out.println("In Post(Validate Discount) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Validate Discount) API response, Status message is -- " + response.get("status message"));
		
		return response;
		
	}
	
	public void verifyStatusCode(String statusCode){
		verifyStatusCode(response.get("status code"), statusCode);
	}
	
	public void verifyStatusMessage(String statusMessage){
		verifyStatusMessage(response.get("status message"), statusMessage);
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
	
	/**
	 * IF code is active and applicable
	 *  1. Check respective discountCode is returned
	 *  2. Check discountType
	 *  3. Check discountValue
	 *  4. Check calculated discountAmount is obeying PERCENTAGE or FIXED based on type
	 *  5. Check min/max discountAmount as applicable
	 *  6. Check totalPriceAfterDiscount is difference of totalPrice & discountAmount
	 * ELSE
	 *  1. Verify pp-code
	 *  2. Verify pp-message  
	 * @throws ParseException 
	 */
	public void verifyDiscountCode(String discountCode, String expired, String discountType, String discountValue, String minSpend,
			String maxSpend, String minDiscount, String maxDiscount, String status, String message, String ppcode, String ppmessage) throws ParseException{
		
		String responseString = response.get("response body");
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		
		JSONObject priceSummaryObject = (JSONObject) object.get("priceSummary");
		Object totalPrice = priceSummaryObject.get("totalPrice");
		Double obtainedTotalPriceAfterDiscount = (Double) priceSummaryObject.get("totalPriceAfterDiscount");
		obtainedTotalPriceAfterDiscount = (double) Float.parseFloat(new DecimalFormat("##.00").format(obtainedTotalPriceAfterDiscount));
		
		// Get service fee
		Object fees = priceSummaryObject.get("fees");
		Object feeAmount = 0.0;
		
		if(fees!=null){
			JSONArray feesArray = (JSONArray) priceSummaryObject.get("fees");
			Iterator<JSONObject> i = feesArray.iterator();
			while(i.hasNext()){
				
				JSONObject discountObject = i.next();
				
				feeAmount = discountObject.get("amount");			
			}
		}
		
		Float calculatedDiscountAmount = null;
		
		// Calculate discount amount based on type
		if(discountType.equals("PERCENTAGE")){
			calculatedDiscountAmount = (Float.parseFloat(totalPrice.toString()) - Float.parseFloat(feeAmount.toString())) * (Float.parseFloat(discountValue)/100);
			BigDecimal d = new BigDecimal(calculatedDiscountAmount.toString());
			d = d.setScale(2, BigDecimal.ROUND_UP);
			calculatedDiscountAmount = Float.parseFloat(d.toString());
		}
		else if(discountType.equals("FIXED")){
			calculatedDiscountAmount = Float.parseFloat(discountValue);
		}
		
		JSONArray discountArray = (JSONArray) object.get("discount");
		
		Iterator<JSONObject> j = discountArray.iterator();
		
		while(j.hasNext()){
			
			JSONObject discountObject = j.next();
			
			Object obtainedDiscountCode = discountObject.get("discountCode");
			Assert.assertEquals(obtainedDiscountCode,discountCode,"Obtained discount code "+obtainedDiscountCode+" doesn't match with expected code "+discountCode);
			System.out.println("Obtained discount code "+obtainedDiscountCode+" is as expected");
		
			Object obtainedDiscountType = discountObject.get("discountType");
			Assert.assertEquals(obtainedDiscountType,discountType,"Obtained discount type "+obtainedDiscountType+" doesn't match with expected type "+discountType);
			System.out.println("Obtained discount type "+obtainedDiscountType+" is as expected");
			
			Object obtainedDiscountValue = discountObject.get("discountValue");
			Assert.assertEquals(Double.parseDouble(obtainedDiscountValue.toString()),Double.parseDouble(discountValue),"Obtained discount value "+obtainedDiscountValue+" doesn't match with expected type "+discountValue);
			System.out.println("Obtained discount value "+obtainedDiscountValue+" is as expected");
			
			Object obtainedDiscountAmount = discountObject.get("discountAmount");
			Assert.assertEquals(Float.parseFloat(obtainedDiscountAmount.toString()),calculatedDiscountAmount,"Obtained discount ["+obtainedDiscountAmount+"] doesn't match with calculated discount ["+calculatedDiscountAmount+"] at "+discountValue+" "+discountType);
			System.out.println("Obtained discount ["+obtainedDiscountAmount+"] matches with calculated discount ["+calculatedDiscountAmount+"] at "+discountValue+" "+discountType);
			
			if(minDiscount!=null)
				Assert.assertTrue(Double.parseDouble(minDiscount) <= Double.parseDouble(obtainedDiscountAmount.toString()),"Discount amount ["+obtainedDiscountAmount+"] is less than minimum allowed discount ["+maxDiscount+"]");
			if(maxDiscount!=null)
				Assert.assertTrue(Double.parseDouble(obtainedDiscountAmount.toString()) <= Double.parseDouble(maxDiscount),"Discount amount ["+obtainedDiscountAmount+"] is more than maximum allowed discount ["+maxDiscount+"]");
			System.out.println("Discount amount is within allowed range ["+minDiscount+"] <= ["+obtainedDiscountAmount+"] <= ["+maxDiscount+"]");
		
			
			
			Double calculatedTotalPriceAfterDiscount = (double) (Float.parseFloat(totalPrice.toString()) - Float.parseFloat(obtainedDiscountAmount.toString()));
			calculatedTotalPriceAfterDiscount = (double) Float.parseFloat(new DecimalFormat("##.00").format(calculatedTotalPriceAfterDiscount));
			if(discountCode.contains("HOLIDAY5")||discountCode.contains("HOLIDAY6"))
			{
				if(calculatedTotalPriceAfterDiscount<0.5)
				{
				calculatedTotalPriceAfterDiscount=(Double) 0.0;
				}

			}
				Assert.assertEquals(obtainedTotalPriceAfterDiscount, calculatedTotalPriceAfterDiscount,"Obtained totalPriceAfterDiscount ["+obtainedTotalPriceAfterDiscount+"] doesn't match with expected ["+calculatedTotalPriceAfterDiscount+"]");
				System.out.println("Obtained totalPriceAfterDiscount ["+obtainedTotalPriceAfterDiscount+"] is as expected");

			
		}
	}
}
