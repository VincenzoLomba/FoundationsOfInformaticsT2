package bussy.model.test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bussy.model.Fermata;
import bussy.model.Linea;
import bussy.model.LineaCircolare;
import bussy.model.NoSuchStopException;
import bussy.model.Percorso;

public class LineaCircolareTest {
	
	Map<Integer,Fermata> mappa32chiusa = new HashMap<>();
	Map<Integer,Fermata> mappa33chiusa = new HashMap<>();
	LineaCircolare trentadue, trentatre;
	
	@BeforeEach
	void setup() {
		mappa32chiusa = Map.of(
				0, new Fermata("40", "Porta San Mamolo"), 
				3, new Fermata("42", "Aldini"),
				5, new Fermata("44", "Porta Saragozza - Villa Cassarini"),
				17, new Fermata("09", "Stazione Centrale"),
				38, new Fermata("40", "Porta San Mamolo")
				);
		mappa33chiusa = Map.of(
				0, new Fermata("47", "Porta Saragozza - Villa Cassarini"),
				2, new Fermata("49", "Aldini"),
				3, new Fermata("45", "Petrarca"),
				5, new Fermata("43", "Porta San Mamolo"),
				26, new Fermata("09", "Stazione Centrale"),
				38, new Fermata("47", "Porta Saragozza - Villa Cassarini")
				);
		trentadue = new LineaCircolare("32", mappa32chiusa);
		trentatre = new LineaCircolare("33", mappa33chiusa);
	}
	
	
	@Test
	void testOKCapolinea() {
		assertTrue(trentadue.isCapolineaIniziale("Porta San Mamolo"));
		assertTrue(trentadue.isCapolineaFinale("Porta San Mamolo"));
		assertTrue(trentatre.isCapolineaIniziale("Porta Saragozza - Villa Cassarini"));
		assertTrue(trentatre.isCapolineaFinale("Porta Saragozza - Villa Cassarini"));
	}
	
	@Test
	void testOKOrariPassaggioFermate() {
		assertEquals("32", trentadue.getId());
		assertEquals(  3,  trentadue.getOrarioPassaggioAllaFermata("Aldini"));
		assertEquals("33", trentatre.getId());
		assertEquals(  2,  trentatre.getOrarioPassaggioAllaFermata("Aldini"));
	}
	
	@Test
	void testThrowFermataInesistente() {
		assertThrows(NoSuchStopException.class, () -> trentadue.getOrarioPassaggioAllaFermata("Pluto"));
	}
	
	@Test
	void testOKGetMappe() {
		assertEquals(mappa32chiusa, trentadue.getOrariPassaggioAlleFermate());
		assertNotEquals(mappa33chiusa, trentadue.getOrariPassaggioAlleFermate());
	}
	
	@Test
	void testOKPercorso() {
		Optional<Percorso> op1 = trentatre.getPercorso("Porta Saragozza - Villa Cassarini", "Petrarca");
		assertTrue(op1.isPresent());
		assertEquals(3, op1.get().getDurata());
		// System.out.println(op1.get());
		Optional<Percorso> op2 = trentatre.getPercorso("Petrarca", "Porta Saragozza - Villa Cassarini");
		assertTrue(op2.isPresent());
		assertEquals(35, op2.get().getDurata());
		// System.out.println(op2.get());
		Optional<Percorso> op3 = trentatre.getPercorso("Porta Saragozza - Villa Cassarini", "xxxx");
		assertFalse(op3.isPresent());  
	}
	
	@Test
	void testIllegalArgVari() {
		assertThrows(IllegalArgumentException.class, () -> new LineaCircolare("", mappa32chiusa));
		assertThrows(IllegalArgumentException.class, () -> new LineaCircolare(null, mappa32chiusa));
		assertThrows(IllegalArgumentException.class, () -> new LineaCircolare("32", null));
	}
	@Test
	void testEquals() {
		Linea ancoraTrentadue = new LineaCircolare("32", mappa32chiusa);
		Linea bis32 = trentadue;
		assertEquals(trentadue, ancoraTrentadue);
		assertEquals(trentadue, bis32);
		assertNotEquals(trentadue,trentatre);
		assertNotEquals(trentadue,null);
		assertNotEquals(trentadue,Integer.valueOf(33));
		assertEquals(trentadue.hashCode(),ancoraTrentadue.hashCode());
		assertEquals(trentadue.hashCode(),bis32.hashCode());
		assertNotEquals(trentadue.hashCode(),trentatre.hashCode());
	}
}

