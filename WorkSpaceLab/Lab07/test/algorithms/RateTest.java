package algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fondt2.tlc.Band;
import fondt2.tlc.PhoneCall;
import fondt2.tlc.Rate;
import fondt2.tlc.tests.util.LocalDateTimeHelper;

public class RateTest {
private Rate rate;
	
	@BeforeEach
	public void setUp() {
		
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
	
	private Rate createInvalidRate() {
		
		DayOfWeek[] workWeek = new DayOfWeek[] { DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
				DayOfWeek.FRIDAY };
		DayOfWeek[] weekEnd = new DayOfWeek[] { DayOfWeek.SATURDAY,
				DayOfWeek.SUNDAY };
		Band[] bands = new Band[] {
				new Band(LocalTime.MIN, LocalTime.MAX, weekEnd,
						10),
				new Band(LocalTime.MIN, LocalTime.of(8, 0).minusNanos(1), workWeek,
						10),
				new Band(LocalTime.of(8, 0), LocalTime.of(18, 0).minusNanos(1),
						workWeek, 20) };
		return new Rate("TIM", bands, 1000, 10, "+39339");
	}

	@Test
	public void testGetCost() {
		
		LocalDateTime start = LocalDateTimeHelper.getLocalDateTimeWith(22, 24, DayOfWeek.WEDNESDAY);
		LocalDateTime end = LocalDateTimeHelper.getLocalDateTimeWith(22, 27, DayOfWeek.WEDNESDAY);
		PhoneCall call = new PhoneCall(start, end, "+3933912312312");
		assertEquals(1812, rate.getCallCost(call), 0.0001);
	}

	@Test
	public void testIsValid() {
		
		assertTrue(rate.isValid());
	}

	@Test
	public void testNotValid() {
		
		assertFalse(createInvalidRate().isValid());
	}
}
