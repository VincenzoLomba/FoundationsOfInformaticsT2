package zannopoll.tests.persistence;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import zannopoll.model.Intervista;
import zannopoll.model.Sesso;
import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.MySondaggioReader;

public class ReaderTest {

	@Test(expected = IllegalArgumentException.class)
	public void testLeggiRisposteConReaderNull() throws BadFileFormatException, IOException {
		MySondaggioReader r = new MySondaggioReader();
		r.leggiRisposte(null);
	}

	@Test
	public void testLeggiRisposte() throws IOException, BadFileFormatException {
		String s = "Insieme per il salume nostrano,54,F\n" + "Pizza è progresso,89,M\n";
		MySondaggioReader r = new MySondaggioReader();
		List<Intervista> interviste = r.leggiRisposte(new StringReader(s));

		assertEquals(2, interviste.size());

		Intervista i = interviste.get(0);
		assertEquals("Insieme per il salume nostrano", i.getNome());
		assertEquals(54, i.getEta());
		assertEquals(Sesso.FEMMINA, i.getSesso());
		
		i = interviste.get(1);
		assertEquals("Pizza è progresso", i.getNome());
		assertEquals(89, i.getEta());
		assertEquals(Sesso.MASCHIO, i.getSesso());
	}

	@Test(expected = BadFileFormatException.class)
	public void etaNonNumerico() throws IOException, BadFileFormatException {
		String s = "Insieme per il salume nostrano,XXX,F\n" + "Pizza è progresso,89,M\n";
		MySondaggioReader r = new MySondaggioReader();
		r.leggiRisposte(new StringReader(s));
	}

	@Test(expected = BadFileFormatException.class)
	public void mancaNomePartito() throws IOException, BadFileFormatException {
		String s = "54,F\n" + "Pizza è progresso,89,M\n";
		MySondaggioReader r = new MySondaggioReader();
		r.leggiRisposte(new StringReader(s));
	}

	@Test(expected = BadFileFormatException.class)
	public void sessoNonConforme() throws IOException, BadFileFormatException {
		String s = "Insieme per il salume nostrano,54,X\n" + "Pizza è progresso,89,M\n";
		MySondaggioReader r = new MySondaggioReader();
		r.leggiRisposte(new StringReader(s));
	}

	@Test(expected = BadFileFormatException.class)
	public void mancaSesso() throws IOException, BadFileFormatException {
		String s = "Insieme per il salume nostrano,54\n" + "Pizza è progresso,89,M\n";
		MySondaggioReader r = new MySondaggioReader();
		r.leggiRisposte(new StringReader(s));
	}

	@Test(expected = BadFileFormatException.class)
	public void mancaEta() throws IOException, BadFileFormatException {
		String s = "Insieme per il salume nostrano,F\n" + "Pizza è progresso,89,M\n";
		MySondaggioReader r = new MySondaggioReader();
		r.leggiRisposte(new StringReader(s));
	}

}
