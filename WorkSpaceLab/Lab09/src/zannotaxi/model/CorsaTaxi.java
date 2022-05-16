package zannotaxi.model;

import java.time.LocalTime;
import java.util.ArrayList;

/* This class has been provided by the professor. */

public class CorsaTaxi {
	
	private double[] rilevazioniDistanze;
	private LocalTime oraPartenza;
	private String dettagliCorsa;

	/* Costruttore principale: definisce una corsa taxi con i relativi campioni di spazio percorso,
	 * presi a intervalli di un secondo uno dall'altro.
	 * */
	public CorsaTaxi(String descrizione, LocalTime oraPartenza, double[] rilevazioniDistanze) {
		this.rilevazioniDistanze = rilevazioniDistanze;
		this.oraPartenza=oraPartenza;
		this.dettagliCorsa = descrizione;
	}

	/* Costruttore accessorio utile a fini di test.
	 * Definisce una corsa taxi a velocitï¿½ costante che dura il tempo indicato.
	 * L'array di rilevazioni è caricato automaticamente con la giusta serie di campioni di spazio percorso.
	*/
	public CorsaTaxi(String descrizione, LocalTime oraPartenza, double velocitaInKmH, int durataInSecondi) {
		this(descrizione, oraPartenza, new double[] { velocitaInKmH }, new int[] { durataInSecondi });
	}

	/* Costruttore accessorio utile a fini di test.
	 * Definisce una corsa taxi a due velocitï¿½ costanti successive: la prima parte del tragitto si svolge a
	 * velocitaInKmH1 e dura il tempo indicato da durataInSecondi1, mentre la seconda parte si svolge a
	 * velocitaInKmH2 e dura il tempo indicato da durataInSecondi2.
	 * L'array di rilevazioni è caricato automaticamente con la giusta serie di campioni di spazio percorso.
	*/
	public CorsaTaxi(String descrizione, LocalTime oraPartenza, double velocitaInKmH1, int durataInSecondi1, double velocitaInKmH2, int durataInSecondi2) {
		this(descrizione, oraPartenza, new double[] { velocitaInKmH1, velocitaInKmH2 }, new int[] { durataInSecondi1, durataInSecondi2 });
	}
	
	public CorsaTaxi(String descrizione, LocalTime oraPartenza, double[] velocitaInKmH, int[] durateInSecondi) {
		ArrayList<Double> distanze = new ArrayList<Double>();
		distanze.add(0.0);
		int durataAccumulata = 1;
		double distanzaAccumulata = 0;
		for (int i = 0; i < velocitaInKmH.length; ++i) {
			int maxDurata = (durataAccumulata - 1) + durateInSecondi[i];
			for (int s = durataAccumulata; s <= maxDurata; ++s) {
				distanzaAccumulata += velocitaInKmH[i] / 3.6;
				distanze.add(distanzaAccumulata);
			}
			durataAccumulata = maxDurata;
		}
		
		this.oraPartenza = oraPartenza;
		this.rilevazioniDistanze = new double[distanze.size()];
		for (int i = 0; i < rilevazioniDistanze.length; ++i) {
			rilevazioniDistanze[i] = distanze.get(i);
		}
		this.dettagliCorsa = descrizione;
	}

	public String getDettagliCorsa() { return dettagliCorsa; }

	public void setDettagliCorsa(String dettagliCorsa) { this.dettagliCorsa = dettagliCorsa; }
	
	public double[] getRilevazioniDistanze() { return rilevazioniDistanze; }

	public LocalTime getOraPartenza() { return oraPartenza; }
	
	@Override
	public String toString() { return "CorsaTaxi [" + dettagliCorsa + "] ora partenza=" + oraPartenza; }
	
}
