package application;


import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import stockProject.Analytics;
import stockProject.CompleteStock;
import stockProject.Stock;
import stockProject.StockExchange;

public class StockExchangeWindowController implements Initializable {
	
	@FXML private TableView<CompleteStock> table;
    @FXML private TableColumn<CompleteStock, String> companyColumn;
    @FXML private TableColumn<CompleteStock, String> symbolColumn;
    @FXML private TableColumn<CompleteStock, Integer> tradeIdColumn;
    @FXML private TableColumn<CompleteStock, String> priceColumn;
    @FXML private TableColumn<CompleteStock, String> rocColumn;
    @FXML private TableColumn<CompleteStock, String> orocColumn;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		companyColumn.setCellValueFactory(new PropertyValueFactory<CompleteStock, String>("company"));
		symbolColumn.setCellValueFactory(new PropertyValueFactory<CompleteStock, String>("symbol"));
		tradeIdColumn.setCellValueFactory(new PropertyValueFactory<CompleteStock, Integer>("tradeId"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<CompleteStock, String>("price"));
		rocColumn.setCellValueFactory(new PropertyValueFactory<CompleteStock, String>("roc"));
		orocColumn.setCellValueFactory(new PropertyValueFactory<CompleteStock, String>("oroc"));
		table.setItems(populateTable());
	}
	
	public ObservableList<CompleteStock> populateTable() {
		LinkedList<CompleteStock> comSto = new LinkedList<>();
		StockExchange se = Main.getStockExchange();
		for(Stock stock : se.getStockExchange().values()) {
			String dailyRate = String.format("%.3f", Analytics.daily_RateOfChange(stock));
			String overallRate = String.format("%.3f", Analytics.overall_RateOfChange(stock));
			String price = String.format("$%.2f", stock.getPrice());
			comSto.add(new CompleteStock(stock.getCompany(), stock.getSymbol(), stock.getTradeID(), price, dailyRate, overallRate));
		}
		
		ObservableList<CompleteStock> stocks = FXCollections.observableArrayList();
		try {
			Iterator<CompleteStock> stcks = comSto.iterator();
			while(stcks.hasNext()) {
				CompleteStock current = stcks.next();
				stocks.add(current);
			}
		}
		catch(Exception e) {
			
		}
		return stocks; 
	}

}