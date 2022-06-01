package oroscopo.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import oroscopo.model.Oroscopo;
import oroscopo.model.OroscopoMensile;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class OroscopoMensileTest {

	@Test
	public void testOroscopoMensileCostruttore1() {
		Previsione previsione = new Previsione("previsione", 1);
		new OroscopoMensile(SegnoZodiacale.ACQUARIO.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.ARIETE.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.BILANCIA.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.CANCRO.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.CAPRICORNO.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.LEONE.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.GEMELLI.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.PESCI.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.SAGITTARIO.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.SCORPIONE.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.TORO.toString(), previsione, previsione, previsione);
		new OroscopoMensile(SegnoZodiacale.VERGINE.toString(), previsione, previsione, previsione);
	}

	@Test
	public void testOroscopoMensileCostruttore1fail() {
		Previsione previsione = new Previsione("previsione", 1);

		assertThrows(IllegalArgumentException.class, () ->
		new OroscopoMensile("QUESTO_SEGNO_NON_ESISTE", previsione, previsione, previsione));
	}

	@Test
	public void testOroscopoMensileCostruttore2() {
		Previsione previsione = new Previsione("previsione", 1);

		new OroscopoMensile(SegnoZodiacale.ACQUARIO, previsione, previsione, previsione);
	}

	@Test
	public void testOroscopoMensileCostruttore2fail_1() {
		Previsione previsione = new Previsione("previsione", 1);

		assertThrows(IllegalArgumentException.class, () ->
		new OroscopoMensile(SegnoZodiacale.ACQUARIO, null, previsione, previsione));
	}

	@Test
	public void testOroscopoMensileCostruttore2fail_2() {
		Previsione previsione = new Previsione("previsione", 1);

		assertThrows(IllegalArgumentException.class, () -> 
		new OroscopoMensile(SegnoZodiacale.ACQUARIO, previsione, null, previsione));
	}

	@Test
	public void testOroscopoMensileCostruttore2fail_3() {
		Previsione previsione = new Previsione("previsione", 1);

		assertThrows(IllegalArgumentException.class, () ->
		new OroscopoMensile(SegnoZodiacale.ACQUARIO, previsione, previsione, null));
	}

	@Test
	public void testOroscopoMensileCostruttore2fail_previsione_valida_per_segno() {
		Previsione previsione = new Previsione("previsione", 1);

		Set<SegnoZodiacale> segni = new HashSet<>();
		segni.addAll(Set.of(SegnoZodiacale.values()));
		segni.remove(SegnoZodiacale.BILANCIA);
		Previsione previsione2 = new Previsione("previsione", 1, segni);

		new OroscopoMensile(SegnoZodiacale.CANCRO, previsione2, previsione, previsione2);
	}

	@Test
	public void testOroscopoMensileCostruttore2fail_previsione_lavoro_non_valida_per_segno() {
		Set<SegnoZodiacale> segni = new HashSet<>();
		segni.addAll(Set.of(SegnoZodiacale.values()));
		segni.remove(SegnoZodiacale.BILANCIA);
		Previsione previsione = new Previsione("previsione", 1, segni);

		Previsione previsione2 = new Previsione("previsione", 1);

		assertThrows(IllegalArgumentException.class, () ->
		new OroscopoMensile(SegnoZodiacale.BILANCIA, previsione2, previsione, previsione2));
	}

	@Test
	public void testOroscopoMensileCostruttore2fail_previsione_amore_non_valida_per_segno() {
		Previsione previsione = new Previsione("previsione", 1, Collections.emptySet());
		Previsione previsione2 = new Previsione("previsione", 1);

		assertThrows(IllegalArgumentException.class, () ->
		new OroscopoMensile(SegnoZodiacale.SCORPIONE, previsione, previsione2, previsione2));
	}

	@Test
	public void testOroscopoMensileCostruttore2fail_previsione_salute_non_valida_per_segno() {
		Set<SegnoZodiacale> segni = new HashSet<>();
		segni.addAll(Set.of(SegnoZodiacale.values()));
		segni.remove(SegnoZodiacale.TORO);
		segni.remove(SegnoZodiacale.VERGINE);
		Previsione previsione = new Previsione("previsione", 1, segni);

		Previsione previsione2 = new Previsione("previsione", 1);

		assertThrows(IllegalArgumentException.class, () ->
		new OroscopoMensile(SegnoZodiacale.VERGINE, previsione2, previsione2, previsione));
	}

	@Test
	public void testGetSegnoZodiacale() {
		Previsione previsione = new Previsione("previsione", 1);

		Oroscopo daTestare = new OroscopoMensile(SegnoZodiacale.ACQUARIO.toString(), previsione, previsione, previsione);

		assertEquals(SegnoZodiacale.ACQUARIO, daTestare.getSegnoZodiacale());
	}

	@Test
	public void testGetPrevisioneAmore() {
		Previsione previsione = new Previsione("previsione", 1);
		Previsione previsione2 = new Previsione("previsione", 1);

		Oroscopo daTestare = new OroscopoMensile(SegnoZodiacale.CAPRICORNO, previsione, previsione2, previsione2);

		assertEquals(previsione, daTestare.getPrevisioneAmore());
	}

	@Test
	public void testGetPrevisioneSalute() {
		Previsione previsione = new Previsione("previsione", 1);
		Previsione previsione2 = new Previsione("previsione", 1);

		Oroscopo daTestare = new OroscopoMensile(SegnoZodiacale.CAPRICORNO, previsione2, previsione2, previsione);

		assertEquals(previsione, daTestare.getPrevisioneSalute());
	}

	@Test
	public void testGetPrevisioneLavoro() {
		Previsione previsione = new Previsione("previsione", 1);
		Previsione previsione2 = new Previsione("previsione", 1);

		Oroscopo daTestare = new OroscopoMensile(SegnoZodiacale.CAPRICORNO, previsione2, previsione, previsione2);

		assertEquals(previsione, daTestare.getPrevisioneLavoro());
	}

	@Test
	public void testGetFortuna() {
		Previsione previsione = new Previsione("previsione", 2);
		Previsione previsione2 = new Previsione("previsione", 4);
		Previsione previsione3 = new Previsione("previsione", 6);

		Oroscopo daTestare = new OroscopoMensile(SegnoZodiacale.ARIETE, previsione, previsione2, previsione3);

		assertEquals(4, daTestare.getFortuna());
	}

	@Test
	public void testGetFortunaArrotondamentoPerDifetto() {
		Previsione previsione = new Previsione("previsione", 2);
		Previsione previsione2 = new Previsione("previsione", 3);
		Previsione previsione3 = new Previsione("previsione", 5);

		Oroscopo daTestare = new OroscopoMensile(SegnoZodiacale.ARIETE, previsione, previsione2, previsione3);

		assertEquals(3, daTestare.getFortuna());
	}

	@Test
	public void testGetFortunaArrotondamentoPerEccesso() {
		Previsione previsione = new Previsione("previsione", 2);
		Previsione previsione2 = new Previsione("previsione", 3);
		Previsione previsione3 = new Previsione("previsione", 6);

		Oroscopo daTestare = new OroscopoMensile(SegnoZodiacale.ARIETE, previsione, previsione2, previsione3);

		assertEquals(4, daTestare.getFortuna());
	}

	@Test
	public void testToString() {
		Previsione previsione = new Previsione("previsione.txt.prev", 1);
		Previsione previsione2 = new Previsione("previsione2.txt.prev", 1);
		Previsione previsione3 = new Previsione("p2.txt.prev", 1);

		String testoDaVerificare = new OroscopoMensile("SAGITTARIO", previsione, previsione2, previsione3).toString();
		
		assertTrue(testoDaVerificare.indexOf(previsione.getPrevisione()) > -1);
		assertTrue(testoDaVerificare.indexOf(previsione2.getPrevisione()) > -1);
		assertTrue(testoDaVerificare.indexOf(previsione3.getPrevisione()) > -1);
	}

	@Test
	public void testCompareTo_primoSegnoPrecedente() {
		Previsione previsione = new Previsione("previsione", 2);

		Comparable<Oroscopo> o1 = new OroscopoMensile("CANCRO", previsione, previsione, previsione);

		Oroscopo o2 = new OroscopoMensile("PESCI", previsione, previsione, previsione);

		assertTrue(o1.compareTo(o2) < 0);
	}

	@Test
	public void testCompareTo_primoSegnoSuccessivo() {
		Previsione previsione = new Previsione("previsione", 2);

		Comparable<Oroscopo> o1 = new OroscopoMensile("PESCI", previsione, previsione, previsione);

		Oroscopo o2 = new OroscopoMensile("ACQUARIO", previsione, previsione, previsione);

		assertTrue(o1.compareTo(o2) > 0);
	}

	@Test
	public void testCompareTo_segniUguali() {
		Previsione previsione = new Previsione("previsione", 2);

		Comparable<Oroscopo> o1 = new OroscopoMensile("GEMELLI", previsione, previsione, previsione);

		Oroscopo o2 = new OroscopoMensile("GEMELLI", previsione, previsione, previsione);

		assertTrue(o1.compareTo(o2) == 0);
	}

}
