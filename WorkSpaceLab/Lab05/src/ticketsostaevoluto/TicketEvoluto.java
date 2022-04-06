package ticketsostaevoluto;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TicketEvoluto {
	
	private double costo;
	private LocalDateTime inizioSosta, fineSosta;
	
	public TicketEvoluto (LocalDateTime inizio, LocalDateTime fine, double costo) {
		
		this.costo = costo;
		inizioSosta = inizio;
		fineSosta = fine;
	}
	
	public LocalDateTime getInizioSosta () { return inizioSosta; }
	public LocalDateTime getFineSosta () { return fineSosta; }
	public double getCosto () { return costo; }
	
	public String getCostoAsString () {
		return NumberFormat.getCurrencyInstance(Locale.ITALY).format(costo);
	}
	
	public String toStringDuration (Duration duration) {
		int m = duration.toMinutesPart();
		System.out.println(duration.toHours());
		return duration.toHours() + ":" + (m < 10 ? "0" : "") + m;
	}
	
	@Override
	public String toString () {
		return "Sosta autorizzata da " +
			   inizioSosta.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.ITALY)) + 
			   " a " + fineSosta.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.ITALY)) +
			   "\nDurata totale: " + toStringDuration(Duration.between(inizioSosta, fineSosta)) +
			   "\nTotale pagato: " + getCostoAsString()
		;
	}

	
}
