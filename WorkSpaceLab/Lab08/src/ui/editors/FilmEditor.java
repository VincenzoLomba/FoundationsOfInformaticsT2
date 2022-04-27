package ui.editors;

import media.*;

public class FilmEditor extends MediaEditor {

	public Media create() {
		String titolo = getInput().readString("titolo");
		if (titolo == null)
			return null;
		int anno = getInput().readInteger("anno");
		if (anno == -1)
			return null;
		String regista = getInput().readString("regista");
		if (regista == null)
			return null;
		int durata = getInput().readInteger("durata");
		if (durata == -1)
			return null;
		String genere = getInput().readString("genere");
		if (genere == null)
			return null;
		String[] attori = getInput().readStringArray("attori", "nome e cognome");
		if (attori == null)
			return null;

		return new Film(titolo, anno, regista, durata, attori, genere);
	}

}
