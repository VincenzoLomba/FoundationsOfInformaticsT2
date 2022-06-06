package battleship.model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import battleship.model.ShipItem;

class ShipItemTest {

	@Test
	void testOK() {
		assertEquals("<", ShipItem.LEFT.getValue());
		assertEquals(">", ShipItem.RIGHT.getValue());
		assertEquals("x", ShipItem.CENTRE.getValue());
		assertEquals("^", ShipItem.UP.getValue());
		assertEquals("v", ShipItem.DOWN.getValue());
		assertEquals("~", ShipItem.SEA.getValue());
		assertEquals("#", ShipItem.EMPTY.getValue());
		assertEquals("o", ShipItem.SINGLE.getValue());
		//
		assertEquals(ShipItem.LEFT, ShipItem.of("<"));
		assertEquals(ShipItem.RIGHT, ShipItem.of(">"));
		assertEquals(ShipItem.CENTRE, ShipItem.of("x"));
		assertEquals(ShipItem.UP, ShipItem.of("^"));
		assertEquals(ShipItem.DOWN, ShipItem.of("v"));
		assertEquals(ShipItem.SEA, ShipItem.of("~"));
		assertEquals(ShipItem.EMPTY, ShipItem.of("#"));
		assertEquals(ShipItem.SINGLE, ShipItem.of("o"));
		assertThrows(IllegalArgumentException.class, () -> ShipItem.of("U"));
	}

}
