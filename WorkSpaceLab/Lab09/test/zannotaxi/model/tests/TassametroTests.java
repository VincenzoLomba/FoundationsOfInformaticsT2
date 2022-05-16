package zannotaxi.model.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;



import zannotaxi.model.*;

public class TassametroTests {
	
	private final double scattoInizialeDiurna = 5;
	private final double scattoInizialeNotturna = 10;
	private final double velocitaLimite = 20;
	
	private FasciaOraria[] getFasceOrarie() {
		return new FasciaOraria[] { 
				new FasciaOraria(LocalTime.of(06, 00), LocalTime.of(21, 59), scattoInizialeDiurna), 
				new FasciaOraria(LocalTime.of(22, 00), LocalTime.of(23, 59), scattoInizialeNotturna),
				new FasciaOraria(LocalTime.of(00, 00), LocalTime.of(05, 59), scattoInizialeNotturna),
				};
	}
	
	@Test
	public void testTassametro_SoloTempo() {
		ArrayList<ITariffaTaxi> tds = new ArrayList<ITariffaTaxi>();
		tds.add(new TariffaATempo("TT", 0, velocitaLimite,  0.1, 100));		
		tds.add(new TariffaADistanza("TD1", velocitaLimite, 1000, 0, 10, 7, 50));
		tds.add(new TariffaADistanza("TD2", velocitaLimite, 1000, 10, Double.MAX_VALUE, 7, 25));
		Tassometro zt = new Tassometro(tds.toArray(new ITariffaTaxi[0]), getFasceOrarie());
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 10, 100, 15, 100);	
		assertEquals(scattoInizialeDiurna + 2 * tds.get(0).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	
	@Test
	public void testTassametro_SoloDistanza_TD1() {
		ArrayList<ITariffaTaxi> tds = new ArrayList<ITariffaTaxi>();
		tds.add(new TariffaATempo("TT", 0, velocitaLimite,  0.1, 100));	
		tds.add(new TariffaADistanza("TD1", velocitaLimite, 1000, 0, 1000, 0.2, 100));
		tds.add(new TariffaADistanza("TD2", velocitaLimite, 1000, 1000, Double.MAX_VALUE, 0.3, 25));
		Tassometro zt = new Tassometro(tds.toArray(new ITariffaTaxi[0]), getFasceOrarie());
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 30, 60, 60, 60);	
		assertEquals(scattoInizialeDiurna + 15 * tds.get(1).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	
	@Test
	public void testTassametro_SoloDistanza_TD1_TD2() {
		ArrayList<ITariffaTaxi> tds = new ArrayList<ITariffaTaxi>();
		tds.add(new TariffaATempo("TT", 0, velocitaLimite,  0.01, 100));	
		tds.add(new TariffaADistanza("TD1", velocitaLimite, 1000, 0, 5, 1, 50));
		tds.add(new TariffaADistanza("TD2", velocitaLimite, 1000, 5, Double.MAX_VALUE, 2, 50));
		Tassometro zt = new Tassometro(tds.toArray(new ITariffaTaxi[0]), getFasceOrarie());
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 30, 60, 60, 60);	
		assertEquals(scattoInizialeDiurna + 5 * tds.get(1).getValoreScatto() +
				5 * tds.get(2).getValoreScatto() +
				20 * tds.get(2).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	
	@Test
	public void testTassametro_Tempo_TD1() {
		ArrayList<ITariffaTaxi> tds = new ArrayList<ITariffaTaxi>();
		tds.add(new TariffaATempo("TT", 0, velocitaLimite,  0.1, 10));	
		tds.add(new TariffaADistanza("TD1", velocitaLimite, 1000, 0, 100, 1, 50));
		tds.add(new TariffaADistanza("TD2", velocitaLimite, 1000, 100, Double.MAX_VALUE, 2, 50));
		Tassometro zt = new Tassometro(tds.toArray(new ITariffaTaxi[0]), getFasceOrarie());
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 10, 60, 60, 60);	
		assertEquals(scattoInizialeDiurna + 6 * tds.get(0).getValoreScatto() +
				20 * tds.get(1).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}
	
	@Test
	public void testTassametro_TD1_Tempo() {
		ArrayList<ITariffaTaxi> tds = new ArrayList<ITariffaTaxi>();
		tds.add(new TariffaATempo("TT", 0, velocitaLimite, 0.1, 10));
		tds.add(new TariffaADistanza("TD1", velocitaLimite, 1000, 0, 100, 1, 50));
		tds.add(new TariffaADistanza("TD2", velocitaLimite, 1000, 100, Double.MAX_VALUE, 2, 50));
		Tassometro zt = new Tassometro(tds.toArray(new ITariffaTaxi[0]), getFasceOrarie());
		
		CorsaTaxi ct = new CorsaTaxi("C1", LocalTime.of(10, 10), 60, 60, 10, 60);	
		assertEquals(scattoInizialeDiurna + 6 * tds.get(0).getValoreScatto() +
				20 * tds.get(1).getValoreScatto(), zt.calcolaCostoCorsa(ct), 0.0001);
	}

}
