package flights.controller.tests;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class LocalDateHelper {
	public static LocalDate getLocalDateWith(DayOfWeek day) {
		LocalDate localDate = LocalDate.now();
		LocalDate date = localDate.with(TemporalAdjusters.previousOrSame(day));
		return date;
	}
}
