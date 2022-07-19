package mediaesami.model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mediaesami.model.AttivitaFormativa;

class AttivitaFormativaTest {

	@Test
	void testOK_IntCfu() {
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		assertEquals("Fondamenti di Informatica T-2", af.getNome());
		assertEquals(28006, af.getId());
		assertEquals(12, af.getCfu());
		
		AttivitaFormativa af2 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		assertEquals(af, af2);
		assertTrue(af.equals(af2));
		assertEquals(af.hashCode(), af2.hashCode());
	}

	@Test
	void testOK_DblCfu() {
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 10.5);
		assertEquals("Fondamenti di Informatica T-2", af.getNome());
		assertEquals(28006, af.getId());
		assertEquals(10.5, af.getCfu(), 0.01);
		
		AttivitaFormativa af2 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 10.5);
		assertEquals(af, af2);
		assertTrue(af.equals(af2));
		assertEquals(af.hashCode(), af2.hashCode());
	}

	@Test
	void testKO_NoName() {
		assertThrows(IllegalArgumentException.class, () -> new AttivitaFormativa(28006, "", 12));
	}
	
	@Test
	void testKO_WrongId_Neg() {
		assertThrows(IllegalArgumentException.class, () -> new AttivitaFormativa(-55555, "AAA", 12));
	}

	@Test
	void testKO_WrongCfu_0() {
		assertThrows(IllegalArgumentException.class, () -> new AttivitaFormativa(28006, "AAA",  0));
	}

	@Test
	void testKO_WrongCfu_Neg() {
		assertThrows(IllegalArgumentException.class, () -> new AttivitaFormativa(28006, "AAA", -2));
	}

}
