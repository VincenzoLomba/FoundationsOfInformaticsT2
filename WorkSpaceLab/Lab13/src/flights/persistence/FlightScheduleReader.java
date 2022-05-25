package flights.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.FlightSchedule;

public interface FlightScheduleReader {
	
	Collection<FlightSchedule> read(Reader reader, Map<String, Airport> airportMap, Map<String, Aircraft> aircraftMap) throws IOException, BadFileFormatException;
}
