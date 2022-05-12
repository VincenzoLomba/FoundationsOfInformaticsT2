package myfitnessdiary.persistence;

import java.util.Set;
import myfitnessdiary.model.Activity;

public interface ActivityRepository {

	public Set<String> getActivityNames();

	public Activity getActivity(String name);
}
