package stockProject;

import java.io.File;
import java.io.FileNotFoundException;

public class TestClass {
	
	static Stock spcx = new Stock("SpaceX", "SPCX", 123.45, 0);
	static Stock apple = new Stock("Apple, Inc", "APPL", 178.96, 0);
	static Stock homeDepot = new Stock("Home Depot", "HMDP", 46.38, 0);
	static Stock publix = new Stock("Publix", "PBLX", 38.54, 0);

	public static void main(String[] args) throws FileNotFoundException {

		//testMethod(); //for testing different unrelated things easily
		//testStockExchange(); //tests StockExchange methods
		//testShareHolder_AddShare();
		//testShareHolder_RemoveShare();
		//testShareHolder_Report();
		//testAnalytics(); //for testing Analytics class methods
		testSystem(); //tests everything
	}
	
	public static void testSystem() throws FileNotFoundException {
		StockExchange stockExchange = makeStockExchange();
		ShareHolder dave = new ShareHolder("Dave", "Joyner", 500.00);
		
		System.out.println(stockExchange);
		System.out.println(dave);
				
		System.out.println(">>>>>This is the original condition of the Stock Exchange and the ShareHolder<<<<<");
		System.out.println();
		System.out.println(">>>>>Now we will buy 2 Shares of SpaceX, Home Depot and Publix, respectively and print the new report before advancing one day of trading<<<<<\n\n");
		
		dave.addShare(spcx,  2);
		dave.addShare(homeDepot, 2);
		dave.addShare(publix, 2);
		
		System.out.println(dave);
		
		String shareHolderReport = Analytics.getShareHolderReport(stockExchange, dave);
		System.out.println(shareHolderReport);	
		
		System.out.println(">>>>>Conducting trading for the day...<<<<<\n\n");
		stockExchange.advanceDay(dave.getSharesOwned());
		
		System.out.println(stockExchange);
		System.out.println(dave);		
		shareHolderReport = Analytics.getShareHolderReport(stockExchange, dave);
		System.out.println(shareHolderReport);	
		
		System.out.println(">>>>>Now let's look at Dave's profile to see if he made any money<<<<<\n");
		System.out.println(">>>>>We will do this buy selling everything and comaring the wallet balance to the initial amount of $500<<<<<\n");
		System.out.println(">>>>>There should be no stock in the profile and the balance will be close to $500.00<<<<<");

		dave.removeShare(spcx, 2);
		dave.removeShare(homeDepot, 2);
		dave.removeShare(publix, 2);
		
		System.out.println(dave);		
		shareHolderReport = Analytics.getShareHolderReport(stockExchange, dave);
		System.out.println(shareHolderReport);

		System.out.println(stockExchange);
		
		System.out.println(">>>>>Rebuy the 6 shares and do 30 days of training...<<<<<\n");
		dave.addShare(spcx,  2);
		dave.addShare(homeDepot, 2);
		dave.addShare(publix, 2);
		
		System.out.println(">>>>>Now double check that all the Stock fields were properly reset<<<<<\n");
		
		System.out.println(stockExchange);
		System.out.println(dave);		
		shareHolderReport = Analytics.getShareHolderReport(stockExchange, dave);
		System.out.println(shareHolderReport);	
		
		for (int i = 0; i < 30; i++) {
			stockExchange.advanceDay(dave.getSharesOwned());
		}
		
		System.out.println(">>>>>After 30 days of Exchange activity, we check in<<<<<\n");
		
		shareHolderReport = Analytics.getShareHolderReport(stockExchange, dave);
		System.out.println(shareHolderReport);	
		
		System.out.println(stockExchange);
	}
	
	public static void testMethod() {
		double testNum = 1279.14335654;
		String result = "";
		result += String.format("$%-20.2f$%-20.2f", testNum, testNum);
		System.out.println(result);
	}
	
	public static void testStockExchange() throws FileNotFoundException {
		StockExchange testExchange = makeStockExchange();
		ShareHolder dave = makeShareHolder();
		
		System.out.println(testExchange);
		
		System.out.println();
		System.out.println("*************************************************************************************");
		System.out.println();
		
		testExchange.advanceDay(dave.getSharesOwned());	
		System.out.println(testExchange);
	}
	
	public static void testShareHolder_AddShare() {
		ShareHolder shareHolder = new ShareHolder("Dave", "Joyner", 500.00);
		
		System.out.print(shareHolder);
		
		System.out.println("Buying a single share of SpaceX at $123.45 a share\n");
		String result = shareHolder.addShare(spcx,  1);
		
		System.out.println(result + "\n");
		
		System.out.print(shareHolder);
		
		System.out.println("Buying another share of SpaceX at $123.45 a share\n");
		result = shareHolder.addShare(spcx,  1);
		
		System.out.println(result + "\n");
		
		System.out.print(shareHolder + "\n");
		
		System.out.println("Attempting to buy three more shares of SpaceX at $123.45 a share, testing not having enough money");
		System.out.println("Nothing should happen, the error message should appear and the shareholder report should remain unchanged\n");
		
		result = shareHolder.addShare(spcx,  3);
		System.out.println(result + "\n");
		
		System.out.print(shareHolder + "\n");
		
		System.out.println("Buying 1 Apple stock");
		System.out.println("The wallet balance should update and the report should show both stocks.\n");
		result = shareHolder.addShare(apple, 1);
		System.out.println(result + "\n");
		System.out.print(shareHolder + "\n");
	}
	
