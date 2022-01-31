package application;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.TreeMap;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import stockProject.Analytics;
import stockProject.ShareHolder;
import stockProject.Stock;
import stockProject.StockExchange;

/**
 * 
 * @author noelrojas
 *
 */
public class AnalyticsController implements Initializable {

    @FXML private Label overallTrend;
    @FXML private Label dailyTrend;
    @FXML private Label shareHolderAnalytics;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		Analytics ana = new Analytics();
		StockExchange se = Main.getStockExchange();
		ShareHolder shareHolder = MainController.getShareHolder();
		TreeMap<String, Stock> tm = shareHolder.getSharesOwned();
		buildOverallAndDailyTrends(se, tm);
		buildShareHJolderInfo(se, shareHolder);
		
	}

	private void buildOverallAndDailyTrends(StockExchange se, TreeMap<String, Stock> tm) {
		String overallTrends = "";
		String dailyTrends = "";
		Iterator<Stock> trendsIter = tm.values().iterator();
		while(trendsIter.hasNext()) {
			Stock currentStock = trendsIter.next();
			overallTrends += Analytics.overallTrend_ByStock(se, currentStock) + "\n";
			dailyTrends += Analytics.dailyTrend_ByStock(se, currentStock) + "\n";
			
		}
		overallTrend.setText(overallTrends);
		dailyTrend.setText(dailyTrends);
	}
	
	private void buildShareHJolderInfo(StockExchange se, ShareHolder shareHolder) {
		shareHolderAnalytics.setText(Analytics.getShareHolderReport(se, shareHolder));
	}
    
}

