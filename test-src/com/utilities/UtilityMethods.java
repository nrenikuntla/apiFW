/*************************************** PURPOSE **********************************

 - This class contains all Generic methods which are not related to specific application
*/


package com.utilities;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import nl.flotsam.xeger.Xeger;

//import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
import org.testng.Assert;
//import com.testng.Assert;

import com.datamanager.ConfigManager;

public class UtilityMethods 
{

	static ConfigManager sys = new ConfigManager();
	static Logger log = Logger.getLogger("UtilityMethods");
	static Thread thread;
	//ip address with regular expression
    private static final String IPADDRESS_PATTERN = 
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/**
	 * Purpose - to get the system name
	 * @return - String (returns system name)
	 */
    public static String machineName()
    {
    	String sComputername = null;
    	try 
    	{
			sComputername=InetAddress.getLocalHost().getHostName();
		} 
    	catch (UnknownHostException e) 
    	{
			log.error("Unable to get the hostname..."+ e.getCause());
		}
		return sComputername;
    }
    
    /**
     * TODO To get the entire exception stack trace
     * 
     * @return returns the stack trace
     */
    public static String getStackTrace()
    {
		String trace = "";
		int i;
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for(i=2;i<stackTrace.length;i++)
		{
			trace = trace+"\n"+stackTrace[i];
		}
		return trace;
    }

    

    /**
     * Purpose - to get current date and time
     * @return - String (returns date and time)
     */
    public static String getCurrentDateTime()
    {
    	Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
        String dateNow = formatter.format(currentDate.getTime());
        return dateNow;    	
    }
    /**
     * Purpose - to get  time stamp
     * @return - time 
     */
    public static String timeStamp()
    {
    	Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ssa");
		String timeStamp = sdf.format(date);
        return timeStamp;    	
    }
    /**
	 * Purpose - To convert given time in "yyyy-MM-dd-HH:mm:ss" to IST time
	 * @returns date in String format 
	 * @throws Exception
	 */
	
    public static String convertToISTTime(String origTime) 
	{

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        TimeZone obj = TimeZone.getTimeZone("GMT");
        formatter.setTimeZone(obj);
        try 
        {
			Date date = formatter.parse(origTime);
			formatter = new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
			//System.out.println(date);
			return formatter.format(date);
		} catch (ParseException e) {
			log.error("Cannot parse given date .." + origTime);
			log.info("returning current date and time .." + origTime);
		}
		return getCurrentDateTime();
    }    

    /**
     * Purpose - to display message box
     * @param infoMessage
     * @param location
     */
    public static void infoBox(String infoMessage, String location)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + location, JOptionPane.INFORMATION_MESSAGE);
    }

    
    /**
     * Purpose - to stop the execution
     * @param suspendRunImagePath
     * @throws Exception
     */
    public static void suspendRun(String suspendRunImagePath) throws Exception
    {
    	Assert.fail("TEST RUN HAS BEEN SUSPENDED");
    }
    
    public static String randomeCharactres()
    {
    	Random ran = new Random();
		int length = 7;
		char ch = ' ';
		String randomLet = "";

		for (int i = 0; i < length; i++) {
			ch = (char) (ran.nextInt(26) + 97);
			randomLet = ch + randomLet;
		}
		return randomLet;
    }

    /**
     * Purpose - to generate the random number which will be used while saving a screenshot
     * @return - returns a random number
     */
 	public static int getRandomNumber()
 	{
 		Random rand = new Random();
 		int numNoRange = rand.nextInt();
 		return numNoRange;
     }
 	
 	/**
 	 * TODO Typecasts the wait time values from String to integer
 	 *
 	 * @param WaitTime
 	 * @return returns value of wait time in integer
 	 * @throws Exception
 	 */
 	public static int getWaitTime(String WaitType)
 	{
 		int iSecondsToWait = 15;
 		String wait;
 		if(WaitType!=null&&!WaitType.equalsIgnoreCase(""))
 		{
 			wait = sys.getProperty(WaitType);
 		}
 		else
 		{
 			log.error("WaitType cannot be empty...defaulting to MEDIUM WAIT");
 			wait = sys.getProperty("WAIT.MEDIUM");
 		}
 		try
 		{
 			iSecondsToWait = Integer.parseInt(wait);
 		}
 		catch(NumberFormatException e)
 		{
 			log.error("Please check the config file. Wait values must be a number...defaulting to 15 seconds");
 		} 		
 		return iSecondsToWait;		
 	}
 	
 	



	/**
	 * Method: To get caller method and class names
	 */
