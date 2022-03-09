
public class Frazione  {
	
	private int num, den;
	
	public Frazione (int num, int den) {
		
		this.num = num;
		this.den = den;
		if (den < 0) {
			this.num = -this.num;
			this.den = -this.den;
		}
	}
	
	public Frazione (int num) { this(num, 1); }
	
	public int getNum () { return num; }
	public int getDen () { return den; }
	
	public boolean equals (Frazione f) { return num*f.getDen() == den*f.getNum(); }
	
	@Override
	public String toString () { return den == 1 ? String.valueOf(num) : num+"/"+den; }
	
	public Frazione minTerm () {
		
		int d = MyMath.mcd(num, den);
		return new Frazione(num / d, den / d);
	}
}