package fondt2.tlc;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import fondt2.tlc.util.DayOfWeekHelper;
import lombok.Getter;

@Getter
public class Band {
	
	private DayOfWeek[] combinedDays;
	private double costInterval;
	private LocalTime startTime, endTime;
	
	public Band(LocalTime startTime, LocalTime endTime, DayOfWeek[] combinedDays, double costInterval) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.costInterval = costInterval;
		this.combinedDays = Arrays.copyOf(combinedDays, combinedDays.length);
	}
	
	public boolean isInBand (LocalDateTime localDateTime) {
		
		LocalTime lt = LocalTime.of(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
		return DayOfWeekHelper.isDayIn(localDateTime.getDayOfWeek(), combinedDays) && !lt.isBefore(startTime) && !lt.isAfter(endTime);
	}
	
	public boolean isValid () {
		
		return startTime.isBefore(endTime) && combinedDays != null && Arrays.stream(combinedDays).filter(x -> x != null).count() != combinedDays.length && costInterval >= 0;
	}

}
