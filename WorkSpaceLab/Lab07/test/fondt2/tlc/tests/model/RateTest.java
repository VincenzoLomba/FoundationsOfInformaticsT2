package fondt2.tlc.tests.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fondt2.tlc.Band;
import fondt2.tlc.Rate;

public class RateTest {
private Rate rate;
	
	@BeforeEach
	public void setUp()
	{
		
		DayOfWeek[] workWeek = new DayOfWeek[] { DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
				DayOfWeek.FRIDAY };
		DayOfWeek[] weekEnd = new DayOfWeek[] { DayOfWeek.SATURDAY,
				DayOfWeek.SUNDAY };
		Band[] bands = new Band[] {
				new Band(LocalTime.MIN, LocalTime.MAX, weekEnd, 10),
				new Band(LocalTime.of(0, 0), LocalTime.of(8, 0).minusNanos(1), workWeek, 10),
				new Band(LocalTime.of(8, 0), LocalTime.of(18, 00).minusNanos(1), workWeek, 20),
				new Band(LocalTime.of(18, 00), LocalTime.MAX, workWeek, 10) 
				};
		rate= new Rate("AMRC", bands, 1000, 12, "+39339");
		
	}
	
	@Test
	void testGetName() {
		assertTrue(rate.getName().equals("AMRC"));
	}

	@Test
	void testGetBands() {
		Band[] bande = rate.getBands();
		assertEquals(4, bande.length);

		// 1 band
		LocalTime start = bande[0].getStartTime();
		assertTrue(start.getHour() == LocalTime.MIN.getHour() && start.getMinute() == LocalTime.MIN.getMinute());
		LocalTime end = bande[0].getEndTime();
		assertTrue(end.getHour() == LocalTime.MAX.getHour() && end.getMinute() == LocalTime.MAX.getMinute());
		assertEquals(bande[0].getCostPerInterval(), 10, 0.1);

		// 2 band
		start = bande[1].getStartTime();
		assertTrue(start.getHour() == 0 && start.getMinute() == 0);
		end = bande[1].getEndTime();
		assertTrue(end.getHour() == LocalTime.of(8, 0).minusNanos(1).getHour() && end.getMinute() ==LocalTime.of(8, 0).minusNanos(1).getMinute());
		assertEquals(bande[1].getCostPerInterval(), 10, 0.1);

		// 3 band
		start = bande[2].getStartTime();
		assertTrue(start.getHour() == 8 && start.getMinute() == 0);
		end = bande[2].getEndTime();
		assertTrue(end.getHour() == LocalTime.of(18, 00).minusNanos(1).getHour() && end.getMinute() ==LocalTime.of(18, 00).minusNanos(1).getMinute());
		assertEquals(bande[2].getCostPerInterval(), 20, 0.1);

		// 3 band
		start = bande[3].getStartTime();
		assertTrue(start.getHour() == 18 && start.getMinute() == 0);
		end = bande[3].getEndTime();
		assertTrue(end.getHour() == LocalTime.MAX.getHour() && end.getMinute() == LocalTime.MAX.getMinute());
		assertEquals(bande[3].getCostPerInterval(), 10, 0.1);

	}

	@Test
	void testIsApplicableTo() {
		assertTrue(rate.isApplicableTo("+39339"));
	}

}
