package ticketsosta.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import ticketsosta.Ticket;

public class TicketTest {

	@Test
	public void testTicket0830_1500() {
		Ticket tk = new Ticket(LocalTime.of(8,30), LocalTime.of(15,00), 3.50);
		assertEquals(3.50, tk.getCosto(), 0.02);
		// NB: lo spazio prima/dopo il simbolo di valuta � il non-breakable space (codice 160=0xA0), NON lo spazio classico (codice 32=0x20)
		assertEquals("3,50\u00A0€", tk.getCostoAsString());
		System.out.println("Ticket test -- \n" + tk);
	}

}
