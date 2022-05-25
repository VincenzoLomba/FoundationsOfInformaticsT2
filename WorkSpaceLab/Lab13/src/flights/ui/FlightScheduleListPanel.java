package flights.ui;

import java.util.Collection;

import flights.model.FlightSchedule;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class FlightScheduleListPanel extends ScrollPane {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private VBox containerPanel;

	public FlightScheduleListPanel() {

		containerPanel = new VBox(2);
		{
			containerPanel.setSpacing(10);
		}

		setContent(containerPanel);
		setPadding(new Insets(5, 5, 5, 5));
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setVbarPolicy(ScrollBarPolicy.ALWAYS);
		setFitToWidth(true);
		setFitToHeight(true);
	}

	public void setFlightSchedules(Collection<FlightSchedule> flightSchedules) {
		containerPanel.getChildren().clear();
		for (FlightSchedule flightSchedule : flightSchedules) {
			FlightSchedulePanel panel = new FlightSchedulePanel(flightSchedule);
			containerPanel.getChildren().add(panel);
		}
		containerPanel.requestLayout();
	}
}
