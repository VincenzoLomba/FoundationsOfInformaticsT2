package minesweeper.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class MyConfigReader implements ConfigReader {

	private int mines = -1;
	private int size = -1;
	private static final String MINES = "MINES";
	private static final String SIZE = "SIZE";
	private static final String SEPARATOR = ":";
	
	public MyConfigReader (Reader reader) throws BadFileFormatException {
		
		if (reader == null) throw new IllegalArgumentException(
			"Si e' tentato di istanziare un oggetto di classe \"" + MyConfigReader.class.getSimpleName() + "\" "
			+ "fornendo al costruttore un \"" + Reader.class.getSimpleName()+ "\" nullo."
		);
		
		try {
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			int lineCounter = 0;
			while ((line = bufferedReader.readLine()) != null) {
				
				if (line.isBlank()) continue;
				++lineCounter;
				if (lineCounter > 2) throw new BadFileFormatException("Il file deve contenere esattamente solo due righe (non vuote).");

				String[] items = line.trim().split("\s*:\s*");

				if (items.length != 2 || items[0].isBlank()) throw new BadFileFormatException(
					"Ogni riga deve presentare due valori (uno come stringa, uno come numerico) separati tra loro mediante carattere \"" + SEPARATOR + "\"."
				);
				
				if (!items[1].matches("[0-9]+")) throw new BadFileFormatException(
					"In ogni riga (non vuota), a seguito del carattere \"" + SEPARATOR + "\", deve essere presente un valore numerico intero e positivo."
				);
				
				switch (items[0].toUpperCase()) {
					case MINES: {
						if (mines != -1) throw new BadFileFormatException("E' presente piu' di una riga per la definizione del numero di mine.");
						mines = Integer.parseInt(items[1]);
						break;
					}
					case SIZE: {
						if (size != -1) throw new BadFileFormatException("E' presente piu' di una riga per la definizione della dimensione del campo.");
						size = Integer.parseInt(items[1]);
						break;
					}
					default: {
						throw new BadFileFormatException(
							"Individuata una riga che non e' attinente ne' alla definizione del numero di mine ne' alla definizione della dimensione del campo."
						);
					}
				}
			}
			
			if (mines == -1) throw new BadFileFormatException("Il file non presenta alcuna riga per la definizione del numero di mine.");
			if (size == -1) throw new BadFileFormatException("Il file non presenta alcuna riga per la definizione della dimensione del campo.");
			
		} catch (IOException e) {
			throw new BadFileFormatException("Durante la lettura e' avvenuto un errore di IO:\n" + e.toString());
		}
		
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getMinesNumber() {
		return mines;
	}

}
