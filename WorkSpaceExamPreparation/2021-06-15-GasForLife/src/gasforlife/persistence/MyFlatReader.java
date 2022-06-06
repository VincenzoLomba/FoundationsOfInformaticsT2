package gasforlife.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import gasforlife.model.Flat;

public class MyFlatReader implements FlatReader {
	
	private Map<String, Flat> flats;
	
	public MyFlatReader(Reader reader) throws IOException, BadFileFormatException {
		
		flats = new HashMap<String, Flat>(); 
		loadAllItems(reader);
	}
 
	@Override
	public Map<String, Flat> getItems() { return flats; }

	private void loadAllItems(Reader reader) throws IOException, BadFileFormatException {
		
		BufferedReader buffreader = new BufferedReader(reader);
		String line;
		while((line=buffreader.readLine()) != null) {
			if(line.trim().length()==0) continue;
			String[] items = line.trim().split("\\s+");
			if (items.length!=3)
				throw new BadFileFormatException("Wrong number of items in line: " + line);
			double maxConsumption;
			String id = items[0].trim();
			String owner = items[2].trim();
			try {
				maxConsumption = Double.parseDouble(items[1]);
			} catch(NumberFormatException e) {
				throw new BadFileFormatException("Wrong elements of items in line: " + line);
			}
			flats.put(id, new Flat(id, maxConsumption, owner));
		}
	}
}
