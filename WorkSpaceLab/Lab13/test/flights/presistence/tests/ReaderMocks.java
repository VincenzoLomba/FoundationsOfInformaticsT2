package flights.presistence.tests;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.City;
import flights.model.FlightSchedule;
import flights.persistence.AircraftsReader;
import flights.persistence.BadFileFormatException;
import flights.persistence.CitiesReader;
import flights.persistence.FlightScheduleReader;

class AircraftsReaderMock implements AircraftsReader {

	@Override
	public Collection<Aircraft> read(Reader reader) throws IOException,
			BadFileFormatException {
		// TODO Auto-generated method stub
		return null;
	}

}

class CitiesReaderMock implements CitiesReader {

	@Override
	public Collection<City> read(Reader reader) throws IOException,
			BadFileFormatException {
		// TODO Auto-generated method stub
		return null;
	}

}

class FlightScheduleReaderMock implements FlightScheduleReader {

	@Override
	public Collection<FlightSchedule> read(Reader reader,
			Map<String, Airport> airportMap, Map<String, Aircraft> aircraftMap)
			throws IOException, BadFileFormatException {
		// TODO Auto-generated method stub
		return null;
	}

}
