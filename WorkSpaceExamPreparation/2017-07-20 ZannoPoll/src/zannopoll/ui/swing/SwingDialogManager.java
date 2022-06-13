package zannopoll.ui.swing;

import javax.swing.JOptionPane;

import zannopoll.ui.controller.DialogManager;

public class SwingDialogManager implements DialogManager {

	@Override
	public void alert(String title, String header, String content) {
		JOptionPane.showMessageDialog(null, header + "\n\n" + content);
	}
	
}
