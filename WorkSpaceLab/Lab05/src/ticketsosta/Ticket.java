package ticketsosta;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Ticket {
	
	private double costo;
	private LocalTime inizioSosta, fineSosta;
	
	public Ticket (LocalTime inizio, LocalTime fine, double costo) {
		
		this.costo = costo;
		inizioSosta = inizio;
		fineSosta = fine;
	}
	
	public LocalTime getInizioSosta () { return inizioSosta; }
	public LocalTime getFineSosta () { return fineSosta; }
	public double getCosto () { return costo; }
	
	public String getCostoAsString () {
		return NumberFormat.getCurrencyInstance(Locale.ITALY).format(costo);
	}
	
	public String toStringDuration (Duration duration) {
		int m = duration.toMinutesPart();
		return duration.toHours() + ":" + (m < 10 ? "0" : "") + m;
	}
	
	@Override
	public String toString () {
		return "Sosta autorizzata dalle " +
			   inizioSosta.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY)) + 
			   " alle " + fineSosta.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY)) +
			   "\nDurata totale: " + toStringDuration(Duration.between(inizioSosta, fineSosta)) +
			   "\nTotale pagato: " + getCostoAsString()
		;
	}

	
}
