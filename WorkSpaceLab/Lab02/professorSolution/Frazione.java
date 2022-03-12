/**
 * Frazione come tipo di dato astratto (ADT)
 * 
 * @author Fondamenti di Informatica T-2
 * @version March 2022
 */
public class Frazione {
	private int num, den;

	/**
	 * Costruttore della Frazione
	 * 
	 * @param num
	 *            Numeratore
	 * @param den
	 *            Denominatore
	 */
	public Frazione(int num, int den) {
		boolean negativo = num * den < 0;
		this.num = negativo ? -Math.abs(num) : Math.abs(num);
		this.den = Math.abs(den);
	}

	/**
	 * Costruttore della Frazione
	 * 
	 * @param num
	 *            Numeratore
	 */
	public Frazione(int num) {
		this(num, 1);
	}

	/**
	 * Recupera il numeratore
	 * 
	 * @return Numeratore della frazione
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Recupera il denominatore
	 * 
	 * @return Denominatore della frazione
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
	public Frazione minTerm() {
		if (getNum()==0) return new Frazione(getNum(), getDen());
		int mcd = MyMath.mcd(Math.abs(getNum()), getDen());
		int n = getNum() / mcd;
		int d = getDen() / mcd;
		return new Frazione(n, d);
	}

	/**
	 * Calcola la somma con un'altra frazione
	 * 
	 * @param f Frazione da sommare all'attuale
	 *            
	 * @return Nuova frazione risultato della somma
	 */
	public Frazione sum(Frazione f) {
		int mcm = MyMath.mcm(f.getDen(), this.getDen());
		int n = ((mcm / this.getDen()) * this.getNum()) + ((mcm / f.getDen()) * f.getNum());
		int d = mcm;
		return (new Frazione(n, d)).minTerm();
	}
	
	/**
	 * Calcola la somma con un'altra frazione (versione con mcm)
	 * 
	 * @param f
	 *            Frazione da sommare all'attuale
	 * @return Nuova frazione risultato della somma
	 */
	public Frazione sumWithMcm(Frazione f) {
		int mcm = MyMath.mcm(f.getDen(), den);
		int n = ((mcm / den) * num) + ((mcm / f.getDen()) * f.getNum());
		int d = mcm;
		Frazione fSum = new Frazione(n, d);
		return fSum.minTerm();
	}
	
	/**
	 * Calcola la sottrazione con un'altra frazione
	 * 
	 * @param f
	 *            Frazione da sottrarre all'attuale
	 * @return Nuova frazione risultato della sottrazione
	 */
	public Frazione sub(Frazione f) {
		int mcm = MyMath.mcm(f.getDen(), den);
		int n = ((mcm / den) * num) - ((mcm / f.getDen()) * f.getNum());
		int d = mcm;
		Frazione fSub = new Frazione(n, d);
		return fSub.minTerm();
	}
	
	
	/**
	 * Calcola la moltiplicazione con un'altra frazione
	 * 
	 * @param f
	 *            Frazione da moltiplicare all'attuale
	 * @return Nuova frazione risultato della moltiplicazione
	 */
	public Frazione mul(Frazione f) {
		int n = this.getNum() * f.getNum();
		int d = this.getDen() * f.getDen();
		return new Frazione(n, d).minTerm();
	}

	/**
	 * Calcola la divisione con un'altra frazione
	 * 
	 * @param f
	 *            Frazione da dividere all'attuale
	 * @return Nuova frazione risultato della divisione
	 */
	public Frazione div(Frazione f) {
		return mul(new Frazione(f.getDen(), f.getNum())).minTerm();
	}

	/**
	 * Recupera il numero reale equivalente alla frazione
	 * 
	 * @return Valore reale
	 */
	public double getDouble() {
		return (double) getNum() / (double) getDen();
	}

	/**
	 * Verifica se una frazione è maggiore o minore di quella passata
	 * 
	 * @param  f Frazione da confrontare
	 * @return 0 se sono uguali, 1 se la Frazione è maggiore di quella passata, -1 se è minnore
	 */
	public int compareTo(Frazione f) {
		 int thisValue, otherValue;

		thisValue = this.getNum() * f.getDen();
		otherValue = f.getNum() * this.getDen();
		if (thisValue == otherValue)
			return 0;
		else
			return thisValue > otherValue ? 1 : -1;
	}
	
	 /** Calcola il reciroco frazione
		 * 
		 * @return Nuova frazione che rappresenta il reciproco già ai minimi termini
		 */
		public Frazione reciprocal() {
			Frazione r = new Frazione(getDen(), getNum());
			return r.minTerm();
		}
		
	

	public boolean equals(Frazione f) {
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
