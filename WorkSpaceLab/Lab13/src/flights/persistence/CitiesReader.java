package flights.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import flights.model.City;

public interface CitiesReader {
	
	Collection<City> read(Reader reader) throws IOException, BadFileFormatException;
}
