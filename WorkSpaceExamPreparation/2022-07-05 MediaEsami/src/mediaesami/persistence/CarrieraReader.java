package mediaesami.persistence;

import java.io.IOException;
import java.io.Reader;

import mediaesami.model.Carriera;

public interface CarrieraReader {
	public Carriera leggiCarriera(Reader rdr) throws IOException;
}