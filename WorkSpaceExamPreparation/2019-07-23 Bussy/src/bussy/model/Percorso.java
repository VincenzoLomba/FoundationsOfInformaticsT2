package bussy.model;

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

	public String getFermataDa() {
		return fermataDa;
	}

	public String getFermataA() {
		return fermataA;
	}

	public Linea getLinea() {
		return linea;
	}

	public int getDurata() {
		return durata;
	}
	
	@Override
	public String toString() {
		return "Percorso da " + fermataDa + " a " + fermataA + ", linea " + linea.getId() + ", durata " + durata + " min";
	}

	@Override
	public int compareTo(Percorso that) {
		// STARTKIT ORIGINALE, impreciso nel caso di percorsi di eguale durata
		// return Integer.compare(this.getDurata(), that.getDurata());
		// STARTKIT MODIFICATO per considerare anche quel corner case:
		int confrontoDurate = Integer.compare(this.getDurata(), that.getDurata());
		return confrontoDurate!=0 ? confrontoDurate : this.getLinea().getId().compareTo(that.getLinea().getId());
	}

}
