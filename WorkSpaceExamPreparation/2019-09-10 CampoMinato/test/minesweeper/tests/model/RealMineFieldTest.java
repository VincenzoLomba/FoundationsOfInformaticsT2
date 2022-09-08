package minesweeper.tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import minesweeper.model.*;

public class RealMineFieldTest {

	@Test
	public void testOK() {
		RealMineField f = new RealMineField(5, 3);
		//System.out.print(f);
		assertEquals(3, f.getMinesNumber());
	}
	
	@Test
	public void testOKDefault() {
		RealMineField f = new RealMineField(8);
		//System.out.print(f);
		assertEquals(10, f.getMinesNumber());
	}
	
}
