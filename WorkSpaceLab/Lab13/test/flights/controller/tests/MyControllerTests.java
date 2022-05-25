package flights.controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import flights.controller.MyController;
import flights.model.Airport;
import flights.model.FlightSchedule;
import flights.presistence.tests.DataManagerMock;

public class MyControllerTests {

	@Test
	public void testMyController() {
		new MyController(new DataManagerMock());
	}

	@Test
	public void testGetSortedAirports() {
		MyController controller = new MyController(new DataManagerMock());

		Airport[] airports = controller.getSortedAirports().toArray(
				new Airport[0]);
		assertEquals(3, airports.length);

		assertEquals("Dep", airports[0].getCode());
		assertEquals("Dep1", airports[1].getCode());
		assertEquals("Arr", airports[2].getCode());
	}

	@Test
	public void testSearchFlights() {
		DataManagerMock data = new DataManagerMock();
		MyController controller = new MyController(data);
		Airport dep = data.getAirportMap().get("Dep");
		Airport arr = data.getAirportMap().get("Arr");

		LocalDate date = LocalDateHelper.getLocalDateWith(DayOfWeek.MONDAY);
		List<FlightSchedule> schedules = controller.searchFlights(dep, arr,
				date);

		assertEquals(1, schedules.size());
		assertEquals("A", schedules.get(0).getCode());
	}

	@Test
	public void testSearchFlights_NotFound1() {
		DataManagerMock data = new DataManagerMock();
		MyController controller = new MyController(data);
		Airport dep = data.getAirportMap().get("Dep");
		Airport arr = data.getAirportMap().get("Arr");

		LocalDate date = LocalDateHelper.getLocalDateWith(DayOfWeek.SUNDAY);
		List<FlightSchedule> schedules = controller.searchFlights(dep, arr,date);

		assertEquals(0, schedules.size());
	}

	@Test
	public void testSearchFlights_NotFound2() {
		DataManagerMock data = new DataManagerMock();
		MyController controller = new MyController(data);
		Airport dep = data.getAirportMap().get("Dep1");
		Airport arr = data.getAirportMap().get("Arr");

		LocalDate date = LocalDateHelper.getLocalDateWith(DayOfWeek.MONDAY);
		List<FlightSchedule> schedules = controller.searchFlights(dep, arr,date);

		assertEquals(0, schedules.size());
	}

}
