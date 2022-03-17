package libreriastatica;

public class MatriceLibTestMain {
	
	public static void main(String[] args) {
	
	  double[][] m = {{ 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
	  double[][] n = {{ 2, 1, 1 }, { 1, 1, 0 },{ 0, 0, 1 } };    
	  double[][] q = MatriceLib.sommaMatrici(m,n);
	
	  System.out.println("SOMMA");
	  MatriceLib.stampaMatrice(q);
	  
	  m = new double[][] {{ 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
      n  = new double[][]{{ 2, 1, 1 }, { 1, 1, 0 }, { 0, 0, 1 } };    
      q = MatriceLib.prodottoMatrici(m,n);	
     
      System.out.println("\nPRODOTTO");
      MatriceLib.stampaMatrice(q);
	}
}
