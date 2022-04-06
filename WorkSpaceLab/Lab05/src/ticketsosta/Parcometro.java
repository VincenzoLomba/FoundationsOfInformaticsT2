package ticketsosta;

import java.time.Duration;
import java.time.LocalTime;

public class Parcometro {
	
	private Tariffa tariffa;
	
	public Parcometro (Tariffa tariffa) {
		this.tariffa = tariffa;
	}

	@Override
	public String toString () {
		return "Parcometro configurato con la tariffa: " + tariffa.toString();
	}
	
	private double calcolaCosto (double costoOrario, LocalTime da, LocalTime a) {
		
		if (a.isBefore(da) || LocalTime.of(0, 0).equals(a)) a = LocalTime.of(23, 59).plusMinutes(1);
		long minutes = Duration.between(da, a).toMinutes();
		minutes -= tariffa.getMinutiFranchigia();
		if (minutes < tariffa.getDurataMinima()) minutes = tariffa.getDurataMinima();
		return costoOrario * minutes / 60;
	}
	
	public Ticket emettiTicket (LocalTime inizio, LocalTime fine) { return new Ticket(inizio, fine, calcolaCosto(tariffa.getTariffaOraria(), inizio, fine)); }
}
