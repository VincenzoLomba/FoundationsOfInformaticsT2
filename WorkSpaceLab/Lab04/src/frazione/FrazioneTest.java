package frazione;

import util.MyMath;

/**
 * Classe di test
 * @author Fondamenti di Informatica T-2
 * @version March 2022
 */
public class FrazioneTest
{
	public static void main(String[] args) {
		
		//test MyMath mcd
		assert(MyMath.mcd(10, 5) == 5);
		assert(MyMath.mcd(7, 3) == 1);
		assert(MyMath.mcd(21, 49) == 7);		
		
		//test costruzione frazione
		//test funzionamento metodi accessor e toString
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
		
		//test funzionamento somma fra due frazioni
		Frazione sum = frazione6.sum(frazione7);
		assert(sum.getNum() == 1 && sum.getDen() == 21);
		
		//test funzionamento somma con mcm
		Frazione sum2 = frazione2.sumWithMcm(frazione3);
		assert(sum2.getNum() == 3 && sum2.getDen() == 8);
		
		sum2 = new Frazione(-1, 4).sumWithMcm(frazione3);
		assert(sum2.getNum() == -1 && sum2.getDen() == 8);
		
		//test uguaglianza somma fra due frazioni con mcm e senza
		Frazione sumWithMcm = frazione6.sumWithMcm(frazione7);
		assert(sum.getNum() == sumWithMcm.getNum() && sum.getDen() == sumWithMcm.getDen());
		assert(sum.equals(sumWithMcm) == true);
		
		//test funzionamento somma fra due frazioni
				Frazione sub = frazione2.sub(frazione3);
				assert(sub.getNum() == 1 && sub.getDen() == 8);
				
		
		//test funzionamento prodotto fra due frazioni
		Frazione mul = frazione4.mul(frazione5);
		assert(mul.getNum() == -1 && mul.getDen() == 2);
		
		//test funzionamento divisione fra due frazioni
		Frazione div = frazione6.div(frazione2);
		assert(div.getNum() == -8 && div.getDen() == 3);
		
		//test funzionamento reciproco
		Frazione r = frazione1.reciprocal();
		assert(r.getNum() == 4 && r.getDen() == 1);
		
		//test funzionamento compareTo
		    assert(frazione1.compareTo(frazione2)==0);
		    
		  //test funzionamento getDouble
		    assert(frazione1.getDouble()==0.25);
		    
		 //test funzionamento toString
		    assert(frazione1.toString().equals("3/12"));
		
		    
	}
	
}
