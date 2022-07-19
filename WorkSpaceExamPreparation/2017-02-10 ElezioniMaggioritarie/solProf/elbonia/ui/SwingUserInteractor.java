package elbonia.ui;

import javax.swing.JOptionPane;

public class SwingUserInteractor implements UserInteractor {

	public SwingUserInteractor() {
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);		
	}

	@Override
	public void shutDownApplication() {
		System.exit(1);
	}

}
