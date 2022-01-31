package stockProject;

import java.util.Map;
import java.util.TreeMap;

public class ShareHolder {
	
	private TreeMap<String, Stock> sharesOwned;  
	private String firstName;
	private String lastName;
	private double balance;
	
	public ShareHolder(String firstName, String lastName, double balance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;		
		this.sharesOwned = new TreeMap<String, Stock>();  
	}
	
	//**************************Getters*********************************
	public TreeMap<String, Stock> getSharesOwned() { return sharesOwned; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public double getBalance() { return balance; }
	
	//will return a Stock with a value of null if the user does not own the stock
	public Stock getUserOwnedStock(String symbol) {
		if (sharesOwned.keySet().contains(symbol)) {
			return sharesOwned.get(symbol);
		}
		else
			return null;	
	}
	
	
	//**************************Setters*********************************
	//for buying shares of stock
	public String addShare(Stock stock, int numToAdd) {		
		String key = stock.getSymbol();
		double costToBuy = stock.getPrice() * numToAdd;
		
		if (balance >= costToBuy) { //the user has the balance to afford purchase
			balance -= costToBuy; //pay for purchase
			
			//if user already owns the stock in question
			if (sharesOwned.keySet().contains(key)) { 
				Stock ownedStock = sharesOwned.get(key); //gets the owned stock
				int numOwnedStock = ownedStock.getQuantity(); //gets the number of owned shares
				ownedStock.setQuantity(numOwnedStock + numToAdd); //adds the number of owned + purchased and sets this new amount
				//purchasePrice is not updated
				//dayPurchased is not updated
				return "Your purchase of " + numToAdd + " additional shares of " + ownedStock.getCompany() + "'s stock is successful!"; 
			}
			//else user does not already own this stock
			else {
				sharesOwned.put(key, stock); //add stock to user's profile
				stock.setQuantity(numToAdd); //sets the amount to the number owned
				stock.setPurchasePrice(stock.getPrice()); //records purchasePrice for analytics
				stock.setDayPurchased(StockExchange.getNumDays());
				return "Your purchase of " + numToAdd + " shares of " + sharesOwned.get(key).getCompany() + "'s stock was completed!"; 
			}
		}
		else { //user does not have enough money
			return "You cannot make this purchase as your funds are too low!";
		}
	}
	
	//for selling shares of stock
	public String removeShare (Stock stock, int numSold) { 
		String key = stock.getSymbol();
		
		//if stock is already owned
		if (sharesOwned.keySet().contains(key)) {		
			Stock ownedStock = sharesOwned.get(key); //gets the owned stock		
			int numOwned = ownedStock.getQuantity(); //get number of already owned shares of stock
			//if user has enough shares to complete sale
			if (numSold <= numOwned) { //if the user has enough stock to make the sale
				//get current value of stock
				double currentPrice = stock.getPrice();
				//get money made from sale
				double profit = numSold * currentPrice;
				//update balance with result
				balance += profit;
				//update quantity owned
				ownedStock.setQuantity(numOwned - numSold);	
				
				if (ownedStock.getQuantity() < 1) { //removes the stock from the profile if the amount owned is less than 0
					sharesOwned.remove(key, stock);
					return "The sale of the remainder of your shares in " + ownedStock.getCompany() + " at $" + currentPrice + " was completed!";
				}
				return "The sale of " + numSold + " shares of " + ownedStock.getCompany() + " at $" + currentPrice + " was successful!";
			}
			//else the user does not own enough shares to complete sale
			else {
				return "This sale order could not be completed as you do not own enough shares!";
			}
		}
		//else user does not own the stock to be sold
		else {
			return "You do not own any shares in this company!!!";			
		}
	}
	
	
	@Override
	public String toString() {
		String result = "";
		
		result += String.format("ShareHolder: %s %s\n", getFirstName(), getLastName());
		result += String.format("Wallet Balance: $%.2f\n", getBalance());
		result += String.format("Total Share Value: $%.2f\n", getSharesTotalValue());
		result += "\n" + getSharesReport();
		
		return result;
	}
	
	
	//*****************************************************
	//                      helpers	
	//*****************************************************
	
	private String getSharesReport() { 
		String result = "";
		
		for (Map.Entry<String, Stock> entry : sharesOwned.entrySet()) {
			Stock stock = entry.getValue();		
			result += stock.toString() + "\n";			
		}
		
		return result;
	}
	
	public double getSharesTotalValue() {
		double result = 0.0;
		
		for (Map.Entry<String, Stock> entry : sharesOwned.entrySet()) {
			Stock stock = entry.getValue();		
			result += stock.getPrice() * stock.getQuantity();			
		}

		return result;
	}
}
