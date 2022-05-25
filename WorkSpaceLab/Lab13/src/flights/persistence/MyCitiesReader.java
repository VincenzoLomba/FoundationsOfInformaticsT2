package flights.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import flights.model.Airport;
import flights.model.City;

public class MyCitiesReader implements CitiesReader {

	@Override
	public Collection<City> read(Reader reader) throws IOException, BadFileFormatException {
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		ArrayList<City> cityList = new ArrayList<City>();
		City city;
		while ((city = readCity(bufferedReader)) != null) {
			cityList.add(city);
		}
		return cityList;
	}

	private City readCity(BufferedReader reader) throws IOException, BadFileFormatException {
		
		String line = reader.readLine();
		if (line == null || line.trim().isEmpty())
			return null;

		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		if (tokenizer.countTokens() < 3)
			throw new BadFileFormatException(
					"Numero di token diverso dalle attese (>= 3)");

		String code = tokenizer.nextToken();
		int timeZone;
		try {
			String toParse = tokenizer.nextToken().substring(3); // "GMT".length()
																	// == 3
			if (toParse.isEmpty()) {
				timeZone = 0;
			} else {
				if (toParse.startsWith("+")) {
					toParse = toParse.substring(1); // Se positivo, toglie il
													// segno, se negativo lo
													// lascia
				}
				timeZone = Integer.parseInt(toParse);
			}
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(e);
		}
		String name = tokenizer.nextToken();

		City city = new City(code, name, timeZone);

		if (tokenizer.hasMoreTokens()) {
			try {
				while (tokenizer.hasMoreTokens()) {
					String airportCode = tokenizer.nextToken(",/");
					String airportName = tokenizer.nextToken();
					Airport airport = new Airport(airportCode, airportName,
							city);
					city.getAirports().add(airport);
				}
			} catch (NoSuchElementException e) {
				throw new BadFileFormatException(e);
			}
		} else {
			Airport airport = new Airport(city.getCode(), city.getName(), city);
			city.getAirports().add(airport);
		}
		return city;
	}

}
