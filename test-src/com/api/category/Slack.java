package com.api.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.datamanager.ConfigManager;
import com.utilities.APIHelpers;

public class Slack extends APIHelpers{
	
//	Declaration of respective API Parts instances
	public ConfigManager api = new ConfigManager("Api");
		
	public void requestSlack(String requestJSON,String requestBody){
		
		String requestURL = api.getProperty("Api.slack_channel");		
				
		List<String> headers = new ArrayList<String>();
		HashMap<String, String> response;
		
		headers.add("Content-Type:text/html");
		headers.add("Accept:text/html");				
		
//		execute API request
		response = executeRequestMethod(null, null, requestURL, headers, "POST", false, "", false, "", true, requestBody, requestJSON);
		System.out.println("In Post(Get Products) API response, Status code is -- " + response.get("status code"));
		System.out.println("In Post(Get Products) API response, Status message is -- " + response.get("status message"));		
	}
	
}
