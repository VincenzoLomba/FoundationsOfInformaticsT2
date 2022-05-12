package myfitnessdiary.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import myfitnessdiary.model.Activity;
import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.model.Workout;
import myfitnessdiary.persistence.ActivityRepository;
import myfitnessdiary.persistence.ReportWriter;

public abstract class Controller {

	private FitnessDiary diary;
	private ActivityRepository activityRepository;
	private ReportWriter reportWriter;

	public Controller(FitnessDiary diary, ActivityRepository activityRepository, ReportWriter reportWriter) {
		
		this.diary = diary;
		this.activityRepository = activityRepository;
		this.reportWriter = reportWriter;
	}

	protected Set<String> getActivityNames() {
		return activityRepository.getActivityNames();
	}
	
	protected Writer createReportWriter() throws IOException {
		return new FileWriter("ReportSettimanale.txt");
	}
	
	protected FitnessDiary getFitnessDiary() {
		return diary;
	}

	public void addWorkout(Workout wo) {
		diary.addWorkout(wo);
	}

	public Activity getActivity(String activityName) {
		return activityRepository.getActivity(activityName);
	}

	public List<Workout> getDayWorkouts(LocalDate date) {
		return diary.getDayWorkouts(date);
	}

	public List<Workout> getWeekWorkouts(LocalDate date) {
		return diary.getWeekWorkouts(date);
	}

	public abstract String getDayWorkout(LocalDate data);

	public Set<String> getActivities() {
		return new TreeSet<String>(getActivityNames());
	}
	
	public void printWeekReport(LocalDate date) {
		try (Writer writer = createReportWriter()) {
			reportWriter.printReport(writer, date, getFitnessDiary());
		} catch (IOException e) {
			alert("Errore", "Scrittura Report", "Impossibile scrivere il file di report.");
		}
	}

	public void alert(String title, String headerMessage, String contentMessage) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

}