package ticketsostaevoluto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import ticketsosta.Tariffa;

public class ParcometroEvoluto {
	
	private Tariffa[] tariffe;
	
	public ParcometroEvoluto (Tariffa[] tariffe) {
		
		this.tariffe = Arrays.copyOf(tariffe, 7);
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder("Parchimetro configurato con tariffe:\n");
		int  j = 0;
		for (Tariffa tariffa : tariffe) {
			sb.append(DayOfWeek.of(j++));
			sb.append(": ");
			sb.append(tariffa.toString());
			if (j != tariffe.length) sb.append("\n");
		}
		return sb.toString();
	}
	
	/*
	private double calcolaCosto (LocalTime da, LocalTime a) {
		
		return 0.0;
	}
	*/
	
	private double calcolaCostoSuPiuGiorni (LocalDateTime inizio, LocalDateTime fine) {
		
		/* Assumption: "inizio" is always before "fine". */
		
		System.out.println("-----");
		System.out.println(inizio + " " + fine);
		
		LocalDate firstDay = inizio.toLocalDate();
		LocalDate currentDay = firstDay.minusDays(1);
		LocalDate lastDay = fine.toLocalDate();
		long franchigia = tariffe[currentDay.getDayOfWeek().ordinal()].getMinutiFranchigia();
		long minMinutes = tariffe[currentDay.getDayOfWeek().ordinal()].getDurataMinima();
		
		double costo = 0.0;
		
		System.out.println("\\/");
		
		do {
			currentDay = currentDay.plusDays(1);
			
			LocalTime startingTime = currentDay.isEqual(firstDay) ?
									 inizio.toLocalTime() :
									 LocalTime.of(0, 0);
			LocalTime endingTime = currentDay.isEqual(lastDay) ?
								   fine.toLocalTime() : 
								   LocalTime.of(23, 59);
			Duration todayDuration = Duration.between(startingTime, endingTime).plusMinutes(!currentDay.isEqual(lastDay) ? 1 : 0);
			System.out.println(startingTime + "  " + endingTime + (!currentDay.isEqual(lastDay) ? "(+1)" : ""));
			System.out.println(todayDuration.toMinutes() + " " + firstDay + " " + currentDay + " " + lastDay);
			System.out.println(franchigia);
			System.out.println(minMinutes);
			long todayMinutes = todayDuration.toMinutes();
			
			if (franchigia > 0) {
				franchigia -= todayMinutes;
				if (franchigia > 0) continue;
				todayMinutes -= (franchigia + todayMinutes);
			}
			
			// The "franchigia" value has been exhausted & the remained todayMinute value is greater than (or equal) zero.
			
			if (todayMinutes == 0) continue;
			
			// The "franchigia" value has been exhausted & the remained todayMinute value is greater than zero.
			
			if (minMinutes > 0) {
				
				minMinutes -= todayMinutes;
				if (minMinutes > 0) {
					costo += todayMinutes * tariffe[currentDay.getDayOfWeek().ordinal()].getTariffaOraria() / 60;
					continue;
				}
				costo += (minMinutes + todayMinutes) * tariffe[currentDay.getDayOfWeek().ordinal()].getTariffaOraria() / 60;
				
				System.out.println(minMinutes + todayMinutes);
				todayMinutes -= (minMinutes + todayMinutes);
				System.out.println(todayMinutes);
				System.out.println(costo);
				
				
			}
			
			// The "franchigia" and the "minMinutes" values have been exhausted & the remained todayMinute value is greater than (or equal) zero.
			// Variable "costo" contains the cost which is related to the first "minMinutes" minutes after the "franchigia" subtraction.
			
			costo += todayMinutes * tariffe[currentDay.getDayOfWeek().ordinal()].getTariffaOraria() / 60;
			System.out.println(costo);
			
		} while (currentDay.isBefore(lastDay));
		
		if (franchigia > 0) {
			
			// The ENTIRE duration of the stop is lower then the "franchigia" value.
			return tariffe[firstDay.getDayOfWeek().ordinal()].getDurataMinima() * tariffe[firstDay.getDayOfWeek().ordinal()].getTariffaOraria() / 60 ;
		}
		
		if (minMinutes > 0) {
			
			// After the "franchigia" value has been subtracted from the ENTIRE duration, the remained value is LOWER then the "minMinutes" value.
			return tariffe[firstDay.getDayOfWeek().ordinal()].getDurataMinima() * tariffe[firstDay.getDayOfWeek().ordinal()].getTariffaOraria() / 60 ;
		}
		
		return costo;
		
		
	}
	
	public TicketEvoluto emettiTicket (LocalDateTime inizio, LocalDateTime fine) {
		
		return new TicketEvoluto(inizio, fine, calcolaCostoSuPiuGiorni(inizio, fine));
	}
}
