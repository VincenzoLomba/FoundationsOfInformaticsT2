package ui;

import media.Media;
import media.Type;
import ui.editors.MediaEditor;
import ui.editors.MediaEditorFactory;

public class MediaFactory {
	private String[] mediaTypes = new String[] { "Film", "Canzone", "Foto", "Ebook" };
	private MediaEditorFactory editFactory = null;

	public MediaFactory() {
		editFactory = new MediaEditorFactory();
	}

	public String[] getMediaTypeNames() {
		return mediaTypes;
	}

	public Media create(Type tipo) {
		MediaEditor e = editFactory.getEditor(tipo);
		if (e == null)
			return null;
		return e.create();
	}
}
