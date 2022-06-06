package battleship.controller.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import battleship.controller.Controller;
import battleship.controller.MyController;
import battleship.model.ShipBoard;
import battleship.model.ShipItem;
import battleship.model.ShipType;

public class MyControllerTest {
	private Controller controller;
	
	@BeforeEach
	public void setUp()
	{
		ShipBoard solution = new ShipBoard(
				  "~ < x x > ~ ~ ^\n"
				+ "~ ~ ~ ~ ~ ~ ~ v\n"
				+ "~ ^ ~ ^ ~ ~ ~ ~\n"
				+ "~ x ~ v ~ ~ < >\n"
				+ "~ v ~ ~ ~ ~ ~ ~\n"
				+ "~ ~ ~ o ~ ~ ~ ~\n"
				+ "~ ~ ~ ~ ~ < x >\n"
				+ "~ o ~ o ~ ~ ~ ~");
		ShipBoard player = new ShipBoard(
				  "# # # # > # # ^\r\n"
				+ "# # # # # # # #\r\n"
				+ "# # # # # # # #\r\n"
				+ "# x # # # # < #\r\n"
				+ "# # # # # # # #\r\n"
				+ "# # # # # # # #\r\n"
				+ "# # # # # # # #\r\n"
				+ "# # # # # # # #");
		controller= new MyController(solution, player);
	}
	

	@Test
	public void testGetSize() {
		assertEquals(8, controller.getSize());
	}
	
	
	@Test
	public void testGetCell() {
		assertEquals(ShipItem.LEFT,   controller.getCurrentCellItem(3,6));		
		assertEquals(ShipItem.RIGHT,  controller.getCurrentCellItem(0,4));
		assertEquals(ShipItem.CENTRE, controller.getCurrentCellItem(3,1));
		assertEquals(ShipItem.UP,     controller.getCurrentCellItem(0,7));
		
		assertEquals(ShipItem.EMPTY, controller.getCurrentCellItem(0,0));
		assertEquals(ShipItem.EMPTY, controller.getCurrentCellItem(1,1));
		assertEquals(ShipItem.EMPTY, controller.getCurrentCellItem(2,2));
		assertEquals(ShipItem.EMPTY, controller.getCurrentCellItem(4,4));
		assertEquals(ShipItem.EMPTY, controller.getCurrentCellItem(5,5));
		assertEquals(ShipItem.EMPTY, controller.getCurrentCellItem(7,7));
	}
	
	@Test
	public void testSetCell() {
		controller.setCurrentCellItem(0,0,ShipItem.SEA);
		controller.setCurrentCellItem(1,1,ShipItem.SEA);
		controller.setCurrentCellItem(3,0,ShipItem.SEA);
		controller.setCurrentCellItem(3,2,ShipItem.SEA);
		controller.setCurrentCellItem(6,3,ShipItem.SEA);

		assertEquals(ShipItem.SEA, controller.getCurrentCellItem(0,0));
		assertEquals(ShipItem.SEA, controller.getCurrentCellItem(1,1));
		assertEquals(ShipItem.SEA, controller.getCurrentCellItem(3,0));
		assertEquals(ShipItem.SEA, controller.getCurrentCellItem(3,2));
		assertEquals(ShipItem.SEA, controller.getCurrentCellItem(6,3));
	}
	
	@Test
	void testCtorGetSize_OK() {
		assertArrayEquals(new int[] {0,5,1,5,1,1,2,4}, controller.getCountingRow());
		assertArrayEquals(new int[] {5,1,2,4,1,1,3,2}, controller.getCountingColumn());
	}
	
