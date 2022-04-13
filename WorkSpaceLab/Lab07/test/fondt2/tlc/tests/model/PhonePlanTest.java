package fondt2.tlc.tests.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fondt2.tlc.Band;
import fondt2.tlc.PhonePlan;
import fondt2.tlc.Rate;

public class PhonePlanTest {
private PhonePlan phoneplane;
	
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
		Rate rate= new Rate("AMRC", bands, 1000, 12, "+39339");
		Rate[] rat= new Rate[1] ;
		rat[0]=rate;
		phoneplane= new PhonePlan("EasyPlan", rat);
		
	}
	
	@Test
	void testGetName() {
		assertTrue(phoneplane.getName().contentEquals("EasyPlan"));
	}

}
