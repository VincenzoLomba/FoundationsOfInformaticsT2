package bussy.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import bussy.model.Fermata;
import bussy.model.Linea;
import bussy.model.LineaCircolare;
import bussy.model.LineaPaP;

public class MyTransportLinesReader implements TransportLinesReader {

	private static final String LINEA = "LINEA";
	private static final String TERMINATOR = "TERMINATOR";
	private static final String SEPARATOR = "SEPARATOR";
	
	@Override
	public Map<String, Linea> readTransportLines(BufferedReader reader) throws IOException, BadFileFormatException {
		
		if (reader == null) throw new IllegalArgumentException("Buffered Reader nullo.");
		
		String line = null;
		Map<String, Linea> response = new HashMap<>();
		while ((line = reader.readLine()) != null) {
			if (line.isBlank()) continue;
			String [] items = line.trim().toUpperCase().split("\s*" +  LINEA + "\s+");
			if (items.length != 2 && items[0].equals("") && items[1].matches("([a-zA-Z]|[0-9])+")) throw new BadFileFormatException(
				"La prima riga di ogni gruppo deve iniziare con la parola chiave \"" + LINEA + "\" (case insensitive) e a seguire un separato valore alfanumerico identificativo per la stessa."
			);
			response.put(items[1], getLinea(items[1], reader));
		}
		return response;
	}

	private Linea getLinea (String id, BufferedReader reader) throws BadFileFormatException, IOException {
		
		String line;
		TreeMap<Integer, Fermata> map = new TreeMap<>();
		while (!isTerminator(line = reader.readLine())) {
			String [] items = line.trim().split("\s*" + SEPARATOR + "\s*");
			if (items.length != 3 || items[0].isBlank() || !items[0].matches("[0-9]+")) throw new BadFileFormatException(
				"Ogni riga descrittiva di una fermata deve presentare tre elementi separati tra di loro mediante carattere \"" + SEPARATOR + "\" (primo dei quali deve essere numerico intero positivo)."
			);
			int durata = Integer.parseInt(items[0]);
			if (map.containsKey(durata)) throw new BadFileFormatException(
				"In uno stesso gruppo di fermate (relativo a linea di id \"" + id + "\") sono presenti piu' fermate associate a una stessa durata." 
			);
			map.put(durata, new Fermata(items[1], items[2]));
		}
		if (map.size() <= 1) throw new BadFileFormatException(
			"Per la linea di id \"" + id + "\" non e' indicata alcuna fermata oppure solamente una."
		);
		if (map.containsKey(0)) throw new BadFileFormatException(
				"Per la linea di id \"" + id + "\" non e' indicata alcuna fermata di durata zero."
		);
		return map.get(0).getNome().equals(map.get(map.keySet().stream().mapToInt(i -> i).max().getAsInt()).getNome()) ? new LineaCircolare(id, map) : new LineaPaP(id, map);
	}
	
	private boolean isTerminator (String line) throws BadFileFormatException {
		
		if (line == null) throw new BadFileFormatException("Il file deve terminare con una riga che presenti almeno due volte il simbolo \"" + TERMINATOR + "\" (chiusura di un gruppo).");
		return line.startsWith(TERMINATOR + TERMINATOR);
	}
}
