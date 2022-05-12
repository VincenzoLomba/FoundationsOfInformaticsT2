import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.model.Workout;

public class MyFitnessDiary implements FitnessDiary {

	private List<Workout> workouts;
	
	public MyFitnessDiary() { this.workouts = new ArrayList<>(); }
	
	@Override
	public void addWorkout(Workout wo) { workouts.add(wo); }
	
	@Override
	public List<Workout> getDayWorkouts(LocalDate date) {
		
		return workouts.stream().filter(wo -> wo.getDate().equals(date)).toList();
	}
	
	@Override
	public List<Workout> getWeekWorkouts(LocalDate date) {
		
		List<Workout> response = new ArrayList<Workout>();
		LocalDate day = date.minusDays(date.getDayOfWeek().ordinal());
		while (day.getDayOfWeek().ordinal() < 6) {
			response.addAll(getDayWorkouts(date));
			day.plusDays(1);
		}
		return response;
	}
	
	private int getWorkoutsCalories (List<Workout> workouts) { return workouts.stream().map(w -> w.getBurnedCalories()).reduce(0, Integer::sum); }
	
	@Override
	public int getDayWorkoutCalories(LocalDate date) { return getWorkoutsCalories(getDayWorkouts(date)); }

	@Override
	public int getWeekWorkoutCalories(LocalDate date) { return getWorkoutsCalories(getWeekWorkouts(date)); }

	private int getWorkoutsMinutes (List<Workout> workouts) { return workouts.stream().map(w -> w.getDuration()).reduce(0, Integer::sum); }

	@Override
	public int getDayWorkoutMinutes(LocalDate date) { return getWorkoutsMinutes(getDayWorkouts(date)); }

	@Override
	public int getWeekWorkoutMinutes(LocalDate date) { return getWorkoutsMinutes(getWeekWorkouts(date)); }

	

}
