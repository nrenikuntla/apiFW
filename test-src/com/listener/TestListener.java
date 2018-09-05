package com.listener;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.base.BaseSetup;
import com.api.category.Slack;
import com.datamanager.ConfigManager;
import com.datamanager.JSONManager;
import com.utilities.UtilityMethods;
import com.excel.utility.JDBCConn;
import com.excel.utility.ResultsToLocalExcel;

public class TestListener extends TestListenerAdapter implements ISuiteListener
{

	JSONManager jsonObject = new JSONManager();
	ConfigManager sys = new ConfigManager();
	ConfigManager depend = new ConfigManager("TestDependency");
	Logger log =Logger.getLogger("TestListener");
	Slack slack = new Slack();
	
	private static boolean milestoneCreated = false;
	public String fileSeparator = File.separator;
	public static Object stackTrace;
	public static String errorMessage;
	public static Object exceptionClass;

	/**
	 * This method will be called if a test case is failed. 
	 * Purpose - For attaching captured screenshots and videos in ReportNG report 
	 */
	public void onTestFailure(ITestResult result)
	{
		depend.writeProperty(result.getName(),"Fail");

		log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n" );
		log.error("ERROR ----------"+result.getName()+" has failed-----------------" );
		log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n" );
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.setCurrentTestResult(result);
		UtilityMethods.verifyPopUp();
		Reporter.setCurrentTestResult(null);
		stackTrace = result.getThrowable();
		exceptionClass = ((Throwable) stackTrace).getClass().toString();
		errorMessage = ((Throwable) stackTrace).getMessage();
		HashMap <String, String> columnsAndValues = generateTestDetails(result,stackTrace,exceptionClass,errorMessage);
		try {			   
			JDBCConn.SaveTestResult(columnsAndValues);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		postResultToSlack(columnsAndValues);
	}

	/**
	 * This method will be called if a test case is skipped. 
	 * 
	 */
	public void onTestSkipped(ITestResult result)
	{			
		log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );
		log.warn("WARN ------------"+result.getName()+" has skipped-----------------" );
		log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );			
		depend.writeProperty(result.getName(),"Skip");

