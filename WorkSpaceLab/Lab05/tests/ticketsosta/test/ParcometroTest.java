package ticketsosta.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ticketsosta.Tariffa;
import ticketsosta.Ticket;
import ticketsosta.Parcometro;


public class ParcometroTest {
	
	private Tariffa tariffaH1, tariffaH1f;
	
	@BeforeEach
	public void setUp() {
		// ARGOMENTI: nomeTariffa, tariffaOraria, minutiFranchigia, durataMinima
		tariffaH1  = new Tariffa("H1",  0.50, 0, 60);  // senza franchigia,  minimo 1h
		tariffaH1f = new Tariffa("H1f", 0.50, 60, 60); // con franchigia 1h, minimo 1h
	}
	
	@Test
	public void testSostaConMinimo1h_1130_1150() {
		Parcometro emitter = new Parcometro(tariffaH1);
		Ticket tk = emitter.emettiTicket(LocalTime.of(11,30), LocalTime.of(11,50));
		assertEquals("0,50\u00A0€", tk.getCostoAsString());
	}

	@Test
	public void testSostaSingoloGiorno730_1500() {
		Parcometro emitter = new Parcometro(tariffaH1);
		Ticket tk = emitter.emettiTicket(LocalTime.of(7,30), LocalTime.of(15,00));
		assertEquals("3,75\u00A0€", tk.getCostoAsString());
	}
	
	@Test
	public void testSostaSingoloGiorno730_2100() {
		Parcometro emitter = new Parcometro(tariffaH1);
		Ticket tk = emitter.emettiTicket(LocalTime.of(7,30), LocalTime.of(21,00));
		assertEquals("6,75\u00A0€", tk.getCostoAsString());
	}
	
	@Test
	public void testSostaSingoloGiornoConFranchigia1h_700_1500() {
		Parcometro emitter = new Parcometro(tariffaH1f);
		Ticket tk = emitter.emettiTicket(LocalTime.of(7,00), LocalTime.of(15,00));
		assertEquals("3,50\u00A0€", tk.getCostoAsString());
	}

	@Test
	public void testSostaSingoloGiornoConFranchigia1h_730_1500() {
		Parcometro emitter = new Parcometro(tariffaH1f);
		Ticket tk = emitter.emettiTicket(LocalTime.of(7,30), LocalTime.of(15,00));
		assertEquals("3,25\u00A0€", tk.getCostoAsString());
	}

	@Test
	public void testSostaSingoloGiornoConFranchigia1h730_2100() {
		Parcometro emitter = new Parcometro(tariffaH1f);
		Ticket tk = emitter.emettiTicket(LocalTime.of(7,30), LocalTime.of(21,00));
		assertEquals("6,25\u00A0€", tk.getCostoAsString());
	}

}
