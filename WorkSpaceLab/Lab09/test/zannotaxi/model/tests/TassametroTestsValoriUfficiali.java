package zannotaxi.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import zannotaxi.model.CorsaTaxi;
import zannotaxi.model.FasciaOraria;
import zannotaxi.model.ITariffaTaxi;
import zannotaxi.model.TariffaADistanza;
import zannotaxi.model.TariffaATempo;
import zannotaxi.model.Tassometro;

public class TassametroTestsValoriUfficiali {
	
	private Tassometro zt;
	private ArrayList<ITariffaTaxi> tds;
	private final double scattoInizialeDiurna = 4;
	private final double scattoInizialeNotturna = 6;
	private final double velocitaLimite = 27;
	
	private FasciaOraria[] getFasceOrarie() {
		return new FasciaOraria[] { 
				new FasciaOraria(LocalTime.of(06, 00), LocalTime.of(21, 59), scattoInizialeDiurna), 
				new FasciaOraria(LocalTime.of(22, 00), LocalTime.of(23, 59), scattoInizialeNotturna),
				new FasciaOraria(LocalTime.of(00, 00), LocalTime.of(05, 59), scattoInizialeNotturna),
				};
	}
	
@BeforeEach
public void setUp() {
	tds = new ArrayList<ITariffaTaxi>();
	tds.add(new TariffaATempo("T0", 0, velocitaLimite,  0.15, 12));		
	tds.add(new TariffaADistanza("T1", velocitaLimite, 1000,  0, 10,  0.25, 100));
	tds.add(new TariffaADistanza("T2", velocitaLimite, 1000, 10, 25,  0.20,  85));
	tds.add(new TariffaADistanza("T3", velocitaLimite, 1000, 25, Double.MAX_VALUE, 0.15,  65));
    zt = new Tassometro(tds.toArray(new ITariffaTaxi[0]), getFasceOrarie());

}

	
	// TariffaATempo(nome, velocitaMinima, velocitaMassima, valoreScatto, tempoDiScatto)
	// T0: TariffaATempo("T0", 0, 27,  0.15, 12)

	// TariffaADistanza(nome, velocitaMinima, velocitaMassima, costoMinimo, costoMassimo, valoreScatto, distanzaDiScatto)
	// T1: TariffaADistanza("T1", 27, 1000,  0, 10,  0.25, 100)
	// T2: TariffaADistanza("T2", 27, 1000, 10, 25,  0.20,  85)
	// T3: TariffaADistanza("T3", 27, 1000, 25, MAX, 0.15,  65)
	
	// CorsaTaxi(descrizione, oraPartenza, velocitaInKmH, durataInSecondi)
	// Definisce una corsa taxi a velocita costante che dura il tempo indicato. 
	// L'array di rilevazioni � caricato automaticamente con la giusta serie di campioni di spazio percorso.

	// CorsaTaxi(String descrizione, LocalTime oraPartenza, double velocitaInKmH1, int durataInSecondi1, double velocitaInKmH2, int durataInSecondi2)
	// Definisce una corsa taxi a due velocit� costanti successive: la prima parte del tragitto si svolge a velocitaInKmH1 con durataInSecondi1, 
	// mentre la seconda parte si svolge a velocitaInKmH2 e dura il tempo indicato da durataInSecondi2.
	// L'array di rilevazioni � caricato automaticamente con la giusta serie di campioni di spazio percorso.
	
	@Test
	public void testTassametro_SoloTempo_T0() {
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 10, 100,  15, 100);
		// 10 km/h x 100 secondi + 15 km/h x 100 secondi, con 1 scatto ogni 12 secondi sono 8+8=16 scatti
		assertEquals(scattoInizialeDiurna + 16 * tds.get(0).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	
	@Test
	public void testTassametro_SoloDistanza_T1() {
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 30, 60, 60, 60);	
		// 30 km/h x 60 secondi + 60 km/h x 60 secondi = 0.5 km/min x 1 min + 1 km/min x 1 min, con 1 scatto ogni 100m sono 5+10=15 scatti
		assertEquals(scattoInizialeDiurna + 15 * tds.get(1).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	

	
	@Test
	public void testTassametro_SoloDistanza_T1_T2_failed() {
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 60, 600);  // velocit� multipla di 85m sarebbe 60.72
		// 60 km/h x 600 secondi = 1 km/min x 10 minuti = 10 km 
		// inizialmente 1 scatto da 25 cent ogni 100m (ma solo fino a 10 euro di spesa totale) --> 40 scatti, corrispondenti a 4 km
		// Ma in 10 minuti a 60km/h percorre 10 km, quindi gli altri 6km vanno tariffati a tariffa T2.
		// A tariffa T2, si fa uno scatto da 20 cent ogni 85m --> 70 scatti @20 cent = 14 euro (totale 24, quindi per un soffio non scatta T3)
		assertEquals(scattoInizialeDiurna +  40 * tds.get(1).getValoreScatto() + 70 * tds.get(2).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	
	
	
	@Test
	public void testTassametro_mix_T0_T1() {
	
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 10, 60, 60, 60);	
		// 60 km/h x 60 secondi + 10 km/h x 60 secondi = 1 km/min x 1 min + (60 secondi a tempo) 
		// a tempo: 1 scatto ogni 12 secondi --> 5 scatti
		// a distanza: con 1 scatto ogni 100m --> 10 scatti
		assertEquals(scattoInizialeDiurna + 5 * tds.get(0).getValoreScatto() +
				10 * tds.get(1).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}


	
	@Test
	public void testTassametro_mix_T1_T0() {
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 60, 60, 10, 60);	
		// 60 km/h x 60 secondi + 10 km/h x 60 secondi = 1 km/min x 1 min + (60 secondi a tempo) 
		// a tempo: 1 scatto ogni 12 secondi --> 5 scatti
		// a distanza: con 1 scatto ogni 100m --> 10 scatti
		assertEquals(scattoInizialeDiurna + 5 * tds.get(0).getValoreScatto() +
				10 * tds.get(1).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}

}
