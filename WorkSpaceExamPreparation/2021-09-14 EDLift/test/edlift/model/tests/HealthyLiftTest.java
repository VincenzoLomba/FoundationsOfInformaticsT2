package edlift.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.OptionalInt;

import org.junit.jupiter.api.Test;

import edlift.model.*;

public class HealthyLiftTest {

	@Test
	public void testGetTipo() {
		Lift lift = new HealthyLift(2, 10, 5, 1.0);
		assertEquals("HEALTHY", lift.getMode().toUpperCase());
	}

	@Test
	public void testGetCurrentFloor() {
		Lift lift = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCurrentFloor());
	}

	@Test
	public void testSetCurrentFloor() {
		Lift lift = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCurrentFloor());
		lift.setCurrentFloor(3);
		assertEquals(3, lift.getCurrentFloor());
	}

	@Test
	public void testGetCallingFloor() {
		Lift lift = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCallingFloor()); // default
	}

	@Test
	public void testSetCallingFloor() {
		Lift lift = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(5, lift.getCallingFloor()); // default
		lift.setCallingFloor(4);
		assertEquals(4, lift.getCallingFloor());
		assertEquals(5, lift.getCurrentFloor());
	}

	@Test
	public void testGetCurrentState() {
		Lift lift = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(LiftState.REST, lift.getCurrentState());
	}
	
	@Test
	public void testSetCurrentState() {
		Lift lift = new HealthyLift(2, 10, 5, 1.0);
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
	public void testGoToFloorOK_ChiamataModificata_AccettatoPianoInferiore() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.MODIFIED, asc.goToFloor(7));
		// Ascensore fermo al piano 5, ma ha registrato la chiamata per il 6
		assertEquals(6, asc.getCallingFloor());
		assertEquals(5, asc.getCurrentFloor());
	}
	
	@Test
	public void testGoToFloorOK_ChiamataModificata_AccettatoPianoSuperiore() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.MODIFIED, asc.goToFloor(2));
		// Ascensore fermo al piano 5, accetta la chiamata modificando il 2 in 3
		assertEquals(3, asc.getCallingFloor());
		assertEquals(5, asc.getCurrentFloor());
	}

	@Test
	public void testGoToFloorOK_ChiamataModificata_AccettatoPianoSuperioreAdiacente() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.MODIFIED, asc.goToFloor(3));
		// Ascensore fermo al piano 5, accetta la chiamata modificando il 3 in 4
		// NB: in questo caso non importa che risulti adiacente
		assertEquals(4, asc.getCallingFloor());
		assertEquals(5, asc.getCurrentFloor());
	}

	@Test
	public void testGoToFloorOK_ChiamataRifiutata_PianoAdiacente() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(6));
		// Ascensore fermo al piano 5, rifiuta la chiamata per il 6 perché adiacente
		assertNotEquals(6, asc.getCurrentFloor());
		asc.setCurrentFloor(7);
		assertEquals(7, asc.getCurrentFloor());
	}

	@Test
	public void testGoToFloorOK_DueChiamate_RifiutataModificata() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(6));
		// Ascensore fermo al piano 5, rifiuta la chiamata per il 6
		assertNotEquals(6, asc.getCurrentFloor());
		asc.setCurrentFloor(7);
		assertEquals(7, asc.getCurrentFloor());
		// accetta un'altra chiamata perché è ancora in stato REST
		// anche se questa situazione non dovrebbe mai verificarsi
		// (ma magari un override da pulsantiera interna alla cabina..)
		RequestResult res = asc.goToFloor(2);
		assertEquals(RequestResult.MODIFIED, res);
		assertEquals(OptionalInt.of(3), RequestResult.MODIFIED.getFloor());
	}

	@Test
	public void testGoToFloorOK_ChiamataRifiutata_DirezioneOpposta() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		asc.setCurrentState(LiftState.UP);
		assertEquals(RequestResult.REJECTED, asc.goToFloor(2));
		// rifiuta la chiamata perché sarebbe nella direzione sbagliata
		assertEquals(5, asc.getCallingFloor());
		// callingFloor è rimasto al valore iniziale = 5
	}
	
	@Test
	public void testGoToFloorKO_PianoDiDestinazioneOltreIlMax() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertThrows(IllegalArgumentException.class, () -> asc.goToFloor(11));
	}
	
	@Test
	public void testGoToFloorKO_PianoDiDestinazioneSottoIlMin() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertThrows(IllegalArgumentException.class, () -> asc.goToFloor(1));
	}

	@Test
	public void testHasPendingFloors() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertFalse(asc.hasPendingFloors());
	}

	@Test
	public void testNextPendingFloor() {
		Lift asc = new HealthyLift(2, 10, 5, 1.0);
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.REST));
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.UP));
		assertEquals(OptionalInt.empty(), asc.nextPendingFloor(LiftState.DOWN));
	}
	
}
