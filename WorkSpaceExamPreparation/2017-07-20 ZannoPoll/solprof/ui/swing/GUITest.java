package zannopoll.ui.swing;

import java.io.IOException;
import java.io.Reader;

import org.mockito.Mockito;

import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.ReaderMock;
import zannopoll.persistence.SondaggioReader;
import zannopoll.ui.controller.Controller;
import zannopoll.ui.controller.MyController;

public class GUITest {
	public static void main(String[] args) throws IOException, BadFileFormatException {
		SondaggioReader votiReader = new ReaderMock();	
		Reader r = Mockito.mock(Reader.class);
		Controller controller = new MyController(votiReader, r, new SwingDialogManager());

		JMainFrame mainFrame = new JMainFrame(controller);
		mainFrame.setVisible(true);
	}
}
