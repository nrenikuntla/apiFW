package com.datamanager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.csvreader.CsvReader;
import org.testng.Assert;
//import com.testng.Assert;
import com.utilities.UtilityMethods;


public class CSVManager {
	 private Logger log = Logger.getLogger("CsvExcelManager");
	 FileReader freader;
	 CsvReader reader;	
	 File file;
	 String sFilePath;
	/**
	 * Purpose - To initialize the CsvReader and checks whether file exists or not 
	 * @param filepath - pass path of the csv file
	 */
	  public CSVManager(String filePath){
		  this.sFilePath=filePath;
		  try{
		  file=new File(sFilePath);
		  if(file.exists())
		  {
			reader=new CsvReader(new FileReader(file));
		  } 
		  else
		  {
			  log.error("File -'" + sFilePath
						+ "' does not exist in Data Folder, Please Re-check given file"+UtilityMethods.getStackTrace());
			  Assert.fail("File -'" + sFilePath
						+ "' does not exist in Data Folder, Please Re-check given file");
		  }
		  } catch (FileNotFoundException e) {
			   log.error("File with Specified name - "+sFilePath+" does not exist \n" +UtilityMethods.getStackTrace());
				Assert.fail("File with Specified name - "+sFilePath+" does not exist \n" + 
			                   "Exception is - "+e.getCause());
		  }catch(NullPointerException e){
			  log.error("Please check the filepath provided - "+sFilePath+"\n"+UtilityMethods.getStackTrace());
				Assert.fail("Please check whether provided filepath is not null \n"+e.getCause()); 
		  }
		  catch (Exception e) {
			   log.error("Exception while reading file - "+sFilePath+"\n" +UtilityMethods.getStackTrace());
				Assert.fail("Exception while reading file - "+sFilePath+"\n" +e.getCause());
		  }
	  }
	 
	  /**
	   * Purpose -To get row count of a csv file
 	   * @param filepath - pass the path of .csv file
 	   * @returns row count as an integer
	   */
	@SuppressWarnings("resource")
	public int getRowCount(){
		  BufferedReader bufferedReader;
		  int count = 0;
	    try{
			bufferedReader = new BufferedReader(new FileReader(sFilePath));
		    while((bufferedReader.readLine()) != null)
				 {
				     count++;
				 }
	       }catch (IOException e) {
				log.info("Exception occurred while accessing the file - "+sFilePath);
				Assert.fail("Exception occurred while accessing the file - "+sFilePath+UtilityMethods.getStackTrace());
			}
		return count; 
	  }
	  /**
	   * Purpose - To get cell data from csv
	   * @Param row- Pass the row number 
	   * @Param column -Pass the column number
	   * @returns cellData as a string
	   */
	  public String getCellData(int row,int column) 
	  {
		  String value="";
		  try{
		  int i=0;
		    List<String> lines =Files.readAllLines(file.toPath(), 
		            StandardCharsets.UTF_8);
		    for (String line : lines) {
		    	if(i==row){
		        String[] array = line.split(",");
		        value=array[column];
		        break;
		    	}
		    	i++;
		    }
	  } catch(IOException e){
		  log.info("Exception occurred while accessing the file - "+sFilePath);
			Assert.fail("Exception occurred while accessing the file - "+sFilePath+UtilityMethods.getStackTrace());
		  }
	    return value.trim(); 
	  }
	   /**
		 * Purpose - Gets the single record data from.csv file and stores in an array
		 * @param - Pass row number which you want to retrieve
		 * returns entireRowData as a string array
		 */
	public String[] getEntireRowData(int j)
	{
		String[] rowData=new String[16];
		for(int i=0;i<16;i++){
			rowData[i]=getCellData(j, i);
		    }
		return rowData;
	}
	
	/**
	   * Purpose - To get cell data from csv
	   * @Param row- Pass the row number 
	   * @Param column -Pass the column number
	   * @returns whole column data as a List<String>
	   */
	  public List<String> getEntireColumnData(int column) 
	  {
		 List<String> columnData=new ArrayList<>() ;
		  try{
		 
		    List<String> lines =Files.readAllLines(file.toPath(), 
		            StandardCharsets.UTF_8);
		    for (String line : lines) {
		        String[] array = line.split(",");
		    columnData.add(array[column].trim());
		    }
	  } catch(IOException e){
		  log.info("Exception occurred while accessing the file - "+sFilePath);
			Assert.fail("Exception occurred while accessing the file - "+sFilePath+UtilityMethods.getStackTrace());
		  }
	    return columnData; 
	  } 
	  

}