		Reporter.setCurrentTestResult(result);
		UtilityMethods.verifyPopUp();
		Reporter.setCurrentTestResult(null);
		stackTrace = "-";
		exceptionClass = "-";
		errorMessage = "-";
		HashMap <String, String> columnsAndValues = generateTestDetails(result,stackTrace,exceptionClass,errorMessage);
		try {			   
			JDBCConn.SaveTestResult(columnsAndValues);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will be called if a test case is passed. 
	 * Purpose - For attaching captured videos in ReportNG report 
	 */
	public void onTestSuccess(ITestResult result)
	{
		depend.writeProperty(result.getName(),"Pass");
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		log.info("###############################################################" );
		log.info("SUCCESS ---------"+result.getName()+" has passed-----------------" );
		log.info("###############################################################" );
		Reporter.setCurrentTestResult(result);
		UtilityMethods.verifyPopUp();
		Reporter.setCurrentTestResult(null);
		stackTrace = "-";
		exceptionClass = "-";
		errorMessage = "-";
		HashMap <String, String> columnsAndValues = generateTestDetails(result,stackTrace,exceptionClass,errorMessage);
		try {			   
			JDBCConn.SaveTestResult(columnsAndValues);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * This method will be called before a test case is executed. 
	 * Purpose - For starting video capture and launching balloon popup in ReportNG report 
	 */
	public void onTestStart(ITestResult result)
	{
		log.infoLevel("<h2>**************CURRENTLY RUNNING TEST************ "+result.getName()+"</h2>" );
		UtilityMethods.currentRunningTestCaseBalloonPopUp(result.getName());
	}

	public void onStart(ITestContext context) 
	{

	}

	public void onFinish(ITestContext context) 
	{
		
		
		/*Set<String> attributes = context.getAttributeNames();
		for(String attribute:attributes){
			log.info("Attribute - "+attribute);
			System.out.println("Attribute - "+attribute);
		}	
		Iterator<ITestResult> failedTestCases = context.getFailedTests().getAllResults().iterator();
		while (failedTestCases.hasNext())
		{
			ITestResult failedTestCase = failedTestCases.next();
			ITestNGMethod method = failedTestCase.getMethod();            
			if (context.getFailedTests().getResults(method).size() > 1)
			{
				if(sys.getProperty("KeepFailedResult").equalsIgnoreCase("false")){
					//log.info("failed test case remove as dup:" + failedTestCase.getTestClass().toString());
					failedTestCases.remove(); 
				}
			}
			else
			{	                
				if (context.getPassedTests().getResults(method).size() > 0)
				{
					if(sys.getProperty("KeepFailedResult").equalsIgnoreCase("false")){
						//log.info("failed test case remove as pass retry:" + failedTestCase.getTestClass().toString());
						failedTestCases.remove();
					}	                    
				}                          
			}            
		}*/
	}


	@Override
	public void onStart(ISuite iSuite) {
		if(sys.getProperty("UpdateResultsToTestRail").equalsIgnoreCase("true")){
			if(!milestoneCreated){
				milestoneCreated = true;
			}
		}
	}



	/**
	 * This method is used to update the results to testrail
	 * @param id - test case id
	 * @param testName - name of the test method
	 * @param testStatus - test method status
	 */
	private void updateToTestRail(String id, String testName, int testStatus) {
		if (sys.getProperty("UpdateResultsToTestRail").equalsIgnoreCase("true")) {
			if(UtilityMethods.isNumeric(id)){
			}	
		}
	}

	/**
	 * This method is used to get the test case id
	 * @param strTestName - name of the test method 
	 * @return test case id if test method contains id, otherwise return null
	 */
	private String getCaseId(String strTestName){
		if(sys.getProperty("UpdateResultsToTestRail").equalsIgnoreCase("true")){
			String[] arr = strTestName.split("_");
			return arr[arr.length-1];
		}
		else
			return null;
	}
	
	private HashMap <String, String> generateTestDetails(ITestResult result, Object stackTrace, Object exceptionClass, String errorMessage){
		
		ITestContext context = result.getTestContext();
		
		String testName = result.getName();
		String product = sys.getProperty("Product");
		String env = BaseSetup.environment;
		String testResult = getTestResult(result);
		String moduleName = determineModule(result);
		String testData = getParameters(result);
		String testDate = formatDate(Calendar.getInstance().getTimeInMillis());
		long startTime = result.getStartMillis();
		String start = formatDate(startTime);
		long finishTime = result.getEndMillis();
		String finish = formatDate(finishTime);
		String osName = System.getProperty("os.name");
		String hostName = System.getenv().get("COMPUTERNAME")+"\\"+System.getProperty("user.name");
		String imageLink = "-";
		String videoLink = "-";
		String testDevice = sys.getProperty("TestDevice");
		String projectId = sys.getProperty("ProjectID");
		String testSuite = getTestName(context);
		String runBy = sys.getProperty("RunBy");
		String execMode = "-";
		String failType = determineFailType(exceptionClass); 
		
		log.info("RunID: "+BaseSetup.runID);
		log.info("Product: "+product);
		log.info("ModuleName: "+moduleName);
		log.info("Testname: "+testName);
		log.info("Result: "+testResult);
		log.info("Testdata: "+testData);
		log.info("Stack trace: "+stackTrace);
		log.info("Browser/env: "+env);
		log.info("Test Machine: "+hostName);
		log.info("Image Link: "+imageLink);
		log.info("Video Link: "+videoLink);
		log.info("Test Device: "+testDevice);
		log.info("Test OS: "+osName);
		log.info("Test Date: "+testDate);
		log.info("Test Start: "+start);
		log.info("Test Finish: "+finish);
		log.info("Project ID: "+projectId);
		log.info("Test Suite: "+testSuite);
		log.info("Run By: "+runBy);
		log.info("Error message: "+errorMessage);
		log.info("Execution mode: "+execMode);
		log.info("Fail type: "+failType);
		
		HashMap<String, String> columnsAndValues = new HashMap<>();
		columnsAndValues.put("RUN_ID",BaseSetup.runID);
		columnsAndValues.put("PRODUCT_NAME",product);
		columnsAndValues.put("MODULE_NAME",moduleName);
		columnsAndValues.put("TESTCASE_TITLE",testName);
		columnsAndValues.put("TEST_STATUS",testResult);
		columnsAndValues.put("TEST_DATA",testData);
		columnsAndValues.put("FAIL_STACKTRACE",stackTrace.toString().replace("'", ""));
		columnsAndValues.put("TEST_BROWSER",env);
		columnsAndValues.put("TEST_MACHINE",hostName);
		columnsAndValues.put("IMAGE_LINK",imageLink);
		columnsAndValues.put("VIDEO_LINK",videoLink);
		columnsAndValues.put("TEST_DEVICE",testDevice);
		columnsAndValues.put("TEST_OS",osName);
		columnsAndValues.put("TEST_DATE",testDate);
		columnsAndValues.put("TEST_START_TIME",start);
		columnsAndValues.put("TEST_END_TIME",finish);
		columnsAndValues.put("PROJECT_ID",projectId);
		columnsAndValues.put("TEST_SUITE",testSuite);
		columnsAndValues.put("RUN_BY",runBy);
		columnsAndValues.put("ERROR_MESSAGE",errorMessage.replace("'", ""));
		columnsAndValues.put("EXECUTION_MODE",execMode);
		columnsAndValues.put("FAIL_TYPE",failType);
		/*String excelPath = sys.getProperty("TestResultExcelFile");
		ResultsToLocalExcel rs = new ResultsToLocalExcel(excelPath);
		rs.insertresultRow(sys.getProperty("ActiveSheet"),columnsAndValues);*/
		
		return columnsAndValues;
		
	}
	
	public String determineModule(ITestResult result){
		
		String packageName = result.getMethod().getTestClass().getName();
		if(packageName.contains("hertz")){
			return "RENTAL CARS"; 
		}else{
			return "EXPERIENCES";
		}
	}
	
	public String getTestResult(ITestResult result){
		String testResult = null;
		switch (result.getStatus()){
		case ITestResult.SUCCESS:
			testResult = "PASSED";
			break;
		case ITestResult.FAILURE:
			testResult = "FAILED";
			break;
		case ITestResult.SKIP:
			testResult = "SKIPPED";
			break;
		}
		return testResult;
	}

	public String getParameters(ITestResult result){
		
		Object[] params = result.getParameters();
		
		 StringBuffer param = new StringBuffer();
		 String p = "";
		 param.append("(");
		 for (int i = 0; i < params.length; i++) {
		  param.append(params[i] + ",");
		 }
		 p = param.substring(0, param.length() - 1) + ")".toString();
		  
		return p;
	}
	
	public String formatDate(long date){
		SimpleDateFormat testngformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH); 
		SimpleDateFormat dashboardformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.'530'");
		String sDate = new Date(date).toString();
		Date dateobj = null;
		try {
			dateobj = testngformat.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dashboardformat.format(dateobj);		
	}
	
	public String getSuiteName(ITestContext context){
		
		return context.getCurrentXmlTest().getSuite().getName();
	}
	
	public String getTestName(ITestContext context){
		
		return context.getName();
	}
	
	public String determineFailType(Object exceptionClass){
		if(((String) exceptionClass).contains("AssertionError"))
			return "Assertion";
		else if(((String) exceptionClass).contains("Exception"))
			return "Others";
		else
			return "-";
	}
	
	public void postResultToSlack(HashMap<String,String> columnsAndValues){
		
		String longString = "------------------------------------\n"+
		"RunID: "+columnsAndValues.get("RUN_ID")+"\n"+
		"Product: "+columnsAndValues.get("PRODUCT_NAME")+"\n"+
		"ModuleName: "+columnsAndValues.get("MODULE_NAME")+"\n"+
		"Testname: "+columnsAndValues.get("TESTCASE_TITLE")+"\n"+
		"Result: "+columnsAndValues.get("TEST_STATUS")+"\n"+
		"Testdata: "+columnsAndValues.get("TEST_DATA")+"\n"+
		"Stack trace: "+columnsAndValues.get("FAIL_STACKTRACE")+"\n"+
		"Environment: "+columnsAndValues.get("TEST_BROWSER")+"\n"+
		"Test Machine: "+columnsAndValues.get("TEST_MACHINE")+"\n"+
		"Test OS: "+columnsAndValues.get("TEST_OS")+"\n"+
		"Test Date: "+columnsAndValues.get("TEST_DATE")+"\n"+
		"Test Start: "+columnsAndValues.get("TEST_START_TIME")+"\n"+
		"Test Finish: "+columnsAndValues.get("TEST_END_TIME")+"\n"+
		"Test Suite: "+columnsAndValues.get("TEST_SUITE")+"\n"+
		"Error message: "+columnsAndValues.get("ERROR_MESSAGE")+"\n"+
		"Execution mode: "+columnsAndValues.get("EXECUTION_MODE")+"\n"+
		"Fail type: "+columnsAndValues.get("FAIL_TYPE")+"\n";
		
		String requestJSON = sys.getProperty("SlackJSON");
		String requestBody = jsonObject.readJSONContentToString(requestJSON);
		requestBody  = jsonObject.updateListOfValuesInSimpleJsonKey(requestBody, "text", longString);
		slack.requestSlack(requestJSON,requestBody);
	}
	
	@Override
	public void onFinish(ISuite arg0) {
		// TODO Auto-generated method stub
	}

}
