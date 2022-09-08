package agenda.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import agenda.gui.view.DialogResult;
import agenda.gui.view.MainView;
import agenda.gui.view.YesNoQuestionDialog;
import agenda.model.Contact;
import agenda.persistence.BadFileFormatException;
import agenda.persistence.PersistenceManager;

public class AgendaController {
	private PersistenceManager manager;
	private ViewFactory viewFactory;

	public AgendaController(PersistenceManager manager, ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
		this.manager = manager;
	}

	public void showUI() {
		MainView view = viewFactory.createMainView();
		view.setController(this);
		view.showView();
	}

	public boolean loadContacts(String filename) {
		try {
			manager.load(filename);
			return true;
		} catch (FileNotFoundException e) {
			viewFactory.createMessageDialog().showMessage(
					"Errore nel caricamento", "File non trovato: " + filename);
			return false;
		} catch (IOException e) {
			viewFactory.createMessageDialog().showMessage(
					"Errore nel caricamento", "Errore di input/output: " + filename);
			return false;
		} catch (BadFileFormatException e) {
			viewFactory.createMessageDialog().showMessage(
					"Errore nel caricamento", "Errore nel formato del file: " + filename);
			return false;
		} catch (UnsupportedOperationException e) {
			viewFactory.createMessageDialog().showMessage(
					"Errore nel caricamento", "Formato non supportato: " + filename);
			return false;
		}
	}

	public void save() {
		if (manager.getAgenda() != null) {
			try {
				manager.save(manager.getFileName());
			} catch (IOException e) {
				viewFactory.createMessageDialog().showMessage(
						"Errore nel salvataggio", "Errore di input/output: " + manager.getFileName());
			} catch (BadFileFormatException e) {
				viewFactory.createMessageDialog().showMessage(
						"Errore nel caricamento", "Formato del file errato: " + e.getMessage());
			}
		}
	}

	public Set<Contact> getAll() {
		return manager.getAgenda().getContacts();
	}

	public Set<Contact> search(String query) {
		return manager.getAgenda().searchContacts(query);
	}

	public boolean deleteContact(Contact contact) {
		if (contact != null) {
			YesNoQuestionDialog dialog = viewFactory
					.createYesNoQuestionDialog();
			DialogResult result = dialog.showQuestion("Conferma Eliminazione",
					"Procedere con l'eliminazione?");

			if (result == DialogResult.Yes) {
				manager.getAgenda().removeContact(contact);
				return true;
			}
		}
		return false;
	}

	public boolean insertContact() {
		ContactController controller = new ContactController(viewFactory);
		Contact contact = controller.insertContact();
		if (contact != null) {
			manager.getAgenda().addContact(contact);
			return true;
		}
		return false;
	}

	public boolean editContact(Contact contact) {
		if (contact != null) {
			ContactController controller = new ContactController(viewFactory);
			return controller.editContact(contact);
			// e' per riferimento quindi viene automaticamente aggiornato.
		}
		return false;
	}

	public void showDetails(Contact contact) {
		DetailController controller = new DetailController(contact, viewFactory);
		controller.showUI();
	}
}
