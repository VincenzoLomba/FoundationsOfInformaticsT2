package ticketsosta;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Ticket {
	private LocalTime inizio, fine;
	private double costo;
	
	public Ticket(LocalTime inizio, LocalTime fine, double costo) {
		super();
		this.inizio = inizio;
		this.fine = fine;
		this.costo = costo;
	}
	
	public LocalTime getInizioSosta() {
		return inizio;
	}
	
	public LocalTime getFineSosta() {
		return fine;
	}
	
	public double getCosto() {
		return costo;
	}
	
	public String getCostoAsString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
		// NB: lo spazio prima/dopo il simbolo di valuta è il non-breakable space (codice 160=0xA0), NON lo spazio classico (codice 32=0x20)
		return formatter.format(costo);
	}
	
	@Override
	public String toString() {
		return "Sosta autorizzata\ndalle " + 
				inizio.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY)) +
				" alle " +
				fine.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY)) +
				"\n" +
				"Durata totale: " + toStringDuration(Duration.between(inizio, fine)) +
				"\n" +
				"Totale pagato: " + getCostoAsString();
	}
	
	private String toStringDuration(Duration duration) {
		int minuti = duration.toMinutesPart();
		String sMinuti = (minuti<10 ? "0" : "") + minuti;
		return duration.toHours() + ":" + sMinuti;
	}
	
}
