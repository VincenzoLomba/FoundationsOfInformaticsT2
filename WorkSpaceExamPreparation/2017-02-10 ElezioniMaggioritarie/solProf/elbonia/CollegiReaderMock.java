package elbonia;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import elbonia.model.Collegio;
import elbonia.model.Partito;
import elbonia.persistence.BadFileFormatException;
import elbonia.persistence.CollegiReader;

public class CollegiReaderMock implements CollegiReader {
	private final int MAX_DATA = 180;
	private ArrayList<Collegio> data;

	public CollegiReaderMock() {
	}

	@Override
	public List<Collegio> caricaElementi(Reader reader) throws IOException, BadFileFormatException {
		if (data == null) {
			data = new ArrayList<Collegio>();
			String[] nomiPartiti = new String[] { "No", "Sì", "Forse", "Chissà" };
			for (int i = 0; i < MAX_DATA; ++i) {
				TreeSet<Partito> partiti = new TreeSet<>();
				for (int j = 0; j < nomiPartiti.length; ++j) {
					partiti.add(new Partito(nomiPartiti[j], (int) (Math.random() * 10000)));
				}
				Collegio c = new Collegio("collegio" + (i + 1), partiti);
				data.add(c);
			}
		}
		return data;
	}
}
