package flights.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import flights.model.Airport;
import flights.model.FlightSchedule;
import flights.persistence.DataManager;

public class MyController implements Controller {

	private DataManager dataManager;
	private List<Airport> sortedAirports = null;
	
	public MyController (DataManager dataManager) {
		
		if (dataManager == null)
			throw new IllegalArgumentException("Nella creazione di un oggetto istanza della classe " + DataManager.class.getSimpleName() + " e' stato passato come parametro un " + DataManager.class.getSimpleName() + " nullo.");
		this.dataManager = dataManager;
	}
	
	@Override
	public List<Airport> getSortedAirports() {
		
		sortedAirports = new ArrayList<>(dataManager.getAirportMap().values());
		Collections.sort(sortedAirports,
			Comparator.comparing(
				(Airport a) -> a.getCity().getName()
			).thenComparing(
				Airport::getName
			)
		);
		return sortedAirports;
	}

	@Override
	public List<FlightSchedule> searchFlights(Airport departureAirport, Airport arrivalAirport, LocalDate date) {
		
		return dataManager.getFlightSchedules().stream().filter(
			fs -> fs.getDepartureAirport().equals(departureAirport) &&
			fs.getArrivalAirport().equals(arrivalAirport) &&
			fs.getDaysOfWeek().contains(date.getDayOfWeek())
		).toList();
	}
}
