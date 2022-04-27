package media.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import media.Photo;
import media.Type;

public class PhotoTest {
	Photo foto = null;
	@BeforeEach
	public void setUp() throws Exception {
		foto = new Photo("I tulipani", 2011, new String[] { "Ambra Molesini" });
	}

	@Test
	public void testGetTipo() {
		assertEquals(Type.PHOTO, foto.getType());
	}

	@Test
	public void testEqualsObject() {
		Photo f2 = new Photo("I tulipani", 2011, new String[] { "Ambra Molesini" });
		assertTrue(foto.equals(f2));
		f2 = new Photo("I tulipani", 2011, new String[] { "Gabriele Zannoni" });
		assertFalse(foto.equals(f2));
	}

	@Test
	public void testGetSetAutore() {
		foto.setAuthors(new String[] { "Gabriele Zannoni" });
		assertEquals("Gabriele Zannoni", foto.getAuthors()[0]);

	}

	@Test
	public void testGetSetTitolo() {
		foto.setTitle("Poster");
		assertEquals("Poster", foto.getTitle());
	}

	@Test
	public void testGetSetAnno() {
		foto.setYear(1975);
		assertEquals(1975, foto.getYear());
	}

}
