package oroscopo.persistence.test;


import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oroscopo.model.Previsione;
import oroscopo.persistence.BadFileFormatException;
import oroscopo.persistence.OroscopoRepository;

public class TextFileOroscopoRepositoryMock implements OroscopoRepository {

	private Map<String, List<Previsione>> data;

	public TextFileOroscopoRepositoryMock(Reader baseReader) throws IOException, BadFileFormatException {
		data=  new HashMap<>();		
		
		ArrayList<Previsione> prevs = new ArrayList<>();
		prevs.add(new Previsione("S1 - Tu, io, quello e questo", 10));
		prevs.add(new Previsione("S1 - Per questo, questo e quest'altro motivo", 20));
		prevs.add(new Previsione("S1 - Fai gli scherzi", 30));
		data.put("amore", prevs);
		
		prevs = new ArrayList<>();
		prevs.add(new Previsione("S2 - Unduettre", 10));
		prevs.add(new Previsione("S2 - Cioccolato e caffè", 20));
		prevs.add(new Previsione("S2 - Cetriolo e pomodoro", 30));
		data.put("lavoro", prevs);
		
		prevs = new ArrayList<>();
		prevs.add(new Previsione("S3 - Sei un quaraquaqua", 10));
		prevs.add(new Previsione("S3 - Sveglia pelandrone!", 20));
		prevs.add(new Previsione("S3 - Non sei divertente", 30));
		
		data.put("salute", prevs);
		
		
	}

	@Override
	public List<Previsione> getPrevisioni(String sezione) {
		return data.get(sezione.trim().toLowerCase());
	}

	@Override
	public Set<String> getSettori() {
		return data.keySet();
	}

}
