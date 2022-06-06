package gasforlife.persistence;

import java.util.List;
import java.util.Map;

public interface ConsumptionReader {
	
	Map<String,List<Double>> getItems();
}
