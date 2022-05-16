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

public class TassametroTestsValoriUfficialiMod {
	
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
	tds.add(new TariffaADistanza("T2", velocitaLimite, 1000, 10, 25,  0.20,  83));
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
	public void testTassametro_SoloDistanza_T1_T2modificata() {
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 60, 600);
		// 60 km/h x 600 secondi = 1 km/min x 10 minuti 
		// inizialmente 1 scatto da 25 cent ogni 100m (ma solo fino a 10 euro di spesa totale) --> 40 scatti, corrispondenti a 4 km
		// Ma in 10 minuti a 60km/h percorre 10 km, quindi gli altri 6km vanno tariffati a tariffa T2.
		// A tariffa T2, si fa uno scatto da 20 cent ogni 83m --> 72 scatti @20 cent = 14,40 euro (totale 24.40, quindi per un soffio non scatta T3)
		assertEquals(scattoInizialeDiurna +  40 * tds.get(1).getValoreScatto() + 72 * tds.get(2).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	
	
	
	@Test
	public void testTassametro_mix_T0_T1_T2_modificata() {
	
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 10, 60, 60, 600);
		// 10 km/h x 60 secondi + 60 km/h x 600 secondi = 0.5 km/min x 1 min + 1 km/min x 10 min 
		// i primi 60 secondi implicano 5 scatti da 15 cent = costo parziale 0.75 euro 
		// poi, si va a distanza: qui inizialmente 1 scatto da 25 cent ogni 100m (ma solo fino a 10 euro di spesa totale --> fino a 9.25 euro residui)
		// La tariffa T1 copre quindi solo i primi 37 scatti @25 cent = 9.25 euro [ovveri i primi 3700m], oltre scatta la tariffa T2.
		// Nei 10 minuti globali fa 10 km, quindi gli altri 6300m vanno a tariffa T2 (e forse pure T3.. vedremo)
		// A tariffa T2, con uno scatto ogni 85m --> 75 scatti @20 cent = 15 euro (totale 25 esatti, per un soffio non scatta T3)
		assertEquals(scattoInizialeDiurna +  5 * tds.get(0).getValoreScatto() +
				37 * tds.get(1).getValoreScatto() +
				75 * tds.get(2).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	

}
