package myfitnessdiary.model;

import java.util.HashMap;
import java.util.Map;

public class Activity {

	private String name;
	private Map<Intensity, Integer> calories;

	public Activity(String name) {
		
		this.setName(name);
		calories = new HashMap<Intensity, Integer>();
	}

	public void setName(String name) { this.name = name; }

	public String getName() { return this.name; }

	/**
	 * Permette di inserire le calorie di riferimento per l'intensit‡
	 * 
	 * @param intensity
	 *           Ë una stringa che rappresenta il livello di intensit‡†
	 * @param calories
	 *            Ë un intero che rappresenta le calorie bruciate riferite
	 *            all'intesit‡
	 */
	public void insertCalories(Intensity intensity, int calories) { this.calories.put(intensity, calories); }

	/**
	 * Permette di recuperare le calorie bruciate associate all'intensit‡†
	 * 
	 * @param intensity
	 *            Ë una stringa che rappresenta il livello di intensit√†
	 */
	public int getCalories(Intensity intensity) { return this.calories.get(intensity); }

}
