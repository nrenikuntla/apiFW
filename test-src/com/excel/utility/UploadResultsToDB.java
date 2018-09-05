package com.excel.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.datamanager.ConfigManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Exception.FilloException;
import Fillo.Connection;
import Fillo.Fillo;
import Fillo.Recordset;

public class UploadResultsToDB
{
	public static String fileSeparator = System.getProperty("file.separator");
	
	static Fillo fillo;
	public static Connection connection;
	public static String excelPath = "TestAutomationResults.xlsx";

	
	public static void main(String []i)
	{
		uploadResultsToD3API();
	}
		
	public static void uploadResultsToD3API()
	{
		Logger log = Logger.getLogger("UploadResultsToDB");
		ConfigManager sys = new ConfigManager();
		ResultsToLocalExcel resultToExcel = new ResultsToLocalExcel(sys.getProperty("TestResultExcelFile"));
		Connection connection = resultToExcel.connection;
		String latestSheet = sys.getProperty("ActiveSheet");
		
		JsonArray ja = new JsonArray();
		try
		{
			Recordset recordset =connection.executeQuery("Select * from "+latestSheet);
			ArrayList<String> columns = recordset.getFieldNames();
			HashMap<String, String> columnsAndValues = new HashMap<>();
			while (recordset.next())
			{
				JsonObject jo = new JsonObject();
				for(int c=0;c<columns.size();c++)
				{
					columnsAndValues.put(recordset.getField(c).name(), recordset.getField(recordset.getField(c).name()));
					String propertyKey = recordset.getField(c).name().toLowerCase();
					String propertyKey_split[] = propertyKey.split("_");
					String d = propertyKey_split[0];
					for(int i=1;i<propertyKey_split.length;i++)
					{
						String s1 = propertyKey_split[i].substring(0, 1).toUpperCase();
						d= d+s1+propertyKey_split[i].substring(1);						
					}					
					jo.addProperty(d,  recordset.getField(recordset.getField(c).name()));
				}
				ja.add(jo);				
			}
			System.out.println(post(ja.toString()));
			System.out.println(ja);
			log.info("Writing the details is completed");
		}
		catch(FilloException e)
		{
			log.info("Fillo has some issue");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			log.info("some Exception occured while writing the data");
			e.printStackTrace();
		}
		finally
		{
			if(!(connection==null))
			{
				log.info("Connection not closed. Hence retry to close");
				connection.close();
				log.info("Connection closed");
			}
		}
	}
	
	public static String post(String strBody) throws ClientProtocolException, IOException
	{
		ConfigManager sys =  new ConfigManager();
		String strURL = sys.getProperty("D3.API.URL");
		String strContentType = "application/json";
		HttpClient client =  HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(strURL);
		post.addHeader("Content-Type", strContentType);
		post.setEntity(new StringEntity(strBody));
		HttpResponse response = client.execute(post);
		response.getStatusLine().getStatusCode();
		return getResponseBody(response); 
	}
	/**
	 * 
	 * To get response body
	 * 
	 * @param HttpResponse
	 *            response object
	 * @return String Response body
	 */
	private static String getResponseBody(HttpResponse response) {
		try {
			InputStreamReader in = new InputStreamReader(response.getEntity()
					.getContent());
			BufferedReader br = new BufferedReader(in);
			String responseLine;
			String responseBody = "";
			while ((responseLine = br.readLine()) != null) {
				responseBody = responseBody + responseLine;
			}
			if (responseBody.length() > 0) {				
				return responseBody;
			} else {				
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

}
