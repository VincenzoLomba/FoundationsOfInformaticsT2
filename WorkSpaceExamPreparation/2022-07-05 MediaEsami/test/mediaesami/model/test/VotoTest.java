package mediaesami.model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mediaesami.model.Voto;

class VotoTest {

	@Test
	void testOK_ValoriInterni() {
		assertEquals(18, Voto.DICIOTTO.getValue().getAsInt());
		assertEquals(19, Voto.DICIANNOVE.getValue().getAsInt());
		assertEquals(20, Voto.VENTI.getValue().getAsInt());
		assertEquals(21, Voto.VENTUNO.getValue().getAsInt());
		assertEquals(22, Voto.VENTIDUE.getValue().getAsInt());
		assertEquals(23, Voto.VENTITRE.getValue().getAsInt());
		assertEquals(24, Voto.VENTIQUATTRO.getValue().getAsInt());
		assertEquals(25, Voto.VENTICINQUE.getValue().getAsInt());
		assertEquals(26, Voto.VENTISEI.getValue().getAsInt());
		assertEquals(27, Voto.VENTISETTE.getValue().getAsInt());
		assertEquals(28, Voto.VENTOTTO.getValue().getAsInt());
		assertEquals(29, Voto.VENTINOVE.getValue().getAsInt());
		assertEquals(30, Voto.TRENTA.getValue().getAsInt());
		assertEquals(30, Voto.TRENTAELODE.getValue().getAsInt());
		assertTrue(Voto.RESPINTO.getValue().isEmpty());
		assertTrue(Voto.RITIRATO.getValue().isEmpty());
		assertTrue(Voto.IDONEO.getValue().isEmpty());
	}
	
	@Test
	void testOK_VotiInterniGiudizi() {
		assertTrue(Voto.RESPINTO.compareTo(Voto.DICIOTTO)<0);
		assertTrue(Voto.RITIRATO.compareTo(Voto.DICIOTTO)<0);
		assertTrue(Voto.IDONEO.compareTo(Voto.DICIOTTO)<0);
	}
	
	@Test
	void testOK_Superati() {
		assertFalse(Voto.RESPINTO.superato());
		assertFalse(Voto.RITIRATO.superato());
		assertTrue(Voto.TRENTAELODE.superato());
		assertTrue(Voto.TRENTA.superato());
		assertTrue(Voto.VENTINOVE.superato());
		assertTrue(Voto.VENTOTTO.superato());
		assertTrue(Voto.VENTISETTE.superato());
		assertTrue(Voto.VENTISEI.superato());
		assertTrue(Voto.VENTICINQUE.superato());
		assertTrue(Voto.VENTIQUATTRO.superato());
		assertTrue(Voto.VENTITRE.superato());
		assertTrue(Voto.VENTIDUE.superato());
		assertTrue(Voto.VENTUNO.superato());
		assertTrue(Voto.VENTI.superato());
		assertTrue(Voto.DICIANNOVE.superato());
		assertTrue(Voto.DICIOTTO.superato());
		assertTrue(Voto.IDONEO.superato());
	}

	@Test
	void testOK_of() {
		assertEquals(Voto.RESPINTO, Voto.of("RE"));
		assertEquals(Voto.RITIRATO, Voto.of("RT"));
		assertEquals(Voto.IDONEO, 	Voto.of("ID"));
		assertEquals(Voto.TRENTAELODE, Voto.of("30L"));
		assertEquals(Voto.TRENTA, 		Voto.of("30"));
		assertEquals(Voto.VENTINOVE, 	Voto.of("29"));
		assertEquals(Voto.VENTOTTO, 	Voto.of("28"));
		assertEquals(Voto.VENTISETTE, 	Voto.of("27"));
		assertEquals(Voto.VENTISEI,  	Voto.of("26"));
		assertEquals(Voto.VENTICINQUE, 	Voto.of("25"));
		assertEquals(Voto.VENTIQUATTRO, Voto.of("24"));
		assertEquals(Voto.VENTITRE, 	Voto.of("23"));
		assertEquals(Voto.VENTIDUE, 	Voto.of("22"));
		assertEquals(Voto.VENTUNO, 		Voto.of("21"));
		assertEquals(Voto.VENTI, 		Voto.of("20"));
		assertEquals(Voto.DICIANNOVE, 	Voto.of("19"));
		assertEquals(Voto.DICIOTTO, 	Voto.of("18"));
	}
	
	@Test
	void testKO_of_Wrong() {
		assertThrows(IllegalArgumentException.class, () -> Voto.of("Re"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("re"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("REX"));
		
		assertThrows(IllegalArgumentException.class, () -> Voto.of("Rt"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("rt"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("RTX"));
		
		assertThrows(IllegalArgumentException.class, () -> Voto.of("Id"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("id"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("IDX"));
		
		assertThrows(IllegalArgumentException.class, () -> Voto.of("30l"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("30 L"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("30 E LODE"));

		assertThrows(IllegalArgumentException.class, () -> Voto.of("29x"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("x29"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("2x9"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("14"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("15"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("16"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("17"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("31"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("32"));
		assertThrows(IllegalArgumentException.class, () -> Voto.of("33"));
	}
}
