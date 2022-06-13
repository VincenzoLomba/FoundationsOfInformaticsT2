package dentinia.governor.ui.javafx;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.Partito;
import dentinia.governor.model.RisultatoElezioni;
import dentinia.governor.ui.controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ElectionPane extends BorderPane {
	
	public ElectionPane (Controller controller) {
		
		setPadding(new Insets(10));
		
		Label sbarramentoLabel = new Label("Sbarramento%");
		HBox.setMargin(sbarramentoLabel, new Insets(0, 10, 0, 0));
		Slider slider = new Slider(0, 10, 0);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setBlockIncrement(0.5);
		slider.setSnapToTicks(true);
		slider.setPrefWidth(300);
		HBox top = new HBox();
		top.setAlignment(Pos.CENTER_LEFT);
		top.getChildren().addAll(sbarramentoLabel, slider);
		setTop(top);
		
		TextArea tabellaRisultato = new TextArea("");
		tabellaRisultato.setEditable(false);
		tabellaRisultato.setPrefWidth(500);
		tabellaRisultato.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
		TextArea tabellaPercentuale = new TextArea("");
		tabellaPercentuale.setEditable(false);
		tabellaPercentuale.setPrefWidth(500);
		tabellaPercentuale.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
		VBox left = new VBox();
		left.setAlignment(Pos.CENTER_LEFT);
		left.getChildren().addAll(tabellaRisultato, tabellaPercentuale);
		setLeft(left);
		
		Button calcolaButton = new Button("Calcola");
		HBox.setMargin(calcolaButton, new Insets(0, 10, 0, 0));
		Button salvaButton = new Button("Salva");
		HBox bottom = new HBox();
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().addAll(calcolaButton, salvaButton);
		setBottom(bottom);
		
		ObservableList<PieChart.Data> dati = FXCollections.observableArrayList();
		for (Partito p : controller.getElezioni().getPartiti())
			dati.add(new PieChart.Data(p.getNome(), 0));
		PieChart pieChart = new PieChart(dati);
		pieChart.setTitle("Distribuzione seggi");
		pieChart.setPrefWidth(60);
		pieChart.setLabelsVisible(false);
		pieChart.setAnimated(true);
		VBox right = new VBox();
		right.getChildren().add(pieChart);
		setRight(right);
		
		calcolaButton.setOnAction(event -> {
			salvaButton.setDisable(false);
			double sbarr = slider.getValue() / 100.0;
			Elezioni elezioni = controller.ricalcola(sbarr);
			RisultatoElezioni risultatoElezioni = elezioni.getRisultato();
			tabellaRisultato.setText(elezioni.toString());
			tabellaPercentuale.setText(elezioniToStringPercentuale(elezioni));
			int counter = 0;
			for (Partito p : elezioni.getPartiti())
				dati.get(counter++).setPieValue(risultatoElezioni.getSeggi(p));
		});
		salvaButton.setOnAction(event -> {
			try {
				controller.salvaSuFile("Metodo D'Hondt con sbarramento del " + slider.getValue() + "%");
				salvaButton.setDisable(true);
			} catch (IOException e1) {
				controller.alert("Errore nel salvataggio", "Impossibile scrivere su file",
						"Non è stato possibile aprire il file o scrivere su di esso");
				System.err.println(e1);
				return;
			}
		});
		
		calcolaButton.fire();
		salvaButton.setDisable(true);
	}
	
	private String elezioniToStringPercentuale (Elezioni elezioni) {
		
		NumberFormat formatter = NumberFormat.getPercentInstance(Locale.ITALY);
		RisultatoElezioni risultato = elezioni.getRisultato();
		StringBuilder sb = new StringBuilder();
		if (!risultato.seggiSettati()) sb.append("*** ATTENZIONE: seggi non ancora calcolati ***" + System.lineSeparator());
		for (Partito p : elezioni.getPartiti()) {
			sb.append(String.format("%-35.35s %5s %11s %8s %4s%n", p.toString(), "Voti:",
					formatter.format((double) elezioni.getVoti(p) / elezioni.getVotiTotali()), "Seggi:", formatter.format((double) risultato.getSeggi(p) / risultato.getSeggiTotali())));
		}
		return sb.toString();
	}

}
