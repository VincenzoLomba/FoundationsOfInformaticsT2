package bussy.model.test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.SortedSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bussy.model.Cercatore;
import bussy.model.Fermata;
import bussy.model.Linea;
import bussy.model.LineaCircolare;
import bussy.model.LineaPaP;
import bussy.model.MyCercatore;
import bussy.model.Percorso;


public class MyCercatoreTest {
	
	Map<Integer,Fermata> mappa32chiusa = new HashMap<>();
	Map<Integer,Fermata> mappa33chiusa = new HashMap<>();
	Map<Integer,Fermata> mappaFakeMetro = new HashMap<>();
	Map<String,Linea> mappaLinee;
	Cercatore cercatore;
	
	@BeforeEach
	void setup() {
		
		mappa32chiusa = Map.of(
				0, new Fermata("40", "Porta San Mamolo"), 
				3, new Fermata("42", "Aldini"),
				5, new Fermata("44", "Porta Saragozza - Villa Cassarini"),
				17, new Fermata("16", "Stazione Centrale"),
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
		mappaFakeMetro = Map.of(
				0, new Fermata("40", "Porta San Mamolo"), 
				3, new Fermata("803", "Tribunale"),
				5, new Fermata("701", "Piazza Malpighi"),
				7, new Fermata("452", "Marconi"),
				10, new Fermata("474", "Stazione Centrale")
				);
		mappaLinee = new HashMap<>();
		mappaLinee.put("32", new LineaCircolare("32", mappa32chiusa));
		mappaLinee.put("33", new LineaCircolare("33", mappa33chiusa));
		mappaLinee.put("M1", new LineaPaP("M1", mappaFakeMetro));
		cercatore = new MyCercatore(mappaLinee);
	}
	
	@Test
	void testPercorsiInTondo() {
		
		SortedSet<Percorso> percorsi = cercatore.cercaPercorsi("Aldini", "Porta Saragozza - Villa Cassarini", OptionalInt.of(5));
		//	System.out.println(percorsi);
		assertEquals("32", percorsi.first().getLinea().getId());
		assertEquals(   2, percorsi.first().getDurata());
		//
		percorsi = cercatore.cercaPercorsi("Aldini", "Porta San Mamolo", OptionalInt.of(5));
		//	System.out.println(percorsi);
		assertEquals("33", percorsi.first().getLinea().getId());
		assertEquals(  3,  percorsi.first().getDurata());
		//
		percorsi = cercatore.cercaPercorsi("Porta San Mamolo", "Porta San Mamolo", OptionalInt.of(45));
		//	System.out.println(percorsi);
		assertEquals("32", percorsi.first().getLinea().getId());
		assertEquals( 38,  percorsi.first().getDurata());
	}
		
	@Test
	void testPercorsiSecantiNord() {
		
		SortedSet<Percorso> percorsi = cercatore.cercaPercorsi("Stazione Centrale", "Porta San Mamolo", OptionalInt.of(45));
		assertEquals(   2, percorsi.size());
		for (Percorso p : percorsi) {
			System.out.println(p);
			switch(p.getLinea().getId()) {
				case "32": assertEquals( 21,  p.getDurata()); break;
				case "33": assertEquals( 17,  p.getDurata()); break;
			}
		}			
	}
	
	@Test
	void testPercorsiSecantiSud() {
		
		SortedSet<Percorso> percorsi = cercatore.cercaPercorsi("Porta San Mamolo", "Stazione Centrale", OptionalInt.of(45));
		assertEquals(3, percorsi.size());
		for (Percorso p : percorsi) {
			System.out.println(p);
			switch(p.getLinea().getId()) {
				case "32": assertEquals( 17,  p.getDurata()); break;
				case "33": assertEquals( 21,  p.getDurata()); break;
				case "M1": assertEquals( 10,  p.getDurata()); break;
			}
		}			
	}

	@Test
	void testGetMappaLinee() {
		
		assertEquals(mappaLinee, cercatore.getMappaLinee());
	}
	
}

