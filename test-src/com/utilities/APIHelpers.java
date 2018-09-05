package com.utilities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.passworddecoder.PasswordDecorder;


public class APIHelpers{
	public ExcelManager excel;
	public ConfigManager app_FileData=new ConfigManager("app");
	JSONManager jsonObj;
	HttpResponse response;
	public APIHelpers() {

		jsonObj=new JSONManager();
	} 
	public String time_Stamp;
	String responseBody;
	private static Logger log = Logger.getLogger(APIHelpers.class);
	private  static char cQuote = '"';
	private String fileseperator=File.separator;
	/**
	 * Purpose - Enum for the type of request
	 * @return enum - returns the corresponding request type
	 */
	public enum RequestType {
		POST, PUT, DELETE, GET, TRACE
	}
	/**
	 * Purpose - Enum  to get the status
	 * @return enum returns the corresponding status phrase and status code
	 */
	public enum Status {

		OK("200,OK"), CREATED(
				"201,Created"),NOT_FOUND("404,Not Found"),UNAUTHORIZED(
						"401,Unauthorized"),INTERNAL_SERVER_ERRROR("500,Internal Server Error"),BAD_REQUEST("400,Bad Request");
		String statusCode;
		public String getAction() {
			return this.statusCode;
		}
		private Status(String statusCode) {
			this.statusCode = statusCode;
		}
	}

	/**
	 * Purpose - To initialize Http client
	 * @return HttpClient
	 */

	public HttpClient getClient() {
		try {
			log.info("initializing HttpClient");
			return HttpClientBuilder.create().build();
		} catch (Exception e) {
			log.error("error while initialising Httpcient");
			return null;
		}
	}

	/**
	 * Purpose - To perform http requst
	 * @param strURL - End point URL
	 * @param RequestType - API method(GET/PUT/POST/DELETE/TRACE)
	 * @return HttpRequestBase
	 */
	public HttpRequestBase getHttpMethod(String strURL, RequestType requestType) {
		try{
			log.info("initialising request type : " + requestType);
			switch (requestType) {
			case POST:
				return new HttpPost(strURL);
			case PUT:
				return new HttpPut(strURL);
			case GET:
				return new HttpGet(strURL);
			case DELETE:
				return new HttpDelete(strURL);
			case TRACE:
				return new HttpTrace(strURL);

			default:
				log.error("No http method of type :- " + requestType);
				Assert.fail("No http method of type :- " + requestType);
				return null;
			}
		}
		catch(Exception e){
			log.error("Exception while initializing HTTP Request method" + requestType+UtilityMethods.getStackTrace());
			Assert.fail("Exception while initializing HTTP Request method" + requestType+e.getMessage());
			return null;
		}

	}

	/**
	 * Purpose - To get the status of response 
	 * @param HttpResponse - Response need to validate
	 * @return String[] with Status Code and Status Message
	 */
	public String[] getStatus(HttpResponse response) {
		try {
			log.info("get status from response");
			String actStatusMessage = response.getStatusLine()
					.getReasonPhrase();
			int actStatusCode = response.getStatusLine().getStatusCode();
			String[] actStatus = { actStatusMessage,
					String.valueOf(actStatusCode) };
			log.debug("Status code : " + actStatusCode);
			log.debug("Status Phrase : " + actStatusMessage);
			return actStatus;
		} catch (Exception e) {
			log.error("error while getting actual status : "
					+ e.getStackTrace());
			Assert.fail("error while getting actual status : " + e.getCause());
			return null;
		}
	}

	/**
	 * Purpose - To post the Http post Request
	 * @param List<NameValuePair> - List of NameValuePairs
	 * @param HttpPost -  method add header values to postheaders list
	 * @throws UnsupportedEncodingException
	 */
	public void postParamsHttpPost(List<NameValuePair> Parameters,
			HttpPost httpPost)
					throws UnsupportedEncodingException {
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(Parameters);
		httpPost.setEntity(formEntity);
	}
	/**
	 * Purpose - To post the http put request
	 * @param List<NameValuePair> - List of NameValuePairs
	 * @param HttpPut -  method add header values to postheaders list
	 * @throws UnsupportedEncodingException
	 */
	public void postParamsHttpPut(List<NameValuePair> Parameters,
			HttpPut httpPut)
					throws UnsupportedEncodingException {
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(Parameters);
		httpPut.setEntity(formEntity);
	}

