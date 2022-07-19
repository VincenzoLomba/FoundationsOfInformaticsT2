package elbonia.model;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class CalcolatoreSeggiMaggioritario implements CalcolatoreSeggi {

	public Map<String,Integer> assegnaSeggi(int dimensioneCollegio, List<Collegio> listaCollegi) {
		
		List<Collegio> listaCollegiRaggruppati = Collegio.raggruppaCollegi(listaCollegi, dimensioneCollegio);
		Set<String> nomiPartiti = Partito.getNomiPartiti(listaCollegiRaggruppati.get(0)); // un collegio vale l'altro, qui hanno tutti gli stessi partiti

		// inizializzazione mappaseggi: tutti i partiti hanno inizialmente 0 seggi
		Map<String,Integer> mappaSeggi = new HashMap<String, Integer>();
		for(String nomePartito : nomiPartiti){
			mappaSeggi.put(nomePartito, 0);
		}
		
		// calcolo seggi
		for (Collegio c : listaCollegiRaggruppati){
			// tutti i partiti sono presenti nella mappa, al più con 0 seggi: ergo, la get non può fallire
			Integer seggiPartito = mappaSeggi.get(c.getVincitore().getNome());
			mappaSeggi.put(c.getVincitore().getNome(), seggiPartito+dimensioneCollegio); 
		}
		return mappaSeggi;
	}

}
