package bussy.model;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MyCercatore implements Cercatore {

	private Map<String, Linea> mappaLinee;
	
	public MyCercatore (Map<String, Linea> mappaLinee) {
		
		if (mappaLinee == null || mappaLinee.size() == 0) throw new IllegalArgumentException(
			"Nell'istanziare un oggetto della classe \"" + MyCercatore.class.getSimpleName() + "\" e' stata fornita al costruttore una mappa " + (mappaLinee == null ? "nulla." : "vuota.")
		);
		this.mappaLinee = mappaLinee;
	}
	
	@Override
	public SortedSet<Percorso> cercaPercorsi(String fermataDa, String fermataA, OptionalInt durataMax) {
		
		if (fermataDa == null || fermataA == null) throw new IllegalArgumentException(
			"Al metodo \"cercaPercorsi\" della classe \"" + MyCercatore.class.getSimpleName() + "\" sono stati forniti nomi di fermate nulli (o uno, o entrambi)."
		);
		return mappaLinee.values().stream().map(l -> {
			Optional<Percorso> p = l.getPercorso(fermataDa, fermataA);
			return p.isEmpty() ? null : p.get();
		}).filter(
			(Percorso p) -> p != null && (durataMax == null || durataMax.isEmpty() || p.getDurata() <= durataMax.getAsInt())
		).collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public Map<String, Linea> getMappaLinee() {
		return mappaLinee;
	}

}
