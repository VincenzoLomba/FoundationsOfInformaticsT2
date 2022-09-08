package agenda.persistence.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import agenda.persistence.TextContactsPersister;
import agenda.model.Contact;
import agenda.persistence.BadFileFormatException;
import agenda.persistence.FileUtils;

public class TextContactsPersisterTest {
	TextContactsPersister contactReader;

	@BeforeEach
	public void setUp() throws Exception {
		contactReader= new TextContactsPersister();
		
	}

	@Test
	public void testLoad() {
		String str = "StartContact"+FileUtils.NEWLINE + 
				"Enrique;Dent" +FileUtils.NEWLINE +
				"Phone;Cell.;323233223"+FileUtils.NEWLINE + 
				"EMail;@Job;enrico.denti@unibo.it"+FileUtils.NEWLINE +
				"Address;Job;Viale del Risorgimento;2;40100;Bologna;Italia"+FileUtils.NEWLINE +
				"Address;Job;Viale dell'Imperatore;100;40121;ReggioEmilia;Italia"+FileUtils.NEWLINE + 
				"EndContact"+FileUtils.NEWLINE +
				"StartContact"+FileUtils.NEWLINE +  
				"Amber;Molesque"+FileUtils.NEWLINE + 
				"Phone;Cell.;343344334"+FileUtils.NEWLINE + 
				"Phone;Home;0515445544"+FileUtils.NEWLINE + 
				"Address;Job;Viale del Risorgimento;2;40100;Bologna;Italia"+FileUtils.NEWLINE + 
				"Address;Home;Giardini Magherita Violetta;99;40100;Bologna;Italia"+FileUtils.NEWLINE + 
				"EndContact"+FileUtils.NEWLINE +
				"StartContact"+FileUtils.NEWLINE + 
				"Robby;Calegax"+FileUtils.NEWLINE + 
				"Phone;Cell.;355667788"+FileUtils.NEWLINE + 
				"Phone;Home;051552598"+FileUtils.NEWLINE + 
				"Address;Job;Viale del Risorgimento;2;40100;Bologna;Italia"+FileUtils.NEWLINE + 
				"Address;Home;Giardini del Pesco in fiore;7;40100;Bologna;Italia"+FileUtils.NEWLINE + 
				"EndContact"+FileUtils.NEWLINE;
		Reader  baseReader = new StringReader(str);
		try {
			List<Contact> contatti= contactReader.load(baseReader);
			assertEquals(3, contatti.size());
			
		} catch (IOException | BadFileFormatException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testNoReader() {
		 Exception exception = assertThrows(IOException.class, () ->
		  contactReader.load(null));
	      assertEquals("reader null", exception.getMessage());
	
	}
	
	@Test
	public void testNoStart() {
		String str = "Enrique;Dent" +FileUtils.NEWLINE +
				"Phone;Cell.;323233223"+FileUtils.NEWLINE + 
				"EMail;@Job;enrico.denti@unibo.it"+FileUtils.NEWLINE +
				"Address;Job;Viale del Risorgimento;2;40100;Bologna;Italia"+FileUtils.NEWLINE +
				"Address;Job;Viale dell'Imperatore;100;40121;ReggioEmilia;Italia"+FileUtils.NEWLINE + 
				"EndContact"+FileUtils.NEWLINE +
				"StartContact"+FileUtils.NEWLINE +  
				"Amber;Molesque"+FileUtils.NEWLINE + 
				"Phone;Cell.;343344334"+FileUtils.NEWLINE + 
				"Phone;Home;0515445544"+FileUtils.NEWLINE + 
				"Address;Job;Viale del Risorgimento;2;40100;Bologna;Italia"+FileUtils.NEWLINE + 
				"Address;Home;Giardini Magherita Violetta;99;40100;Bologna;Italia"+FileUtils.NEWLINE + 
				"EndContact"+FileUtils.NEWLINE +
				"StartContact"+FileUtils.NEWLINE + 
				"Robby;Calegax"+FileUtils.NEWLINE + 
				"Phone;Cell.;355667788"+FileUtils.NEWLINE + 
				"Phone;Home;051552598"+FileUtils.NEWLINE + 
				"Address;Job;Viale del Risorgimento;2;40100;Bologna;Italia"+FileUtils.NEWLINE + 
				"Address;Home;Giardini del Pesco in fiore;7;40100;Bologna;Italia"+FileUtils.NEWLINE + 
				"EndContact"+FileUtils.NEWLINE;
		Reader  baseReader = new StringReader(str);		
		  Exception exception = assertThrows(BadFileFormatException.class, () ->
		  contactReader.load(baseReader));
	      assertEquals("StartContact expected", exception.getMessage());
		
	}
	
	@Test
	public void testDetUnKnown() {
		String str = "StartContact"+FileUtils.NEWLINE +
				"Enrique;Dent" +FileUtils.NEWLINE +
				"Telefono;Cell.;323233223"+FileUtils.NEWLINE + 
				"EMail;@Job;enrico.denti@unibo.it"+FileUtils.NEWLINE +
				"Address;Job;Viale del Risorgimento;2;40100;Bologna;Italia"+FileUtils.NEWLINE +
				"Address;Job;Viale dell'Imperatore;100;40121;ReggioEmilia;Italia"+FileUtils.NEWLINE + 
				"EndContact"+FileUtils.NEWLINE;
				
		Reader  baseReader = new StringReader(str);
		Exception exception = assertThrows(BadFileFormatException.class, () ->
		  contactReader.load(baseReader));
	      assertEquals("Unknown Detail Type", exception.getMessage());
	}	


	 
	
	@Test
	public void testNoDet() {
		String str = "StartContact"+FileUtils.NEWLINE + 
				"Enrique;Dent" +FileUtils.NEWLINE;
				
		Reader  baseReader = new StringReader(str);
		Exception exception = assertThrows(BadFileFormatException.class, () ->
		  contactReader.load(baseReader));
	      assertEquals("Detail or EndContact expected", exception.getMessage());
	}	


}
