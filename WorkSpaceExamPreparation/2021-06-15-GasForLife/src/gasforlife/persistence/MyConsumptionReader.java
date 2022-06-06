package gasforlife.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyConsumptionReader implements ConsumptionReader {
	
	private static final double conversionFactor = 10.69;
	private Map<String,List<Double>> gasConsumption;
	
	public MyConsumptionReader (Reader reader) throws IOException, BadFileFormatException {
		
		if (reader == null)
			throw new IllegalArgumentException("Nell'istanziare un oggetto di classe " + MyConsumptionReader.class.getSimpleName() + " e' stato fornito un Reader nullo.");
		gasConsumption = new HashMap<String, List<Double>>();
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = null;
		int counter = 0;
		while ((line = bufferedReader.readLine()) != null) {
			++counter;
			if (line.isBlank()) continue;
			String [] items = line.split("\\s*:\\s*");
			if (items.length != 2)
				throw new BadFileFormatException(
					"Errore di formato riga " + counter + ": ogni riga del file deve presentare codice identificativo seguito da \":\" e poi dalle dodici letture dei consumi (tra loro separate mediante \"|\")."
				);
			String codeId = items[0].trim();
			if (codeId.isBlank())
				throw new BadFileFormatException("Errore di formato riga " + counter + ": non e' stato individuato alcun codice identificativo.");
			items = items[1].split("\\s*\\|\\s*");
			if (items.length != 12)
				throw new BadFileFormatException("Errore di formato riga " + counter + ": non sono state individuabili esattamente 12 letture di consumo sepratate tra loro mediante \"|\".");
			try {
				gasConsumption.put(codeId, Arrays.stream(items).map(sc -> ((double) Integer.parseInt(sc.trim())) / conversionFactor).collect(Collectors.toList()));
			} catch (NumberFormatException e) {
				throw new BadFileFormatException("Errore di formato riga " + counter + ": non e' stato possible convertire tutte le letture di consumo in valori interi.");
			}
		}
		if (gasConsumption.size() == 0) throw new BadFileFormatException("Alla classe " + MyConsumptionReader.class.getSimpleName() + " e' stato fornito Reader vuoto.");
	}

	@Override
	public Map<String, List<Double>> getItems() { return gasConsumption; }

}
