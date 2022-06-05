package rfd.model.test;

import org.junit.jupiter.api.Test;

import rfd.model.MyPointOfInterest;
import rfd.model.RailwayLine;
import rfd.model.Segment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class RailwayLineTest {

	@Test
	public void testOK1() {
		RailwayLine lineaBoRn = new RailwayLine(Map.of(
				"Santarcangelo di Romagna", new MyPointOfInterest("Santarcangelo di Romagna", "101+273"),
				"RiminiFiera", new MyPointOfInterest("RiminiFiera", "106+969"),
				"Bologna Centrale", new MyPointOfInterest("Bologna Centrale", "0+000"),
				"Giulianova", new MyPointOfInterest("Giulianova", "312+355")
				),
				new TreeSet<>(Set.of("Bologna Centrale")
				));
		
		List<String> stations = lineaBoRn.getStations(); // must be in alphabetical order
		assertEquals("Bologna Centrale", stations.get(0));
		assertEquals("Giulianova", 	stations.get(1));
		assertEquals("RiminiFiera", stations.get(2));
		assertEquals("Santarcangelo di Romagna", stations.get(3));
		
		assertTrue(lineaBoRn.getTransferPoints().contains("Bologna Centrale")); 
		
		assertEquals("Santarcangelo di Romagna", lineaBoRn.getPointOfInterest("Santarcangelo di Romagna").get().getStationName());
		assertEquals("101+273", lineaBoRn.getPointOfInterest("Santarcangelo di Romagna").get().getKm());
		assertEquals(101.273, lineaBoRn.getPointOfInterest("Santarcangelo di Romagna").get().getKmAsNum(), 0.001);
		
		assertEquals("RiminiFiera", lineaBoRn.getPointOfInterest("RiminiFiera").get().getStationName());
		assertEquals("106+969", lineaBoRn.getPointOfInterest("RiminiFiera").get().getKm());
		assertEquals(106.969, lineaBoRn.getPointOfInterest("RiminiFiera").get().getKmAsNum(), 0.001);
		
		assertEquals("Bologna Centrale", lineaBoRn.getPointOfInterest("Bologna Centrale").get().getStationName());
		assertEquals("0+000", lineaBoRn.getPointOfInterest("Bologna Centrale").get().getKm());
		assertEquals(0, lineaBoRn.getPointOfInterest("Bologna Centrale").get().getKmAsNum(), 0.001);
		
		assertEquals("Giulianova", lineaBoRn.getPointOfInterest("Giulianova").get().getStationName());
		assertEquals("312+355", lineaBoRn.getPointOfInterest("Giulianova").get().getKm());
		assertEquals(312.355, lineaBoRn.getPointOfInterest("Giulianova").get().getKmAsNum(), 0.001);

		assertEquals(106.969, lineaBoRn.getDistance("Bologna Centrale", "RiminiFiera").getAsDouble());
		assertEquals(106.969, lineaBoRn.getDistance("RiminiFiera","Bologna Centrale").getAsDouble());
		assertEquals(101.273, lineaBoRn.getDistance("Bologna Centrale", "Santarcangelo di Romagna").getAsDouble());
		assertEquals(312.355, lineaBoRn.getDistance("Giulianova","Bologna Centrale").getAsDouble());
		
		assertEquals(101.273, lineaBoRn.getDistance("Santarcangelo di Romagna","Bologna Centrale").getAsDouble());
		assertEquals(101.273, lineaBoRn.getDistance("Bologna Centrale","Santarcangelo di Romagna").getAsDouble());
		assertEquals(  5.696, lineaBoRn.getDistance("Santarcangelo di Romagna", "RiminiFiera").getAsDouble(), 0.001);
		assertEquals(  5.696, lineaBoRn.getDistance("RiminiFiera","Santarcangelo di Romagna").getAsDouble(), 0.001);
		assertEquals(211.082, lineaBoRn.getDistance("Giulianova","Santarcangelo di Romagna").getAsDouble(), 0.001);
		assertEquals(211.082, lineaBoRn.getDistance("Santarcangelo di Romagna","Giulianova").getAsDouble(), 0.001);
		
		assertEquals(new Segment(new MyPointOfInterest("RiminiFiera", "106+969"), new MyPointOfInterest("Bologna Centrale", "0+000")), 
				 lineaBoRn.getSegment("RiminiFiera","Bologna Centrale").get());
		assertEquals(new Segment(new MyPointOfInterest("Bologna Centrale", "0+000"),new MyPointOfInterest("RiminiFiera", "106+969")), 
				 lineaBoRn.getSegment("Bologna Centrale","RiminiFiera").get());
		assertEquals(new Segment(new MyPointOfInterest("Giulianova", "312+355"), new MyPointOfInterest("Bologna Centrale", "0+000")), 
				 lineaBoRn.getSegment("Giulianova","Bologna Centrale").get());
		assertEquals(new Segment(new MyPointOfInterest("Giulianova", "312+355"), new MyPointOfInterest("Santarcangelo di Romagna", "101+273")), 
				 lineaBoRn.getSegment("Giulianova","Santarcangelo di Romagna").get());
		
	}

}