	@Test
	public void testGetSolutionCell() {		
		assertEquals(ShipItem.LEFT, controller.getSolutionCellItem(0,1));
		assertEquals(ShipItem.LEFT, controller.getSolutionCellItem(3,6));
		assertEquals(ShipItem.LEFT, controller.getSolutionCellItem(6,5));
		
		assertEquals(ShipItem.RIGHT, controller.getSolutionCellItem(0,4));
		assertEquals(ShipItem.RIGHT, controller.getSolutionCellItem(3,7));
		assertEquals(ShipItem.RIGHT, controller.getSolutionCellItem(6,7));
		
		assertEquals(ShipItem.SINGLE, controller.getSolutionCellItem(5,3));
		assertEquals(ShipItem.SINGLE, controller.getSolutionCellItem(7,1));
		assertEquals(ShipItem.SINGLE, controller.getSolutionCellItem(7,3));
		
		assertEquals(ShipItem.UP, controller.getSolutionCellItem(0,7));
		assertEquals(ShipItem.UP, controller.getSolutionCellItem(2,1));
		assertEquals(ShipItem.UP, controller.getSolutionCellItem(2,3));
		
		assertEquals(ShipItem.DOWN, controller.getSolutionCellItem(1,7));
		assertEquals(ShipItem.DOWN, controller.getSolutionCellItem(3,3));
		assertEquals(ShipItem.DOWN, controller.getSolutionCellItem(4,1));

		assertEquals(ShipItem.SEA, controller.getSolutionCellItem(0,0));
		assertEquals(ShipItem.SEA, controller.getSolutionCellItem(1,1));
		assertEquals(ShipItem.SEA, controller.getSolutionCellItem(2,2));
		assertEquals(ShipItem.SEA, controller.getSolutionCellItem(4,4));
		assertEquals(ShipItem.SEA, controller.getSolutionCellItem(5,5));
		assertEquals(ShipItem.SEA, controller.getSolutionCellItem(7,7));
		
		assertEquals(ShipItem.CENTRE, controller.getSolutionCellItem(0,3));
		assertEquals(ShipItem.CENTRE, controller.getSolutionCellItem(0,2));
		assertEquals(ShipItem.CENTRE, controller.getSolutionCellItem(3,1));
		assertEquals(ShipItem.CENTRE, controller.getSolutionCellItem(6,6));
	}
	
	@Test
	public void testGetShipCount() {
		Map<ShipType, Integer> map = controller.getShipCount();
		assertEquals(3, map.get(ShipType.SOMMERGIBILE));
		assertEquals(3, map.get(ShipType.CACCIATORPEDINIERE));
		assertEquals(2, map.get(ShipType.INCROCIATORE));
		assertEquals(1, map.get(ShipType.PORTAEREI));
	} 
	
	@Test
	public void testVerify_OK_1() {
		assertEquals(0, controller.verify());
		controller.setCurrentCellItem(0,0,ShipItem.SEA);
		controller.setCurrentCellItem(1,1,ShipItem.SEA);
		controller.setCurrentCellItem(3,0,ShipItem.SEA);
		controller.setCurrentCellItem(3,2,ShipItem.SEA);
		controller.setCurrentCellItem(6,3,ShipItem.SEA);
		assertEquals(0, controller.verify());
		assertFalse(controller.isGameOver());
	} 
	
	@Test
	public void testVerify_OK_2() {
		assertEquals(0, controller.verify());
		controller.setCurrentCellItem(0,1,ShipItem.LEFT);
		controller.setCurrentCellItem(0,2,ShipItem.CENTRE);
		controller.setCurrentCellItem(2,1,ShipItem.UP);
		controller.setCurrentCellItem(2,3,ShipItem.UP);
		controller.setCurrentCellItem(4,1,ShipItem.DOWN);
		controller.setCurrentCellItem(5,3,ShipItem.SINGLE);
		assertEquals(0, controller.verify());
		assertFalse(controller.isGameOver());
	} 
	
	@Test
	public void testVerify_OK_3() {
		assertEquals(0, controller.verify());
		// check corner cases in first & last row/column, no empty cells
		controller.setCurrentCellItem(0,0,ShipItem.SEA);
		controller.setCurrentCellItem(0,7,ShipItem.UP);
		controller.setCurrentCellItem(7,0,ShipItem.SEA);
		controller.setCurrentCellItem(7,7,ShipItem.SEA);
		assertEquals(0, controller.verify());
		assertFalse(controller.isGameOver());
	}
	
	@Test
	public void testVerify_OK_4() {
		assertEquals(0, controller.verify());
		// check corner cases in first & last row/column, with empty cells
		controller.setCurrentCellItem(0,0,ShipItem.EMPTY);
		controller.setCurrentCellItem(0,7,ShipItem.UP);
		controller.setCurrentCellItem(7,0,ShipItem.EMPTY);
		controller.setCurrentCellItem(7,7,ShipItem.SEA);
		assertEquals(0, controller.verify());
		assertFalse(controller.isGameOver());
	} 

	
	@Test
	public void testVerify_KO_1() {
		assertEquals(0, controller.verify());
		controller.setCurrentCellItem(0,1,ShipItem.LEFT);
		controller.setCurrentCellItem(0,2,ShipItem.CENTRE);
		controller.setCurrentCellItem(2,1,ShipItem.UP);
		controller.setCurrentCellItem(2,3,ShipItem.LEFT); // wrong, should be UP
		controller.setCurrentCellItem(4,1,ShipItem.DOWN);
		controller.setCurrentCellItem(5,3,ShipItem.SINGLE);
		assertEquals(1, controller.verify());
		assertFalse(controller.isGameOver());
	} 

