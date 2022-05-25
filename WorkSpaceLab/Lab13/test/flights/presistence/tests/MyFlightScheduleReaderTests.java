package flights.presistence.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.StringReader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.City;
import flights.model.FlightSchedule;
import flights.persistence.BadFileFormatException;
import flights.persistence.MyFlightScheduleReader;

public class MyFlightScheduleReaderTests {

	@BeforeEach
	public void before() {
		Locale.setDefault(Locale.ITALY);
	}
	
	@Test
	public void testRead() throws IOException, BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,14:30,Arr,16:15,0,1-3-5-7,111\nAZ1345,Arr,07:00,Dep,08:45,-1,-2-4-6-,999\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		Collection<FlightSchedule> schedules = flightScheduleReader.read(
				new StringReader(toRead), airports, aircrafts);

		assertEquals(2, schedules.size());
		FlightSchedule[] array = schedules.toArray(new FlightSchedule[0]);

		FlightSchedule fs = array[0];
		assertEquals("AZ1344", fs.getCode());
		assertEquals(dep, fs.getDepartureAirport());
		assertEquals(LocalTime.of(14, 30), fs.getDepartureLocalTime());
		assertEquals(arr, fs.getArrivalAirport());
		assertEquals(LocalTime.of(16, 15), fs.getArrivalLocalTime());
		assertEquals(0, fs.getDayOffset());
		assertEquals(lit, fs.getAircraft());
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.MONDAY));
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.WEDNESDAY));
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.FRIDAY));
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.SUNDAY));

		fs = array[1];
		assertEquals("AZ1345", fs.getCode());
		assertEquals(arr, fs.getDepartureAirport());
		assertEquals(LocalTime.of(7, 00), fs.getDepartureLocalTime());
		assertEquals(dep, fs.getArrivalAirport());
		assertEquals(LocalTime.of(8, 45), fs.getArrivalLocalTime());
		assertEquals(-1, fs.getDayOffset());
		assertEquals(big, fs.getAircraft());
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.TUESDAY));
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.THURSDAY));
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.SATURDAY));
	}

	@Test
	public void testRead_MissingAirport() throws IOException,
			BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,14:30,Arr,16:15,0,1-3-5-7,111\nAZ1345,Missing,07:00,Dep,08:45,0,-2-4-6-,999\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		
		assertThrows(BadFileFormatException.class, () ->
			flightScheduleReader.read(new StringReader(toRead), airports, aircrafts));
		//assertTrue(exception.getMessage().contains("Departure airport not present"));
	}

	@Test
	public void testRead_MissingAircraft() throws IOException,
			BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,14:30,Arr,16:15,0,1-3-5-7,111\nAZ1345,Arr,07:00,Dep,08:45,0,-2-4-6-,Missing\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		
		assertThrows(BadFileFormatException.class, () ->
			flightScheduleReader.read(new StringReader(toRead), airports, aircrafts));
		//assertTrue(exception.getMessage().contains("Aircraft not present"));
	}

	@Test
	public void testRead_BadTimeFormat1() throws IOException,
			BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,14:30,Arr,16:,0,1-3-5-7,111\nAZ1345,Arr,07:00,Dep,8.45,0,-2-4-6-,999\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		
		assertThrows(BadFileFormatException.class, () ->
			flightScheduleReader.read(new StringReader(toRead), airports, aircrafts));
		//assertTrue(exception.getMessage().contains("could not be parsed"));
	}

	@Test
	public void testRead_BadTimeFormat2() throws IOException,
			BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,14:30,Arr,:15,0,1-3-5-7,111\nAZ1345,Arr,07:00,Dep,08:45,0,-2-4-6-,999\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		
		assertThrows(BadFileFormatException.class, () ->
			flightScheduleReader.read(new StringReader(toRead), airports, aircrafts));
		//assertTrue(exception.getMessage().contains("DateTime"));
	}

	@Test
	public void testRead_BadTimeFormat3() throws IOException,
			BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,AA:BB,Arr,AA:BB,0,1-3-5-7,111\nAZ1345,Arr,07:00,Dep,08:45,0,-2-4-6-,999\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		
		assertThrows(BadFileFormatException.class, () ->
			flightScheduleReader.read(new StringReader(toRead), airports, aircrafts));
		//assertTrue(exception.getMessage().contains("DateTime"));
	}

	@Test
	public void testRead_BadDaysFormat1() throws IOException,
			BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,14:30,Arr,16:15,0,1-3-5-7,111\nAZ1345,Arr,07:00,Dep,08:45,0,-5-4-3-,999\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		
		assertThrows(BadFileFormatException.class, () ->
			flightScheduleReader.read(new StringReader(toRead), airports, aircrafts));
		//assertTrue(exception.getMessage().contains("Bad Days Format"));
	}

	@Test
	public void testRead_BadDaysFormat2() throws IOException,
			BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,14:30,Arr,16:15,0,1-3-5-7,111\nAZ1345,Arr,07:00,Dep,08:45,0,-X-X-X-,999\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		assertThrows(BadFileFormatException.class, () ->
			flightScheduleReader.read(new StringReader(toRead), airports, aircrafts));
		//assertTrue(exception.getMessage().contains("Bad Days Format"));
	}
	
	@Test
	public void testRead_BadDayOffset() throws IOException, BadFileFormatException {
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		Airport dep = new Airport("Dep", "Departure", new City("Alf",
				"Alfonsine", 2));
		airports.put(dep.getCode(), dep);
		Airport arr = new Airport("Arr", "Arrival", new City("Can",
				"Canicatti", 4));
		airports.put(arr.getCode(), arr);

		HashMap<String, Aircraft> aircrafts = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircrafts.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircrafts.put(big.getCode(), big);

		String toRead = "AZ1344,Dep,14:30,Arr,16:15,X,1-3-5-7,111\nAZ1345,Arr,07:00,Dep,08:45,X,-2-4-6-,999\n";
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();
		
		assertThrows(BadFileFormatException.class, () ->
			flightScheduleReader.read(new StringReader(toRead), airports, aircrafts));
		//assertTrue(exception.getMessage().contains("NumberFormat"));
	}
}
