package taxcomparator.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import taxcomparator.model.Fasce;
import taxcomparator.model.Fascia;

class FasceTest {

	@Test
	void testOK1() throws ParseException {
		List<Fascia> listaFasce = List.of( 
				new Fascia(0,     16000, "15%"), 
				new Fascia(16000, 25000, "20%"),
				new Fascia(25000, 46000, "33%"),
				new Fascia(46000, "40%"));
		;
		Fasce fasce = new Fasce("fascia unica di test", listaFasce, 8000);
		assertEquals(listaFasce, fasce.getFasce());
		assertEquals(8000, fasce.getNoTaxArea(), 0.001);
	}

	@Test
	void testKO_EmptyFasce() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fasce("vuote", Collections.emptyList(), 5000));
	}
	
	@Test
	void testKO_NegativeNoTaxArea() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fasce("fascia unica di test", 
									List.of(new Fascia(0, 16000, "15%")), -1000));
	}
	
	@Test
	void testKO_InconsistentSecondaFasciaMin() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fasce("fascia unica di test", 
				List.of(
				new Fascia(0,     16000, "15%"), 
				new Fascia(17000, 25000, "20%"),
				new Fascia(25000, 46000, "33%")
				), 5000));
	}

	@Test
	void testKO_InconsistentSecondaFasciaMax() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fasce("fascia unica di test", 
				List.of(
				new Fascia(0,     16000, "15%"), 
				new Fascia(16000, 24500, "20%"),
				new Fascia(25000, 46000, "33%")
				), 5000));
	}

	@Test
	void testKO_InconsistentPrimaFasciaMin() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fasce("fascia unica di test", 
				List.of(
				new Fascia(10,    16000, "15%"), 
				new Fascia(16000, 25000, "20%"),
				new Fascia(25000, 46000, "33%")
				), 5000));
	}
	
	@Test
	void testKO_InconsistentFasciaOltreInMezzo() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fasce("fascia unica di test", 
				List.of(
				new Fascia(10,    16000, "15%"), 
				new Fascia(16000, "20%"),
				new Fascia(25000, 46000, "33%"),
				new Fascia(46000, "40%")
				), 5000));
	}
}
