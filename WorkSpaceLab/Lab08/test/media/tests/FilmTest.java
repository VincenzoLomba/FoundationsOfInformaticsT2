package media.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import media.Film;
import media.Type;

public class FilmTest {
	Film film = null;

	@BeforeEach
	public void setUp() throws Exception {
		film = new Film("Guerre Stellari", 1977, "George Lucas", 127,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher" }, "Fantascienza");
	}

	@Test
	public void testGetTipo() {
		assertEquals(Type.FILM, film.getType());
	}

	@Test
	public void testEqualsObject() {
		Film film2 = new Film("Guerre Stellari", 1977, "George Lucas", 127,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher" }, "Fantascienza");
		assertEquals(film, film2);
		film2 = new Film("L'impero colpisce ancora", 1977, "George Lucas", 135,
				new String[] { "Mark Hamill", "Harrison Ford", "Carrie Fisher" }, "Fantascienza");
		assertFalse(film.equals(film2));
	}

	@Test
	public void testGetSetRegista() {
		film.setDirector("Ron Howard");
		assertEquals("Ron Howard", film.getDirector());
	}

	@Test
	public void testGetSetAttori() {
		film.setActors(new String[] { "Liam Neeson", "Ewan McGregor", "Natalie Portman" });
		assertEquals("Liam Neeson", film.getActors()[0]);
		assertEquals("Ewan McGregor", film.getActors()[1]);
		assertEquals("Natalie Portman", film.getActors()[2]);
	}

	@Test
	public void testGetSetDurata() {
		film.setDuration(10);
		assertEquals(10, film.getDuration());
	}

	@Test
	public void testGetSetTitolo() {
		film.setTitle("Una nuova speranza");
		assertEquals("Una nuova speranza", film.getTitle());
	}

	@Test
	public void testGetSetAnno() {
		film.setYear(1975);
		assertEquals(1975, film.getYear());
	}
}
