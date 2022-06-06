package battleship.persistence.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import battleship.model.ShipBoard;
import battleship.model.ShipItem;
import battleship.persistence.BadFileFormatException;
import battleship.persistence.BattleshipReader;
import battleship.persistence.MyBattleshipReader;

public class MyBattleshipReaderTest {
	
	@Test
	public void testOK() throws BadFileFormatException, IOException {
		String fakeFile = 
				"~ < x x > ~ ~ ^\r\n"
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\r\n"
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n"
				+ "~ o ~ o ~ ~ ~ ~";
		StringReader reader = new StringReader(fakeFile);
		BattleshipReader myReader = new MyBattleshipReader();
	    ShipBoard board= myReader.getSolutionBoard(reader);
	    //
	    assertEquals(8, board.getSize());
	    //
	    assertArrayEquals(new int[] {0,5,1,5,1,1,2,4}, board.getCountingRow());
		assertArrayEquals(new int[] {5,1,2,4,1,1,3,2}, board.getCountingCol());
		//
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
	public void testKO_WrongNumberOfItemsInFirstLine_MissingElement() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~  ^\r\n" // one item missing in first line
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\r\n"
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n"
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_WrongNumberOfItemsInFirstLine_TwoItemsMerged() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~~ ^\r\n" // space missing in first line, so two items become one
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\r\n"
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n"
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_WrongNumberOfItemsInFirstLine_MissingEOLN() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~ ~ ^" // newline missing in first line, so it merges with second line
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\r\n"
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n"
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_WrongNumberOfItemsInLastLine_MissingEOLN() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~ ~ ^\r\n" 
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\r\n"
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >" // newline missing in this line, so the last two lines merge 
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_ExtraEOLN() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~ ~ ^\r\n" 
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\r\n"
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n\r\n" // double newline, so next line is empty and there is an extra unexpected line at the end 
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_IllegalItemInLastLine() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~ ~ ^\r\n" 
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\r\n"
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n" 
				+ "~ o ~ o ~ ~ w ~"; // illegal w
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_IllegalItemInThirdLine() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~ ~ ^\r\n" 
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ \t ~ ~\r\n" // illegal tab
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n" 
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_IllegalItemsInSeveralPossiblePlaces() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~ ~ ^\r\n" 
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ = ~ ~\r\n" // illegal =
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ A ~ ~ ~ ~\r\n" // illegal A
				+ "~ ~ ~ o ~ q ~ ~\r\n" // illegal q
				+ "~ ~ ~ ~ ~ < x >\r\n" 
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_IllegalEmptyItemInSolutionBoard() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "~ < x x > ~ ~ ^\r\n" 
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ # ~ ~\r\n" // illegal #
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n" 
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}
	

	@Test
	public void testKO_IllegalSeaItemInPlayerBoard() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "# # # # > # # ^\r\n"
				  + "# # ~ # # # # #\r\n"	// illegal ~
				  + "# # # # # # # #\r\n"
				  + "# x # # # # < #\r\n"
				  + "# # # # # # # #\r\n"
				  + "# # # # # # # #\r\n"
				  + "# # # # # # # #\r\n"
				  + "# # # # # # # #";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getPlayerBoard(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_ExtraLine() throws BadFileFormatException, IOException {
		String fakeFile = 
				"~ < x x > ~ ~ ^\r\n"
				+ "~ ~ ~ ~ ~ ~ ~ v\r\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\r\n"
				+ "~ x ~ v ~ ~ < >\r\n"
				+ "~ v ~ ~ ~ ~ ~ ~\r\n"
				+ "~ ~ ~ o ~ ~ ~ ~\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n"
				+ "~ ~ ~ ~ ~ < x >\r\n" // this line duplicates so 9 lines instead of 8
				+ "~ o ~ o ~ ~ ~ ~";
		BattleshipReader myReader = new MyBattleshipReader();
	    assertThrows(BadFileFormatException.class, () -> myReader.getSolutionBoard(new StringReader(fakeFile)));
	}
}
