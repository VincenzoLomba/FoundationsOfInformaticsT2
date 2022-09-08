package agenda.gui.view;

import agenda.controller.AgendaController;

public interface MainView {
	void setController(AgendaController controller);

	void showView();
}
