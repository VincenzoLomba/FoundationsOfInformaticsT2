package media.filters;

import lombok.Setter;
import media.Media;

@Setter
public class GenreFilter implements Filter {

	private String genre = null;
	
	public GenreFilter (String genre) { this.genre = genre; }
	
	@Override
	public boolean filter(Media m) {
		
		if (m instanceof HasGenre hg)
			return this.genre.equals(" ") || hg.getGenre().equals(this.genre);
		return false;
	}

}
