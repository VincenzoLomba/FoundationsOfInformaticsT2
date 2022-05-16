package zannotaxi.model;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter /* See: https://projectlombok.org/features/GetterSetter */
@AllArgsConstructor /* See: https://projectlombok.org/features/constructor */
public class TariffaATempo implements ITariffaTaxi {

	private String nome;
	private double velocitaMinima;
	private double velocitaMassima;
	private double valoreScatto;
	private int tempoDiScatto;

	@Override
	public Optional<Scatto> getScattoCorrente(int tempoTrascorsoDaUltimoScatto, double spazioPercorsoDaUltimoScatto, double costoCorrente) {
		
		double velocita = spazioPercorsoDaUltimoScatto / tempoTrascorsoDaUltimoScatto * 3.6;
		return velocita >= getVelocitaMinima() && velocita < getVelocitaMassima() && tempoTrascorsoDaUltimoScatto >= getTempoDiScatto() ?
			Optional.of(new Scatto(getTempoDiScatto(), spazioPercorsoDaUltimoScatto, getValoreScatto())) : Optional.empty();
	}

}
