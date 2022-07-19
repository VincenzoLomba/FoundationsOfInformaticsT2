package zannopoll.ui.controller;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

import zannopoll.model.Sesso;
import zannopoll.model.SondaggioPercentuale;
import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.SondaggioReader;

public class MyController extends Controller {
	
	public MyController(SondaggioReader myReader, Reader reader, DialogManager dialogManager) 
			throws IOException, BadFileFormatException {
		super(myReader, reader, dialogManager);
	}

	@Override
	public Optional<SondaggioPercentuale> getSondaggioPercentuale(int minAge, int maxAge, Optional<Sesso> maybeSex) {
		Optional<SondaggioPercentuale> sondaggioFiltrato = getSondaggioPercentuale().getSondaggioFiltrato(minAge, maxAge, maybeSex);
		if (!sondaggioFiltrato.isPresent()) {
			alert("Errore", "Filtro", "I parametri selezionati danno luogo ad un insieme vuoto di interviste");
		}
		return sondaggioFiltrato;
	}


}
