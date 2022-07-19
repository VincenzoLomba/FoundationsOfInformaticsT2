package zannopoll.ui.javafx;

import java.io.IOException;
import java.io.Reader;

import org.mockito.Mockito;

import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.ReaderMock;
import zannopoll.persistence.SondaggioReader;
import zannopoll.ui.controller.Controller;
import zannopoll.ui.controller.MyController;

public class PollApplicationMock extends PollApplication {

	@Override
	protected Controller createController() {
		SondaggioReader sondaggiReader = new ReaderMock();		
		Reader r = Mockito.mock(Reader.class);
		try {
			return new MyController(sondaggiReader, r, this);
		} catch (IOException | BadFileFormatException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
