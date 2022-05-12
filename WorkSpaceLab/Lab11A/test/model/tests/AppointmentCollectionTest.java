package model.tests;

import static org.junit.jupiter.api.Assertions.*;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import model.*;

public class AppointmentCollectionTest {
	
	AppointmentCollection appColl=new AppointmentCollection(10);

	@Test
	public void testAdd() {
		Appointment app=new Appointment("test", LocalDateTime.now(), Duration.of(10, ChronoUnit.MINUTES));
		appColl.add(app);
		appColl.add(app);
		appColl.add(app);
		assertEquals(3, appColl.size());
	}

	@Test
	public void testRemoveAt() {
		Appointment app=new Appointment("test", LocalDateTime.now(), Duration.of(10, ChronoUnit.MINUTES));
		appColl.add(app);
		appColl.add(app);
		appColl.add(app);
		assertEquals(3, appColl.size());
		appColl.remove(0);
		assertEquals(2, appColl.size());
	}
	
	@Test
	public void testGet() {
		Appointment app=null;
		appColl.add(new Appointment("test", LocalDateTime.now(), Duration.of(10, ChronoUnit.MINUTES)));
		app= appColl.get(0);
		assertNotNull(app);
	}

}
