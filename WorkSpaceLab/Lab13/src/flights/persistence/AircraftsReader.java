package flights.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import flights.model.Aircraft;

public interface AircraftsReader {
	
	Collection<Aircraft> read(Reader reader) throws IOException, BadFileFormatException;
}
