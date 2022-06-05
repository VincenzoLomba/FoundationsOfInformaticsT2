package rfd.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import rfd.controller.Controller;
import rfd.model.Route;


public class MainPane extends BorderPane {
	
	private TextArea outputArea; 
	private ComboBox<String> comboFrom, comboTo;
	private Controller controller;
	private CheckBox senzaCambi, unCambio;

	public MainPane(Controller controller) {
		
		this.controller=controller;
		HBox topBox = new HBox();
		topBox.setPrefHeight(40);
		
		comboFrom = new ComboBox<>(); populateCombo(comboFrom);
		comboTo   = new ComboBox<>(); populateCombo(comboTo);
		comboFrom.setOnAction(this::reset);
		comboTo.setOnAction(this::reset);
		topBox.getChildren().addAll(new Label("Partenza "), comboFrom, new Label("  Arrivo "), comboTo);
		this.setTop(topBox);
		
		HBox centerBox = new HBox();
		centerBox.setPrefHeight(80);
		centerBox.setAlignment(Pos.CENTER);
		senzaCambi = new CheckBox("senza cambi");
		unCambio = new CheckBox("con un cambio");
		centerBox.getChildren().addAll(new Label("Mostra soluzioni:    "), senzaCambi, new Label("   "), unCambio);
		this.setCenter(centerBox);
		senzaCambi.setOnAction(this::myHandle); 
		unCambio.setOnAction(this::myHandle); 
		outputArea = new TextArea();
		outputArea.setPrefSize(500,300);
		outputArea.setFont(Font.font("Courier New", 12));
		this.setBottom(outputArea);
		
		/* Placeholders */
		comboFrom.setValue("Ancona");
		comboTo.setValue("Anzola dell'Emilia");
	}

	private void populateCombo(ComboBox<String> combo) {
		
		/* "controller.getStationNames()" returns an already sorted List. */
		combo.setItems(FXCollections.observableArrayList(controller.getStationNames()));
	}
	
	private void reset(ActionEvent event) {
		
		outputArea.setText("");
		senzaCambi.setSelected(false);
		unCambio.setSelected(false);
	}
	
	protected void myHandle(ActionEvent event) {
		
		outputArea.setText("");
		if (comboFrom.getValue() == null || comboTo.getValue() == null) return;
		if (senzaCambi.isSelected())
			findDirectRoutes(comboFrom.getValue(), comboTo.getValue());
		if (unCambio.isSelected())
			findIndirectRoutes(comboFrom.getValue(), comboTo.getValue());
	}
	
	protected void findDirectRoutes(String from, String to) {
		
		List<Route> rs = controller.getDirectRoutes(from, to);
		if (!rs.isEmpty()) printAll("PERCORSI DIRETTI SENZA CAMBI:", rs);
	}
	protected void findIndirectRoutes(String from, String to) {
		
		List<Route> rs = controller.getIndirectRoutes(from, to);
		if (!rs.isEmpty()) printAll("PERCORSI INDIRETTI CON CAMBI:", rs);
	}
	
	private void printAll (String introduction, List<Route> routes) {
		
		outputArea.setText(
			new StringBuilder(introduction).append(System.lineSeparator()).append(
				routes.stream()
					.map(Route::toString)
					.collect(Collectors.joining(System.lineSeparator()))
			).toString()
		);
	}

}
