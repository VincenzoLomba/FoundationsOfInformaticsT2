package fondt2.tlc;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import fondt2.tlc.util.DayOfWeekHelper;
import lombok.Getter;

@Getter /* See: https://projectlombok.org/features/GetterSetter */
public class Band {
	
	private DayOfWeek[] combinedDays;
	private double costPerInterval;
	private LocalTime startTime, endTime;
	
	public Band(LocalTime startTime, LocalTime endTime, DayOfWeek[] combinedDays, double costPerInterval) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.costPerInterval = costPerInterval;
		this.combinedDays = Arrays.copyOf(combinedDays, combinedDays.length);
	}
	
	public boolean isInBand (LocalDateTime localDateTime) {
		
		LocalTime lt = localDateTime.toLocalTime();
		return DayOfWeekHelper.isDayIn(localDateTime.getDayOfWeek(), getCombinedDays())
			&& !lt.isBefore(getStartTime())
			&& !lt.isAfter(getEndTime());
	}
	
	public boolean isValid () {
		
		return getCostPerInterval() >= 0
			&& getStartTime() != null
			&& getEndTime() != null
			&& getStartTime().isBefore(getEndTime())
			&& getCombinedDays() != null
			&& getCombinedDays().length > 0
			&& Arrays.stream(getCombinedDays()).allMatch(x -> x != null)
		;
	}

}
