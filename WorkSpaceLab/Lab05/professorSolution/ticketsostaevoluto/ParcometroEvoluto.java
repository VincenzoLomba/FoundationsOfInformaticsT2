package ticketsostaevoluto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import ticketsosta.Tariffa;

public class ParcometroEvoluto {
	
	private Tariffa tariffe[];
	
	public ParcometroEvoluto(Tariffa tariffa[]) {
		this.tariffe = new Tariffa[7]; 
		this.tariffe = Arrays.copyOf(tariffa,7);
	}
	
	

	public TicketEvoluto emettiTicket(LocalDateTime inizio, LocalDateTime fine) {
		// tariffa non è più unica: serve quella del PRIMO giorno
		Tariffa tariffaPrimoGiorno = this.tariffe[inizio.getDayOfWeek().ordinal()];
		// franchigia: si pospone l'inizio effettivo della sosta
		LocalDateTime inizioEffettivo = inizio.plusMinutes(tariffaPrimoGiorno.getMinutiFranchigia());
		double costo;
		// minimo di durata: se durata effettiva è inferiore, si considera la durata minima
		long durataSosta = Duration.between(inizioEffettivo,fine).toMinutes();
		
		if (durataSosta<tariffaPrimoGiorno.getDurataMinima()) {
			costo = tariffaPrimoGiorno.getDurataMinima() * tariffaPrimoGiorno.getTariffaOraria() / 60.0;
		}
		else {
			costo = calcolaCostoSuPiuGiorni(inizio, fine);
		}
		return new TicketEvoluto(inizio, fine, costo); //NB: nel ticket metto orario ufficiale, non effettivo
	}
	
	
	private double calcolaCostoSuPiuGiorni(LocalDateTime inizio, LocalDateTime fine) {
	    LocalDate day = inizio.toLocalDate();
	    LocalDate dayEnd = fine.toLocalDate(); 
	    double costo=0;
		while (!dayEnd.isBefore(day)) {
			LocalTime dayInizio;
			LocalTime dayFine;
			Tariffa tariffa = this.tariffe[day.getDayOfWeek().ordinal()];
			
			if (inizio.toLocalDate().equals(day)) {
				dayInizio = inizio.toLocalTime().plusMinutes(tariffa.getMinutiFranchigia());
			} else {
				dayInizio = LocalTime.of(0, 0);
			}

			if (fine.toLocalDate().equals(day)) {
				dayFine = fine.toLocalTime();
			} else {
				dayFine = LocalTime.of(0, 0);
			}

			costo += calcolaCosto(tariffa.getTariffaOraria(), dayInizio, dayFine);
			day = day.plusDays(1);
		}
		
		return costo;
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
		StringBuilder builder = new StringBuilder();
		builder.append("Parcometro configurato con le tariffe:  ");
		builder.append(System.lineSeparator());
		
		for(Tariffa tari : tariffe)
		{
			builder.append(tari);
			builder.append(System.lineSeparator());
		}
		
		return builder.toString();
		
	}
}
