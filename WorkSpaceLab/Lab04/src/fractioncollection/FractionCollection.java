package fractioncollection;

import frazione.Frazione;

public class FractionCollection {
	
	private static final int DEFAULT_PHYSICAL_SIZE = 10;
	private Frazione[] innerContainer;
	private int size;
	
	public FractionCollection (int physicalSize) {
		
		size = physicalSize;
		innerContainer = new Frazione[size];
	}
	
	public FractionCollection () { this(DEFAULT_PHYSICAL_SIZE); }
	
	public FractionCollection (Frazione[] collection) {
		
		innerContainer = new Frazione[collection.length];
		int size = 0;
		for (Frazione f : collection) if (f != null) innerContainer[size++] = f;
	}
	
	public int size () { return size; }
	
	public Frazione get (int index) { return index >= 0 && index < size ? innerContainer[index] : null; }
	
	public void put (Frazione f) {
		
		if (size < innerContainer.length) {
			innerContainer[++size] = f;
		} else {
			Frazione[] nw = new Frazione[innerContainer.length*2];
			int j = 0;
			for (Frazione fr : innerContainer) nw[j++] = fr;
			nw[j] = f;
			innerContainer = nw;
			++size;
		}
	}
	
	public void remove (int index) {
		
		if (index < 0 || index >= size || size == 0) return;
		if (index < size - 1) for (int j = index + 1 ; j < size ; ++j) innerContainer[j - 1] = innerContainer[j];
		--size;
	}
	
	@Override
	public String toString () {
		
		if (size == 0) return "[]";
		StringBuilder b = new StringBuilder(innerContainer[0].toString());
		for (int j = 1 ; j < size ; ++j) b.append(", " + innerContainer[j].toString());
		return "[" + b + "]";
	}
	
	public FractionCollection sum (FractionCollection collection) {
		
		if (size != collection.size()) return null;
		Frazione[] result = new Frazione[size];
		for (int j = 0 ; j < collection.size() ; ++j) result[j] = get(j).sum(collection.get(j));
		return new FractionCollection(result);
	}
	
	public FractionCollection sub (FractionCollection collection) {
		
		if (size != collection.size()) return null;
		Frazione[] result = new Frazione[size];
		for (int j = 0 ; j < collection.size() ; ++j) result[j] = get(j).sub(collection.get(j));
		return new FractionCollection(result);
	}

	public FractionCollection mul (FractionCollection collection) {
		
		if (size != collection.size()) return null;
		Frazione[] result = new Frazione[size];
		for (int j = 0 ; j < collection.size() ; ++j) result[j] = get(j).mul(collection.get(j));
		return new FractionCollection(result);
	}
	
	public FractionCollection div (FractionCollection collection) {
		
		if (size != collection.size()) return null;
		Frazione[] result = new Frazione[size];
		for (int j = 0 ; j < collection.size() ; ++j) result[j] = get(j).div(collection.get(j));
		return new FractionCollection(result);
	}
}
