package bussy.model.test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bussy.model.Fermata;

public class FermataTest {
	
	@Test
	void testOK() {
		Fermata f1 = new Fermata("42", "Aldini");
		Fermata f2 = new Fermata("49", "Aldini");
		Fermata f3 = new Fermata("44", "Porta Saragozza - Villa Cassarini");
		Fermata f4 = new Fermata("47", "Porta Saragozza - Villa Cassarini");
		assertEquals("Aldini", f1.getNome());
		assertEquals("Aldini", f2.getNome());
		assertEquals("Porta Saragozza - Villa Cassarini", f3.getNome());
		assertEquals("Porta Saragozza - Villa Cassarini", f4.getNome());
		assertEquals("42", f1.getId());
	}
	
	@Test
	void testIllegalArg2B() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata("42", null));
		assertThrows(IllegalArgumentException.class, () -> new Fermata("", "Aldeeneee"));
		assertThrows(IllegalArgumentException.class, () -> new Fermata(null, "Porta porta"));
		assertThrows(IllegalArgumentException.class, () -> new Fermata("", null));
		assertThrows(IllegalArgumentException.class, () -> new Fermata("44", ""));
	}
	
	@Test
	void testEquals() {
		Fermata f1 = new Fermata("42", "Aldini");
		Fermata f2 = new Fermata("42", "Aldini");
		Fermata f3 = f1;
		Fermata f4 = new Fermata("99", "Aldini");
		assertEquals(f1,f2);
		assertEquals(f1,f3);
		assertEquals(f3,f2);
		assertNotEquals(f1,f4);
		assertNotEquals(f1,null);
		assertNotEquals(f1,Integer.valueOf(33));
		assertEquals(f1.hashCode(),f2.hashCode());
		assertEquals(f1.hashCode(),f3.hashCode());
		assertNotEquals(f1.hashCode(),f4.hashCode());
	}
	
}

