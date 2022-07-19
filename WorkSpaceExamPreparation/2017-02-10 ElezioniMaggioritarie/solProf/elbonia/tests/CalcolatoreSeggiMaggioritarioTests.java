package elbonia.tests;

import static org.junit.Assert.*;
import elbonia.model.CalcolatoreSeggi;
import elbonia.model.CalcolatoreSeggiMaggioritario;
import elbonia.model.Collegio;
import elbonia.model.Partito;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class CalcolatoreSeggiMaggioritarioTests {
	
	private String[] nomiPartiti;
	private List<Collegio> listaCollegi;
	
	@Before
	public void setUp() {
		nomiPartiti = new String[] {"Gialli", "Neri", "Blu", "Rossi", "Verdi"};
		int[] votiPartiti = {1, 2, 3, 4, 5};
		
		List<Partito> listaPartiti = new ArrayList<Partito>();
		
		for (int i=0; i< nomiPartiti.length; i++){
			listaPartiti.add(new Partito(nomiPartiti[i], votiPartiti[i]));
		}
		
		// due collegi identici, ognuno con metà voti (vincono i Verdi in entrambi)

		listaCollegi = new ArrayList<Collegio>();
		listaCollegi.add(new Collegio("c1", new TreeSet<Partito>(listaPartiti)));
		listaCollegi.add(new Collegio("c2", new TreeSet<Partito>(listaPartiti)));
		listaCollegi.add(new Collegio("c3", new TreeSet<Partito>(listaPartiti)));
		listaCollegi.add(new Collegio("c4", new TreeSet<Partito>(listaPartiti)));
	}

	@Test
	public void testAssegnaSeggi4() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs3 = new CalcolatoreSeggiMaggioritario();
		Map<String, Integer> mappaSeggi = cs3.assegnaSeggi(listaCollegi.size(), listaCollegi);
		
		for (int i = 0; i < nomiPartiti.length; i++) {
			assertTrue(mappaSeggi.containsKey(nomiPartiti[i]));
			int seggiAssegnati = mappaSeggi.get(nomiPartiti[i]);
			int seggiPrevisti = nomiPartiti[i].equals("Verdi") ? 4 : 0;
			assertEquals(seggiPrevisti, seggiAssegnati);
		}
	}

	@Test
	public void testAssegnaSeggi2() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs3 = new CalcolatoreSeggiMaggioritario();
		Map<String, Integer> mappaSeggi = cs3.assegnaSeggi(listaCollegi.size() / 2, listaCollegi);
		
		for (int i = 0; i < nomiPartiti.length; i++) {
			assertTrue(mappaSeggi.containsKey(nomiPartiti[i]));
			int seggiAssegnati = mappaSeggi.get(nomiPartiti[i]);
			int seggiPrevisti = nomiPartiti[i].equals("Verdi") ? 4 : 0;
			assertEquals(seggiPrevisti, seggiAssegnati);
		}
	}

	@Test
	public void testAssegnaSeggi1() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs3 = new CalcolatoreSeggiMaggioritario();
		Map<String, Integer> mappaSeggi = cs3.assegnaSeggi(listaCollegi.size() / 4, listaCollegi);
		
		for (int i = 0; i < nomiPartiti.length; i++) {
			assertTrue(mappaSeggi.containsKey(nomiPartiti[i]));
			int seggiAssegnati = mappaSeggi.get(nomiPartiti[i]);
			int seggiPrevisti = nomiPartiti[i].equals("Verdi") ? 4 : 0;
			assertEquals(seggiPrevisti, seggiAssegnati);
		}
	}
}
