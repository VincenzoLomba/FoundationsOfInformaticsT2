package minesweeper.ui.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import minesweeper.model.GameStatus;

public interface Controller {
	
	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	void save();
	void close();

	int getMinesNumber();
	int getSize();
	String getPlayerMineField();
	String getRealMineField();
	GameStatus revealCell(int row, int col);

	void restart();

}
