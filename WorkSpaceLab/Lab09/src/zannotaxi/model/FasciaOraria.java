package zannotaxi.model;

import java.time.LocalTime;

import lombok.Getter;

public class FasciaOraria {
	
	@Getter private double costoScattoIniziale; /* See: https://projectlombok.org/features/GetterSetter */
	private LocalTime inizio, fine;
	
	public FasciaOraria(LocalTime inizio, LocalTime fine, double costoScattoIniziale) {
		
		if (inizio == null || fine == null || costoScattoIniziale == Double.NaN || costoScattoIniziale < 0)
			throw new IllegalArgumentException("Impossibile creare una Fascia Oraria: i parametri passati al costruttore sono inammissibili.");
		this.inizio = inizio;
		this.fine = fine;
		this.costoScattoIniziale = costoScattoIniziale;
	}
	
	public boolean contiene (LocalTime localTime) {
		
		localTime = localTime.withSecond(0).withNano(0);
		return !localTime.isBefore(inizio) && !localTime.isAfter(fine);
	}
}
