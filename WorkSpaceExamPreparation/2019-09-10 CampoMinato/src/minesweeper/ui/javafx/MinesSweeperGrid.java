package minesweeper.ui.javafx;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import minesweeper.model.GameStatus;
import minesweeper.ui.controller.Controller;

public class MinesSweeperGrid extends GridPane {

	private int size, tot;
	private Button[][] grid;  
	private Controller controller;
	private TextArea output;
	
	public MinesSweeperGrid(Controller controller, TextArea output) {
		this.size=controller.getSize();
		tot = size*size;
		this.controller = controller;
		this.output=output;
		grid = new Button[size][size];
		for(int k=0; k<tot; k++) {
			int row=k/size, col=k%size;
			grid[row][col]=new Button();
			grid[row][col].setStyle("-fx-background-color: GREY");
			grid[row][col].setFont(Font.font("Courier New", FontWeight.BOLD, 16));
			grid[row][col].setText(" ");
			grid[row][col].setOnAction(ev -> handle(row,col));
			add(grid[row][col],col,row,1,1);
		}
	}
	
	public void updateGridStatus(String gridStatus) {
		output.setText("Current game status:\n");
		output.appendText(gridStatus);
		String[] items = gridStatus.split("\t+|\n");
		for(int k=0; k<tot; k++) {
			final int row=k/size, col=k%size;
			grid[row][col].setText(items[k].trim());
		}
	}
	
	private void handle(int row, int col) {
		GameStatus status = controller.revealCell(row, col);
		updateGridStatus(controller.getPlayerMineField());
		switch(status) {
			case WON: 
				output.appendText("YOU WIN!\n" + controller.getRealMineField());
				endGame();
				break;
			case EXPLODED: 
				output.appendText("YOU WERE BLOWN UP!\n"+controller.getRealMineField());
				endGame();
				break;
			case CONTINUING: 
				output.appendText("CONTINUE\n");
		}
	}
	
	public void endGame() {
		for(int k=0; k<tot; k++) {
			final int row=k/size, col =k%size;
			grid[row][col].setDisable(true);
		}
		controller.save();
		controller.close();
	}

}
