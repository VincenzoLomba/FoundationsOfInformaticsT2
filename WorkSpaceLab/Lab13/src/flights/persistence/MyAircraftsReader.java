package flights.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

import flights.model.Aircraft;

public class MyAircraftsReader implements AircraftsReader {

	@Override
	public Collection<Aircraft> read(Reader reader) throws IOException, BadFileFormatException {
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		ArrayList<Aircraft> aircraftList = new ArrayList<Aircraft>();
		Aircraft aircraft;
		while ((aircraft = readAircraft(bufferedReader)) != null) { aircraftList.add(aircraft); }
		return aircraftList;
	}

	private Aircraft readAircraft(BufferedReader bufferedReader) throws IOException, BadFileFormatException {
		
		String line = bufferedReader.readLine();
		if (line == null || line.trim().isEmpty()) return null;

		String[] tokens = line.split(",");

		if (tokens.length != 3)
			throw new BadFileFormatException("Numero di token diverso dalle attese (3)");

		int seats;
		try {
			seats = Integer.parseInt(tokens[2]);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(e);
		}

		return new Aircraft(tokens[0], tokens[1], seats);
	}

}
