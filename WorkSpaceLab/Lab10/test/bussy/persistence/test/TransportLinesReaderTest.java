package bussy.persistence.test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bussy.model.Linea;
import bussy.persistence.BadFileFormatException;
import bussy.persistence.TransportLinesReader;

public class TransportLinesReaderTest {
	
	private BufferedReader fakeRdr;

	@BeforeEach
	void setup() {
	}
	
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
			for (String s : linee.keySet()) System.out.println(linee.get(s));
	}
	
	@Test
	public void testTransportLinesReaderOK_shortFinalLine() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40,  Porta San Mamolo\r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		Map<String,Linea> linee = fltRdr.readTransportLines(fakeRdr);// throws IOException, BadFileFormatException
		assertEquals(1, linee.size());
	}
	
	@Test
	public void testTransportLinesReaderKO_noLinea() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("32\r\n" + 
				"0,  40, Porta San Mamolo \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}
	
	@Test
	public void testTransportLinesReaderKO_badLinea() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("LINEA 32\r\n" + 
				"0,  40, Porta San Mamolo \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}
	
	@Test
	public void testTransportLinesReaderKO_noIdLinea() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea \r\n" + 
				"0,  40, Porta San Mamolo \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}

	@Test
	public void testTransportLinesReaderKO_noTimeInFermata() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				",  40, Porta San Mamolo \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}

	@Test
	public void testTransportLinesReaderKO_norTimeNorCommaInFermata() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"40, Porta San Mamolo \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}

	@Test
	public void testTransportLinesReaderKO_noIdFermata() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  , Porta San Mamolo \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}

	@Test
	public void testTransportLinesReaderKO_norIdFermataNorComma() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  Porta San Mamolo \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}

	@Test
	public void testTransportLinesReaderKO_noNameFermata() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40, \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}
	
	@Test
	public void testTransportLinesReaderKO_noNameFermataNorComma() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40 \r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}
	
	@Test
	public void testTransportLinesReaderKO_noCommaBeforeNameFermata() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40  Porta San Mamolo\r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}

	@Test
	public void testTransportLinesReaderKO_noCommaBeforeIdFermata() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0  40,  Porta San Mamolo\r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}
	
	@Test
	public void testTransportLinesReaderKO_noCommasAtAll() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0  40  Porta San Mamolo\r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}
	
	@Test
	public void testTransportLinesReaderKO_noNewLine() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40,  Porta San Mamolo" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"--------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}
	
	@Test
	public void testTransportLinesReaderKO_noFinalLine() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40  Porta San Mamolo\r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}

	@Test
	public void testTransportLinesReaderKO_badFinalLine() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40  Porta San Mamolo\r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"uahuah------------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}
	
	@Test
	public void testTransportLinesReaderKO_tooShortFinalLine() throws IOException, BadFileFormatException {
		fakeRdr = new BufferedReader(new StringReader("Linea 32\r\n" + 
				"0,  40  Porta San Mamolo\r\n" + 
				"3,  42, Aldini\r\n" + 
				"5,  44, Porta Saragozza - Villa Cassarini\r\n" + 
				"17, 16, Stazione Centrale\r\n" + 
				"38, 40, Porta San Mamolo\r\n" + 
				"-u-----------------------"));		
		TransportLinesReader fltRdr = TransportLinesReader.of();
		assertThrows(BadFileFormatException.class, () -> fltRdr.readTransportLines(fakeRdr));
	}

}

