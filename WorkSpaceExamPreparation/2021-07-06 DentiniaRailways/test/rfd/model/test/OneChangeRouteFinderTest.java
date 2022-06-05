package rfd.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import rfd.model.OneChangeRouteFinder;
import rfd.model.RouteFinder;
import rfd.model.MyPointOfInterest;
import rfd.model.RailwayLine;
import rfd.model.Route;
import rfd.model.Segment;

public class OneChangeRouteFinderTest {

	@Test
	void testOKOneRoute() {
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
		
		RouteFinder finder = new OneChangeRouteFinder(Set.of(lineaBoMi,lineaBoLe));
		
		assertEquals(List.of(new Route(
								new Segment(new MyPointOfInterest("Modena", "36+932"), new MyPointOfInterest("Bologna Centrale", "0+000")),
								new Segment(new MyPointOfInterest("Bologna Centrale", "0+000"), new MyPointOfInterest("Giulianova", "312+355"))
					)),
					finder.getRoutes("Modena","Giulianova"));
		
		assertEquals(List.of(new Route(
								new Segment(new MyPointOfInterest("Reggio Emilia", "61+435"), new MyPointOfInterest("Bologna Centrale", "0+000")),
								new Segment(new MyPointOfInterest("Bologna Centrale", "0+000"), new MyPointOfInterest("Giulianova", "312+355"))
					)),
					finder.getRoutes("Reggio Emilia","Giulianova"));
	
	}
	
	@Test
	void testOKTwoRoutes() {
		RailwayLine lineaBoVr = new RailwayLine(Map.of(
				"Verona Porta Nuova", new MyPointOfInterest("Verona Porta Nuova", "114+951"),
				"Bologna Centrale", new MyPointOfInterest("Bologna Centrale", "0+000")
				),
				new TreeSet<>(Set.of("Bologna Centrale", "Verona Porta Nuova")
				));
		RailwayLine lineaMoVr = new RailwayLine(Map.of(
				"Verona Porta Nuova", new MyPointOfInterest("Verona Porta Nuova", "97+858"),
				"Modena", new MyPointOfInterest("Modena", "0+000")
				),
				new TreeSet<>(Set.of("Modena", "Verona Porta Nuova")
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
		
		RouteFinder finder = new OneChangeRouteFinder(Set.of(lineaBoMi,lineaBoVr,lineaMoVr));
		
		assertTrue(List.of(
					new Route(
								new Segment(new MyPointOfInterest("Reggio Emilia", "61+435"), new MyPointOfInterest("Modena", "36+932")),
								new Segment(new MyPointOfInterest("Modena", "0+000"), new MyPointOfInterest("Verona Porta Nuova", "97+858"))
					),
					new Route(
								new Segment(new MyPointOfInterest("Reggio Emilia", "61+435"), new MyPointOfInterest("Bologna Centrale", "0+000")),
								new Segment(new MyPointOfInterest("Bologna Centrale", "0+000"), new MyPointOfInterest("Verona Porta Nuova", "114+951"))
					)).containsAll(
					finder.getRoutes("Reggio Emilia","Verona Porta Nuova")));
		
		assertEquals(Set.of(lineaBoMi), finder.getLinesAtStation("Reggio Emilia"));
		assertEquals(Set.of(lineaBoMi), finder.getLinesAtStation("Piacenza"));
		assertEquals(Set.of(lineaBoMi,lineaBoVr), finder.getLinesAtStation("Bologna Centrale"));
		assertEquals(Set.of(lineaBoMi,lineaMoVr), finder.getLinesAtStation("Modena"));
		assertEquals(Set.of(lineaMoVr,lineaBoVr), finder.getLinesAtStation("Verona Porta Nuova"));
		assertEquals(Collections.emptySet(), finder.getLinesAtStation("XXX"));
		assertEquals(Collections.emptySet(), finder.getLinesAtStation(null));
	}
	
	@Test
	void testOKSpecialCases() {
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
		
		RouteFinder finder = new OneChangeRouteFinder(Set.of(lineaBoMi,lineaBoLe));
		
		assertEquals(Collections.emptyList(),
					finder.getRoutes("Modena","Modena"));	
	}

}
