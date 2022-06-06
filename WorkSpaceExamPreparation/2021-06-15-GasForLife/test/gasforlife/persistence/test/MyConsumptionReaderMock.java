package gasforlife.persistence.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gasforlife.persistence.ConsumptionReader;

public class MyConsumptionReaderMock implements ConsumptionReader{
	
public	Map<String,List<Double>> getItems()
	{
		
		Map<String, List<Double>> consumption = new HashMap<>();
		Double d[] = {120.0,120.0,120.0,90.0,90.0,0.0,0.0,0.0,100.0,90.0,120.0,120.0};
		List<Double> aL = new ArrayList<Double>(Arrays.asList(d));
		consumption.put("1-1A", aL);
	
		
		Double d1[] = {130.0,130.0,120.0,80.0,80.0,0.0,0.0,0.0,40.0,90.0,120.0,120.0};
		aL = new ArrayList<Double>(Arrays.asList(d1));
		consumption.put("1-1B", aL);
	
		
		Double d2[] = {100.0,100.0,100.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
		aL = new ArrayList<Double>(Arrays.asList(d2));
		consumption.put("1-2A", aL);
		
		
		Double d3[] = {100.0,100.0,100.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
		aL = new ArrayList<Double>(Arrays.asList(d3));
		consumption.put("1-2B", aL);
		
		
		Double d4[] = {100.0,100.0,100.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
		aL = new ArrayList<Double>(Arrays.asList(d4));
		consumption.put("1-3A", aL);
	
		
		Double d5[] = {100.0,100.0,100.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
		aL = new ArrayList<Double>(Arrays.asList(d5));
		consumption.put("1-3B", aL);
		
		
		Double d6[] = {100.0,100.0,90.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
		aL = new ArrayList<Double>(Arrays.asList(d6));
		consumption.put("1-4A", aL);
		
		
		Double d7[] = {100.0,100.0,90.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
		aL = new ArrayList<Double>(Arrays.asList(d7));
		consumption.put("1-4B", aL);
		return consumption;
	}

}
