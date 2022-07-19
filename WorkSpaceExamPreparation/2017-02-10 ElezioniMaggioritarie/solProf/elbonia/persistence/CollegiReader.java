package elbonia.persistence;
import elbonia.model.Collegio;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface CollegiReader {
	public List<Collegio> caricaElementi(Reader reader) throws IOException, BadFileFormatException;
}
