package minesweeper.ui.javafx;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import minesweeper.ui.controller.Controller;

public class MinesSweeperPane extends BorderPane {
	
	private Controller controller;
	private MinesSweeperGrid grid;
	private TextArea output;
	private Button restart;
	private Button save;
	
	public MinesSweeperPane (Controller controller, Stage stage) {
		
		this.controller = controller;
		
		setTop(new Label("Mines: " + this.controller.getMinesNumber()));
		
		output = new TextArea();
		output.setPrefColumnCount(60);
		output.setPrefRowCount(10);
		output.setFont(Font.font("Courier New", FontWeight.NORMAL, 12));
		setRight(output);
		
		HBox bottom = new HBox();
		save = new Button("Save");
		save.setOnAction(this::save);
		restart = new Button("Restart");
		restart.setOnAction(this::restart);
		bottom.getChildren().addAll(save, restart);
		setBottom(bottom);
		
		grid = new MinesSweeperGrid(controller, output);
		setCenter(grid);
		
	}

	
	private void save (ActionEvent ae) {
		controller.save();
	}
	
	private void restart (ActionEvent ae) {
		controller.restart();
		output.setText("");
		grid = new MinesSweeperGrid(controller, output);
		setCenter(grid);
	}
}
