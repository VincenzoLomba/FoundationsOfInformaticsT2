package media;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Media {
	
	private String title = null;
	private int year = -1;
	
	public Media (String title, int year) {
		
		this.title = title;
		this.year = year;
	}
	
	public abstract Type getType ();
	
	public boolean equals (Object o) {
		
		if (o instanceof Media m)
			return getTitle().equals(m.getTitle()) && getYear() == m.getYear();
		return false;
	}
	
	public String toString () {
		
		return new StringBuilder().append("Titolo: ").append(getTitle()).append("\nAnno: ").append(getYear()).toString();
	}

}
