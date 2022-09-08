package agenda.persistence;


import java.util.Map;
import java.util.StringTokenizer;

import agenda.model.Detail;

public interface DetailPersister {
	
	Detail load(StringTokenizer source) throws BadFileFormatException;
	void save(Detail d, StringBuilder sb);
	
	public static DetailPersister of(String name) {
		
		Map<String, DetailPersister> persisterMap = Map.of(
			"EMail", new EMailPersister(),
			"Phone", new PhonePersister(),
			"Address", new AddressPersister() );
		return persisterMap.get(name);
	}
}
