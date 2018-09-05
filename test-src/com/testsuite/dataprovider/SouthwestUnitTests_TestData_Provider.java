package com.testsuite.dataprovider;

import org.testng.annotations.DataProvider;

import com.datamanager.ExcelManager;

public class SouthwestUnitTests_TestData_Provider {
	
	public static String fileSeparator = System.getProperty("file.separator");
	
	
	//******************************************************** Search ****************************************************
	
		@DataProvider(name = "tc001_searchProducts")
		public static String[][] tc001_searchProducts(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC001_SearchProducts.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc002_searchProductsByCategory")
		public static String[][] tc002_searchProductsByCategory(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC002_SearchProductsByCategory.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc003_searchProductsByDuration")
		public static String[][] tc003_searchProductsByDuration(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC003_SearchProductsByDuration.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc004_searchProductsByAddress")
		public static String[][] tc004_searchProductsByAddress(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC004_SearchProductsByAddress.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc005_searchProductsByAddress")
		public static String[][] tc005_searchProductsByAddress(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC005_SearchProductsByAddress.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc006_queryProductsByPrice")
		public static String[][] tc006_queryProductsByPrice(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC006_QueryProductsByPrice.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc007_queryProductsByRating")
		public static String[][] tc007_queryProductsByRating(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC007_QueryProductsByRating.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc008_queryProductsByReviews")
		public static String[][] tc008_queryProductsByReviews(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC008_QueryProductsByReviews.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc009_queryProductsByTriedandTrue")
		public static String[][] tc009_queryProductsByTriedandTrue(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC009_QueryProductsByTriedandTrue.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc010_searchProductsByWebId")
		public static String[][] tc010_searchProductsByWebId(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC010_SearchProductsByWebId.xlsx");
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc011_searchProductsByLatLong")
		public static String[][] tc011_searchProductsByLatLong(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC011_SearchProductsByLatLong.xlsx");
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc012_sortProductsByPriceLowHigh")
		public static String[][] tc012_sortProductsByPriceLowHigh(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC012_SortProductsByPriceLowHigh.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc013_sortProductsByPriceHighLow")
		public static String[][] tc013_sortProductsByPriceHighLow(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC013_SortProductsByPriceHighLow.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc014_sortProductsByPopularity")
		public static String[][] tc014_sortProductsByPopularity(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC014_SortProductsByPopularity.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc015_sortProductsByRecommended")
		public static String[][] tc015_sortProductsByRecommended(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC015_SortProductsByRecommended.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc016_sortProductsByTopRated")
		public static String[][] tc016_sortProductsByTopRated(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC016_SortProductsByTopRated.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc017_sortProductsByPriceLowHighFilteredByPriceRange")
		public static String[][] tc017_sortProductsByPriceLowHighFilteredByPriceRange(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC017_SortProductsByPriceLowHighFilteredByPriceRange.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc018_sortProductsByPriceHighLowForcedHitsPerPage")
		public static String[][] tc018_sortProductsByPriceHighLowForcedHitsPerPage(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC018_SortProductsByPriceHighLowForcedHitsPerPage.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc019_sortProductsByPriceHighLowFilteredByLatLong")
		public static String[][] tc019_sortProductsByPriceHighLowFilteredByLatLong(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC019_SortProductsByPriceHighLowFilteredByLatLong.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "tc020_sortProductsByPriceHighLowFilteredByWebId")
		public static String[][] tc020_sortProductsByPriceHighLowFilteredByWebId(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC020_SortProductsByPriceHighLowFilteredByWebId.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		
		@DataProvider(name = "tc025_searchProductsByProductId")
		public static String[][] tc025_searchProductsByProductId(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC025_SearchProductsByProductId.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
	
	
		//******************************************************** Booking Service ****************************************************
		
		@DataProvider(name = "TC001_CreateShoppingCart")
		public static String[][] TC001_CreateShoppingCart(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC001_CreateShoppingCart.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC001_AddBookingOptionsToCart")
		 public static String[][] TC001_AddBookingOptionsToCart(){
		  
		  ExcelManager excel = null;
		  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC001_AddBookingOptionsToCart.xlsx");
		  
		  return excel.getExcelSheetData("Sheet1");
		  
		 }
		
		@DataProvider(name = "TC001_GetBookingQuestions")
		public static String[][] TC001_GetBookingQuestions(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "GetBookingQuestions" + fileSeparator + "TC001_GetBookingQuestions.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		@DataProvider(name = "TC001_UpdateBookerDetails")
		public static String[][] TC001_UpdateBookerDetails(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC001_UpdateBookerDetails.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC001_UpdateTravelerDetails")
		public static String[][] TC001_UpdateTravelerDetails(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC001_UpdateTravelerDetails.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC001_ProcessPaymentAndCreateBooking")
		public static String[][] TC001_ProcessPaymentAndCreateBooking(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC001_ProcessPaymentAndCreateBooking.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC001_ValidateShoppingCart")
		public static String[][] TC001_ValidateShoppingCart(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "ValidateShoppingCart" + fileSeparator + "TC001_ValidateShoppingCart.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "TC001_ViewBookingsHistory")
		public static String[][] TC001_ViewBookingsHistory(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "ViewBookingsHistory" + fileSeparator + "TC001_ViewBookingsHistory.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC001_GetSingleBooking")
		public static String[][] TC001_GetSingleBooking(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "GetSingleBooking" + fileSeparator + "TC001_GetSingleBooking.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC001_RequestCancelBooking")
		public static String[][] TC001_RequestCancelBooking(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "RequestCancelBooking" + fileSeparator + "TC001_RequestCancelBooking.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC001_GetVoucherDetails")
		public static String[][] TC001_GetVoucherDetails(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "GetVoucherDetails" + fileSeparator + "TC001_GetVoucherDetails.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC001_RemoveItemsFromShoppingCart")
		public static String[][] TC001_RemoveItemsFromShoppingCart(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestBooking" + fileSeparator + "RemoveItemsFromShoppingCart" + fileSeparator + "TC001_RemoveItemsFromShoppingCart.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}	
}
		//******************************************************** Products ****************************************************
		
		@DataProvider(name = "TC001_getProductAvailability")
		public static String[][] TC001_getProductAvailability(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator+"SouthWest"+fileSeparator +"Products" + fileSeparator + "GetProductAvailability" + fileSeparator + "TC001_GetProductAvailability.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		
		@DataProvider(name = "TC001_getProductOptions")
		public static String[][] TC001_getProductOptions(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator +"SouthWest"+fileSeparator+ "Products" + fileSeparator + "GetProductOptions" + fileSeparator + "TC001_GetProductOptions.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}

		@DataProvider(name = "TC002_getProductOptions")
		public static String[][] TC002_getProductOptions(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator+"SouthWest"+fileSeparator + "Products" + fileSeparator + "GetProductOptions" + fileSeparator + "TC002_GetProductOptions.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "TC001_getProductReviews")
		public static String[][] TC001_getProductReviews(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator +"SouthWest"+fileSeparator+ "Products" + fileSeparator + "GetProductReviews" + fileSeparator + "TC001_GetProductReviews.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "TC001_getProductsDetails")
		public static String[][] TC001_getProductsDetails(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator+"SouthWest"+fileSeparator + "Products" + fileSeparator + "GetProductsDetails" + fileSeparator + "TC001_GetProductDetails.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		

		@DataProvider(name = "TC001_getHotelDetails")
		public static String[][] TC001_getHotelDetails(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator+"SouthWest"+fileSeparator + "Products" + fileSeparator + "GetHotelDetails" + fileSeparator + "TC001_GetHotelDetails.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		@DataProvider(name = "TC001_getPriceMatch")
		public static String[][] TC001_getPriceMatch(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator+"SouthWest"+fileSeparator + "Products" + fileSeparator + "GetPriceMatch" + fileSeparator + "TC001_GetPriceMatch.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
		}
		
		
		//********************************************************Availability Search ****************************************************
		
		@DataProvider(name = "TC001_AvailabilitySearch")
		public static String[][] TC001_AvailabilitySearch(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC001_AvailabilitySearch.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC002_AvailabilitySearchByCategory")
		public static String[][] TC002_AvailabilitySearchByCategory(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "southWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC002_AvailabilitySearchByCategory.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC003_AvailabilitySearchByDuration")
		public static String[][] TC003_AvailabilitySearchByDuration(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC003_AvailabilitySearchByDuration.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC004_AvailabilitySearchByAddress")
		public static String[][] TC004_AvailabilitySearchByAddress(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC004_AvailabilitySearchByAddress.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC005_AvailabilitySearch_QueryProductsByPrice")
		public static String[][] TC005_AvailabilitySearch_QueryProductsByPrice(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC005_AvailabilitySearch_QueryProductsByPrice.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC006_AvailabilitySearch_QueryProductsByRating")
		public static String[][] TC006_AvailabilitySearch_QueryProductsByRating(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC006_AvailabilitySearch_QueryProductsByRating.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC007_AvailabilitySearch_QueryProductsByReviews")
		public static String[][] TC007_AvailabilitySearch_QueryProductsByReviews(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC007_AvailabilitySearch_QueryProductsByReviews.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC008_AvailabilitySearch_QueryProductsByTriedandTrue")
		public static String[][] TC008_AvailabilitySearch_QueryProductsByTriedandTrue(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC008_AvailabilitySearch_QueryProductsByTriedandTrue.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC009_AvailabilitySearch_SearchProductsByWebId")
		public static String[][] TC009_AvailabilitySearch_SearchProductsByWebId(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC009_AvailabilitySearch_SearchProductsByWebId.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC010_AvailabilitySearch_SearchProductsByLatLong")
		public static String[][] TC010_AvailabilitySearch_SearchProductsByLatLong(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC010_AvailabilitySearch_SearchProductsByLatLong.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC011_AvailabilitySearch_SortProductsByPriceLowHigh")
		public static String[][] TC011_AvailabilitySearch_SortProductsByPriceLowHigh(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC011_AvailabilitySearch_SortProductsByPriceLowHigh.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC012_AvailabilitySearch_SortProductsByPriceHighLow")
		public static String[][] TC012_AvailabilitySearch_SortProductsByPriceHighLow(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC012_AvailabilitySearch_SortProductsByPriceHighLow.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC013_AvailabilitySearch_SortProductsByPopularity")
		public static String[][] TC013_AvailabilitySearch_SortProductsByPopularity(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC013_AvailabilitySearch_SortProductsByPopularity.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC014_AvailabilitySearch_SortProductsByRecommended")
		public static String[][] TC014_AvailabilitySearch_SortProductsByRecommended(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC014_AvailabilitySearch_SortProductsByRecommended.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC015_AvailabilitySearch_SortProductsByTopRated")
		public static String[][] TC015_AvailabilitySearch_SortProductsByTopRated(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC015_AvailabilitySearch_SortProductsByTopRated.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC016_AvailabilitySearch_SortProductsByPriceLowHighFilteredByPriceRange")
		public static String[][] TC016_AvailabilitySearch_SortProductsByPriceLowHighFilteredByPriceRange(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC016_AvailabilitySearch_SortProductsByPriceLowHighFilteredByPriceRange.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC017_AvailabilitySearch_SortProductsByPriceHighLowForcedHitsPerPage")
		public static String[][] TC017_AvailabilitySearch_SortProductsByPriceHighLowForcedHitsPerPage(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC017_AvailabilitySearch_SortProductsByPriceHighLowForcedHitsPerPage.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC018_AvailabilitySearch_SortProductsByPriceHighLowFilteredByLatLong")
		public static String[][] TC018_AvailabilitySearch_SortProductsByPriceHighLowFilteredByLatLong(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC018_AvailabilitySearch_SortProductsByPriceHighLowFilteredByLatLong.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC019_AvailabilitySearch_SortProductsByPriceHighLowFilteredByWebId")
		public static String[][] TC019_AvailabilitySearch_SortProductsByPriceHighLowFilteredByWebId(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC019_AvailabilitySearch_SortProductsByPriceHighLowFilteredByWebId.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
		@DataProvider(name = "TC020_AvailabilitySearch_SortProductsByPriceLowHighFilteredByWebIdQueryPrice")
		public static String[][] TC020_AvailabilitySearch_SortProductsByPriceLowHighFilteredByWebIdQueryPrice(){
			
			ExcelManager excel = null;
			excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "SouthWestSearches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC020_AvailabilitySearch_SortProductsByPriceLowHighFilteredByWebIdQueryPrice.xlsx");
			
			return excel.getExcelSheetData("Sheet1");
			
		}
		
