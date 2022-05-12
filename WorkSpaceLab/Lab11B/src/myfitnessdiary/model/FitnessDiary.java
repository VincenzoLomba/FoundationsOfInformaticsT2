package myfitnessdiary.model;

import java.time.LocalDate;
import java.util.List;

public interface FitnessDiary {
	
	List<Workout> getWeekWorkouts(LocalDate date);

	List<Workout> getDayWorkouts(LocalDate date);

	void addWorkout(Workout wo);

	int getWeekWorkoutCalories(LocalDate date);

	int getDayWorkoutCalories(LocalDate date);

	int getWeekWorkoutMinutes(LocalDate date);

	int getDayWorkoutMinutes(LocalDate date);

}
