package zannopoll.ui.javafx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import zannopoll.model.Sesso;
import zannopoll.model.SondaggioPercentuale;
import zannopoll.ui.controller.Controller;

public class MyPollPane extends BorderPane {
	
	public MyPollPane (Controller controller) {
		
		setPadding(new Insets(10));
		
		VBox top = new VBox();
		top.setAlignment(Pos.CENTER_LEFT);
		Label etaMinLabel = new Label("Età minima intervistati");
		Label etaMaxLabel = new Label("Età massima intervistati");
		etaMaxLabel.setPadding(new Insets(10, 0, 0, 0));
		Slider etaMinSlider = new Slider(
			controller.getSondaggioPercentuale().getEtaMinIntervistati(),
			controller.getSondaggioPercentuale().getEtaMaxIntervistati(),
			controller.getSondaggioPercentuale().getEtaMinIntervistati()
		);
		Slider etaMaxSlider = new Slider(
			controller.getSondaggioPercentuale().getEtaMinIntervistati(),
			controller.getSondaggioPercentuale().getEtaMaxIntervistati(),
			controller.getSondaggioPercentuale().getEtaMaxIntervistati()
		);
		configureSlider(etaMinSlider);
		configureSlider(etaMaxSlider);
		HBox sex = new HBox();
		sex.setAlignment(Pos.CENTER_LEFT);
		Label sexLabel = new Label("Sesso intervistati:");
		sex.setPadding(new Insets(20, 0, 0, 0));
		sex.setSpacing(15);
		ToggleGroup g = new ToggleGroup();
		RadioButton ambedue = new RadioButton("Ambedue");
		RadioButton maschio = new RadioButton("Maschio");
		RadioButton femmina = new RadioButton("Femmina");
		ambedue.setToggleGroup(g);
		maschio.setToggleGroup(g);
		femmina.setToggleGroup(g);
		ambedue.setSelected(true);
		sex.getChildren().addAll(sexLabel, ambedue, maschio, femmina);
		
		top.getChildren().addAll(etaMinLabel, etaMinSlider, etaMaxLabel, etaMaxSlider, sex);
		setTop(top);
		
		VBox center = new VBox();
		center.setAlignment(Pos.CENTER);
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (String scelta : new TreeSet<String>(controller.getSondaggioPercentuale().getMappaInterviste().keySet())) {
			pieChartData.add(new PieChart.Data(scelta, 0));
		}
		PieChart pieChart = new PieChart(pieChartData);
		pieChart.setLabelsVisible(false);
		pieChart.setAnimated(true);
		pieChart.setPrefWidth(70);
		Button calcolaButton = new Button("Calcola");
		VBox.setMargin(calcolaButton, new Insets(10, 0, 0, 0));
		
		center.getChildren().addAll(pieChart, calcolaButton);
		setCenter(center);
		
		calcolaButton.setOnAction(event -> {
			Optional<SondaggioPercentuale> ops = controller.getSondaggioPercentuale(
				(int) etaMinSlider.getValue(),
				(int) etaMaxSlider.getValue(),
				ambedue.isSelected() ? Optional.empty() : (maschio.isSelected() ? Optional.of(Sesso.MASCHIO) : Optional.of(Sesso.FEMMINA))
			);
			ops.ifPresent(s -> {
				ArrayList<String> interviste = new ArrayList<>(s.getMappaInterviste().keySet());
				Collections.sort(interviste);
				int max = Math.min(pieChartData.size(), interviste.size());
				for (int index = 0 ; index < max ; ++index) {
					String scelta = interviste.get(index);
					pieChartData.get(index).setName(scelta);
					pieChartData.get(index).setPieValue(s.getPercentualeIntervistati(scelta));
				}
				if (interviste.size() < pieChartData.size()) {
					pieChartData.remove(interviste.size(), pieChartData.size());
				} else if (interviste.size() > pieChartData.size()) {
					for (int index = pieChartData.size() ; index < interviste.size() ; ++index) {
						String scelta = interviste.get(index);
						pieChartData.add(new PieChart.Data(scelta, s.getPercentualeIntervistati(scelta)));
					}
				}
			});
		});
		
		calcolaButton.fire();
	}

	private void configureSlider (Slider slider) {
		
		slider.setShowTickMarks(false);  /* Nascosta la graduazione per lo slider */
		slider.setShowTickLabels(true);  /* Mostra etichette (per i Major Ticks) */
		slider.setMajorTickUnit(10);     /* Imposta la distanza tra due Major Ticks adiacenti */
		slider.setMinorTickCount(9);     /* Imposta il numero di Minor Ticks da rendere disponibili tra due Major Ticks adiacenti */
		slider.setBlockIncrement(1);     /* In caso di movimento con tasti freccia: di quanti Minor Ticks ci si deve spostare alla volta */
		slider.setSnapToTicks(true);     /* Vincola il cursore a rimanere sui ticks */
		slider.setPrefWidth(200);        /* Setting della lunghezza predefinita */
	}
}
