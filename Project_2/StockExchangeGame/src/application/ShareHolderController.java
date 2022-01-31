package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import stockProject.ShareHolder;

/**
 * 
 * @author noelrojas
 *
 */
public class ShareHolderController implements Initializable {

    @FXML  private Label shareHolderInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ShareHolder shareHolder = MainController.getShareHolder();
		shareHolderInfo.setText(shareHolder.toString());
	}
}