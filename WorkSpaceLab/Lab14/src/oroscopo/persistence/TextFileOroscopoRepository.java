package oroscopo.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class TextFileOroscopoRepository implements OroscopoRepository {

	private Map<String, List<Previsione>> data;
	private static final String FINE = "FINE";
	private static final String SEPARATORS = "\t";
	
	public TextFileOroscopoRepository (Reader baseReader) throws IOException, BadFileFormatException {
		
		if (baseReader == null)
			throw new IllegalArgumentException(
				"Errore di persistenza: al costruttore della classe " + TextFileOroscopoRepository.class.getSimpleName() + " e' stato passato un " + Reader.class.getSimpleName() + " nullo."
			);
		data = new HashMap<String, List<Previsione>>();
		BufferedReader bufferedReader = new BufferedReader(baseReader);
		String line = null;
		while ( (line = bufferedReader.readLine()) != null) {
			if (line.isBlank()) continue;
			String settore = line.trim().toUpperCase();
			if (!settore.chars().allMatch(ci -> "QWERTYUIOPLKJHGFDSAZXCVBNM0123456789".contains(((char) ci) + "")))
				throw new BadFileFormatException(
					"Errore di formattazione: il file deve contenere diversi gruppi relativi a diversi settori" +
					" dove la prima riga di ogni gruppo deve essere il nome del settore come stringa senza alcuno spazio o simili (tabulatori, ecc...)."
				);
			if (data.containsKey(settore)) throw new BadFileFormatException( "Il file di testo contiene piu' di una sola volta indicazioni per il settore " + settore + ".");
			data.put(settore, caricaPrevisioni(bufferedReader, settore));
		}
	}
	
	private List<Previsione> caricaPrevisioni (BufferedReader bufferedReader, String settore) throws IOException, BadFileFormatException {
		
		String line = null;
		ArrayList<Previsione> response = new ArrayList<Previsione>();
		while ( (line = bufferedReader.readLine()) != null) {
			if (line.isBlank()) break;
			if (line.trim().toUpperCase().equals(FINE)) return response;
			StringTokenizer stringTokenizer = new StringTokenizer(line, SEPARATORS);
			if (stringTokenizer.countTokens() != 2 && stringTokenizer.countTokens() != 3)
				throw new BadFileFormatException("Errore di formattazione in una delle righe descrittive di una previsione del settore " + settore + ".");
			String descrizione = stringTokenizer.nextToken().trim();
			int fortuna = -1;
			try {
				 fortuna = Integer.parseInt(stringTokenizer.nextToken().trim());
				if (fortuna < 0) throw new NumberFormatException();
			} catch (NumberFormatException e) {
				throw new BadFileFormatException(
					"Errore di formattazione in una delle righe descrittive di una previsione del settore " + settore + ": il valore di fortuna non e' un intero positivo o nullo."
				);
			}
			Previsione previsione = null;
			if (stringTokenizer.hasMoreTokens()) {
				try {
					String s = stringTokenizer.nextToken();
					Set<SegnoZodiacale> szs = new TreeSet<>(Arrays.stream(s.trim().split(",")).map((String sz) -> SegnoZodiacale.valueOf(sz)).toList());
					previsione = new Previsione(descrizione, fortuna, szs);
				} catch (IllegalArgumentException e) {
					throw new BadFileFormatException(
						"Errore di formattazione in una delle righe descrittive di una previsione del settore " + settore + ": sono indicati dei segni zodiacali non accettabili."
					);
				}
			} else previsione = new Previsione(descrizione, fortuna);
			response.add(previsione);
		}
		if (response.size() == 0) throw new BadFileFormatException("Il settore " + settore + " pare non contenga alcuna previsione.");
		return response;
	}
	
	@Override
	public Set<String> getSettori() { return data.keySet(); }
	@Override
	public List<Previsione> getPrevisioni(String settore) throws IllegalArgumentException { return data.get(settore.trim().toUpperCase()); }
}
