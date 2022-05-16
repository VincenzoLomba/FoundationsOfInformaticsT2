package myfitnessdiary.model;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class Workout {
	
	private LocalDate date;
	private int duration; /* It is assumed that the duration is expressed in minutes! */
	private Intensity intensity;
	private Activity activity;
	
	public Workout (LocalDate date, int duration, Intensity intensity, Activity activity) {
		if (date == null || duration <= 0 || intensity == null || activity == null)
			throw new IllegalArgumentException("Uno o piu' dei parametri passati a costruttore risultano essere nulli.");
		this.date = date;
		this.duration = duration;
		this.intensity = intensity;
		this.activity = activity;
	}
	
	public int getBurnedCalories () { return duration*activity.getCalories(intensity); }
}
