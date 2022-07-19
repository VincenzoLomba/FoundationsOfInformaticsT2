package zannopoll.model;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.MySondaggioReader;
import zannopoll.persistence.SondaggioReader;

public class Sondaggio {

	private Map<String, List<Intervista>> mappa;
	private List<Intervista> listaInterviste;
	private int totaleIntervistati;

	public Sondaggio(List<Intervista> listaInterviste) {
		if (listaInterviste == null || listaInterviste.size() == 0)
			throw new IllegalArgumentException("lista interviste nulla o vuota");

		this.listaInterviste = listaInterviste;
		this.mappa = new HashMap<>();
		totaleIntervistati = 0;
		popolaMappa();
	}

	private void popolaMappa() {
		for (Intervista iv : listaInterviste) {
			String key = iv.getNome();
			List<Intervista> value = mappa.get(key);
			if (value == null) {
				value = new ArrayList<>();
				mappa.put(key, value);
			}
			value.add(iv);
			totaleIntervistati++;
		}
	}

	protected List<Intervista> getListaInterviste() {
		return listaInterviste;
	}

	public Map<String, List<Intervista>> getMappaInterviste() {
		return mappa;
	}

	public List<Intervista> getListaIntervistati(String scelta) {
		if (scelta == null || scelta.isEmpty())
			throw new IllegalArgumentException("scelta nulla o vuota");

		List<Intervista> list = mappa.get(scelta);
		if (list == null)
			throw new IllegalArgumentException("scelta non valida");

		return list;
	}

	public int getEtaMaxIntervistati() {
		return listaInterviste.stream().mapToInt(iv -> iv.getEta()).max().getAsInt(); // list
																						// è
																						// certamente
																						// non
																						// vuota
	}

	public int getEtaMinIntervistati() {
		return listaInterviste.stream().mapToInt(iv -> iv.getEta()).min().getAsInt(); // list
																						// è
																						// certamente
																						// non
																						// vuota
	}

	public int getTotaleIntervistati() {
		return totaleIntervistati;
	}

	// quick test
	public static void main(String args[]) throws IOException, BadFileFormatException {
		try (Reader r = new FileReader("RisultatoSondaggio.txt")) {
			SondaggioReader vReader = new MySondaggioReader();
			List<Intervista> myList = vReader.leggiRisposte(r);
			Sondaggio sondaggio = new Sondaggio(myList);
			System.out.println(sondaggio.getMappaInterviste().size());
			for (String scelta : sondaggio.getMappaInterviste().keySet()) {
				System.out.println(scelta + "(" + sondaggio.getMappaInterviste().get(scelta).size() + ")");
			}
			System.out.println("Eta max: " + sondaggio.getEtaMaxIntervistati());
			System.out.println("Eta min: " + sondaggio.getEtaMinIntervistati());
		}
	}

}
