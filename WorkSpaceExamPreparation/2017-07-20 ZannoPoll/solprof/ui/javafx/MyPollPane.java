package zannopoll.ui.javafx;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import zannopoll.model.Sesso;
import zannopoll.model.SondaggioPercentuale;
import zannopoll.ui.controller.Controller;

public class MyPollPane extends BorderPane {

	private Slider ageMin, ageMax;
	private RadioButton entrambi, m, f;
	private ToggleGroup tg;
	private Controller controller;
	private Button calcola;
	private FlowPane chartPane;
	private PieChart chart;

	public MyPollPane(Controller controller) {
		this.controller = controller;
		//
		VBox filterControlPane = new VBox();
		ageMin = new Slider(controller.getSondaggioPercentuale().getEtaMinIntervistati(), controller.getSondaggioPercentuale().getEtaMaxIntervistati(), 0);
		ageMax = new Slider(controller.getSondaggioPercentuale().getEtaMinIntervistati(), controller.getSondaggioPercentuale().getEtaMaxIntervistati(), 0);
		ageMin.setPrefWidth(300);	 ageMax.setPrefWidth(300);
		//ageMin.setShowTickMarks(true);
		ageMin.setShowTickLabels(true); ageMax.setShowTickLabels(true);
		ageMin.setMajorTickUnit(10);  ageMax.setMajorTickUnit(10);
		ageMin.setSnapToTicks(true); ageMax.setSnapToTicks(true);
		ageMin.setBlockIncrement(1); ageMax.setBlockIncrement(1);
		ageMin.setValue(controller.getSondaggioPercentuale().getEtaMinIntervistati());
		ageMax.setValue(controller.getSondaggioPercentuale().getEtaMaxIntervistati());
		//filterControlPane.getChildren().add(new Label("FILTRI INTERVISTATI"));
		filterControlPane.getChildren().add(new Label("Eta min intervistati"));
		filterControlPane.getChildren().addAll(ageMin);
		filterControlPane.getChildren().add(new Label("Eta max intervistati"));
		filterControlPane.getChildren().addAll(ageMax);
		//--radiobutton
		HBox buttonsBox = new HBox();
		buttonsBox.setPadding(new Insets(10));
		buttonsBox.setSpacing(30);
		tg = new ToggleGroup();
		entrambi = new RadioButton("entrambi");	entrambi.setToggleGroup(tg);
		m = new RadioButton(Sesso.MASCHIO.toString());  m.setToggleGroup(tg);
		f = new RadioButton(Sesso.FEMMINA.toString());	f.setToggleGroup(tg);
		tg.selectToggle(entrambi);
		buttonsBox.getChildren().add(new Label("Sesso intervistati"));
		buttonsBox.getChildren().addAll(entrambi,m,f);
		filterControlPane.getChildren().addAll(buttonsBox);
		this.setTop(filterControlPane);
		//--trigger
		calcola = new Button("Calcola");
		calcola.setOnAction(this::calcolaPercentualiSondaggio);
		TilePane commandPane = new TilePane();
		commandPane.setAlignment(Pos.CENTER);
		commandPane.getChildren().add(calcola);
		this.setBottom(commandPane);
		chartPane = new FlowPane();
		this.setCenter(chartPane);
		ridisegnaGrafico(controller.getSondaggioPercentuale()); // inizialmente filtro trasparente
	}

	private void calcolaPercentualiSondaggio(ActionEvent event) {	
		int minAgeValue = (int) ageMin.getValue();
		int maxAgeValue = (int) ageMax.getValue();
		Optional<Sesso> sex = Optional.empty();	
		Toggle t = tg.getSelectedToggle();
		if (t != entrambi){
			sex = Optional.of(((t==m) ? Sesso.MASCHIO : Sesso.FEMMINA));  
		}
		
		Optional<SondaggioPercentuale> sondaggioFiltrato = controller.getSondaggioPercentuale(minAgeValue, maxAgeValue, sex);
		if (sondaggioFiltrato.isPresent()) {
			ridisegnaGrafico(sondaggioFiltrato.get());
		}
	}

	private void ridisegnaGrafico(SondaggioPercentuale sondaggioFiltrato) {
		ObservableList<PieChart.Data> dati = FXCollections.observableArrayList();
		for (String nomeScelta : sondaggioFiltrato.getMappaPercentuali().keySet()) {
			double percentuale = sondaggioFiltrato.getPercentualeIntervistati(nomeScelta);
			if (percentuale  > 0) {
				dati.add(new PieChart.Data(nomeScelta, percentuale));
			}
		}
		chartPane.getChildren().remove(chart);
		chart = new PieChart(dati);
		chart.setLabelsVisible(false);
		//chart.setTitle("Risultati sondaggio");
		chart.setPrefWidth(600);
		chartPane.getChildren().add(chart);
	}
}
