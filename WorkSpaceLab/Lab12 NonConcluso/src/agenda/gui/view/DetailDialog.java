package agenda.gui.view;

import agenda.controller.DetailController;

public interface DetailDialog {
	void setController(DetailController controller);

	DialogResult showDialog();
}
