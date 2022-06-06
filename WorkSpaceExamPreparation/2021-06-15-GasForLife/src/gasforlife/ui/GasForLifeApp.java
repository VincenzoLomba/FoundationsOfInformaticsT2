package gasforlife.ui;
import java.io.FileReader;
import java.io.IOException;

import gasforlife.controller.Controller;
import gasforlife.controller.MyController;
import gasforlife.persistence.BadFileFormatException;
import gasforlife.persistence.ConsumptionReader;
import gasforlife.persistence.MyConsumptionReader;
import gasforlife.persistence.FlatReader;
import gasforlife.persistence.MyFlatReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GasForLifeApp extends Application  {
	private Controller controller;

	public void start(Stage stage) {
		try {
			FlatReader flatReader = new MyFlatReader(new FileReader("flat.txt"));
			ConsumptionReader consReader = new MyConsumptionReader(new FileReader("consumption.txt"));
			controller = new MyController(flatReader,consReader);
			System.out.println("Configuration read");
		} catch ( BadFileFormatException  | IOException e) {
			alert("File non trovato", "Errore nella creazione del controller", "Addio");
			System.exit(1);
		} 
		stage.setTitle("GasForLife");
		MainPane root = new MainPane(controller, stage);
		Scene scene = new Scene(root, 1050, 500, Color.ALICEBLUE);
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