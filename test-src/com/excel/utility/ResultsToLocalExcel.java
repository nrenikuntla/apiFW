package com.excel.utility;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import Exception.FilloException;
import Fillo.Connection;
import Fillo.Fillo;
import Fillo.Recordset;

public class ResultsToLocalExcel 
{

	Fillo fillo;
	public Connection connection;
	String path;
	Logger log = Logger.getLogger("ResultsToLocalExcel");
	public ResultsToLocalExcel(String path1)
	{
		this.path = path1;
		fillo=new Fillo();

		try 
		{
			connection=fillo.getConnection(path);
		} 
		catch (FilloException e) 
		{
			log.info("Connection has some problem");
			e.printStackTrace();
		}
	}

	public void insertresultRow(String sheet,HashMap<String, String> columnsAndValues)
	{
		try
		{
			log.info("Connection success. The file name: "+path);
			String [] rowDetails = generateColumnsString(columnsAndValues);
			String strQuery="INSERT INTO "+sheet+"("+rowDetails[0]+") VALUES("+rowDetails[1]+")";
			log.info("Executing the Query "+strQuery); 
			connection.executeUpdate(strQuery);
			log.info("Row/Rows inserted successfully");
			connection.close();
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


	/**
	 * Create the coloumn values from a HashMap
	 * @param columnsAndValues
	 * @return
	 */
	public  String[] generateColumnsString(HashMap<String, String> columnsAndValues)
	{
		String columnString="";
		String valuesString="";
		int i = 1;
		log.info("The dynamic columns and values string generation is started...");
		for ( String key : columnsAndValues.keySet()) 
		{
			columnString = columnString+key;
			valuesString = valuesString+"'"+columnsAndValues.get(key)+"'";
			if(!(i==columnsAndValues.size()))
			{
				columnString+=",";
				valuesString+=",";
			}
			i++;
		}
		String [] dd = {columnString,valuesString};
		return dd;
	}

	/**
	 * Move Latest To old
	 * @throws FilloException 
	 */
	public void moveLatestToOld(String oldSheet,String latestSheet)
	{
		log.info("Moving Latest results to Old Reports sheet");
		try
		{
			Recordset recordset =connection.executeQuery("Select * from "+latestSheet);
			ArrayList<String> columns = recordset.getFieldNames();
			HashMap<String, String> columnsAndValues = new HashMap<>();
			while (recordset.next())
			{
				for(int c=0;c<columns.size();c++)
				{
					columnsAndValues.put(recordset.getField(c).name(), recordset.getField(recordset.getField(c).name()));
				}
				String[] colDetails = generateColumnsString(columnsAndValues);
				connection.executeUpdate("INSERT INTO "+oldSheet+"("+colDetails[0]+") VALUES("+colDetails[1]+")");
			}
			log.info("Writing the details is completed");
			log.info("Deleting rows from Lates sheet");
			connection.executeUpdate("DELETE from "+latestSheet);
			connection.close();
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
}
