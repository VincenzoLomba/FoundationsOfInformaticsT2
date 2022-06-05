package rfd.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import rfd.model.RouteFinder;
import rfd.model.MyPointOfInterest;
import rfd.model.RailwayLine;
import rfd.model.Route;

public class RouteFinderTest {

	@Test
	void testOK() {
		RailwayLine lineaBoLe = new RailwayLine(Map.of(
				"Santarcangelo di Romagna", new MyPointOfInterest("Santarcangelo di Romagna", "101+273"),
				"RiminiFiera", new MyPointOfInterest("RiminiFiera", "106+969"),
				"Bologna Centrale", new MyPointOfInterest("Bologna Centrale", "0+000"),
				"Giulianova", new MyPointOfInterest("Giulianova", "312+355")
				),
				new TreeSet<>(Set.of("Bologna Centrale")
				));
		RailwayLine lineaBoMi = new RailwayLine(Map.of(
				"Modena", new MyPointOfInterest("Modena", "36+932"),
				"Reggio Emilia", new MyPointOfInterest("Reggio Emilia", "61+435"),
				"Parma", new MyPointOfInterest("Parma", "89+741"),
				"Bologna Centrale", new MyPointOfInterest("Bologna Centrale", "0+000"),
				"Piacenza", new MyPointOfInterest("Piacenza", "146+823")
				),
				new TreeSet<>(Set.of("Bologna Centrale", "Parma", "Modena")
				));
		
		RouteFinder finder = new RouteFinder(Set.of(lineaBoMi,lineaBoLe)) {
			public List<Route> getRoutes(String from, String to) {
				return Collections.emptyList();
			}
		};
		// test getRoutes
		assertEquals(Collections.emptyList(), finder.getRoutes("Reggio Emilia", "Bologna Centrale"));
		assertEquals(Collections.emptyList(), finder.getRoutes("XXXX", "Bologna Centrale"));
		assertEquals(Collections.emptyList(), finder.getRoutes("Reggio Emilia", "YYY"));
		assertEquals(Collections.emptyList(), finder.getRoutes("Reggio Emilia", ""));
		assertEquals(Collections.emptyList(), finder.getRoutes("", "Bologna Centrale"));
		// the default fake finder never throws exceptions
		assertEquals(Collections.emptyList(), finder.getRoutes("Reggio Emilia", null));
		assertEquals(Collections.emptyList(), finder.getRoutes(null, "Bologna Centrale"));
		// test getLinesAtStation
		assertEquals(Set.of(lineaBoMi), finder.getLinesAtStation("Reggio Emilia"));
		assertEquals(Set.of(lineaBoMi), finder.getLinesAtStation("Piacenza"));
		assertEquals(Set.of(lineaBoLe), finder.getLinesAtStation("RiminiFiera"));
		assertEquals(Set.of(lineaBoMi,lineaBoLe), finder.getLinesAtStation("Bologna Centrale"));
		assertEquals(Collections.emptySet(), finder.getLinesAtStation("XXX"));
		assertEquals(Collections.emptySet(), finder.getLinesAtStation(null));
	}

}
