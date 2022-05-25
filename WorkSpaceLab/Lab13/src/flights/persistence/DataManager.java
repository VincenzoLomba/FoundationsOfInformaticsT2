package flights.persistence;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.City;
import flights.model.FlightSchedule;

public class DataManager {
	
	private HashMap<String, Aircraft> aircraftMap;
	private HashMap<String, Airport> airportMap;
	private AircraftsReader aircraftsReader;
	private CitiesReader citiesReader;
	private FlightScheduleReader flightScheduleReader;
	private Collection<FlightSchedule> flightSchedules;
	
	public DataManager (CitiesReader citiesReader, AircraftsReader aircraftsReader, FlightScheduleReader flightScheduleReader) {
		
		if (citiesReader == null || aircraftsReader == null || flightScheduleReader == null)
			throw new IllegalArgumentException("Ricevuti parametri nulli durante la costruzione di un oggetto istanza di " + DataManager.class.getSimpleName() + ".");
		this.citiesReader = citiesReader;
		this.aircraftsReader = aircraftsReader;
		this.flightScheduleReader = flightScheduleReader;
		aircraftMap = new HashMap<String, Aircraft>();
		airportMap = new HashMap<String, Airport>();
		flightSchedules = new ArrayList<FlightSchedule>();
		
	}
	
	public void readAll () throws IOException, BadFileFormatException {
		
		citiesReader.read(new FileReader("Cities.txt")).stream().map(
			City::getAirports
		).reduce(
			new HashSet<Airport>(), (Set<Airport> s1, Set<Airport> s2) -> { s1.addAll(s2); return new HashSet<>(s1); }
		).forEach(
			a -> airportMap.put(a.getCode(), a)
		);
		
		aircraftsReader.read(new FileReader("Aircrafts.txt")).stream().forEach(
			a -> aircraftMap.put(a.getCode(), a)
		);
		
		flightSchedules.addAll(flightScheduleReader.read(new FileReader("FlightSchedule.txt"), airportMap, aircraftMap));
	}

	public Map<String, Aircraft> getAircraftMap() { return aircraftMap; }
	public Map<String, Airport> getAirportMap() { return airportMap; }
	public Collection<FlightSchedule> getFlightSchedules() { return flightSchedules; }
}
