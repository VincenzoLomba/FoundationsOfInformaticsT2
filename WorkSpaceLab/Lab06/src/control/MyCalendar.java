package control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import model.Appointment;
import model.AppointmentCollection;

public class MyCalendar {
	
	private AppointmentCollection allAppointments;
	
	public MyCalendar () {
		
		allAppointments = new AppointmentCollection();
	}
	
	public void add (Appointment app) { allAppointments.add(app); }
	
	public boolean remove (Appointment app) {
		
		int index = allAppointments.indexOf(app);
		if (index == -1) return false;
		allAppointments.remove(index);
		return true;
	}
	
	public AppointmentCollection getAllAppointments () {
		
		AppointmentCollection response = new AppointmentCollection();
		for (int i = 0 ; i < allAppointments.size() ; ++i)
			response.add(allAppointments.get(i));
		return response;
	}

	private boolean isOverlapped (LocalDateTime start, LocalDateTime end, LocalDateTime refStart, LocalDateTime refEnd) {
		
		return
			(end.isBefore(refEnd) && end.isAfter(refStart)) ||
			(start.isAfter(refStart) && start.isBefore(refEnd)) ||
			(start.isBefore(refStart) && end.isAfter(refEnd))
		;
	}
	
	private AppointmentCollection getAppointmentsIn (LocalDateTime start, LocalDateTime end) {
		
		AppointmentCollection response = new AppointmentCollection();
		for (int i = 0 ; i < allAppointments.size() ; ++i) {
			Appointment app = allAppointments.get(i);
			if (isOverlapped(app.getFrom(), app.getTo(), start, end)) response.add(app);
		}
		return response;
	}
	
	public AppointmentCollection getDayAppointments(LocalDate date) {
		
		LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
		return getAppointmentsIn(start, start.plusDays(1));
	}
	
	public AppointmentCollection getWeekAppointments(LocalDate date) {
		
		LocalDateTime start = LocalDateTime.of(date.minusDays(date.getDayOfWeek().ordinal()), LocalTime.of(0, 0, 0));
		return getAppointmentsIn(start, start.plusWeeks(1));
	}
	
	public AppointmentCollection getMonthAppointments(LocalDate date) {
		
		LocalDateTime start = LocalDateTime.of(date.withDayOfMonth(1), LocalTime.of(0, 0, 0));
		return getAppointmentsIn(start, start.plusMonths(1));
	}
}