	@Test
	public void testVerify_KO_2() {
		assertEquals(0, controller.verify());
		controller.setCurrentCellItem(0,1,ShipItem.LEFT);
		controller.setCurrentCellItem(0,2,ShipItem.CENTRE);
		controller.setCurrentCellItem(2,1,ShipItem.UP);
		controller.setCurrentCellItem(2,3,ShipItem.LEFT); // wrong, should be UP
		controller.setCurrentCellItem(4,1,ShipItem.DOWN);
		controller.setCurrentCellItem(5,3,ShipItem.SEA); // wrong, should be SINGLE
		assertEquals(2, controller.verify());
		assertFalse(controller.isGameOver());
	} 

	@Test
	public void testVerify_KO_3() {
		assertEquals(0, controller.verify());
		controller.setCurrentCellItem(0,1,ShipItem.LEFT);
		controller.setCurrentCellItem(0,2,ShipItem.CENTRE);
		controller.setCurrentCellItem(2,1,ShipItem.UP);
		controller.setCurrentCellItem(2,3,ShipItem.LEFT); // wrong, should be UP
		controller.setCurrentCellItem(4,1,ShipItem.DOWN);
		controller.setCurrentCellItem(5,3,ShipItem.EMPTY); // wrong but EMPTY cells don't count
		assertEquals(1, controller.verify());
		assertFalse(controller.isGameOver());
	} 

	@Test
	public void testVerify_KO_4() {
		assertEquals(0, controller.verify());
		// the following are ok..
		controller.setCurrentCellItem(0,1,ShipItem.LEFT);
		controller.setCurrentCellItem(0,2,ShipItem.CENTRE);
		controller.setCurrentCellItem(2,1,ShipItem.UP);
		controller.setCurrentCellItem(2,3,ShipItem.UP);
		controller.setCurrentCellItem(4,1,ShipItem.DOWN);
		controller.setCurrentCellItem(5,3,ShipItem.SINGLE);
		// .. but these are not
		controller.setCurrentCellItem(0,7,ShipItem.SEA);
		controller.setCurrentCellItem(7,7,ShipItem.SINGLE);
		assertEquals(2, controller.verify());
		assertFalse(controller.isGameOver());
	} 

	@Test
	public void testGameOver_OK() {
		assertEquals(0, controller.verify());
		// the following are ok..
		controller.setCurrentCellItem(0,1,ShipItem.LEFT);
		controller.setCurrentCellItem(3,6,ShipItem.LEFT);
		controller.setCurrentCellItem(6,5,ShipItem.LEFT);
		
		controller.setCurrentCellItem(0,4,ShipItem.RIGHT);
		controller.setCurrentCellItem(3,7,ShipItem.RIGHT);
		controller.setCurrentCellItem(6,7,ShipItem.RIGHT);
		
		controller.setCurrentCellItem(0,3,ShipItem.CENTRE);
		controller.setCurrentCellItem(0,2,ShipItem.CENTRE);
		controller.setCurrentCellItem(3,1,ShipItem.CENTRE);
		controller.setCurrentCellItem(6,6,ShipItem.CENTRE);
		
		controller.setCurrentCellItem(0,7,ShipItem.UP);
		controller.setCurrentCellItem(2,1,ShipItem.UP);
		controller.setCurrentCellItem(2,3,ShipItem.UP);
		
		controller.setCurrentCellItem(1,7,ShipItem.DOWN);
		controller.setCurrentCellItem(3,3,ShipItem.DOWN);
		controller.setCurrentCellItem(4,1,ShipItem.DOWN);
		
		controller.setCurrentCellItem(5,3,ShipItem.SINGLE);
		controller.setCurrentCellItem(7,1,ShipItem.SINGLE);
		controller.setCurrentCellItem(7,3,ShipItem.SINGLE);
							
		assertFalse(controller.isGameOver()); // manca il mare
		assertEquals(0, controller.verify());
		
		for(int row=0; row<controller.getSize(); row++)
			for(int col=0; col<controller.getSize(); col++)
				if (controller.getCurrentCellItem(row,col)==ShipItem.EMPTY)
					controller.setCurrentCellItem(row,col,ShipItem.SEA);
		assertEquals(0, controller.verify());
		assertTrue(controller.isGameOver());
	} 

}
