
public class Frazione {
	
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
		
		if (num == 0) return new Frazione(0, 1);
		int d = MyMath.mcd(Math.abs(num), den);
		return new Frazione(num / d, den / d);
	}
	
	public int compareTo (Frazione f) {
		
		if (this.equals(f)) return 0;
		return num*f.getDen() < den*f.getNum() ? -1 : 1;
	}
	
	public Frazione div (Frazione f) {
		return new Frazione(num*f.getDen(), den*f.getNum()).minTerm();
	}
	
	public double getDouble () { return ((double) num) / den; }
	
	public Frazione mul (Frazione f) {
		return new Frazione(num*f.getNum(), den*f.getDen()).minTerm();
	}
	
	public Frazione reciprocal () {
		return new Frazione(den, num).minTerm();
	}
	
	public Frazione sub (Frazione f) {
		return new Frazione(num*f.getDen() - f.getNum()*den, den*f.getDen()).minTerm();
	}
	
	public Frazione sum (Frazione f) {
		return new Frazione(num*f.getDen() + f.getNum()*den, den*f.getDen()).minTerm();
	}
	
	public Frazione sumWithMcm (Frazione f) {
		
		int mcm = MyMath.mcm(den, f.getDen());
		return new Frazione(mcm / den * num + mcm / f.getDen() * f.getNum(), mcm).minTerm();
	}
	
}