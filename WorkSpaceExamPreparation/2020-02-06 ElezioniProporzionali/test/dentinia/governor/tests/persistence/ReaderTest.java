package dentinia.governor.tests.persistence;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.Partito;
import dentinia.governor.persistence.BadFileFormatException;
import dentinia.governor.persistence.MyVotiReader;
import dentinia.governor.persistence.VotiReader;

public class ReaderTest {

	@Before
	public void setUp() throws Exception {
		String s =  "SEGGI 100\nLista A 1.100.000, Lista B 2.200.000\n";
		new MyVotiReader(new StringReader(s));
	}

	@Test
	public void testCostruttoreMyVotiReader() throws BadFileFormatException, IOException {
		String s =  "SEGGI 100\nLista A 1.100.000, Lista B 2.200.000\n";
		new MyVotiReader(new StringReader(s));
	}

	@Test
	public void testCostruttoreMyVotiReaderConReaderNull() throws BadFileFormatException, IOException {
		assertThrows(IllegalArgumentException.class, () -> new MyVotiReader(null));
	}


	@Test
	public void testCaricaElementi() throws IOException, BadFileFormatException {
		String s =  "SEGGI 100\nLista A 1.100.000, Lista B 2.200.000\n";
		VotiReader reader = new MyVotiReader(new StringReader(s));		
		Elezioni voti = reader.getElezioni();
		
		assertEquals(100, voti.getSeggiDaAssegnare());
		
		Partito[] partiti = voti.getPartiti().toArray(new Partito[0]);
		assertEquals(2, partiti.length);
		
		assertEquals(1100000, voti.getVoti(partiti[0]));
		assertEquals(2200000, voti.getVoti(partiti[1]));
		assertEquals("Lista A", partiti[0].getNome());
		assertEquals("Lista B", partiti[1].getNome());
	}
	

	@Test
	public void mancaNumeroVoti() throws IOException, BadFileFormatException {
		String s =  "SEGGI 100\nLista A XXX, Lista B 2.200.000\n";
		assertThrows(BadFileFormatException.class, () -> new MyVotiReader(new StringReader(s)));		
	}
	
	@Test
	public void mancaNomePartito() throws IOException, BadFileFormatException {
		String s =  "SEGGI 100\n1.100.000, Lista B 2.200.000\n";
		assertThrows(BadFileFormatException.class, () -> new MyVotiReader(new StringReader(s)));		
	}

	@Test
	public void mancaNumeroSeggiInPalio() throws IOException, BadFileFormatException {
		String s =  "SEGGI XXX\nLista A 1.100.000, Lista B 2.200.000\n";
		assertThrows(BadFileFormatException.class, () -> new MyVotiReader(new StringReader(s)));		
	}
	
	@Test
	public void mancaSeggiInPalio() throws IOException, BadFileFormatException {
		String s =  "XXX XXX XXX 100\nA uno 1.100.000, B due 2.200.000\n";
		assertThrows(BadFileFormatException.class, () -> new MyVotiReader(new StringReader(s)));
	}

}
