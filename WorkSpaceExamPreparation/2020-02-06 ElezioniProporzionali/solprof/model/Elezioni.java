package dentinia.governor.model;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Elezioni {

	private SortedMap<Partito, Long> mappaVoti;
	private long seggiDaAssegnare;
	private LeggeElettorale algoritmo;

	public Elezioni(long seggiDaAssegnare) {
		this.seggiDaAssegnare = seggiDaAssegnare;
		this.mappaVoti = new TreeMap<>();
		this.algoritmo=null;
	}
	
	public Elezioni(long seggiDaAssegnare, LeggeElettorale algoritmo) {
		this.seggiDaAssegnare = seggiDaAssegnare;
		this.mappaVoti = new TreeMap<>();
		this.algoritmo = algoritmo;
	}

	public LeggeElettorale getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(LeggeElettorale algoritmo) {
		this.algoritmo = algoritmo;
	}

	public void setVoti(Partito p, long voti) {
		mappaVoti.put(p, voti);
	}

	public long getVoti(Partito p) {
		return mappaVoti.get(p);
	}

	public long getVotiTotali() {
		return mappaVoti.values().stream().collect(Collectors.summingLong(x -> x));
	}

	public SortedSet<Partito> getPartiti() {
		return (SortedSet<Partito>) mappaVoti.keySet();
	}

	public long getSeggiDaAssegnare() {
		return seggiDaAssegnare;
	}
	
	public RisultatoElezioni getRisultato(){
		if (algoritmo==null) return new RisultatoElezioni(this.getPartiti());
		else return algoritmo.apply(this);
	}

	public String toString() {
		RisultatoElezioni risultato = getRisultato();
		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ITALY);
		StringBuilder sb = new StringBuilder();
		if (!risultato.seggiSettati()) sb.append("*** ATTENZIONE: seggi non ancora calcolati ***" + System.lineSeparator());
		for (Partito p : getPartiti()) {
			sb.append(String.format("%-35.35s %5s %11s %8s %4d%n", p.toString(), "Voti:",
					formatter.format(getVoti(p)), "Seggi:", risultato.getSeggi(p)));
		}
		sb.append(String.format("%-35.35s %5s %11s %8s %4d%n", "TOTALE", "Voti:",
				formatter.format(getVotiTotali()), "Seggi:", risultato.getSeggiTotali()));
		return sb.toString();
	}
}
