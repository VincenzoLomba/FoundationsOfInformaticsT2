package media.collection;

import media.Media;

public class MediaCollection {
	
	private static final int DEFAULT_PHYSICAL_SIZE = 10;
	private static final int DEFAULT_GROWTH_FACTOR = 2;
	
	private Media[] innerContainer;
	private int size;
	
	public MediaCollection(Media[] collection) {
		innerContainer = new Media[collection.length];
		int i;
		for (i = 0; i < collection.length && collection[i] != null; ++i) {
			innerContainer[i] = collection[i];
		}
		size = i;
	}
	
	public MediaCollection(int physicalSize) {
		innerContainer = new Media[physicalSize];
		size = 0;
	}
	
	public MediaCollection() {
		this(DEFAULT_PHYSICAL_SIZE);
	}
	
	public int size() {
		return size;
	}
	
	public void add(Media f) {
		if (innerContainer.length == size) {
			Media[] newContainer = new Media[size * DEFAULT_GROWTH_FACTOR];
			for (int i = 0; i < innerContainer.length; ++i) {
				newContainer[i] = innerContainer[i];
			}
			innerContainer = newContainer;
		}
		innerContainer[size++] = f;
	}
	
	public void remove(int index) {
		if (index < 0 || index >= size)
			return;
		
		for (int i = index; i < size - 1; ++i) {
			innerContainer[i] = innerContainer[i+1];
		}
		size--;
	}
	
	public int indexOf(Media m) {
		for (int i = 0; i < size(); ++i) {
			if (innerContainer[i].equals(m)) {
				return i;
			}
		}
		return -1;
	}
	
	public Media get(int index) {
		if (index < 0 || index >= size)
			return null;
	
		return innerContainer[index];
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < size(); ++i) {
			sb.append(get(i).toString());
			if (i < size() - 1)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
}