package stockProject;

public class CompleteStock { 
	private String company;
	private String symbol;
	private int tradeId;
	private String price;
	private String roc;
	private String oroc;
	
	public CompleteStock(String company, String symbol, int tradeId, String price, String roc, String oroc) {
		this.company = company;
		this.symbol = symbol;
		this.tradeId = tradeId;
		this.price = price;
		this.roc = roc;
		this.oroc = oroc;
	}

	public String getPrice() {
		return price;
	}

	public String getCompany() {
		return company;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getTradeId() {
		return tradeId;
	}

	public String getRoc() {
		return roc;
	}

	public String getOroc() {
		return oroc;
	}
	
	

}
