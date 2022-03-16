package frazlib;

import java.util.Arrays;

import frazione.Frazione;

public class FrazLib {
	
	public static Frazione sum(Frazione[] tutte) {
		
		/*
		if (tutte.length == 0) return new Frazione(0,1);
		Frazione result = tutte[0];
		for (int j = 1 ; j < tutte.length ; ++j) {
			result = result.sum(tutte[j]);
		}
		return result;
		*/
		return Arrays.stream(tutte).reduce(new Frazione(0,1), Frazione::sum);
	}
	
	public static Frazione mul(Frazione[] tutte) {
		
		/*
		if (tutte.length == 0) return new Frazione(1,1);
		Frazione result = tutte[0];
		for (int j = 1 ; j < tutte.length ; ++j) {
			result = result.mul(tutte[j]);
		}
		return result;
		*/
		return Arrays.stream(tutte).reduce(new Frazione(1,1), Frazione::mul);
	}

}
