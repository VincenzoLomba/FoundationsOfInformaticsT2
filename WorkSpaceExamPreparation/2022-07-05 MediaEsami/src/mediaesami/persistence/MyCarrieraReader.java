package mediaesami.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import mediaesami.model.AttivitaFormativa;
import mediaesami.model.Carriera;
import mediaesami.model.Esame;
import mediaesami.model.Voto;


public class MyCarrieraReader implements CarrieraReader {

	/*
	 * 	27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT
		27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22
		28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24
		29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26
		26337	LINGUA INGLESE B-2					6,0
		27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE
		27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT
		28006	FONDAMENTI DI INFORMATICA T-2		12,0
		28011	RETI LOGICHE T						6,0
		...
	* */

	private static final String SEPARATOR = "\t";
	
	@Override
	public Carriera leggiCarriera(Reader rdr) throws IOException {
		
		if (rdr == null) throw new IllegalArgumentException(
			"Alla classe \"" + MyCarrieraReader.class.getSimpleName() +"\" e' stato fornito un \"" + Reader.class.getSimpleName() + "\" nullo."
		);
		
		BufferedReader br = new BufferedReader(rdr);
		String line = null;
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALY);
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(1);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy").withLocale(Locale.ITALY);
		
		Carriera response = new Carriera();
		
		while ((line = br.readLine()) != null) {
			
			if (line.isBlank()) continue;
			StringTokenizer sk = new StringTokenizer(line, SEPARATOR);
			int tkn = sk.countTokens();
			
			if (tkn != 3 && tkn != 5) throw new BadFileFormatException(
				"Ogni riga deve presentare o tre o cinque elementi (tra loro separati mediante una o piu' tabulazioni)."
			);
			long id = getId(sk);
			String denominazione = getDenominazione(sk);
			double cfu = getCfu(sk, nf);
			
			if (tkn == 5) {
				LocalDate data = getData(sk, df);
				Voto voto = getVoto(sk);
				try {
					response.inserisci(new Esame(new AttivitaFormativa(id, denominazione, cfu), data, voto));
				} catch (IllegalArgumentException e) {
					throw new BadFileFormatException(
						"Individuato un esame non inseribile in carriera. Nello specifico: \""
						+ e.getMessage() + "\"."
					);
				}
			}
		}
		
		return response;
	}
	
	private long getId (StringTokenizer sk) throws BadFileFormatException {
		
		String response = sk.nextToken().trim();
		if (!response.matches("[0-9]+")) throw new BadFileFormatException(
			"Il primo elemento di ogni riga deve essere un intero positivo long (rappresentativo dell'identificativo della attivita' formativa)."
		);
		return Long.parseLong(response);
	}
	
	private String getDenominazione(StringTokenizer sk) {
		
		return sk.nextToken().trim().toUpperCase();
	}
	
	private double getCfu(StringTokenizer sk, NumberFormat nf) throws BadFileFormatException {
		
		/* ATTENZIONE:
		 * 
		 * Nel formato numerico italiano, il simbolo "." e' ammesso, per quanto non venga impiegato come separatore per valori decimali (il quale risulta essere ",")
		 * Per questo, e' necessario un controllo (prima della conversione) che verifichi il non utilizzo del carattere "."
		 * Se così non fosse, si è verificato che si incorrebbe in problemi di conversione del tipo: stringa 6.0 convertita nel valore double 60.0
		 * Inoltre, tale verifica a priori deve anche essere relativa ad accertamento della non negatività del valore dei cfu (ovvero assenza del simbolo "-")
		 * Dato che il carattere "." e' impiegato (nel formato italiano) come separatore delle migliaia, escluderlo significa immaginare soddisfatto il vincolo per cui il numero di cfu e' inferiore alla migliaia
		 * Tale vincolo viene allora esplcitamente verificato (se e' vero come non si vogliono valore numerici non inferiori alla migliaia, e' vero anche come questi potrebbero essere rappresentati senza l'uso di ".")
		 * */
		
		try {
			String response = sk.nextToken().trim();
			
			/* Utilizzo di sole cifre numeriche e virgole */
			if (!response.matches("([0-9]|,)+")) response = "-";
			
			/* Utilizzo di al piu' tre elementi prima dell'ultima virgola (che dovrebbe essere l'unica presente)*/
			if (response.lastIndexOf(",") != -1 && response.substring(0, response.lastIndexOf(",")).length() > 3) response = "-";
			
			return nf.parse(response).doubleValue();
		} catch (ParseException e) {
			throw new BadFileFormatException(
				"Il terzo elemento di ogni riga deve essere un valore numerico reale (inferiore alla migliaia) formattato, per quanto attiene alla parte decimale,"
				+ " secondo le convenzioni italiane (tale valore numerico e' rappresentativo del numero di cfu della attivita' formativa)."
			);
		}
	}
	
	private LocalDate getData (StringTokenizer sk, DateTimeFormatter df) throws BadFileFormatException {
		
		try {
			return LocalDate.parse(sk.nextToken().trim(), df);
		} catch (DateTimeParseException e) {
			throw new BadFileFormatException(
				"Le righe che presentano cinque elementi devono presentare come quarto elemento una data correttente formattata secondo la struttura GG/MM/AAAA"
				+ " (rappresentativa della data in cui e' stato sostenuto l'esame associato alla riga corrente)."
			);
		}
	}
	
	private Voto getVoto (StringTokenizer sk) throws BadFileFormatException {
		
		try {
			return Voto.of(sk.nextToken().trim());
		} catch (IllegalArgumentException e) {
			throw new BadFileFormatException(
				"Le righe che presentano cinque elementi devono presentare come quarto elemento un voto che sia stringa uguale a uno dei seguenti valori: "
				+ Arrays.stream(Voto.values()).map(v -> "\"" + v.toString() + "\"").collect(Collectors.joining(","))
			);
		}
	}
}