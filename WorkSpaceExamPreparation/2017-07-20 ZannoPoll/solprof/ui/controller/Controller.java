package zannopoll.ui.controller;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

import zannopoll.model.Intervista;
import zannopoll.model.Sesso;
import zannopoll.model.SondaggioPercentuale;
import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.SondaggioReader;

public abstract class Controller {
	private List<Intervista> list;
	private SondaggioPercentuale sondaggioPercentuale;
	private DialogManager dialogManager;

	public Controller(SondaggioReader myReader, Reader reader, DialogManager dialogManager) throws IOException, BadFileFormatException {
		list = myReader.leggiRisposte(reader);
		sondaggioPercentuale = new SondaggioPercentuale(list);
		this.dialogManager = dialogManager;
	}

	public SondaggioPercentuale getSondaggioPercentuale() {
		return sondaggioPercentuale;
	}

	public abstract Optional<SondaggioPercentuale> getSondaggioPercentuale(int minAge, int maxAge, Optional<Sesso> sex);
	
	public void alert(String title, String header, String content) {
		dialogManager.alert(title, header, content);
	}

}
