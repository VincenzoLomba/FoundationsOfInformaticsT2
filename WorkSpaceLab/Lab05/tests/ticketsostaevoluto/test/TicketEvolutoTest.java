package ticketsostaevoluto.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import ticketsostaevoluto.TicketEvoluto;


public class TicketEvolutoTest {

	@Test
	public void testTicket0830_1500() {
		
		LocalDateTime from = LocalDateTime.of(2021, Month.MARCH, 24, 12, 30, 0);
		LocalDateTime to = LocalDateTime.of(2021, Month.MARCH, 24, 13, 30, 0);
		TicketEvoluto tk = new TicketEvoluto(from, to, 3.50);
		assertEquals(3.50, tk.getCosto(), 0.02);
		
		// NB: lo spazio prima/dopo il simbolo di valuta � il non-breakable space (codice 160=0xA0), NON lo spazio classico (codice 32=0x20)
		
		assertEquals("3,50\u00A0€", tk.getCostoAsString());
		System.out.println("<> Ticket test <>\n" + tk);
	}

}
