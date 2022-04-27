package media.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import media.Song;
import media.Type;

public class SongTest {
	Song song = null;

	@BeforeEach
	public void setUp() {
		song = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");

	}

	@Test
	public void testGetTipo() {
		assertEquals(Type.SONG, song.getType());
	}

	@Test
	public void testEqualsObject() {
		Song song2 = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 6, "Melenso");
		assertEquals(song, song2);
		song2 = new Song("Questo Piccolo Grande Amore", 1972, "Claudio Baglioni", 8, "Melenso");
		assertFalse(song.equals(song2));
	}

	@Test
	public void testGetSetSinger() {
		song.setSinger("Mina");
		assertEquals("Mina", song.getSinger());
	}

	@Test
	public void testGetSetDurata() {
		song.setDuration(10);
		assertEquals(10, song.getDuration());
	}

	@Test
	public void testGetSetTitolo() {
		song.setTitle("Poster");
		assertEquals("Poster", song.getTitle());
	}

	@Test
	public void testGetSetAnno() {
		song.setYear(1975);
		assertEquals(1975, song.getYear());
	}

}
