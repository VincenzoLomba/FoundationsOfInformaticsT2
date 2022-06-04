package edlift.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import edlift.model.Installation;

public interface InstallationReader {
	
	public List<Installation> readAll(Reader reader) throws BadFileFormatException, IOException;
}
