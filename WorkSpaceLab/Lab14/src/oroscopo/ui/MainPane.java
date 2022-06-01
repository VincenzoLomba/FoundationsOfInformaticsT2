package oroscopo.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import oroscopo.controller.AbstractController;
import oroscopo.controller.Mese;
import oroscopo.model.Oroscopo;
import oroscopo.model.SegnoZodiacale;

public class MainPane extends VBox {

	public MainPane (AbstractController controller, int minFortuna) {
		
		/* Structure definition */
		
		ComboBox<SegnoZodiacale> combo = new ComboBox<>(FXCollections.observableArrayList(controller.getSegni()));
		getChildren().addAll(
			new Label("Segno zodiacale:"),
			combo,
			new Label("Oroscopo mensile del segno:")
		);
		TextArea ta = new TextArea();
		ta.setEditable(false);
		getChildren().add(ta);
		Button button = new Button("Stampa annuale");
		getChildren().add(button);
		
		/* Event's listeners definition */
		
		combo.setOnAction(event -> ta.setText(controller.generaOroscopoCasuale(combo.getValue()).toString()));
		button.setOnAction(event -> {
			SegnoZodiacale segnoZodiacale = combo.getValue();
			StringBuilder stringBuilder = new StringBuilder(segnoZodiacale.toString());
			stringBuilder.append(System.lineSeparator()).append("-------");
			Oroscopo[] oroscopi = controller.generaOroscopoAnnuale(segnoZodiacale, minFortuna);
			for (Mese mese : Mese.values())
				stringBuilder.append(System.lineSeparator()).append(mese.toString()).append(System.lineSeparator()).append(oroscopi[mese.ordinal()].toString()).append(System.lineSeparator());
			stringBuilder.append(System.lineSeparator()).append("GRADO DI FORTUNA:\t").append(minFortuna + "");
			System.out.print(stringBuilder.toString());
		});
		
		/* Styling & place-holding */
		
		setPadding(new Insets(10, 5, 5, 5));
		VBox.setMargin(combo, new Insets(4, 0, 10, 0));
		VBox.setMargin(ta, new Insets(4, 0, 0, 0));
		VBox.setMargin(button, new Insets(20, 0, 0, 0));
		combo.getSelectionModel().selectFirst(); /* Setting placeholder */
		combo.fireEvent(new ActionEvent()); /* Simulating a mouse click */
	}
}
