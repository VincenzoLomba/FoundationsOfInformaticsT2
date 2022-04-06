package control.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.MyCalendar;
import model.*;

public class MyCalendarTest {
	MyCalendar myCal;

	@BeforeEach
	public void setUp() {
		myCal = new MyCalendar();
	}

	@Test
	public void testAdd() {
		LocalDateTime from = LocalDateTime.of(2019, Month.MARCH, 10, 12, 30, 0);
		LocalDateTime to = LocalDateTime.of(2019, Month.MARCH, 10, 15, 30, 0);
		Appointment app = new Appointment("Compleanno", from, to);
		myCal.add(app);
		
		assertEquals(1, myCal.getAllAppointments().size());
	}

	@Test
	public void testGetListAppointmentDay() {
		LocalDateTime from1 = LocalDateTime.of(2019, Month.MARCH, 10, 12, 30, 0);
		LocalDateTime to1 = LocalDateTime.of(2019, Month.MARCH, 10, 15, 30, 0);
		Appointment app1 = new Appointment("Compleanno", from1, to1);
		myCal.add(app1);

		LocalDateTime from3 = LocalDateTime.of(2019, Month.MARCH, 10, 20, 30, 0);
		LocalDateTime to3 = LocalDateTime.of(2019, Month.MARCH, 10, 22, 30, 0);
		Appointment app3 = new Appointment("Cena Lavoro", from3, to3);
		myCal.add(app3);

		LocalDate date = LocalDate.of(2019, Month.MARCH, 10);
		AppointmentCollection coll = myCal.getDayAppointments(date);
		assertEquals(2, coll.size());

	}

	@Test
	public void testRemove() {
		LocalDateTime from1 = LocalDateTime.of(2012, Month.MARCH, 10, 12, 30, 0);
		LocalDateTime to1 = LocalDateTime.of(2012, Month.MARCH, 10, 15, 30, 0);
		Appointment app1 = new Appointment("Compleanno", from1, to1);
		myCal.add(app1);

		LocalDateTime from3 = LocalDateTime.of(2012, Month.MARCH, 10, 20, 30, 0);
		LocalDateTime to3 = LocalDateTime.of(2012, Month.MARCH, 10, 22, 30, 0);
		Appointment app3 = new Appointment("Cena Lavoro", from3, to3);
		myCal.add(app3);

		boolean removed = myCal.remove(app3); 
		assertTrue(removed);
		
		LocalDate date = LocalDate.of(2012, Month.MARCH, 10);
		assertEquals(1, myCal.getDayAppointments(date).size());
	}

	@Test
	public void testGetListAppointmentMonth() {
		LocalDateTime from1 = LocalDateTime.of(2012, Month.MARCH, 31, 12, 30, 0);
		LocalDateTime to1 = LocalDateTime.of(2012, Month.MARCH, 13, 15, 30, 0);
		Appointment app1 = new Appointment("Compleanno", from1, to1);
		myCal.add(app1);

		LocalDateTime from2 = LocalDateTime.of(2012, Month.MARCH, 30, 20, 30, 0);
		LocalDateTime to2 = LocalDateTime.of(2012, Month.MARCH, 30, 22, 30, 0);
		Appointment app2 = new Appointment("Cena Lavoro", from2, to2);
		myCal.add(app2);

		LocalDateTime from3 = LocalDateTime.of(2012, Month.APRIL, 29, 15, 30, 0);
		LocalDateTime to3 = LocalDateTime.of(2012, Month.APRIL, 29, 18, 30, 0);
		Appointment app3 = new Appointment("Lezione", from3, to3);
		myCal.add(app3);

		LocalDate date = LocalDate.of(2012, Month.MARCH, 10);
		AppointmentCollection coll = myCal.getMonthAppointments(date);
		assertEquals(2, coll.size());
	}

	@Test
	public void testGetListAppointmentWeek() {
		LocalDateTime from1 = LocalDateTime.of(2015, Month.MARCH, 31, 12, 30, 0);
		LocalDateTime to1 = LocalDateTime.of(2015, Month.MARCH, 31, 15, 30, 0);
		Appointment app1 = new Appointment("Compleanno", from1, to1);
		myCal.add(app1);

		LocalDateTime from2 = LocalDateTime.of(2015, Month.MARCH, 30, 20, 30, 0);
		LocalDateTime to2 = LocalDateTime.of(2015, Month.MARCH, 30, 22, 30, 0);
		Appointment app2 = new Appointment("Cena Lavoro", from2, to2);
		myCal.add(app2);

		LocalDateTime from3 = LocalDateTime.of(2015, Month.MARCH, 28, 15, 30, 0);
		LocalDateTime to3 = LocalDateTime.of(2015, Month.MARCH, 28, 18, 30, 0);
		Appointment app3 = new Appointment("Cena Lavoro", from3, to3);
		myCal.add(app3);

		LocalDateTime from4 = LocalDateTime.of(2015, Month.APRIL, 2, 15, 30, 0);
		LocalDateTime to4 = LocalDateTime.of(2015, Month.APRIL, 2, 18, 30, 0);
		Appointment app4 = new Appointment("Lezione", from4, to4);
		myCal.add(app4);

		LocalDate date = LocalDate.of(2015, Month.APRIL, 1);
		AppointmentCollection coll = myCal.getWeekAppointments(date);
		assertEquals(3, coll.size());
	}

}
