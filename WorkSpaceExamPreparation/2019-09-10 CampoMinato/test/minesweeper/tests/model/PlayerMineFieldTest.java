package minesweeper.tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import minesweeper.model.*;

public class PlayerMineFieldTest {

	@Test
	public void testOKBasic() {
		PlayerMineField f = new PlayerMineField(5);
		//System.out.print(f);
		assertEquals(25, f.getHiddenCellsNumber());
	}
	
	@Test
	public void testOKWithModifications() {
		PlayerMineFieldHelper f = new PlayerMineFieldHelper(5);
		f.setCell(1,1,new Cell(CellType.NUM, 2));
		System.out.print(f);
		assertEquals(24, f.getHiddenCellsNumber());
		f.setCell(3,2,new Cell(CellType.NUM, 5));
		System.out.print(f);
		assertEquals(23, f.getHiddenCellsNumber());
	}
	
}

class PlayerMineFieldHelper extends PlayerMineField{
	public PlayerMineFieldHelper(int n) {
		super(n);
	}
	public void setCell(int row, int col, Cell cell) {
		super.setCell(row, col, cell);
	}
}
