package battleship.controller;

import java.util.Map;

import battleship.model.ShipItem;
import battleship.model.ShipType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public interface Controller {
	
	public int getSize();
	public ShipItem getCurrentCellItem(int row, int col);
	public void setCurrentCellItem(int row, int col, ShipItem value);
	public boolean isGameOver();
	public String getShipList();
	public int[] getCountingRow();
	public int[] getCountingColumn();
	public int verify();
	public Map<ShipType, Integer> getShipCount();
	public ShipItem getSolutionCellItem(int row, int col);
	
	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
}
