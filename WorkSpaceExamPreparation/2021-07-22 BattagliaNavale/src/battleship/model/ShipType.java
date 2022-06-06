package battleship.model;

public enum ShipType {
	
	SOMMERGIBILE(1), CACCIATORPEDINIERE(2), INCROCIATORE(3), PORTAEREI(4); 
	
	private int len;
	
	private ShipType(int len) { this.len=len; }
	public int getLength() { return len; }
	public String toString() { return this.name() + " (" + len + " element" + (len==1 ? "o)" : "i)"); }
	public static ShipType of(int length) { return ShipType.values()[length-1]; }
}
