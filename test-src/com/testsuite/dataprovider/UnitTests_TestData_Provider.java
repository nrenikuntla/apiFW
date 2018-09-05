package com.testsuite.dataprovider;

import org.testng.annotations.DataProvider;

import com.datamanager.ExcelManager;

public class UnitTests_TestData_Provider {
	
	public static String fileSeparator = System.getProperty("file.separator");
	
	
	//******************************************************** Products ****************************************************
	
	@DataProvider(name = "TC001_getProductAvailability")
	public static String[][] TC001_getProductAvailability(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator +"Products" + fileSeparator + "GetProductAvailability" + fileSeparator + "TC001_GetProductAvailability.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC002_getProductAvailability")
	public static String[][] TC002_getProductAvailability(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator +"Products" + fileSeparator + "GetProductAvailability" + fileSeparator + "TC002_GetProductAvailability.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "getProductLanguageOptions_TestData")
	public static String[][] getProductLanguageOptions_TestData(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator +"Products" + fileSeparator + "GetProductLanguageOptions" + fileSeparator + "GetProductLanguageOptions_TestData.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_getProductOptions")
	public static String[][] TC001_getProductOptions(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetProductOptions" + fileSeparator + "TC001_GetProductOptions.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}

	@DataProvider(name = "TC002_getProductOptions")
	public static String[][] TC002_getProductOptions(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetProductOptions" + fileSeparator + "TC002_GetProductOptions.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_getProductReviews")
	public static String[][] TC001_getProductReviews(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetProductReviews" + fileSeparator + "TC001_GetProductReviews.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC002_getProductReviews")
	public static String[][] TC002_getProductReviews(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetProductReviews" + fileSeparator + "TC002_GetProductReviews.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_getProductsDetails")
	public static String[][] TC001_getProductsDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetProductsDetails" + fileSeparator + "TC001_GetProductDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC002_getProductsDetails")
	public static String[][] TC002_getProductsDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetProductsDetails" + fileSeparator + "TC002_GetProductDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_getHotelDetails")
	public static String[][] TC001_getHotelDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetHotelDetails" + fileSeparator + "TC001_GetHotelDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC002_getHotelDetails")
	public static String[][] TC002_getHotelDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetHotelDetails" + fileSeparator + "TC002_GetHotelDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_getPriceMatch")
	public static String[][] TC001_getPriceMatch(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetPriceMatch" + fileSeparator + "TC001_GetPriceMatch.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC002_getPriceMatch")
	public static String[][] TC002_getPriceMatch(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Products" + fileSeparator + "GetPriceMatch" + fileSeparator + "TC002_GetPriceMatch.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	//******************************************************** Search ****************************************************
		
	@DataProvider(name = "tc001_searchProducts")
	public static String[][] tc001_searchProducts(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC001_SearchProducts.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc002_searchProductsByCategory")
	public static String[][] tc002_searchProductsByCategory(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC002_SearchProductsByCategory.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc003_searchProductsByDuration")
	public static String[][] tc003_searchProductsByDuration(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC003_SearchProductsByDuration.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc004_searchProductsByAddress")
	public static String[][] tc004_searchProductsByAddress(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC004_SearchProductsByAddress.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc005_searchProductsByAddress")
	public static String[][] tc005_searchProductsByAddress(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC005_SearchProductsByAddress.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc006_queryProductsByPrice")
	public static String[][] tc006_queryProductsByPrice(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC006_QueryProductsByPrice.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc007_queryProductsByRating")
	public static String[][] tc007_queryProductsByRating(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC007_QueryProductsByRating.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc008_queryProductsByReviews")
	public static String[][] tc008_queryProductsByReviews(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC008_QueryProductsByReviews.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc009_queryProductsByTriedandTrue")
	public static String[][] tc009_queryProductsByTriedandTrue(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC009_QueryProductsByTriedandTrue.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc010_searchProductsByWebId")
	public static String[][] tc010_searchProductsByWebId(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC010_SearchProductsByWebId.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc011_searchProductsByLatLong")
	public static String[][] tc011_searchProductsByLatLong(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC011_SearchProductsByLatLong.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc012_sortProductsByPriceLowHigh")
	public static String[][] tc012_sortProductsByPriceLowHigh(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC012_SortProductsByPriceLowHigh.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc013_sortProductsByPriceHighLow")
	public static String[][] tc013_sortProductsByPriceHighLow(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC013_SortProductsByPriceHighLow.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc014_sortProductsByPopularity")
	public static String[][] tc014_sortProductsByPopularity(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC014_SortProductsByPopularity.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc015_sortProductsByRecommended")
	public static String[][] tc015_sortProductsByRecommended(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC015_SortProductsByRecommended.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc016_sortProductsByTopRated")
	public static String[][] tc016_sortProductsByTopRated(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC016_SortProductsByTopRated.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc017_sortProductsByPriceLowHighFilteredByPriceRange")
	public static String[][] tc017_sortProductsByPriceLowHighFilteredByPriceRange(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC017_SortProductsByPriceLowHighFilteredByPriceRange.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc018_sortProductsByPriceHighLowForcedHitsPerPage")
	public static String[][] tc018_sortProductsByPriceHighLowForcedHitsPerPage(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC018_SortProductsByPriceHighLowForcedHitsPerPage.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc019_sortProductsByPriceHighLowFilteredByLatLong")
	public static String[][] tc019_sortProductsByPriceHighLowFilteredByLatLong(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC019_SortProductsByPriceHighLowFilteredByLatLong.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc020_sortProductsByPriceHighLowFilteredByWebId")
	public static String[][] tc020_sortProductsByPriceHighLowFilteredByWebId(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC020_SortProductsByPriceHighLowFilteredByWebId.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc021_sortProductsByPriceLowHighFilteredByWebIdQueryPrice")
	public static String[][] tc021_sortProductsByPriceLowHighFilteredByWebIdQueryPrice(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC021_SortProductsByPriceLowHighFilteredByWebIdQueryPrice.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc022_filterProductsByTags")
	public static String[][] tc022_filterProductsByTags(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC022_FilterProductsByTags.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc023_filterProductsByVendorId")
	public static String[][] tc023_filterProductsByVendorId(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC023_FilterProductsByVendorId.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc024_queryWithoutAndInvalidPartnerId")
	public static String[][] tc024_queryWithoutAndInvalidPartnerId(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC024_QueryWithoutAndInvalidPartnerId.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc025_searchProductsByProductId")
	public static String[][] tc025_searchProductsByProductId(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC025_SearchProductsByProductId.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "tc026_searchProductsForNearby")
	public static String[][] tc026_searchProductsForNearby(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC026_SearchProductsByWebIdForNearby.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	
	@DataProvider(name = "tc027_searchProductsForNearbyLatLng")
	public static String[][] tc027_searchProductsForNearbyLatLng(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "CreateProductSearch" + fileSeparator + "TC027_SearchProductsByLatLngForNearby.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_GetRelatedProducts")
	public static String[][] TC001_GetRelatedProducts(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "GetRelatedProducts" + fileSeparator + "TC001_GetRelatedProducts.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC002_GetRelatedProducts")
	public static String[][] TC002_GetRelatedProducts(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "GetRelatedProducts" + fileSeparator + "TC002_GetRelatedProducts.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_POST_ProductsByIds")
	public static String[][] TC001_POST_ProductsByIds(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "GetProducts" + fileSeparator + "TC001_POST_ProductsByIds.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC001_Get_ProductsByIds")
	public static String[][] TC001_Get_ProductsByIds(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "ProductsByIds" + fileSeparator + "TC001_Get_ProductsByIds.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	//******************************************************** Booking Service ****************************************************
	
	@DataProvider(name = "TC001_CreateShoppingCart")
	public static String[][] TC001_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC001_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_CreateShoppingCart")
	public static String[][] TC002_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC002_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_CreateShoppingCart")
	public static String[][] TC003_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC003_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_CreateShoppingCart")
	public static String[][] TC004_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC004_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_CreateShoppingCart")
	public static String[][] TC005_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC005_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC006_CreateShoppingCart")
	public static String[][] TC006_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC006_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC007_CreateShoppingCart")
	public static String[][] TC007_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC007_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC008_CreateShoppingCart")
	public static String[][] TC008_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC008_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC009_CreateShoppingCart")
	public static String[][] TC009_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC009_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC010_CreateShoppingCart")
	public static String[][] TC010_CreateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "CreateShoppingCart" + fileSeparator + "TC010_CreateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_AddBookingOptionsToCart")
	 public static String[][] TC001_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC001_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC002_AddBookingOptionsToCart")
	 public static String[][] TC002_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC002_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC003_AddBookingOptionsToCart")
	 public static String[][] TC003_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC003_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC004_AddBookingOptionsToCart")
	 public static String[][] TC004_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC004_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC005_AddBookingOptionsToCart")
	 public static String[][] TC005_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC005_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC006_AddBookingOptionsToCart")
	 public static String[][] TC006_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC006_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC007_AddBookingOptionsToCart")
	 public static String[][] TC007_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC007_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC008_AddBookingOptionsToCart")
	 public static String[][] TC008_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC008_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC009_AddBookingOptionsToCart")
	 public static String[][] TC009_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC009_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC010_AddBookingOptionsToCart")
	 public static String[][] TC010_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC010_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC011_AddBookingOptionsToCart")
	 public static String[][] TC011_AddBookingOptionsToCart(){
	  
	  ExcelManager excel = null;
	  excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "AddBookingOptionsToCart" + fileSeparator + "TC011_AddBookingOptionsToCart.xlsx");
	  
	  return excel.getExcelSheetData("Sheet1");
	  
	 }
	
	@DataProvider(name = "TC001_GetBookingQuestions")
	public static String[][] TC001_GetBookingQuestions(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "GetBookingQuestions" + fileSeparator + "TC001_GetBookingQuestions.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_UpdateBookingAnswers")
	public static String[][] TC001_UpdateBookingAnswers(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookingAnswers" + fileSeparator + "TC001_UpdateBookingAnswers.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_UpdateBookingAnswers")
	public static String[][] TC002_UpdateBookingAnswers(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookingAnswers" + fileSeparator + "TC002_UpdateBookingAnswers.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_UpdateBookingAnswers")
	public static String[][] TC003_UpdateBookingAnswers(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookingAnswers" + fileSeparator + "TC003_UpdateBookingAnswers.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_UpdateBookingAnswers")
	public static String[][] TC004_UpdateBookingAnswers(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookingAnswers" + fileSeparator + "TC004_UpdateBookingAnswers.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_UpdateBookingAnswers")
	public static String[][] TC005_UpdateBookingAnswers(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookingAnswers" + fileSeparator + "TC005_UpdateBookingAnswers.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_UpdateTravelerDetails")
	public static String[][] TC001_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC001_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_UpdateTravelerDetails")
	public static String[][] TC002_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC002_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_UpdateTravelerDetails")
	public static String[][] TC003_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC003_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_UpdateTravelerDetails")
	public static String[][] TC004_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC004_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_UpdateTravelerDetails")
	public static String[][] TC005_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC005_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC006_UpdateTravelerDetails")
	public static String[][] TC006_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC006_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC007_UpdateTravelerDetails")
	public static String[][] TC007_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC007_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC008_UpdateTravelerDetails")
	public static String[][] TC008_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC008_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC009_UpdateTravelerDetails")
	public static String[][] TC009_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC009_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC010_UpdateTravelerDetails")
	public static String[][] TC010_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC010_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC011_UpdateTravelerDetails")
	public static String[][] TC011_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC011_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC012_UpdateTravelerDetails")
	public static String[][] TC012_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC012_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC013_UpdateTravelerDetails")
	public static String[][] TC013_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC013_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC014_UpdateTravelerDetails")
	public static String[][] TC014_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC014_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC015_UpdateTravelerDetails")
	public static String[][] TC015_UpdateTravelerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateTravelerDetails" + fileSeparator + "TC015_UpdateTravelerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_UpdateBookerDetails")
	public static String[][] TC001_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC001_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_UpdateBookerDetails")
	public static String[][] TC002_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC002_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_UpdateBookerDetails")
	public static String[][] TC003_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC003_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_UpdateBookerDetails")
	public static String[][] TC004_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC004_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_UpdateBookerDetails")
	public static String[][] TC005_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC005_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC006_UpdateBookerDetails")
	public static String[][] TC006_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC006_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC007_UpdateBookerDetails")
	public static String[][] TC007_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC007_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC008_UpdateBookerDetails")
	public static String[][] TC008_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC008_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC009_UpdateBookerDetails")
	public static String[][] TC009_UpdateBookerDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateBookerDetails" + fileSeparator + "TC009_UpdateBookerDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_ProcessPaymentAndCreateBooking")
	public static String[][] TC001_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC001_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_ProcessPaymentAndCreateBooking")
	public static String[][] TC002_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC002_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_ProcessPaymentAndCreateBooking")
	public static String[][] TC003_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC003_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_ProcessPaymentAndCreateBooking")
	public static String[][] TC004_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC004_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_ProcessPaymentAndCreateBooking")
	public static String[][] TC005_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC005_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC006_ProcessPaymentAndCreateBooking")
	public static String[][] TC006_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC006_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC007_ProcessPaymentAndCreateBooking")
	public static String[][] TC007_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC007_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC008_ProcessPaymentAndCreateBooking")
	public static String[][] TC008_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC008_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC009_ProcessPaymentAndCreateBooking")
	public static String[][] TC009_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC009_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	

	@DataProvider(name = "TC011_ProcessPaymentAndCreateBooking")
	public static String[][] TC011_ProcessPaymentAndCreateBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ProcessPaymentAndCreateBooking" + fileSeparator + "TC011_ProcessPaymentAndCreateBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_GetVoucherDetails")
	public static String[][] TC001_GetVoucherDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "GetVoucherDetails" + fileSeparator + "TC001_GetVoucherDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_GetSingleBooking")
	public static String[][] TC001_GetSingleBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "GetSingleBooking" + fileSeparator + "TC001_GetSingleBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_GetSingleBookingForCustomer")
	public static String[][] TC001_GetSingleBookingForCustomer(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "GetSingleBookingForCustomer" + fileSeparator + "TC001_GetSingleBookingForCustomer.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_GetSingleBookingForCustomer")
	public static String[][] TC002_GetSingleBookingForCustomer(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "GetSingleBookingForCustomer" + fileSeparator + "TC002_GetSingleBookingForCustomer.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_GetSingleBookingForCustomer")
	public static String[][] TC003_GetSingleBookingForCustomer(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "GetSingleBookingForCustomer" + fileSeparator + "TC003_GetSingleBookingForCustomer.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_ViewBookingsHistory")
	public static String[][] TC001_ViewBookingsHistory(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ViewBookingsHistory" + fileSeparator + "TC001_ViewBookingsHistory.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_ViewBookingsHistory")
	public static String[][] TC002_ViewBookingsHistory(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ViewBookingsHistory" + fileSeparator + "TC002_ViewBookingsHistory.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_ViewBookingsHistory")
	public static String[][] TC003_ViewBookingsHistory(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ViewBookingsHistory" + fileSeparator + "TC003_ViewBookingsHistory.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_ViewBookingsHistory")
	public static String[][] TC004_ViewBookingsHistory(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ViewBookingsHistory" + fileSeparator + "TC004_ViewBookingsHistory.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_ViewBookingsHistory")
	public static String[][] TC005_ViewBookingsHistory(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ViewBookingsHistory" + fileSeparator + "TC005_ViewBookingsHistory.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_FindPastBooking")
	public static String[][] TC001_FindPastBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "FindPastBooking" + fileSeparator + "TC001_FindPastBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_FindPastBooking")
	public static String[][] TC002_FindPastBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "FindPastBooking" + fileSeparator + "TC002_FindPastBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_RequestCancelBooking")
	public static String[][] TC001_RequestCancelBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "RequestCancelBooking" + fileSeparator + "TC001_RequestCancelBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_RequestGroupBooking")
	public static String[][] TC001_RequestGroupBooking(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "RequestGroupBooking" + fileSeparator + "TC001_RequestGroupBooking.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_UpdateLanguageOptionCode")
	public static String[][] TC001_UpdateLanguageOptionCode(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateLanguageOptionCode" + fileSeparator + "TC001_UpdateLanguageOptionCode.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_UpdateLanguageOptionCode")
	public static String[][] TC002_UpdateLanguageOptionCode(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateLanguageOptionCode" + fileSeparator + "TC002_UpdateLanguageOptionCode.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_UpdateHotelPickupLocation")
	public static String[][] TC001_UpdateHotelPickupLocation(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "UpdateHotelPickupLocation" + fileSeparator + "TC001_UpdateHotelPickupLocation.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_RemoveItemsFromShoppingCart")
	public static String[][] TC001_RemoveItemsFromShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "RemoveItemsFromShoppingCart" + fileSeparator + "TC001_RemoveItemsFromShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_RemoveItemsFromShoppingCart")
	public static String[][] TC002_RemoveItemsFromShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "RemoveItemsFromShoppingCart" + fileSeparator + "TC002_RemoveItemsFromShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_ValidateShoppingCart")
	public static String[][] TC001_ValidateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ValidateShoppingCart" + fileSeparator + "TC001_ValidateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC002_ValidateShoppingCart")
	public static String[][] TC002_ValidateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ValidateShoppingCart" + fileSeparator + "TC002_ValidateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC003_ValidateShoppingCart")
	public static String[][] TC003_ValidateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ValidateShoppingCart" + fileSeparator + "TC003_ValidateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC004_ValidateShoppingCart")
	public static String[][] TC004_ValidateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ValidateShoppingCart" + fileSeparator + "TC004_ValidateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC005_ValidateShoppingCart")
	public static String[][] TC005_ValidateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ValidateShoppingCart" + fileSeparator + "TC005_ValidateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	@DataProvider(name = "TC006_ValidateShoppingCart")
	public static String[][] TC006_ValidateShoppingCart(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Booking" + fileSeparator + "ValidateShoppingCart" + fileSeparator + "TC006_ValidateShoppingCart.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
	}
	
	//******************************************************** Hertz ****************************************************	
	
	@DataProvider(name = "TC001_HertzGetVehicleLocations")
	public static String[][] TC001_HertzGetVehicleLocations(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzGetVehicleLocations" + fileSeparator + "TC001_HertzGetVehicleLocations.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_HertzGetVehicleLocationDetails")
	public static String[][] TC001_HertzGetVehicleLocationDetails(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzGetVehicleLocationDetails" + fileSeparator + "TC001_HertzGetVehicleLocationDetails.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_HertzGetVehicleAvailRate")
	public static String[][] TC001_HertzGetVehicleAvailRate(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzGetVehicleAvailRate" + fileSeparator + "TC001_HertzGetVehicleAvailRate.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_HertzGetVehicleAvailRate")
	public static String[][] TC002_HertzGetVehicleAvailRate(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzGetVehicleAvailRate" + fileSeparator + "TC002_HertzGetVehicleAvailRate.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_HertzGetVehicleAvailRateOptions")
	public static String[][] TC001_HertzGetVehicleAvailRateOptions(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzGetVehicleAvailRateOptions" + fileSeparator + "TC001_HertzGetVehicleAvailRateOptions.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_HertzGetVehicleRes")
	public static String[][] TC001_HertzGetVehicleRes(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzGetVehicleRes" + fileSeparator + "TC001_HertzGetVehicleRes.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_HertzGetVehicleRetRes")
	public static String[][] TC001_HertzGetVehicleRetRes(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzGetVehicleRetRes" + fileSeparator + "TC001_HertzGetVehicleRetRes.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_HertzGetVehicleCancel")
	public static String[][] TC001_HertzGetVehicleCancel(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzGetVehicleCancel" + fileSeparator + "TC001_HertzGetVehicleCancel.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_HertzSPGSuite")
	public static String[][] TC001_HertzSPGSuite(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Hertz" + fileSeparator + "HertzSPGSuite" + fileSeparator + "TC001_HertzSPGSuite.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}	
	
	
	//******************************************************** Location Service ****************************************************
	
	@DataProvider(name = "TC001_GetAllCountryTypePlaces")
	public static String[][] TC001_GetAllCountryTypePlaces(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "GetAllCountryTypePlaces" + fileSeparator + "TC001_GetAllCountryTypePlaces.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_GetPlaceByLatLng")
	public static String[][] TC001_GetPlaceByLatLng(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "GetPlaceByLatLng" + fileSeparator + "TC001_GetPlaceByLatLng.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_PlacePass_GetPlaceByLatLng")
	public static String[][] TC001_PlacePassGetPlaceByLatLng(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "GetPlaceByLatLng" + fileSeparator + "TC001_PlacePass_GetPlaceByLatLng.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_GetTopDestinations")
	public static String[][] TC001_GetTopDestinations(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "GetTopDestinations" + fileSeparator + "TC001_GetTopDestinations.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_GetTopDestinations")
	public static String[][] TC002_GetTopDestinations(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "GetTopDestinations" + fileSeparator + "TC002_GetTopDestinations.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_LookupPartnerConfig")
	public static String[][] TC001_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC001_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_PlacePass_LookupPartnerConfig")
	public static String[][] TC001_PlacePass_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC001_PlacePass_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_LookupPartnerConfig")
	public static String[][] TC002_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC002_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_PlacePass_LookupPartnerConfig")
	public static String[][] TC002_PlacePass_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC002_PlacePass_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_LookupPartnerConfig")
	public static String[][] TC003_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC003_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_PlacePass_LookupPartnerConfig")
	public static String[][] TC003_PlacePass_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC003_PlacePass_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_LookupPartnerConfig")
	public static String[][] TC004_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC004_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_PlacePass_LookupPartnerConfig")
	public static String[][] TC004_PlacePass_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC004_PlacePass_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_LookupPartnerConfig")
	public static String[][] TC005_LookupPartnerConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerConfig" + fileSeparator + "TC005_LookupPartnerConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_LookupPartnerPlaceConfig")
	public static String[][] TC001_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC001_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_PlacePass_LookupPartnerPlaceConfig")
	public static String[][] TC001_PlacePass_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC001_PlacePass_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_LookupPartnerPlaceConfig")
	public static String[][] TC002_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC002_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_PlacePass_LookupPartnerPlaceConfig")
	public static String[][] TC002_PlacePass_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC002_PlacePass_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_LookupPartnerPlaceConfig")
	public static String[][] TC003_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC003_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_PlacePass_LookupPartnerPlaceConfig")
	public static String[][] TC003_PlacePass_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC003_PlacePass_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_LookupPartnerPlaceConfig")
	public static String[][] TC004_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC004_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}	
	
	@DataProvider(name = "TC004_PlacePass_LookupPartnerPlaceConfig")
	public static String[][] TC004_PlacePass_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC004_PlacePass_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_LookupPartnerPlaceConfig")
	public static String[][] TC005_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC005_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC007_LookupPartnerPlaceConfig")
	public static String[][] TC007_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC007_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC008_LookupPartnerPlaceConfig")
	public static String[][] TC008_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC008_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC009_LookupPartnerPlaceConfig")
	public static String[][] TC009_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC009_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC010_LookupPartnerPlaceConfig")
	public static String[][] TC010_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC010_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC011_LookupPartnerPlaceConfig")
	public static String[][] TC011_LookupPartnerPlaceConfig(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceConfig" + fileSeparator + "TC011_LookupPartnerPlaceConfig.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_LookupPartnerPlaces")
	public static String[][] TC001_LookupPartnerPlaces(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaces" + fileSeparator + "TC001_LookupPartnerPlaces.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_PlacePass_LookupPartnerPlaces")
	public static String[][] TC001_PlacePass_LookupPartnerPlaces(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaces" + fileSeparator + "TC001_PlacePass_LookupPartnerPlaces.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_LookupPartnerPlaces")
	public static String[][] TC002_LookupPartnerPlaces(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaces" + fileSeparator + "TC002_LookupPartnerPlaces.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_LookupPartnerPlaces")
	public static String[][] TC003_LookupPartnerPlaces(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaces" + fileSeparator + "TC003_LookupPartnerPlaces.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_LookupPartnerPlaceWithProperties")
	public static String[][] TC001_LookupPartnerPlaceWithProperties(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceWithProperties" + fileSeparator + "TC001_LookupPartnerPlaceWithProperties.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_LookupPartnerPlaceWithProperties")
	public static String[][] TC002_LookupPartnerPlaceWithProperties(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceWithProperties" + fileSeparator + "TC002_LookupPartnerPlaceWithProperties.xlsx");
		
		return excel.getExcelSheetData("Sheet2");
		
	}
	
	
	@DataProvider(name = "TC001_PlacePass_LookupPartnerPlaceWithProperties")
	public static String[][] TC001_PlacePass_LookupPartnerPlaceWithProperties(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerPlaceWithProperties" + fileSeparator + "TC001_PlacePass_LookupPartnerPlaceWithProperties.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_LookupPartnerProperty")
	public static String[][] TC001_LookupPartnerProperty(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerProperty" + fileSeparator + "TC001_LookupPartnerProperty.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_LookupPartnerProperty")
	public static String[][] TC002_LookupPartnerProperty(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerProperty" + fileSeparator + "TC002_LookupPartnerProperty.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_LookupPartnerProperty")
	public static String[][] TC003_LookupPartnerProperty(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerProperty" + fileSeparator + "TC003_LookupPartnerProperty.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	
	@DataProvider(name = "TC005_LookupPartnerProperty")
	public static String[][] TC005_LookupPartnerProperty(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "LookupPartnerProperty" + fileSeparator + "TC005_LookupPartnerProperty.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_PartnerPlaceSitemap")
	public static String[][] TC001_PartnerPlaceSitemap(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "PartnerPlaceSitemap" + fileSeparator + "TC001_PartnerPlaceSitemap.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC001_PlacePass_PartnerPlaceSitemap")
	public static String[][] TC001_PlacePass_PartnerPlaceSitemap(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "PartnerPlaceSitemap" + fileSeparator + "TC001_PlacePass_PartnerPlaceSitemap.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_PartnerPlaceSitemap")
	public static String[][] TC002_PartnerPlaceSitemap(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Data" + fileSeparator + "PartnerPlaceSitemap" + fileSeparator + "TC002_PartnerPlaceSitemap.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	//******************************************************** Discount ****************************************************
	
	
	@DataProvider(name = "TC001_ValidateDiscount")
	public static String[][] TC001_ValidateDiscount(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "DiscountService" + fileSeparator + "ValidateDiscount" + fileSeparator + "TC001_ValidateDiscount.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_ValidateDiscount")
	public static String[][] TC002_ValidateDiscount(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "DiscountService" + fileSeparator + "ValidateDiscount" + fileSeparator + "TC002_ValidateDiscount.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	//******************************************************** Discount ****************************************************
	
	@DataProvider(name = "TC001_AvailabilitySearch")
	public static String[][] TC001_AvailabilitySearch(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC001_AvailabilitySearch.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC002_AvailabilitySearchByCategory")
	public static String[][] TC002_AvailabilitySearchByCategory(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC002_AvailabilitySearchByCategory.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC003_AvailabilitySearchByDuration")
	public static String[][] TC003_AvailabilitySearchByDuration(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC003_AvailabilitySearchByDuration.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC004_AvailabilitySearchByAddress")
	public static String[][] TC004_AvailabilitySearchByAddress(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC004_AvailabilitySearchByAddress.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC005_AvailabilitySearch_QueryProductsByPrice")
	public static String[][] TC005_AvailabilitySearch_QueryProductsByPrice(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC005_AvailabilitySearch_QueryProductsByPrice.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC006_AvailabilitySearch_QueryProductsByRating")
	public static String[][] TC006_AvailabilitySearch_QueryProductsByRating(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC006_AvailabilitySearch_QueryProductsByRating.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC007_AvailabilitySearch_QueryProductsByReviews")
	public static String[][] TC007_AvailabilitySearch_QueryProductsByReviews(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC007_AvailabilitySearch_QueryProductsByReviews.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC008_AvailabilitySearch_QueryProductsByTriedandTrue")
	public static String[][] TC008_AvailabilitySearch_QueryProductsByTriedandTrue(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC008_AvailabilitySearch_QueryProductsByTriedandTrue.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC009_AvailabilitySearch_SearchProductsByWebId")
	public static String[][] TC009_AvailabilitySearch_SearchProductsByWebId(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC009_AvailabilitySearch_SearchProductsByWebId.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC010_AvailabilitySearch_SearchProductsByLatLong")
	public static String[][] TC010_AvailabilitySearch_SearchProductsByLatLong(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC010_AvailabilitySearch_SearchProductsByLatLong.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC011_AvailabilitySearch_SortProductsByPriceLowHigh")
	public static String[][] TC011_AvailabilitySearch_SortProductsByPriceLowHigh(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC011_AvailabilitySearch_SortProductsByPriceLowHigh.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC012_AvailabilitySearch_SortProductsByPriceHighLow")
	public static String[][] TC012_AvailabilitySearch_SortProductsByPriceHighLow(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC012_AvailabilitySearch_SortProductsByPriceHighLow.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC013_AvailabilitySearch_SortProductsByPopularity")
	public static String[][] TC013_AvailabilitySearch_SortProductsByPopularity(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC013_AvailabilitySearch_SortProductsByPopularity.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC014_AvailabilitySearch_SortProductsByRecommended")
	public static String[][] TC014_AvailabilitySearch_SortProductsByRecommended(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC014_AvailabilitySearch_SortProductsByRecommended.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC015_AvailabilitySearch_SortProductsByTopRated")
	public static String[][] TC015_AvailabilitySearch_SortProductsByTopRated(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC015_AvailabilitySearch_SortProductsByTopRated.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC016_AvailabilitySearch_SortProductsByPriceLowHighFilteredByPriceRange")
	public static String[][] TC016_AvailabilitySearch_SortProductsByPriceLowHighFilteredByPriceRange(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC016_AvailabilitySearch_SortProductsByPriceLowHighFilteredByPriceRange.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC017_AvailabilitySearch_SortProductsByPriceHighLowForcedHitsPerPage")
	public static String[][] TC017_AvailabilitySearch_SortProductsByPriceHighLowForcedHitsPerPage(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC017_AvailabilitySearch_SortProductsByPriceHighLowForcedHitsPerPage.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC018_AvailabilitySearch_SortProductsByPriceHighLowFilteredByLatLong")
	public static String[][] TC018_AvailabilitySearch_SortProductsByPriceHighLowFilteredByLatLong(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC018_AvailabilitySearch_SortProductsByPriceHighLowFilteredByLatLong.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC019_AvailabilitySearch_SortProductsByPriceHighLowFilteredByWebId")
	public static String[][] TC019_AvailabilitySearch_SortProductsByPriceHighLowFilteredByWebId(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC019_AvailabilitySearch_SortProductsByPriceHighLowFilteredByWebId.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC020_AvailabilitySearch_SortProductsByPriceLowHighFilteredByWebIdQueryPrice")
	public static String[][] TC020_AvailabilitySearch_SortProductsByPriceLowHighFilteredByWebIdQueryPrice(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC020_AvailabilitySearch_SortProductsByPriceLowHighFilteredByWebIdQueryPrice.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	@DataProvider(name = "TC021_AvailabilitySearch_FilterProductsByVendorId")
	public static String[][] TC021_AvailabilitySearch_FilterProductsByVendorId(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "Searches" + fileSeparator + "AvailabilitySearch" + fileSeparator + "TC021_AvailabilitySearch_FilterProductsByVendorId.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
	
	//*********************************************Bookingflow*******************************************************
	@DataProvider(name = "TC001_Bookingflow")
	public static String[][] TC001_Bookingflow(){
		
		ExcelManager excel = null;
		excel = new ExcelManager(System.getProperty("user.dir") + fileSeparator + "Data" + fileSeparator + "UnitTests" + fileSeparator + "BookingFlow" +  fileSeparator + "Search" +fileSeparator + "TC001_Bookingflow.xlsx");
		
		return excel.getExcelSheetData("Sheet1");
		
	}
}
