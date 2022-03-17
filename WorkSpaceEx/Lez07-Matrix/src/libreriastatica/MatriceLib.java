package libreriastatica;

public class MatriceLib {
	
	public static double[][] sommaMatrici (double[][] a, double[][] b) {
		
		if (a.length != b.length || a[0].length != b[0].length) throw new IllegalArgumentException(
			"Le matrici in ingresso debbono essere della stessa dimensione."
		);
		double[][] c = new double[a.length][a[0].length];
		for (int i = 0 ; i < a.length ; ++i) {
			for (int j = 0 ; j < a[0].length ; ++j) {
				c[i][j] = a[i][j] + b[i][j];
			}
		}
		return c;
	}
	
	public static double[][] prodottoMatrici (double[][] a, double[][] b) {
		
		if (a[0].length != b.length) throw new IllegalArgumentException(
			"Le colonne della prima matrice debbono essere tante quante le righe della seconda."
		);
		double[][] c = new double[a.length][b[0].length];
		int i = 0;
		for (double[] aLine : a) {
			for (int j = 0 ; j < b[0].length ; ++j) {
				c[i][j] = 0;
				for (int jj = 0 ; jj < b.length ; ++jj) {
					c[i][j] += aLine[jj] * b[jj][j];
				}
			}
			++i;
		}
		return c;
	}
	
	public static void stampaMatrice (double[][] a) {
		
		StringBuilder b = new StringBuilder("[ " + a[0][0]);
		for (int i = 0 ; i < a.length ; ++i) {
			for (int j = 1 ; j < a[i].length ; ++j) {
				b.append(", " + a[i][j]);
			}
			b.append(i == a.length - 1 ? " ]" : " ]\n[ " + a[1+i][0]);
		}
		System.out.println(b);
	}

}
