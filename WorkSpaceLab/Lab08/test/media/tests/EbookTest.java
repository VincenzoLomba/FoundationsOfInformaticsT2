package media.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import media.Ebook;
import media.Type;

public class EbookTest {
	Ebook ebook = null;

	@BeforeEach
	public void setUp() throws Exception {
		ebook = new Ebook("Il Signore degli Anelli", 1955, new String[] { "J.R.R. Tolkien" }, "Fantasy");
	}

	@Test
	public void testGetTipo() {
		assertEquals(Type.EBOOK, ebook.getType());
	}

	@Test
	public void testEqualsObject() {
		Ebook eb1 = new Ebook("Il Signore degli Anelli", 1955, new String[] { "J.R.R. Tolkien" }, "Fantasy");
		assertEquals(ebook, eb1);
		eb1 = new Ebook("Il Signore degli Anelli", 1955, new String[] { "Ambra Molesini" }, "Fantasy");
		assertFalse(ebook.equals(eb1));
	}

	@Test
	public void testGetSetAutori() {
		ebook.setAuthors(new String[] { "Ambra Molesini", "Gabriele Zannoni", "Enrico Denti" });
		assertEquals("Ambra Molesini", ebook.getAuthors()[0]);
		assertEquals("Gabriele Zannoni", ebook.getAuthors()[1]);
		assertEquals("Enrico Denti", ebook.getAuthors()[2]);
	}

	@Test
	public void testGetGenere() {
		ebook.setGenre("Narrativa");
		assertEquals("Narrativa", ebook.getGenre());
	}

	@Test
	public void testGetSetTitolo() {
		ebook.setTitle("Poster");
		assertEquals("Poster", ebook.getTitle());
	}

	@Test
	public void testGetSetAnno() {
		ebook.setYear(1975);
		assertEquals(1975, ebook.getYear());
	}

}
