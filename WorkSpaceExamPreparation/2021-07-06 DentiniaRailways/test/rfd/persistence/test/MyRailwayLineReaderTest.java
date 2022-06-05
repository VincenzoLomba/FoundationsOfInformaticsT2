package rfd.persistence.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import rfd.model.RailwayLine;
import rfd.persistence.MyRailwayLineReader;

public class MyRailwayLineReaderTest {

	@Test
	void testRailwayLineReaderOK() throws IOException {
		StringReader fakeReader = new StringReader(
				"216+176	Milano Centrale+\r\n" + 
				"212+397	Milano Lambrate+\r\n" + 
				"208+751	Milano Rogoredo+\r\n" + 
				"206+609	San Donato Milanese\r\n" + 
				"204+543	Borgolombardo\r\n" + 
				"202+610	San Giuliano Milanese\r\n" + 
				"197+912	Melegnano\r\n" + 
				"193+916	San Zenone al Lambro\r\n" + 
				"190+409	Tavazzano\r\n" + 
				"182+685	Lodi\r\n" + 
				"170+775	Secugnago\r\n" + 
				"163+832	Casalpusterlengo\r\n" + 
				"158+959	Codogno\r\n" + 
				"154+885	Santo Stefano Lodigiano\r\n" + 
				"146+823	Piacenza\r\n" + 
				"137+995	Pontenure\r\n" + 
				"131+864	Cadeo\r\n" + 
				"125+212	Fiorenzuola\r\n" + 
				"111+754	Fidenza\r\n" + 
				"102+150	Castelguelfo\r\n" + 
				"99+173	Ponte Taro per Medesano\r\n" + 
				"89+741	Parma+\r\n" + 
				"83+477	San Prospero Parmense\r\n" + 
				"78+878	Sant'Ilario d'Enza\r\n" + 
				"61+435	Reggio Emilia\r\n" + 
				"49+585	Rubiera\r\n" + 
				"45+700	Marzaglia\r\n" + 
				"36+932	Modena+\r\n" + 
				"25+008	Castelfranco Emilia\r\n" + 
				"17+130	Samoggia\r\n" + 
				"12+735	Anzola dell'Emilia\r\n" + 
				"0+000	Bologna Centrale+");
		
		RailwayLine line = new MyRailwayLineReader().getRailwayLine(fakeReader);
		
		assertEquals(32,line.getStations().size());
		assertTrue(line.getStations().contains("San Prospero Parmense"));
		assertTrue(line.getStations().contains("Fidenza"));
		assertTrue(line.getStations().contains("Anzola dell'Emilia"));
		assertEquals(6,line.getTransferPoints().size());
		
		assertTrue(line.getTransferPoints().contains("Bologna Centrale"));
		assertTrue(line.getTransferPoints().contains("Milano Centrale"));
		assertTrue(line.getTransferPoints().contains("Milano Lambrate"));
		assertTrue(line.getTransferPoints().contains("Milano Rogoredo"));
		assertTrue(line.getTransferPoints().contains("Parma"));
		assertTrue(line.getTransferPoints().contains("Modena"));
		
		assertEquals("12+735", line.getPointOfInterest("Anzola dell'Emilia").get().getKm());
		assertEquals("99+173", line.getPointOfInterest("Ponte Taro per Medesano").get().getKm());
		assertEquals("137+995", line.getPointOfInterest("Pontenure").get().getKm());
		assertEquals("0+000", line.getPointOfInterest("Bologna Centrale").get().getKm());
	}
	
	@Test
	void testRailwayLineReaderKO_MissingNewLine() throws IOException {
		StringReader fakeReader = new StringReader(
				"216+176	Milano Centrale+" + 
				"212+397	Milano Lambrate+\r\n" + 
				"146+823	Piacenza\r\n" + 
				"111+754	Fidenza\r\n" + 
				"99+173	Ponte Taro per Medesano\r\n" + 
				"89+741	Parma+\r\n" + 
				"83+477	San Prospero Parmense\r\n" + 
				"49+585	Rubiera\r\n" + 
				"36+932	Modena+\r\n" + 
				"17+130	Samoggia\r\n" + 
				"12+735	Anzola dell'Emilia\r\n" + 
				"0+000	Bologna Centrale+");
		assertThrows(IllegalArgumentException.class, () -> new MyRailwayLineReader().getRailwayLine(fakeReader));
	}
	
	@Test
	void testRailwayLineReaderKO_MissingStationName() throws IOException {
		StringReader fakeReader = new StringReader(
				"216+176			+\r\n " + 
				"212+397	Milano Lambrate+\r\n" + 
				"146+823	Piacenza\r\n" + 
				"111+754	Fidenza\r\n" + 
				"99+173	Ponte Taro per Medesano\r\n" + 
				"89+741	Parma+\r\n" + 
				"83+477	San Prospero Parmense\r\n" + 
				"49+585	Rubiera\r\n" + 
				"36+932	Modena+\r\n" + 
				"17+130	Samoggia\r\n" + 
				"12+735	Anzola dell'Emilia\r\n" + 
				"0+000	Bologna Centrale+");
		assertThrows(IllegalArgumentException.class, () -> new MyRailwayLineReader().getRailwayLine(fakeReader));
	}
	
