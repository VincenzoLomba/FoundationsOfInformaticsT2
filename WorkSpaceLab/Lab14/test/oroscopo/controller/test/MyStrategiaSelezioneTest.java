package oroscopo.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oroscopo.controller.MyStrategiaSelezione;
import oroscopo.controller.StrategiaSelezione;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyStrategiaSelezioneTest {

	private List<Previsione> previsioni;

	@BeforeEach
	public void setup() {	
		previsioni = new ArrayList<>();
		previsioni.add(new Previsione("previsione 0", 10));
		previsioni.add(new Previsione("previsione 1", 10));
		previsioni.add(new Previsione("previsione 2", 10, Collections.singleton(SegnoZodiacale.ACQUARIO)));
		Set<SegnoZodiacale> segni = new HashSet<>();
		segni.add(SegnoZodiacale.PESCI);
		segni.add(SegnoZodiacale.ACQUARIO);
		previsioni.add(new Previsione("previsione 3", 10, segni));
		previsioni.add(new Previsione("previsione 4", 10, Collections.emptySet()));
	}

	@Test
	public void testSelezionaPerSegno() {

		StrategiaSelezione daTestare = new MyStrategiaSelezione();

		for (SegnoZodiacale s : SegnoZodiacale.values()) {
			Previsione p = daTestare.seleziona(previsioni, s);
			assertTrue(p.validaPerSegno(s));
		}
	}

	@Test
	public void testDistribuzioneDiProbabilita2su5() {

		StrategiaSelezione daTestare = new MyStrategiaSelezione();

		int[] probabilita = new int[5];
		for (int i = 0; i < 10000; i++) {
			Previsione p = daTestare.seleziona(previsioni, SegnoZodiacale.ARIETE);
			probabilita[previsioni.indexOf(p)]++;
		}

		assertTrue(probabilita[0] > 1000);
		assertTrue(probabilita[1] > 1000);
		assertEquals(0, probabilita[2]);
		assertEquals(0, probabilita[3]);
		assertEquals(0, probabilita[4]);
	}

	@Test
	public void testDistribuzioneDiProbabilita3su5() {

		StrategiaSelezione daTestare = new MyStrategiaSelezione();

		int[] probabilita = new int[5];
		for (int i = 0; i < 10000; i++) {
			Previsione p = daTestare.seleziona(previsioni, SegnoZodiacale.PESCI);
			probabilita[previsioni.indexOf(p)]++;
		}

		assertTrue(probabilita[0] > 1000);
		assertTrue(probabilita[1] > 1000);
		assertEquals(0, probabilita[2]);
		assertTrue(probabilita[3] > 1000);
		assertEquals(0, probabilita[4]);
	}

	@Test
	public void testDistribuzioneDiProbabilita4su5() {

		StrategiaSelezione daTestare = new MyStrategiaSelezione();

		int[] probabilita = new int[5];
		for (int i = 0; i < 10000; i++) {
			Previsione p = daTestare.seleziona(previsioni, SegnoZodiacale.ACQUARIO);
			probabilita[previsioni.indexOf(p)]++;
		}

		assertTrue(probabilita[0] > 1000);
		assertTrue(probabilita[1] > 1000);
		assertTrue(probabilita[2] > 1000);
		assertTrue(probabilita[3] > 1000);
		assertEquals(0, probabilita[4]);
	}

}
