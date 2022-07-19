package mediaesami.ui;

import java.time.LocalDate;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mediaesami.controller.Controller;
import mediaesami.model.AttivitaFormativa;
import mediaesami.model.Carriera;
import mediaesami.model.Esame;
import mediaesami.model.Voto;


public class MediaEsamiAppMock extends Application {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Calcolo media esami - MOCK");

		Controller controller = new Controller(creaCarrieraFake());
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 630, 400, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}
	
	private SortedMap<String,Carriera> creaCarrieraFake() {
		var carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28004, "Fondamenti di Informatica T-1", 12);
		carriera.inserisci(new Esame(af1, LocalDate.of(2020,2,13), Voto.VENTIQUATTRO));
		AttivitaFormativa af2 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		carriera.inserisci(new Esame(af2, LocalDate.of(2020, 1, 12), Voto.RITIRATO));
		carriera.inserisci(new Esame(af2, LocalDate.of(2020, 2, 10), Voto.VENTIDUE));
		AttivitaFormativa af3 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		carriera.inserisci(new Esame(af3, LocalDate.of(2020, 6, 10), Voto.RESPINTO));
		carriera.inserisci(new Esame(af3, LocalDate.of(2020, 7, 2), Voto.RITIRATO));
		AttivitaFormativa af4 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		carriera.inserisci(new Esame(af4, LocalDate.of(2020, 6, 6), Voto.RESPINTO));
		carriera.inserisci(new Esame(af4, LocalDate.of(2020, 6, 28), Voto.RITIRATO));
		carriera.inserisci(new Esame(af4, LocalDate.of(2020, 7, 1), Voto.DICIOTTO));
		return new TreeMap<>(Map.of("carriera fake", carriera));
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}
