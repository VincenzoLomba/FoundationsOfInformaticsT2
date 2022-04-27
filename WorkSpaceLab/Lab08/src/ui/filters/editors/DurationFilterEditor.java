package ui.filters.editors;

import media.filters.DurationFilter;
import media.filters.Filter;
import utils.StdInput;

public class DurationFilterEditor implements FilterEditor {

	@Override
	public Filter create() {
		StdInput input = new StdInput();
		System.out.println("Inserisci una durata (0 se le vuoi tutte)");
		int durata = input.readInt(-1);
		if (durata == -1) {
			System.out.println("Durata inserita non valida");
			return null;
		}
		return new DurationFilter(durata);
	}

}
