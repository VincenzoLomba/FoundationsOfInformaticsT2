package minesweeper.tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import minesweeper.model.*;

public class GameTest {

	@Test
	public void testOKBasic() {
		Game g = new Game(5,3);
		//System.out.print(g);
		assertEquals(5, g.getSize());
		assertEquals(3, g.getMinesNumber());
	}
	
	@Test
	public void testOKHelperBasic() {
		Game g = new Game(new TesterRealMineField());
		//System.out.print(g);
		assertEquals(5, g.getSize());
		assertEquals(3, g.getMinesNumber());
	}

	@Test
	public void testOKHelperWithOneMove_NoAdjacents() {
		String STATUS_AFTER_ONE_MOVE = 
				"?	?	?	?	?" + System.lineSeparator() + 
				"?	1	?	?	?" + System.lineSeparator() + 
				"?	?	?	?	?" + System.lineSeparator() + 
				"?	?	?	?	?" + System.lineSeparator() + 
				"?	?	?	?	?" + System.lineSeparator();
		Game g = new Game(new TesterRealMineField());
		//System.out.print(g);
		assertEquals(GameStatus.CONTINUING,g.revealCell(1, 1));
		assertEquals(STATUS_AFTER_ONE_MOVE, g.getPlayerMineField());
		//System.out.print(g);		
	}

	@Test
	public void testOKHelperWithOneMove_WithAdjacents() {
		String STATUS_AFTER_ONE_MOVE = 
				"0	1	?	1	0" + System.lineSeparator() + 
				"0	1	1	1	0" + System.lineSeparator() + 
				"0	0	0	0	0" + System.lineSeparator() + 
				"1	1	2	1	1" + System.lineSeparator() + 
				"?	?	?	?	?" + System.lineSeparator();
		Game g = new Game(new TesterRealMineField());
		assertEquals(GameStatus.CONTINUING,g.revealCell(2, 2));
		System.out.print(g);		
		assertEquals(STATUS_AFTER_ONE_MOVE, g.getPlayerMineField());
	}
}


class TesterRealMineField extends RealMineField {
	public TesterRealMineField() {
		super(5,3);
		setBoard();
	}
	public void setBoard() {
		/*
		0	1	M	1	0
		0	1	1	1	0
		0	0	0	0	0
		1	1	2	1	1
		1	M	2	M	1
		*/
		setCell(0,0, new Cell(CellType.NUM,0));
		setCell(0,1, new Cell(CellType.NUM,1));
		setCell(0,2, new Cell(CellType.MINE));
		setCell(0,3, new Cell(CellType.NUM,1));
		setCell(0,4, new Cell(CellType.NUM,0));

		setCell(1,0, new Cell(CellType.NUM,0));
		setCell(1,1, new Cell(CellType.NUM,1));
		setCell(1,2, new Cell(CellType.NUM,1));
		setCell(1,3, new Cell(CellType.NUM,1));
		setCell(1,4, new Cell(CellType.NUM,0));
		
		setCell(2,0, new Cell(CellType.NUM,0));
		setCell(2,1, new Cell(CellType.NUM,0));
		setCell(2,2, new Cell(CellType.NUM,0));
		setCell(2,3, new Cell(CellType.NUM,0));
		setCell(2,4, new Cell(CellType.NUM,0));
		
		setCell(3,0, new Cell(CellType.NUM,1));
		setCell(3,1, new Cell(CellType.NUM,1));
		setCell(3,2, new Cell(CellType.NUM,2));
		setCell(3,3, new Cell(CellType.NUM,1));
		setCell(3,4, new Cell(CellType.NUM,1));

		setCell(4,0, new Cell(CellType.NUM,1));
		setCell(4,1, new Cell(CellType.MINE));
		setCell(4,2, new Cell(CellType.NUM,2));
		setCell(4,3, new Cell(CellType.MINE));
		setCell(4,4, new Cell(CellType.NUM,1));
	}
}
