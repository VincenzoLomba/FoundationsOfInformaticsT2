package oroscopo.controller;

import java.io.IOException;

import oroscopo.model.Oroscopo;
import oroscopo.model.OroscopoMensile;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.OroscopoRepository;

public class MyController extends AbstractController {

	private StrategiaSelezione selezione;
	
	public MyController(OroscopoRepository repository, StrategiaSelezione selezione) throws IOException {
		
		super(repository);
		this.selezione = selezione;
	}

	@Override
	public SegnoZodiacale[] getSegni() { return SegnoZodiacale.values(); }

	@Override
	public Oroscopo[] generaOroscopoAnnuale(SegnoZodiacale segno, int fortunaMin) {
		
		Oroscopo[] oroscopi = new OroscopoMensile[12];
		for (int j = 0 ; j < 12 ; ++j) {
			do {
				oroscopi[j] = generaOroscopoCasuale(segno);
			} while (oroscopi[j].getFortuna() <= fortunaMin);
		}
		return oroscopi;
	}

	@Override
	public Oroscopo generaOroscopoCasuale(SegnoZodiacale segno) {
		
		if (!getRepository().getSettori().stream().allMatch(s -> s.toUpperCase().equals(Settore.AMORE.toString()) || s.toUpperCase().equals(Settore.LAVORO.toString()) || s.toUpperCase().equals(Settore.SALUTE.toString())))
			throw new IllegalArgumentException(
				"Non sono stati reperiti nel file di testo relativo alle previsioni informazioni per tutti i settori richiesti, ovvero: " + Settore.values().toString() + "."
			);
		return new OroscopoMensile(
			segno,
			selezione.seleziona(getRepository().getPrevisioni(Settore.AMORE.toString()), segno),
			selezione.seleziona(getRepository().getPrevisioni(Settore.LAVORO.toString()), segno),
			selezione.seleziona(getRepository().getPrevisioni(Settore.SALUTE.toString()), segno)
		);
	}

	private enum Settore { AMORE, LAVORO, SALUTE; }
}
