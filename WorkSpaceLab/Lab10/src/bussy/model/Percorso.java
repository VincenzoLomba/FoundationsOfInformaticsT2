package bussy.model;

import lombok.Getter;

/* This class has been provided by the professor. */

@Getter
public class Percorso implements Comparable<Percorso> {

	private String fermataDa, fermataA;
	private Linea linea;
	private int durata;
	
	public Percorso(String fermataDa, String fermataA, Linea linea, int durata) {
		this.fermataDa = fermataDa;
		this.fermataA = fermataA;
		this.linea = linea;
		this.durata=durata;
	}
	
	@Override
	public String toString() { return "Percorso da " + fermataDa + " a " + fermataA + ", linea " + linea.getId() + ", durata " + durata + " min"; }

	@Override
	public int compareTo(Percorso that) {
		/* STARTKIT ORIGINALE, impreciso nel caso di percorsi di eguale durata
		/* return Integer.compare(this.getDurata(), that.getDurata());
		/* STARTKIT MODIFICATO per considerare anche quel corner case:*/
		int confrontoDurate = Integer.compare(this.getDurata(), that.getDurata());
		return confrontoDurate!=0 ? confrontoDurate : this.getLinea().getId().compareTo(that.getLinea().getId());
	}

}
