package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import stockProject.ShareHolder;

/**
 * 
 * @author noelrojas
 *
 */
public class MainController implements Initializable {

	private static ShareHolder shareHolder;
	
    @FXML private TextField lastName;
    @FXML private TextField amount;
    @FXML private TextField name;
    @FXML private Label nameWarning;
    @FXML private Label lastNameWarning;
    @FXML private Label amountWarning;
    @FXML private Button start;

    @FXML
    void onClickStart(ActionEvent event) {
    	String holderName = name.getText();
    	String holderLastName = lastName.getText();
    	String holderAmount = amount.getText();
    	
    	if(holderName.equals(""))
    		nameWarning.setVisible(true);
    	if(holderLastName.equals(""))
    		lastNameWarning.setVisible(true);
    	if(holderAmount.equals(""))
    		amountWarning.setVisible(true);
    	else {
    		nameWarning.setVisible(false);
    		lastNameWarning.setVisible(false);
    		amountWarning.setVisible(false);
    		
    		shareHolder = new ShareHolder(holderName, holderLastName, Double.parseDouble(holderAmount));
    		
    		try {
    			AnchorPane root1 = (AnchorPane)FXMLLoader.load(Main.class.getResource("/application/StockExchange.fxml"));
    			Stage stage = (Stage)start.getScene().getWindow();
    			stage.setTitle("Main");
    			Scene scene = new Scene(root1);
    			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    			stage.setScene(scene);
    			stage.show();
    		} 
    		 catch(Exception e) {
    			System.out.println("Something went wrong... " + e);
    		}
    	}
    		
    }
    
	public static ShareHolder getShareHolder() {
		return shareHolder;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}