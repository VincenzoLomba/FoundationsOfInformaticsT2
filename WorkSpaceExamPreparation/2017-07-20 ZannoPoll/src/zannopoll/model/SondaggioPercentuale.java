package zannopoll.model;

import java.text.NumberFormat;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class SondaggioPercentuale extends Sondaggio {

	Map<String, Double> mappaPercentuali; /* All values must be in the range [0,1]. */
	
	public SondaggioPercentuale(List<Intervista> listaInterviste) {
		super(listaInterviste);
		mappaPercentuali = getMappaInterviste().entrySet().stream().map(
			e -> new AbstractMap.SimpleEntry<String, Double>(e.getKey(), (double) e.getValue().size() / getTotaleIntervistati())
		).collect(Collectors.toMap(Entry::getKey, Entry::getValue));	
	}
	
	public Map<String, Double> getMappaPercentuali () { return mappaPercentuali; }
	
	public double getPercentualeIntervistati(String scelta) {
		if (scelta == null || scelta.isBlank()) throw new IllegalArgumentException("scelta nulla o vuota");
		if (!mappaPercentuali.containsKey(scelta)) throw new IllegalArgumentException("scelta non valida");
		return mappaPercentuali.get(scelta);
	}
	
	public  List<Intervista> getIntervisteFiltrate (int minAge, int maxAge, Optional<Sesso> mabyeSex) {
		
		List<Sesso> possibleSexs = mabyeSex.isPresent() ? Arrays.asList(mabyeSex.get()) : Arrays.asList(Sesso.FEMMINA, Sesso.MASCHIO);
		return getListaInterviste().stream().filter(i -> i.getEta() >= minAge && i.getEta() <= maxAge && possibleSexs.contains(i.getSesso())).collect(Collectors.toList());
	}
	
	public Optional<SondaggioPercentuale> getSondaggioFiltrato (int minAge, int maxAge, Optional<Sesso> mabyeSex) {
		
		List<Intervista> fi = getIntervisteFiltrate(minAge, maxAge, mabyeSex);
		return fi.size() == 0 ? Optional.empty() : Optional.of(new SondaggioPercentuale(fi));
	}
	
	@Override
	public String toString() {
		
		if (mappaPercentuali.size() == 0) return "Sondaggio privo di dati.";
		NumberFormat p = NumberFormat.getPercentInstance(Locale.ITALY);
		p.setMaximumFractionDigits(2);
		p.setMinimumFractionDigits(2);
		int l = mappaPercentuali.keySet().stream().mapToInt(s -> s.length()).max().getAsInt() + 2;
		return mappaPercentuali.entrySet().stream().map(
			e -> new StringBuilder()
						.append(e.getKey())
						.append(" ".repeat(l- e.getKey().length()))
						.append(p.format(e.getValue()))
		).collect(Collectors.joining(System.lineSeparator()));
	}
}
