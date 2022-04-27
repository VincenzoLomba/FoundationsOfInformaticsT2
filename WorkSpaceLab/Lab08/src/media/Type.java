package media;

public enum Type {
	
	FILM("Film"),
	SONG("Canzone"),
	PHOTO("Foto"),
	EBOOK("Ebook");

	private String type;

	Type(String string) { this.type = string; }

	public String toString() { return type; }
}
