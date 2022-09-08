package minesweeper.tests.persistence;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.Test;

import minesweeper.persistence.BadFileFormatException;
import minesweeper.persistence.ConfigReader;
import minesweeper.persistence.MyConfigReader;

public class MyConfigReaderTest {
	
	@Test
	public void testOK1() throws BadFileFormatException {
		String fakeFile = "MINES:  3\r\n" +	"Size:  6";
		StringReader reader = new StringReader(fakeFile);
		ConfigReader configReader = new MyConfigReader(reader);
		assertEquals(6, configReader.getSize());
		assertEquals(3, configReader.getMinesNumber());
	}
	
	@Test
	public void testOK2() throws BadFileFormatException {
		String fakeFile = "Mines: 3\r\n" +	"Size:  6";
		StringReader reader = new StringReader(fakeFile);
		ConfigReader configReader = new MyConfigReader(reader);
		assertEquals(6, configReader.getSize());
		assertEquals(3, configReader.getMinesNumber());
	}

	@Test
	public void testOK3() throws BadFileFormatException {
		String fakeFile = "mines:3\r\n" +	"size:  5";
		StringReader reader = new StringReader(fakeFile);
		ConfigReader configReader = new MyConfigReader(reader);
		assertEquals(5, configReader.getSize());
		assertEquals(3, configReader.getMinesNumber());
	}
	
	@Test
	public void testKO_missingFirstNumber() {
		String fakeFile = "mines:XX\r\n" +	"size:  5";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

	@Test
	public void testKO_missingSecondNumber() {
		String fakeFile = "mines: 3\r\n" +	"size: UU";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

	@Test
	public void testKO_wrongFirstWord() {
		String fakeFile = "Mine: 3\r\n" +	"size:  5";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

	@Test
	public void testKO_wrongSecondWord() {
		String fakeFile = "Mines: 3\r\n" +	"Dim:  5";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

	@Test
	public void testKO_missingFirstWord() {
		String fakeFile = ": 3\r\n" +	"size:  5";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

	@Test
	public void testKO_missingSecondWord() {
		String fakeFile = "mines: 3\r\n" +	":  5";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

	@Test
	public void testKO_missingFirstSeparator() {
		String fakeFile = "mines 3\r\n" +	"size : 5";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

	@Test
	public void testKO_missingSecondSeparator() {
		String fakeFile = "mines: 3\r\n" +	"size  5";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

	@Test
	public void testKO_missingNewline() {
		String fakeFile = "mines: 3 " +	"size: 5";
		StringReader reader = new StringReader(fakeFile);		
		assertThrows(BadFileFormatException.class, () -> new MyConfigReader(reader));
	}

}
