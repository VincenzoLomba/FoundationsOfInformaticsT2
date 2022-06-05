package rfd.persistence.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import rfd.persistence.RailwayLineReader;


public class AllLineNamesTest {

	@Test
	void testFilesIntegrity() throws IOException {
		List<String> lineNames = RailwayLineReader.getAllLineNames(Path.of(".")).get();
		assertEquals(8, lineNames.size());
		assertTrue(lineNames.contains("BS-PR.txt"));
		assertTrue(lineNames.contains("BO-PD.txt"));
		assertTrue(lineNames.contains("BO-LE.txt"));
		assertTrue(lineNames.contains("BO-MI.txt"));
		assertTrue(lineNames.contains("BO-VR.txt"));
		assertTrue(lineNames.contains("MI-VE.txt"));
		assertTrue(lineNames.contains("MO-VR.txt"));
		assertTrue(lineNames.contains("Fumettopoli.txt"));
	}

}
