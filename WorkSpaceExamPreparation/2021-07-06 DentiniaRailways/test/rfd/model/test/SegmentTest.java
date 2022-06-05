package rfd.model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import rfd.model.MyPointOfInterest;
import rfd.model.Segment;

public class SegmentTest {

	@Test
	void test() {
		Segment s1 = new Segment(new MyPointOfInterest("Alpha","102+203"), new MyPointOfInterest("Beta", "122+408"));
		assertEquals(new MyPointOfInterest("Alpha","102+203"), s1.getFrom());
		assertEquals(new MyPointOfInterest("Beta", "122+408"), s1.getTo());
		Segment s2 = new Segment(new MyPointOfInterest("Alpha","102+203"), new MyPointOfInterest("Beta", "122+408"));
		assertEquals(s1,s2);
		assertEquals(20.205, s1.getLength(), 0.001);
		Segment s3 = new Segment(new MyPointOfInterest("Beta", "122+408"),new MyPointOfInterest("Alpha","102+203"));
		assertEquals(20.205, s3.getLength(), 0.001);
		assertNotEquals(s1,s3);
	}

}
