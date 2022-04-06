package ticketsostaevoluto.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ticketsosta.Tariffa;
import ticketsostaevoluto.ParcometroEvoluto;
import ticketsostaevoluto.TicketEvoluto;

public class ParcometroEvolutoTest {
		
	private Tariffa tariffa[];
		
	@BeforeEach
	public void setUp() {
		// ARGOMENTI: nomeTariffa, tariffaOraria, minutiFranchigia, durataMinima
		tariffa = new Tariffa[] {
		                     new Tariffa("Lunedì",  0.50, 0, 60), // senza franchigia,  minimo 1h
		                     new Tariffa("Martedì", 0.50, 60, 60), // con franchigia 1h, minimo 1h
		                     new Tariffa("Mercoledì", 1.50, 60, 60), // con franchigia 1h, minimo 1h
		                     new Tariffa("Giovedì", 1.50, 60, 60), // con franchigia 1h, minimo 1h
		                     new Tariffa("Venerdì", 0.50, 60, 60), // con franchigia 1h, minimo 1h
		                     new Tariffa("Sabato", 2.50, 0, 30), //  senza franchigia , minimo 30min
		                     new Tariffa("Domenica", 3.50, 0, 30), // senza franchigia , minimo 30min
		};
	}
	
	@Test
	public void testSostaConMinimo1h_1130_1150() {
		ParcometroEvoluto emitter = new ParcometroEvoluto(tariffa);
		LocalDateTime from = LocalDateTime.of(2021, Month.MARCH, 24, 11, 30, 0);
		LocalDateTime to =   LocalDateTime.of(2021, Month.MARCH, 24, 11, 50, 0);
		TicketEvoluto tk = emitter.emettiTicket(from, to);
		assertEquals("1,50\u00A0€", tk.getCostoAsString());
	}

		
	@Test public void testSostaSingoloGiorno730_1500() { 
		ParcometroEvoluto  emitter = new ParcometroEvoluto(tariffa); 
		LocalDateTime from = LocalDateTime.of(2021, Month.MARCH, 24, 7, 30, 0); 
		LocalDateTime to =   LocalDateTime.of(2021, Month.MARCH, 24, 15, 00, 0); 
		TicketEvoluto tk = emitter.emettiTicket(from, to); 
		assertEquals("9,75\u00A0€", tk.getCostoAsString()); 
	}
	 
	  
	@Test public void testSostaSingoloGiorno730_2100() {
		ParcometroEvoluto  emitter = new ParcometroEvoluto(tariffa); 
		LocalDateTime from = LocalDateTime.of(2021, Month.MARCH, 22, 7, 30, 0); 
		LocalDateTime to =   LocalDateTime.of(2021, Month.MARCH, 22, 21, 00, 0); 
		TicketEvoluto tk = emitter.emettiTicket(from, to); 
		assertEquals("6,75\u00A0€", tk.getCostoAsString());
	}
	
  
	@Test public void testSostaSingoloGiornoConFranchigia1h_700_1500() {
		ParcometroEvoluto emitter = new ParcometroEvoluto(tariffa);
		LocalDateTime  from = LocalDateTime.of(2021, Month.MARCH, 23, 7, 00, 0); 
		LocalDateTime to = LocalDateTime.of(2021, Month.MARCH, 23, 15, 00, 0); 
		TicketEvoluto tk = emitter.emettiTicket(from, to); 
		assertEquals("3,50\u00A0€",tk.getCostoAsString());
	}
	 
  
	
	@Test public void testSostaSingoloGiornoConFranchigia1h_730_1500() {
		ParcometroEvoluto emitter = new ParcometroEvoluto(tariffa); 
		LocalDateTime  from = LocalDateTime.of(2021, Month.MARCH, 23, 7, 30, 0);
		LocalDateTime to =  LocalDateTime.of(2021, Month.MARCH, 23, 15, 00, 0); 
		TicketEvoluto tk =  emitter.emettiTicket(from, to); 
		assertEquals("3,25\u00A0€", tk.getCostoAsString());
	}
  
	@Test public void testSostaSingoloGiornoConFranchigia1h730_2100() {
		ParcometroEvoluto emitter = new ParcometroEvoluto(tariffa); 
		LocalDateTime from = LocalDateTime.of(2021, Month.MARCH, 26, 7, 30, 0); 
		LocalDateTime to =   LocalDateTime.of(2021, Month.MARCH, 26, 21, 00, 0); 
		TicketEvoluto tk =  emitter.emettiTicket(from, to); 
		assertEquals("6,25\u00A0€", tk.getCostoAsString());
	}
	  
	
	@Test public void testSostaDueGiorniConFranchigia1h730_2100() {
		ParcometroEvoluto emitter = new ParcometroEvoluto(tariffa); 
		LocalDateTime from = LocalDateTime.of(2021, Month.MARCH, 26, 7, 30, 0); 
		LocalDateTime to =   LocalDateTime.of(2021, Month.MARCH, 27, 21, 00, 0); 
		TicketEvoluto tk =  emitter.emettiTicket(from, to); 
		assertEquals("60,25\u00A0€", tk.getCostoAsString());
	}
	 
	@Test public void testSostaPiuGiorniTraDueSettimane() {
		ParcometroEvoluto emitter = new ParcometroEvoluto(tariffa); 
		LocalDateTime from = LocalDateTime.of(2021, Month.MARCH, 26, 7, 30, 0); 
		LocalDateTime to =   LocalDateTime.of(2021, Month.APRIL, 1, 21, 00, 0); 
		TicketEvoluto tk =  emitter.emettiTicket(from, to); 
		assertEquals("243,25\u00A0€", tk.getCostoAsString());		
	}
	
}
