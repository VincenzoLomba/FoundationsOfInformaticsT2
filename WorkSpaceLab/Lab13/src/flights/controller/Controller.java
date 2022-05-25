package flights.controller;

import java.time.LocalDate;
import java.util.List;

import flights.model.Airport;
import flights.model.FlightSchedule;

public interface Controller {
	
	List<Airport> getSortedAirports();

	List<FlightSchedule> searchFlights(Airport departureAirport, Airport arrivalAirport, LocalDate date);
}
