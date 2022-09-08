package agenda;

import agenda.controller.AgendaController;
import agenda.controller.ViewFactory;
import agenda.gui.viewfx.impl.FxViewFactory;
import agenda.model.Agenda;
import agenda.persistence.ContactsPersister;
import agenda.persistence.PersistenceManager;
import agenda.persistence.TextContactsPersister;
import javafx.application.Application;
import javafx.stage.Stage;


public class AgendaApplication extends Application {
	private AgendaController controller;

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void init() {
	}

	@Override
	public void start(Stage stage) throws Exception {
		ContactsPersister contactsPersister = new TextContactsPersister();
		Agenda agenda = new Agenda();
		PersistenceManager manager = new PersistenceManager(agenda,contactsPersister);
		ViewFactory viewFactory = new FxViewFactory(stage);
		controller = new AgendaController(manager, viewFactory);
		controller.showUI();
	}

}
