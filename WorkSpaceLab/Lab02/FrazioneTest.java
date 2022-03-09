/**
 * Classe di test
 * @author Fondamenti di Informatica T-2
 * @version March 2022
 */
public class FrazioneTest
{
	public static void main(String[] args) {
		//test costruzione frazione
		//test funzionamento metodi accessor 
		Frazione frazione1 = new Frazione(3, 12);
		assert(frazione1.getNum() == 3 && frazione1.getDen() == 12);
		
		Frazione frazione2 = new Frazione(1, 4);
		assert(frazione2.getNum() == 1 && frazione2.getDen() == 4);
		
		Frazione frazione3 = new Frazione(1, 8);
		assert(frazione3.getNum() == 1 && frazione3.getDen() == 8);
		
		Frazione frazione4 = new Frazione(4, 1);
		assert(frazione4.getNum() == 4 && frazione4.getDen() == 1);
		
		//test valori negativi
		Frazione frazione5 = new Frazione(-1, 8);
		assert(frazione5.getNum() == -1 && frazione5.getDen() == 8);
		
		Frazione frazione6 = new Frazione(2, -3);
		assert(frazione6.getNum() == -2 && frazione6.getDen() == 3);
		
		Frazione frazione7 = new Frazione(-5, -7);
		assert(frazione7.getNum() == 5 && frazione7.getDen() == 7);
		
		//test funzionamento equals
		assert(frazione1.equals(frazione2));
		assert(!frazione1.equals(frazione3));
		assert(!frazione1.equals(frazione6));
		
		//test funzionamento riduzioneMinimiTermini
		Frazione frazioneRid = frazione1.minTerm();
		assert(frazioneRid.getNum() == 1 && frazioneRid.getDen() == 4);
		
		frazioneRid = frazione6.minTerm();
		assert(frazioneRid.getNum() == -2 && frazioneRid.getDen() == 3);
		
		    
	}
	
}
