package myfitnessdiary.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.persistence.ActivityRepository;
import myfitnessdiary.persistence.ReportWriter;

public class MyController extends Controller {

	private final DateTimeFormatter dateTimeFormatter;
	
	public MyController(FitnessDiary diary, ActivityRepository activityRepository, ReportWriter reportWriter) {
		
		super(diary, activityRepository, reportWriter);
		dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).localizedBy(Locale.ITALIAN);
	}

	@Override
	public String getDayWorkout(LocalDate data) {

		return new StringBuilder("Allenamento di " + dateTimeFormatter.format(data))
			.append(
				getDayWorkouts(data).stream().map(w -> {
					return new StringBuilder()
						.append(w.getActivity().getName())
						.append(" minuti: ")
						.append(w.getDuration())
						.append(" calorie bruciate: ")
						.append(w.getBurnedCalories())
						.append("\n")
						.toString()
					;
				}).reduce("\n", (s1, s2) -> { return new StringBuilder(s1).append(s2).toString(); })
			)
			.append("\nMinuti totali di allenamento: ")
			.append(getFitnessDiary().getDayWorkoutMinutes(data))
			.append("\nCalorie totali bruciate: ")
			.append(getFitnessDiary().getDayWorkoutCalories(data))
			.toString()
		;
	}

}
