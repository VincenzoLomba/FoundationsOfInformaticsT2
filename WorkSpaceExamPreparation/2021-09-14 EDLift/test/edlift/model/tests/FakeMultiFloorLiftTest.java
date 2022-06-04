package edlift.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.OptionalInt;

import org.junit.jupiter.api.Test;

import edlift.model.*;

public class FakeMultiFloorLiftTest {

	@Test
	public void testGetTipo() {
		Lift lift = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals("MULTIFLOOR", lift.getMode().toUpperCase());
	}

	@Test
	public void testGetCurrentFloor() {
		Lift lift = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCurrentFloor());
	}

	@Test
	public void testSetCurrentFloor() {
		Lift lift = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCurrentFloor());
		lift.setCurrentFloor(3);
		assertEquals(3, lift.getCurrentFloor());
	}

	@Test
	public void testGetCallingFloor() {
		Lift lift = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCallingFloor()); // default
	}

	@Test
	public void testSetCallingFloor() {
		Lift lift = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCallingFloor()); // default
		lift.setCallingFloor(4);
		assertEquals(4, lift.getCallingFloor());
		assertEquals(5, lift.getCurrentFloor());
	}

	@Test
	public void testGetCurrentState() {
		Lift lift = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(LiftState.REST, lift.getCurrentState());
	}
	
	@Test
	public void testSetCurrentState() {
		Lift lift = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(LiftState.REST, lift.getCurrentState());
		lift.setCurrentState(LiftState.UP);
		assertEquals(LiftState.UP, lift.getCurrentState());
		lift.setCurrentState(LiftState.DOWN);
		lift.setCurrentState(LiftState.DOWN);
		assertEquals(LiftState.DOWN, lift.getCurrentState());
		lift.setCurrentState(LiftState.REST);
		assertEquals(LiftState.REST, lift.getCurrentState());
	}
	
	
	@Test
	public void testGoToFloorOK_ChiamataAccettata1() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(7));
		// Ascensore fermo al piano 5
		assertNotEquals(7, asc.getCallingFloor());
		assertEquals(5, asc.getCurrentFloor());
	}

	@Test
	public void testGoToFloorOK_ChiamataAccettata2() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(6));
		// Ascensore fermo al piano 5
		assertNotEquals(6, asc.getCurrentFloor());
		asc.setCurrentFloor(7);
		assertEquals(7, asc.getCurrentFloor());
	}
	
	@Test
	public void testGoToFloorKO_PianoDiDestinazioneOltreIlMax() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertThrows(IllegalArgumentException.class, () -> asc.goToFloor(11));
	}
	
	@Test
	public void testGoToFloorKO_PianoDiDestinazioneSottoIlMin() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertThrows(IllegalArgumentException.class, () -> asc.goToFloor(1));
	}

	@Test
	public void testHasPendingFloors_Empty() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertFalse(asc.hasPendingFloors());
	}
	
	@Test
	public void testNextPendingFloor_empty() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.REST));
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.UP));
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.DOWN));
	}


	@Test
	public void testHasPendingFloors_Empty_REST() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(9));
		// Ascensore fermo al piano 5, ma ha registrato la chiamata per il 6
		assertEquals(RequestResult.REJECTED, asc.goToFloor(7));
		// non accetta la nuova chiamata
		assertEquals(5, asc.getCurrentFloor());
		assertFalse(asc.hasPendingFloors());
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.REST));
	}

	@Test
	public void testHasPendingFloors_NotEmpty_UP() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(9));
		// Ascensore fermo al piano 5
		assertEquals(5, asc.getCurrentFloor());
		assertFalse(asc.hasPendingFloors());
		// ora cambia stato e parte
		asc.setCurrentState(LiftState.UP);
		asc.setCurrentFloor(6);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(7));
		// non accetta la nuova chiamata perché è nel percorso della precedente
		assertNotEquals(7, asc.getCallingFloor());
		assertFalse(asc.hasPendingFloors());
	}
	
	@Test
	public void testHasPendingFloors_NotEmpty_UP_2() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(9));
		// Ascensore fermo al piano 5, ma ha registrato la chiamata per il 9
		assertEquals(5, asc.getCurrentFloor());
		assertFalse(asc.hasPendingFloors());
		// ora cambia stato e parte
		asc.setCurrentState(LiftState.UP);
		asc.setCurrentFloor(6);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(8));
		assertEquals(RequestResult.REJECTED, asc.goToFloor(7));
		// non accetta la due nuove chiamate
		assertNotEquals(8, asc.getCurrentFloor());
		assertNotEquals(7, asc.getCallingFloor());
		assertFalse(asc.hasPendingFloors());
	}
	
	@Test
	public void testHasPendingFloors_NotEmpty_DOWN_2() {
		Lift asc = new FakeMultiFloorLift(2, 10, 7, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(2));
		// Ascensore fermo al piano 7
		assertEquals(7, asc.getCurrentFloor());
		assertFalse(asc.hasPendingFloors());
		// ora cambia stato e parte
		asc.setCurrentState(LiftState.DOWN);
		asc.setCurrentFloor(6);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(3));
		assertEquals(RequestResult.REJECTED, asc.goToFloor(4));
		// non accetta nuove chiamate
		assertEquals(6, asc.getCurrentFloor());
		assertEquals(7, asc.getCallingFloor());
		assertFalse(asc.hasPendingFloors());
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.DOWN));
	}

	@Test
	public void testHasPendingFloors_Empty_DOWN() {
		Lift asc = new FakeMultiFloorLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(9));
		// Ascensore fermo al piano 5, ha rifiutato la chiamata per il 9
		assertEquals(5, asc.getCurrentFloor());
		assertNotEquals(9, asc.getCallingFloor());
		assertFalse(asc.hasPendingFloors());
		//
		asc.setCurrentState(LiftState.DOWN);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(7));
		// non accetta la nuova chiamata perché non è nel percorso della precedente
		assertEquals(5, asc.getCurrentFloor());
		assertFalse(asc.hasPendingFloors());
		// case UP   -> carCallsList.pollFirst();
		// case DOWN -> carCallsList.pollLast();
		// case REST -> getCallingFloor() > getCurrentFloor() ?
		//			     carCallsList.pollFirst() : carCallsList.pollLast(); 
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.DOWN));
	}
		
}
