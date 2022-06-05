package rfd.model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import rfd.model.MyPointOfInterest;
import rfd.model.Route;
import rfd.model.Segment;

public class RouteTest {

	@Test
	void testOK() {
		Segment s1 = new Segment(new MyPointOfInterest("Alpha","102+203"), new MyPointOfInterest("Beta", "122+408"));
		Segment s2 = new Segment(new MyPointOfInterest("Beta", "122+408"), new MyPointOfInterest("Gamma","126+203"));
		Route rABC = new Route();
		rABC.add(s1); rABC.add(s2);
		assertEquals(24.00, rABC.getLength(), 0.001);
		Segment s3 = new Segment(new MyPointOfInterest("Gamma","126+203"), new MyPointOfInterest("Delta","226+208"));
		rABC.add(s3);
		assertEquals(124.005, rABC.getLength(), 0.001);
	}
	
	@Test
	void testOK2() {
		Route rABC = new Route();
		assertEquals(0, rABC.getLength(), 0.001);
	}
	
	@Test
	void testOK3() {
		Segment s1 = new Segment(new MyPointOfInterest("Alpha","102+203"), new MyPointOfInterest("Beta", "122+408"));
		Route rABC = new Route(s1);
		assertEquals(20.205, rABC.getLength(), 0.001);
		Segment s2 = new Segment(new MyPointOfInterest("Beta", "122+408"), new MyPointOfInterest("Gamma","126+203"));
		rABC.add(s2);
		assertEquals(24.00, rABC.getLength(), 0.001);
	}
	
	@Test
	void testOK4() {
		Segment s1 = new Segment(new MyPointOfInterest("Alpha","102+203"), new MyPointOfInterest("Beta", "122+408"));
		Segment s2 = new Segment(new MyPointOfInterest("Beta", "122+408"), new MyPointOfInterest("Gamma","126+203"));
		Segment s3 = new Segment(new MyPointOfInterest("Gamma","126+203"), new MyPointOfInterest("Delta","226+208"));
		Route rABC = new Route(s1,s2,s3);
		assertEquals(124.005, rABC.getLength(), 0.001);
	}
	
	@Test
	void testK0_notAdjacent() {
		Segment s1 = new Segment(new MyPointOfInterest("Alpha","102+203"), new MyPointOfInterest("Beta", "122+408"));
		Route rABC = new Route();
		rABC.add(s1); 
		assertEquals(20.205, rABC.getLength(), 0.001);
		Segment s3 = new Segment(new MyPointOfInterest("Gamma","126+203"), new MyPointOfInterest("Delta","226+208"));
		assertThrows(IllegalArgumentException.class, () -> rABC.add(s3));
	}

}
