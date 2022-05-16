package zannotaxi.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

import zannotaxi.model.CorsaTaxi;

public class CorsaTaxiTests {

	@Test
	public void testCorsaTaxi_DurataCostante() {
		CorsaTaxi ct = new CorsaTaxi("corsa1", LocalTime.of(10, 20), new double[] { 60, 120, 240 },
				new int[] { 60, 60, 60 });

		int totaleSecondi = 60 + 60 + 60;
		assertEquals(totaleSecondi, ct.getRilevazioniDistanze().length - 1);
		
		double totaleDistanza = 60 * 60 / 3.6 + 60 * 120 / 3.6 + 60 * 240 / 3.6;
		assertEquals(totaleDistanza, ct.getRilevazioniDistanze()[ct.getRilevazioniDistanze().length - 1], 0.0001);		
	}
	
	@Test
	public void testCorsaTaxi_DurataCrescente() {
		CorsaTaxi ct = new CorsaTaxi("corsa2", LocalTime.of(10, 20), new double[] { 60, 120, 240 },
				new int[] { 10, 20, 30 });

		int totaleSecondi = 10 + 20 + 30;
		assertEquals(totaleSecondi, ct.getRilevazioniDistanze().length - 1);
		
		double totaleDistanza = 10 * 60 / 3.6 + 20 * 120 / 3.6 + 30 * 240 / 3.6;
		assertEquals(totaleDistanza, ct.getRilevazioniDistanze()[ct.getRilevazioniDistanze().length - 1], 0.0001);		
	}

}
