package gasforlife.persistence.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gasforlife.model.Flat;
import gasforlife.persistence.BadFileFormatException;
import gasforlife.persistence.FlatReader;
import gasforlife.persistence.MyFlatReader;


public class MyFlatReaderTest {

	@Test
	void testOK() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A  100  owner1\r\n" + 
				"1-1B  100  owner2\r\n" +
				"1-2A  80   owner3\r\n" +
				"1-2B  80   owner4\r\n" +
				"1-3A  80   owner5\r\n" +
				"1-3B  80   owner6\r\n" +
				"1-4A  90   owner7\r\n" +
				"1-4B  90   owner8\r\n";
		StringReader reader = new StringReader(fakeFile);
		FlatReader flatReader = new MyFlatReader(reader);
		Map<String, Flat> map = flatReader.getItems(); 
		assertEquals(8, map.entrySet().size());
		assertTrue(map.containsKey("1-1A"));
		assertTrue(map.containsKey("1-1B"));
		assertTrue(map.containsKey("1-2A"));
		assertTrue(map.containsKey("1-2B"));
		assertTrue(map.containsKey("1-3A"));
		assertTrue(map.containsKey("1-3B"));
		assertTrue(map.containsKey("1-4A"));
		assertTrue(map.containsKey("1-4B"));
		assertEquals(100, map.get("1-1A").getMaxConsumption(),0.001);
		assertEquals(100, map.get("1-1B").getMaxConsumption(),0.001);
		assertEquals(80, map.get("1-2A").getMaxConsumption(),0.001);
		assertEquals(80, map.get("1-2B").getMaxConsumption(),0.001);
		assertEquals(80, map.get("1-3A").getMaxConsumption(),0.001);
		assertEquals(80, map.get("1-3B").getMaxConsumption(),0.001);
		assertEquals(90, map.get("1-4A").getMaxConsumption(),0.001);
		assertEquals(90, map.get("1-4B").getMaxConsumption(),0.001);

		assertEquals("owner1", map.get("1-1A").getOwner());
		assertEquals("owner2", map.get("1-1B").getOwner());
		assertEquals("owner3", map.get("1-2A").getOwner());
		assertEquals("owner4", map.get("1-2B").getOwner());
		assertEquals("owner5", map.get("1-3A").getOwner());
		assertEquals("owner6", map.get("1-3B").getOwner());
		assertEquals("owner7", map.get("1-4A").getOwner());
		assertEquals("owner8", map.get("1-4B").getOwner());
		
	}
	

	@Test
	void testKO1() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A  100  owner1\r\n" + 
				"1-1B  100  \r\n" +
				"1-2A  80   owner3\r\n" +
				"1-2B  80   owner4\r\n" +
				"1-3A  80   owner5\r\n" +
				"1-3B  80   owner6\r\n" +
				"1-4A  90   owner7\r\n" +
				"1-4B  90   owner8\r\n";
		StringReader reader = new StringReader(fakeFile);
		assertThrows(BadFileFormatException.class, () -> {
			FlatReader flatReader = new MyFlatReader(reader);
			flatReader.getItems();
		});
	}	

	@Test
	void testKO2() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A  100  owner1\r\n" + 
				"1-1B  100  owner2\r\n" +
				"  80   owner3\r\n" +
				"1-2B  80   owner4\r\n" +
				"1-3A  80   owner5\r\n" +
				"1-3B  80   owner6\r\n" +
				"1-4A  90   owner7\r\n" +
				"1-4B  90   owner8\r\n";
		StringReader reader = new StringReader(fakeFile);
		assertThrows(BadFileFormatException.class, () -> {
			FlatReader flatReader = new MyFlatReader(reader);
			flatReader.getItems();
		});
	}		
	
	@Test
	void testKO4() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A  100  owner1\r\n" + 
				"1-1B  100  owner2\r\n" +
				"1-2A  CCCC   owner3\r\n" +
				"1-2B  80   owner4\r\n" +
				"1-3A  80   owner5\r\n" +
				"1-3B  80   owner6\r\n" +
				"1-4A  90   owner7\r\n" +
				"1-4B  90   owner8\r\n";
		StringReader reader = new StringReader(fakeFile);
		assertThrows(BadFileFormatException.class, () -> {
			FlatReader flatReader = new MyFlatReader(reader);
			flatReader.getItems();
		});
	}

	@Test
	void testKO5() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A  100  missing_newline" + 
				"1-1B  100  owner2\r\n" +
				"1-2A  80   owner3\r\n" +
				"1-2B  80   owner4\r\n" +
				"1-3A  80   owner5\r\n" +
				"1-3B  80   owner6\r\n" +
				"1-4A  90   owner7\r\n" +
				"1-4B  90   owner8\r\n";
		StringReader reader = new StringReader(fakeFile);
		assertThrows(BadFileFormatException.class, () -> {
			FlatReader flatReader = new MyFlatReader(reader);
			flatReader.getItems();
		});
	}		
	
}
