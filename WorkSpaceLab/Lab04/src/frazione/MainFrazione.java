package frazione;

public class MainFrazione {
		
	  public static void main(String[] args) {
		  
		  Frazione[] collezioneA = new Frazione[10];
		  collezioneA[0] = new Frazione(1,3);
		  collezioneA[1] = new Frazione(2,3);
		  collezioneA[2] = new Frazione(-1,2); 
		  collezioneA[3] = new Frazione(1,6);
		  Frazione[] collezioneB = new Frazione[10];
		  collezioneB[0] = new Frazione(1,5);
		  collezioneB[1] = new Frazione(2,8);
		  collezioneB[2] = new Frazione(1,7);
		  collezioneB[3] = new Frazione(-1,6);
		  Frazione[] somma = Frazione.sum(collezioneA,collezioneB);
		  System.out.println(Frazione.convertToString(somma));
		  Frazione[] prodotto = Frazione.mul(collezioneA,collezioneB);
		  System.out.println(Frazione.convertToString(prodotto));
	  }
}
