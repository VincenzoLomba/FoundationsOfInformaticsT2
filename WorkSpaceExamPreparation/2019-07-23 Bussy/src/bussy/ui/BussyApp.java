package bussy.ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Map;

import bussy.model.Linea;
import bussy.persistence.BadFileFormatException;
import bussy.persistence.TransportLinesReader;
import bussy.ui.controller.Controller;

public class BussyApp extends Application {

	public void start(Stage stage){
		TransportLinesReader fltRdr = TransportLinesReader.of();
		FileReader fileRdr = null;
		Map<String,Linea> linee = null;
		try {
			fileRdr = new FileReader("Linee.txt");
			linee = fltRdr.readTransportLines(new BufferedReader(fileRdr));
			fileRdr.close();
		}
		catch(IOException e1) {
			Controller.alert("ERRORE IMPREVISTO", "Errore di I/O", "Impossibile accedere al file delle linee di trasporto\n" + e1.getMessage());
			System.exit(1);
		}
		catch(BadFileFormatException e2) {
			Controller.alert("ERRORE IMPREVISTO", "Errore di formato", "Il file delle linee di trasporto è corrotto\n" + e2.getMessage());
			System.exit(1);			
		}
		// --- set up del controller con le linee caricate
		Controller controller = new Controller(linee);
		// --- retrieve available file trackings
		MainPane panel = new MainPane(controller);
		// --- set up scene
		stage.setTitle("Bussy - Viaggia con trasporto!");
		Scene scene = new Scene(panel,Color.WHITE);
		stage.setScene(scene);
		stage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
}
