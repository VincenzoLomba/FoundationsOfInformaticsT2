package elbonia.ui.controller;

import elbonia.model.Collegio;
import elbonia.model.Partito;
import elbonia.persistence.BadFileFormatException;
import elbonia.persistence.CollegiReader;
import elbonia.ui.UserInteractor;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Controller {

	private List<Collegio> listaCollegi;
	private int seggiMassimi;
	private UserInteractor userInteractor;
	
	protected Controller(UserInteractor userInteractor) {
		this.userInteractor = userInteractor;
	}

	public void loadData(Reader reader, CollegiReader collegiReader) {
		try {
			this.listaCollegi = collegiReader.caricaElementi(reader);
			this.seggiMassimi = listaCollegi.size();
		} catch (IOException | BadFileFormatException e) {
			this.userInteractor.showMessage(e.getMessage());
			e.printStackTrace();
			this.userInteractor.shutDownApplication();
		}
	}

	public int getSeggiMassimi() {
		return seggiMassimi;
	}

	public List<Collegio> getListaCollegi() {
		return Collections.unmodifiableList(listaCollegi);
	}
	
	protected UserInteractor getUserInteractor() {
		return userInteractor;
	}
	
	public abstract Map<String, Integer> calcola(int numeroSeggi);
	
	public List<Partito> getListaPartiti() {
		List<Collegio> listaCollegi = getListaCollegi();
		List<Partito> listaPartiti = Collegio.generaListaPartiti(listaCollegi);
		return listaPartiti;
	}

	public Map<String, Integer> getMappaSeggiVuota() {	
		HashMap<String, Integer> mappaSeggi = new HashMap<String, Integer>();
		for (Partito p : getListaPartiti()) {
			mappaSeggi.put(p.getNome(), 0);
		}
		return mappaSeggi;
	}

}