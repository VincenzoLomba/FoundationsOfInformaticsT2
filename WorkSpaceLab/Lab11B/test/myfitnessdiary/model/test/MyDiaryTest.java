package myfitnessdiary.model.test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;


import myfitnessdiary.model.Activity;
import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.model.Intensity;
import myfitnessdiary.model.MyFitnessDiary;
import myfitnessdiary.model.Workout;

public class MyDiaryTest {

	@Test
	public void addTest() {
		FitnessDiary diary = new MyFitnessDiary();

		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);

		LocalDate now = LocalDate.now();
		Workout wo = new Workout(now, 20, Intensity.LOW, act);
		diary.addWorkout(wo);
		
		List<Workout> wouts = diary.getDayWorkouts(now); 
		assertEquals(1, wouts.size());
		assertEquals(wo, wouts.get(0));
	}

	@Test
	public void getDayFeatures() {

		FitnessDiary diary = new MyFitnessDiary();

		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);

		LocalDate now = LocalDate.now();
		Workout wo = new Workout(now, 20, Intensity.LOW, act);
		diary.addWorkout(wo);
		wo = new Workout(now, 20, Intensity.LOW, act);
		diary.addWorkout(wo);
		wo = new Workout(now.plusDays(1), 20, Intensity.LOW, act);
		diary.addWorkout(wo);
		
		assertEquals(40, diary.getDayWorkoutMinutes(now));
		assertEquals(10*40, diary.getDayWorkoutCalories(now));
	}

	@Test
	public void getWeekFeatures() {

		FitnessDiary diary = new MyFitnessDiary();

		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);

		LocalDate now = LocalDate.now();
		now = now.plusDays(DayOfWeek.WEDNESDAY.getValue() - now.getDayOfWeek().getValue());
		Workout wo = new Workout(now, 20, Intensity.LOW, act);
		diary.addWorkout(wo);
		wo = new Workout(now.plusDays(1), 20, Intensity.LOW, act);
		diary.addWorkout(wo);
		wo = new Workout(now.plusWeeks(1), 20, Intensity.LOW, act);
		diary.addWorkout(wo);
		
		assertEquals(40, diary.getWeekWorkoutMinutes(now));
		assertEquals(10*40, diary.getWeekWorkoutCalories(now));
	}

	@Test
	public void getDayTest() {

		FitnessDiary diary = new MyFitnessDiary();

		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);

		Workout wo = new Workout(LocalDate.now(), 20, Intensity.LOW, act);

		diary.addWorkout(wo);
		wo = new Workout(LocalDate.now(), 30, Intensity.MEDIUM, act);

		diary.addWorkout(wo);
		wo = new Workout(LocalDate.now().plusDays(1), 30, Intensity.HIGH, act);
		diary.addWorkout(wo);
		assertEquals(2, diary.getDayWorkouts(LocalDate.now()).size());

	}

	@Test
	public void getWeekTest() {

		FitnessDiary diary = new MyFitnessDiary();

		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);

		LocalDate date = LocalDate.of(2017, Month.JUNE, 13);

		Workout wo = new Workout(date, 20, Intensity.LOW, act);
		diary.addWorkout(wo);

		wo = new Workout(date.minusDays(1), 30, Intensity.MEDIUM, act);

		diary.addWorkout(wo);

		wo = new Workout(date.plusDays(1), 30, Intensity.HIGH, act);
		diary.addWorkout(wo);

		wo = new Workout(date.plusDays(2), 30, Intensity.HIGH, act);
		diary.addWorkout(wo);

		wo = new Workout(date.plusWeeks(1), 30, Intensity.HIGH, act);
		diary.addWorkout(wo);

		assertEquals(4, diary.getWeekWorkouts(date).size());

		assertEquals(1, diary.getWeekWorkouts(date.plusWeeks(1)).size());

	}

}
