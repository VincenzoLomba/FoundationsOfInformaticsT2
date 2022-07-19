package zannopoll.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import zannopoll.model.Intervista;

public interface SondaggioReader {
	public List<Intervista> leggiRisposte(Reader r) throws IOException, BadFileFormatException;
}
