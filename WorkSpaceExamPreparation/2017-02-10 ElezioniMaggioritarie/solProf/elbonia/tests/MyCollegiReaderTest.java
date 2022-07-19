package elbonia.tests;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import static org.junit.Assert.*;
import elbonia.model.Collegio;
import elbonia.model.Partito;
import elbonia.persistence.BadFileFormatException;
import elbonia.persistence.MyCollegiReader;

import org.junit.Test;

public class MyCollegiReaderTest {

	@Test
	public void testCaricaElementi() throws IOException, BadFileFormatException {
		String data = "collegio;Gialli;Verdi;Blu\n1;11;21;31\n2;12;22;32\n3;13;23;33\n";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		List<Collegio> collegi = collegiReader.caricaElementi(reader);
		assertEquals(3, collegi.size());
		
		Collegio c;
		Partito[] partiti;
		Partito p;
		
		c = collegi.get(0);
		assertEquals("collegio1", c.getDenominazione());
		partiti = c.getPartiti().toArray(new Partito[0]);
		assertEquals(3, partiti.length);
		p = partiti[0];
		assertEquals("Blu", p.getNome());
		assertEquals(31, p.getVoti());
		p = partiti[1];
		assertEquals("Verdi", p.getNome());
		assertEquals(21, p.getVoti());
		p = partiti[2];
		assertEquals("Gialli", p.getNome());
		assertEquals(11, p.getVoti());

		c = collegi.get(1);
		assertEquals("collegio2", c.getDenominazione());
		partiti = c.getPartiti().toArray(new Partito[0]);
		assertEquals(3, partiti.length);
		p = partiti[0];
		assertEquals("Blu", p.getNome());
		assertEquals(32, p.getVoti());
		p = partiti[1];
		assertEquals("Verdi", p.getNome());
		assertEquals(22, p.getVoti());
		p = partiti[2];
		assertEquals("Gialli", p.getNome());
		assertEquals(12, p.getVoti());

		c = collegi.get(2);
		assertEquals("collegio3", c.getDenominazione());
		partiti = c.getPartiti().toArray(new Partito[0]);
		assertEquals(3, partiti.length);
		p = partiti[0];
		assertEquals("Blu", p.getNome());
		assertEquals(33, p.getVoti());
		p = partiti[1];
		assertEquals("Verdi", p.getNome());
		assertEquals(23, p.getVoti());
		p = partiti[2];
		assertEquals("Gialli", p.getNome());
		assertEquals(13, p.getVoti());
	}
	

	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementi_MissingToken() throws IOException, BadFileFormatException {
		String data = "collegio;Gialli;Verdi;Blu\n1;11;21;31\n2;12;22\n3;13;23;33\n";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		collegiReader.caricaElementi(reader);
	}

	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementi_NumberFormat() throws IOException, BadFileFormatException {
		String data = "collegio;Gialli;Verdi;Blu\n1;CCC;21;31\n2;12;22;32\n3;13;23;33\n";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		collegiReader.caricaElementi(reader);
	}

	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementi_TooMuchTokens() throws IOException, BadFileFormatException {
		String data = "collegio;Gialli;Verdi;Blu\n1;11;21;31\n2;12;22;32;42\n3;13;23;33\n";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		collegiReader.caricaElementi(reader);
	}

}
