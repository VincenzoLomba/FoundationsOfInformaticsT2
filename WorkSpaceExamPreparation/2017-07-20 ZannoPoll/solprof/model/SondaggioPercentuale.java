package zannopoll.model;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SondaggioPercentuale extends Sondaggio {

	private Map<String, Double> mappaPercentuali;

	public SondaggioPercentuale(List<Intervista> listaInterviste) {
		super(listaInterviste);
		mappaPercentuali = new HashMap<>();
		popolaMappaPercentuali();
	}

	private void popolaMappaPercentuali() {
		for (String key : this.getMappaInterviste().keySet()) {
			mappaPercentuali.put(key, ((double) this.getListaIntervistati(key).size()) / this.getTotaleIntervistati());
		}
	}

	public Map<String, Double> getMappaPercentuali() {
		return mappaPercentuali;
	}

	public double getPercentualeIntervistati(String scelta) {
		if (scelta == null || scelta.isEmpty())
			throw new IllegalArgumentException("scelta nulla o vuota");

		Double res = mappaPercentuali.get(scelta);
		if (res == null)
			throw new IllegalArgumentException("scelta non valida");
		return res;
	}

	public List<Intervista> getIntervisteFiltrate(int minAge, int maxAge, Optional<Sesso> maybeSex) {
		Predicate<Intervista> filter = (Intervista iv) -> iv.getEta() >= minAge && iv.getEta() <= maxAge;
		if (maybeSex.isPresent()) {
			Sesso sex = maybeSex.get();
			filter = filter.and(iv -> iv.getSesso() == sex);
		}

		return this.getListaInterviste().stream().filter(filter).collect(Collectors.toList());
	}

	public Optional<SondaggioPercentuale> getSondaggioFiltrato(int minAge, int maxAge, Optional<Sesso> maybeSex) {
		List<Intervista> interviste = getIntervisteFiltrate(minAge, maxAge, maybeSex);
		if (interviste.size() > 0) {
			SondaggioPercentuale sondaggioFiltrato = new SondaggioPercentuale(interviste);
			return Optional.of(sondaggioFiltrato);
		} else {
			return Optional.empty();
		}
	}

	public String toString() {
		NumberFormat formatter = NumberFormat.getPercentInstance();
		formatter.setMaximumFractionDigits(2);
		StringBuilder sb = new StringBuilder();
		for (String scelta : this.getMappaPercentuali().keySet()) {
			sb.append(scelta + "\t" + formatter.format(this.getMappaPercentuali().get(scelta)) + "\n");
		}
		return sb.toString();
	}

}
