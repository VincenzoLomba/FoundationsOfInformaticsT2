package taxcomparator.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import taxcomparator.controller.Controller;
import taxcomparator.model.CalcolatoreImposta;
import taxcomparator.model.Fasce;
import taxcomparator.model.MyCalcolatoreImposta;
import taxcomparator.persistence.BadFileFormatException;
import taxcomparator.persistence.FasceReader;
import taxcomparator.persistence.MyFasceReader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class TaxComparatorApp extends Application {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Tax Comparator");
	
		FasceReader reader = new MyFasceReader();
		Fasce fasceAttuali, fasceRiforma;
		try {
			fasceAttuali = reader.readFasce("fasce 2021", new FileReader("FasceAttuali.txt"));
			fasceRiforma = reader.readFasce("fasce 2022", new FileReader("FasceRiforma.txt"));
			
			CalcolatoreImposta calc = new MyCalcolatoreImposta();
			
			Controller controller = new Controller(calc, List.of(fasceAttuali,fasceRiforma));			
				
			MainPane mainPanel = new MainPane(controller);
			Scene scene = new Scene(mainPanel, 500, 400, Color.AQUAMARINE);
			stage.setScene(scene);
			stage.show();
		}	
		catch (IOException e) {
			alert(
					"ERRORE DI I/O",
					"Errore di lettura: impossibile leggere i dati",
					"Dettagli: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			alert(
					"ERRORE NEI PARAMETRI",
					"Errore negli argomenti interni del file: impossibile leggere i dati",
					"Dettagli: " + e.getMessage());
		} catch (BadFileFormatException e) {
			alert(
					"ERRORE NEI FORMATO DEL FILE",
					"Formato dei file errato: impossibile fare il parsing dei dati",
					"Dettagli: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}


}
