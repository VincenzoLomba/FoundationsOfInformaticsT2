package bussy.ui.controller;

import java.util.Collection;
import java.util.Map;
import java.util.OptionalInt;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import bussy.model.Cercatore;
import bussy.model.Fermata;
import bussy.model.Linea;
import bussy.model.MyCercatore;
import bussy.model.Percorso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {

	private Map<String,Linea> linee;
	private SortedSet<String> nomiFermate;
	private Cercatore cercatore ;
	
	public Controller(Map<String,Linea> linee) {
	 	if (linee==null || linee.isEmpty()) throw new IllegalArgumentException("Mappa linee di trasporto pubblico nulla o vuota");
		this.linee=linee;
		nomiFermate = linee.values().stream()
									.map(linea -> linea.getOrariPassaggioAlleFermate().values()) // Stream<Collection<Fermata>>
									.flatMap((Collection<Fermata> list) -> list.stream())        // Stream<Fermata>
									.map(x -> x.getNome())										 // Stream<String>
									.collect(Collectors.toCollection(TreeSet::new));
		// System.out.println(nomiFermate);
		cercatore = new MyCercatore(linee);
	}
	
	public Map<String,Linea> getLinee() { 
		return linee;
	}

	public ObservableList<String> getNomiFermate() {
		return FXCollections.observableArrayList(nomiFermate);
	}

	public SortedSet<Percorso> cercaPercorsi(String fermataDa, String fermataA, OptionalInt durataMax){
		return cercatore.cercaPercorsi(fermataDa, fermataA, durataMax);
	}
	
	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
}
