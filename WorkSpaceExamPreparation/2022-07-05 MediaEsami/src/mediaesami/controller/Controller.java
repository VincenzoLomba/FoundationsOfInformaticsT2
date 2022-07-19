package mediaesami.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import mediaesami.model.Carriera;

public class Controller {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	//--------------
	
	private Map<String,Carriera> mappaCarriere;

	public Controller(SortedMap<String,Carriera> carriera) {
		this.mappaCarriere = carriera;
	}
	
	public Carriera getCarriera(String idCarriera) {
		return mappaCarriere.get(idCarriera);
	}

	public List<String> getListaIdCarriere() {
		return new ArrayList<String>(mappaCarriere.keySet()); 
	}
	
	public double getMediaPesata(String idCarriera) {
		return mappaCarriere.get(idCarriera).mediaPesata();
	}

	public double getCreditiAcquisiti(String idCarriera) {
		return mappaCarriere.get(idCarriera).creditiAcquisiti();
	}
}
