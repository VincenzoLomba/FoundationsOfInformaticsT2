package util;
public class MyMath {
	
	public static int mcd(int a, int b){
		
		int resto;
		if (b > a){
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

	public static int mcm(int a, int b) { return a*b/mcd(a, b); }
	
}