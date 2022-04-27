package ui;

import java.util.Arrays;

import controller.MediaController;
import media.Media;
import media.Type;
import media.collection.MediaCollection;
import media.filters.Filter;
import ui.editors.MediaEditor;
import ui.editors.MediaEditorFactory;
import ui.filters.editors.FilterEditor;
import ui.filters.editors.FilterEditorFactory;
import utils.*;

public class MediaView {

	private MediaController myMediaController = null;

	public MediaView() {
		myMediaController = new MediaController();
	}

	public void addMedia() {
		MediaEditorFactory mediaEditorFactory = new MediaEditorFactory();
		Type[] mediaTypes = mediaEditorFactory.getMediaTypes();
		String[] mediaTypesStr = Arrays.stream(mediaTypes).map(mt -> mt.toString()).toArray(String[]::new);
		Menu menu = new Menu("Aggiungi Media", mediaTypesStr);
		int option = menu.showAndGetOption();
		if (option > 0) {
			MediaEditor e = mediaEditorFactory.getEditor(mediaTypes[option - 1]);
			if (e == null) {
				System.out.println("Si è verificato un errore nella creazione del nuovo media");
				return;
			}

			Media m = e.create();
			if (m == null) {
				System.out.println("Si è verificato un errore nella creazione del nuovo media");
				return;
			}

			boolean ok = myMediaController.add(m);
			if (ok)
				System.out.println("Inserimento nella collezione avvenuto con successo");
			else
				System.out.println("Si è verificato un errore nell'inserimento nella collezione");
		}
	}

	public void removeMedia() {
		MediaCollection media = myMediaController.getAll();
		String[] menuItems = new String[media.size()];
		for (int i = 0; i < media.size(); i++) {
			menuItems[i] = media.get(i).toString();
		}
		Menu menu = new Menu("Rimozione Media", menuItems);
		int option = menu.showAndGetOption();
		if (option > 0) {
			if (myMediaController.remove(media.get(option - 1)) == true)
				System.out.println("Rimozione avvenuta con successo");
			else
				System.out.println("Problemi nella rimozione, operazione annullata");
		}
	}

	public void showAll() {
		MediaCollection medias = myMediaController.getAll();
		print(medias);
	}

	public void find() {
		FilterEditorFactory filterEditorFactory = new FilterEditorFactory();
		String[] filterTypes = filterEditorFactory.getFilterTypes();
		Menu menu = new Menu("Seleziona il tipo di Ricerca", filterTypes);
		int option = menu.showAndGetOption();
		if (option > 0) {
			String filterType = filterTypes[option - 1];
			FilterEditor filterEditor = filterEditorFactory.getEditor(filterType);
			Filter filter = filterEditor.create();
			MediaCollection medias = myMediaController.find(filter);
			print(medias);
		}
	}

	private void print(MediaCollection medias) {
		for (int i = 0; i < medias.size(); i++) {
			System.out.println(medias.get(i));
		}
	}
}
