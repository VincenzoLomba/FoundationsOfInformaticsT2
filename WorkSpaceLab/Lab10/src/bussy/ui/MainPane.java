package bussy.ui;

import java.util.OptionalInt;
import java.util.SortedSet;

import bussy.model.Percorso;
import bussy.ui.controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainPane extends BorderPane {

	private Controller controller;
	private ComboBox<String> comboDa, comboA;
	private Button button;
	private TextArea output;
	
	public MainPane(Controller controller) {
		this.controller = controller;
		// --- popolamento combo
		comboDa = new ComboBox<>();
		comboDa.setItems(this.controller.getNomiFermate());
		comboDa.setTooltip(new Tooltip("Scegliere la fermata di partenza"));
		comboA = new ComboBox<>();
		comboA.setItems(this.controller.getNomiFermate());
		comboA.setTooltip(new Tooltip("Scegliere la fermata di destinazione"));
		// posizionamento combo
		HBox rigaSuperiore = new HBox();
		rigaSuperiore.setSpacing(10);
		rigaSuperiore.getChildren().add(new Label("Da"));
		rigaSuperiore.getChildren().add(comboDa);
		rigaSuperiore.getChildren().add(new Label("A"));
		rigaSuperiore.getChildren().add(comboA);
		this.setTop(rigaSuperiore);
		// posizionamento area centrale
		output = new TextArea();
		output.setPrefSize(200, 100);
		output.setStyle("-fx-background-color: lightcoral;");
		this.setCenter(output);				
		// posizionamento bottom line
		HBox rigaInferiore = new HBox();
		rigaInferiore.setAlignment(Pos.CENTER);
		button = new Button("Cerca percorso");
		rigaInferiore.getChildren().add(button);
		this.setBottom(rigaInferiore);
		button.setOnAction(this::myHandler);
	}
	
	public void myHandler(ActionEvent event) {
		output.clear();
		String fermataDa = comboDa.getValue();
		String fermataA  = comboA.getValue();
		SortedSet<Percorso> percorsi = controller.cercaPercorsi(fermataDa, fermataA, OptionalInt.of(60));
		if(percorsi.isEmpty()) {
			output.appendText("Nessun percorso trovato"); 
		} else { 
			percorsi.stream().forEach(p -> output.appendText(p.toString() + System.lineSeparator()));
		}
	}
}
