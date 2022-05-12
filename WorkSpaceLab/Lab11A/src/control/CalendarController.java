package control;

import java.time.LocalDate;

import model.Appointment;
import model.AppointmentCollection;

public interface CalendarController {
	
	public boolean add (Appointment appointment);
	public AppointmentCollection getAllAppointments ();
	public AppointmentCollection getDayAppointments (LocalDate localDate);
	public AppointmentCollection getMonthAppointments (LocalDate localDate);
	public AppointmentCollection getWeekAppointments (LocalDate localDate);
	public boolean remove (Appointment appointment);
}
