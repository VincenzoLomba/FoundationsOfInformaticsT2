package media;

import lombok.Getter;
import lombok.Setter;
import media.filters.HasDuration;
import media.filters.HasGenre;

@Getter
@Setter
public class Song extends Media implements HasDuration, HasGenre {

	private int duration = -1;
	private String genre = null;
	private String singer = null;
	
	public Song (String title, int year, String singer, int duration, String genre) {
		
		super(singer, year);
		this.duration = duration;
		this.genre = genre;
		this.singer = singer;
	}
	
	@Override
	public Type getType() { return Type.SONG; }
	
	@Override
	public boolean equals (Object o) {
		
		if (o instanceof Song s)
			return getDuration() == s.getDuration()
				&& getGenre().equals(s.getGenre())
				&& getSinger().equals(s.getSinger())
				&& super.equals(s);
		return false;
	}
	
	@Override
	public String toString () {
		
		return new StringBuilder()
			.append(super.toString())
			.append("\nDurata: ").append(getDuration())
			.append("\nGenere: ").append(getGenre())
			.append("\nCantante: ").append(getSinger())
			.toString();
	}
}
