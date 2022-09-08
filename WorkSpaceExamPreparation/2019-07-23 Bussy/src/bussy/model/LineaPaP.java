package bussy.model;

import java.util.Map;
import java.util.Optional;

public class LineaPaP extends Linea {

	public LineaPaP(String id, Map<Integer,Fermata> orariPassaggioAlleFermate) {
		super(id, orariPassaggioAlleFermate);
		Fermata fermataIniziale = this.getCapolineaIniziale().getValue();
		Fermata fermataFinale = this.getCapolineaFinale().getValue();
		if (isCircolare()) throw new BadLineException("La linea non è PaP, i capilinea coincidono " + fermataIniziale.getId() + "/" + fermataFinale.getId());
		/*
		if (this.getCapolineaIniziale().equals(this.getCapolineaFinale())) { // allora è linea circolare!
			throw new BadLineException("linea inversa non effettua lo stesso percorso a rovescio");
		}
		*/
	}
	
	public Optional<Percorso> getPercorso(String fermataDa, String fermataA) {
		try {
			int a = this.getOrarioPassaggioAllaFermata(fermataDa); // se non c'è, lancia eccezione
			int b = this.getOrarioPassaggioAllaFermata(fermataA);  // se non c'è, lancia eccezione
			// ci sono entrambe --> esiste un percorso diretto
			// in linee PaP si accetta solo percorso diretto nel giusto verso: se durata negativa, è nel senso inverso --> lo escludiamo
			int durata = b-a;
			return (durata>0) ? Optional.of(new Percorso(fermataDa, fermataA, this, durata)) : Optional.empty();
		}
		catch (NoSuchStopException e) {
			return Optional.empty(); 
		}
	}

}
