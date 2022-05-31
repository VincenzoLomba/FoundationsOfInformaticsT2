package taxcomparator.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import taxcomparator.model.Fasce;
import taxcomparator.model.Fascia;

public class MyFasceReader implements FasceReader {
	
	private static final String NO_TAX_AREA = "no-tax area:";
	private static final String SEPARATORS = "-: \t";
	private static final String OLTRE = "oltre";
	
	@Override
	public Fasce readFasce(String descr, Reader reader) throws BadFileFormatException, IOException {
		
		if (reader == null) throw new IllegalArgumentException("Il Reader fornito alla classe " + MyFasceReader.class.getSimpleName() + " e' risultato nullo.");
		BufferedReader br = new BufferedReader(reader);
		ArrayList<Fascia> responsePart = new ArrayList<>();
		
		String line = br.readLine();
		if (line == null)
			throw new BadFileFormatException("Il file aperto in lettura non contiene alcuna riga di testo.");
		
		double noTaxArea = 0.0;
		try {
			if (!line.substring(0, 12).toLowerCase().equals(NO_TAX_AREA))
				throw new BadFileFormatException("La prima linea del file di testo deve iniziare con la stringa \"" + NO_TAX_AREA +"\"");
			noTaxArea = NumberFormat.getInstance(Locale.ITALY).parse(line.substring(12).trim()).doubleValue();
		} catch (IndexOutOfBoundsException | ParseException e) {
			throw new BadFileFormatException("Formattazione errata della prima riga del file di testo.", e);
		}
		
		while ((line = br.readLine()) != null) {
			StringTokenizer stk = new StringTokenizer(line, SEPARATORS);
			if (stk.countTokens() != 3)
				throw new BadFileFormatException("Formattazione errata di una delle righe che riportano le fasce disponibili.");
			try {
				double d1 = NumberFormat.getInstance(Locale.ITALY).parse(stk.nextToken().trim()).doubleValue();
				String limiteSuperiore = stk.nextToken().trim().toLowerCase();
				double d2 = limiteSuperiore.equals(OLTRE) ? Double.MAX_VALUE : NumberFormat.getInstance(Locale.ITALY).parse(limiteSuperiore).doubleValue();
				Fascia fascia = new Fascia(d1, d2, stk.nextToken().trim());
				responsePart.add(fascia);
			} catch (ParseException e) {
				throw new BadFileFormatException("Formattazione errata di una delle righe che riportano le fasce disponibili (valori numerici non reperibili).", e);
			}
		}
		
		return new Fasce(descr, responsePart, noTaxArea);
	}

}
