package ticketsosta;

import java.time.Duration;
import java.time.LocalTime;

public class Parcometro {
	
	private Tariffa tariffa;
	
	public Parcometro(Tariffa tariffa) {
		this.tariffa = tariffa;
	}
	
	public Ticket emettiTicket(LocalTime inizio, LocalTime fine) {
		
		// franchigia: si pospone l'inizio effettivo della sosta
		LocalTime inizioEffettivo = inizio.plusMinutes(tariffa.getMinutiFranchigia());
		//	System.out.println("inizio effettivo: " + inizioEffettivo + " (franchigia " + tariffa.getFranchigia()+")");	
		double costo;
		// minimo di durata: se durata effettiva è inferiore, si considera la durata minima
		long durataSosta = Duration.between(inizioEffettivo,fine).toMinutes();
		//	System.out.println("durata sosta: " + durataSosta/60.0 + " ore");
		if (durataSosta<tariffa.getDurataMinima()) 
			costo =  tariffa.getDurataMinima() * tariffa.getTariffaOraria() / 60.0;
		else 
			costo = calcolaCosto(tariffa.getTariffaOraria(), inizioEffettivo, fine);
		return new Ticket(inizio, fine, costo); //NB: nel ticket metto orario ufficiale, non effettivo
	}

	private double calcolaCosto(double costoOrario, LocalTime da, LocalTime a) {
		Duration durataSosta;
		if (a.isBefore(da) || LocalTime.of(0, 0).equals(a)) {
			durataSosta = Duration.between(da, LocalTime.of(23, 59)).plusMinutes(1);
		} else {
			durataSosta = Duration.between(da, a);
		}
		return costoOrario*durataSosta.toMinutes()/60.0;
	}	
	
	@Override
	public String toString() {
		return "Parcometro configurato con la tariffa " + tariffa.toString();
	}
}
