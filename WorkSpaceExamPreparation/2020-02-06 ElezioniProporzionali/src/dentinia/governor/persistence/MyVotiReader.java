package dentinia.governor.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.Partito;

public class MyVotiReader implements VotiReader {

	private Elezioni elezioni;
	
	public MyVotiReader (Reader receivedReader) throws IllegalArgumentException, IOException, BadFileFormatException {
		
		if (receivedReader == null) throw new IllegalArgumentException(
			"Nell'istanziare un oggetto della classe " + MyVotiReader.class.getSimpleName() + " si e' fornito al suo costruttore un Reader nullo."
		);
		try (BufferedReader reader = new BufferedReader(receivedReader)) {
			int lineCounter = 0;
			long seggiDaAssegnare = 0;
			elezioni = null;
			String line = null;
			while ( (line = reader.readLine()) != null) {
				if (line.isBlank()) continue;
				++lineCounter;
				if (lineCounter == 1) seggiDaAssegnare = caricaSeggiDaAssegnare(line);
				else if (lineCounter == 2) elezioni = caricaElementi(seggiDaAssegnare, line);
				else throw new BadFileFormatException("Il file contiene piu' di due righe non vuote.");
			}
			if (lineCounter == 0) throw new BadFileFormatException("Il file non contiene alcuna riga (non vuota).");
			if (lineCounter == 1) throw new BadFileFormatException("Il file non contiene la riga indicante il numero di voti ottenuti da ogni partito.");
		}
	}
	
	private long caricaSeggiDaAssegnare (String line) throws BadFileFormatException {
		
		String[] items = line.trim().split("SEGGI\\s+");
		if (items.length == 0 || !items[0].equals("") || items.length != 2 || !items[1].matches("[0-9]+"))
			throw new BadFileFormatException(
				"La prima riga (non vuota) del file deve iniziare con la dicitura \"SEGGI\" subito seguita da indicazione numerica intera del numero di seggi."
			);
		return Integer.parseInt(items[1]);
	}
	
	private Elezioni caricaElementi (long seggiDaAssegnare, String line) throws BadFileFormatException {
		
		try {
			Elezioni response = new Elezioni(seggiDaAssegnare);
			for (String element : line.trim().split("\\s*\\,\\s*")) {
				int index = element.lastIndexOf(" ");
				if (index == -1) throw new BadFileFormatException();
				String name = element.substring(0, index).trim();
				if (name.isBlank()) throw new BadFileFormatException();
				String votes = element.substring(index).trim();
				if (!votes.matches("([0-9]|\\.)+")) throw new BadFileFormatException();
				long lvotes = 0;
				try { lvotes = NumberFormat.getInstance(Locale.ITALY).parse(votes).longValue(); } catch (ParseException e) {}
				response.setVoti(new Partito(name), lvotes);
			}
			return response;
		} catch (BadFileFormatException e) {
			throw new BadFileFormatException("La riga indicante il numero di voti ottenuti da ogni partito e' formatta in maniera errata.");
		}
	}
	
	@Override
	public Elezioni getElezioni() { return elezioni == null ? new Elezioni(0) : elezioni; }
}
