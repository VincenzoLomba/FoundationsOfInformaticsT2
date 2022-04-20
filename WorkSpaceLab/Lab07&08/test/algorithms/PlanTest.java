package algorithms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;



import fondt2.tlc.*;
import fondt2.tlc.tests.util.LocalDateTimeHelper;

public class PlanTest {
	
private PhonePlan plane;
	
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
		Rate rate= new Rate("AMRC", bands, 1000, 12, "+39339");
		Rate[] rat= new Rate[1] ;
		rat[0]=rate;
		plane= new PhonePlan("EasyPlan", rat);
		
	}

	@Test
	public void testGetCallCost() {
		
		LocalDateTime start = LocalDateTimeHelper.getLocalDateTimeWith(22, 24, DayOfWeek.WEDNESDAY);
		LocalDateTime end = LocalDateTimeHelper.getLocalDateTimeWith(22, 27, DayOfWeek.WEDNESDAY);
		PhoneCall call = new PhoneCall(start, end, "+3933912312312");
		double actual = plane.getCallCost(call);
		assertEquals(1812, actual, 0.00001);
	}
	
	@Test
	public void testGetCallCostBetweeenRates() {
		
		DayOfWeek[] workWeek = new DayOfWeek[] { DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
				DayOfWeek.FRIDAY };
		DayOfWeek[] weekEnd = new DayOfWeek[] { DayOfWeek.SATURDAY,
				DayOfWeek.SUNDAY };
		Band[] bands = new Band[] {
				new Band(LocalTime.MIN, LocalTime.MAX, weekEnd, 10),
				new Band(LocalTime.of(0, 0), LocalTime.of(8, 0).minusNanos(1), workWeek, 10),
				new Band(LocalTime.of(8, 0), LocalTime.of(18, 30).minusNanos(1), workWeek, 20),
				new Band(LocalTime.of(18, 30), LocalTime.MAX, workWeek, 10) 
				};
		Rate r = new Rate("TIM", bands, 60000, 15, "+39339");
		PhonePlan plan = new PhonePlan("Altro piano TIM", new Rate[] {r});
		
		LocalDateTime start = LocalDateTimeHelper.getLocalDateTimeWith(18, 28, 10, DayOfWeek.WEDNESDAY);
		LocalDateTime end = LocalDateTimeHelper.getLocalDateTimeWith(18, 32, 25, DayOfWeek.WEDNESDAY);
		PhoneCall call = new PhoneCall(start, end, "+3933912312312");
		double actual = plan.getCallCost(call);
		assertEquals(115, actual, 0.00001);
	}

	@Test
	public void testGetCallCost_2() {
		
		DayOfWeek[] days1 = { DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY,
				DayOfWeek.FRIDAY };
		LocalTime start1 = LocalTime.MIN;
		LocalTime end1 = LocalTime.MAX;
		Band band1 = new Band(start1, end1, days1, 1.25);

		DayOfWeek[] days2 = { DayOfWeek.TUESDAY, DayOfWeek.SATURDAY,
				DayOfWeek.THURSDAY, DayOfWeek.SUNDAY };
		LocalTime start2 = LocalTime.MIN;
		LocalTime end2 = LocalTime.MAX;
		Band band2 = new Band(start2, end2, days2, 2.25);

		Band[] bands = { band1, band2 };

		Rate rate = new Rate("ZMPhone", bands, 1000, 10, "+39339");

		Rate[] rates = { rate };
		String name = "Autoricarica10";

		PhonePlan plan = new PhonePlan(name, rates);

		LocalDateTime start = LocalDateTime.of(2019, Month.MARCH, 29, 17, 55, 0);
		LocalDateTime end = LocalDateTime.of(2019, Month.MARCH, 29, 17, 59, 0);

		PhoneCall phoneCall = new PhoneCall(start, end, "+3933912345678");
		assertEquals(310.00, plan.getCallCost(phoneCall), 0.00001);
	}

	@Test
	public void testGetCallCost_UnknownRate() {
		
		LocalDateTime notRelevant = LocalDateTime.now();
		PhoneCall call = new PhoneCall(notRelevant, notRelevant, "");
		double actual = plane.getCallCost(call);
		assertEquals(-1, actual, 0.01);
	}

	@Test
	public void testIsValid() {
	
		assertTrue(plane.isValid());
	}
	
	@Test
	public void testIsNotValid() {
		
		DayOfWeek[] workWeek = new DayOfWeek[] { DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
				DayOfWeek.FRIDAY };
		DayOfWeek[] weekEnd = new DayOfWeek[] { DayOfWeek.SATURDAY,
				DayOfWeek.SUNDAY };
		Band[] bands = new Band[] {
				new Band(LocalTime.MIN, LocalTime.MAX, weekEnd, 10),
				new Band(LocalTime.of(0, 0), LocalTime.of(8, 0).minusNanos(1), workWeek, 10),
				new Band(LocalTime.of(8, 0), LocalTime.of(18, 30).minusNanos(1), workWeek, 20),
				new Band(LocalTime.of(18, 30), LocalTime.MAX, workWeek, 10) 
				};
		Rate r = new Rate("TIM", bands, 60000, 15, "+39339");
		PhonePlan plan = new PhonePlan("Piano TIM valido", new Rate[] {r});
		assertTrue(plan.isValid());
		
		DayOfWeek[] halfWeek = new DayOfWeek[] { DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY};
		bands = new Band[] {
				new Band(LocalTime.MIN, LocalTime.MAX, weekEnd, 10),
				new Band(LocalTime.of(0, 0), LocalTime.of(8, 0).minusNanos(1), workWeek, 10),
				new Band(LocalTime.of(8, 0), LocalTime.of(18, 30).minusNanos(1), workWeek, 20),
				new Band(LocalTime.of(18, 30), LocalTime.MAX, halfWeek, 10) 
				};
		r = new Rate("TIM", bands, 60000, 15, "+39339");
		plan = new PhonePlan("Piano TIM invalido", new Rate[] {r});
		assertFalse(plan.isValid());
	}
}
