package bussy.ui.controller.test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import bussy.model.Linea;
import bussy.persistence.BadFileFormatException;
import bussy.persistence.TransportLinesReader;
import bussy.ui.controller.Controller;
import javafx.collections.ObservableList;

public class ControllerTest {
	
	private BufferedReader fakeRdr;
	
	@Test
	public void testTransportLinesReaderOK() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40, Porta San Mamolo \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------\r\n" + 
				"Linea 33\r\n" + 
				" 0, 47, Porta Saragozza - Villa Cassarini\r\n" + 
				" 2, 49, Aldini\r\n" + 
				" 3, 45, Petrarca\r\n" + 
				" 5, 43, Porta San Mamolo\r\n" + 
				"26, 09, Stazione Centrale\r\n" + 
				"38, 47, Porta Saragozza - Villa Cassarini\r\n" + 
				"--------------------------\r\n" + 
				"Linea M1\r\n" + 
				"0, 40, Porta San Mamolo\r\n" + 
				"3, 803, Tribunale\r\n" + 
				"5, 701, Piazza Malpighi\r\n" + 
				"7, 452, Marconi\r\n" + 
				"10, 474, Stazione Centrale\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		Map<String,Linea> linee = fltRdr.readTransportLines(fakeRdr);// throws IOException, BadFileFormatException
		assertEquals(3, linee.size());
		Controller controller = new Controller(linee);
		assertEquals(linee, controller.getLinee());
		//	for (String s : linee.keySet()) System.out.println(linee.get(s));
		ObservableList<String> nomiFermate = controller.getNomiFermate();
		assertEquals(List.of("Aldini", "Marconi", "Petrarca", "Piazza Malpighi", "Porta San Mamolo", 
							 "Porta Saragozza - Villa Cassarini", "Stazione Centrale", "Tribunale"),
					 nomiFermate);
	}
	
	// non si effettua il test di cercaPercorsi in quanto si tratta di banale remapping sul metodo omonimo di Cercatore
}

