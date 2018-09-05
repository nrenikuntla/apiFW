package com.testsuite.placepass.booking;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class ViewBookingsHistory extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	Booking booking;
	public ConfigManager api;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		api = new ConfigManager("Api");
		booking = new Booking();
		currentTestName = method.getName();
		
	}
	
	/**
	 * @throws ParseException 
	 *  
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_ViewBookingsHistory")
	public void tc_API_001_ViewBookingsHistoryUsingGetMethod(String endPoint, String partnerId, String hitsPerPage, String customerId, String pageNumber) throws ParseException {
		
		
		booking.requestViewBookingsHistory(currentTestName, endPoint, partnerId, customerId, hitsPerPage, pageNumber);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyBookingsHistory(hitsPerPage, customerId, pageNumber);
		
	}
	
	
	/**
	 * Without partner-id 
	 * Invalid partner-id
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_ViewBookingsHistory")
	public void tc_API_002_ViewBookingsHistoryUsingGetMethod(String endPoint, String partnerId, String hitsPerPage,
			String customerId, String pageNumber, String status, String message, String ppcode, String ppmessage) {
		
		
		booking.requestViewBookingsHistory(currentTestName, endPoint, partnerId, customerId, hitsPerPage, pageNumber);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode, ppmessage);
		
	}
	
	
	/**
	 * Without customerId 
	 * Invalid customerId
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC003_ViewBookingsHistory")
	public void tc_API_003_ViewBookingsHistoryUsingGetMethod(String endPoint, String partnerId, String hitsPerPage,
			String customerId, String pageNumber, String status, String message) throws ParseException{
		
		
		HashMap<String,String> response = booking.requestViewBookingsHistory(currentTestName, endPoint, partnerId, customerId, hitsPerPage, pageNumber);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(customerId!=null){
			String responseString = response.get("response body");
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(responseString);
			Object totalRecords = object.get("totalRecords");
			
			Assert.assertEquals(totalRecords.toString(), "0","Obtained "+totalRecords+" records for customerId "+customerId);
			System.out.println("Obtained "+totalRecords+" records for customerId "+customerId+" is as expected");
		}
		else{
			System.out.println("Could not query for previous records since customerId was not supplied");
		}
	}
	
	
	/**
	 * Empty hitsPerPage 
	 * Invalid hitsPerPage (-1)
	 * Empty pageNumber
	 * Invalid pageNumber (-1)
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC004_ViewBookingsHistory")
	public void tc_API_004_ViewBookingsHistoryUsingGetMethod(String endPoint, String partnerId, String hitsPerPage,
			String customerId, String pageNumber, String status, String message, String ppcode, String ppmessage){
		
		booking.requestViewBookingsHistory(currentTestName, endPoint, partnerId, customerId, hitsPerPage, pageNumber);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
		else
			System.out.println("Could not query for previous records since hitsPerPage/pageNumber was not supplied");		
	}
	
	
	/**
	 * hitsPerPage > total records
	 * hitsPerPage == total records
	 * hitsPerPage < total records page=0
	 * hitsPerPage < total records page=1
	 * @throws ParseException 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC005_ViewBookingsHistory")
	public void tc_API_005_ViewBookingsHistoryUsingGetMethod(String endPoint, String partnerId, String hitsPerPage,
			String customerId, String pageNumber) throws ParseException{
		
		booking.requestViewBookingsHistory(currentTestName, endPoint, partnerId, customerId, hitsPerPage, pageNumber);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyBookingsHistory(hitsPerPage, customerId, pageNumber);				
	}
	
}
