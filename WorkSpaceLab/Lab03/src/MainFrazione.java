/**
 * Classe di test
 * @author Fondamenti di Informatica T-2
 * @version March 2022
 */
public class MainFraction
{

	
	public static void main(String[] args) {
		
		
		//test costruzione Fraction
		//test funzionamento metodi accessor e toString
		Fraction Fraction1 = new Fraction(3, 12);
		System.out.println("Creata la Fraction " + Fraction1);

		Fraction Fraction2 = new Fraction(1, 4);
		System.out.println("Creata la Fraction " + Fraction2);

		Fraction Fraction3 = new Fraction(1, 8);
		System.out.println("Creata la Fraction " + Fraction3);

		Fraction Fraction4 = new Fraction(4, 1);
		System.out.println("Creata la Fraction " + Fraction4);
		
		//test valori negativi
		Fraction Fraction5 = new Fraction(-1, 8);
		assert(Fraction5.getNum() == -1 && Fraction5.getDen() == 8);
		System.out.println("Creata la Fraction " + Fraction5);
		
		Fraction Fraction6 = new Fraction(2, -3);
		System.out.println("Creata la Fraction " + Fraction6);
		
		Fraction Fraction7 = new Fraction(-5, -7);
		System.out.println("Creata la Fraction " + Fraction7);

		//test funzionamento equals
		
		if (Fraction1.equals(Fraction2)) // true
			System.out.println("Le frazioni " + Fraction1 + " e " + Fraction2 + " sono equivalenti");
		else
			System.out.println("Le frazioni " + Fraction1 + " e " + Fraction2 + " non sono equivalenti");

		
		if (Fraction1.equals(Fraction3)) // false
			System.out.println("Le frazioni " + Fraction1 + " e " +Fraction3 + " sono equivalenti");
		else
			System.out.println("Le frazioni " + Fraction1 + " e " + Fraction3 + " non sono equivalenti");
		
		
		if (Fraction1.equals(Fraction6)) // false
			System.out.println("Le frazioni " + Fraction1 + " e " + Fraction6 + " sono equivalenti");
		else
			System.out.println("Le frazioni " + Fraction1 + " e " + Fraction6 + " non sono equivalenti");

		//test funzionamento riduzioneMinimiTermini
		Fraction FractionRid = Fraction1.minTerm();
		
		System.out.println("La Fraction " + Fraction1 + " ridotta ai minimi termini diventa " + FractionRid); // 1/4
		
		FractionRid = Fraction6.minTerm();
		System.out.println("La Fraction " + Fraction6 + " ridotta ai minimi termini diventa " + FractionRid); // -2/3
		
		//test funzionamento somma fra due frazioni
		Fraction sum = Fraction6.sum(Fraction7);
		System.out.println("-2/3 + 5/7 = " + sum); // 1/21
		
		//test funzionamento somma con mcm
		Fraction sum2 = Fraction2.sumWithMcm(Fraction3);
		System.out.println("1/4 + 1/8 = " + sum2); //3/8
		
		sum2 = new Fraction(-1, 4).sumWithMcm(Fraction3);
		System.out.println("-1/4 + 2/16 = " + sum2); //-1/8
		
		//test uguaglianza somma fra due frazioni con mcm e senza
		Fraction sumWithMcm = Fraction6.sumWithMcm(Fraction7);
		System.out.println("La somma ottenuta con mcm e' " + sumWithMcm+ " senza e' "+ sum);
		
		//test funzionamento somma fra due frazioni
		Fraction sub = Fraction2.sub(Fraction3);
		System.out.println("1/4 - 1/8 =  " + sub);
		
		//test funzionamento prodotto fra due frazioni
		Fraction mul = Fraction4.mul(Fraction5);
		System.out.println("4 * -1/8 = " + mul); // -1/2
		
		//test funzionamento divisione fra due frazioni
		Fraction div = Fraction6.div(Fraction2);
		System.out.println("-2/3 : 1/4 = " + div); // -8/3
		
		//test funzionamento compareTo
	    System.out.println("il confronto tra 3/12 e 1/4 è: "+ Fraction1.compareTo(Fraction2));
	    
	  //test funzionamento getDouble
	    System.out.println("valore reale associato a : "+ Fraction1 + " è "+Fraction1.getDouble());
	    

	
	    
		
	}
}
