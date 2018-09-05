package com.datamanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.utilities.UtilityMethods;

public class JSONManager
{
	File jFile;
	JSONParser parser = new JSONParser();
	private static Logger log = Logger.getLogger("Verify");
	String requestBody;
	JSONObject object;
	JSONArray jsonArray;
	
	public void readJsonContent(String pnode, String key, String filePath){
		try {
			jFile = new File(filePath);
			JSONObject root_Object = (JSONObject) parser.parse(new FileReader(jFile));
			
			JSONObject parent = (JSONObject) root_Object.get(pnode);
			System.out.println(parent.get(key));
		}
		catch (IOException | ParseException e) {
			log.error("Exception while reading key - "+key+" from JSON file - "+ filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception while reading key - "+key+" from JSON file - "+ filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	public List<String> getValueFromSimpleJsonKey(String response, String... array_Key) {
//		String text = null;
		List<String> text = new ArrayList<>();
		try {
				String JsonString = response.toString();
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(JsonString);
				for(int i=0;i<=array_Key.length-1;i++)
				text.add(object.get(array_Key[i]).toString());
		  }
		catch (Exception e) {
			log.error("Exception while reading key - " + array_Key
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ array_Key + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}
	
	public List<String> getValueFromArrayJsonKey(String response,String pNode, String ... key_List) {
		List<String> text = new ArrayList<>();
		try {
//            String keys[]=key_List.split(",") ;  
			String JsonString = response.toString();
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(JsonString);
			
				JSONArray array_Nodes = (JSONArray) object.get(pNode);
				Iterator<JSONObject> i = array_Nodes.iterator();
				while (i.hasNext())
				{
					int j = 0;
					JSONObject innerObject = i.next();
					for(int k=0;k<=key_List.length-1;k++)
					text.add((String) innerObject.get(key_List[k]));
//					text.add((String) innerObject.get(keys[j+1]));
					
				}
			
		} catch (Exception e) {
			log.error("Exception while reading key - " + pNode
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ pNode + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}
	
	public List<Object> getValueAsObjectFromArrayJsonKey(String response,String pNode, String ... key_List) {
		List<Object> objectHolder = new ArrayList<>();
		try {
			String JsonString = response.toString();
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(JsonString);
			
			JSONArray array_Nodes = (JSONArray) object.get(pNode);
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> i = array_Nodes.iterator();
			while (i.hasNext())
			{
				JSONObject innerObject = i.next();
				for(int k=0;k<=key_List.length-1;k++)
					objectHolder.add((Object) innerObject.get(key_List[k]));
			}
			
		} catch (Exception e) {
			log.error("Exception while reading key - " + pNode
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */
			Assert.fail("Exception while reading key - "
					+ pNode + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return objectHolder;
	}
	
	
	public HashMap<String,String> getValuesFromInnerJsonKey(String response,String pNode, String ... key_List) {
	  List<String> text = new ArrayList<>();
	  HashMap<String,String> innerJson = null;
	  try {
		   JSONArray jsonArray = null;
		   
		   String JsonString = response.toString();
		   JSONParser parser = new JSONParser();
		   JSONObject object = (JSONObject) parser.parse(JsonString);
	   
		   JSONArray array_Nodes = (JSONArray) object.get(pNode);
		   Iterator<JSONObject> i = array_Nodes.iterator();
		   while (i.hasNext())
		   {
			 JSONObject innerObject = i.next();
		     String strVal=innerObject.get(key_List[0]).toString();
		     text.add(strVal);
		   }
		   
		   innerJson = new HashMap<String,String>();
		   
		   int k=1;
		   for (int j = 0; j < text.size(); j++) {
		    	jsonArray= (JSONArray) parser.parse(text.get(j));
		    	Iterator<JSONObject> i2 = jsonArray.iterator();
		    
			    while (i2.hasNext())
			    {
			    	 JSONObject innerObject = i2.next();
				     String strVal=innerObject.get(key_List[1]).toString();
				     innerJson.put(key_List[1]+"_"+k,strVal);
				     String strVal1=innerObject.get(key_List[2]).toString();
				     innerJson.put(key_List[2]+"_"+k,strVal1);
				     k++;
			    }
		   }
	   
	  	} catch (Exception e) {
	  		log.error("Exception while reading key - " + pNode
		     + " from JSON - " + e.getMessage()
		     + UtilityMethods.getStackTrace());
		   // Custom 
		   Assert.fail("Exception while reading key - "
		     + pNode + " from JSON  - " + e.getMessage()
		     + UtilityMethods.getStackTrace());
	  	}
	  return innerJson;
	 }

	
	public List<ArrayList<String>> getValueFromArrayJsonKey2(String response,String pNode, String ... key_List) {
		List<ArrayList<String>> text = new ArrayList<>();
		try {
//            String keys[]=key_List.split(",") ;  
			String JsonString = response.toString();
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(JsonString);
			
				JSONArray array_Nodes = (JSONArray) object.get(pNode);
				Iterator<JSONObject> i = array_Nodes.iterator();
				while (i.hasNext())
				{
					int j = 0;
					JSONObject innerObject = i.next();
					for(int k=0;k<=key_List.length-1;k++)
					text.add((ArrayList<String>) innerObject.get(key_List[k]));
				}
			
		} catch (Exception e) {
			log.error("Exception while reading key - " + pNode
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ pNode + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}
	
	public List<Double> getValueFromArrayJsonKey3(String response,String pNode, String ... key_List) {
		List<Double> text = new ArrayList<>();
		try {
//            String keys[]=key_List.split(",") ;  
			String JsonString = response.toString();
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(JsonString);
			
				JSONArray array_Nodes = (JSONArray) object.get(pNode);
				Iterator<JSONObject> i = array_Nodes.iterator();
				while (i.hasNext())
				{
					int j = 0;
					JSONObject innerObject = i.next();
					for(int k=0;k<=key_List.length-1;k++)
					text.add((Double)innerObject.get(key_List[k]));
				}
			
		} catch (Exception e) {
			log.error("Exception while reading key - " + pNode
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ pNode + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}
	
//	public List<String> getValueFromArrayJsonKey(String response,String pNode, String key_List) {
//		List<String> text = new ArrayList<>();
//		try {
//            String keys[]=key_List.split(",") ;  
//			String JsonString = response.toString();
//			JSONParser parser = new JSONParser();
//			JSONObject object = (JSONObject) parser.parse(JsonString);
//			
//				JSONArray array_Nodes = (JSONArray) object.get(pNode);
//				Iterator<JSONObject> i = array_Nodes.iterator();
//				while (i.hasNext())
//				{
//					int j = 0;
//					JSONObject innerObject = i.next();
//					for(int k=0;k<=keys.length-1;k++)
//					text.add((String) innerObject.get(keys[k]));
////					text.add((String) innerObject.get(keys[j+1]));
//					
//				}
//			
//		} catch (Exception e) {
//			log.error("Exception while reading key - " + pNode
//					+ " from JSON - " + e.getMessage()
//					+ UtilityMethods.getStackTrace());
//			/* Custom */Assert.fail("Exception while reading key - "
//					+ pNode + " from JSON  - " + e.getMessage()
//					+ UtilityMethods.getStackTrace());
//		}
//		return text;
//	}
	@SuppressWarnings("unchecked")
	public void writeJsonContent(String pnode, String key, String value, String filePath){
		try 
		{
			jFile = new File(filePath);
			JSONObject root_Object = (JSONObject) parser.parse(new FileReader(jFile));
		
			JSONObject parent = (JSONObject) root_Object.get(pnode);
			parent.put(key, value);
			updateFile(root_Object, filePath);
		}
		catch (IOException | ParseException e) {
			log.error("Exception while writing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception while writing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	@SuppressWarnings("unchecked")
	public void writeSimpleJsonContent(String key, String value, String filePath){
		try 
		{
			jFile = new File(filePath);
			JSONObject root_Object = (JSONObject) parser.parse(new FileReader(jFile));
		
			JSONObject parent = (JSONObject) root_Object.get("");
			parent.put(key, value);
			updateFile(root_Object, filePath);
		}
		catch (IOException | ParseException e) {
			log.error("Exception while writing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception while writing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	public void removeJsonContent(String pnode, String key, String filePath){
		try {
			jFile = new File(filePath);
			JSONObject root_Object = (JSONObject) parser.parse(new FileReader(jFile));
		
			JSONObject parent = (JSONObject) root_Object.get(pnode);
			parent.remove(key);
			updateFile(root_Object, filePath);
		}
		catch (IOException | ParseException e) {
			log.error("Exception while removing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception while removing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	
	public void updateFile(JSONObject root_Object, String filePath){
		FileWriter file;
		try {
			file = new FileWriter(filePath);
			file.write(root_Object.toJSONString());
			file.flush();
			file.close();
		}
		catch (IOException e) {
			log.error("Exception accessing JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception accessing JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	
	
	
	public Map<String, ArrayList<String>> getValueFromArrayJsonKey1(String response,String pNode, String ... key_List) {
		
	//	List<Arrays> abc = null;
		Map<String, ArrayList<String>> text = new HashMap<String, ArrayList<String>>();
		try {
//            String keys[]=key_List.split(",") ;  
			String JsonString = response.toString();
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(JsonString);
			
				JSONArray array_Nodes = (JSONArray) object.get(pNode);
				Iterator<JSONObject> i = array_Nodes.iterator();
				while (i.hasNext())
				{
					int j = 0;
					JSONObject innerObject = i.next();
					for(int k=0;k<=key_List.length-1;k++){
						text.put("FormattedAddress"+(k+1), (ArrayList<String>) innerObject.get(key_List[k]));	
						
					//	text.add((String) innerObject.get(key_List[k]));
				//	text.add((String) innerObject.get(keys[j+1]));
					}
				}
			
		} catch (Exception e) {
			log.error("Exception while reading key - " + pNode
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ pNode + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}
	
	public List<ArrayList<String>> getListOfValuesFromArrayJsonKey(String response,String pNode, String ... key_List) {
		List<ArrayList<String>> text = new ArrayList<>();
		try {
//            String keys[]=key_List.split(",") ;  
			String JsonString = response.toString();
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(JsonString);
			
				JSONArray array_Nodes = (JSONArray) object.get(pNode);
				Iterator<JSONObject> i = array_Nodes.iterator();
				while (i.hasNext())
				{
					int j = 0;
					JSONObject innerObject = i.next();
					for(int k=0;k<=key_List.length-1;k++)
					text.add((ArrayList<String>) innerObject.get(key_List[k]));
//					text.add((String) innerObject.get(keys[j+1]));
					
				}
			
		} catch (Exception e) {
			log.error("Exception while reading key - " + pNode
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ pNode + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}
	
	public List<String> getValueFromArrayJsonKeyWithoutParentNode(String response, String ... key_List) {
		List<String> text = new ArrayList<>();
		try {
//            String keys[]=key_List.split(",") ;  
			String JsonString = response.toString();
			JSONParser parser = new JSONParser();
			JSONArray arrayObject = (JSONArray) parser.parse(JsonString);
			Iterator<JSONObject> i = arrayObject.iterator();
			JSONObject innerObject;
			while (i.hasNext()){
				int j = 0;
				innerObject = i.next();
				for(int k=0;k<=key_List.length-1;k++){
					text.add((String) innerObject.get(key_List[k]));
				}
			}
			
		} catch (Exception e) {
			log.error("Exception while reading JSON - " + e.getMessage() + UtilityMethods.getStackTrace());
			Assert.fail("Exception while reading JSON  - " + e.getMessage() + UtilityMethods.getStackTrace());
		}
		return text;
	}
	
	public List<ArrayList<String>> getListOfValuesFromSimpleJsonKey(String requestJSON, String... array_Key) {
//		String text = null;
		List<ArrayList<String>> text = new ArrayList<>();
		try {	
			//	String JsonString = requestJSON.toString();
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(new FileReader(requestJSON));
				for(int i=0;i<=array_Key.length-1;i++)
					text.add((ArrayList<String>) object.get(array_Key[i]));
		  }
		catch (Exception e) {
			log.error("Exception while reading key - " + array_Key
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ array_Key + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}
	
	
	public String readJSONContentToString(String filePath){
		
		try{
			InputStreamReader in = new InputStreamReader(new FileInputStream(filePath));
			BufferedReader br=new BufferedReader(in);
			StringBuilder sb = new StringBuilder();
			String requestLine;
			while ((requestLine=br.readLine())!=null) {
				sb.append(requestLine + "\n");
			}
			requestBody=sb.toString();
			
			System.out.println("----------- Request body is -----------");
			System.out.println(requestBody);
			
		}catch(Exception e){
			return null;
		}
		
		return requestBody;
		
	}
	
	public String updateListOfValuesInArrayJsonKey(String request, String key, String... value) {
		
		List<ArrayList<String>> text = new ArrayList<>();
		try {
//            String keys[]=key_List.split(",") ;  
			String JsonString = request.toString();
			object = (JSONObject) parser.parse(JsonString);
			jsonArray = (JSONArray) object.get(key);
			
			jsonArray.clear();
			if(value != null){
				for(int i = 0; i < value.length; i++)
					jsonArray.add(value[i].trim());
			}
				
			System.out.println("==============After Updation===========\n" + object.toJSONString());
			
		} catch (Exception e) {
			log.error("Exception while reading key - in JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			 Assert.fail("Exception while updating key - in JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return object.toJSONString();
	}
	
	public String updateListOfValuesInSimpleJsonKey(String request, String key, String value) {
		
		try {	String JsonString = request.toString();
				object = (JSONObject) parser.parse(JsonString);
				object.put(key,value);
				
				System.out.println("==============After Updation===========\n" + object.toJSONString());
			
		} catch (Exception e) {
			log.error("Exception while reading key - in JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			 Assert.fail("Exception while updating key - in JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return object.toJSONString();
	}
	
	public String updateJSONValueInJsonKey(String request, String key, String value) {
		  
		  try { 
			  	String JsonString = request.toString();
			    object = (JSONObject) parser.parse(JsonString);
			    JSONObject valueObj = (JSONObject) parser.parse(value);
			    object.put(key,valueObj);
			    
			    System.out.println("==============After Updation===========\n" + object.toJSONString());
		   
		  } catch (Exception e) {
		   log.error("Exception while reading key - in JSON - " + e.getMessage()
		     + UtilityMethods.getStackTrace());
		    Assert.fail("Exception while updating key - in JSON  - " + e.getMessage()
		     + UtilityMethods.getStackTrace());
		  }
		  return object.toJSONString();
	 }
	
	public String updateJSONValueInJsonKeys(String request, String key, JSONArray value) {
		  
		  try { 
			 String JsonString = request.toString();
			 String obj=request;
			    object = (JSONObject) parser.parse(JsonString);    
			 
			    //JSONObject valueObj = (JSONObject) parser.parse(value);
			    object.put(key,value);
			    //object=bookingoptions;
			    
			    System.out.println("==============After Updation===========\n" + object.toJSONString());
		   
		  } catch (Exception e) {
		   log.error("Exception while reading key - in JSON - " + e.getMessage()
		     + UtilityMethods.getStackTrace());
		    Assert.fail("Exception while updating key - in JSON  - " + e.getMessage()
		     + UtilityMethods.getStackTrace());
		  }
		  return object.toJSONString();
	 }
	public List<ArrayList<String>> getListOfValuesFromSimpleJsonKey1(String response, String key) {
//		String text = null;
		List<ArrayList<String>> text = new ArrayList<>();
		try {	
				String JsonString = response.toString();
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(JsonString);
								
				text.add((ArrayList<String>) object.get(key));
		  }
		catch (Exception e) {
			log.error("Exception while reading key - " + key + " from JSON - " + e.getMessage()	+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - " + key + " from JSON  - " + e.getMessage() + UtilityMethods.getStackTrace());
		}
		return text;
	}
	public String updateListOfValuesInSimpleJsonKeys(JSONArray request, String key, String value) {
		
		try {	
		JSONArray JsonString = request;
		
		JSONObject valueobject=(JSONObject) JsonString.get(0);
		valueobject.put(key, value);
	object=valueobject;
		
		 System.out.println("==============After Updation===========\n" + object.toJSONString());
			   
		  } catch (Exception e) {
		   log.error("Exception while reading key - in JSON - " + e.getMessage()
		     + UtilityMethods.getStackTrace());
		    Assert.fail("Exception while updating key - in JSON  - " + e.getMessage()
		     + UtilityMethods.getStackTrace());
		  }
		  return object.toJSONString();
		
		
		
	}
	
}
