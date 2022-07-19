package mediaesami.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import mediaesami.model.AttivitaFormativa;
import mediaesami.model.Esame;
import mediaesami.model.Voto;

class EsameTest {

	@Test
	void testOK() {
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame = new Esame(af, LocalDate.of(2022, 6, 6), Voto.DICIOTTO); 
		assertEquals(af, esame.getAf());
		assertEquals(LocalDate.of(2022, 6, 6), esame.getDate());
		assertEquals(Voto.DICIOTTO, esame.getVoto());
		Esame esameBis = new Esame(af, LocalDate.of(2022, 6, 6), Voto.DICIOTTO);
		assertEquals(esameBis, esame);
		assertEquals(esameBis.hashCode(), esame.hashCode());
	}

	@Test
	void testKO_NoAf() {
		assertThrows(IllegalArgumentException.class, () -> new Esame(null, LocalDate.of(2022, 6, 6), Voto.DICIOTTO));
	}
	
	@Test
	void testKO_NoDate() {
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		assertThrows(IllegalArgumentException.class, () -> new Esame(af, null, Voto.DICIOTTO));
	}

	@Test
	void testKO_FutureDate() {
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		assertThrows(IllegalArgumentException.class, () -> new Esame(af, LocalDate.now().plusDays(2), Voto.DICIOTTO));
	}

	@Test
	void testKO_NoVoto() {
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		assertThrows(IllegalArgumentException.class, () -> new Esame(af, LocalDate.of(2022, 6, 6), null));
	}


}
