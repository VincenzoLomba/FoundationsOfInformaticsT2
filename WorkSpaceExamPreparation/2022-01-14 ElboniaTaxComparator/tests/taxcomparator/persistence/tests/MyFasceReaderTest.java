package taxcomparator.persistence.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import taxcomparator.model.*;
import taxcomparator.persistence.*;

public class MyFasceReaderTest {

	@Test
	public void testRead_ShouldSucceed() throws IOException, BadFileFormatException {
		String toRead = "no-tax area:  8.174\n" +
						"0--15.123: 5%\n" +
						"15.123--20.321: 10%\n" +
						"20.321--oltre: 15%\n";

		FasceReader myReader = new MyFasceReader();
		Fasce fasce = myReader.readFasce("fascia di test", new StringReader(toRead));
		
		assertEquals(3, fasce.getFasce().size());
		assertEquals(8174, fasce.getNoTaxArea(), 0.001);
		
		Fascia fascia;
	
		fascia = fasce.getFasce().get(0);
		assertEquals(0, fascia.getMin(), 0);
		assertEquals(15123, fascia.getMax(), 0);
		assertEquals(0.05, fascia.getAliquota(), 0);
		
		fascia = fasce.getFasce().get(1);
		assertEquals(15123, fascia.getMin(), 0);
		assertEquals(20321, fascia.getMax(), 0);
		assertEquals(0.1, fascia.getAliquota(), 0.01);
		
		fascia = fasce.getFasce().get(2);
		assertEquals(20321, fascia.getMin(), 0);
		assertEquals(Double.MAX_VALUE, fascia.getMax(), 0);
		assertEquals(0.15, fascia.getAliquota(), 0);
	}
	
	@Test
	public void testRead_ShouldFailForFewTokens1() throws IOException, BadFileFormatException {
		String toRead = 
				"no-tax area:  8.174\n" +
				"0--15.123: 5%\n" +
				"15.123: 10%\n" +
				"20.321-oltre: 15%\n";
		FasceReader myReader = new MyFasceReader();
		assertThrows(BadFileFormatException.class, () -> myReader.readFasce("fascia di test", new StringReader(toRead)));
	}
	
	@Test
	public void testRead_ShouldFailForFewTokens2() throws IOException, BadFileFormatException {
		String toRead = 
				"no-tax area:  8.174\n" +
				"0--15.123: 5%\n" +
				"15.123--20.321\n" +
				"20.321--oltre: 15%\n";
		FasceReader myReader = new MyFasceReader();
		assertThrows(BadFileFormatException.class, () -> myReader.readFasce("fascia di test", new StringReader(toRead)));
	}
	
	@Test
	public void testRead_ShouldFailForFewTokens3() throws IOException, BadFileFormatException {
		String toRead = 
				"no-tax area:  8.174\n" +
				"0--15.123: 5%\n" +
				"20.321: 10%\n" +
				"20.321--oltre: 15%\n";
		FasceReader myReader = new MyFasceReader();
		assertThrows(BadFileFormatException.class, () -> myReader.readFasce("fascia di test", new StringReader(toRead)));
	}
	
	@Test
	public void testRead_ShouldFailForBadNumber1() throws IOException, BadFileFormatException {
		String toRead = 
				"no-tax area:  8.174\n" +
				"xxx--15.123: 5%\n" +
				"15.123--20.321: 10%\n" +
				"20.321--oltre: 15%\n";
		FasceReader myReader = new MyFasceReader();
		assertThrows(BadFileFormatException.class, () -> myReader.readFasce("fascia di test", new StringReader(toRead)));
	}
	
	@Test
	public void testRead_ShouldFailForBadNumber2() throws IOException, BadFileFormatException {
		String toRead = 
				"no-tax area:  8.174\n" +
				"0--xxx: 5%\n" +
				"15.123--20.321: 10%\n" +
				"20.321--oltre: 15%\n";
		FasceReader myReader = new MyFasceReader();
		assertThrows(BadFileFormatException.class, () -> myReader.readFasce("fascia di test", new StringReader(toRead)));
	}
	
	@Test
	public void testRead_ShouldFailForBadNumber3() throws IOException, BadFileFormatException {
		String toRead = 
				"no-tax area:  8.174\n" +
				"0--15.123: xxx\n" +
				"15.123--20.321: 10%\n" +
				"20.321--oltre: 15%\n";
		FasceReader myReader = new MyFasceReader();
		assertThrows(BadFileFormatException.class, () -> myReader.readFasce("fascia di test", new StringReader(toRead)));
	}
	
	@Test
	public void testRead_ShouldFailForBadNumber4() throws IOException, BadFileFormatException {
		String toRead = 
				"no-tax area:  8.174\n" +
				"0--15.123: 5\n" +
				"15.123--20.321: 10%\n" +
				"20.321--oltre: 15%\n";
		FasceReader myReader = new MyFasceReader();
		assertThrows(BadFileFormatException.class, () -> myReader.readFasce("fascia di test", new StringReader(toRead)));
	}
	
	@Test
	public void testRead_ShouldFailForBadLastWord() throws IOException, BadFileFormatException {
		String toRead = 
				"no-tax area:  8.174\n" +
				"0--15.123: 5\n" +
				"15.123--20.321: 10%\n" +
				"20.321--oltre: 15%\n";
		FasceReader myReader = new MyFasceReader();
		assertThrows(BadFileFormatException.class, () -> myReader.readFasce("fascia di test", new StringReader(toRead)));
	}
}
