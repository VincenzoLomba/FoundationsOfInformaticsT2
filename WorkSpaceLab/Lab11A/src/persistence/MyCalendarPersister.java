package persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.AppointmentCollection;

public class MyCalendarPersister implements CalendarPersister {

	private static final String FILE_PATH = "calendar.bin";
	
	@Override
	public AppointmentCollection load() throws ClassNotFoundException, IOException {
		
		/* All InputStream that are opened inside the declaration of a try-block are always closed after it. */
		try (FileInputStream fis = new FileInputStream(FILE_PATH)) {
			try (ObjectInputStream ois = new ObjectInputStream(fis)) {
				AppointmentCollection response = (AppointmentCollection) ois.readObject();
				return response;
			}
		} catch (FileNotFoundException fnfe) { return new AppointmentCollection(); }
	}

	@Override
	public void save(AppointmentCollection appointmentCollection) throws ClassNotFoundException, IOException {
		
		try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) { /* A non-existing file will be created (as a blank file). */
			try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(appointmentCollection);
				oos.flush(); /* This method is used to be sure that the previous "writeObject* operation ends correctly. In the last versions of Java it should not be necessary any more.  */
			}
		}
	}

}
