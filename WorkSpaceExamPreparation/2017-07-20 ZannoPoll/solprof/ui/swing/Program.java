package zannopoll.ui.swing;

import java.io.FileReader;
import java.io.Reader;

import zannopoll.persistence.MySondaggioReader;
import zannopoll.persistence.SondaggioReader;
import zannopoll.ui.controller.Controller;
import zannopoll.ui.controller.MyController;

public class Program {
	public static void main(String[] args) {
		Controller controller = null;
		try(Reader reader = new FileReader("RisultatoSondaggio.txt")) {
			SondaggioReader votiReader = new MySondaggioReader();			
			controller = new MyController(votiReader, reader, new SwingDialogManager());

			JMainFrame mainFrame = new JMainFrame(controller);
			mainFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}