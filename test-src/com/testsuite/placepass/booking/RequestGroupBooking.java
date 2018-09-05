package com.testsuite.placepass.booking;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Booking;
import com.base.BaseSetup;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

	public class RequestGroupBooking extends BaseSetup {	
	
		Booking booking;
		JSONManager jsonObject;
		
		public String currentTestName;
		List<String> headers = new ArrayList<String>();
	
		/**
		 * Purpose - Initializes the API parts instances
		 */@BeforeMethod(alwaysRun=true)
		 public void Setup(Method method)
		 {
			 jsonObject=new JSONManager();
			 booking= new Booking();
			 currentTestName=method.getName();
			 
		 }
		 
		 @Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_RequestGroupBooking")
		 public void tc_API_001_RequestGroupBookingUsingPostMethod(String endPoint, String partnerId, String requestJSON, String status, String message)
		 {
			 booking.requestGroupBooking(currentTestName, endPoint, partnerId, requestJSON);
			 
		 //	 verify responsecode and message
			 booking.verifyStatusCode(status);
			 booking.verifyStatusMessage(message);
			 
			 System.out.println("Group booking request succeeded with response code 200");
		 }	
	
}