	/**
	 * Purpose - To add header
	 * @param Headers[] -- each header should be in 'key:value' format
	 * @param HttpRequestBase
	 *            (HttpGet, HttpPost, HttpPut ......) method add headers to
	 *            HttpRequestBase
	 */
	public void addHeader(HttpRequestBase httpmethod, List<String> headers) {
		try {
			log.info("adding headers");
			for(int i=0;i<headers.size();i++){
				httpmethod.addHeader(headers.get(i).split(":")[0], headers.get(i).split(":")[1]);
				log.info("header "+headers.get(i).split(":")[0]+":"+headers.get(i).split(":")[1]+" is added");
			}
		} catch (Exception e) {

			log.error("Exception while adding header(s)"+UtilityMethods.getStackTrace());
			Assert.fail("Exception while adding header(s)"+e.getMessage());
		}

	}
	/**
	 * Purpose - To execute
	 * @param HttpClient client object which need to execute
	 * @param HttpRequestBase
	 *            (HttpGet, HttpPost, HttpPut ......) method add headers to
	 *            HttpRequestBase
	 * @return HttpResponse - it returns HttpResponse response of the executed request
	 */
	public HttpResponse execute(HttpClient client, HttpRequestBase httpMethod) {

		try {
			log.info("Executing : " + httpMethod.getMethod() + " Request");
			HttpResponse response = client.execute(httpMethod);
			//			statusCode.append(responce.getStatusLine());
			return response;
		}catch(UnknownHostException e){
			Assert.fail("Unable to contact host \nURL : "+httpMethod.getURI());
			log.error("Unable to contact host \nURL : "+httpMethod.getURI()+UtilityMethods.getStackTrace());
			return null;
		}catch (Exception e) {
			try{
				log.error("error while executing the request"+httpMethod.getURI()+"\n"+UtilityMethods.getStackTrace());
				Assert.fail("error while executing the request"+httpMethod.getURI()+"\n"+e.getMessage());
			}
			catch(Exception e1)
			{
				System.out.println(e1);
			}
			return null;
		}
	}


