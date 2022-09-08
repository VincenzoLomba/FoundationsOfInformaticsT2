package bussy.model.test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bussy.model.Fermata;
import bussy.model.Linea;
import bussy.model.LineaPaP;
import bussy.model.NoSuchStopException;
import bussy.model.Percorso;

public class LineaPaPTest {
	
	Map<Integer,Fermata> mappa22 = new HashMap<>();
	Map<Integer,Fermata> mappa23 = new HashMap<>();
	LineaPaP ventidue, ventitre;
	
	@BeforeEach
	void setup() {
		mappa22 = Map.of(
				0, new Fermata("40", "Porta San Mamolo"), 
				3, new Fermata("42", "Aldini"),
				5, new Fermata("44", "Porta Saragozza - Villa Cassarini")
				);
		mappa23 = Map.of(
				0, new Fermata("47", "Porta Saragozza - Villa Cassarini"),
				2, new Fermata("49", "Aldini"),
				3, new Fermata("45", "Petrarca"),
				5, new Fermata("43", "Porta San Mamolo")
				);
		ventidue = new LineaPaP("22", mappa22);
		ventitre = new LineaPaP("23", mappa23);
	}
	
	@Test
	void testOK0() {
		assertTrue(ventidue.isCapolineaIniziale("Porta San Mamolo"));
		assertTrue(ventidue.isCapolineaFinale("Porta Saragozza - Villa Cassarini"));
		assertTrue(ventitre.isCapolineaIniziale("Porta Saragozza - Villa Cassarini"));
		assertTrue(ventitre.isCapolineaFinale("Porta San Mamolo"));
	}
	

	@Test
	void testOK1() {
		assertEquals("22", ventidue.getId());
		assertEquals(3,    ventidue.getOrarioPassaggioAllaFermata("Aldini"));
		assertEquals("23", ventitre.getId());
		assertEquals(   2, ventitre.getOrarioPassaggioAllaFermata("Aldini"));
	}
	
	@Test
	void testOK2() {
		assertThrows(NoSuchStopException.class, () -> ventidue.getOrarioPassaggioAllaFermata("Pluto"));
	}
	
	@Test
	void testOK3() {
		assertEquals(   mappa22, ventidue.getOrariPassaggioAlleFermate());
		assertNotEquals(mappa22, ventitre.getOrariPassaggioAlleFermate());
	}
	
	@Test
	void testOKPercorso() {
		Optional<Percorso> op1 = ventitre.getPercorso("Porta Saragozza - Villa Cassarini", "Petrarca");
		assertTrue(op1.isPresent()); 
		Optional<Percorso> op2 = ventitre.getPercorso("Porta Saragozza - Villa Cassarini", "xxxx");
		assertFalse(op2.isPresent());  
	}
	
	@Test
	void testIllegalArg2B() {
		assertThrows(IllegalArgumentException.class, () -> new LineaPaP("",   mappa22));
		assertThrows(IllegalArgumentException.class, () -> new LineaPaP(null, mappa22));
		assertThrows(IllegalArgumentException.class, () -> new LineaPaP("32", null));
	}
	@Test
	void testEquals() {
		Linea new22 = new LineaPaP("22", mappa22);
		Linea bis22 = ventidue;
		assertEquals(ventidue, new22);
		assertEquals(ventidue, bis22);
		assertNotEquals(ventidue,ventitre);
		assertNotEquals(ventidue,null);
		assertNotEquals(ventidue,Integer.valueOf(23));
		assertEquals(ventidue.hashCode(),    new22.hashCode());
		assertEquals(ventidue.hashCode(),    bis22.hashCode());
		assertNotEquals(ventidue.hashCode(), ventitre.hashCode());
	}
}

