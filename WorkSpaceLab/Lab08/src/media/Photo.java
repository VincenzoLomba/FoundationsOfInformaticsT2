package media;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import utils.StringUtils;

@Getter
@Setter
public class Photo extends Media {

	private String[] authors = null;
	
	public Photo (String title, int year, String[] authors) {
		
		super(title, year);
		this.authors = Arrays.copyOf(authors, authors.length);
	}
	
	@Override
	public Type getType() { return Type.PHOTO; }
	
	@Override
	public boolean equals (Object o) {
		
		if (o instanceof Photo p)
			return StringUtils.areEquivalent(getAuthors(), p.getAuthors())
				&& super.equals(p);
		return false;
	}
	
	@Override
	public String toString () {
		
		return new StringBuilder()
			.append(super.toString())
			.append("\nAutori: ").append(Arrays.stream(getAuthors()).reduce((s1, s2) -> s1 + "," + s2 ).get())
			.toString();
	}
}
