package bussy.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class LineaPaP extends Linea {

	public LineaPaP (String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		
		super(id, orariPassaggioAlleFermate);
		if (isCircolare()) ErrorMessage.emit("Si è tentato di creare una linea PaP come fosse circolare!");
	}

	@Override
	public Optional<Percorso> getPercorso(String nomeFermataDa, String nomeFermataA) {
		
		Optional<List<Entry<Integer, Fermata>>> es = super.getFermate(nomeFermataDa, nomeFermataA);
		return es.isEmpty() || es.get().get(0).getKey() >= es.get().get(1).getKey() ? Optional.empty() : Optional.of(new Percorso(
			nomeFermataDa,
			nomeFermataA,
			this,
			es.get().get(1).getKey() - es.get().get(0).getKey()
		));
	}
}
