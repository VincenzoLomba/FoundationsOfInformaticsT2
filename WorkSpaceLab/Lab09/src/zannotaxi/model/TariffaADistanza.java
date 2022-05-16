package zannotaxi.model;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter /* See: https://projectlombok.org/features/GetterSetter */
@AllArgsConstructor /* See: https://projectlombok.org/features/constructor */
public class TariffaADistanza implements ITariffaTaxi {
	
	private String nome;
	private double velocitaMinima;
	private double velocitaMassima;
	private double costoMinimo;
	private double costoMassimo;
	private double valoreScatto;
	private double distanzaDiScatto;
	
	@Override
	public Optional<Scatto> getScattoCorrente(int tempoTrascorsoDaUltimoScatto, double spazioPercorsoDaUltimoScatto, double costoCorrente) {
		
		double velocita = spazioPercorsoDaUltimoScatto / tempoTrascorsoDaUltimoScatto  * 3.6;
		return velocita >= getVelocitaMinima() && velocita < getVelocitaMassima()
			&& costoCorrente >= getCostoMinimo() && costoCorrente < getCostoMassimo()
			&& Math.round(spazioPercorsoDaUltimoScatto) >= distanzaDiScatto
			?
			Optional.of(new Scatto(tempoTrascorsoDaUltimoScatto, getDistanzaDiScatto(), getValoreScatto())): Optional.empty();
	}
}
