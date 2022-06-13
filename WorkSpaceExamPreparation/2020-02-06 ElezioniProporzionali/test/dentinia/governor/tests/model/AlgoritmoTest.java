package dentinia.governor.tests.model;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.SortedSet;

import org.junit.Test;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.LeggeElettorale;
import dentinia.governor.model.LeggeElettoraleDHondt;
import dentinia.governor.model.Partito;
import dentinia.governor.model.RisultatoElezioni;

public class AlgoritmoTest {	
		
	
	private Elezioni creaElezioni() {
		Elezioni elezioni = new Elezioni(100,new LeggeElettoraleDHondt());
		elezioni.setVoti(new Partito("Zero"), 20);
		elezioni.setVoti(new Partito("Uno"), 80);
		elezioni.setVoti(new Partito("Due"), 200);
		elezioni.setVoti(new Partito("Tre"), 300);
		elezioni.setVoti(new Partito("Quattro"), 400);
		return elezioni;
	}
	
	@Test
	public void noSbarramento() {
		Elezioni elezioni = creaElezioni();
		SortedSet<Partito> partiti = elezioni.getPartiti();
		RisultatoElezioni r = elezioni.getRisultato();
		assertEquals(2, r.getSeggi(findPartito(partiti, "Zero")));
		assertEquals(8, r.getSeggi(findPartito(partiti, "Uno")));
		assertEquals(20, r.getSeggi(findPartito(partiti, "Due")));
		assertEquals(30, r.getSeggi(findPartito(partiti, "Tre")));
		assertEquals(40, r.getSeggi(findPartito(partiti, "Quattro")));
	}
	
	@Test
	public void sbarramento3Percento() {
		Elezioni elezioni = creaElezioni();
		SortedSet<Partito> partiti = elezioni.getPartiti();
		Elezioni elezioniFiltrate = LeggeElettorale.applicaSbarramento(elezioni, 0.03);
			System.out.println(elezioniFiltrate);
		RisultatoElezioni r = elezioniFiltrate.getRisultato();
		assertEquals(0,  r.getSeggi(findPartito(partiti, "Zero")));
		assertEquals(8,  r.getSeggi(findPartito(partiti, "Uno")));
		assertEquals(22, r.getSeggi(findPartito(partiti, "Due")));
		assertEquals(30, r.getSeggi(findPartito(partiti, "Tre")));
		assertEquals(40, r.getSeggi(findPartito(partiti, "Quattro")));
	}
	
	@Test
	public void sbarramento2Percento() {
		Elezioni elezioni = creaElezioni();
		SortedSet<Partito> partiti = elezioni.getPartiti();
		Elezioni elezioniFiltrate = LeggeElettorale.applicaSbarramento(elezioni, 0.02);
		RisultatoElezioni r = elezioniFiltrate.getRisultato();
		assertEquals(2,  r.getSeggi(findPartito(partiti, "Zero")));
		assertEquals(8,  r.getSeggi(findPartito(partiti, "Uno")));
		assertEquals(20, r.getSeggi(findPartito(partiti, "Due")));
		assertEquals(30, r.getSeggi(findPartito(partiti, "Tre")));
		assertEquals(40, r.getSeggi(findPartito(partiti, "Quattro")));
	}
	
	@Test
	public void sbarramento9Percento() {
		Elezioni elezioni = creaElezioni();
		SortedSet<Partito> partiti = elezioni.getPartiti();
		Elezioni elezioniFiltrate = LeggeElettorale.applicaSbarramento(elezioni, 0.09);
		RisultatoElezioni r = elezioniFiltrate.getRisultato();
		assertEquals(0,  r.getSeggi(findPartito(partiti, "Zero")));
		assertEquals(0,  r.getSeggi(findPartito(partiti, "Uno")));
		assertEquals(23, r.getSeggi(findPartito(partiti, "Due")));
		assertEquals(33, r.getSeggi(findPartito(partiti, "Tre")));
		assertEquals(44, r.getSeggi(findPartito(partiti, "Quattro")));
	}
	
	@Test
	public void percentualeMinore0() {
		Elezioni elezioni = creaElezioni();
		assertThrows(IllegalArgumentException.class, () -> LeggeElettorale.applicaSbarramento(elezioni, -0.01));
	}
	
	@Test
	public void votiNull() {
		assertThrows(NullPointerException.class, () -> new LeggeElettoraleDHondt().apply(null));
	}


	private Partito findPartito(SortedSet<Partito> partiti, String nome) {
		for (Partito p : partiti) {
			if (p.getNome().equals(nome))
				return p;
		}
		return null;
	}
}
