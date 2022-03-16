
public class Fraction {
	
	private int num, den;
	
	public Fraction (int num, int den) {
		
		this.num = num;
		this.den = den;
		if (den < 0) {
			this.num = -this.num;
			this.den = -this.den;
		}
	}
	
	public Fraction (int num) { this(num, 1); }
	
	public int getNum () { return num; }
	public int getDen () { return den; }
	
	public boolean equals (Fraction f) { return num*f.getDen() == den*f.getNum(); }
	
	@Override
	public String toString () { return den == 1 ? String.valueOf(num) : num+"/"+den; }
	
	public Fraction minTerm () {
		
		if (num == 0) return new Fraction(0, 1);
		int d = MyMath.mcd(Math.abs(num), den);
		return new Fraction(num / d, den / d);
	}
	
	public int compareTo (Fraction f) {
		
		if (this.equals(f)) return 0;
		return num*f.getDen() < den*f.getNum() ? -1 : 1;
	}
	
	public Fraction div (Fraction f) {
		return new Fraction(num*f.getDen(), den*f.getNum()).minTerm();
	}
	
	public double getDouble () { return ((double) num) / den; }
	
	public Fraction mul (Fraction f) {
		return new Fraction(num*f.getNum(), den*f.getDen()).minTerm();
	}
	
	public Fraction reciprocal () {
		return new Fraction(den, num).minTerm();
	}
	
	public Fraction sub (Fraction f) {
		return new Fraction(num*f.getDen() - f.getNum()*den, den*f.getDen()).minTerm();
	}
	
	public Fraction sum (Fraction f) {
		return new Fraction(num*f.getDen() + f.getNum()*den, den*f.getDen()).minTerm();
	}
	
	public Fraction sumWithMcm (Fraction f) {
		
		int mcm = MyMath.mcm(den, f.getDen());
		return new Fraction(mcm / den * num + mcm / f.getDen() * f.getNum(), mcm).minTerm();
	}
	
}