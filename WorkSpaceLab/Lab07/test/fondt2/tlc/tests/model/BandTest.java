package fondt2.tlc.tests.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;



import fondt2.tlc.Band;
import fondt2.tlc.tests.util.LocalDateTimeHelper;


public class BandTest {
	private Band band;
	
	@BeforeEach
	public void startUp()
	{
	 band = new Band(LocalTime.of(8, 0), LocalTime.of(18, 0),
	 new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY}, 15);
			
	}
	
	@Test
	public void testGetCostPerInterval()
	{
		assertEquals(band.getCostPerInterval(), 15, 0.1);
	}
	
	@Test
	public void testGetStartTime()
	{
		LocalTime start= band.getStartTime();
		assertTrue(start.getHour()==8 && start.getMinute()==0);		
	
	}
	
	@Test
	public void testGetCombinedDays()
	{
		DayOfWeek[] result= band.getCombinedDays();
		assertEquals(result[0],DayOfWeek.MONDAY);
		assertEquals(result[1],DayOfWeek.TUESDAY);
		assertEquals(result[2],DayOfWeek.WEDNESDAY);
		
		
		
	}
	
	@Test
	public void testGetEndTime()
	{
		LocalTime end= band.getEndTime();
		assertTrue(end.getHour()==18 && end.getMinute()==0);	
		
	}
	
	
	
	
	@Test
	public void testIsInBand() {
		Band band = new Band(LocalTime.of(10, 0), LocalTime.of(12, 0),
				new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.TUESDAY }, 10);
		
		LocalDateTime dateTime = LocalDateTimeHelper.getLocalDateTimeWith(22, 22, DayOfWeek.THURSDAY);
		assertFalse(band.isInBand(dateTime));
		
		dateTime = LocalDateTimeHelper.getLocalDateTimeWith(11, 22, DayOfWeek.TUESDAY);
		assertTrue(band.isInBand(dateTime));
		
		dateTime = LocalDateTimeHelper.getLocalDateTimeWith(19, 22, DayOfWeek.MONDAY);
		assertFalse(band.isInBand(dateTime));
	}

	@Test
	public void testIsValid() {
		
		assertTrue(band.isValid());
	}

	@Test
	public void testIsNotValid1() {
		Band band1 = new Band(LocalTime.of(10, 0), LocalTime.of(12, 0),
				new DayOfWeek[0], 10);
		assertFalse(band1.isValid());
	}

	@Test
	public void testIsNotValid2() {
		Band band1 = new Band(LocalTime.of(12, 0), LocalTime.of(10, 0),
				new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.TUESDAY }, 10);
		assertFalse(band1.isValid());
	}

}
