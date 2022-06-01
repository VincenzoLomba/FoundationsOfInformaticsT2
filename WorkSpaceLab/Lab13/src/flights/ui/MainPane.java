package flights.ui;

import java.time.LocalDate;
import java.util.ArrayList;

import flights.controller.Controller;
import flights.model.Airport;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane {

	public MainPane (Controller controller) {
		
		FlightScheduleListPanel rightPanel = new FlightScheduleListPanel();
		rightPanel.setFlightSchedules(new ArrayList<>());
		VBox leftPanel = new VBox();
		leftPanel.setAlignment(Pos.BASELINE_RIGHT);
		/* The Frame dimensions are imposed by the main Application. */
		
		this.setLeft(leftPanel);
		this.setCenter(rightPanel); /* In a BordarPane the central element always has to be set. */
		
		Label departureAirportLabel = new Label("Departure Airport");
		leftPanel.getChildren().add(departureAirportLabel);
		ComboBox<Airport> departureAirportsComboBox = new ComboBox<>();
		departureAirportsComboBox.setItems(FXCollections.observableArrayList(controller.getSortedAirports()));
		departureAirportsComboBox.setEditable(false);
		leftPanel.getChildren().add(departureAirportsComboBox);
		
		Label arrivalAirportLabel = new Label("Arrival Airport");
		leftPanel.getChildren().add(arrivalAirportLabel);
		ComboBox<Airport> arrivalAirportsComboBox = new ComboBox<>();
		arrivalAirportsComboBox.setItems(FXCollections.observableArrayList(controller.getSortedAirports()));
		arrivalAirportsComboBox.setEditable(false);
		leftPanel.getChildren().add(arrivalAirportsComboBox);
		
		
		Label departureDateLabel = new Label("Departure Date");
		leftPanel.getChildren().add(departureDateLabel);
		DatePicker datePicker = new DatePicker();
		leftPanel.getChildren().add(datePicker);
		
		Button findButton = new Button("Find Flight Schedules");
		findButton.setOnAction(event -> {
			rightPanel.setFlightSchedules(controller.searchFlights(
				departureAirportsComboBox.getValue(),
				arrivalAirportsComboBox.getValue(),
				datePicker.getValue()
			));
		});
		leftPanel.getChildren().add(findButton);
		
		/* Doing some padding & margin in order to obtain a better appearance. */
		leftPanel.setPadding(new Insets(10, 20, 0, 20));
		arrivalAirportLabel.setPadding(new Insets(20, 0, 0, 0));
		departureDateLabel.setPadding(new Insets(20, 0, 0, 0));
		VBox.setMargin(findButton, new Insets(20, 0, 0, 0));
		
		/* Adding and loading a custom preview. */
		departureAirportsComboBox.setValue(controller.getSortedAirports().stream().filter(a -> a.getCode().equals("CAI")).findFirst().get());
		arrivalAirportsComboBox.setValue(controller.getSortedAirports().stream().filter(a -> a.getCode().equals("FCO")).findFirst().get());
		datePicker.setValue(LocalDate.of(2021, 5, 18));
		findButton.fire(); /* <-- Simulates a button click! */
	}

}
