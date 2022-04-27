package media.filters.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import media.Ebook;
import media.Film;
import media.Media;
import media.Photo;
import media.Song;
import media.Type;
import media.collection.MediaCollection;
import media.filters.Filter;
import media.filters.TypeFilter;

public class TypeFilterTest {
private MediaCollection collection;
	
	
	@BeforeEach
	private void setUP()
	{  collection = new MediaCollection(10);
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		collection.add(song);
		song = new Song("Hanno Ucciso L'Uomo Ragno", 1993, "883", 4, "Pop");
		collection.add(song);
		Photo foto = new Photo("I tulipani", 2011, new String[] { "Ambra Molesini" });
		collection.add(foto);
		foto=new Photo("I Girasoli", 2020,new String[] { "Ambra Molesini" } );
		collection.add(foto);
		Ebook eb = new Ebook("Il Signore degli Anelli", 1955, new String[] { "J.R.R. Tolkien" }, "Fantasy");
		collection.add(eb);
		eb = new Ebook("Harry Potter e la Pietra Filosofale", 1997, new String[] { "J. K. Rowling" }, "Fantasy");
		collection.add(eb);
		Film film= new Film("Guerre Stellari IV: Una Nuova Speranza", 1977, "George Lucas", 127,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher" }, "Fantascienza");
		collection.add(film);
		film= new Film("Guerre Stellari V: L'Impero Colpisce Ancora", 1980, "George Lucas", 124,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher" }, "Fantascienza");
		collection.add(film);
	}

	
	
	@Test
	public void testFilterFilm() {
		Filter f = new TypeFilter(Type.FILM);
		MediaCollection result = new MediaCollection(10);
		for (int i = 0; i < collection.size(); i++) {
			Media media = collection.get(i);
			if (f.filter(media)) {
				result.add(media);
			}
		}
	  assertEquals(2, result.size());			
	}	

	@Test
	public void testFilterSong() {
		Filter f = new TypeFilter(Type.SONG);
		MediaCollection result = new MediaCollection(125);
		for (int i = 0; i < collection.size(); i++) {
			Media media = collection.get(i);
			if (f.filter(media)) {
				result.add(media);
			}
		}
	  assertEquals(2, result.size());			
	}
	
	
	@Test
	public void testFilterPhoto() {
		Filter f = new TypeFilter(Type.PHOTO);
		MediaCollection result = new MediaCollection(125);
		for (int i = 0; i < collection.size(); i++) {
			Media media = collection.get(i);
			if (f.filter(media)) {
				result.add(media);
			}
		}
	  assertEquals(2, result.size());			
	}
	
	@Test
	public void testFilterEBook() {
		Filter f = new TypeFilter(Type.EBOOK);
		MediaCollection result = new MediaCollection(125);
		for (int i = 0; i < collection.size(); i++) {
			Media media = collection.get(i);
			if (f.filter(media)) {
				result.add(media);
			}
		}
	  assertEquals(2, result.size());			
	}
	

}
