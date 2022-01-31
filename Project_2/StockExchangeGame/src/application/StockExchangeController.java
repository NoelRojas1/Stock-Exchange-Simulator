package application;


import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stockProject.ShareHolder;
import stockProject.Stock;
import stockProject.StockExchange;

/**
 * 
 * @author noelrojas
 *
 */
public class StockExchangeController implements Initializable {
	private static StockExchange se = Main.getStockExchange();
	private ShareHolder shareHolder = MainController.getShareHolder();
	Alert a = new Alert(AlertType.NONE);
	
	@FXML private BorderPane mainPane;
	@FXML private TableView<Stock> table;
	@FXML private TableColumn<Stock, String> companyColumn;
	@FXML private TableColumn<Stock, String> symbolColumn;
	@FXML private TableColumn<Stock, Double> priceColumn;
	@FXML private TextField txtBuySell;
	@FXML private Label name;
	@FXML private Label wallet;
	@FXML private Label sharesValue;
	@FXML private Label lblWarning;
	@FXML private Label lblAdvanceSuccess;
	@FXML private MenuItem analytics;
	@FXML private TextArea overallTrend;
	@FXML private TextArea dailyTrend;
	@FXML private TextArea shareHolderAnalytics;
	@FXML private Label txtCustomAdvanceSupply;
	@FXML private TextField txtCustomAdvance;
	
	
	public ObservableList<Stock> populateTable() {
		ObservableList<Stock> stocks = FXCollections.observableArrayList();
		try {
			se = new StockExchange(new File("src/stockProject/Companies-And-Symbols.txt"));
			Iterator<Stock> stcks = se.getStockExchange().values().iterator();
			while(stcks.hasNext()) {
				Stock current = stcks.next();
				stocks.add(current);
			}
		}
		catch(Exception e) {
			
		}
		return stocks;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		companyColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("company"));
		symbolColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("symbol"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>("price"));
		table.setItems(populateTable());
		
		name.setText("Share Holder: " + shareHolder.getFirstName() + " " + shareHolder.getLastName());
		wallet.setText(String.format("Wallet: $%.2f", shareHolder.getBalance()));
		sharesValue.setText(String.format("Shares Value: $%.2f", shareHolder.getSharesTotalValue()));
	}
	
	 @FXML
     void onClickAnalytics(ActionEvent event) {
		 try {
			AnchorPane root1 = (AnchorPane) FXMLLoader.load(Main.class.getResource("/application/Analytics.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Analytics");
			stage.setScene(new Scene(root1));
			stage.show();
		} 
		 catch(Exception e) {
			System.out.println("Something went wrong... " + e.toString());
			}
     }
	 
     @FXML
     void onClickShareHolder(ActionEvent event) {
       	try {
			AnchorPane root1 = (AnchorPane) FXMLLoader.load(Main.class.getResource("/application/ShareHolder.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Share Holder Info");
			stage.setScene(new Scene(root1));
			stage.show();
		} 
		 catch(Exception e) {
			System.out.println("Something went wrong... " + e.toString());
		}
     }
	    
	 @FXML
	 void onClickStockExchange(ActionEvent event) {
		 try {
				AnchorPane root1 = (AnchorPane) FXMLLoader.load(Main.class.getResource("/application/StockExchangeWindow.fxml"));
				Stage stage = new Stage();
				stage.setTitle("Stock Exchange");
				stage.setScene(new Scene(root1));
				stage.show();
			} 
			 catch(Exception e) {
				System.out.println("Something went wrong... " + e.toString());
				}
	 }

     @FXML
     void onClickBuy(MouseEvent event) {
    	 if(!txtBuySell.getText().equals("")) {
 			lblWarning.setVisible(false);
 			int sharesToBuy = Integer.parseInt(txtBuySell.getText());
 			Stock stock = table.getSelectionModel().getSelectedItem();
 			shareHolder.addShare(stock, sharesToBuy);
 			name.setText("Share Holder: " + shareHolder.getFirstName() + " " + shareHolder.getLastName());
 			wallet.setText(String.format("Wallet: $%.2f", shareHolder.getBalance()));
 			sharesValue.setText(String.format("Shares Value: $%.2f", shareHolder.getSharesTotalValue()));
 			txtBuySell.clear();
 		}
 		else {
 			lblWarning.setVisible(true);
 		}
 		
     }

     @FXML
     void onClickSell(MouseEvent event) {
    	 a.setAlertType(AlertType.CONFIRMATION);
     	 a.setContentText("Are you sure you want to sell?");
     	 a.showAndWait().ifPresent(response -> {
     		 if(response == ButtonType.OK) {
     			if(!txtBuySell.getText().equals("")) {
     	  			lblWarning.setVisible(false);
     	  			int sharesToSell = Integer.parseInt(txtBuySell.getText());
     	  			Stock stock = table.getSelectionModel().getSelectedItem();
     	  			shareHolder.removeShare(stock, sharesToSell);
     	 			wallet.setText(String.format("Wallet: $%.2f", shareHolder.getBalance()));
     	 			sharesValue.setText(String.format("Shares Value: $%.2f", shareHolder.getSharesTotalValue()));
     	  			txtBuySell.clear();
     	  		}
     	  		else {
     	  			lblWarning.setVisible(true);
     	  		}
     		 }
     	 });
     }

     @FXML
     void advance1Day(ActionEvent event) {
    	 commonAdvanceDays(1);
     }
     
     
     @FXML
     void advance14Days(ActionEvent event) {
    	 commonAdvanceDays(14);
     }
     

     @FXML
     void advance30Days(ActionEvent event) {
    	 commonAdvanceDays(30);
     }

     @FXML
     void advance90Days(ActionEvent event) {
    	 commonAdvanceDays(90);
     }
     
     private void commonAdvanceDays(int days) {
    	 for(int x = 0; x < days; x++) {
    		 se.advanceDay(shareHolder.getSharesOwned());
    	 }
		 wallet.setText(String.format("Wallet: $%.2f", shareHolder.getBalance()));
		 sharesValue.setText(String.format("Shares Value: $%.2f", shareHolder.getSharesTotalValue()));
		 
		 
		 a.setAlertType(AlertType.INFORMATION);
		 a.setContentText("Advanced " + days + " day(s) successfully!");
		 a.show();
     }
     
     @FXML
     void customAdvance(ActionEvent event) {
    	 if(!txtCustomAdvance.getText().equals("")) {
    		 txtCustomAdvanceSupply.setVisible(false);
    		 String daysToAdvance = txtCustomAdvance.getText();
    		 commonAdvanceDays(Integer.parseInt(daysToAdvance));
    	 }
    	 else {
    		 txtCustomAdvanceSupply.setVisible(true);
    	 }
     }


    public static StockExchange getStockExchange() {
    	return se;
    }
}
