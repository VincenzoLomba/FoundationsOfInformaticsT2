package ui.editors;

import media.Type;

public class MediaEditorFactory {
	private Type[] mediaTypes = Type.values();

	public Type[] getMediaTypes() {
		return mediaTypes;
	}

	public MediaEditor getEditor(Type tipo) {
		switch (tipo) {
		case FILM:
			return new FilmEditor();
		case SONG:
			return new SongEditor();
		case PHOTO:
			return new PhotoEditor();
		case EBOOK:
			return new EbookEditor();
		}
		return null;
	}

}
