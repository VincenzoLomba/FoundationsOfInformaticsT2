package frazione;
/**
 * Classe di test
 * @author Fondamenti di Informatica T-2
 * @version March 2022
 */
public class MainFrazione
{

	
	public static void main(String[] args) {
		
		
		//test costruzione frazione
		//test funzionamento metodi accessor e toString
		Frazione frazione1 = new Frazione(3, 12);
		System.out.println("Creata la frazione " + frazione1);

		Frazione frazione2 = new Frazione(1, 4);
		System.out.println("Creata la frazione " + frazione2);

		Frazione frazione3 = new Frazione(1, 8);
		System.out.println("Creata la frazione " + frazione3);

		Frazione frazione4 = new Frazione(4, 1);
		System.out.println("Creata la frazione " + frazione4);
		
		//test valori negativi
		Frazione frazione5 = new Frazione(-1, 8);
		assert(frazione5.getNum() == -1 && frazione5.getDen() == 8);
		System.out.println("Creata la frazione " + frazione5);
		
		Frazione frazione6 = new Frazione(2, -3);
		System.out.println("Creata la frazione " + frazione6);
		
		Frazione frazione7 = new Frazione(-5, -7);
		System.out.println("Creata la frazione " + frazione7);

		//test funzionamento equals
		
		if (frazione1.equals(frazione2)) // true
			System.out.println("Le frazioni " + frazione1 + " e " + frazione2 + " sono equivalenti");
		else
			System.out.println("Le frazioni " + frazione1 + " e " + frazione2 + " non sono equivalenti");

		
		if (frazione1.equals(frazione3)) // false
			System.out.println("Le frazioni " + frazione1 + " e " +frazione3 + " sono equivalenti");
		else
			System.out.println("Le frazioni " + frazione1 + " e " + frazione3 + " non sono equivalenti");
		
		
		if (frazione1.equals(frazione6)) // false
			System.out.println("Le frazioni " + frazione1 + " e " + frazione6 + " sono equivalenti");
		else
			System.out.println("Le frazioni " + frazione1 + " e " + frazione6 + " non sono equivalenti");

		//test funzionamento riduzioneMinimiTermini
		Frazione frazioneRid = frazione1.minTerm();
		
		System.out.println("La frazione " + frazione1 + " ridotta ai minimi termini diventa " + frazioneRid); // 1/4
		
		frazioneRid = frazione6.minTerm();
		System.out.println("La frazione " + frazione6 + " ridotta ai minimi termini diventa " + frazioneRid); // -2/3
		
		//test funzionamento somma fra due frazioni
		Frazione sum = frazione6.sum(frazione7);
		System.out.println("-2/3 + 5/7 = " + sum); // 1/21
		
		//test funzionamento somma con mcm
		Frazione sum2 = frazione2.sumWithMcm(frazione3);
		System.out.println("1/4 + 1/8 = " + sum2); //3/8
		
		sum2 = new Frazione(-1, 4).sumWithMcm(frazione3);
		System.out.println("-1/4 + 2/16 = " + sum2); //-1/8
		
		//test uguaglianza somma fra due frazioni con mcm e senza
		Frazione sumWithMcm = frazione6.sumWithMcm(frazione7);
		System.out.println("La somma ottenuta con mcm e' " + sumWithMcm+ " senza e' "+ sum);
		
		//test funzionamento somma fra due frazioni
		Frazione sub = frazione2.sub(frazione3);
		System.out.println("1/4 - 1/8 =  " + sub);
		
		//test funzionamento prodotto fra due frazioni
		Frazione mul = frazione4.mul(frazione5);
		System.out.println("4 * -1/8 = " + mul); // -1/2
		
		//test funzionamento divisione fra due frazioni
		Frazione div = frazione6.div(frazione2);
		System.out.println("-2/3 : 1/4 = " + div); // -8/3
		
		//test funzionamento compareTo
	    System.out.println("il confronto tra 3/12 e 1/4 è: "+ frazione1.compareTo(frazione2));
	    
	  //test funzionamento getDouble
	    System.out.println("valore reale associato a : "+ frazione1 + " è "+frazione1.getDouble());
	    

	
	    
		
	}
}
