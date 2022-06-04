package edlift.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.OptionalInt;

import org.junit.jupiter.api.Test;

import edlift.model.BasicLift;
import edlift.model.Lift;
import edlift.model.LiftState;
import edlift.model.RequestResult;

public class BasicLiftTest {

	@Test
	public void testGetTipo() {
		Lift lift = new BasicLift(2, 10, 5, 1.0);
		assertEquals("BASIC", lift.getMode().toUpperCase());
	}

	@Test
	public void testGetCurrentFloor() {
		Lift lift = new BasicLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCurrentFloor());
	}

	@Test
	public void testSetCurrentFloor() {
		Lift lift = new BasicLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCurrentFloor());
		lift.setCurrentFloor(3);
		assertEquals(3, lift.getCurrentFloor());
	}

	@Test
	public void testGetCallingFloor() {
		Lift lift = new BasicLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCallingFloor()); // default
	}

	@Test
	public void testSetCallingFloor() {
		Lift lift = new BasicLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCallingFloor()); // default
		lift.setCallingFloor(4);
		assertEquals(4, lift.getCallingFloor());
		assertEquals(5, lift.getCurrentFloor());
	}

	@Test
	public void testGetCurrentState() {
		Lift lift = new BasicLift(2, 10, 5, 1.0);
		assertEquals(LiftState.REST, lift.getCurrentState());
	}
	
	@Test
	public void testSetCurrentState() {
		Lift lift = new BasicLift(2, 10, 5, 1.0);
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
		Lift asc = new BasicLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.ACCEPTED, asc.goToFloor(7));
		// Ascensore fermo al piano 5, ma ha registrato la chiamata per il 7
		assertEquals(7, asc.getCallingFloor());
		assertEquals(5, asc.getCurrentFloor());
	}

	@Test
	public void testGoToFloorOK_ChiamataAccettata2() {
		Lift asc = new BasicLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.ACCEPTED, asc.goToFloor(6));
		// Ascensore fermo al piano 5, ma ha registrato la chiamata per il 6
		assertNotEquals(6, asc.getCurrentFloor());
		asc.setCurrentFloor(7);
		assertEquals(7, asc.getCurrentFloor());
	}

	@Test
	public void testGoToFloorOK_ChiamataAccettata3() {
		Lift asc = new BasicLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.ACCEPTED, asc.goToFloor(6));
		// Ascensore fermo al piano 5, ma ha registrato la chiamata per il 6
		assertNotEquals(6, asc.getCurrentFloor());
		asc.setCurrentFloor(7);
		assertEquals(7, asc.getCurrentFloor());
		// accetta un'altra chiamata perché è ancora in stato REST
		// anche se questa situazione non dovrebbe mai verificarsi
		// (ma magari un override da pulsantiera interna alla cabina..)
		System.out.println(asc.getCurrentFloor());
		assertEquals(RequestResult.ACCEPTED, asc.goToFloor(2));
	}

	@Test
	public void testGoToFloorOK_ChiamataRifiutata1() {
		Lift asc = new BasicLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.ACCEPTED, asc.goToFloor(6));
		// Ascensore fermo al piano 5, ma ha registrato la chiamata per il 6
		asc.setCurrentState(LiftState.UP);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(2));
		// rifiuta la seconda chiamata perché è ancora in corso la precedente
		assertEquals(6, asc.getCallingFloor());
	}
	
	@Test
	public void testGoToFloorKO_PianoDiDestinazioneOltreIlMax() {
		Lift asc = new BasicLift(2, 10, 5, 1.0);
		assertThrows(IllegalArgumentException.class, () -> asc.goToFloor(11));
	}
	
	@Test
	public void testGoToFloorKO_PianoDiDestinazioneSottoIlMin() {
		Lift asc = new BasicLift(2, 10, 5, 1.0);
		assertThrows(IllegalArgumentException.class, () -> asc.goToFloor(1));
	}

	@Test
	public void testHasPendingFloors() {
		Lift asc = new BasicLift(2, 10, 5, 1.0);
		assertFalse(asc.hasPendingFloors());
	}

	@Test
	public void testNextPendingFloor() {
		Lift asc = new BasicLift(2, 10, 5, 1.0);
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.REST));
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.UP));
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.DOWN));
	}
	
}
