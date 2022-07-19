package mediaesami.ui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mediaesami.controller.Controller;
import mediaesami.model.Carriera;
import mediaesami.persistence.CarrieraReader;
import mediaesami.persistence.MyCarrieraReader;


public class MediaEsamiApp extends Application {

	public static String[] listing(File dir, String extension) {
		if (dir.isDirectory()) { 
			return dir.list((f,name) -> name.endsWith(extension));
		}
		throw new IllegalArgumentException(dir + " is not a directory");
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Calcolo media esami");
		
		CarrieraReader cReader = new MyCarrieraReader();
		String[] nomiFileCarriere = listing(new File("."), ".txt");
		
		Map<String,Carriera> mappaCarriere =
				Stream.of(nomiFileCarriere).collect(Collectors.toMap(
						nomeFile -> nomeFile.substring(0,nomeFile.indexOf('.')), 
						nomeFile -> {
							try {
								return cReader.leggiCarriera(new FileReader(nomeFile));
							} catch (IOException | IllegalArgumentException e) {
								Controller.alert(
										"Errore di lettura o formato del file errato",
										"Impossibile leggere i dati",
										"Dettagli: " + e.getMessage());
								return null;
							}
						}
				));
		Controller controller = new Controller(new TreeMap<>(mappaCarriere));
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 700, 500, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
