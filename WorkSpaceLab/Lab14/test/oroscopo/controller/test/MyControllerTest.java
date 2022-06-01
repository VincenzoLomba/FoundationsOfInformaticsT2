package oroscopo.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import oroscopo.controller.AbstractController;
import oroscopo.controller.MyController;
import oroscopo.controller.StrategiaSelezione;
import oroscopo.model.Oroscopo;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.OroscopoRepository;
import oroscopo.persistence.test.TextFileOroscopoRepositoryMock;

public class MyControllerTest {

	private OroscopoRepository reader;
	private StrategiaSelezione strategia;

	@BeforeEach
	public void setUp() {
		try {
			reader = new TextFileOroscopoRepositoryMock(null);
			strategia = new MyStrategiaSelezioneMock();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testMyController() throws IOException {
		new MyController(reader, strategia);
	}

	@Test
	public void testGetSegni() throws IOException {

		AbstractController underTest = new MyController(reader, strategia);

		SegnoZodiacale[] segni = underTest.getSegni();

		assertEquals(AbstractController.NUMERO_SEGNI, segni.length);

		assertEquals(segni[0], SegnoZodiacale.ARIETE);
		assertEquals(segni[1], SegnoZodiacale.TORO);
		assertEquals(segni[2], SegnoZodiacale.GEMELLI);
		assertEquals(segni[3], SegnoZodiacale.CANCRO);
		assertEquals(segni[4], SegnoZodiacale.LEONE);
		assertEquals(segni[5], SegnoZodiacale.VERGINE);
		assertEquals(segni[6], SegnoZodiacale.BILANCIA);
		assertEquals(segni[7], SegnoZodiacale.SCORPIONE);
		assertEquals(segni[8], SegnoZodiacale.SAGITTARIO);
		assertEquals(segni[9], SegnoZodiacale.CAPRICORNO);
		assertEquals(segni[10], SegnoZodiacale.ACQUARIO);
		assertEquals(segni[11], SegnoZodiacale.PESCI);
	}

	@Test
	public void testGeneraOroscopoCasuale() throws IOException {

		AbstractController underTest = new MyController(reader, strategia);

		Oroscopo risultato = underTest.generaOroscopoCasuale(SegnoZodiacale.ARIETE);

		assertEquals("S1 - Tu, io, quello e questo", risultato.getPrevisioneAmore().getPrevisione());
		assertEquals("S2 - Unduettre", risultato.getPrevisioneLavoro().getPrevisione());
		assertEquals("S3 - Sei un quaraquaqua", risultato.getPrevisioneSalute().getPrevisione());

		assertEquals(10, risultato.getPrevisioneAmore().getValore());
		assertEquals(10, risultato.getPrevisioneLavoro().getValore());
		assertEquals(10, risultato.getPrevisioneSalute().getValore());

		assertEquals(SegnoZodiacale.ARIETE, risultato.getSegnoZodiacale());
		assertEquals(10, risultato.getFortuna());
	}

	@Test
	public void testGeneraOroscopoAnnuale() throws IOException {
		AbstractController underTest = new MyController(reader, strategia);
		Oroscopo[] risultato = underTest.generaOroscopoAnnuale(SegnoZodiacale.ARIETE, 5);

		assertEquals(12, risultato.length);

		int fortuna = 0;
		for (Oroscopo o : risultato) {
			fortuna += o.getFortuna();
		}

		assertEquals(10, fortuna / 12);

	}

}
