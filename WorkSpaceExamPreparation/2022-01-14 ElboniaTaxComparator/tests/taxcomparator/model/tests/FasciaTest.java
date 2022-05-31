package taxcomparator.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

import taxcomparator.model.Fascia;

class FasciaTest {

	@Test
	void testOK1_string() throws ParseException {
		Fascia f1 = new Fascia(0, 16000, "15%");
		assertEquals(0.15, f1.getAliquota(), 0.001);
		assertEquals(0, f1.getMin(), 0.001);
		assertEquals(16000, f1.getMax(), 0.001);
	}

	@Test
	void testOK2_string() throws ParseException {
		Fascia f1 = new Fascia(70000, "55%");
		assertEquals(0.55, f1.getAliquota(), 0.001);
		assertEquals(70000, f1.getMin(), 0.001);
		assertEquals(Double.MAX_VALUE, f1.getMax());
	}

	@Test
	void testOK1_double() throws ParseException {
		Fascia f1 = new Fascia(0, 16000, 0.15);
		assertEquals(0.15, f1.getAliquota(), 0.001);
		assertEquals(0, f1.getMin(), 0.001);
		assertEquals(16000, f1.getMax(), 0.001);
		//System.out.println(f1);
	}

	@Test
	void testOK2_double() throws ParseException {
		Fascia f1 = new Fascia(25000, 50000, 0.33);
		assertEquals(0.33, f1.getAliquota(), 0.001);
		assertEquals(25000, f1.getMin(), 0.001);
		assertEquals(50000, f1.getMax(), 0.001);
		//System.out.println(f1);
	}
	
	@Test
	void testOK3_double() throws ParseException {
		Fascia f1 = new Fascia(70000, 0.55);
		assertEquals(0.55, f1.getAliquota(), 0.001);
		assertEquals(70000, f1.getMin(), 0.001);
		assertEquals(Double.MAX_VALUE, f1.getMax(), 0.001);
		//System.out.println(f1);
	}
	
	@Test
	void testKO_NegativeDa() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fascia(-3, 70000, 0.55));
	}
	
	@Test
	void testKO_NegativeA() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fascia(0, -70000, 0.55));
	}
	
	@Test
	void testKO_IllegalDaA() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fascia(30000, 20000, 0.55));
	}
	
	@Test
	void testKO_NegativeAliquota() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fascia(0, 70000, -0.1));
	}

	@Test
	void testKO_AliquotaGreaterThan1() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fascia(0, 70000, 1.1));
	}

	@Test
	void testKO_NegativeAliquota_string() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fascia(0, 70000, "-1%"));
	}

	@Test
	void testKO_AliquotaGreaterThan1_string() throws ParseException {
		assertThrows(IllegalArgumentException.class, () ->  new Fascia(0, 70000, "115%"));
	}
}
