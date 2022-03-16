/**
 * Classe di test
 * @author Fondamenti di Informatica T-2
 * @version March 2022
 */
public class FractionTest
{
	public static void main(String[] args) {
		
		//test MyMath mcd
				assert(MyMath.mcd(10, 5) == 5);
				assert(MyMath.mcd(7, 3) == 1);
				assert(MyMath.mcd(21, 49) == 7);		
		
		//test costruzione Fraction
		//test funzionamento metodi accessor e toString
		Fraction Fraction1 = new Fraction(3, 12);
		assert(Fraction1.getNum() == 3 && Fraction1.getDen() == 12);
		
		Fraction Fraction2 = new Fraction(1, 4);
		assert(Fraction2.getNum() == 1 && Fraction2.getDen() == 4);
		
		Fraction Fraction3 = new Fraction(1, 8);
		assert(Fraction3.getNum() == 1 && Fraction3.getDen() == 8);
		
		Fraction Fraction4 = new Fraction(4, 1);
		assert(Fraction4.getNum() == 4 && Fraction4.getDen() == 1);
		
		//test valori negativi
		Fraction Fraction5 = new Fraction(-1, 8);
		assert(Fraction5.getNum() == -1 && Fraction5.getDen() == 8);
		
		Fraction Fraction6 = new Fraction(2, -3);
		assert(Fraction6.getNum() == -2 && Fraction6.getDen() == 3);
		
		Fraction Fraction7 = new Fraction(-5, -7);
		assert(Fraction7.getNum() == 5 && Fraction7.getDen() == 7);
		
		//test funzionamento equals
		assert(Fraction1.equals(Fraction2));
		assert(!Fraction1.equals(Fraction3));
		assert(!Fraction1.equals(Fraction6));
		
		//test funzionamento riduzioneMinimiTermini
		Fraction FractionRid = Fraction1.minTerm();
		assert(FractionRid.getNum() == 1 && FractionRid.getDen() == 4);
		
		FractionRid = Fraction6.minTerm();
		assert(FractionRid.getNum() == -2 && FractionRid.getDen() == 3);
		
		//test funzionamento somma fra due frazioni
		Fraction sum = Fraction6.sum(Fraction7);
		assert(sum.getNum() == 1 && sum.getDen() == 21);
		
		//test funzionamento somma con mcm
		Fraction sum2 = Fraction2.sumWithMcm(Fraction3);
		assert(sum2.getNum() == 3 && sum2.getDen() == 8);
		
		sum2 = new Fraction(-1, 4).sumWithMcm(Fraction3);
		assert(sum2.getNum() == -1 && sum2.getDen() == 8);
		
		//test uguaglianza somma fra due frazioni con mcm e senza
		Fraction sumWithMcm = Fraction6.sumWithMcm(Fraction7);
		assert(sum.getNum() == sumWithMcm.getNum() && sum.getDen() == sumWithMcm.getDen());
		assert(sum.equals(sumWithMcm) == true);
		
		//test funzionamento somma fra due frazioni
				Fraction sub = Fraction2.sub(Fraction3);
				assert(sub.getNum() == 1 && sub.getDen() == 8);
				
		
		//test funzionamento prodotto fra due frazioni
		Fraction mul = Fraction4.mul(Fraction5);
		assert(mul.getNum() == -1 && mul.getDen() == 2);
		
		//test funzionamento divisione fra due frazioni
		Fraction div = Fraction6.div(Fraction2);
		assert(div.getNum() == -8 && div.getDen() == 3);
		
		//test funzionamento reciproco
		Fraction r = Fraction1.reciprocal();
		assert(r.getNum() == 4 && r.getDen() == 1);
		
		//test funzionamento compareTo
		    assert(Fraction1.compareTo(Fraction2)==0);
		    
		  //test funzionamento getDouble
		    assert(Fraction1.getDouble()==0.25);
		    
		 //test funzionamento toString
		    assert(Fraction1.toString().equals("3/12"));
		
		    
	}
	
}
