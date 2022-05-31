package taxcomparator.model;

import java.util.List;

public class Fasce {
	
	private List<Fascia> fasce;
	private double noTaxArea;
	private String name;

	public Fasce(String name, List<Fascia> fasce, double noTaxArea) {
		
		if (name==null || name.isBlank()) this.name = toFullString(); 
		else this.name=name; 
		if (fasce.isEmpty()) throw new IllegalArgumentException("lista fasce di reddito vuota");
		if (noTaxArea < 0) throw new IllegalArgumentException("no tax area negativa");
		if (!isConsistent(fasce)) throw new IllegalArgumentException("lista fasce di reddito inconsistente");
		this.fasce = fasce;
		this.noTaxArea = noTaxArea;
	}

	private boolean isConsistent(List<Fascia> fasce) {
		
		if (fasce.size() == 0) return false;
		double value = 0.0; /* First "Fascia" must have "0.0" as min value. */
		for (Fascia fascia : fasce) {
			if (fascia.getMin() != value || fascia.getMin() >= fascia.getMax())
				return false;
			value = fascia.getMax();
		}
		return value == Double.MAX_VALUE; /* Last "Fascia" must have "Double.MAX_VALUE" as max value. */
	}

	public List<Fascia> getFasce() { return fasce; }
	public double getNoTaxArea() { return noTaxArea; }

	@Override
	public String toString() { return name; }
	public String toFullString() {
		return "Fasce [fasce=" + fasce + ", noTaxArea=" + noTaxArea + "]";
	}
	
}
