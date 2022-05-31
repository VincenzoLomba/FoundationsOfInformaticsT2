package taxcomparator.ui;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import taxcomparator.controller.Controller;
import taxcomparator.model.CalcolatoreImposta;
import taxcomparator.model.Fasce;
import taxcomparator.model.Fascia;
import taxcomparator.model.MyCalcolatoreImposta;

public class TaxComparatorAppMock extends Application {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("MOCK - Tax Comparator");
			
		List<Fascia> listaFasce = List.of( 
				new Fascia(0,     16000, 0.15), 
				new Fascia(16000, 25000, 0.20),
				new Fascia(25000, 46000, 0.33),
				new Fascia(46000, 0.40));
		Fasce unicaFasciaMock = new Fasce("mock", listaFasce, 8000);
		
		CalcolatoreImposta calc = new MyCalcolatoreImposta();
		Controller controller = new Controller(calc, List.of(unicaFasciaMock));
		
		MainPane mainPanel = new MainPane(controller);
		Scene scene = new Scene(mainPanel, 500, 400, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}
