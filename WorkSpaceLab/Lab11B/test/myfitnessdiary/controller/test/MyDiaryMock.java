package myfitnessdiary.controller.test;

import java.time.LocalDate;

import myfitnessdiary.model.Activity;
import myfitnessdiary.model.Intensity;
import myfitnessdiary.model.MyFitnessDiary;
import myfitnessdiary.model.Workout;

public class MyDiaryMock extends MyFitnessDiary {

	public MyDiaryMock() {
		super();
		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);

		Workout wo = new Workout(LocalDate.now(), 20, Intensity.LOW, act);
		addWorkout(wo);

		act = new Activity("AS1");
		act.insertCalories(Intensity.LOW, 1);
		act.insertCalories(Intensity.MEDIUM, 2);
		act.insertCalories(Intensity.HIGH, 3);
		wo = new Workout(LocalDate.now().plusDays(1), 30, Intensity.MEDIUM, act);
		addWorkout(wo);

		act = new Activity("AS2");
		act.insertCalories(Intensity.LOW, 4);
		act.insertCalories(Intensity.MEDIUM, 5);
		act.insertCalories(Intensity.HIGH, 6);

		wo = new Workout(LocalDate.now(), 20, Intensity.LOW, act);
		addWorkout(wo);

		act = new Activity("AS3");
		act.insertCalories(Intensity.LOW, 7);
		act.insertCalories(Intensity.MEDIUM, 8);
		act.insertCalories(Intensity.HIGH, 9);

		wo = new Workout(LocalDate.now().plusDays(1), 20, Intensity.HIGH, act);
		addWorkout(wo);
	}

}
