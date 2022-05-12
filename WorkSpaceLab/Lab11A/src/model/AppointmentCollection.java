package model;

import java.io.Serializable;

public class AppointmentCollection implements Serializable {
	
	private static final long serialVersionUID = 1L; /* A different value of this serial has to be inserted in each class of the project that implements the Serializable interface. */
	
	private static final int DEFAULT_GROWTH_FACTOR = 2;
	private static final int DEFAULT_PHYSICAL_SIZE = 10;
	private Appointment[] innerContainer;
	private int size;
	
	public AppointmentCollection (int physicalSize) {
		
		innerContainer = new Appointment[physicalSize];
		size = 0;
	}
	
	public AppointmentCollection () { this(DEFAULT_PHYSICAL_SIZE); }
	
	public int size () { return size; }
	
	public void add (Appointment a) {
		
		/* Checking if an equal appointment is ALREADY contained. */
		// if (Arrays.stream(innerContainer).filter(x -> x != null && x.equals(a)).count() > 0) return;
		
		if (size < innerContainer.length) {
			innerContainer[size++] = a;
		} else {
			Appointment[] nw = new Appointment[innerContainer.length*DEFAULT_GROWTH_FACTOR];
			int j = 0;
			for (Appointment fr : innerContainer) nw[j++] = fr;
			nw[j] = a;
			innerContainer = nw;
			++size;
		}
	}
	
	public void remove (int index) {
		
		if (index < 0 || index >= size || size == 0) return;
		if (index < size - 1) for (int j = index + 1 ; j < size ; ++j) innerContainer[j - 1] = innerContainer[j];
		--size;
	}
	
	public Appointment get (int index) { return index >= 0 && index < size ? innerContainer[index] : null; }

	public int indexOf (Appointment appointment) {
		
		int j = -1;
		for (Appointment app : innerContainer) {
			++j;
			if (appointment.equals(app)) return j;
		}
		return -1;
	}
}
