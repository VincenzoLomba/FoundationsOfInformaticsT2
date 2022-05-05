package bussy.model;

import java.util.Map;
import java.util.OptionalInt;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class MyCercatore implements Cercatore {

	private Map<String, Linea> mappaLinee;
	
	public MyCercatore (Map<String, Linea> mappaLinee) { this.mappaLinee = mappaLinee; }
	
	@Override
	public SortedSet<Percorso> cercaPercorsi(String fermataDa, String fermataA, OptionalInt durataMax) {
		
		if (fermataDa == null || fermataA == null || durataMax == null) ErrorMessage.emit("Richiesta di ricerca percorsi a cui sono stati passati dei parametri nulli.");
		Stream<Percorso> ps = mappaLinee.values().stream().map(
			l -> l.getPercorso(fermataDa, fermataA).orElse(null)
		).filter(
			p -> p != null && (durataMax.isEmpty() || p.getDurata() <= durataMax.getAsInt())
		);
		return new TreeSet<>(ps.toList());
	}

}