	@Test
	void testRailwayLineReaderKO_MissingProgressiva() throws IOException {
		StringReader fakeReader = new StringReader(
				"	Milano Centrale+\r\n" + 
				"212+397	Milano Lambrate+\r\n" + 
				"146+823	Piacenza\r\n" + 
				"111+754	Fidenza\r\n" + 
				"99+173	Ponte Taro per Medesano\r\n" + 
				"89+741	Parma+\r\n" + 
				"83+477	San Prospero Parmense\r\n" + 
				"49+585	Rubiera\r\n" + 
				"36+932	Modena+\r\n" + 
				"17+130	Samoggia\r\n" + 
				"12+735	Anzola dell'Emilia\r\n" + 
				"0+000	Bologna Centrale+");
		assertThrows(IllegalArgumentException.class, () -> new MyRailwayLineReader().getRailwayLine(fakeReader));
	}
	
	@Test
	void testRailwayLineReaderKO_BadSeparatorBetweenFirstTwoFields() throws IOException {
		StringReader fakeReader = new StringReader(
				"216+176 Milano Centrale+\r\n" + 		// spaces instead of tab 
				"212+397	Milano Lambrate	+\r\n" + 
				"146+823	Piacenza\r\n" + 
				"111+754	Fidenza\r\n" + 
				"99+173	Ponte Taro per Medesano\r\n" + 
				"89+741	Parma+\r\n" + 
				"83+477	San Prospero Parmense\r\n" + 
				"49+585	Rubiera\r\n" + 
				"36+932	Modena+\r\n" + 
				"17+130	Samoggia\r\n" + 
				"12+735	Anzola dell'Emilia\r\n" + 
				"0+000	Bologna Centrale+");
		assertThrows(IllegalArgumentException.class, () -> new MyRailwayLineReader().getRailwayLine(fakeReader));
	}

	@Test
	void testRailwayLineReaderKO_BadSeparatorBetweenSecondTwoFields() throws IOException {
		StringReader fakeReader = new StringReader(
				"216+176	Milano Centrale +\r\n " + 		// spaces between station name and "+" 
				"212+397	Milano Lambrate+\r\n" + 
				"146+823	Piacenza\r\n" + 
				"111+754	Fidenza\r\n" + 
				"99+173	Ponte Taro per Medesano\r\n" + 
				"89+741	Parma+\r\n" + 
				"83+477	San Prospero Parmense\r\n" + 
				"49+585	Rubiera\r\n" + 
				"36+932	Modena+\r\n" + 
				"17+130	Samoggia\r\n" + 
				"12+735	Anzola dell'Emilia\r\n" + 
				"0+000	Bologna Centrale+");
		assertThrows(IllegalArgumentException.class, () -> new MyRailwayLineReader().getRailwayLine(fakeReader));
	}

	@Test
	void testRailwayLineReaderKO_BadFormatInKm() throws IOException {
		StringReader fakeReader = new StringReader(
				"AAA+BBB	Milano Centrale+\r\n " + 		// spaces instead of tab 
				"212+397	Milano Lambrate+\r\n" + 
				"146+823	Piacenza\r\n" + 
				"111+754	Fidenza\r\n" + 
				"99+173	Ponte Taro per Medesano\r\n" + 
				"89+741	Parma+\r\n" + 
				"83+477	San Prospero Parmense\r\n" + 
				"49+585	Rubiera\r\n" + 
				"36+932	Modena+\r\n" + 
				"17+130	Samoggia\r\n" + 
				"12+735	Anzola dell'Emilia\r\n" + 
				"0+000	Bologna Centrale+");
		assertThrows(IllegalArgumentException.class, () -> new MyRailwayLineReader().getRailwayLine(fakeReader));
	}
	
	@Test
	void testRailwayLineReaderKO_BadFormatInStatioName() throws IOException {
		StringReader fakeReader = new StringReader(
				"216+176	222+\r\n" + 		// station name starts with digit 
				"212+397	Milano Lambrate+\r\n" + 
				"146+823	Piacenza\r\n" + 
				"111+754	Fidenza\r\n" + 
				"99+173	Ponte Taro per Medesano\r\n" + 
				"89+741	Parma+\r\n" + 
				"83+477	San Prospero Parmense\r\n" + 
				"49+585	Rubiera\r\n" + 
				"36+932	Modena+\r\n" + 
				"17+130	Samoggia\r\n" + 
				"12+735	Anzola dell'Emilia\r\n" + 
				"0+000	Bologna Centrale+");
		assertThrows(IllegalArgumentException.class, () -> new MyRailwayLineReader().getRailwayLine(fakeReader));
	}

}
