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

public class BussyAppMock extends Application {

	private BufferedReader fakeRdr;	
	
	public void start(Stage stage){
		fakeRdr = new BufferedReader(new StringReader(
			"Linea 32\r\n" + 
			"0,  40, Porta San Mamolo \r\n" + 
			"3,  42, Aldini\r\n" + 
			"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
			"17, 16, Stazione Centrale\r\n" + 
			"38, 40, Porta San Mamolo\r\n" + 
			"--------------------------\r\n" + 
			"Linea 33\r\n" + 
			" 0, 47, Porta Saragozza - Villa Cassarini\r\n" + 
			" 2, 49, Aldini\r\n" + 
			" 3, 45, Petrarca\r\n" + 
			" 5, 43, Porta San Mamolo\r\n" + 
			"26, 09, Stazione Centrale\r\n" + 
			"38, 47, Porta Saragozza - Villa Cassarini\r\n" + 
			"--------------------------\r\n" + 
			"Linea M1\r\n" + 
			"0, 40, Porta San Mamolo\r\n" + 
			"3, 803, Tribunale\r\n" + 
			"5, 701, Piazza Malpighi\r\n" + 
			"7, 452, Marconi\r\n" + 
			"10, 474, Stazione Centrale\r\n" + 
			"--------------------------"));	
		TransportLinesReader fltRdr = TransportLinesReader.of();
		Map<String,Linea> linee = null;
		try {
			linee = fltRdr.readTransportLines(new BufferedReader(fakeRdr));
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
		stage.setTitle("Bussy MOCK - Avremmo viaggiato con trasporto, se avessimo letto i file..");
		Scene scene = new Scene(panel,Color.PINK);
		stage.setScene(scene);
		stage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
}
