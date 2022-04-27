package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import controller.MediaController;
import media.*;
import media.filters.DurationFilter;
import media.filters.GenreFilter;

public class MediaControllerTest {

	MediaController media = null;

	@BeforeEach
	public void setUp() throws Exception {

		media = new MediaController();

	}

	@Test
	public void testAggiungiMedia() {
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		assertTrue(media.add(song));

	}

	@Test
	public void testRimuovi() {
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		media.add(song);
		Film film = new Film("Guerre Stellari", 1977, "George Lucas", 127,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher" }, "Fantascienza");
		media.add(film);
		assertTrue(media.remove(song));
		assertEquals(1, media.getAll().size());
		Ebook ebook = new Ebook("Il Signore degli Anelli", 1955, new String[] { "J.R.R. Tolkien" }, "Fantasy");
		assertFalse(media.remove(ebook));
	}

	@Test
	public void testGetAll() {
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		media.add(song);
		media.add(song);	// doppio
		Film film = new Film("Guerre Stellari", 1977, "George Lucas", 127,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher" }, "Fantascienza");
		media.add(film);
		media.add(film);	// doppio
		media.add(film);	// doppio
		Ebook ebook = new Ebook("Il Signore degli Anelli", 1955, new String[] { "J.R.R. Tolkien" }, "Fantasy");
		media.add(ebook);
		assertEquals(3, media.getAll().size());
	}

	@Test
	public void testCerca() {
		// preparo la collezione
		Song song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		media.add(song);
		song = new Song("Questo Piccolo Grande Amore 2", 1973, "Claudio Baglioni", 6, "Melenso");
		media.add(song);
		Film film = new Film("Guerre Stellari", 1977, "George Lucas", 127,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher" }, "Fantascienza");
		media.add(film);
		film = new Film("Guerre Stellari 23", 2040, "George Lucas", 127,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher (RIP)" }, "Fantascienza");
		media.add(film);
		film = new Film("Guerre Stellari 42", 2080, "George Lucas", 127,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher (RIP RIP)" }, "Fantascienza");
		media.add(film);
		Ebook ebook = new Ebook("Il Signore degli Anelli", 1955, new String[] { "J.R.R. Tolkien" }, "Fantasy");
		media.add(ebook);
		Photo foto = new Photo("I tulipani", 2011, new String[] { "Ambra Molesini" });
		media.add(foto);

		// cerco la durata

		DurationFilter dFinder = new DurationFilter(0);
		assertEquals(5, media.find(dFinder).size());
		dFinder.setDuration(6);
		assertEquals(2, media.find(dFinder).size());
		dFinder.setDuration(127);
		assertEquals(5, media.find(dFinder).size());

		// cerco il genere
		GenreFilter gFinder = new GenreFilter(" ");
		assertEquals(6, media.find(gFinder).size());
		gFinder.setGenre("Natura");
		assertEquals(0, media.find(gFinder).size());
		gFinder.setGenre("Fantasy");
		assertEquals(1, media.find(gFinder).size());
	}

}