	public static void testShareHolder_RemoveShare() {
		ShareHolder shareHolder = new ShareHolder("Dave", "Joyner", 500.00);
		shareHolder.addShare(spcx,  2);
		shareHolder.addShare(apple, 1);	
		
		System.out.print(shareHolder + "\n");
		
		System.out.println("Attempting to sell one share of Apple stock...");
		System.out.println("The stock should disappear from the report and the balance should be $253.10\n");
		String result = shareHolder.removeShare(apple,  1);
		
		System.out.println(result + "\n");
		
		System.out.print(shareHolder + "\n");
		
		System.out.println("Attempting to sell one share of SpaceX stock...");
		System.out.println("The stock quantity should decrement to 1 and the balance should be $376.55\n");
		
		result = shareHolder.removeShare(spcx,  1);
		System.out.println(result + "\n");
		
		System.out.print(shareHolder + "\n");	
		
		System.out.println(">>>>>Attempting to sell 2 shares of SpaceX, one more than we own<<<<<");
		System.out.println(">>>>>The appropriate arror should appear<<<<<");
		
		result = shareHolder.removeShare(spcx,  2);
		System.out.println(result + "\n");
		
		System.out.println(">>>>>Attempting to sell 1 shares of Apple, which we do not own<<<<<");
		System.out.println(">>>>>The appropriate arror should appear<<<<<");
		
		result = shareHolder.removeShare(apple,  1);	
		System.out.println(result + "\n");
		
		System.out.print(shareHolder + "\n");
		
	}
	
	public static void testShareHolder_Report() throws FileNotFoundException {
		StockExchange testExchange = makeStockExchange();
		ShareHolder dave = new ShareHolder("Dave", "Joyner", 500.00);
		
		dave.addShare(spcx,  2);
		dave.addShare(publix, 2);
		
		System.out.println(Analytics.getShareHolderReport(testExchange, dave));
		
		for (int i = 0; i < 15; i++) {
			testExchange.advanceDay(dave.getSharesOwned());
		}
		
		dave.addShare(homeDepot, 2);
		
		for (int i = 0; i < 15; i++) {
			testExchange.advanceDay(dave.getSharesOwned());
		}
		
		System.out.println(Analytics.getShareHolderReport(testExchange, dave));	
	}
	
	public static void testAnalytics() throws FileNotFoundException {
		StockExchange testExchange = makeStockExchange();
		ShareHolder guy = makeShareHolder();
		
		//tests Analytics without any trading		
		System.out.println(Analytics.getShareHolderReport(testExchange, guy));
		
		testExchange.advanceDay(guy.getSharesOwned());
		
		System.out.println("*************************************************************************************");
		System.out.println();
		System.out.println(testExchange);
		System.out.println();
		System.out.println(Analytics.dailyTrend_ByStock(testExchange, testExchange.stockExchange.get(spcx.getSymbol())));
		System.out.println();
		System.out.println(Analytics.overallTrend_ByStock(testExchange, testExchange.stockExchange.get(spcx.getSymbol())));
		
		testExchange.advanceDay(guy.getSharesOwned());
		
		System.out.println("*************************************************************************************");
		System.out.println();
		System.out.println(testExchange);
		System.out.println();
		System.out.println(Analytics.dailyTrend_ByStock(testExchange, testExchange.stockExchange.get(spcx.getSymbol())));
		System.out.println();
		System.out.println(Analytics.overallTrend_ByStock(testExchange, testExchange.stockExchange.get(spcx.getSymbol())));
		
		System.out.println(Analytics.getShareHolderReport(testExchange, guy));
	}

	
	//*****************************************************
	//                      helpers	
	//*****************************************************
	

	private static ShareHolder makeShareHolder() {
		ShareHolder user = new ShareHolder("Dave", "Joyner", 500.00);
		
		user.addShare(spcx, 2);
		user.addShare(homeDepot, 2);
		user.addShare(publix, 2);
		
		return user;
	}
	
	private static StockExchange makeStockExchange() throws FileNotFoundException {
		StockExchange exchange = new StockExchange(new File("src/stockProject/Companies-And-Symbols.txt"));
		
//		exchange.addStock(spcx);
//		exchange.addStock(apple);
//		exchange.addStock(homeDepot);
//		exchange.addStock(publix);
		
		return exchange;		
	}	
}
