/*************************************** PURPOSE **********************************

 - This class certain database specific methods to create/insert/select/modify/delete result sets of a specific table in MYSQL
 - Update the values of DBURL, JDBCDRIVER, DBNAME, DBUSERNAME, DBPASSWORD based on your configuration and MYSQL access
 - The above data can be modified under Sys.properties file
*/

package com.excel.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.datamanager.ConfigManager;


public class JDBCConn {
	
	private static Logger logger = Logger.getLogger("Sync");
	private static ConfigManager sys = new ConfigManager();
	private static Connection conn = null;  //Create object of Connection object
	
	
	/**
	 * Method - Method to establish connection with DB
	 * @return - Return Statement object with connected DB
	 */
	public static Statement establishconnection() {
		try {
			String url = sys.getProperty("DBURL");
			String dbname = sys.getProperty("DBNAME");
			String userName = sys.getProperty("DBUSERNAME");
			String password = sys.getProperty("DBPASSWORD");
			logger.info("Establishing connection to DB : " + dbname);
			System.out.println(url +"databaseName=" + dbname);
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("Connection Success");
		return conn.createStatement();
		} catch (Exception e) {
			logger.error("error while creating connection", e);
			if (conn != null) {
				try {
					if (!conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			return null;
		}
	}
	
	public static int select(String strSelectQuery){
		ResultSet resulSet = null;
		int runID = 0;
		try{
			logger.info("Executing Select Query : "+strSelectQuery);
			Statement st = establishconnection();
			resulSet = st.executeQuery(strSelectQuery);
			resulSet.next();
			runID = resulSet.getInt("RUN_ID");
		}
		catch (Exception e) { 
			logger.error("Error occured while exeguting select query : "+strSelectQuery+"\n"+e.getMessage());
			resulSet=null;
		}
		finally {
			if (conn != null) {
				try {
					if (!conn.isClosed()) {
						conn.close();
						logger.info("connection closed sucessfully");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return runID;		
	}
	
	
	public static void SaveTestResult(HashMap<String,String> todb) throws SQLException
	 {
	  Statement st = null;
	  String strInsertQuery ="";
	   int tr = 0;
	   if (todb.get("TEST_STATUS") == "PASSED"){
	    tr = 1;
	   }
	   else if(todb.get("TEST_STATUS") == "FAILED"){
	    tr=2;
	   }
	   else if(todb.get("TEST_STATUS") == "SKIPPED"){
	    tr=3;
	   }
	  
	  strInsertQuery = "CALL "+sys.getProperty("DBNAME")+".zqsp_inserttestresult ( '" +
	  todb.get("TESTCASE_TITLE")+"',"+
	    tr+",'"+
	  todb.get("TEST_DATE") +"','" +
	  todb.get("TEST_DATE")  +"','" +
	  todb.get("TEST_BROWSER")  +"','" +
	  todb.get("TEST_MACHINE")  +"','" +
	  todb.get("MODULE_NAME") +"','" +
	  todb.get("ERROR_MESSAGE") +"','" +
	  todb.get("FAIL_STACKTRACE") +"','',"+
	  todb.get("RUN_ID")+");";
	  
	  try
	  {
	   logger.info("Executing INSERT query : "+strInsertQuery);
	   st= establishconnection();   
	   st.execute(strInsertQuery);
	   logger.info("Values inserted into table");	   
	  }	  
	  catch (Exception e) { 
	   e.printStackTrace();
	   logger.error("Error while executing INSERT query : "+strInsertQuery+"\n"+e.getMessage());
	  }	  
	  finally { 
	   try {
	    st.getConnection().close();
	   } catch (SQLException e) {
	    e.printStackTrace();
	   } 
	  }
	 }
	
	/**
	 * Method - Method to insert values into a specific table which already exists under DB
	 * @param- Update INSERT query as needed  
	 */
	public static void insert(String strInsertQuery)
	{
		Statement st = null;

		try
		{
			logger.info("Executing INSERT query : "+strInsertQuery);
			st= establishconnection(); 		
			st.execute(strInsertQuery);
			logger.info("Values inserted into table");			
		}		
		catch (Exception e) { 
			e.printStackTrace();
			logger.error("Error while executing INSERT query : "+strInsertQuery+"\n"+e.getMessage());
		}		
		finally { 
			try {
				st.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}		
	}


	/**
	 * Method - Method to create a table under MYSQL
	 * query - Update the query as needed 
	 * @throws Exception
	 */
	public static void createTable(String strUpdateQuery) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,Exception
	{
		try
		{
			Statement stmt = establishconnection();
			stmt.executeUpdate(strUpdateQuery); 		
		}
		
		catch (Exception e) { 
			e.printStackTrace();
			logger.error("Error under createTable method"+e.getMessage());
		}
		
		finally {
			if(conn!=null)
			{
				conn.close();
				logger.info("connection closed sucessfully");
			}
		} 		
	}
	
	
	/**
	 * Method - Method to insert values into a specific table which already exists under MYSQL
	 * @param sTableName - Table Name
	 * @param sName - existing value in table (change as per table structure)
	 * @param sDate - existing value in table (change as per table structure)
	 * @query - Update the query as needed  
	 * @throws Exception
	 */
	public static void insertTableValues(String sTableName,int PY_bAccount_Type,int PY_bType,String PY_sPayment_Account,int PY_lAccount_Code) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,Exception
	{

		try
		{
			String url = sys.getProperty("DBURL");
			String driver = sys.getProperty("JDBCDRIVER"); //different for Oracle or other databases
			String dbname = sys.getProperty("DBNAME"); 
			String userName= sys.getProperty("DBUSERNAME"); 
			String password = sys.getProperty("DBPASSWORD");
			String sQuery = "insert into py_payment values(?,?,?,?)";
			
			Class.forName(driver).newInstance(); //create object of Driver class 
			conn=DriverManager.getConnection(url+dbname,userName,password); //connection will be established from this line 	
			
			//********Prepared statement**insert********** 
			//Prepared Statement is used for sql statement with conditional statements 
			PreparedStatement pstmt1= conn.prepareStatement(sQuery); 		
			pstmt1.setInt(1, PY_bAccount_Type); 
			pstmt1.setInt(2, PY_bType); 
			pstmt1.setString(3,PY_sPayment_Account); 
			pstmt1.setInt(4, PY_lAccount_Code); 
			pstmt1.executeUpdate(); 
			logger.info("Values inserted into table");
			
		}
		
		catch (Exception e) { 
			e.printStackTrace();
			logger.error("Error under insertData method"+e.getMessage());
		}
		
		finally { 
			conn.close(); 
		} 
		
	}
	
	/**
	 * Method - Method to select data based on a condition under MYSQL
	 * @param sTableName - Table Name
	 * @param sValue - desired value to be displayed (change as per table structure)
	 * @param sKey - existing key in table (change as per table structure)
	 * @param sReqValue - enter the required value (change as per table structure)
	 * @query - Update the query as needed 
	 * @return String - row value is retrieved based on key and its corresponding value entered
	 * @throws Exception
	 */
	public static String selectData(String sTableName,String sReqKey,String Condition1, String Condition1Val,String Condition2, String Condition2Val,String Condition3, String Condition3Val) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,Exception
	{
		try
		{
			String url = sys.getProperty("DBURL");
			String driver = sys.getProperty("JDBCDRIVER"); //different for Oracle or other databases
			String dbname = sys.getProperty("DBNAME"); 
			String userName= sys.getProperty("DBUSERNAME"); 
			String password = sys.getProperty("DBPASSWORD");
			String result = null;

			String sQuery = "select "+ sReqKey +"  From "+ sTableName +" where "+Condition1+" = ?  and "+Condition2+" = ? and "+Condition3+" = ?";
			
			Class.forName(driver).newInstance(); //create object of Driver class 
			conn=DriverManager.getConnection(url+dbname,userName,password); //connection will be established from this line 			
			
			//********Prepared statement**select********** 
			//Prepared Statement is used for sql statement with conditional statements 
			PreparedStatement pstmt= conn.prepareStatement(sQuery); 
			pstmt.setString(1, Condition1Val);
			pstmt.setString(2, Condition2Val);
			pstmt.setString(3, Condition3Val);
			
			ResultSet rs1=pstmt.executeQuery(); 
			while(rs1.next()){ 
				System.out.println(rs1.getString(1)); 
				result = rs1.getString(1);
			} 		
			logger.info("Account ID Value: "+ result);
			return result;	
				
		}
		
		catch (Exception e) { 			
			e.printStackTrace();
			logger.error("Error under selectData method - Query did not fetch the result due to: "+e.getMessage());			
		}
		
		finally { 
			conn.close(); 
		}
		return "";		
	}
	
	/** Method - Method to select data from table with 1 condition under MYSQL*/
	public static String selectData(String sTableName,String sReqKey,String Condition1, String Condition1Val) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,Exception
	{
		try
		{
			String url = sys.getProperty("DBURL");
			String driver = sys.getProperty("JDBCDRIVER"); //different for Oracle or other databases
			String dbname = sys.getProperty("DBNAME"); 
			String userName= sys.getProperty("DBUSERNAME"); 
			String password = sys.getProperty("DBPASSWORD");
			String result = null;

			String sQuery = "select "+ sReqKey +"  From "+ sTableName +" where "+Condition1+" = ?";
			
			Class.forName(driver).newInstance(); //create object of Driver class 
			conn=DriverManager.getConnection(url+dbname,userName,password); //connection will be established from this line 			
			
			//********Prepared statement**select********** 
			//Prepared Statement is used for sql statement with conditional statements 
			PreparedStatement pstmt= conn.prepareStatement(sQuery); 
			pstmt.setString(1, Condition1Val);
			
			ResultSet rs1=pstmt.executeQuery(); 
			while(rs1.next()){ 
				System.out.println(rs1.getString(1)); 
				result = rs1.getString(1);
			} 		
			logger.info("Account ID Value: "+ result);
			return result;	
				
		}
		
		catch (Exception e) { 			
			e.printStackTrace();
			logger.error("Error under selectData method - Query did not fetch the result due to: "+e.getMessage());			
		}
		
		finally { 
			conn.close(); 
		}
		return "";		
	}
	
	/** Method - Method to select data from table with 2 conditions under MYSQL*/
	public static String selectData(String sTableName,String sReqKey,String Condition1, String Condition1Val,String Condition2, String Condition2Val) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,Exception
	{
		try
		{
			String url = sys.getProperty("DBURL");
			String driver = sys.getProperty("JDBCDRIVER"); //different for Oracle or other databases
			String dbname = sys.getProperty("DBNAME"); 
			String userName= sys.getProperty("DBUSERNAME"); 
			String password = sys.getProperty("DBPASSWORD");
			String result = null;

			String sQuery = "select "+ sReqKey +"  From "+ sTableName +" where "+Condition1+" = ?  and "+Condition2+" = ?";
			
			Class.forName(driver).newInstance(); //create object of Driver class 
			conn=DriverManager.getConnection(url+dbname,userName,password); //connection will be established from this line 			
			
			//********Prepared statement**select********** 
			//Prepared Statement is used for sql statement with conditional statements 
			PreparedStatement pstmt= conn.prepareStatement(sQuery); 
			pstmt.setString(1, Condition1Val);
			pstmt.setString(2, Condition2Val);
			
			ResultSet rs1=pstmt.executeQuery(); 
			while(rs1.next()){ 
				System.out.println(rs1.getString(1)); 
				result = rs1.getString(1);
			} 		
			logger.info("Account ID Value: "+ result);
			return result;	
				
		}
		
		catch (Exception e) { 			
			e.printStackTrace();
			logger.error("Error under selectData method - Query did not fetch the result due to: "+e.getMessage());			
		}
		
		finally { 
			conn.close(); 
		}
		return "";		
	}
	
	
	
	/**
	 * Method - Method to view table data under MYSQL
	 * @param sTableName - Table Name
	 * @query - Update the query as needed 
	 * @return String - row value is retrieved based on key and its corresponding value entered
	 * @throws Exception
	 */
	public static void viewTableData(String sTableName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,Exception
	{
		try
		{
			String url = sys.getProperty("DBURL");
			String driver = sys.getProperty("JDBCDRIVER"); //different for Oracle or other databases
			String dbname = sys.getProperty("DBNAME"); 
			String userName= sys.getProperty("DBUSERNAME"); 
			String password = sys.getProperty("DBPASSWORD");
			String sQuery = "select * from +sTableName+";
			
			Class.forName(driver).newInstance(); //create object of Driver class 
			conn=DriverManager.getConnection(url+dbname,userName,password); //connection will be established from this line 			
			
			//***********Statement***********Without condition Select Statement********* 
			Statement stmt = conn.createStatement();    
			ResultSet rs = stmt.executeQuery(sQuery); 
			while(rs.next()){ 
				System.out.println(rs.getString(1) + " " + rs.getString(2)); 
			} 
			
		}
		
		catch (Exception e) { 
			e.printStackTrace();
			logger.error("Error under viewTableData method"+e.getMessage());
		}
		
		finally { 
			conn.close(); 
		} 
		
	}	
	
	/**
	 * Method - Method to delete a specific row under table based on certain condition under MYSQL
	 * @param sTableName - Table Name
	 * @param sKey - existing key in table (change as per table structure)
	 * @param SValue - enter the required value (change as per table structure)
	 * @query - Update the query as needed 
	 * @return String - row value is retrieved based on key and its corresponding value entered
	 * @throws Exception
	 */
	public static void deleteSpecificRow(String sTableName, String sKey,String SValue) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,Exception
	{
		try
		{
			String url = sys.getProperty("DBURL");
			String driver = sys.getProperty("JDBCDRIVER"); //different for Oracle or other databases
			String dbname = sys.getProperty("DBNAME"); 
			String userName= sys.getProperty("DBUSERNAME"); 
			String password = sys.getProperty("DBPASSWORD");
			String sQuery = "DELETE from +sTableName+ where +sKey+ = +'SValue'+";
			
			Class.forName(driver).newInstance(); //create object of Driver class 
			conn=DriverManager.getConnection(url+dbname,userName,password); //connection will be established from this line 			
			
			//***********Statement***********Without condition Select Statement********* 
			Statement stmt = conn.createStatement();    
			ResultSet rs = stmt.executeQuery(sQuery); 
			while(rs.next()){ 
				System.out.println(rs.getString(1) + " " + rs.getString(2)); 
			} 
			
		}
		
		catch (Exception e) { 
			e.printStackTrace();
			logger.error("Error under deleteSpecificRow method"+e.getMessage());
		}
		
		finally { 
			conn.close(); 
		} 
		
	}	

}

