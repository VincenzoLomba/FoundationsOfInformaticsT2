package edlift.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edlift.model.Installation;
import edlift.model.Lift;

public class MyInstallationReader implements InstallationReader {
	
	private List<String> tipiAmmissibili = List.of("BASIC","MULTI","HEALTHY");

	@Override
	public List<Installation> readAll(Reader reader) throws BadFileFormatException, IOException {
		
		if (reader == null)
			throw new IllegalArgumentException("Alla classe " + MyInstallationReader.class.getSimpleName() + " e' stato finito un " + Reader.class.getSimpleName());
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line;
		ArrayList<Installation> installations = new ArrayList<Installation>();
		while ((line = bufferedReader.readLine()) != null) {
			
			if (line.isBlank()) continue;
			String [] items = line.split("(A|a)(S|s)(C|c)(E|e)(N|n)(S|s)(O|o)(R|r)(E|e)\\s+");
			if (items[0] != "" || items.length != 2) throw new BadFileFormatException(
				"Per ogni gruppo descrittivo di un ascensore, la prima riga deve iniziare con la parola \"ASCENSORE\" e a seguito di spazio deve presentare solamente il nome dell'ascensore stesso."
			);
			String name = items[1].trim();
			
			do { line = bufferedReader.readLine(); } while (line != null && line.isBlank());
			if (line == null)
				throw new IllegalArgumentException("Ogni gruppo descrittivo di un ascensore deve essere composto di tre righe.");
			items = line.toUpperCase().split("(TIPO |A |PIANI |DA |\\s)+");
			System.out.println(Arrays.asList(items));
			if (items[0] != "" || items.length != 5) throw new BadFileFormatException(
				"Errore di formato nelle indicazioni della installazione per l'ascensore \"" + name + "\". " +
				"Per ogni gruppo descrittivo di un ascensore, la seconda riga deve iniziare con la parola \"TIPO\" e essere nel formato \"TIPO MODO A N PIANI da MIN a MAX\"."
			);
			if (!tipiAmmissibili.contains(items[1])) throw new IllegalArgumentException("Individuato un tipo di ascensore non ammissibile: \"" + items[1] + "\".");
			String type = items[1];
			int minFloor, maxFloor, floors;
			try {
				floors = Integer.parseInt(items[2]);
				minFloor = Integer.parseInt(items[3]);
				maxFloor = Integer.parseInt(items[4]);
				if (minFloor > maxFloor)
					throw new IllegalArgumentException(
						"L' ascensore \"" + name + "\" ha piano piu' alto inferiore a quello piu' basso (e la cosa non puo' essere)(maxFloor=" + maxFloor+ ", minFloor=" + minFloor + ")."
					);
				if ((maxFloor - minFloor + 1) != floors)
					throw new IllegalArgumentException(
						"L' ascensore \"" + name + "\" ha numero totale di piani non coerente con i numerici di piani piu' alto e piu' basso: " + 
						"minFloor=" + minFloor + " e maxFloor=" + maxFloor + " risultano in un totale di " + (maxFloor - minFloor + 1) + " piano/i, mentre invece e' indicato " + floors + "."
					);
			} catch (NumberFormatException e) {
				throw new BadFileFormatException(
					"Per l'ascensore \"" + name + "\" non e' stato possibile ottenere numericamente uno o piu' dei valori: numero totale di piani, piano inziale e finale"
				);
			}
			
			do { line = bufferedReader.readLine(); } while (line != null && line.isBlank());
			if (line == null)
				throw new IllegalArgumentException("Ogni gruppo descrittivo di un ascensore deve essere composto di tre righe.");
			items = line.toUpperCase().split("(VELOCITA\\s+)|(\\s*M/S\\s*)");
			if (items[0] != "" || items.length != 2) throw new BadFileFormatException(
				"Per ogni gruppo descrittivo di un ascensore, la seconda riga deve iniziare con la parola \"VELOCITA\" e a seguito avere indicazione numerica subito seguita da \"m/s\"."
			);
			double vel;
			try {
				vel = Double.parseDouble(items[1]);
			} catch (NumberFormatException e) {
				throw new BadFileFormatException(
					"Per l'ascensore \"" + name + "\" non e' stato possibile ottenere numericamente il valore della velocita' (\"" + items[1] +"\" non accettabile)."
				);
			}
			installations.add(new Installation(name, Lift.of(type, minFloor, maxFloor, vel)));		
		}
		if (installations.size() == 0) throw new BadFileFormatException("Il file in lettura risulta essere vuoto.");
		return installations;
	}

}
