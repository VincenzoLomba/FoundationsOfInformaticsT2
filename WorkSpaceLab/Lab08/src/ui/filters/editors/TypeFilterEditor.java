package ui.filters.editors;

import java.util.Arrays;

import media.Type;
import media.filters.Filter;
import media.filters.TypeFilter;
import ui.editors.MediaEditorFactory;
import utils.Menu;

public class TypeFilterEditor implements FilterEditor {

	@Override
	public Filter create() {
		MediaEditorFactory myFactory = new MediaEditorFactory();
		Type[] mediaTypes = myFactory.getMediaTypes();
		String[] mediaTypesStr = Arrays.stream(mediaTypes).map(mt -> mt.toString()).toArray(String[]::new);
		Menu menu = new Menu("Seleziona il tipo di Media", mediaTypesStr);
		int option = menu.showAndGetOption();
		if (option > 0) {
			Type mediaToFind = mediaTypes[option - 1];
			return new TypeFilter(mediaToFind);
		}
		return null;
	}

}
