package edlift.controller;

import java.io.PrintWriter;
import java.util.List;

import edlift.model.Lift;
import edlift.model.Installation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public interface Controller {
	
	List<Installation> getInstallations();
	
	void startSimulation();
	void stopSimulation();
	void setLogger(TextArea view, PrintWriter pw);
	
	Lift getLift();
	void setLift(Lift newAsc);
	void goToFloor(int floor);	
	
	
	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	public static void info(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
}
