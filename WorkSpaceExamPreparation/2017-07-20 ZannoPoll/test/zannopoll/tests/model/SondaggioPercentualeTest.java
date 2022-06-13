package zannopoll.tests.model;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import zannopoll.model.Intervista;
import zannopoll.model.Sesso;
import zannopoll.model.SondaggioPercentuale;

public class SondaggioPercentualeTest {	
		
	
	private ArrayList<Intervista> makeInterviste() {
		ArrayList<Intervista> interviste = new ArrayList<Intervista>();
		interviste.add(new Intervista("Uno", 20, Sesso.MASCHIO));
		interviste.add(new Intervista("Due", 30, Sesso.MASCHIO));
		interviste.add(new Intervista("Uno", 40, Sesso.MASCHIO));
		interviste.add(new Intervista("Due", 50, Sesso.MASCHIO));
		interviste.add(new Intervista("Uno", 60, Sesso.MASCHIO));
		interviste.add(new Intervista("Due", 70, Sesso.FEMMINA));
		interviste.add(new Intervista("Uno", 80, Sesso.FEMMINA));
		interviste.add(new Intervista("Due", 90, Sesso.FEMMINA));
		interviste.add(new Intervista("Uno", 100, Sesso.FEMMINA));
		interviste.add(new Intervista("Due", 110, Sesso.FEMMINA));		
		return interviste;
	}
	
	@Test
	public void creaSondaggioPercentuale() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertEquals(20, sp.getEtaMinIntervistati());
		assertEquals(110, sp.getEtaMaxIntervistati());
		assertEquals(5, sp.getListaIntervistati("Uno").size());
		assertEquals(5, sp.getListaIntervistati("Due").size());
		assertEquals(0.5, sp.getPercentualeIntervistati("Uno"), 0.001);
		assertEquals(0.5, sp.getPercentualeIntervistati("Due"), 0.001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void sceltaInesistente() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		sp.getPercentualeIntervistati("XXX");
	}
	
	@Test
	public void filtraIntervisteTutte() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertEquals(10, sp.getIntervisteFiltrate(0, 200, Optional.empty()).size());
	}
	
	@Test
	public void filtraIntervisteFinoA50() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertEquals(4, sp.getIntervisteFiltrate(0, 50, Optional.empty()).size());
	}
	
	@Test
	public void filtraIntervisteDa50A100() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertEquals(6, sp.getIntervisteFiltrate(50, 100, Optional.empty()).size());
	}
	
	@Test
	public void filtraIntervisteFinoA100Femmine() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertEquals(4, sp.getIntervisteFiltrate(0, 100, Optional.of(Sesso.FEMMINA)).size());
	}
	
	@Test
	public void filtraIntervisteFinoA100Maschi() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertEquals(5, sp.getIntervisteFiltrate(0, 100, Optional.of(Sesso.MASCHIO)).size());
	}
	
	@Test
	public void filtraIntervisteNessuna() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertEquals(0, sp.getIntervisteFiltrate(0, 40, Optional.of(Sesso.FEMMINA)).size());
	}
	
	@Test
	public void sondaggioFiltratoVuoto() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertFalse(sp.getSondaggioFiltrato(0, 40, Optional.of(Sesso.FEMMINA)).isPresent());
	}
	
	@Test
	public void sondaggioFiltrato() {
		SondaggioPercentuale sp = new SondaggioPercentuale(makeInterviste());
		assertTrue(sp.getSondaggioFiltrato(0, 80, Optional.of(Sesso.FEMMINA)).isPresent());
	}
}
