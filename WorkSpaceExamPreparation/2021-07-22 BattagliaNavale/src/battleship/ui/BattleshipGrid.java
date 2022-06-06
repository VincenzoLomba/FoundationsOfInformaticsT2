package battleship.ui;

import java.util.Optional;

import battleship.controller.Controller;
import battleship.model.ShipItem;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class BattleshipGrid extends GridPane {

	class CellButton extends Button {
		int row, col;
		
		public CellButton(String txt, int row, int col) {
			super(txt);
			this.row=row;
			this.col=col;
		}
		
		public CellButton(int row, int col) {
			super();
			this.row=row;
			this.col=col;
		}

		public int getRow() {
			return this.row;
		}
		
		public int getColumn() {
			return this.col;
		}

	}
	
	private int size, tot;
	private CellButton[][] grid;  
	private Controller controller;
	private ComboBox<ShipItem> combo;
	
	public BattleshipGrid(Controller controller, ComboBox<ShipItem> combo) {
		this.setPrefSize(600, 500);
		this.size = controller.getSize();
		tot = size*size;
		this.controller = controller;
		this.combo = combo;
		//
		grid = new CellButton[size+1][size+1];
		for(int k=0; k<tot; k++) {
			int row=k/size, col=k%size;
			grid[row][col]=new CellButton(row,col);
			grid[row][col].setStyle("-fx-background-color: WHITE");
			grid[row][col].setStyle("-fx-border-width: 1;");
			grid[row][col].setOnAction(this::myHandle);
			add(grid[row][col],col,row,1,1);
		}
		//
		setHintColumn(controller.getCountingColumn());
		setHintRow(controller.getCountingRow());
		updateGridStatus();
	}
	
	private void setHintColumn(int[] values) {
		for(int k=0; k<size; k++) {
			grid[size][k] = new CellButton(String.valueOf(values[k]), size, k);
			grid[size][k].setDisable(true);
			grid[size][k].setPrefSize(60, 60);
			grid[size][k].setFont(Font.font("Arial",20));
			add(grid[size][k],size,k,1,1);
		}
	}

	private void setHintRow(int[] values) {
		for(int k=0; k<size; k++) {
			grid[k][size] = new CellButton(String.valueOf(values[k]), k, size);
			grid[k][size].setDisable(true);
			grid[k][size].setPrefSize(65, 60);
			grid[k][size].setFont(Font.font("Arial",20));
			add(grid[k][size],k,size,1,1);
		}
	}

	public void updateGridStatus() {
		for(int k=0; k<tot; k++) {
			int row=k/size, col=k%size;
			//grid[row][col].setText(controller.getCellItem(row,col).getValue());
			Optional<Image> optImage = controller.getCurrentCellItem(row,col).getImage();
			if (optImage.isPresent()) {
				grid[row][col].setGraphic(new ImageView(optImage.get()));
			}
			else {
				grid[row][col].setPrefSize(70, 60);
				grid[row][col].setText("?");
			}
		}
	}

	private void myHandle(ActionEvent e) {
		ShipItem value = combo.getValue();
		CellButton source = (CellButton)e.getSource(); 
		source.setGraphic(new ImageView(value.getImage().get()));
		controller.setCurrentCellItem(source.getRow(), source.getColumn(), value);
	}

}
