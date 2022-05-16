package zannotaxi.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.*;

import zannotaxi.model.*;

public class TariffaATempoTests {
	private TariffaATempo tt;
	
	@BeforeEach
	public void setUp() {
		tt = new TariffaATempo("T0", 0, 27,  0.15, 12);	
	}



	@Test
	public void testGetNome() {
			assertEquals("T0", tt.getNome());
	}

	@Test
	public void testGetValoreScattoInEuroCent() {
		assertEquals(0.15, tt.getValoreScatto(), 0.0001);
	}

	@Test
	public void testGetScattoDaContabilizzare() {
		Optional<Scatto> scatto = tt.getScattoCorrente(200, 600,  1.0);
		assertTrue(scatto.isPresent());
		
		scatto = tt.getScattoCorrente(50, 600,  1.0);
		assertFalse(scatto.isPresent());
		
	}

	@Test
	public void testGetVelocitaMinima() {	
		assertEquals(0, tt.getVelocitaMinima(), 0.0001);
	}

	@Test
	public void testGetVelocitaMassima() {
		assertEquals(27, tt.getVelocitaMassima(), 0.0001);
	}

	
	@Test
	public void testGetTempoDiScatto() {
		assertEquals(12, tt.getTempoDiScatto(), 0.0001);
	}

}
