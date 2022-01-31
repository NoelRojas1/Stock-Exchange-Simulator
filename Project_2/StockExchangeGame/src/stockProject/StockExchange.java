package stockProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class StockExchange {
	
	protected TreeMap<String, Stock> stockExchange = new TreeMap<String, Stock>();
//	protected LinkedList<CompleteStock> stockList = new LinkedList<>();
	protected static int numDays = 1; //starts at Day One, updates after Advancer fires
	protected static File file;

	//Constructor
		public StockExchange (File file) throws FileNotFoundException {
			this.file = file;
			makeStockExchange();
		}

		//Getters
		public static int getNumDays() { return numDays; }

		@Override
		public String toString() { 
			String result = "";
			String separateLines = "________________________________________|____________________|_____________|________________________|_________________________|__________________________|\n";
			result += String.format("Dasgupta Stock Exchange Current Values as of Day %d\n", numDays);
			result += String.format("####################################################\n\n");
			result += String.format("%s                        |  %s  |  %s  |  %s  |  %s  |  %s\n","Issuing Company:","Exchange Symbol:","Trade ID:", "Current Share Value:", "Daily Rate of Change:", "Overall Rate of Change:");
			result += separateLines;
			for (Map.Entry<String, Stock> entry : stockExchange.entrySet()) {
				Stock stock = entry.getValue();		
				double rateOfChange = Analytics.daily_RateOfChange(stock);
				double overallRateofChange = Analytics.overall_RateOfChange(stock);
				result += String.format("   %-43s  %-15s  %-18d  $%-21.2f  %%%-23.2f  %%%-6.2f\n", stock.getCompany(), stock.getSymbol(), stock.getTradeID(), stock.getPrice(), rateOfChange, overallRateofChange);
				result += separateLines;
				
			}	
			return result;
		}
	
	
	//*****************************************************
	//                      helpers	
	//*****************************************************
	
		public void makeStockExchange() throws FileNotFoundException {
			boolean fileExists = file.exists();
			if(fileExists) {
				try {
					Scanner input = new Scanner(file);
					while(input.hasNext()) {
						String company = input.nextLine();
						String companyName = company.substring(0, company.lastIndexOf(" "));
						String symbol = company.substring(company.lastIndexOf(" ") + 1);
						double price = generatePrice();
						Stock stock = new Stock(companyName, symbol, price, 0);
						addStock(stock);
					}
					input.close();
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
					System.out.println(e.getStackTrace());
				}
			}
		}
		
		private static int generatePrice() {
			return (int)(Math.random() * 130 + 1);
		}

	
	//Used to add stock to the exchange, helper method for the admins only!!!
	protected void addStock(Stock stock) { //can be made private once the makeStockExchange method is built
		if (!stockExchange.keySet().contains(stock.getSymbol())) {
			stockExchange.put(stock.getSymbol(), stock);
		}
		else { //else stock already exists in the exchange
			//no error message is required as this is an admin function only
		}
	}
	
	//simulates a day's trading
	public void advanceDay(TreeMap<String, Stock> sharesOwned) {
		int coinToss = coinToss();	
		priceChanger(coinToss);	
		stockMX(sharesOwned);
		numDays++;
	}
	
	public void stockMX(TreeMap<String, Stock> sharesOwned) {
		for (Map.Entry<String, Stock> entry : stockExchange.entrySet()) {
			Stock stock = entry.getValue();	
			if (stock.getPrice() <= 0.0) {
				stockExchange.remove(stock.getSymbol());
				sharesOwned.remove(stock.getSymbol());
			}
		}		
	}
	
	public void priceChanger(int n) {
		for (Map.Entry<String, Stock> entry : stockExchange.entrySet()) {
			Stock stock = entry.getValue();
			int id = stock.getTradeID();
			double currentValue = stock.getPrice();
			
			//determines changeInPrice value
			int coinToss = coinToss();	
			double changeInPrice = (Math.random() * 4 + 1)/100;	
			if (coinToss == 1) {
				changeInPrice *= -1;
			}
			
			if ((n ==  1 && id >= 3500000) || (n == 2 && id <= 6500000)) {
				double value = (currentValue * changeInPrice);
				stock.setPreviousPrice(currentValue);
				stock.setPrice(round((currentValue + value),2));
			}	
		}	
	}

	public TreeMap<String, Stock> getStockExchange() {
		return stockExchange;
	}

	private static int coinToss() {
		return (int)((Math.random() * 2 + 1));
	}
	
	private double round(double amount, int decimals) {
		String str = String.format("%." + decimals +"f", amount);
		return Double.parseDouble(str);
	}
}
