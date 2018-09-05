package com.testsuite.placepass.booking;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class FindPastBooking extends BaseSetup {
	
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
	 *  
	 * @throws ParseException
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_FindPastBooking")
	public void tc_API_001_FindPastBookingUsingGetMethod(String endPoint, String partnerId, String bookerEmail, String bookingReference) throws ParseException {
		
		
		booking.requestFindPastBooking(currentTestName, endPoint, partnerId, bookerEmail, bookingReference);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		booking.verifyPastBooking(bookerEmail,bookingReference);
		
	}
	
	
	/**
	 * Without partner-id  
	 * Invalid partner-id
	 * Empty booker email
	 * Invalid booker email
	 * Case sensitive booker email
	 * Empty booking reference
	 * Invalid booking reference 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC002_FindPastBooking")
	public void tc_API_002_FindPastBookingUsingGetMethod(String endPoint, String partnerId, String bookerEmail,
			String bookingReference, String status, String message, String ppcode, String ppmessage) throws ParseException {
		
		
		booking.requestFindPastBooking(currentTestName, endPoint, partnerId, bookerEmail, bookingReference);
		
	//	Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		if(ppcode!=null && ppmessage!=null)
			booking.verifyPPCodePPMessage(ppcode, ppmessage);
		else
			System.out.println("Could not query for past booking since bookerEmail/bookingReference was not supplied");
		
	}

}
