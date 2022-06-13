package zannopoll.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import zannopoll.model.Intervista;
import zannopoll.model.Sesso;

public class MySondaggioReader implements SondaggioReader {

	@Override
	public List<Intervista> leggiRisposte(Reader r) throws IOException, BadFileFormatException {
		
		if (r == null) throw new IllegalArgumentException("Reader nullo");
		BufferedReader br = new BufferedReader(r);
		ArrayList<Intervista> response = new ArrayList<>();
		String line = null;
		while ( (line = br.readLine()) != null) {
			if (line.isBlank()) continue;
			StringTokenizer stk = new StringTokenizer(line, ",");
			if (stk.countTokens() != 3) throw new BadFileFormatException("Errore nel formato di una riga (debbono essere presenti due e due soltanto caratteri \",\")"); 
			String scelta = stk.nextToken().trim();
			if (scelta.isBlank()) throw new BadFileFormatException("Individuata una riga con scelta assente");
			String eta = stk.nextToken().trim();
			if (!eta.matches("[0-9]+")) throw new BadFileFormatException("Individuata una riga con età indicata non correttamente (deve essere un valore numerico intero)");
			String sex = stk.nextToken().trim().toUpperCase();
			if (sex.length() != 1 || !sex.matches("F|M")) throw new BadFileFormatException("Individuata una riga con sesso indicato non correttamente (deve essere un singolo carattere, \"F\" oppure \"M\")");
			response.add(new Intervista(scelta, Integer.parseInt(eta), sex.equals("F") ? Sesso.FEMMINA : Sesso.MASCHIO));
		}
		return response;
	}

}