/*	public static void preserveMethodName()
	{
		Throwable t = new Throwable(); 
		StackTraceElement[] elements = t.getStackTrace();
		String callerClassName = elements[1].getClassName();
		String callerMethodName = elements[1].getMethodName(); 
		String sMethod = "\"" + callerMethodName + "\"" + " method from " + callerClassName + " class";
		ReportHelper.setsMethodName(sMethod);		
	}*/
	
	/**
	 * 
	 * TODO method to run balloon popup process/method in a separate thread.
	 *
	 */
	public static void currentRunningTestCaseBalloonPopUp(final String sTestName)
	{
		thread = new Thread() 
		{
			 public void run()
			 {
				BalloonPopUp.currentRunningTestCase(sTestName);
			 }			
		};
		thread.start();
	}
	
	/**
	 * 
	 * TODO method to verify if the balloon pop process/method is ended or not.
	 */
	public static void verifyPopUp()
	{
		try 
		{
			thread.join();
		} catch (InterruptedException e) {
			log.error("balloon popup thread Interrupted"+e.getStackTrace());
		} 
	}

	
	/**
	 * 
	 * TODO Gives the name of operating system your are currently working on
	 * 
	 * @return returns the OS name
	 */
	public static String getOSName()
	{
		return System.getProperty("os.name");
	
	}	
	
	/**
	 * 
	 * TODO Gives the seperator value according to Operation System
	 * 
	 * @return returns the seperator with respect to Operation System
	 */
	public static String getFileSeperator()
	{		
		return System.getProperty("file.separator");
	}

	public static String generateStringFromRegEx(String regEx)
	{
		Xeger generator = new Xeger(regEx);
		return generator.generate();
	}
	
   /**
    * Validate ip address with regular expression
    * @param ip ip address for validation
    * @return true valid ip address, false invalid ip address
    */
    public static boolean validateIP(final String ip){		  
	 return Pattern.compile(IPADDRESS_PATTERN).matcher(ip).find();
    }
    
    public static String replaceVariablesWithRuntimeProperties(String strInputString, String replace_Value){

		String guess = "$";
		
		try{
			log.info("replacing variables");
		while(strInputString.indexOf(guess)>=0){
			int startingindex=strInputString.indexOf(guess);
			int Endingindex=strInputString.indexOf("}")+1;
			while(Endingindex<startingindex){
				Endingindex=strInputString.indexOf("}",Endingindex)+1;
			}
			String substr=strInputString.substring(startingindex, Endingindex);
			strInputString=strInputString.replace(substr,replace_Value);
		}
		log.info(strInputString);
		return strInputString;
		}
		catch(Exception e){
			log.error("Error while replacing values");
			return strInputString;
		}
	}
    public static String replaceVariablesWithRandomeNumber(String strInputString, String newStr){

		String guess = "$";
		
		try{
			log.info("replacing variables");
		while(strInputString.indexOf(guess)>=0){
			int startingindex=strInputString.indexOf(guess);
			int Endingindex=strInputString.indexOf("}")+1;
			while(Endingindex<startingindex){
				Endingindex=strInputString.indexOf("}",Endingindex)+1;
			}
			String substr=strInputString.substring(startingindex, Endingindex);
			strInputString=strInputString.replace(substr,newStr);
		}
		log.info(strInputString);
		return strInputString;
		}
		catch(Exception e){
			log.error("Error while replacing values");
			return strInputString;
		}
	}
    
    public static String generateRandomData(String type, int...len ) {
		  final String num = "0123456789";
		  final String AB = num+"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		  Random rnd = new Random();
		  
		  if(len==null){
			  len[0]=10;
		  }
		  if(type.toLowerCase().contains("string"))
		  {
		  StringBuilder sb = new StringBuilder( len[0] );
		     for( int i = 0; i < len[0]; i++ ) 
		        sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		     return sb.toString();
		  }
		  if(type.toLowerCase().contains("num")){
			  StringBuilder sb = new StringBuilder( len[0] );
			     for( int i = 0; i < len[0]; i++ ) 
			        sb.append( num.charAt( rnd.nextInt(num.length()) ) );
			     return sb.toString();
		  }
		  if(type.toLowerCase().contains("email")){
			  return getCurrentDateTime("ddMMyy_HHmmss")+"@email.com";
		  }
		  if(type.toLowerCase().contains("phone")){
			  return getCurrentDateTime("yyMMddHH")+generateRandomData("NUM", 2);
		  }
		  if(type.toLowerCase().contains("today")){
			  return getCurrentDateTime("yyyy-MM-dd");
		  }
		  else
			  return null;
	  }
    
    /**
     * Purpose - to get current date and time
     * @return - String (returns date and time)
     */
    public static String getCurrentDateTime(String format)
    {
    	Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter= new SimpleDateFormat(format);
        String dateNow = formatter.format(currentDate.getTime());
        return dateNow;    	
    }
    
    /**
     * Purpose - to make file/folder path compatible with OS
     * @return - file absolute path
     */
    public static String filePath(String strPath){
    	
    	if(strPath.contains("//")){
    		strPath.replaceAll("//", File.separator);
    	}
    	if(strPath.contains("\\")){
    		strPath.replaceAll("\\", File.separator);
    	}
    	while(strPath.contains("/")){
    		strPath.replaceAll("/", File.separator);
    	}
    	if(!strPath.toLowerCase().contains(System.getProperty("user.dir")))
    	{
    		strPath.concat(System.getProperty("user.dir"));
    	}
    	return strPath;
    	
    }
    /**
	 * Validate input value is digit or not
	 * @param str input string value
	 * @return true value is digit, false non digit
	 */
	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
}