package fraction;

public class FractionMain {
		
	  public static void main(String[] args) {
		  
		  Fraction[] collezioneA = new Fraction[10];
		  collezioneA[0] = new Fraction(1,3);
		  collezioneA[1] = new Fraction(2,3);
		  collezioneA[2] = new Fraction(-1,2); 
		  collezioneA[3] = new Fraction(1,6);
		  Fraction[] collezioneB = new Fraction[10];
		  collezioneB[0] = new Fraction(1,5);
		  collezioneB[1] = new Fraction(2,8);
		  collezioneB[2] = new Fraction(1,7);
		  collezioneB[3] = new Fraction(-1,6);
		  Fraction[] somma = Fraction.sum(collezioneA,collezioneB);
		  System.out.println(Fraction.convertToString(somma));
		  Fraction[] prodotto = Fraction.mul(collezioneA,collezioneB);
		  System.out.println(Fraction.convertToString(prodotto));
	  }
}
