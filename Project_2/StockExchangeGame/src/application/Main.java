package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import stockProject.ShareHolder;
import stockProject.StockExchange;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * This Main class launches the application
 * 
 * @author noelrojas
 *
 */
public class Main extends Application {
	private static ShareHolder shareHolder;
	
    @FXML private TextField lastName;
    @FXML private TextField amount;
    @FXML private TextField name;
    @FXML private Label nameWarning;
    @FXML private Label lastNameWarning;
    @FXML private Label amountWarning;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Registration");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static StockExchange getStockExchange() {
		return StockExchangeController.getStockExchange();
//		return se;
	}
	
	public static ShareHolder getShareHolder() {
		return shareHolder;
	}
}
