package minesweeper.tests.model;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import minesweeper.model.*;

public class MyCellTest {

	@Test
	public void testOK() {
		new Cell(CellType.MINE);
		Cell c = new Cell(CellType.NUM);
		c.setAdjacents(2);
		assertEquals(2, c.getAdjacents());
		assertEquals("2", c.toString());
	}
	
	@Test
	public void testOK2() {
		Cell c = new Cell(CellType.NUM, 3);
		assertEquals(3, c.getAdjacents());
		assertEquals("3", c.toString());
	}

	@Test
	public void testOK3() {
		Cell c = new Cell(CellType.HIDDEN);
		assertEquals("?", c.toString());
	}

	@Test
	public void testOK4() {
		Cell c = new Cell(CellType.MINE);
		assertEquals("M", c.toString());
	}

	@Test
	public void testKO() {
		assertThrows(UnsupportedOperationException.class, () -> new Cell(CellType.MINE, 3));
	}

	@Test
	public void testKO2() {
		assertThrows(UnsupportedOperationException.class, () -> { Cell c =new Cell(CellType.MINE); c.setAdjacents(4); });
	}

}
