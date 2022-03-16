/**
 * Classe di utility per funzioni matematiche
 * 
 * @author Fondamenti di Informatica T-2
 * @version March 2022
 */
public final class MyMath {
	/**
	 * Calcola il massimo comun divisore (m.c.d.) tra due interi.
	 * 
	 * @param a
	 *            Primo intero
	 * @param b
	 *            Secondo intero
	 * @return m.c.d. risultante.
	 */
	public static int mcd(int a, int b) {
		int resto;
		if (b > a) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		do {
			resto = a % b;
			a = b;
			b = resto;
		} while (resto != 0);
		return a;
	}

	/**
	 * Calcola il Minimo Comune Multiplo tra due numero interi (m.c.m.)
	 * 
	 * @param a
	 *            Primo Intero
	 * @param b
	 *            Secondo Intero
	 * @return m.c.m. risultante
	 */
	public static int mcm(int a, int b) {
		return (a * b) / mcd(a, b);
	}
}
