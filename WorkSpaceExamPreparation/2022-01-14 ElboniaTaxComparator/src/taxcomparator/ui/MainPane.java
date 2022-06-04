package taxcomparator.ui;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import taxcomparator.controller.Controller;
import taxcomparator.model.Fasce;

/*
import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import taxcomparator.controller.Controller;
import taxcomparator.model.Fasce;
*/


public class MainPane extends BorderPane {
	
	// DA COMPLETARE

	private CurrencyTextField reddito, imposta;
	private TextArea dettaglio;
	private Button calcola;
	private ComboBox<Fasce> combo;
	
	public MainPane(Controller controller) {
		
		VBox topBox = new VBox();
		
		/* Pane's elements definition */
		
		Label redditoImponibileLabel = new Label("Reddito imponibile");
		topBox.getChildren().add(redditoImponibileLabel);
		
		reddito = new CurrencyTextField();
		topBox.getChildren().add(reddito);
		
		HBox buttons = new HBox();
		combo = new ComboBox<>(FXCollections.observableArrayList(controller.getListaFasceDisponibili()));
		combo.setValue(controller.getListaFasceDisponibili().get(0));
		buttons.getChildren().add(combo);
		calcola = new Button("Calcola");
		buttons.getChildren().add(calcola);
		topBox.getChildren().add(buttons);
		
		Label impostaDovutaLabel = new Label("Imposta dovuta");
		topBox.getChildren().add(impostaDovutaLabel);
		
		imposta = new CurrencyTextField();
		imposta.setEditable(false);
		topBox.getChildren().add(imposta);
		
		Label dettaglioLabel = new Label("Dettaglio");
		topBox.getChildren().add(dettaglioLabel);
		
		dettaglio = new TextArea();
		dettaglio.setEditable(false);
		topBox.getChildren().add(dettaglio);
		
		this.setTop(topBox);
		
		/* Event listener definition */
		
		calcola.setOnAction(event -> {
			try {
				double r = new DecimalFormat("¤ #,##0.##").parse(reddito.getText()).doubleValue();
				controller.setFasce(combo.getValue());
				imposta.setText(controller.calcolaImposte(r) + "");
				dettaglio.setText(controller.getLog());
			} catch (ParseException e) {
				Controller.alert(
					"ERRORE DI PARSING",
					"Formato importo errato",
					"Si richiede € 0.00"
				);
			}
		});
		
		/* Setting margins and paddings for a better representation */
		
		topBox.setPadding(new Insets(5, 5, 5, 5));
		buttons.setAlignment(Pos.CENTER);
		VBox.setMargin(buttons, new Insets(10, 0, 0, 0));
		dettaglioLabel.setPadding(new Insets(10, 0, 0, 0));
		reddito.setPrefColumnCount(30);
		imposta.setPrefColumnCount(30);
		dettaglio.setPrefSize(50, 200);
	}
}
