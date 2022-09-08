package bussy.model;

import java.util.Map;
import java.util.Optional;

public class LineaCircolare extends Linea {

	public LineaCircolare(String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		super(id, orariPassaggioAlleFermate);
		Fermata fermataIniziale = this.getCapolineaIniziale().getValue();
		Fermata fermataFinale = this.getCapolineaFinale().getValue();
		if (!isCircolare()) throw new BadLineException("La linea non è Circolre, i capilinea non coincidono " + fermataIniziale.getId() + "/" + fermataFinale.getId());
	}

	@Override
	public Optional<Percorso> getPercorso(String fermataDa, String fermataA) {
		
		try {
			int durata;
			
			/* I metodi del tipo "isCapolinea" si basano sul nome della fermata.
			 * Quindi se la linea e' circolare i metodi "isCapolineaIniziale" e "isCapolineaFinale" ritornano lo stesso risultato.
			 * Infatti ognuno di loro verifica che la stringa di ingresso corrisponda rispettivamente alla stringa di nome della fermata a orario zero o a orario maggiore.
			 * Se però la linea è circolare, allora è vero come tali due fermate debbono avere stringhe rappresentative del nome tra di loro uguali.
			 */
			boolean fermataDaCapolinea = isCapolineaIniziale(fermataDa);
			boolean fermataACapolinea = isCapolineaIniziale(fermataA);
			
			if (fermataDaCapolinea && !fermataACapolinea) {
				durata = getOrarioPassaggioAllaFermata(fermataA);
			} else if (!fermataDaCapolinea && fermataACapolinea) {
				durata = getCapolineaFinale().getKey() - getOrarioPassaggioAllaFermata(fermataDa);
			} else if (fermataDaCapolinea && fermataACapolinea) {
				durata = getCapolineaFinale().getKey();
			} else {
				int a = this.getOrarioPassaggioAllaFermata(fermataDa); // se non c'è, lancia eccezione
				int b = this.getOrarioPassaggioAllaFermata(fermataA);  // se non c'è, lancia eccezione
				durata = b-a;
				if (durata <= 0) {
					durata = getCapolineaFinale().getKey() - a + b;
				}
			}
			return Optional.of(new Percorso(fermataDa, fermataA, this, durata));
		} catch (NoSuchStopException e) {
			return Optional.empty();
		}
	}

}
