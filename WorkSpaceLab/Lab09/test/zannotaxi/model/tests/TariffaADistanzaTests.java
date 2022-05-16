package zannotaxi.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.*;

import zannotaxi.model.*;

public class TariffaADistanzaTests {
	private TariffaADistanza td;
	
	@BeforeEach
	public void setUp() {
		td = new TariffaADistanza("T1", 27, Integer.MAX_VALUE, 0, 10, 25, 100);		
	}



	@Test
	public void testGetNome() {
			assertEquals("T1", td.getNome());
	}

	@Test
	public void testGetValoreScattoInEuroCent() {
		assertEquals(25, td.getValoreScatto(), 0.0001);
	}

	@Test
	public void testGetScattoDaContabilizzare() {
		Optional<Scatto> scatto = td.getScattoCorrente(50, 600,  1.0);
		assertTrue(scatto.isPresent());
		
		scatto = td.getScattoCorrente(100, 600,  1.0);
		assertFalse(scatto.isPresent());
		
	}

	@Test
	public void testGetVelocitaMinima() {	
		assertEquals(27, td.getVelocitaMinima(), 0.0001);
	}

	@Test
	public void testGetVelocitaMassima() {
		assertEquals(Integer.MAX_VALUE, td.getVelocitaMassima(), 0.0001);
	}

	@Test
	public void testGetCostoMinimo() {
		assertEquals(0, td.getCostoMinimo(), 0.0001);
	}

	@Test
	public void testGetCostoMassimo() {
		assertEquals(10, td.getCostoMassimo(), 0.0001);
	}

	@Test
	public void testGetDistanzaDiScatto() {
		assertEquals(100, td.getDistanzaDiScatto(), 0.0001);
	}

}
