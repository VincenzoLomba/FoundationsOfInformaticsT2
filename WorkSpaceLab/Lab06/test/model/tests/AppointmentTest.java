package model.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import model.Appointment;

public class AppointmentTest {
	@Test
	public void testSetGetFrom() {
		LocalDateTime dateTime = LocalDateTime.of(1980, Month.JUNE, 16, 12, 45, 0);
		Appointment app = new Appointment("test", dateTime, Duration.of(10, ChronoUnit.MINUTES));;
		assertEquals(dateTime, app.getFrom());
		LocalDateTime newFrom = LocalDateTime.of(1980, Month.JULY, 16, 12, 45, 0); 
		app.setFrom(newFrom);
		assertEquals(newFrom, app.getFrom());
	}

	@Test
	public void testSetGetTo() {
		LocalDateTime dateTime = LocalDateTime.of(2011, Month.JUNE, 16, 12, 45, 0);
		Appointment app = new Appointment("test", dateTime, Duration.of(0, ChronoUnit.MINUTES));;
		assertEquals(dateTime, app.getTo());
		LocalDateTime newTo = LocalDateTime.of(2011, Month.JULY, 16, 12, 45, 0); 
		app.setTo(newTo);
		assertEquals(newTo, app.getTo());
	}

	@Test
	public void testSetGetDescription() {
		Appointment app = new Appointment("Compleanno", LocalDateTime.now(), Duration.of(10, ChronoUnit.MINUTES));
		assertEquals("Compleanno", app.getDescription());
		app.setDescription("Anniversario");
		assertEquals("Anniversario", app.getDescription());	
	}

	@Test
	public void testSetGetDuration() {
		LocalDateTime dateTime = LocalDateTime.of(2011, Month.JUNE, 16, 12, 45, 0);
		Appointment app = new Appointment("test", dateTime, Duration.of(10, ChronoUnit.MINUTES));
		assertEquals(Duration.ofMinutes(10), app.getDuration());
		LocalDateTime toDateTime = app.getTo();
		LocalDateTime expected = LocalDateTime.of(2011, Month.JUNE, 16, 12, 55, 0);
		assertEquals(expected, toDateTime);
		app.setDuration(Duration.of(20, ChronoUnit.MINUTES));
		LocalDateTime newExpected = LocalDateTime.of(2011, Month.JUNE, 16, 13, 05, 0);
		assertEquals(newExpected, app.getTo());
	}

	@Test
	public void testEqualsAppointment() {
		Appointment app1 = new Appointment("Natale", LocalDateTime.of(2018, Month.DECEMBER, 24, 12, 0), Duration.of(10, ChronoUnit.MINUTES));
		Appointment app2 = new Appointment("Natale", LocalDateTime.of(2018, Month.DECEMBER, 24, 12, 0), Duration.of(10, ChronoUnit.MINUTES));
		assertTrue(app1.equals(app2));
	}

	@Test
	public void testNotEqualsAppointment() {
		Appointment app1 = new Appointment("Compleanno", LocalDateTime.now(), Duration.of(10, ChronoUnit.MINUTES));
		Appointment app2 = new Appointment("Non Compleanno", LocalDateTime.now(), Duration.of(10, ChronoUnit.MINUTES));
		assertFalse(app1.equals(app2));
	}

}
