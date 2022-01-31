package stockProject;

import java.util.Map;
import java.util.TreeMap;

public class Analytics {
	
	public static String dailyTrend_ByStock(StockExchange stockExchange, Stock stock) {
		String key = stock.getSymbol();	
		double currentPrice = stock.getPrice();
		double previousPrice = stock.getPreviousPrice();	
		double changeInPrice = currentPrice - previousPrice;
		double growthPercentage = (currentPrice/previousPrice)-1;
		
		String result = "Daily shareprice performance for " + key + "\n";
		result += String.format("Yesterday's Value: $%-10.2f Current Value: $%-10.2f\n", previousPrice, currentPrice);
		result += String.format("Net change in value: $%-10.2f Growth Percentage(%%): %.2f", changeInPrice, growthPercentage*100);
		return result;		
	}

	public static double daily_RateOfChange(Stock stock) {
		return ((stock.getPrice()/stock.getPreviousPrice())-1)*100;
	}
	
	public static double overall_RateOfChange(Stock stock) {
		return ((stock.getPrice()/stock.getOriginalPrice())-1)*100;
	}
	
	public static String overallTrend_ByStock(StockExchange stockExchange, Stock stock) { //for the ShareHolder report
		String result = "";
		double currentPrice = stock.getPrice();
		String key = stock.getSymbol();
		
		double purchasePrice = stock.getPurchasePrice();
		
		double changeInPrice = currentPrice - purchasePrice;
		double growthPercentage = (currentPrice/purchasePrice)-1;
		int numDays = StockExchange.getNumDays() - stock.getDayPurchased();
		
		result += "Overall shareprice performance for " + key + " over " + numDays + " days\n";
		result += String.format("Purchase Price: $%-10.2f Current Value: $%10.2f\n", purchasePrice, currentPrice);
		result += String.format("Net change in value: $%-10.2f Growth Percentage(%%): %.2f\n", changeInPrice, growthPercentage*100);
		
		return result;
	}

	public static String getShareHolderReport(StockExchange stockExchange, ShareHolder shareHolder) {
		TreeMap<String, Stock> sharesOwned = shareHolder.getSharesOwned();
		String result = "";	
		
		result += String.format("ShareHolder: %s %s\n", shareHolder.getFirstName(), shareHolder.getLastName());
		result += String.format("Wallet Balance: $%.2f      Total Share Value: $%.2f\n\n", shareHolder.getBalance(), shareHolder.getSharesTotalValue());

		for (Map.Entry<String, Stock> entry : sharesOwned.entrySet()) {
			Stock stock = entry.getValue();		
			result += stock.toString();
			result += "*****************************************************************************************************\n";
			result += overallTrend_ByStock(stockExchange, stock) + "\n";			
		}

		return result;
	}
}
