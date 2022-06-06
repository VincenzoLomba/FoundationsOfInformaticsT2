package gasforlife.persistence;

import java.util.Map;

import gasforlife.model.Flat;

public interface FlatReader {
	
	Map<String, Flat> getItems();
}
