package gasforlife.persistence.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gasforlife.persistence.BadFileFormatException;
import gasforlife.persistence.ConsumptionReader;
import gasforlife.persistence.MyConsumptionReader;


public class MyConsumptionReaderTest {	 
	
	@Test
	void testOK() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A : 1300|1069|1069|780|780|0|0|0|0|780|1400|1400\r\n" + 
				"1-1B : 1240|1240|1000|880|880|0|0|0|0|980|1290|1240\r\n" +
				"1-2A : 860|860|860|680|680|0|0|0|0|680|860|860 \r\n" +
				"1-2B : 900|900|900|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3A : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3B : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-4A : 940|940|940|780|780|0|0|0|0|780|940|940 \r\n" +
				"1-4B : 940|940|940|780|780|0|0|0|0|780|940|940\r\n";
		StringReader reader = new StringReader(fakeFile);
		ConsumptionReader c_reader = new MyConsumptionReader(reader);
		Map<String, List<Double>> map = c_reader.getItems(); 
		assertEquals(8, map.entrySet().size());
		assertTrue(map.containsKey("1-1A"));
		assertTrue(map.containsKey("1-1B"));
		assertTrue(map.containsKey("1-2A"));
		assertTrue(map.containsKey("1-2B"));
		assertTrue(map.containsKey("1-3A"));
		assertTrue(map.containsKey("1-3B"));
		assertTrue(map.containsKey("1-4A"));
		assertTrue(map.containsKey("1-4B"));
		assertEquals(121.609, map.get("1-1A").get(0),0.001);
	    assertEquals(115.996, map.get("1-1B").get(1),0.001); 
	    assertEquals(80.449,  map.get("1-2A").get(2),0.001); 
	    assertEquals(63.61, map.get("1-2B").get(3),0.001);
		assertEquals(63.61, map.get("1-3A").get(4),0.001); 
		assertEquals(0, map.get("1-3B").get(5),0.001); 
		assertEquals(87.932, map.get("1-4A").get(10),0.001);
		assertEquals(87.932, map.get("1-4B").get(11),0.001);	
	}
	
	@Test
	void testKO1() throws IOException, BadFileFormatException {
		String fakeFile = " : 1300|1069|1069|780|780|0|0|0|0|780|1400|1400\r\n" + 
				"1-1B : 1240|1240|1000|880|880|0|0|0|0|980|1290|1240\r\n" +
				"1-2A : 860|860|860|680|680|0|0|0|0|680|860|860 \r\n" +
				"1-2B : 900|900|900|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3A : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3B : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-4A : 940|940|940|780|780|0|0|0|0|780|940|940 \r\n" +
				"1-4B : 940|940|940|780|780|0|0|0|0|780|940|940\r\n";
		StringReader reader = new StringReader(fakeFile);
		assertThrows(BadFileFormatException.class, () -> {
			ConsumptionReader flatReader = new MyConsumptionReader(reader);
			flatReader.getItems();
		});
	}	
	
	
	@Test
	void testKO2() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A : 1300|1069|1069|780|780|0|0|0|0|780|1400|1400\r\n" + 
				"1-1B : 1240|1240|1000|880|880|0|0|0|0|980|1290|1240\r\n" +
				"1-2A : 860|860|860|680|680|0|0|0|0|680|860|860 \r\n" +
				"1-2B : 900|900|900|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3A : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3B : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-4A : 940|940|940|780|780|0|0|0|0|780|940|940 \r\n" +
				"1-4B : 940|940|780|780|0|0|0|0|780|940|940\r\n";
		StringReader reader = new StringReader(fakeFile);
		assertThrows(BadFileFormatException.class, () -> {
			ConsumptionReader flatReader = new MyConsumptionReader(reader);
			flatReader.getItems();
		});
	}	
	
	@Test
	void testKO3() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A : 1300|1069|1069|780|780|0|0|0|0|780|1400|1400\r\n" + 
				"1-1B : 1240|1240|1000|880|880|0|0|0|0|980|1290|1240\r\n" +
				"1-2A : 860|860|860|680|680|0|0|0|0|680|860|860 \r\n" +
				"1-2B : 900|900|BBB|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3A : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3B : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-4A : 940|940|940|780|780|0|0|0|0|780|940|940 \r\n" +
				"1-4B : 940|940|940|780|780|0|0|0|0|780|940|940\r\n";
		StringReader reader = new StringReader(fakeFile);
		assertThrows(BadFileFormatException.class, () -> {
			ConsumptionReader flatReader = new MyConsumptionReader(reader);
			flatReader.getItems();
		});
	}		
	@Test
	void testKO4() throws IOException, BadFileFormatException {
		String fakeFile = "1-1A : 1300|1069|1069|780|780|0|0|0|0|780|1400|1400\r\n" + 
				"1-1B : 1240|1240|1000|880|880|0|0|0|0|980|1290|1240\r\n" +
				"1-2A\r\n" +
				"1-2B : 900|900|BBB|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3A : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-3B : 860|860|860|680|680|0|0|0|0|680|860|860\r\n" +
				"1-4A : 940|940|940|780|780|0|0|0|0|780|940|940 \r\n" +
				"1-4B : 940|940|940|780|780|0|0|0|0|780|940|940\r\n";
		StringReader reader = new StringReader(fakeFile);
		assertThrows(BadFileFormatException.class, () -> {
			ConsumptionReader flatReader = new MyConsumptionReader(reader);
			flatReader.getItems();
		});
	}	
}
