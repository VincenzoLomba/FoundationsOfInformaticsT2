package persistence;

import java.io.IOException;

import model.AppointmentCollection;

public interface CalendarPersister {
	
	public AppointmentCollection load () throws ClassNotFoundException, IOException;
	public void save (AppointmentCollection appointmentCollection) throws ClassNotFoundException, IOException;

}
