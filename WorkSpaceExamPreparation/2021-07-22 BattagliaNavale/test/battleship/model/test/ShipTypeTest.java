package battleship.model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import battleship.model.ShipType;

class ShipTypeTest {

	@Test
	void testOK() {
		assertEquals(1, ShipType.SOMMERGIBILE.getLength());
		assertEquals(2, ShipType.CACCIATORPEDINIERE.getLength());
		assertEquals(3, ShipType.INCROCIATORE.getLength());
		assertEquals(4, ShipType.PORTAEREI.getLength());
		//
		assertEquals(ShipType.SOMMERGIBILE, ShipType.of(1));
		assertEquals(ShipType.CACCIATORPEDINIERE, ShipType.of(2));
		assertEquals(ShipType.INCROCIATORE, ShipType.of(3));
		assertEquals(ShipType.PORTAEREI, ShipType.of(4));
	}
	
}
