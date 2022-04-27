package media;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import utils.StringUtils;

public class Ebook extends Media {
	
	@Getter @Setter private String[] authors = null;
	@Getter @Setter private String genre = null;
	
	public Ebook (String title, int year, String[] authors, String genre) {
		
		super(title, year);
		this.authors = Arrays.copyOf(authors, authors.length);
		this.genre = genre;
	}

	@Override
	public Type getType() { return Type.EBOOK; }
	
	@Override
	public boolean equals (Object o) {
		
		
		if (o instanceof Ebook e) {
			return StringUtils.areEquivalent(getAuthors(), e.getAuthors()) && getGenre().equals(e.getGenre()) && super.equals(e);
		}
		return false;
	}
	
	@Override
	public String toString () {
		
		return new StringBuilder().append(super.toString()).append(Arrays.stream(getAuthors()).reduce("\nAutori: ", (s1, s2) -> s1 + "," + s2 )).append("\nGenere: ").append(getGenre()).toString();
	}
}
