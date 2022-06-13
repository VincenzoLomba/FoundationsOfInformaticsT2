package zannopoll.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zannopoll.model.Intervista;
import zannopoll.model.Sesso;

public class ReaderMock implements SondaggioReader {
	private final static int campioni = 286;
	
	public static List<Intervista> makeInterviste() {
		String[] partiti = {
				"Partito del cioccolato fondente",
				"Movimento vaniglia democratica",
				"Insieme per il salume nostrano",
				"Pizza è progresso",
				"Vegetarianesimo domani",
				"Unità nel formaggio"
		};
		int ageRange = 90; // +18, quindi 18-108
		Random ageGenerator = new Random();
		Random sexGenerator = new Random();
		Random partyGenerator = new Random();
		List<Intervista> list = new ArrayList<>();
		for (int i=0; i<campioni; i++){
			list.add(new Intervista(
					partiti[partyGenerator.nextInt(partiti.length)],
					(ageGenerator.nextInt(ageRange)+18),
					(sexGenerator.nextBoolean()?Sesso.MASCHIO:Sesso.FEMMINA)));			
		}
		return list;
	}
	
	@Override
	public List<Intervista> leggiRisposte(Reader r) throws IOException, BadFileFormatException {
		return makeInterviste();
	}

}
