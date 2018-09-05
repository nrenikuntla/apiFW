package com.testsuite.dataprovider;

import org.testng.annotations.DataProvider;

import com.datamanager.ExcelManager;

public class WorkFlows_TestData_Provider {
	
	public static String fileSeparator = System.getProperty("file.separator");
	
	@DataProvider(name = "ProductSearch_getRelatedProducts_TestData")
	public static String[][] ProductSearch_getRelatedProducts_TestData(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "WorkFlows" + fileSeparator + "ProductSearch_GetRelatedProducts" + fileSeparator + "TC001_TestData.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_CreateAndViewCart")
	public static String[][] TC001_CreateAndViewCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "WorkFlows" + fileSeparator + "CreateShoppingCart_ViewCart" + fileSeparator + "TC001_CreateAndViewCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC002_CreateAndViewCart")
	public static String[][] TC002_CreateAndViewCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "WorkFlows" + fileSeparator + "CreateShoppingCart_ViewCart" + fileSeparator + "TC002_CreateAndViewCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}

}
