package rfd.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import rfd.model.MyPointOfInterest;
import rfd.model.PointOfInterest;
import rfd.model.RailwayLine;

public class MyRailwayLineReader implements RailwayLineReader {

	@Override
	public RailwayLine getRailwayLine(Reader rdr) throws IOException {
		
		if (rdr == null)
			throw new IllegalArgumentException(
				"Alla classe \"" + MyRailwayLineReader.class.getSimpleName() + "\" e' stato fornito un " + Reader.class.getSimpleName() + " vuoto."
			);
		BufferedReader bufferedReader = new BufferedReader(rdr);
		Map<String,PointOfInterest> map = new HashMap<>();
		SortedSet<String> hbus = new TreeSet<>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.isBlank()) continue;
			StringTokenizer stringTokenizer = new StringTokenizer(line, "\t");
			if (stringTokenizer.countTokens() != 2)
				throw new IllegalArgumentException(
					"Incontrata una riga che a seguito di separazione sulla base del carattere \"\\t\" (tabulatore) non presenta esattamente due elementi."
				);
			String km = stringTokenizer.nextToken().trim();
			String stationName = stringTokenizer.nextToken().trim();
			if ("0123456789".contains(stationName.charAt(0)+""))
				throw new IllegalArgumentException(
					"Il nome di una stazione non puo' iniziare con cifra numerica"
				);
			int index = stationName.indexOf('+');
			if (index != -1 && (index != stationName.length() - 1 || stationName.length() == 1 || stationName.substring(index - 1, index).isBlank()))
				throw new IllegalArgumentException(
					"Se nel nome di una stazione e' presente un simbolo '+' (indicativo di punto di interscambio), questo deve venire alla fine del nome e senza alcuno spazio a precederlo."
				);
			if (index != -1) {
				stationName = stationName.substring(0, stationName.length() - 1);
				hbus.add(stationName);
			}
			map.put(stationName, new MyPointOfInterest(stationName, km));
		}
		return new RailwayLine(map, hbus);
	}

}
