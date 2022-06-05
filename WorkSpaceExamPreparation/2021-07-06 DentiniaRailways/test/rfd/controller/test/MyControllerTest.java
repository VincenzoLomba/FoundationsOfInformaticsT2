package rfd.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import rfd.controller.Controller;
import rfd.controller.MyController;
import rfd.model.MyPointOfInterest;
import rfd.model.RailwayLine;
import rfd.model.Route;
import rfd.model.Segment;

class MyControllerTest {


	@Test
	void testOKOneIndirectRoute() {
		RailwayLine lineaBoRn = new RailwayLine(Map.of(
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
		
		Controller controller = new MyController(Set.of(lineaBoMi,lineaBoRn));
		
		assertEquals(List.of(new Route(
								new Segment(new MyPointOfInterest("Modena", "36+932"), new MyPointOfInterest("Bologna Centrale", "0+000")),
								new Segment(new MyPointOfInterest("Bologna Centrale", "0+000"), new MyPointOfInterest("Giulianova", "312+355"))
					)),
				controller.getIndirectRoutes("Modena","Giulianova"));
		
		assertEquals(List.of(new Route(
								new Segment(new MyPointOfInterest("Reggio Emilia", "61+435"), new MyPointOfInterest("Bologna Centrale", "0+000")),
								new Segment(new MyPointOfInterest("Bologna Centrale", "0+000"), new MyPointOfInterest("Giulianova", "312+355"))
					)),
				controller.getIndirectRoutes("Reggio Emilia","Giulianova"));
	
	}
	
	@Test
	void testOKTwoIndirectRoutes() {
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
		
		Controller controller = new MyController(Set.of(lineaBoMi,lineaBoVr,lineaMoVr));
		
		assertTrue(List.of(
					new Route(
								new Segment(new MyPointOfInterest("Reggio Emilia", "61+435"), new MyPointOfInterest("Modena", "36+932")),
								new Segment(new MyPointOfInterest("Modena", "0+000"), new MyPointOfInterest("Verona Porta Nuova", "97+858"))
					),
					new Route(
								new Segment(new MyPointOfInterest("Reggio Emilia", "61+435"), new MyPointOfInterest("Bologna Centrale", "0+000")),
								new Segment(new MyPointOfInterest("Bologna Centrale", "0+000"), new MyPointOfInterest("Verona Porta Nuova", "114+951"))
					)).containsAll(
					controller.getIndirectRoutes("Reggio Emilia","Verona Porta Nuova")));
	
	}
	
	@Test
	void testOKDirectRoute() {
		RailwayLine lineaBoRn = new RailwayLine(Map.of(
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
		
		Controller controller = new MyController(Set.of(lineaBoMi,lineaBoRn));
	
		assertEquals(List.of(new Route(new Segment(new MyPointOfInterest("Giulianova", "312+355"), new MyPointOfInterest("Bologna Centrale", "0+000")))),
				controller.getDirectRoutes("Giulianova", "Bologna Centrale"));
	
		assertEquals(Collections.emptyList(), controller.getDirectRoutes("Giulianova", "Reggio Emilia"));
	}
	
	@Test
	void testOKStationNames() {
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
		
		Controller controller = new MyController(Set.of(lineaBoMi,lineaBoVr,lineaMoVr));
		
		assertEquals(List.of("Bologna Centrale", "Modena", "Parma", "Piacenza", "Reggio Emilia", "Verona Porta Nuova" ),
					controller.getStationNames().stream().sorted().collect(Collectors.toList()));
	}

}
