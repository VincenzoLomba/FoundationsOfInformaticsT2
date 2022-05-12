package myfitnessdiary.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;



import myfitnessdiary.model.Activity;
import myfitnessdiary.model.Intensity;
import myfitnessdiary.model.Workout;

public class WorkoutTest {

	private Workout wo;
	private Activity act;

	@BeforeEach
	public void setUp() {
		act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);
	}

	@Test
	public void testBadParam1() {

		assertThrows(IllegalArgumentException.class, () ->
		wo = new Workout(null, 20, Intensity.LOW, act));

	}

	@Test
	public void testBadParam2() {

		assertThrows(IllegalArgumentException.class, () -> 
		wo = new Workout(LocalDate.now(), -5, Intensity.LOW, act));

	}

	@Test
	public void testBadParam3() {
		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);
		assertThrows(IllegalArgumentException.class, () -> 
		wo = new Workout(LocalDate.now(), 20, null, act));

	}

	@Test
	public void testBadParam4() {
		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);
		assertThrows(IllegalArgumentException.class, () -> 
		wo = new Workout(LocalDate.now(), 20, Intensity.LOW, null));

	}

	@Test
	public void testgetDate() {
		wo = new Workout(LocalDate.now(), 20, Intensity.LOW, act);
		assertEquals(LocalDate.now(), wo.getDate());
	}

	@Test
	public void testgetDuration() {
		wo = new Workout(LocalDate.now(), 20, Intensity.LOW, act);
		assertEquals(20, wo.getDuration());
	}

	@Test
	public void testgetIntensity() {
		wo = new Workout(LocalDate.now(), 20, Intensity.LOW, act);
		assertEquals(Intensity.LOW, wo.getIntensity());
	}

	@Test
	public void testgetActivity() {
		wo = new Workout(LocalDate.now(), 20, Intensity.LOW, act);
		assertSame(act, wo.getActivity());
	}

	@Test
	public void testgetBurnedCalories() {
		wo = new Workout(LocalDate.now(), 20, Intensity.LOW, act);
		assertEquals(200, wo.getBurnedCalories());
	}

}
