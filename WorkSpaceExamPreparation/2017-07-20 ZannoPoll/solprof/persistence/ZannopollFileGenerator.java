package zannopoll.persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class ZannopollFileGenerator {

/*
risposta di un singolo intervistato, con indicazione della sua età (intero) e sesso (‘M’/’F’), separati da virgole

Partito del cioccolato fondente, 23, M
Movimento vaniglia democratica, 21, F
Insieme per il salume nostrano, 52, M
Pizza è progresso, 36, F
Vegetarianesimo domani, 18, F
Unità nel formaggio, 81, M
*/
	private static int campioni = 286;
	
	public static void main(String[] args) throws FileNotFoundException{
		String[] partiti = {
				"Partito del cioccolato fondente",
				"Movimento vaniglia democratica",
				"Insieme per il salume nostrano",
				"Pizza è progresso",
				"Vegetarianesimo domani",
				"Unità nel formaggio"
		};
		int ageRange = 90; // +18, quindi 18-108
		Random ageGenerator = new Random();
		Random sexGenerator = new Random();
		Random partyGenerator = new Random();
		PrintWriter pw = new PrintWriter("RisultatoSondaggio.txt");
		for (int i=0; i<campioni; i++){
			pw.println(partiti[partyGenerator.nextInt(partiti.length)] + "," + (ageGenerator.nextInt(ageRange)+18) + "," + (sexGenerator.nextBoolean()?"M":"F"));;			
		}
		pw.close();	
	}
}
