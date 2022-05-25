package flights.model.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.City;
import flights.model.FlightSchedule;

public class FlightScheduleTests {

	@Test
	public void testFlightScheduleCtorAndGetters() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 1));
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicattì", 1));
		LocalTime depTime = LocalTime.of(10, 12);
		LocalTime arrTime = LocalTime.of(12, 40);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 0,
				days, air);

		assertEquals("A", fs.getCode());
		assertSame(dep, fs.getDepartureAirport());
		assertSame(arr, fs.getArrivalAirport());
		assertEquals(0, fs.getDayOffset());
		assertEquals(1, fs.getDaysOfWeek().size());
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.MONDAY));
		assertSame(air, fs.getAircraft());
	}

	@Test
	public void testGetFlightDuration_SameTimeZone() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 1));
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatt�", 1));
		LocalTime depTime = LocalTime.of(10, 12);
		LocalTime arrTime = LocalTime.of(12, 40);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 0,
				days, air);
		assertEquals(Duration.ofMinutes(148), fs.getFlightDuration());
	}

	@Test
	public void testGetFlightDuration_WestToEast() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatt�", 4));
		LocalTime depTime = LocalTime.of(17, 30);
		LocalTime arrTime = LocalTime.of(2, 00);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 1,
				days, air);
		assertEquals(Duration.ofMinutes(390), fs.getFlightDuration());
	}

	@Test
	public void testGetFlightDuration_EastToWest() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatt�", -4));
		LocalTime depTime = LocalTime.of(10, 00);
		LocalTime arrTime = LocalTime.of(13, 55);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 0,
				days, air);
		assertEquals(Duration.ofMinutes(595), fs.getFlightDuration());
	}

	@Test
	public void testGetFlightDuration_WestToEast_CrossDayChangeLine() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 9));
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatt�", -7));
		LocalTime depTime = LocalTime.of(16, 10);
		LocalTime arrTime = LocalTime.of(9, 15);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, -1,
				days, air);
		assertEquals(Duration.ofMinutes(545), fs.getFlightDuration());
	}

	@Test
	public void testGetFlightDuration_EastToWest_CrossDayChangeLine() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", -7));
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatt�", 10));
		LocalTime depTime = LocalTime.of(23, 20);
		LocalTime arrTime = LocalTime.of(7, 45);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 1,
				days, air);
		assertEquals(Duration.ofMinutes(925), fs.getFlightDuration());
	}

}
