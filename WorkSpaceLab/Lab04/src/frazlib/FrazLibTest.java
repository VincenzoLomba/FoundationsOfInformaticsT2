package frazlib;

import frazione.Frazione;

public class FrazLibTest {
	
	public static void main(String[] args) {
		
		Frazione f1 = new Frazione(3, 12);
		Frazione f2 = new Frazione(1, 4);
		Frazione f3 = new Frazione(1, 8);
		Frazione[] fs = new Frazione[] {f1, f2, f3};
		System.out.println(FrazLib.sum(fs));
	}

}
