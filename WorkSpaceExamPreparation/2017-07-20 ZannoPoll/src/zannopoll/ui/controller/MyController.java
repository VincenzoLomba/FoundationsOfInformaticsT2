package zannopoll.ui.controller;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

import zannopoll.model.Sesso;
import zannopoll.model.SondaggioPercentuale;
import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.SondaggioReader;

public class MyController extends Controller {

	public MyController(SondaggioReader myReader, Reader reader, DialogManager dialogManager) throws IOException, BadFileFormatException {
		super(myReader, reader, dialogManager);
	}

	@Override
	public Optional<SondaggioPercentuale> getSondaggioPercentuale(int minAge, int maxAge, Optional<Sesso> sex) {
		
		Optional<SondaggioPercentuale> r = getSondaggioPercentuale().getSondaggioFiltrato(minAge, maxAge, sex);
		if (r.isEmpty()) alert(
			"Sondaggio Percentuale Assente",
			"Nessuna delle interviste soddisfa tutte le condizioni di filtro che sono state indicate",
			"Età minima: " + minAge + ", età massima: " + maxAge + ", sesso: " + (sex.isEmpty() ? "qualsiasi" : sex.get().toString().toLowerCase())
		);
		return r;
	}

}
