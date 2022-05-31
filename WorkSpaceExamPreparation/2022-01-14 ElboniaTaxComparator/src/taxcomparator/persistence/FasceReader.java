package taxcomparator.persistence;

import java.io.IOException;
import java.io.Reader;

import taxcomparator.model.Fasce;

public interface FasceReader {
	
	public Fasce readFasce(String descr, Reader reader) throws BadFileFormatException, IOException;
}
