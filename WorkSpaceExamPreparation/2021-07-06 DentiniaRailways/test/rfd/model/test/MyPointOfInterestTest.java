package rfd.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import rfd.model.MyPointOfInterest;


public class MyPointOfInterestTest {

	@Test
	public void testOK1() {
		MyPointOfInterest poi = new MyPointOfInterest("Santarcangelo di Romagna", "101+273");
		assertEquals("Santarcangelo di Romagna", poi.getStationName());
		assertEquals("101+273", poi.getKm());
		assertEquals(101.273, poi.getKmAsNum(), 0.001);
	}

	@Test
	public void testOK2() {
		MyPointOfInterest poi = new MyPointOfInterest("RiminiFiera", "106+969");
		assertEquals("RiminiFiera", poi.getStationName());
		assertEquals("106+969", poi.getKm());
		assertEquals(106.969, poi.getKmAsNum(), 0.001);
	}

	@Test
	public void testOK3() {
		MyPointOfInterest poi = new MyPointOfInterest("Bologna Centrale", "0+000");
		assertEquals("Bologna Centrale", poi.getStationName());
		assertEquals("0+000", poi.getKm());
		assertEquals(0.0, poi.getKmAsNum(), 0.001);
	}
	
	@Test
	public void testKO_BadSeparator() {
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101-273"));
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101.273"));
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101,273"));
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101 273"));
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101\t273"));
	}
	
	@Test
	public void testKO_MetresNotThreeDigits() {
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101+27"));
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101+0"));
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101+0001"));
	}

	@Test
	public void testKO_MetresMissing() {
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101"));
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "101+"));
	}

	@Test
	public void testKO_KmMissing() {
		assertThrows(IllegalArgumentException.class, () -> new MyPointOfInterest("Topolinia", "+270"));
	}

}
