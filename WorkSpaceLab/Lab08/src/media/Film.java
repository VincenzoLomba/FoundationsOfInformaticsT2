package media;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import media.filters.HasDuration;
import media.filters.HasGenre;
import utils.StringUtils;

@Getter
@Setter
public class Film extends Media implements HasDuration, HasGenre {
	
	private String[] actors = null;
	private String director = null;
	private int duration = -1;
	private String genre = null;
	
	public Film (String title, int year, String director, int duration, String[] actors, String genre) {
		
		super(title, year);
		this.genre = genre;
		this.duration = duration;
		this.actors = Arrays.copyOf(actors, actors.length);
		this.director = director;
	}

	@Override
	public Type getType() { return Type.FILM; }
	
	@Override
	public boolean equals (Object o) {
		
		if (o instanceof Film f)
			return StringUtils.areEquivalent(getActors(), f.getActors())
				&& getDirector().equals(f.getDirector())
				&& getDuration() == f.getDuration()
				&& getGenre().equals(f.getGenre())
				&& super.equals(f);
		return false;
	}
	
	@Override
	public String toString () {
		
		return new StringBuilder()
			.append(super.toString())
			.append("\nAttori: ").append(Arrays.stream(getActors()).reduce((s1, s2) -> s1 + "," + s2 ).get())
			.append("\nDirettore: ").append(getDirector())
			.append("\nDurata: ").append(getDuration())
			.append("\nGenere: ").append(getGenre())
			.toString();
	}
}
