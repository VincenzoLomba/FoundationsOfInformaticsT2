package flights.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class FlightSchedule {
	
	private Aircraft aircraft;
	private Airport arrivalAirport;
	private LocalTime arrivalLocalTime;
	private String code;
	private int dayOffset;
	private Set<DayOfWeek> daysOfWeek;
	private Airport departureAirport;
	private LocalTime departureLocalTime;
	
	public FlightSchedule (String code, Airport departureAirport, LocalTime departureLocalTime, Airport arrivalAirport, LocalTime arrivalLocalTime, int dayOffset, Collection<DayOfWeek> daysOfWeek, Aircraft aircraft) {
		
		if (code == null || departureAirport == null || departureLocalTime == null || arrivalAirport == null || arrivalLocalTime == null || daysOfWeek == null || daysOfWeek.size() == 0 || aircraft == null)
			throw new IllegalArgumentException("Alcuni dei parametri passati al costruttore di " + FlightSchedule.class.getSimpleName() + " sono invalidi in quanto nulli o Collezioni vuote.");
		this.aircraft = aircraft;
		this.arrivalAirport = arrivalAirport;
		this.arrivalLocalTime = arrivalLocalTime;
		this.code = code;
		this.dayOffset = dayOffset;
		this.daysOfWeek = new TreeSet<>(daysOfWeek); /* It has to be ordered. */
		this.departureAirport = departureAirport;
		this.departureLocalTime = departureLocalTime;
	}
	
	public Duration getFlightDuration () {
		
		OffsetDateTime departure = OffsetDateTime.of(
			LocalDate.now(),
			departureLocalTime,
			ZoneOffset.ofHours(getDepartureAirport().getCity().getTimeZone())
		);
		OffsetDateTime arrival = OffsetDateTime.of(
			LocalDate.now().plusDays(getDayOffset()),
			arrivalLocalTime,
			ZoneOffset.ofHours(getArrivalAirport().getCity().getTimeZone())
		);
		Duration response = Duration.between(departure, arrival);
		return response.isNegative() ? response.plusDays(1) : response;
	}
	
	@Override
	public int hashCode() { return Objects.hash(aircraft, arrivalAirport, arrivalLocalTime, code, dayOffset, daysOfWeek, departureAirport, departureLocalTime); }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		FlightSchedule other = (FlightSchedule) obj;
		return Objects.equals(aircraft, other.aircraft) && Objects.equals(arrivalAirport, other.arrivalAirport)
				&& Objects.equals(arrivalLocalTime, other.arrivalLocalTime) && Objects.equals(code, other.code)
				&& dayOffset == other.dayOffset && Objects.equals(daysOfWeek, other.daysOfWeek)
				&& Objects.equals(departureAirport, other.departureAirport)
				&& Objects.equals(departureLocalTime, other.departureLocalTime);
	}
	
	public Aircraft getAircraft() { return aircraft; }
	public Airport getArrivalAirport() { return arrivalAirport; }
	public LocalTime getArrivalLocalTime() { return arrivalLocalTime; }
	public String getCode() { return code; }
	public int getDayOffset() { return dayOffset; }
	public Set<DayOfWeek> getDaysOfWeek() { return daysOfWeek; }
	public Airport getDepartureAirport() { return departureAirport; }
	public LocalTime getDepartureLocalTime() { return departureLocalTime; }
}
