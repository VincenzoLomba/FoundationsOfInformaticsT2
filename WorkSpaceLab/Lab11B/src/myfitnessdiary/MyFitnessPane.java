package myfitnessdiary;

import java.time.LocalDate;

import myfitnessdiary.controller.Controller;
import myfitnessdiary.model.Activity;
import myfitnessdiary.model.Intensity;
import myfitnessdiary.model.Workout;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MyFitnessPane extends BorderPane {

	private ComboBox<String> activitiesComboBox;
	private ComboBox<Intensity> intensitiesComboBox;
	private DatePicker datePicker;
	private Spinner<Integer> durationSpinner;
	private TextArea dailyWorkout;
	private DatePicker reportPicker;
	private Controller controller;
	
	public MyFitnessPane(Controller controller) {

		this.controller=controller;
		activitiesComboBox = new ComboBox<String>(FXCollections.observableArrayList(controller.getActivities()));
		activitiesComboBox.setEditable(false);
		activitiesComboBox.setValue(controller.getActivities().iterator().next());

		intensitiesComboBox = new ComboBox<Intensity>(FXCollections.observableArrayList(Intensity.values()));
		intensitiesComboBox.setEditable(false);
		intensitiesComboBox.setValue(Intensity.LOW);

		datePicker = new DatePicker(LocalDate.now());
		datePicker.setValue(LocalDate.now());

		reportPicker = new DatePicker(LocalDate.now());
		reportPicker.setValue(LocalDate.now());

		durationSpinner = new Spinner<Integer>(0, 1000, 0, 5);
		durationSpinner.setEditable(false);

		// dataPicker
		VBox vboxleft = new VBox(2);
		{
			vboxleft.setSpacing(10);
			vboxleft.setPadding(new Insets(0, 20, 10, 20));
			Label label = new Label("Data:");
			vboxleft.getChildren().add(label);
			vboxleft.getChildren().add(datePicker);
			label = new Label("Attività:");
			vboxleft.getChildren().add(label);
			vboxleft.getChildren().add(activitiesComboBox);
		}

		VBox vboxright = new VBox(2);
		{
			vboxright.setSpacing(10);
			vboxright.setPadding(new Insets(0, 20, 10, 20));
			Label label = new Label("Durata:");
			vboxright.getChildren().add(label);
			vboxright.getChildren().add(durationSpinner);
			label = new Label("Intensità:");
			vboxright.getChildren().add(label);
			vboxright.getChildren().add(intensitiesComboBox);
		}

		VBox reportPane = new VBox(2);
		{
			reportPane.setSpacing(10);
			reportPane.setPadding(new Insets(0, 20, 10, 20));
			Label label = new Label("Stampa report settimanale");
			reportPane.getChildren().add(label);
			label = new Label("Seleziona Settimana:");
			reportPane.getChildren().add(label);
			reportPane.getChildren().add(reportPicker);
			Button button = new Button("Stampa");
			reportPane.setAlignment(Pos.BASELINE_RIGHT);
			button.setOnAction(event ->  controller.printWeekReport(reportPicker.getValue()) );
			reportPane.getChildren().add(button);
		}

		VBox leftPane = new VBox(2);
		{
			leftPane.setSpacing(10);
			leftPane.setPadding(new Insets(0, 20, 10, 20));
			HBox hlbox = new HBox(2);
			hlbox.getChildren().add(vboxleft);
			hlbox.getChildren().add(vboxright);
			leftPane.getChildren().add(hlbox);
			Button button = new Button("Inserisci");
			button.setOnAction(this::myHandle);
			leftPane.setAlignment(Pos.BASELINE_RIGHT);
			leftPane.getChildren().add(button);
			leftPane.getChildren().add(reportPane);
		}

		this.setLeft(leftPane);

		dailyWorkout = new TextArea();
		dailyWorkout.setEditable(false);
		dailyWorkout.setPrefWidth(400);

		this.setCenter(dailyWorkout);
	}

	private void myHandle(ActionEvent event) {
		LocalDate date = datePicker.getValue();
		if (date == null) {
			controller.alert("Dati errati", "Data errata", "La data inserita non è corretta");
			return;
		}
		Integer duration = durationSpinner.getValue();
		if (duration <= 0) {
			controller.alert("Dati errati", "Durata non valida", "Una durata nulla o negativa non è ammissibile");
			return;
		}
		Intensity intensity = intensitiesComboBox.getValue();
		if (intensity == null) {
			controller.alert("Dati errati", "Intensità nulla", "Una intensità  negativa non è ammissibile");
			return;
		}
		Activity activity = controller.getActivity(activitiesComboBox.getValue());
		if (activity == null) {
			controller.alert("Dati errati", "Attività  inesistente", "Non esiste alcuna attività  con questo nome");
			return;
		}
		Workout wo = new Workout(date, duration, intensity, activity);
		controller.addWorkout(wo);
		dailyWorkout.setText(controller.getDayWorkout(date));
	}

}
