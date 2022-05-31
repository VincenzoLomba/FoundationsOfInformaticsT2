package taxcomparator.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;

import taxcomparator.model.CalcolatoreImposta;
import taxcomparator.model.Fasce;
import taxcomparator.model.Fascia;
import taxcomparator.model.MyCalcolatoreImposta;

public class CalcolatoreImpostaTest {

	@Test
	public void testCalcolaImpostaInSecondaFascia_SenzaNoTaxArea() throws ParseException {
		List<Fascia> listaFasce = List.of( 
				new Fascia(0,     16000, "15%"), 
				new Fascia(16000, 25000, "20%"),
				new Fascia(25000, 46000, "33%"),
				new Fascia(46000, "40%"));
		Fasce fasce = new Fasce("fascia unica di test", listaFasce, 0);
		CalcolatoreImposta cr = new MyCalcolatoreImposta();
		assertEquals(16000*0.15 + 4000*0.20, cr.calcolaImposta(20000,fasce), 0.01);
	}

	@Test
	public void testCalcolaImpostaInSecondaFascia_ConNoTaxArea() throws ParseException {
		List<Fascia> listaFasce = List.of( 
				new Fascia(0,     16000, "15%"), 
				new Fascia(16000, 25000, "20%"),
				new Fascia(25000, 46000, "33%"),
				new Fascia(46000, "40%"));
		Fasce fasce = new Fasce("fascia unica di test", listaFasce, 8000);
		CalcolatoreImposta cr = new MyCalcolatoreImposta();
		assertEquals(12000*0.15, cr.calcolaImposta(20000,fasce), 0.01);
	}

	@Test
	public void testCalcolaImpostaInTerzaFascia_ConNoTaxArea() throws ParseException {
		List<Fascia> listaFasce = List.of( 
				new Fascia(0,     16000, "15%"), 
				new Fascia(16000, 25000, "20%"),
				new Fascia(25000, 46000, "33%"),
				new Fascia(46000, "40%"));
		Fasce fasce = new Fasce("fascia unica di test", listaFasce, 8000);
		CalcolatoreImposta cr = new MyCalcolatoreImposta();
		assertEquals(16000*0.15 + 6000*0.20, cr.calcolaImposta(30000,fasce), 0.01);
	}

	@Test
	public void testCalcolaImpostaInTerzaFascia_ConNoTaxAreaPiccola() throws ParseException {
		List<Fascia> listaFasce = List.of( 
				new Fascia(0,     16000, "15%"), 
				new Fascia(16000, 25000, "20%"),
				new Fascia(25000, 46000, "33%"),
				new Fascia(46000, "40%"));
		Fasce fasce = new Fasce("fascia unica di test", listaFasce, 2000);
		CalcolatoreImposta cr = new MyCalcolatoreImposta();
		assertEquals(16000*0.15 + 9000*0.20 + 3000*0.33, cr.calcolaImposta(30000,fasce), 0.01);
	}

	@Test
	public void testCalcolaImpostaOltreTerzaFascia_ConNoTaxArea() throws ParseException {
		List<Fascia> listaFasce = List.of( 
				new Fascia(0,     16000, "15%"), 
				new Fascia(16000, 25000, "20%"),
				new Fascia(25000, 46000, "33%"),
				new Fascia(46000, "40%"));
		Fasce fasce = new Fasce("fascia unica di test", listaFasce, 8000);
		CalcolatoreImposta cr = new MyCalcolatoreImposta();
		assertEquals(16000*0.15 + 9000*0.20 + 17000*0.33, cr.calcolaImposta(50000,fasce), 0.01);
	}

	@Test
	public void testCalcolaImpostaOltreTerzaFascia_ConNoTaxAreaPiccola() throws ParseException {
		List<Fascia> listaFasce = List.of( 
				new Fascia(0,     16000, "15%"), 
				new Fascia(16000, 25000, "20%"),
				new Fascia(25000, 46000, "33%"),
				new Fascia(46000, "40%"));
		Fasce fasce = new Fasce("fascia unica di test", listaFasce, 2000);
		CalcolatoreImposta cr = new MyCalcolatoreImposta();
		assertEquals(16000*0.15 + 9000*0.20 + 21000*0.33 + 2000*0.40, cr.calcolaImposta(50000,fasce), 0.01);
	}

}
