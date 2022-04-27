package media.collection.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import media.*;
import media.collection.MediaCollection;


public class MediaCollectionTest {
	@Test
	public void testAggiungi() {
		MediaCollection collection = new MediaCollection(10);
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		collection.add(song);
		collection.add(song);
		collection.add(song);
		assertEquals(3, collection.size());
	}

	@Test
	public void testIndexOf() {
		MediaCollection collection = new MediaCollection(10);
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		collection.add(song);
		Photo foto = new Photo("I tulipani", 2011, new String[] { "Ambra Molesini" });
		collection.add(foto);
		Ebook eb = new Ebook("Il Signore degli Anelli", 1955, new String[] { "J.R.R. Tolkien" }, "Fantasy");
		collection.add(eb);
		assertEquals(1, collection.indexOf(foto));
	}

	@Test
	public void testRimuovi() {
		MediaCollection collection = new MediaCollection(10);
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		collection.add(song);
		collection.add(song);
		collection.add(song);
		assertEquals(3, collection.size());
		collection.remove(0);
		assertEquals(2, collection.size());
	}

	@Test
	public void testGet() {
		MediaCollection collection = new MediaCollection(10);
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		collection.add(song);
		Media m = collection.get(0);
		assertNotNull(m);
	}

}
