package ui.editors;

import media.*;

public class EbookEditor extends MediaEditor {
	public Media create() {
		String titolo = getInput().readString("titolo");
		if (titolo == null)
			return null;
		int anno = getInput().readInteger("anno");
		if (anno == -1)
			return null;
		String[] autori = getInput().readStringArray("autori", "nome e cognome");
		if (autori == null)
			return null;
		String genere = getInput().readString("genere");
		if (genere == null)
			return null;

		return new Ebook(titolo, anno, autori, genere);
	}

}
