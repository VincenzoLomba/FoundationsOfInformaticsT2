package elbonia;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import elbonia.ui.ElboniaPanel;
import elbonia.ui.SwingUserInteractor;
import elbonia.ui.UserInteractor;
import elbonia.ui.controller.Controller;
import elbonia.ui.controller.MyController;

import javax.swing.*;

public class GUITest {

	public static void main(String[] a) {
		UserInteractor userInteractor = new SwingUserInteractor();
		try {
			Controller ctrl = new MyController(userInteractor);
			FileReader reader = new FileReader("Voti.csv");
			ctrl.loadData(reader, new CollegiReaderMock());
			reader.close();
			JFrame f = new JFrame("Elbonia simulator");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(new ElboniaPanel(ctrl));
			f.setSize(350, 605);
			f.setVisible(true);
		} catch (FileNotFoundException e) {
			userInteractor.showMessage("File non trovato.");
			e.printStackTrace();
		} catch (IOException e) {
			userInteractor.showMessage("Errore durante la chiusura del file.");
			e.printStackTrace();
		}
	}

}
