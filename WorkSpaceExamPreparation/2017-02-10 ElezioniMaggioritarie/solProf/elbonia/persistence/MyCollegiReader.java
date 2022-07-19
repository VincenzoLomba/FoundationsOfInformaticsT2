package elbonia.persistence;

import elbonia.model.Collegio;
import elbonia.model.Partito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class MyCollegiReader implements CollegiReader {

	public List<Collegio> caricaElementi(Reader reader) throws IOException, BadFileFormatException {
		BufferedReader innerReader = new BufferedReader(reader);

		String[] nomiPartiti = readPartiti(innerReader);
		List<Collegio> listaCollegi = readListaCollegi(innerReader, nomiPartiti);
		
		return listaCollegi;
	}

	private List<Collegio> readListaCollegi(BufferedReader innerReader, String[] nomiPartiti)
			throws IOException, BadFileFormatException {
		List<Collegio> listaCollegi = new ArrayList<Collegio>();
		String line;
		while ((line = innerReader.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(line, ";");
			int numeroToken = tokenizer.countTokens();
			if (numeroToken != nomiPartiti.length + 1)
				throw new BadFileFormatException("Formato file errato - numero di rilevazioni diverso dal numero di partiti");

			String nomeCollegio = "collegio" + tokenizer.nextToken();
			SortedSet<Partito> partiti = new TreeSet<Partito>();
			for (int i = 0; i < nomiPartiti.length; i++) {
				int voti;
				try {
					voti = Integer.parseInt(tokenizer.nextToken());
				} catch (NumberFormatException e) {
					throw new BadFileFormatException("Formato file errato - il numero di voti non è un valore intero",
							e);
				}
				partiti.add(new Partito(nomiPartiti[i], voti));
			}
			listaCollegi.add(new Collegio(nomeCollegio, partiti));
		}
		return listaCollegi;
	}

	private String[] readPartiti(BufferedReader innerReader) throws IOException, BadFileFormatException {
		String line = innerReader.readLine();
		if (line == null)
			throw new BadFileFormatException("Formato file errato - prima riga mancante");

		StringTokenizer tokenizer = new StringTokenizer(line, ";");
		int partitiCount = tokenizer.countTokens() - 1;
		String[] nomiPartiti = new String[partitiCount];
		tokenizer.nextToken(); // saltiamo l'etichetta "collegio"
		for (int i = 0; i < partitiCount; i++) {
			nomiPartiti[i] = tokenizer.nextToken();
		}
		return nomiPartiti;
	}
}
