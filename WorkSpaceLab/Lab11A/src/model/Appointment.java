package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Appointment implements Serializable {
	
	private static final long serialVersionUID = 2L; /* A different value of this serial has to be inserted in each class of the project that implements the Serializable interface. */
	
	private String description;
	private LocalDateTime from, to;
	
	public Appointment (String description, LocalDateTime from, LocalDateTime to) {
		
		this.description = description;
		this.from = from;
		this.to = to;
	}
	
	public Appointment (String description, LocalDateTime from, Duration duration) {
		
		this(description, from, from.plus(duration));
	}
	
	public String getDescription() { return description; }
	public LocalDateTime getFrom() { return from; }
	public LocalDateTime getTo() { return to; }
	public Duration getDuration() { return Duration.between(from, to); }
	
	public void setDescription(String description) { this.description = description; }
	public void setFrom(LocalDateTime from) { this.from = from; }
	public void setTo(LocalDateTime to) { this.to = to; }
	public void setDuration(Duration duration) { to = from.plus(duration); }
	
	public boolean equals(Appointment appointment) {
		
		return appointment.getFrom().equals(getFrom()) && appointment.getTo().equals(getTo()) && appointment.getDescription().equalsIgnoreCase(getDescription());
	}
	
	@Override
	public String toString () {
		return new StringBuilder(description)
			.append("\n")
			.append("da ")
			.append(getFrom().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY)))
			.append(" a ")
			.append(getTo().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY)))
			.toString();
	}

}
