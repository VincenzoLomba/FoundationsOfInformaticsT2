package oroscopo.persistence.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.BadFileFormatException;
import oroscopo.persistence.OroscopoRepository;
import oroscopo.persistence.TextFileOroscopoRepository;

public class TextFileOroscopoRepositoryTest {
	
	@BeforeAll
	public static void setUp() {
		System.setProperty("java.locale.providers", "JRE");
	}

	@Test
	public void testCtor() throws IOException, BadFileFormatException {

		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE");

		new TextFileOroscopoRepository(mr);
	}

	@Test
	public void testCtor_BadParam() throws IOException, BadFileFormatException {

		Reader mr = null;

		assertThrows(IllegalArgumentException.class, () -> 
		new TextFileOroscopoRepository(mr));
	}

	@Test
	public void testMultiSezione() throws IOException, BadFileFormatException {

		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		new TextFileOroscopoRepository(mr);
	}

	@Test
	public void testNomeSezioneMancante1() throws IOException, BadFileFormatException {

		Reader mr = new StringReader(
				"avrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		assertThrows(BadFileFormatException.class, () ->
		new TextFileOroscopoRepository(mr));
	}

	@Test
	public void testNomeSezioneMancante2() throws IOException, BadFileFormatException {

		Reader mr = new StringReader(
				"stringasenzaspazichepuosempresuccedere\t\t4\tARIETE,TORO,GEMELLI\naltrastringasenzaspazi\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		assertThrows(BadFileFormatException.class, () ->
		new TextFileOroscopoRepository(mr));
	}

	@Test
	public void testMancaValore() throws IOException, BadFileFormatException {

		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t\t\nFINE");

		assertThrows(BadFileFormatException.class, () ->
		new TextFileOroscopoRepository(mr));
	}

	@Test
	public void testMancaFine() throws IOException, BadFileFormatException {

		Reader mr = new StringReader("avrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\n\n"
				+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		assertThrows(BadFileFormatException.class, () ->
		new TextFileOroscopoRepository(mr));
	}

	@Test
	public void testSezioneSenzaFrasi() throws IOException, BadFileFormatException {

		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\n\nFINE");

		assertThrows(BadFileFormatException.class, () ->
		new TextFileOroscopoRepository(mr));
	}

	@Test
	public void testSegniNonParsabili() throws IOException, BadFileFormatException {

		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tMUCCA,SIAMESI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		assertThrows(BadFileFormatException.class, () -> 
		new TextFileOroscopoRepository(mr));
	}

	@Test
	public void testGetPrevisioniPerSezioneEsistenteLowercase() throws IOException, BadFileFormatException {
		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		OroscopoRepository or = new TextFileOroscopoRepository(mr);

		assertEquals(2, or.getPrevisioni("nomesezione").size());
	}

	@Test
	public void testLetturaCorrettaPrevisioni1() throws IOException, BadFileFormatException {
		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		OroscopoRepository or = new TextFileOroscopoRepository(mr);

		assertEquals("avrai la testa un po' altrove", or.getPrevisioni("nomesezione").get(0).getPrevisione());
		assertEquals(4, or.getPrevisioni("nomesezione").get(0).getValore());
		Set<SegnoZodiacale> validi = new HashSet<SegnoZodiacale>() {
			private static final long serialVersionUID = 1L;

			{
				add(SegnoZodiacale.ARIETE);
				add(SegnoZodiacale.TORO);
				add(SegnoZodiacale.GEMELLI);
			}
		};
		for (SegnoZodiacale s : SegnoZodiacale.values()) {
			if (validi.contains(s))
				assertTrue(or.getPrevisioni("nomesezione").get(0).validaPerSegno(s));
			else {
				assertFalse(or.getPrevisioni("nomesezione").get(0).validaPerSegno(s));
			}
		}

		assertEquals("grande intimita'", or.getPrevisioni("nomesezione").get(1).getPrevisione());
		assertEquals(9, or.getPrevisioni("nomesezione").get(1).getValore());
		for (SegnoZodiacale s : SegnoZodiacale.values()) {
			assertTrue(or.getPrevisioni("nomesezione").get(1).validaPerSegno(s));
		}
	}

	@Test
	public void testLetturaCorrettaPrevisioni2() throws IOException, BadFileFormatException {
		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		OroscopoRepository or = new TextFileOroscopoRepository(mr);

		assertEquals(1, or.getPrevisioni("sezione2").size());
		assertEquals("testo di prova", or.getPrevisioni("sezione2").get(0).getPrevisione());
		assertEquals(66, or.getPrevisioni("sezione2").get(0).getValore());

		for (SegnoZodiacale s : SegnoZodiacale.values()) {
			assertTrue(or.getPrevisioni("sezione2").get(0).validaPerSegno(s));
		}
	}

	@Test
	public void testGetPrevisioniPerSezioneEsistenteNomeUppercase() throws IOException, BadFileFormatException {
		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		OroscopoRepository or = new TextFileOroscopoRepository(mr);

		assertEquals(2, or.getPrevisioni("NOMESEZIONE").size());
	}

	@Test
	public void testGetPrevisioniPerSezioneNonEsistenteRestituisceNull() throws IOException, BadFileFormatException {
		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");

		OroscopoRepository or = new TextFileOroscopoRepository(mr);

		assertNull(or.getPrevisioni("SEZIONECHENONCE"));
	}

	@Test
	public void testGetSezioni() throws IOException, BadFileFormatException {
		Reader mr = new StringReader(
				"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		OroscopoRepository or = new TextFileOroscopoRepository(mr);

		assertEquals(2, or.getSettori().size());
		TreeSet<String> settori = new TreeSet<String>(new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareToIgnoreCase(s2);
			}
		});
		settori.addAll(or.getSettori());
		assertTrue(settori.contains("noMeSeZione"));
		assertTrue(settori.contains("sezione2"));
	}

}
