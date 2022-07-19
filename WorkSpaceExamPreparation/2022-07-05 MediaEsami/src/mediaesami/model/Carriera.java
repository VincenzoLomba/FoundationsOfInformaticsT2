package mediaesami.model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Carriera {	
	private List<Esame> listaEsami;
	private Comparator<Esame> cmp;
	
	public Carriera() {
		// DA FARE
	}
	
	public List<Esame> getListaEsami() {
		return listaEsami;
	}

	public List<Esame> getTentativiPrecedenti(Esame esame){
		// DA FARE
		return null; // fake
	}
		
	public Optional<Esame> getUltimoTentativo(Esame esame){
		// DA FARE
		return null; // fake
	}

	public Optional<Esame> getUltimoEsameDato(){
		// DA FARE
		return null; // fake
	}

	
	public void inserisci(Esame esame) {
		// DA FARE
	}
	
	public double creditiAcquisiti() {
		// DA FARE
		return -88; // fake
	}
	
	public double mediaPesata() {
		// DA FARE
		return -99; // fake
	}

	@Override
	public String toString() {
		return listaEsami.stream().map(Esame::toString).collect(Collectors.joining(System.lineSeparator()));
	}
	
}
