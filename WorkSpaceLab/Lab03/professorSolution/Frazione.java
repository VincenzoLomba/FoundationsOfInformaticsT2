/**
 * Fraction come tipo di dato astratto (ADT)
 * 
 * @author Fondamenti di Informatica T-2
 * @version March 2022
 */
public class Fraction {
	private int num, den;

	/**
	 * Costruttore della Fraction
	 * 
	 * @param num
	 *            Numeratore
	 * @param den
	 *            Denominatore
	 */
	public Fraction(int num, int den) {
		boolean negativo = num * den < 0;
		this.num = negativo ? -Math.abs(num) : Math.abs(num);
		this.den = Math.abs(den);
	}

	/**
	 * Costruttore della Fraction
	 * 
	 * @param num
	 *            Numeratore
	 */
	public Fraction(int num) {
		this(num, 1);
	}

	/**
	 * Recupera il numeratore
	 * 
	 * @return Numeratore della Fraction
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Recupera il denominatore
	 * 
	 * @return Denominatore della Fraction
	 */
	public int getDen() {
		return den;
	}

	/**
	 * Calcola la funzione ridotta ai minimi termini.
	 * 
	 * @return Una nuova funzione equivalente all'attuale, ridotta ai minimi
	 *         termini.
	 */
	public Fraction minTerm() {
		if (getNum()==0) return new Fraction(getNum(), getDen());
		int mcd = MyMath.mcd(Math.abs(getNum()), getDen());
		int n = getNum() / mcd;
		int d = getDen() / mcd;
		return new Fraction(n, d);
	}

	/**
	 * Calcola la somma con un'altra Fraction
	 * 
	 * @param f Fraction da sommare all'attuale
	 *            
	 * @return Nuova Fraction risultato della somma
	 */
	public Fraction sum(Fraction f) {
		int mcm = MyMath.mcm(f.getDen(), this.getDen());
		int n = ((mcm / this.getDen()) * this.getNum()) + ((mcm / f.getDen()) * f.getNum());
		int d = mcm;
		return (new Fraction(n, d)).minTerm();
	}
	
	/**
	 * Calcola la somma con un'altra Fraction (versione con mcm)
	 * 
	 * @param f
	 *            Fraction da sommare all'attuale
	 * @return Nuova Fraction risultato della somma
	 */
	public Fraction sumWithMcm(Fraction f) {
		int mcm = MyMath.mcm(f.getDen(), den);
		int n = ((mcm / den) * num) + ((mcm / f.getDen()) * f.getNum());
		int d = mcm;
		Fraction fSum = new Fraction(n, d);
		return fSum.minTerm();
	}
	
	/**
	 * Calcola la sottrazione con un'altra Fraction
	 * 
	 * @param f
	 *            Fraction da sottrarre all'attuale
	 * @return Nuova Fraction risultato della sottrazione
	 */
	public Fraction sub(Fraction f) {
		int mcm = MyMath.mcm(f.getDen(), den);
		int n = ((mcm / den) * num) - ((mcm / f.getDen()) * f.getNum());
		int d = mcm;
		Fraction fSub = new Fraction(n, d);
		return fSub.minTerm();
	}
	
	
	/**
	 * Calcola la moltiplicazione con un'altra Fraction
	 * 
	 * @param f
	 *            Fraction da moltiplicare all'attuale
	 * @return Nuova Fraction risultato della moltiplicazione
	 */
	public Fraction mul(Fraction f) {
		int n = this.getNum() * f.getNum();
		int d = this.getDen() * f.getDen();
		return new Fraction(n, d).minTerm();
	}

	/**
	 * Calcola la divisione con un'altra Fraction
	 * 
	 * @param f
	 *            Fraction da dividere all'attuale
	 * @return Nuova Fraction risultato della divisione
	 */
	public Fraction div(Fraction f) {
		return mul(new Fraction(f.getDen(), f.getNum())).minTerm();
	}

	/**
	 * Recupera il numero reale equivalente alla Fraction
	 * 
	 * @return Valore reale
	 */
	public double getDouble() {
		return (double) getNum() / (double) getDen();
	}

	/**
	 * Verifica se una Fraction è maggiore o minore di quella passata
	 * 
	 * @param  f Fraction da confrontare
	 * @return 0 se sono uguali, 1 se la Fraction è maggiore di quella passata, -1 se è minnore
	 */
	public int compareTo(Fraction f) {
		 int thisValue, otherValue;

		thisValue = this.getNum() * f.getDen();
		otherValue = f.getNum() * this.getDen();
		if (thisValue == otherValue)
			return 0;
		else
			return thisValue > otherValue ? 1 : -1;
	}
	
	 /** Calcola il reciroco Fraction
		 * 
		 * @return Nuova Fraction che rappresenta il reciproco già ai minimi termini
		 */
		public Fraction reciprocal() {
			Fraction r = new Fraction(getDen(), getNum());
			return r.minTerm();
		}
		
	

	public boolean equals(Fraction f) {
		return f.getNum() * getDen() == f.getDen() * getNum();
	}
	
	@Override
   public String toString() {
	   String str = "";
		int num = getNum();
		int den = getDen();

		str += getDen() == 1 ? num : num + "/" + den;		
		return str;	   
   }
	
}
