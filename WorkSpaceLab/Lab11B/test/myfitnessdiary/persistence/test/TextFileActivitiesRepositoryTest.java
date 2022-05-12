package myfitnessdiary.persistence.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;



import myfitnessdiary.model.Activity;
import myfitnessdiary.model.Intensity;
import myfitnessdiary.persistence.BadFileFormatException;
import myfitnessdiary.persistence.TextFileActivityRepository;

public class TextFileActivitiesRepositoryTest {

	@Test
	public void testTextFileActivitiesRepository() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("AS1--1 2 3\nAS2-- 4 5 6\nAS3-- 7 8 9\n");
		new TextFileActivityRepository(mr);
	}

	@Test
	public void testTextFileActivitiesRepositoryIOEx() throws IOException, BadFileFormatException {
		Reader mr = null;
		assertThrows(BadFileFormatException.class, () ->
		new TextFileActivityRepository(mr));
	}

	@Test
	public void testTextParamError() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("AS1--1 * 3\nAS2-- 4 * 6\nAS3-- 7 8 9\n");
		assertThrows(BadFileFormatException.class, () ->new TextFileActivityRepository(mr));
	}

	/*
	 * @Test public void testAddActivity() throws IOException,
	 * BadFileFormatException { Reader mr = new
	 * StringReader("AS1--1 2 3\nAS2-- 4 5 6\nAS3-- 7 8 9\n");
	 * TextFileActivitiesRepository repo= new TextFileActivitiesRepository(mr);
	 * assertEquals(3, repo.getActivitiesName().size()); Activity act = new
	 * Activity("AS4"); act.insertCalories(Intensity.LOW, 10);
	 * act.insertCalories(Intensity.MEDIUM, 11);
	 * act.insertCalories(Intensity.HIGH, 12); repo.addActivity(act);
	 * assertEquals(4, repo.getActivitiesName().size()); }
	 */

	@Test
	public void testGetActivity() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("AS1--1 2 3\nAS2-- 4 5 6\nAS3-- 7 8 9\n");
		TextFileActivityRepository repo = new TextFileActivityRepository(mr);
		Activity a = repo.getActivity("AS8");
		assertNull(a);
		a = repo.getActivity("AS1");
		assertNotNull(a);
		assertEquals(1, a.getCalories(Intensity.LOW));
		assertEquals(2, a.getCalories(Intensity.MEDIUM));
		assertEquals(3, a.getCalories(Intensity.HIGH));
	}

	@Test
	public void testGetActivitiesName() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("AS1--1 2 3\nAS2-- 4 5 6\nAS3-- 7 8 9\n");
		TextFileActivityRepository repo = new TextFileActivityRepository(mr);
		Set<String> names = repo.getActivityNames();
		assertTrue(names.contains("AS1"));
		assertTrue(names.contains("AS2"));
		assertTrue(names.contains("AS3"));

	}

}
