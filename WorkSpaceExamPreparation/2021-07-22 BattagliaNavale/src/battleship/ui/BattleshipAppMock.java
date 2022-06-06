package battleship.ui;


import battleship.controller.Controller;
import battleship.controller.MyController;
import battleship.model.ShipBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BattleshipAppMock extends Application {

	private Controller controller;

	public void start(Stage stage) {
		
		ShipBoard solutionBoard = new ShipBoard(
				  "~ < x x > ~ ~ ^\n"
				+ "~ ~ ~ ~ ~ ~ ~ v\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\n"
				+ "~ x ~ v ~ ~ < >\n"
				+ "~ v ~ ~ ~ ~ ~ ~\n"
				+ "~ ~ ~ o ~ ~ ~ ~\n"
				+ "~ ~ ~ ~ ~ < x >\n"
				+ "~ o ~ o ~ ~ ~ ~");
		
		ShipBoard initialBoard = new ShipBoard(
				 "# # # # > # # ^\n"
			   + "# # # # # # # #\n"
			   + "# # # # # # # #\n"
			   + "# x # # # # < #\n"
			   + "# # # # # # # #\n"
			   + "# # # # # # # #\n"
			   + "# # # # # # # #\n"
			   + "# # # # # # # #");

		controller = new MyController(solutionBoard,initialBoard);
		if (controller == null) {
			alert("Errore irrecuperabile", "Errore nella creazione del controller", "Addio");
			System.exit(1);
		}
		stage.setTitle("Battaglia navale - MOCK");
		MainPane root = new MainPane(controller, stage);
		
		Scene scene = new Scene(root, 820, 550, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

}
