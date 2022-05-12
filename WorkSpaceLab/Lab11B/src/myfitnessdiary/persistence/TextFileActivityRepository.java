package myfitnessdiary.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Set;

import myfitnessdiary.model.Activity;
import myfitnessdiary.model.Intensity;

public class TextFileActivityRepository implements ActivityRepository {

	private HashMap<String, Activity> allActivities;

	public TextFileActivityRepository(Reader baseReader) throws IOException, BadFileFormatException {
		if (baseReader == null)
			throw new BadFileFormatException("Reader nullo");

		allActivities = new HashMap<String, Activity>();

		BufferedReader reader = new BufferedReader(baseReader);
		String line = null;
		while ((line = reader.readLine()) != null) {
			String data[] = line.split("[-]+");				// oppure StringTokenizer(line, "-")
			String name = data[0].trim();
			String cal[] = data[1].trim().split("\\s+");	// oppure StringTokenizer(line)
			int calL, calM, calH;
			try {
				calL = Integer.parseInt(cal[0]);
				calM = Integer.parseInt(cal[1]);
				calH = Integer.parseInt(cal[2]);
			} catch (NumberFormatException num) {
				throw new BadFileFormatException("Valori calorie presentano problemi");
			}
			Activity act = new Activity(name);
			act.insertCalories(Intensity.LOW, calL);
			act.insertCalories(Intensity.MEDIUM, calM);
			act.insertCalories(Intensity.HIGH, calH);
			addActivity(act);
		}

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
