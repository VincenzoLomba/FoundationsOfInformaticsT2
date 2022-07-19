package dentinia.governor.ui.javafx;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.Partito;
import dentinia.governor.ui.controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ElectionPane extends BorderPane {

	private Slider sbarramento;
	private TextArea electionTable, percentageTable;
	private Controller controller;
	private Button calcola, salva;
	private FlowPane rightPane;
	private PieChart chart; 

	public ElectionPane(Controller controller) {

		this.controller = controller;

		VBox outputPane = new VBox();
		outputPane.setAlignment(Pos.CENTER_LEFT);
		electionTable = new TextArea();
		electionTable.setEditable(false);
		electionTable.setPrefWidth(500);
		electionTable.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
		electionTable.setText("");
		outputPane.getChildren().add(electionTable);
		percentageTable = new TextArea();
		percentageTable.setEditable(false);
		percentageTable.setPrefWidth(500);
		percentageTable.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
		percentageTable.setText("");
		outputPane.getChildren().add(percentageTable);
		this.setLeft(outputPane);

		HBox sbarramentoPane = new HBox();
		sbarramentoPane.setAlignment(Pos.CENTER_LEFT);
		sbarramentoPane.getChildren().add(new Label("Sbarramento %"));
		sbarramento = new Slider(0, 10, 0);
		sbarramento.setPrefWidth(300);
		sbarramento.setShowTickMarks(true);
		sbarramento.setShowTickLabels(true);
		sbarramento.setMajorTickUnit(1);
		sbarramento.setSnapToTicks(true);
		sbarramento.setBlockIncrement(0.5);
		sbarramentoPane.getChildren().add(sbarramento);
		this.setTop(sbarramentoPane);

		calcola = new Button("Calcola");
		calcola.setOnAction(this::calcolaSeggi);
		TilePane commandPane = new TilePane();
		commandPane.setAlignment(Pos.CENTER);
		salva = new Button("Salva");
		salva.setDisable(true);
		salva.setOnAction(this::salva);
		commandPane.getChildren().addAll(calcola, salva);
		this.setBottom(commandPane);
		rightPane = new FlowPane();
		this.setRight(rightPane);
		
		electionTable.setText(controller.getElezioni().toString());
		percentageTable.setText(calcolaPercentuali(controller.getElezioni()));
		disegnaGrafico(controller.getElezioni());
	}

	private void calcolaSeggi(ActionEvent event) {
		Elezioni elezioni = controller.ricalcola(sbarramento.getValue() / 100.0);
		electionTable.setText(elezioni.toString());
		percentageTable.setText(calcolaPercentuali(elezioni));
		salva.setDisable(false);
		disegnaGrafico(elezioni);
	}

	
	private String calcolaPercentuali(Elezioni elezioni) {
		StringBuilder sb = new StringBuilder();
		Partito[] partiti = elezioni.getPartiti().toArray(new Partito[1]);
		List<Long> listaVoti = Arrays.stream(partiti).map(elezioni::getVoti).collect(Collectors.toList());
		Long totaleVoti = Arrays.stream(partiti).mapToLong(elezioni::getVoti).sum();
		List<Double> listaPercVoti = listaVoti.stream().map(v-> v.doubleValue()/totaleVoti).collect(Collectors.toList());
		List<Long> listaSeggi = elezioni.getRisultato().getPartiti().stream().map(elezioni.getRisultato()::getSeggi).collect(Collectors.toList());
		List<Double> listaPercSeggi = listaSeggi.stream().map(v-> v.doubleValue()/elezioni.getSeggiDaAssegnare()).collect(Collectors.toList());
		NumberFormat formatter = NumberFormat.getPercentInstance(Locale.ITALY);
		for (int i=0; i< partiti.length; i++) {
			sb.append(String.format("%-35.35s %5s %4s %15s %3s%n", 
					partiti[i], "Voti%:",
					formatter.format(listaPercVoti.get(i)), "Seggi%:", formatter.format(listaPercSeggi.get(i))));
		}
		return sb.toString();
	}
	

	private void disegnaGrafico(Elezioni elezioni) {
		ObservableList<PieChart.Data> dati = FXCollections.observableArrayList();
		for (Partito p : elezioni.getPartiti()) {
			long seggi = elezioni.getRisultato().getSeggi(p);
			if (seggi > 0) {
				dati.add(new PieChart.Data(p.getNome(), seggi));
			}
		}
		rightPane.getChildren().remove(chart);
		chart = new PieChart(dati);
		chart.setLabelsVisible(false);
		chart.setTitle("Distribuizione seggi");
		chart.setPrefWidth(60);
		rightPane.getChildren().add(chart);
	}
	
	private void salva(ActionEvent event) {
		try {
			controller.salvaSuFile("Metodo D'Hondt con sbarramento del " + sbarramento.getValue() + "%");
			salva.setDisable(true);
		} catch (IOException e1) {
			controller.alert("Errore nel salvataggio", "Impossibile scrivere su file",
					"Non è stato possibile aprire il file o scrivere su di esso");
			System.err.println(e1);
			return;
		}
	}

}
