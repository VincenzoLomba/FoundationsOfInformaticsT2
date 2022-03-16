package fractioncollection;

import fraction.Fraction;

public class FractionCollection {
	
	private static final int DEFAULT_PHYSICAL_SIZE = 10;
	private Fraction[] innerContainer;
	private int size;
	
	public FractionCollection (int physicalSize) {
		
		innerContainer = new Fraction[physicalSize];
		size = 0;
	}
	
	public FractionCollection () { this(DEFAULT_PHYSICAL_SIZE); }
	
	public FractionCollection (Fraction[] collection) {
		
		innerContainer = new Fraction[collection.length];
		size = 0;
		for (Fraction f : collection) if (f != null) innerContainer[size++] = f;
	}
	
	public int size () { return size; }
	
	public Fraction get (int index) { return index >= 0 && index < size ? innerContainer[index] : null; }
	
	public void put (Fraction f) {
		
		if (size < innerContainer.length) {
			innerContainer[size++] = f;
		} else {
			Fraction[] nw = new Fraction[innerContainer.length*2];
			int j = 0;
			for (Fraction fr : innerContainer) nw[j++] = fr;
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
		Fraction[] result = new Fraction[size];
		for (int j = 0 ; j < collection.size() ; ++j)result[j] = get(j).sum(collection.get(j));
		return new FractionCollection(result);
	}
	
	public FractionCollection sub (FractionCollection collection) {
		
		if (size != collection.size()) return null;
		Fraction[] result = new Fraction[size];
		for (int j = 0 ; j < collection.size() ; ++j) result[j] = get(j).sub(collection.get(j));
		return new FractionCollection(result);
	}

	public FractionCollection mul (FractionCollection collection) {
		
		if (size != collection.size()) return null;
		Fraction[] result = new Fraction[size];
		for (int j = 0 ; j < collection.size() ; ++j) result[j] = get(j).mul(collection.get(j));
		return new FractionCollection(result);
	}
	
	public FractionCollection div (FractionCollection collection) {
		
		if (size != collection.size()) return null;
		Fraction[] result = new Fraction[size];
		for (int j = 0 ; j < collection.size() ; ++j) result[j] = get(j).div(collection.get(j));
		return new FractionCollection(result);
	}
}
