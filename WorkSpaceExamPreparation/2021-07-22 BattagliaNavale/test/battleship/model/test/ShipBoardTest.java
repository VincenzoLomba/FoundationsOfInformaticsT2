package battleship.model.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import battleship.model.ShipBoard;
import battleship.model.ShipItem;

class ShipBoardTest {
	
	ShipBoard board;
	
	@BeforeEach
	public void setUp()
	{
		board = new ShipBoard(
				  "~ < x x > ~ ~ ^\n"
				+ "~ ~ ~ ~ ~ ~ ~ v\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\n"
				+ "~ x ~ v ~ ~ < >\n"
				+ "~ v ~ ~ ~ ~ ~ ~\n"
				+ "~ ~ ~ o ~ ~ ~ ~\n"
				+ "~ ~ ~ ~ ~ < x >\n"
				+ "~ o ~ o ~ ~ ~ ~");
	}
	
	@Test
	void testCtor0_OK() {
		ShipBoard board = new ShipBoard();
		assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, board.getCountingRow());
		assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, board.getCountingCol());
	}
	
	@Test
	void testCtorGetSize_OK() {
		assertArrayEquals(new int[] {0,5,1,5,1,1,2,4}, board.getCountingRow());
		assertArrayEquals(new int[] {5,1,2,4,1,1,3,2}, board.getCountingCol());
	}
	
	@Test
	void testCountShipsInRow_OK() {
		assertEquals(5, board.countShipCellsInRow(0));
		assertEquals(1, board.countShipCellsInRow(1));
		assertEquals(2, board.countShipCellsInRow(2));
		assertEquals(4, board.countShipCellsInRow(3));
		assertEquals(1, board.countShipCellsInRow(4));
		assertEquals(1, board.countShipCellsInRow(5));
		assertEquals(3, board.countShipCellsInRow(6));
		assertEquals(2, board.countShipCellsInRow(7));
	}
	
	@Test
	void testCountShipsInCol_OK() {
		assertEquals(0, board.countShipCellsInColumn(0));
		assertEquals(5, board.countShipCellsInColumn(1));
		assertEquals(1, board.countShipCellsInColumn(2));
		assertEquals(5, board.countShipCellsInColumn(3));
		assertEquals(1, board.countShipCellsInColumn(4));
		assertEquals(1, board.countShipCellsInColumn(5));
		assertEquals(2, board.countShipCellsInColumn(6));
		assertEquals(4, board.countShipCellsInColumn(7));
	}
	
	@Test
	void testClearCell_OK() {
		board.clearCell(0,1);
		assertEquals(4, board.countShipCellsInRow(0));
		board.clearCell(0,2);
		assertEquals(3, board.countShipCellsInRow(0));
		board.clearCell(0,3);
		assertEquals(2, board.countShipCellsInRow(0));
		board.clearCell(0,4);
		assertEquals(1, board.countShipCellsInRow(0));
		board.setCell(0,1,ShipItem.SINGLE);
		assertEquals(2, board.countShipCellsInRow(0));
	}
	
	@Test
	void testClearCell_KO_ColumnExceedingDIM() {
		assertThrows(IllegalArgumentException.class, () -> board.clearCell(0,8));
	}
	@Test
	void testClearCell_KO_RowExceedingDIM() {
		assertThrows(IllegalArgumentException.class, () -> board.clearCell(8,0));
	}
	@Test
	void testClearCell_KO_ColumnNegative() {
		assertThrows(IllegalArgumentException.class, () -> board.clearCell(0,-1));
	}
	@Test
	void testClearCell_KO_RowNegative() {
		assertThrows(IllegalArgumentException.class, () -> board.clearCell(0-1,7));
	}

	@Test
	void testGetSize_OK() {
		assertEquals(8, board.getSize());
	}
	
	@Test
	void testSetCell_OK() {
		board.setCell(0,1,ShipItem.SINGLE);
		assertEquals(5, board.countShipCellsInRow(0));
		board.setCell(1,1,ShipItem.SINGLE);
		assertEquals(2, board.countShipCellsInRow(1));
	}
	@Test
	void testSetCell_KO_ColumnExceedingDIM() {
		assertThrows(IllegalArgumentException.class, () -> board.setCell(0,8,ShipItem.CENTRE));
	}
	@Test
	void testSetCell_KO_RowExceedingDIM() {
		assertThrows(IllegalArgumentException.class, () -> board.setCell(8,0,ShipItem.CENTRE));
	}
	@Test
	void testSetCell_KO_ColumnNegative() {
		assertThrows(IllegalArgumentException.class, () -> board.setCell(0,-1,ShipItem.CENTRE));
	}
	@Test
	void testSetCell_KO_RowNegative() {
		assertThrows(IllegalArgumentException.class, () -> board.setCell(0-1,7,ShipItem.CENTRE));
	}
	
	@Test
	void testGetCell_OK() {
		assertEquals(ShipItem.LEFT, board.getCell(0,1));
		assertEquals(ShipItem.LEFT, board.getCell(3,6));
		assertEquals(ShipItem.LEFT, board.getCell(6,5));
		
		assertEquals(ShipItem.RIGHT, board.getCell(0,4));
		assertEquals(ShipItem.RIGHT, board.getCell(3,7));
		assertEquals(ShipItem.RIGHT, board.getCell(6,7));
		
		assertEquals(ShipItem.SINGLE, board.getCell(5,3));
		assertEquals(ShipItem.SINGLE, board.getCell(7,1));
		assertEquals(ShipItem.SINGLE, board.getCell(7,3));
		
		assertEquals(ShipItem.UP, board.getCell(0,7));
		assertEquals(ShipItem.UP, board.getCell(2,1));
		assertEquals(ShipItem.UP, board.getCell(2,3));
		
		assertEquals(ShipItem.DOWN, board.getCell(1,7));
		assertEquals(ShipItem.DOWN, board.getCell(3,3));
		assertEquals(ShipItem.DOWN, board.getCell(4,1));

		assertEquals(ShipItem.SEA, board.getCell(0,0));
		assertEquals(ShipItem.SEA, board.getCell(1,1));
		assertEquals(ShipItem.SEA, board.getCell(2,2));
		assertEquals(ShipItem.SEA, board.getCell(4,4));
		assertEquals(ShipItem.SEA, board.getCell(5,5));
		assertEquals(ShipItem.SEA, board.getCell(7,7));
		
		assertEquals(ShipItem.CENTRE, board.getCell(0,3));
		assertEquals(ShipItem.CENTRE, board.getCell(0,2));
		assertEquals(ShipItem.CENTRE, board.getCell(3,1));
		assertEquals(ShipItem.CENTRE, board.getCell(6,6));
	}
	@Test
	void testGetCell_KO_ColumnExceedingDIM() {
		assertThrows(IllegalArgumentException.class, () -> board.getCell(0,8));
	}
	@Test
	void testGetCell_KO_RowExceedingDIM() {
		assertThrows(IllegalArgumentException.class, () -> board.getCell(8,0));
	}
	@Test
	void testGetCell_KO_ColumnNegative() {
		assertThrows(IllegalArgumentException.class, () -> board.getCell(0,-1));
	}
	@Test
	void testGetCell_KO_RowNegative() {
		assertThrows(IllegalArgumentException.class, () -> board.getCell(0-1,7));
	}
	
	@Test
	void testToString_OK() {
		IntStream st = board.toString().chars();
		var newlines = new Object() {int value=0;};
		var spaces = new Object() {int value=0;};
		int n = board.getSize();	
		st.peek(ch1 -> newlines.value += (ch1=='\n' ? 1 : 0))
		  .peek(ch2 -> spaces.value   += (ch2==' '  ? 1 : 0))
		  .boxed().collect(Collectors.toList()); 
		// NB: list result is thrown away, we just need a terminal op to force peek execution
		assertEquals(n, newlines.value);
		assertEquals(n*(n-1), spaces.value);
	}

}
