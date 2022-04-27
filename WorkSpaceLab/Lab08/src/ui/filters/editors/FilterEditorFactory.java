package ui.filters.editors;

public class FilterEditorFactory {
	private String[] finderTypes = new String[] { "Tipo", "Genere", "Durata" };

	public String[] getFilterTypes() {
		return finderTypes;
	}

	public FilterEditor getEditor(String filterType) {
		if (filterType.equals("Tipo"))
			return new TypeFilterEditor();
		if (filterType.equals("Durata"))
			return new DurationFilterEditor();
		if (filterType.equals("Genere"))
			return new GenreFilterEditor();
		return null;
	}
}
