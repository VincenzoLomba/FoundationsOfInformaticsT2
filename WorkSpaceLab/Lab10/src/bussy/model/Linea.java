package bussy.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import lombok.Getter;

@Getter
public abstract class Linea {
	
	private String id;
	private Map<Integer, Fermata> orariPassaggioAlleFermate;
	
	public Linea (String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		
		/* Assumptions:
		 * - The id isn't null (NO MORE AN ASSUMPTION: IT'S VERIFIED).
		 * - The Map has NOT null keys OR negative keys OR null values OR repeated values (two "Fermata" are repeated only if equals)
		 */
		if (id == null || id.replace(" ","").trim().isEmpty()) ErrorMessage.emit("Impossibile creare una linea con un id assente.");
		this.id = id.replace(" ","").trim();
		
		if (orariPassaggioAlleFermate == null) ErrorMessage.emit("Impossibile creare una linea con mappa delle feramte assente.");
		
		this.orariPassaggioAlleFermate = orariPassaggioAlleFermate;
	}
	
	public Entry<Integer, Fermata> getCapolineaIniziale () {
		
		Optional<Entry<Integer, Fermata>> response = getOrariPassaggioAlleFermate().entrySet().stream().reduce((a,b) -> { return a.getKey() < b.getKey() ? a : b; });
		if (response.isEmpty()) ErrorMessage.emit("Impossibile trovare capolinea iniziale: la mappa delle fermate risulta vuota.");
		return response.get();
	}
	
	public Entry<Integer, Fermata> getCapolineaFinale () {
		
		Optional<Entry<Integer, Fermata>> response = getOrariPassaggioAlleFermate().entrySet().stream().reduce((a,b) -> { return a.getKey() > b.getKey() ? a : b; });
		if (response.isEmpty()) ErrorMessage.emit("Impossibile trovare capolinea iniziale: la mappa delle fermate risulta vuota.");
		return response.get();
	}
	
	public int getOrarioPassaggioAllaFermata (String nomeFermata) {
		
		Optional<Entry<Integer, Fermata>> response = getOrariPassaggioAlleFermate().entrySet().stream().filter(a -> a.getValue().getNome().equals(nomeFermata)).findFirst();
		if (response.isEmpty())  ErrorMessage.emit("La fermata richiesta non e' stata trovata.");
		return response.get().getKey();
	}
	
	public boolean isCapolineaIniziale (String nomeFermata) {
		
		try {
			Entry<Integer, Fermata> response = getCapolineaIniziale();
			return response.getValue().getNome().equals(nomeFermata);
		} catch (IllegalArgumentException e) { return false; }
	}
	
	public boolean isCapolineaFinale (String nomeFermata) {
		
		try {
			Entry<Integer, Fermata> response = getCapolineaFinale();
			return response.getValue().getNome().equals(nomeFermata);
		} catch (IllegalArgumentException e) { return false; }
	}
	
	public boolean isCircolare () { return getCapolineaIniziale().getValue().equals(getCapolineaFinale().getValue()); }
	
	/**
	 * Search inside {@link Linea#orariPassaggioAlleFermate} for two {@link Entry<Integer, Fermata>>} that match the input parameters (nomeFermataDa and nomeFermataA).<br/>
	 * Those two values are returned inside a {@link List} (which is wrapped inside an {@link Optional}.<br/>
	 * The first value of this {@link List} is associated to "nomeFermataDa", the second one to "nomeFermataA".<br/>
	 * If one or both of them isn't found, an empty {@link Optional} is returned.<br/>
	 * Notice that if a not empty {@link Optional} is returned, then those two elements of the {@link List} (which are {@link Entry<Integer, Fermata>}) cannot be equal BUT
	 * their values (which are {@link Fermata}) may be equals (only if the same {@link Fermata} associated to two different times, generating two different {@link Entry<Integer, Fermata>}).<br/>
	 * Notice that this method also returns an empty Optional if one or both of the input parameters does not match with any {@link Entry<Integer, Fermata>} of {@link Linea#orariPassaggioAlleFermate}.
	 * @param nomeFermataDa
	 * @param nomeFermataA
	 */
	protected Optional<List<Entry<Integer, Fermata>>> getFermate(String nomeFermataDa, String nomeFermataA) {
		
		List<Entry<Integer, Fermata>> filtered = getOrariPassaggioAlleFermate().entrySet().stream().filter(
			a -> a.getValue().getNome().equals(nomeFermataDa) || a.getValue().getNome().equals(nomeFermataA)
		).toList();
		if (filtered.size() < 2 || (!nomeFermataDa.equals(nomeFermataA) && filtered.get(0).getValue().equals(filtered.get(1).getValue()))) return Optional.empty();
		return filtered.get(0).getValue().getNome().equals(nomeFermataDa) ? Optional.of(filtered) : Optional.of(Arrays.asList(filtered.get(1), filtered.get(0)));
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Linea ln) {
			return getId().equals(ln.getId()) && getOrariPassaggioAlleFermate().equals(ln.getOrariPassaggioAlleFermate());
		}
		return false;
	}
	
	@Override
	public int hashCode() { return Objects.hash(id, getOrariPassaggioAlleFermate()); }
	
	@Override
	public String toString() {

		return new StringBuilder("id: ")
			.append(getId())
			.append(
				orariPassaggioAlleFermate.entrySet().stream().map(
					a -> {
						return new StringBuilder().append("Orario: ").append(a.getKey()).append(", nome: ").append(a.getValue()).toString();
					}
			).reduce("", (a, b) -> { return a + "\n" + b; }))
			.toString();
	}
	
	public abstract Optional<Percorso> getPercorso(String nomeFermataDa, String nomeFermataA);
}
