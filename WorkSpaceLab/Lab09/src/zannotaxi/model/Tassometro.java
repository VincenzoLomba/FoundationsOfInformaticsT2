package zannotaxi.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

public class Tassometro implements ITassometro {
	
	private FasciaOraria [] fasceOrarie;
	private ITariffaTaxi [] tariffe;

	public Tassometro(ITariffaTaxi [] tariffe, FasciaOraria [] fasceOrarie) {
		
		if (tariffe == null || tariffe.length == 0 || Arrays.stream(tariffe).anyMatch(t -> t == null) || fasceOrarie == null || fasceOrarie.length == 0 || Arrays.stream(fasceOrarie).anyMatch(fo -> fo == null)
		) throw new IllegalArgumentException("Impossibile creare un Tassometro: i parametri passati al costruttore sono inammissibili.");
		this.fasceOrarie = Arrays.copyOf(fasceOrarie, fasceOrarie.length);
		this.tariffe = Arrays.copyOf(tariffe, tariffe.length);
	}
	
	private double getScattoIniziale (LocalTime localTime) {
		
		Optional<FasciaOraria> f = Arrays.stream(fasceOrarie).filter(fo -> fo.contiene(localTime)).findFirst();
		return f.isEmpty() ? Double.NaN : f.get().getCostoScattoIniziale();
	}
	
	private Optional<Scatto> findScatto (double spazioPercorsoDaUltimoScatto, int tempoTrascorsoDaUltimoScatto, double costoCorrente) {
		
		Optional<Scatto> scatto = Optional.empty();
		for (ITariffaTaxi tariffa : tariffe) {
			scatto = tariffa.getScattoCorrente(tempoTrascorsoDaUltimoScatto, spazioPercorsoDaUltimoScatto, costoCorrente);
			if (scatto.isPresent()) break;
		}
		return scatto;
	}

	@Override
	public double calcolaCostoCorsa(CorsaTaxi corsaTaxi) {
		
		int tempoTrascorsoDaUltimoScatto = 0;
		double spazioPercorsoDaUltimoScatto = corsaTaxi.getRilevazioniDistanze()[0];
		double costoCorrente = 0.0;
		
		/* Assumption: first value of "corsaTaxi.getRilevazioniDistanze()" is relative to the time t=0. */
		
		for (int j = 1 ; j < corsaTaxi.getRilevazioniDistanze().length ; ++j) {
			
			spazioPercorsoDaUltimoScatto += corsaTaxi.getRilevazioniDistanze()[j] - corsaTaxi.getRilevazioniDistanze()[j-1];
			tempoTrascorsoDaUltimoScatto += 1;
			Optional<Scatto> scatto = findScatto(spazioPercorsoDaUltimoScatto, tempoTrascorsoDaUltimoScatto, costoCorrente);
			if (scatto.isPresent()) {
				spazioPercorsoDaUltimoScatto -= scatto.get().getSpazio();
				tempoTrascorsoDaUltimoScatto -= scatto.get().getTempo();
				costoCorrente += scatto.get().getCosto();
			}
		}
		
		return costoCorrente + getScattoIniziale(corsaTaxi.getOraPartenza());
	}
}
