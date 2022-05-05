package bussy.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class LineaCircolare extends Linea {

	public LineaCircolare (String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		
		super(id, orariPassaggioAlleFermate);
		if (!isCircolare()) ErrorMessage.emit("Si è tentato di creare una linea ciroclare che però non lo è.");
	}

	@Override
	public Optional<Percorso> getPercorso(String nomeFermataDa, String nomeFermataA) {
		
		Optional<List<Entry<Integer, Fermata>>> es = getFermate(nomeFermataDa, nomeFermataA);
		if (es.isEmpty()) return Optional.empty();
		Entry<Integer, Fermata> entryDa = es.get().get(0);
		Entry<Integer, Fermata> entryA = es.get().get(1);
		
		/* If "nomeFermataDa" and "nomeFermataA" are the same:
		 * 
		 * <> They refer to the same "Fermata" which is NOT a "capolinea" (not "capolinea iniziale" nor "capolinea finale"):
		 * 	  The method "getFermate" returns an empty Optional, and this method "getPercorso" returns an ampty Optional too.
		 * <> They refer to a "Fermata" which is the "capolinea" ("capolinea iniziale" e "capolinea finale" are the same):
		 *    The method "getFermate" returns two Entry with the same "Fermata" ("capolinea") and two different times (the one of "capolinea iniziale" and the one of "capolinea finale").
		 * 
		 * After that:
		 * <> If "nomeFermataDa" and "nomeFermataA" refear to the "capolinea", the difference between their time has to be returned.
		 * <> If "nomeFermataDa" and "nomeFermataA" refear to different "Fermata":
		 *    - "nomeFermataDa"'s time is lower then "nomeFermataA"'s time: the difference between their time has to be returned.
		 *    - "nomeFermataDa"'s time is bigger then "nomeFermataA"'s time:
		 *      It has to be return the difference between "nomeFermataDa"'s time and "capolinea finale"'s time summed with the difference between "capolinea iniziale"'s time and "nomeFermataA"'s time.
		 * NOTICE: all the differences must be in absolute values (must be positive)!
		 */		
		
		if (entryDa.getValue().equals(entryA.getValue()) || entryDa.getKey() < entryA.getKey())
			/* If "nomeFermataDa" and "nomeFermataDa" refer to "capolinea", it's not sure that the quantity "entryA.getKey() - entryDa.getKey()" is positive: use Math.abs() */
			return Optional.of(
				new Percorso(nomeFermataDa, nomeFermataA, this, Math.abs(entryA.getKey() - entryDa.getKey()))
			);
		else
			return Optional.of(
				new Percorso(
					nomeFermataDa,
					nomeFermataA,
					this,
					(getCapolineaFinale().getKey() - entryDa.getKey()) + (entryA.getKey() - getCapolineaIniziale().getKey())
				)
			);
	}
}
