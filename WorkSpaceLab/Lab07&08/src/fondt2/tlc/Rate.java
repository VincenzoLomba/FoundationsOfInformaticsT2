package fondt2.tlc;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import lombok.Getter;

public class Rate {
	
	@Getter private String name;   /* See: https://projectlombok.org/features/GetterSetter */
	@Getter private Band[] bands;  /* See: https://projectlombok.org/features/GetterSetter */
	private int intervallInMills;
	private double startCallCost;
	private String numberRoot;
	
	public Rate(String name, Band[] bands, int intervallInMills, double startCallCast, String numberRoot) {
		
		this.name = name;
		this.bands = Arrays.copyOf(bands, bands.length);
		this.intervallInMills = intervallInMills;
		this.startCallCost = startCallCast;
		this.numberRoot = numberRoot;
	}
	
	public boolean isApplicableTo (String destinationNumber) { return destinationNumber.startsWith(numberRoot); }
	
	private Band[] selectBandsInDay (DayOfWeek day) {
		
		return Arrays.stream(bands).filter(b -> Arrays.asList(b.getCombinedDays()).contains(day)).toArray(Band[]::new);
	}
	
	private Band[] sortBandsByStartTime (Band[] bands) {
		
		Arrays.sort(bands, new Comparator<Band>() {
			@Override
			public int compare(Band b1, Band b2) {
				return b1.getStartTime().compareTo(b2.getStartTime());
			}
		});
		return bands;
	}
	
	private boolean validateBandsInDay (Band[] bandsInDay) {
		
		if (!bandsInDay[0].getStartTime().equals(LocalTime.MIN) || !bandsInDay[bandsInDay.length - 1].getEndTime().equals(LocalTime.MAX)) return false;
		if (bands.length > 1)
			for (int j = 0 ; j < bands.length - 1 ; ++j)
				if (!bands[j].getEndTime().plusNanos(1).equals(bands[j+1].getStartTime())) return false;
		return true;
	}
	
	private boolean validateDay (DayOfWeek day) {
		
		return validateBandsInDay(
			sortBandsByStartTime(selectBandsInDay(day))
		);
		
	}
	
	public boolean isValid () {
		
		/* Parameters "name", "intervallInMills", "startCallCast" and "numberRoot" are assumed correct. */
		return bands != null
			&& bands.length > 0
			&& Arrays.stream(bands).allMatch(b -> b.isValid())
			&& Arrays.stream(new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY })
			   .allMatch(d -> validateDay(d));
	}
	
	private double getCostPerInterval (PhoneCall call) {
		
		Optional<Band> band = Arrays.stream(bands).filter(b -> b.isInBand(call.getStart())).findFirst();
		return band.isEmpty() ? -1 : band.get().getCostPerInterval();
	}
	
	public double getCallCost (PhoneCall call) {
				
		/* It is assumed that "call.getStart()" is lower then "call.getEnd()" */
		double costPerInterval = getCostPerInterval(call);
		if (costPerInterval == -1) return -1;
		long intervals = (long) Math.ceil((double) Duration.between(call.getStart(), call.getEnd()).toMillis() / intervallInMills);
		return startCallCost + costPerInterval*intervals;
	}

}
