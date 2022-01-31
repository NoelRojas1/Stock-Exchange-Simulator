package stockProject;

public class Stock {
	
	private String company;
	private int dayPurchased;
	private double originalPrice;
	private double previousPrice;
	private double price;
	private double purchasePrice;
	private int quantity;
	private String symbol;
	private int tradeID;
	
	public Stock(String company, String symbol, double price, int quantity) {
		this.company = company;
		this.symbol = symbol;
		this.price = price;
		this.quantity = quantity;	
		this.tradeID = (int)((Math.random() * 10000000 + 1)); 
		this.previousPrice = price;
		this.originalPrice = price;
	}
	
	//Getters
	public String getCompany() { return company; }
	
	public int getDayPurchased() { return dayPurchased; }
	
	public double getOriginalPrice() { return originalPrice; }
	
	public double getPreviousPrice() { return previousPrice; }
	
	public double getPrice() { return price; }
	
	public double getPurchasePrice() { return purchasePrice; }
	
	public int getQuantity() { return quantity; }
	
	public String getSymbol() { return symbol; }
		
	public int getTradeID() { return tradeID; }
	
	//Setters
	public void setDayPurchased(int day) { this.dayPurchased = day; }
	
	public void setPreviousPrice(double newPrice) { previousPrice = newPrice; }
	
	public void setPrice(double newPrice) { this.price = newPrice; }
	
	public void setQuantity(int newAmount) { this.quantity = newAmount; }
	
	public void setPurchasePrice(double newAmount) { this.purchasePrice = newAmount; }

	
	@Override
	public String toString() {
		String result = "";
		
		result += String.format("%-40s %-30s %s\n", "Issuing Company: " + getCompany(), "SE Symbol: " + getSymbol(), "Trade ID: " + getTradeID()); 
		result += String.format("Current Share Value: $%-17.2f Num Shares Owned: %-11d Current Stock Value: $%.2f\n", getPrice(), getQuantity(), getPrice() * getQuantity());

		return result;
	}
}
