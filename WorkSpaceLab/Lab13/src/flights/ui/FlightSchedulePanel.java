package flights.ui;

import java.time.Duration;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Collectors;

import flights.model.FlightSchedule;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FlightSchedulePanel extends GridPane {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public FlightSchedulePanel(FlightSchedule flightSchedule) {
	
		Label label = new Label("Departure airport:");
		this.add(label, 0, 0);
		label = new Label(flightSchedule.getDepartureAirport().toString());
		this.add(label, 1, 0);

		label = new Label("Arrival airport:");
	
		this.add(label, 0,1);
		label = new Label(flightSchedule.getArrivalAirport().toString());
		this.add(label, 1,1);

		label = new Label("Departure local time:");
		this.add(label, 0,2);
		label = new Label(flightSchedule.getDepartureLocalTime().toString());
		this.add(label, 1, 2);

		label = new Label("Arrival local time:");
		this.add(label, 0,3);
		label = new Label(flightSchedule.getArrivalLocalTime().toString());
		add(label, 1,3);

		String dayLabelText = String.join(", ", flightSchedule.getDaysOfWeek().stream()
				.map(d -> d.getDisplayName(TextStyle.SHORT, Locale.getDefault()))
				.collect(Collectors.toList()));
		label = new Label("Days:");
		this.add(label, 0,4);
		label = new Label(dayLabelText);
		this.add(label, 1,4);

		label = new Label("Flight duration:");
		this.add(label, 0,5);
		label = new Label(formatDuration(flightSchedule.getFlightDuration()));
		this.add(label, 1,5);
	}

	private static String formatDuration(Duration duration) {
		int minutes = (int) (duration.getSeconds() / 60);
		int hours = minutes / 60;
		minutes = minutes % 60;

		return "" + hours + "h " + minutes + "m";
	}
}
