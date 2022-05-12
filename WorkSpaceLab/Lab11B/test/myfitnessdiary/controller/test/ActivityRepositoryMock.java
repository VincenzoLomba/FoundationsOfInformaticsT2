package myfitnessdiary.controller.test;

import java.util.HashMap;
import java.util.Set;

import myfitnessdiary.model.Activity;
import myfitnessdiary.model.Intensity;
import myfitnessdiary.persistence.ActivityRepository;

public class ActivityRepositoryMock implements ActivityRepository {

	private HashMap<String, Activity> allActivities;

	public ActivityRepositoryMock() {
		allActivities = new HashMap<String, Activity>();

		Activity act = new Activity("AS4");
		act.insertCalories(Intensity.LOW, 10);
		act.insertCalories(Intensity.MEDIUM, 11);
		act.insertCalories(Intensity.HIGH, 12);
		addActivity(act);

		act = new Activity("AS1");
		act.insertCalories(Intensity.LOW, 1);
		act.insertCalories(Intensity.MEDIUM, 2);
		act.insertCalories(Intensity.HIGH, 3);
		addActivity(act);

		act = new Activity("AS2");
		act.insertCalories(Intensity.LOW, 4);
		act.insertCalories(Intensity.MEDIUM, 5);
		act.insertCalories(Intensity.HIGH, 6);
		addActivity(act);

		act = new Activity("AS3");
		act.insertCalories(Intensity.LOW, 7);
		act.insertCalories(Intensity.MEDIUM, 8);
		act.insertCalories(Intensity.HIGH, 9);
		addActivity(act);
	}

	private void addActivity(Activity act) {

		allActivities.put(act.getName(), act);
	}

	public Activity getActivity(String name) {

		return allActivities.get(name);

	}

	public Set<String> getActivityNames() {
		return allActivities.keySet();

	}

}
