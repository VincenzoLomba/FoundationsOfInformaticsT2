package fondt2.tlc.tests.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class LocalDateTimeHelper {
	
	public static LocalDateTime getLocalDateTimeWith(int hourOfDay, int minute, DayOfWeek day) {
		
		LocalDateTime dateTime = LocalDateTime.now()
				.withHour(hourOfDay)
				.withMinute(minute)
				.withSecond(0)
				.withNano(0);
		int distanceFromPreviousRequestedDay = 
				dateTime.getDayOfWeek().getValue() - day.getValue();
		int newDayOfMonth = dateTime.getDayOfMonth() - distanceFromPreviousRequestedDay;
		if (newDayOfMonth <= 0) {
			newDayOfMonth = dateTime.getDayOfMonth() + 
					(DayOfWeek.values().length - distanceFromPreviousRequestedDay);
		}
		dateTime = dateTime.withDayOfMonth(newDayOfMonth);
		return dateTime;
	}
	
	public static LocalDateTime getLocalDateTimeWith(int hourOfDay, int minute, int second, DayOfWeek day) {
		
		LocalDateTime dateTime = LocalDateTime.now()
				.withHour(hourOfDay)
				.withMinute(minute)
				.withSecond(second)
				.withNano(0);
		int distanceFromPreviousRequestedDay = 
				dateTime.getDayOfWeek().getValue() - day.getValue();
		int newDayOfMonth = dateTime.getDayOfMonth() - distanceFromPreviousRequestedDay;
		if (newDayOfMonth <= 0) {
			newDayOfMonth = dateTime.getDayOfMonth() + 
					(DayOfWeek.values().length - distanceFromPreviousRequestedDay);
		}
		dateTime = dateTime.withDayOfMonth(newDayOfMonth);
		return dateTime;
	}
}
