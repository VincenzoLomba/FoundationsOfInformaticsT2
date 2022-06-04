package edlift.persistence.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import edlift.model.*;
import edlift.persistence.*;


public class MyInstallationReaderTests {
		
	@Test
	public void testOK_oneMultiInstallation() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";
		
		MyInstallationReader reader = new MyInstallationReader();

		List<Installation> result = reader.readAll(new StringReader(toRead));
		assertEquals(1, result.size());

		Installation e = result.get(0);
		assertTrue("Hotel Miralago".equalsIgnoreCase(e.getName()));
		assertEquals(-2, e.getLift().getMinFloor());
		assertEquals( 4, e.getLift().getMaxFloor());
		assertEquals(FakeMultiFloorLift.class, e.getLift().getClass());
	}
	
	@Test
	public void testOK_oneMultiInstallationWithEmptyLinesAtEnd() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s"
				+ "\n"
				+ "\n";
		
		MyInstallationReader reader = new MyInstallationReader();

		List<Installation> result = reader.readAll(new StringReader(toRead));
		assertEquals(1, result.size());

		Installation e = result.get(0);
		assertTrue("Hotel Miralago".equalsIgnoreCase(e.getName()));
		assertEquals(-2, e.getLift().getMinFloor());
		assertEquals( 4, e.getLift().getMaxFloor());
		assertEquals(FakeMultiFloorLift.class, e.getLift().getClass());
	}

	@Test
	public void testOK_oneBasicInstallation() throws BadFileFormatException, IOException {
		String toRead = 
				    "ASCENSORE Condominio I Girasoli\r\n"
				  + "TIPO BASIC A 8 PIANI da -1 a 6\r\n"
				  + "VELOCITA 0.9 m/s";
		
		MyInstallationReader reader = new MyInstallationReader();

		List<Installation> result = reader.readAll(new StringReader(toRead));
		assertEquals(1, result.size());

		Installation e = result.get(0);
		assertTrue("Condominio I Girasoli".equalsIgnoreCase(e.getName()));
		assertEquals(-1, e.getLift().getMinFloor());
		assertEquals( 6, e.getLift().getMaxFloor());
		assertEquals(BasicLift.class, e.getLift().getClass());
	}

	@Test
	public void testOK_oneHealthyInstallation() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE SALUTISTA\r\n"
				+ "TIPO HEALTHY A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";
		
		MyInstallationReader reader = new MyInstallationReader();

		List<Installation> result = reader.readAll(new StringReader(toRead));
		assertEquals(1, result.size());

		Installation e = result.get(0);
		assertTrue("Salutista".equalsIgnoreCase(e.getName()));
		assertEquals(-2, e.getLift().getMinFloor());
		assertEquals( 4, e.getLift().getMaxFloor());
		assertEquals(HealthyLift.class, e.getLift().getClass());
	}

	@Test
	public void testOK_TwoInstallations_MultiBasic() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s\n"
			    + "ASCENSORE Condominio I Girasoli\r\n"
			    + "TIPO BASIC A 8 PIANI da -1 a 6\r\n"
			    + "VELOCITA 0.9 m/s";
		
		MyInstallationReader reader = new MyInstallationReader();

		List<Installation> result = reader.readAll(new StringReader(toRead));
		assertEquals(2, result.size());

		Installation e = result.get(0);
		assertTrue("Hotel Miralago".equalsIgnoreCase(e.getName()));
		assertEquals(-2, e.getLift().getMinFloor());
		assertEquals( 4, e.getLift().getMaxFloor());
		assertEquals(FakeMultiFloorLift.class, e.getLift().getClass());
		e = result.get(1);
		assertTrue("Condominio I Girasoli".equalsIgnoreCase(e.getName()));
		assertEquals(-1, e.getLift().getMinFloor());
		assertEquals( 6, e.getLift().getMaxFloor());
		assertEquals(BasicLift.class, e.getLift().getClass());
	}
	
	@Test
	public void testOK_TwoInstallations_WithEmptyLinesInBetween() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s\n"
				+ "\n"
				+ "\n"
			    + "ASCENSORE Condominio I Girasoli\r\n"
			    + "TIPO BASIC A 8 PIANI da -1 a 6\r\n"
			    + "VELOCITA 0.9 m/s";

		MyInstallationReader reader = new MyInstallationReader();

		List<Installation> result = reader.readAll(new StringReader(toRead));
		assertEquals(2, result.size());

		Installation e = result.get(0);
		assertTrue("Hotel Miralago".equalsIgnoreCase(e.getName()));
		assertEquals(-2, e.getLift().getMinFloor());
		assertEquals( 4, e.getLift().getMaxFloor());
		assertEquals(FakeMultiFloorLift.class, e.getLift().getClass());
		e = result.get(1);
		assertTrue("Condominio I Girasoli".equalsIgnoreCase(e.getName()));
		assertEquals(-1, e.getLift().getMinFloor());
		assertEquals( 6, e.getLift().getMaxFloor());
		assertEquals(BasicLift.class, e.getLift().getClass());
	}

	@Test
	public void testOK_TwoInstallations_BasicMulti() throws BadFileFormatException, IOException {
		String toRead = 
			      "ASCENSORE Condominio I Girasoli\r\n"
			    + "TIPO BASIC A 8 PIANI da -1 a 6\r\n"
			    + "VELOCITA 0.9 m/s\n"
				+ "ascensoRE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s\n";
		
		MyInstallationReader reader = new MyInstallationReader();

		List<Installation> result = reader.readAll(new StringReader(toRead));
		assertEquals(2, result.size());

		Installation e = result.get(1);
		assertTrue("Hotel Miralago".equalsIgnoreCase(e.getName()));
		assertEquals(-2, e.getLift().getMinFloor());
		assertEquals( 4, e.getLift().getMaxFloor());
		assertEquals(FakeMultiFloorLift.class, e.getLift().getClass());
		e = result.get(0);
		assertTrue("Condominio I Girasoli".equalsIgnoreCase(e.getName()));
		assertEquals(-1, e.getLift().getMinFloor());
		assertEquals( 6, e.getLift().getMaxFloor());
		assertEquals(BasicLift.class, e.getLift().getClass());
	}

	@Test
	public void testOK_TwoInstallations_BasicHealthy() throws BadFileFormatException, IOException {
		String toRead = 
			      "ASCENSORE Condominio I Girasoli\r\n"
			    + "TIPO BASIC A 8 PIANI da -1 a 6\r\n"
			    + "VELOCITA 0.9 m/s\n"
				+ "ascensoRE SALUTista\r\n"
				+ "TIPO HEALTHY A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s\n";
		
		MyInstallationReader reader = new MyInstallationReader();

		List<Installation> result = reader.readAll(new StringReader(toRead));
		assertEquals(2, result.size());

		Installation e = result.get(1);
		assertTrue("Salutista".equalsIgnoreCase(e.getName()));
		assertEquals(-2, e.getLift().getMinFloor());
		assertEquals( 4, e.getLift().getMaxFloor());
		assertEquals(HealthyLift.class, e.getLift().getClass());
		e = result.get(0);
		assertTrue("Condominio I Girasoli".equalsIgnoreCase(e.getName()));
		assertEquals(-1, e.getLift().getMinFloor());
		assertEquals( 6, e.getLift().getMaxFloor());
		assertEquals(BasicLift.class, e.getLift().getClass());
	}

	@Test
	public void testKO_missingKeyWord_Ascensore() throws BadFileFormatException, IOException {
		String toRead = 
				  "HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongKeyWord_Ascensore() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCCENSS HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_missingKeyWord_Tipo() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongKeyWord_Tipo() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "type MULTI A 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}
	
	@Test
	public void testKO_missingMode() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongMode() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO mmm 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_missingNumFloors() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongNumFloors() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI k PIANI da -2 a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}
	
	@Test
	public void testKO_missingMinFloors() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI 7 PIANI da a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongMinFloors() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI 7 PIANI da UU a 4\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_missingMaxFloors() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI 7 PIANI da -2 a\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongMaxFloors() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI 7 PIANI da -2 a kkk\r\n"
				+ "VELOCITA 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongKeyWord_Velocita() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI A 7 PIANI da -2 a 4\r\n"
				+ "speed 1 m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_missingSpeed() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongSpeed() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA Z m/s";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

	@Test
	public void testKO_wrongKeyword_ms() throws BadFileFormatException, IOException {
		String toRead = 
				  "ASCENSORE HOTEL MIRALAGO\r\n"
				+ "TIPO MULTI 7 PIANI da -2 a 4\r\n"
				+ "VELOCITA 0.9 glu";		
		MyInstallationReader reader = new MyInstallationReader();
		assertThrows(BadFileFormatException.class, ()-> reader.readAll(new StringReader(toRead)));
	}

}
