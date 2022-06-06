package gasforlife.persistence.test;

import java.util.HashMap;
import java.util.Map;

import gasforlife.model.Flat;
import gasforlife.persistence.FlatReader;

public class MyFlatReaderMock implements FlatReader
{
	public Map<String, Flat> getItems(){
		Map<String, Flat> flats = new HashMap<>();
		flats.put("1-1A", new Flat("1-1A", 100, "owner1"));
		flats.put("1-1B", new Flat("1-1B", 100, "owner2"));
		flats.put("1-2A", new Flat("1-2A", 80, "owner3"));
		flats.put("1-2B", new Flat("1-2B", 80, "owner4"));
		flats.put("1-3A", new Flat("1-3A", 80, "owner5"));
		flats.put("1-3B", new Flat("1-3B", 80, "owner6"));
		flats.put("1-4A", new Flat("1-4A", 90, "owner7"));
		flats.put("1-4B", new Flat("1-4B", 90, "owner8"));
		return flats;
		
	}
	
}
