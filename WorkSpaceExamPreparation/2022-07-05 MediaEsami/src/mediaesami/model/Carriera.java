package mediaesami.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Carriera {
	
	private List<Esame> listaEsami;
	private Comparator<Esame> cmp;
	
	private static final String PROVA_FINALE = "PROVA FINALE";
	
	public Carriera() {
		listaEsami = new ArrayList<>();
		cmp = Comparator.comparing(Esame::getDate).thenComparing((Esame e) -> e.getAf().getId());
	}
	
	public List<Esame> getListaEsami() { return listaEsami; }
	
	public void inserisci(Esame esame) {
		
		if (esame == null) throw new IllegalArgumentException(
			"Formito al metodo \"inserisci\" della classe " + Carriera.class.getSimpleName() + " un valore nullo."
		);
		if (listaEsami.stream().filter(e -> e.getAf().equals(esame.getAf()) && (e.getVoto().superato() || !e.getDate().isBefore(esame.getDate()))).count() > 0) throw new IllegalArgumentException(
			"Non e' possibile inserire un esame associato a attivita' formativa per la quale risulta gia' sostenuto un esame con esito positivo."
		);
		if (esame.getAf().getNome().equalsIgnoreCase(PROVA_FINALE) && listaEsami.stream().filter(e -> !e.getDate().isBefore(esame.getDate())).count() > 0) throw new IllegalArgumentException(
			"Non e' possibile inserire un esame associato alla \"" + PROVA_FINALE + "\" che non risulti sostenuto in data successiva alle date relative a tutti gli esmai gia' presenti"
		);
		listaEsami.add(esame);
		Collections.sort(listaEsami, cmp);
	}

	public List<Esame> getTentativiPrecedenti(Esame esame){
		
		/* Nel caso in cui, per la relativa attività formativa, fosse presente un (e uno solo) esame con esito positivo:
		 * 
		 * - Se il parametro fornito in ingresso risulta essere tale esame con esito positivo, esso verrà certamente escluso dalla lista restituita da questo metodo.
		 *   (essendo questa composta di soli esami PRECEDENTI a quello ricevuto in ingresso come parametro del metodo)
		 * - Se il parametro fornito in ingresso risulta essere un esame con esito negativo, di certo tutti quelli che lo precedono sono anch'essi con esito negativo.
		 * 
		 * Nella generazione della lista da restituire NON e' allora necessario verificare che non venga restituito un esame con esito positivo: questo non potrà mai accadere.
		 */
		
		return listaEsami.stream().filter(
			e -> e.getAf().equals(esame.getAf()) &&
			e.getDate().isBefore(esame.getDate())
		).collect(Collectors.toList());
	}
		
	public Optional<Esame> getUltimoTentativo(Esame esame){
		List<Esame> response = getTentativiPrecedenti(esame);
		return response.isEmpty() ? Optional.empty() : Optional.of(response.get(response.size() - 1));
	}
	
	public Optional<Esame> getUltimoEsameDato(){
		return listaEsami.isEmpty() ? Optional.empty() : Optional.of(listaEsami.get(listaEsami.size() - 1));
	}
	
	public double creditiAcquisiti() {
		return listaEsami.stream().filter(e -> e.getVoto().superato()).mapToDouble(e -> e.getAf().getCfu()).sum();
	}
	
	public double creditiAcquisitiDaVoto() {
		return listaEsami.stream().filter(e -> e.getVoto().superato() && !e.getVoto().equals(Voto.IDONEO)).mapToDouble(e -> e.getAf().getCfu()).sum();
	}
	
	public double mediaPesata() {
		AbstractMap.SimpleEntry<Double, Double> response = listaEsami.stream()
			.filter(e -> e.getVoto().superato() && !e.getVoto().equals(Voto.IDONEO))
			.map(e -> new AbstractMap.SimpleEntry<Double, Double>(e.getVoto().getValue().getAsInt() * e.getAf().getCfu(), e.getAf().getCfu()))
			.reduce(new AbstractMap.SimpleEntry<Double, Double>(0.0, 0.0), (e1,e2) -> new AbstractMap.SimpleEntry<Double, Double>(e1.getKey() + e2.getKey(), e1.getValue() + e2.getValue()));
		return response.getValue() == 0 ? Double.NaN : response.getKey() / response.getValue();
	}

	@Override
	public String toString() {
		return listaEsami.stream().map(Esame::toString).collect(Collectors.joining(System.lineSeparator()));
	}
}