	/**
	 * Purpose - To provide the credentials
	 * @param strPassword - provide password to HttpClient
	 * @param strUserName - provide username to HttpClient
	 * @return HttpClient with credentials
	 */
	public HttpClient provideCredentials(String strUserName,String strPassword) {
		try {
			log.info("providing credentials for authentication");
			log.debug("USERNAME :- " + strUserName + " PASSSWORD :- " + strPassword);
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
					strUserName, strPassword);
			provider.setCredentials(AuthScope.ANY, credentials);
			return HttpClientBuilder.create()
					.setDefaultCredentialsProvider(provider).build();
		} catch (Exception e) {
			log.error("error while providing Credentials for authentication"+UtilityMethods.getStackTrace());
			Assert.fail("error while providing Credentials for authentication"+e.getMessage());
			return null;
		}
	}

	public String queryParams__New(String[][] queryParams) {
		
 		StringBuffer query = new StringBuffer();
	    System.out.println(queryParams.length);
		try{
		if ((null != queryParams)&(queryParams.length!=0))
		{
			for (int i = 0,k=1; i < queryParams.length-1; i++,k++) 
			{
				for(int j=0;j<queryParams.length;j++){

				query.append("&").append(queryParams[i][j]+ "="+queryParams[k][j]);
				}
			}
		} 
		}catch(Exception e)
		{		
			System.out.println(e);
		}
		query.deleteCharAt(0);
		return "?" + query.toString();
	}
	public String queryParamsDataProviders(List<String> col_headres,String[] parameters) {
		StringBuffer query = new StringBuffer();
		//		System.out.println((null == queryParams));
		//		System.out.println((null == queryParams)||(queryParams.length==0));
		//		int j=3;
		try{

			{
				for (int k = 0; k < col_headres.size(); k++) 
				{
					//					String parameter=queryParams[i].replace("parameter_", "");
					query.append("&").append(col_headres.get(k)+ "="+ parameters[k]);
				}
			}
			query.deleteCharAt(0);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return "?" + query.toString();
	}


	/**
	 * Purpose - To execute http request method
	 * @param strUserName - Provide username
	 * @param strPassword - provide password
	 * @param strURL - pass the url
	 * @param req_Method - request method
	 * @param headers - required API headers
	 * @param parameters -  request parameters
	 * @return HashMap<String> - returns status code, status message and body
	 */
	public HashMap<String, String> executeRequest(String strUserName,
			String strPassword, String strURL, List<String> headers,
			String req_Method, boolean isParamsRequired, String parameters,
			String... request_bodyFile_And_Params) {
		HttpClient client = null;
//		HttpResponse response = null;
		HttpGet get = null;
		HttpPost post = null;
		HttpPut put = null;
		HttpDelete delete = null;
		HashMap<String, String> responseMap = new HashMap<String,String>();
		
		String endPoint;
		
		try{
			endPoint=strURL;
			if (strUserName != null || strPassword != null)
				client = provideCredentials(strUserName, PasswordDecorder.passwordDecrypt(strPassword));
			else
				client = getClient();

			if(endPoint.contains("$")){
				endPoint=UtilityMethods.replaceVariablesWithRuntimeProperties(endPoint,"yyyy");
			}
			if(isParamsRequired){
				endPoint=endPoint+parameters.trim();
			}
			switch (req_Method) {
			case "GET":
				get = (HttpGet) getHttpMethod(endPoint, RequestType.GET);
				if(headers!=null){
					addHeader(get, headers);
				}
				response=execute(client, get);
				break;
			case "POST":
				post = (HttpPost) getHttpMethod(endPoint, RequestType.POST);
				if(headers!=null){
					addHeader(post, headers);
				}
				String strBody_post = getRequestBody(request_bodyFile_And_Params);
				// set body
				post.setEntity(new StringEntity(strBody_post));
				response=execute(client, post);
				break;
			case "PUT":
				put = (HttpPut) getHttpMethod(endPoint, RequestType.PUT);
				if(headers!=null){
					addHeader(put, headers);
				}
				String strBody_Put = getRequestBody(request_bodyFile_And_Params);
				// set body
				put.setEntity(new StringEntity(strBody_Put));
				response=execute(client, put);
				break;
			case "DELETE":
				delete = (HttpDelete) getHttpMethod(endPoint, RequestType.DELETE);
				if(headers!=null){
					addHeader(delete, headers);
				}
				response=execute(client, delete);
				break;

			default:
				log.info("Unknown http request method, please check requested method");
				Assert.fail("Unknown http request method, please check requested method");
				break;
			}
		}
		catch(Exception e)
		{
			log.error("Some Exception occured while execute Get request "+e);
			Assert.fail("Some Exception occured while execute Get request "+e.getStackTrace());
		}
		responseMap.put("status code", String.valueOf(response.getStatusLine().getStatusCode()));
		responseMap.put("status message", String.valueOf(response.getStatusLine().getReasonPhrase()));
		responseMap.put("response body",getResponseBody(response));
		return responseMap;
	}

	public HashMap<String, String> executeGetRequestDataProvider(String strUserName,
			String strPassword, String strURL, List<String> headers,
			boolean isParamsRequired,int col_headers_size,List<String> colHeaders,
			String... parameters) {
		HttpClient client = null;
		HttpGet get = null;
		HttpResponse response;
		HashMap<String, String> responseMap = new HashMap<String,String>();
		try{
			if (strUserName != null || strPassword != null)
				client = provideCredentials(strUserName, PasswordDecorder.passwordDecrypt(strPassword));
			else
				client = getClient();

			String URL=UtilityMethods.replaceVariablesWithRuntimeProperties(strURL,"changeValue");
			if(isParamsRequired)
			{
				URL=URL+queryParamsDataProviders(colHeaders,parameters);
			}
			get = (HttpGet) getHttpMethod(URL, RequestType.GET);
			if(headers!=null)
			{
				addHeader(get, headers);
			}
		}
		catch(Exception e)
		{
			log.error("Some Exception occured while execute Get request "+e);
			Assert.fail("Some Exception occured while execute Get request "+e.getStackTrace());
		}
		
		response=execute(client, get);
		responseMap.put("status code", String.valueOf(response.getStatusLine().getStatusCode()));
		responseMap.put("status message", String.valueOf(response.getStatusLine().getReasonPhrase()));
		responseMap.put("response body",getResponseBody(response));
		return responseMap;
	}
	/**
	 * Purpose - To get response headers
	 * @param headers_keys - provide a specific header keys to retrieve respective headers values else return all header keys-values
	 * @return HashMap<String> - returns header key, header value
	 */
	public HashMap<String, String> getResponseHeaders(String ... headers_keys){
		HashMap<String, String> response_Headers = new HashMap<String,String>();
		int i = 0;
		try{
			if(headers_keys!=null&& headers_keys.length>0){
			for ( i = 0; i < headers_keys.length; i++) {
				String current_header=response.getFirstHeader(headers_keys[i]).toString();
				response_Headers.put(current_header.split(":")[0].trim(), current_header.replaceAll(current_header.split(":")[0].trim()+":","".trim()));
				}
			}else{
				 Header headers[] =response.getAllHeaders();
				 for ( i = 0; i < headers.length; i++){
					 response_Headers.put(headers[i].toString().split(":")[0].trim(), headers[i].toString().replaceAll(headers[i].toString().split(":")[0].trim()+":","").trim());
				    }
			     }  
		}
		catch(NullPointerException e){
			log.error("header key "+headers_keys[i]+" is not exist in response headesrs list, Please check key "+e.getStackTrace());
			Assert.fail("header key "+headers_keys[i]+" is not exist in response headesrs list, Please check key "+e.getStackTrace());
			
		}
		catch(Exception e){
			log.error("Some exception is occured, while getting response headers "+e.getStackTrace());
			Assert.fail("Some exception is occured, while getting response headers "+e.getStackTrace());
			
		}
			return response_Headers;
		}
	/**
	 * Purpose - request body before execute 
	 * @param - parameters values change with body parameters 
	 * @return -String  requested body
	 */
	private  String getRequestBody(String ... request_Body_Data) 
	{
		String strLine;
		String strRequestBody ="";
		try{
			String req_Body_File_Location=request_Body_Data[0];
			FileInputStream fstream = new FileInputStream(req_Body_File_Location);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// Read File Line By Line

			while ((strLine = br.readLine()) != null)
			{
				// Print the content on the console
				strRequestBody = strRequestBody + strLine;
			}
			strRequestBody = strRequestBody.trim();
			strRequestBody = updateRequestParams(strRequestBody,request_Body_Data);
			// Close the input stream
			in.close();
		}
		catch(IOException  e)
		{
			log.error(" Exception is occured, while reading body from specified location, please check request body file path "+e.getStackTrace());
			Assert.fail("Exception is occured, while reading body from specified location, please check request body file path "+e.getStackTrace());
		}
		return strRequestBody;
	}
	/**
	 * Purpose - To change/Modify the request body before execute 
	 * @param - String - body of the given API
	 * @param - String - parameter values change with body parameters 
	 * @return - String Updated/requested body
	 */
	private  String updateRequestParams(String strRequestBody,String[] request_Body_Data) 
	{
		JSONManager jsonObj=new JSONManager();
		String root_Node=request_Body_Data[1];
		String addres_Key=request_Body_Data[2];
		String updateValue=request_Body_Data[3];
		List<String> valuee = jsonObj.getValueFromArrayJsonKey(strRequestBody,root_Node,addres_Key);
		String old_Address = valuee.get(0);
		strRequestBody = strRequestBody.replaceAll(old_Address,updateValue);
		return strRequestBody;
	}
	/**
	 * Purpose - To verify status
	 * @param - response response of the API request
	 * @param status -  expected enum status
	 * 
	 */

	public void verifyStatus(String status_Code,String status_Message,Status status) {
		log.info("verifying Status code and message");
		Assert.assertEquals(status_Code, Status.valueOf(status.name()).getAction().split(",")[0], "Status code does not matched");
		Assert.assertEquals(status_Message, Status.valueOf(status.name()).getAction().split(",")[1], "Status message does not matched");

	}
	public void verifyStatusCode(String Actual_Code,String expected_Code) {
		log.info("verifying Status code");
		Assert.assertEquals(Actual_Code, expected_Code, "Status code does not matched");

	}
	public void verifyStatusMessage(String Actual_Message,String expected_Message) {
		log.info("verifying Status message");
		Assert.assertEquals(Actual_Message, expected_Message, "Status messages does not matched");

	}

	/**
	 * Purpose - To get response body 
	 * @param HttpResponse response object
	 * @param  String test Name
	 * @return  String Response body
	 */
	public String getResponseBody(HttpResponse response){
	//	String test_Name=GoogleMapAPIFuntionalities.current_TestName;
		String test_Name = null;
		try{
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			BufferedReader br=new BufferedReader(in);
			StringBuilder sb = new StringBuilder();
			String responseLine;
			while ((responseLine=br.readLine())!=null) 
			{
				sb.append(responseLine + "\n");
			}
			responseBody=sb.toString();
			if(responseBody.length()>0){
				log.info("ResponseBody:"+responseBody);
				writeToFile(responseBody,test_Name);
				String xmlpath = ".." + fileseperator+"Responses"+fileseperator+test_Name+"_Response_at_"+time_Stamp+".json";
				Reporter.log("<a href="+cQuote+xmlpath+cQuote+">"+" Response</a>");
				return responseBody;
			}
			else {
				log.fatal("null Response");
				return null;
			}
		}
		catch(Exception e){
			return null;
		}

	}
	/**
	 * Purpose - To write response body into file
	 * @param responseBody  String response body 
	 * @param - String test Name
	 * @return - String reponseFilePath 
	 */
	private String writeToFile(String responseBody,String test_Name) throws IOException{
		String reponseFilePath = null;
		try
		{
			time_Stamp=UtilityMethods.timeStamp();
			reponseFilePath=System.getProperty("user.dir")+fileseperator+"Automation Reports"+fileseperator+"LatestResults"+fileseperator+"Responses"+fileseperator+test_Name+"_Response_at_"+time_Stamp+".json";
			File file =new File(reponseFilePath);
			if(file.exists())
			{
				file.delete();
			}
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(responseBody);
			writer.flush();
			writer.close();
			log.info("Response body is writeen into "+test_Name+"_Response_at_"+UtilityMethods.timeStamp()+".json");

		}

		catch(Exception e)
		{
			log.error("Exception while writing response body in to file "+UtilityMethods.getStackTrace());
		}
		return reponseFilePath;
	}
	/**
	 * Purpose - To verify given string regex exists in response body
	 * @param response_Body  String Response_body 
	 * @param regex  String regular expression or string
	 * @return boolean  true/false
	 */
	public boolean verifyPatternExistsInResponse(String response_Body,String regex){
		boolean boolValue = false;
		try
		{
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(response_Body);
			boolValue=matcher.find();
			if(!boolValue)
			{
				log.error("The given '"+regex+ "' regular expression not match with the reponse body ");
				Assert.fail("The given  '"+regex+"' regular expression not match with the reponse body");
			}
		}
		catch(Exception e)
		{
			log.error("Some exception is occured while, comparing "+regex +" with response body");
			Assert.fail("Some exception is occured while, comparing "+regex +" with response body");
		}
		return boolValue;
	}
	/**
	 * Purpose - To verify given regular expression count in response body
	 * @param response_Body  String Response_body 
	 * @param regex  String regular expression or string
	 * @return count integer count of string in response body
	 */
	public void verifyPatternCountToBeFromResponse(String response_Body,String regex,int regexCount){
		int regexActual_Count = 0;
		try
		{
			boolean b=verifyPatternExistsInResponse(response_Body, regex);
			if(b){
				Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(response_Body);
				while(matcher.find()) {
					regexActual_Count++;
				}
				if(regexActual_Count!=regexCount)
				{
					log.error("The Given " +regex+" regex count in the response not equal to "+regexCount+", but found "+regexActual_Count);
					Assert.fail("The given "+ regex+" regex count in the response not equal to "+regexCount+", but found "+regexActual_Count);
				}
			}
			else
				Assert.fail("The given '"+regex+"' is not exists in the response body");

		}
		catch(Exception e)
		{
			log.error("Some exception is occured while, counting "+regex +" in response body "+e.getStackTrace());
		}
	}
	/**
	 * Purpose - To get given string in response body
	 * @param response_Body  String Response_body 
	 * @param regex  String regular expression or string
	 * @return String
	 */
	public String verifyTextExistsInResponse(String response_Body,String input_String,String expectedString){
		String input_String_From_Response = null;
		try
		{
			boolean b=verifyPatternExistsInResponse(response_Body, input_String);
			if(b){
				Pattern pattern = Pattern.compile(input_String, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(response_Body);
				matcher.find();
				input_String_From_Response=matcher.group();
				if(!input_String_From_Response.equalsIgnoreCase(expectedString))
				{
					log.error("The given "+input_String+"' is not equal to  '"+expectedString+ "' , but actual found "+input_String_From_Response);
					Assert.fail("The given "+input_String+" is not equal to  '"+expectedString+ "' , but actual found "+input_String_From_Response);
				}
			}
			else
				Assert.fail("The given "+input_String+" is not exist in response body");

		}
		catch(Exception e)
		{
			log.error("Some exception is occured while, counting "+input_String +" in response body "+e.getStackTrace());
		}
		return input_String_From_Response;
	}
	/**
	 * Purpose - To read the API test data from excel file
	 * @param strFileName - provide name of the file
	 * @param strSheetName - provide sheet name
	 * @param intParamsRowNum - pass row number
	 */
	public String readRequestQueryParamsFromExcel(String strFileName, String strSheetName,int excel_Row_num){
		StringBuffer query = new StringBuffer();
		try
		{
			excel=new ExcelManager(strFileName);
			for (int i = 0; i < excel.getColumnCount(strSheetName); i++) {
//				System.out.println(excel.getExcelCellData(strSheetName, i, 1)+"=="+excel.getExcelCellData(strSheetName, i, 2));
				query.append("&").append(excel.getExcelCellData(strSheetName, i, 1)+ "="+excel.getExcelCellData(strSheetName, i, excel_Row_num));
			}
			log.info("Reading request params data from Excel is done successfully");
			
		}
		
		catch(Exception e)
		{
			log.error("Some Exception occured while reading data from Excel "+e.getStackTrace());
			Assert.fail("Some Exception occured while reading data from Excel "+e.getStackTrace());
		}
		query.deleteCharAt(0);
		return "?" + query.toString();
	}
	/**
	 * Purpose - To verify the Google API Response
	 * @param responseBody - pass the HTTP response body
	 * @param keys - pass the keys of response
	 * @param values - pass the expected values of response
	 */
	public void verifyValuesforkeys(String responseBody,String[] keys, String ... values){
		List<String> valuee = jsonObj.getValueFromSimpleJsonKey(responseBody, keys);
		for(int i=0;i<valuee.size();i++){
			Assert.assertEquals(valuee.get(i), values[i], "Mismatch in '"+keys[i]+"' \n");}
	}
	/**
	 * Purpose - To verify the Google API Response
	 * @param responseBody - pass the HTTP response body
	 * @param keys - pass keys of response body
	 * @param test_run_count - pass the value of test runs
	 * @param parentNode - pass the parent node of response
	 * @param values - pass the expected value
	 */
	public void verifyValuesforkeys(String responseBody,String parentNode,int test_run_count,String[] keys,String ... values){
		List<String> valuee = jsonObj.getValueFromArrayJsonKey(responseBody, parentNode,keys);
		for(int i=0;i<valuee.size();i++){
			Assert.assertEquals(valuee.get(i), values[test_run_count], "Mismatch in '"+keys[i]+"' \n");}
	}
	/**
	 * Purpose - To verify the Google API Response
	 * @param responseBody - pass the HTTP response body
	 * @param keys - pass keys of response body
	 * @param parentNode - pass the parent node of response
	 * @param values - pass the expected value
	 */
	public void verifyValuesforkeys(String responseBody,String parentNode,String[] keys,String ... values){
		List<String> valuee = jsonObj.getValueFromArrayJsonKey(responseBody, parentNode,keys);
		for(int i=0;i<valuee.size();i++){
			Assert.assertEquals(valuee.get(i), values[i], "Mismatch in '"+keys[i]+"' \n");}
	}
	public void verifyValuesforkeys(String responseBody,String parentNode,HashMap<String, String> key_value){
		    List<String> valuee = jsonObj.getValueFromArrayJsonKey(responseBody, parentNode,key_value.get("place_id_key"),key_value.get("address_key"));
			Assert.assertEquals(valuee.get(0), key_value.get("place_id_value"), "Mismatch in '"+key_value.get("place_id_key")+"' \n");
			Assert.assertEquals(valuee.get(1), key_value.get("address_value"), "Mismatch in '"+key_value.get("address_key")+"' \n");
	}
	public void verifyValuesforkeys(String responseBody,String parentNode,String key,String expected_value){
	    List<String> valuee = jsonObj.getValueFromArrayJsonKey(responseBody, parentNode,key);
		Assert.assertEquals(valuee.get(0), expected_value, "Mismatch in '"+key+"' \n");
}
	/**
	 * Purpose - To verify the Google API Response
	 * @param responseBody - pass the HTTP response body
	 * @param key - pass the key of response
	 * @param value - pass the expected value of response
	 */
	public void verifyValueforkey(String responseBody,String key,String value){

		List<String> valuee = jsonObj.getValueFromSimpleJsonKey(responseBody, key);
		Assert.assertEquals(valuee.get(0),value, "Mismatch in place_id \n");
	}
	/**
	 * Purpose - To verify the Google API Response
	 * @param responseBody - pass the HTTP response body
	 * @param place_Id - pass key of response body
	 * @param parentNode - pass the parent node of response
	 * @param value - pass the expected value
	 */
	public void verifyValueforkey(String responseBody, String parentNode,String key, String value){
		List<String> valuee = jsonObj.getValueFromArrayJsonKey(responseBody, parentNode,key);
		Assert.assertEquals(valuee.get(0), value, "Mismatch in place_id \n");

	}
	@DataProvider(name="Parameters")
	public Object[][] parametersData() {
	
		excel=new ExcelManager(System.getProperty("user.dir")+app_FileData.getProperty("Api.DataFile"));
		Object[][] arrayObject =excel.getExcelSheetData("API_TestData");
		return arrayObject;
	}
	
	
	
	/**
	 * Purpose - request body before execute 
	 * @param - parameters values change with body parameters 
	 * @return -String  requested body
	 */
	private  String getRequestBody(boolean updateFlag, String ... request_Body_Data) 
	{
		String strLine;
		String strRequestBody ="";
		try{
			String req_Body_File_Location=request_Body_Data[0];
			FileInputStream fstream = new FileInputStream(req_Body_File_Location);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// Read File Line By Line

			while ((strLine = br.readLine()) != null)
			{
				// Print the content on the console
				strRequestBody = strRequestBody + strLine;
			}
			strRequestBody = strRequestBody.trim();
			if(updateFlag)
				strRequestBody = updateRequestParams(strRequestBody,request_Body_Data);
			// Close the input stream
			in.close();
		}
		catch(IOException  e)
		{
			log.error(" Exception is occured, while reading body from specified location, please check request body file path "+e.getStackTrace());
			Assert.fail("Exception is occured, while reading body from specified location, please check request body file path "+e.getStackTrace());
		}
		return strRequestBody;
	}
	
	/**
	 * Purpose - To execute http request method
	 * @param strUserName - Provide username
	 * @param strPassword - provide password
	 * @param strURL - pass the url
	 * @param req_Method - request method
	 * @param headers - required API headers
	 * @param parameters -  request parameters
	 * @return HashMap<String> - returns status code, status message and body
	 */
	public HashMap<String, String> executeRequestMethod(String strUserName, String strPassword, String strURL, 
														List<String> headers, String req_Method, boolean isParamsRequired, String parameters, 
														boolean updateFlag, String test_Name, 
														boolean directJsonContent, String requestBody,
														String... request_bodyFile_And_Params) {
		HttpClient client = null;
//		HttpResponse response = null;
		HttpGet get = null;
		HttpPost post = null;
		HttpPut put = null;
		HttpDelete delete = null;
		HashMap<String, String> responseMap = new HashMap<String,String>();
		
		String endPoint;
		
		try{
			endPoint=strURL;
			if (strUserName != null || strPassword != null)
			//	client = provideCredentials(strUserName, PasswordDecorder.passwordDecrypt(strPassword));
				client = provideCredentials(strUserName, strPassword);
			else
				client = getClient();

			if(endPoint.contains("$")){
				endPoint=UtilityMethods.replaceVariablesWithRuntimeProperties(endPoint,"yyyy");
			}
			if(isParamsRequired){
				endPoint=endPoint+parameters.trim();
			}
			switch (req_Method) {
			case "GET":
				get = (HttpGet) getHttpMethod(endPoint, RequestType.GET);
				if(headers!=null){
					addHeader(get, headers);
				}
				response=execute(client, get);
				break;
			case "POST":
				post = (HttpPost) getHttpMethod(endPoint, RequestType.POST);
				if(headers!=null){
					addHeader(post, headers);
				}
				String strBody_post = getRequestBody(updateFlag, request_bodyFile_And_Params);
				if(directJsonContent)
					strBody_post = requestBody;
				// set body
				post.setEntity(new StringEntity(strBody_post));
				response=execute(client, post);
				break;
			case "PUT":
				put = (HttpPut) getHttpMethod(endPoint, RequestType.PUT);
				if(headers!=null){
					addHeader(put, headers);
				}
				String strBody_Put = getRequestBody(updateFlag, request_bodyFile_And_Params);
				if(directJsonContent)
					strBody_Put = requestBody;
				// set body
				put.setEntity(new StringEntity(strBody_Put));
				response=execute(client, put);
				break;
			case "DELETE":
				delete = (HttpDelete) getHttpMethod(endPoint, RequestType.DELETE);
				if(headers!=null){
					addHeader(delete, headers);
				}
				response=execute(client, delete);
				break;

			default:
				log.info("Unknown http request method, please check requested method");
				Assert.fail("Unknown http request method, please check requested method");
				break;
			}
		}
		catch(Exception e)
		{
			log.error("Some Exception occured while execute Get request "+e);
			Assert.fail("Some Exception occured while execute Get request "+e.getStackTrace());
		}
		Header[] responseHeaders = response.getAllHeaders();
		HashMap<String,String> headerMap = new HashMap<String,String>();
		for (Header header : responseHeaders) {
			if(header.getName().startsWith("pp"))
				headerMap.put(header.getName(), header.getValue());			
		}		
		responseMap.put("status code", String.valueOf(response.getStatusLine().getStatusCode()));
		responseMap.put("status message", String.valueOf(response.getStatusLine().getReasonPhrase()));
		responseMap.put("response body",getResponseBody(response, test_Name));
		responseMap.put("pp-code", headerMap.get("pp-code"));
		responseMap.put("pp-message", headerMap.get("pp-message"));
		return responseMap;
	}
	
	/**
	 * Purpose - To get response body 
	 * @param HttpResponse response object
	 * @param  String test Name
	 * @return  String Response body
	 */
	public String getResponseBody(HttpResponse response, String test_Name){
		
		try{
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			BufferedReader br=new BufferedReader(in);
			StringBuilder sb = new StringBuilder();
			String responseLine;
			while ((responseLine=br.readLine())!=null) 
			{
				sb.append(responseLine + "\n");
			}
			responseBody=sb.toString();
			if(responseBody.length()>0){
				log.info("ResponseBody:"+responseBody);
				writeToFile(responseBody,test_Name);
				String xmlpath = ".." + fileseperator+"Responses"+fileseperator+test_Name+"_Response_at_"+time_Stamp+".json";
				Reporter.log("<a href="+cQuote+xmlpath+cQuote+">"+" Response</a>");
				return responseBody;
			}
			else {
				log.fatal("null Response");
				return null;
			}
		}
		catch(Exception e){
			return null;
		}

	}
	
}