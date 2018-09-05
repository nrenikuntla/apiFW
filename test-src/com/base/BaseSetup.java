/**************************************** PURPOSE **********************************

 - This class contains the code related to Basic setup of TestSuites such as Create Results Folder, Adding steps LogFile

USAGE
 - Inherit this BaseClass for any TestSuite class. You don't have to write any @Beforeclass and @AfterClass
 - actions in your TestSuite Classes
 
 - Example: 
 --import Com.Base
 --- public class <TestSuiteClassName> extends BaseClass
 */
package com.base;

import java.io.File;
import java.util.Random;

import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.datamanager.ConfigManager;
import com.datamanager.IExcelManager;
import com.excel.utility.UploadResultsToDB;
import com.excel.utility.ResultsToLocalExcel;
import com.utilities.ReportSetup;
import com.utilities.UtilityMethods;

@Listeners({ com.listener.TestListener.class })
public class BaseSetup{
	private boolean isReportFolderCreated = true;
	private Logger log = Logger.getLogger("BaseClass");
	public ConfigManager sys = new ConfigManager();
	private ConfigManager test = new ConfigManager("TestDependency");
	public IExcelManager excel;
	public ResultsToLocalExcel rs;
	public String fileSeparator = File.separator;
	public static String runID;	
	public static String environment;
	
	/**
	 * Creates folder structure to store the automation reports
	 * 
	 * @throws Exception
	 */
	@BeforeSuite
	@Parameters({"environment"})
	public void beforeSuite(@Optional("qa")String env) throws Exception {
		if (isReportFolderCreated) {
			ReportSetup.createFolderStructure();
			isReportFolderCreated = false;
		}
		log.info("<h2>--------------------SuiteRunner Log-------------------------<h2>");
		log.info("<h2>--------------------Environment "+env.toUpperCase()+"-------------------------<h2>");
		runID = String.valueOf(generateRunID());
		environment = env;
	}
	

	/**
	 * 
	 * This method adds Log file link to ReportNG report
	 * 
	 * @throws Exception
	 */
	@AfterSuite
	public void AddLogFileToReport() throws Exception {
		log.info("after suite");
		String sSeperator = UtilityMethods.getFileSeperator();
		String logFilePath = ".." + sSeperator + "Log.log";
		Reporter.log("<br>");
		Reporter.log("<a class=\"cbutton\" href=\"" + logFilePath
				+ "\">Click to Open Log File</a>");
		String PageLoadTimeSummaryFilePath = ".." + sSeperator
				+ "PageLoadTime_Summary.html";
		File f = new File(PageLoadTimeSummaryFilePath);
		if (f.exists()) {
			Reporter.log("<br>");
			Reporter.log("<a class=\"cbutton\" href=\""
					+ PageLoadTimeSummaryFilePath
					+ "\">Click to Open PageLoad Time Summary File</a>");
		}
		/*
		// Push results to D3
		UploadResultsToDB.uploadResultsToD3API();
		
		// Move latest results to old sheet
		ResultsToLocalExcel rs = new ResultsToLocalExcel(sys.getProperty("TestResultExcelFile"));
		rs.moveLatestToOld(sys.getProperty("ArchiveSheet"),sys.getProperty("ActiveSheet"));*/
	}
	
	/**
	 * Generate random number from a number range to be used as Run Id
	 */
	public long generateRunID(){
		Random rand = new Random();
		return rand.nextInt(500000) + 100000;
	}

	/**
	 * 
	 * This method is used to know whether the dependent test has passed or not
	 * 
	 * @param dependentTestName, Need to pass the dependent test name
	 * @throws SkipException
	 */
	public void hasDependentTestMethodPassed(String dependentTestName)
			throws SkipException {
		String currentTestName = Thread.currentThread().getStackTrace()[2]
				.getMethodName();
		if (test.getProperty(dependentTestName) != null) {
			if (test.getProperty(dependentTestName).equalsIgnoreCase("pass")) {
				log.info("dependent test - " + dependentTestName
						+ " has passed \n Running test - " + currentTestName);
			} else {
				log.info("dependent test - " + dependentTestName
						+ " has failed \n Hence test - " + currentTestName
						+ "is skipped");
				throw new SkipException("Dependent test - " + dependentTestName
						+ " has failed \n Hence test - " + currentTestName
						+ "is skipped");
			}
		} else {
			log.info("dependent test - " + dependentTestName
					+ " did not run \n Hence test - " + currentTestName
					+ "is skipped");
			throw new SkipException("Dependent test - " + dependentTestName
					+ " did not run \n Hence test - " + currentTestName
					+ "is skipped");
		}

	}
	
}
