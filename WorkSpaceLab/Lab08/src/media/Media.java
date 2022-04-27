package media;

import lombok.Getter;
import lombok.Setter;
import media.filters.HasType;

@Getter
@Setter
public abstract class Media implements HasType{
	
	private String title = null;
	private int year = -1;
	
	public Media (String title, int year) {
		
		this.title = title;
		this.year = year;
	}
	
	public boolean equals (Object o) {
		
		if (o instanceof Media m)
			return getTitle().equals(m.getTitle()) && getYear() == m.getYear();
		return false;
	}
	
	public String toString () {
		
		return new StringBuilder().append("Titolo: ").append(getTitle()).append("\nAnno: ").append(getYear()).toString();
	}

}
