package ui.filters.editors;

import media.filters.Filter;
import media.filters.GenreFilter;
import utils.StdInput;

public class GenreFilterEditor implements FilterEditor {

	@Override
	public Filter create() {
		StdInput input = new StdInput();
		System.out.println("Inserisci un genere (spazio se li vuoi tutti)");
		String genere = input.readString();
		if (genere == null) {
			System.out.println("Durata inserita non valida");
			return null;
		}
		return new GenreFilter(genere);
	}

}
