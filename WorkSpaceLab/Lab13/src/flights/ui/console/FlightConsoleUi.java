package flights.ui.console;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

import flights.controller.Controller;
import flights.model.Airport;
import flights.model.FlightSchedule;
import fondt2.ioutils.StdInput;

public class FlightConsoleUi {
	private Controller controller;
	private StdInput input;

	public FlightConsoleUi(Controller controller) {
		this.controller = controller;
		this.input = new StdInput();
	}

	public void run() {
		String in;
		do {
			searchFlight();
			in = input.readString("'c' per continuare, 'q' per uscire.");
		} while (!in.equalsIgnoreCase("q"));
	}

	private void searchFlight() {
		List<Airport> airports = controller.getSortedAirports();
		String[] airportNames = new String[airports.size()];
		for (int i = 0; i < airportNames.length; i++) {
			Airport airport = airports.get(i);
			airportNames[i] = airport.getCode() + ": "
					+ airport.getCity().getName() + " - " + airport.getName();
		}
		Menu departureAirportMenu = new Menu("Aeroporto di partenza",
				airportNames);
		int departureAirportOption = departureAirportMenu.showAndGetOption();
		if (departureAirportOption == 0)
			return;

		Menu arrivalAirportMenu = new Menu("Aeroporto di arrivo", airportNames);
		int arrivalAirportOption = arrivalAirportMenu.showAndGetOption();
		if (arrivalAirportOption == 0)
			return;

		LocalDate date = readDate();
		if (date == null)
			return;

		Airport departureAirport = airports.get(departureAirportOption - 1);
		Airport arrivalAirport = airports.get(arrivalAirportOption - 1);

		List<FlightSchedule> schedules = controller.searchFlights(
				departureAirport, arrivalAirport, date);
		for (FlightSchedule schedule : schedules) {
			showSchedule(schedule);
		}
	}

	private void showSchedule(FlightSchedule schedule) {
		System.out.println("Partenza: " + schedule.getDepartureLocalTime()
				+ " - Arrivo: " + schedule.getArrivalLocalTime()
				+ " - Durata: " + schedule.getFlightDuration() + "min");
	}

	private LocalDate readDate() {
		boolean retry = false;
		do {
			String source = input.readString("Data: ");
			DateTimeFormatter dateFormatter = DateTimeFormatter
					.ofLocalizedDate(FormatStyle.SHORT)
					.withLocale(Locale.ITALY);
			try {
				return LocalDate.parse(source, dateFormatter);
			} catch (DateTimeParseException e) {
				System.out
						.println("Formato data non valido - reinserire ('r') o annullare ('a')?");
				retry = input.readString().equalsIgnoreCase("r");
			}
		} while (retry);
		return null;
	}
}
