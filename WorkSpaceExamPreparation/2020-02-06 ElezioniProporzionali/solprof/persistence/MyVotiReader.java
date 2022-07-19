package dentinia.governor.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.Partito;

public class MyVotiReader implements VotiReader {

	private BufferedReader reader;
	private long seggiDaAssegnare;
	private Elezioni elezioni;

	public MyVotiReader(Reader reader) throws IOException, BadFileFormatException {
		if (reader == null)
			throw new IllegalArgumentException("reader");
		
		this.reader = new BufferedReader(reader);
		seggiDaAssegnare = 0;
		elezioni = caricaElementi();
	}

	private Elezioni caricaElementi() throws IOException, BadFileFormatException {

		String line = null;
		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ITALY);
		Elezioni voti;
		line = reader.readLine();
		if (line != null && line.contains("SEGGI")) {
			StringTokenizer st = new StringTokenizer(line, "0123456789");
			st.nextToken();
			try {
				String token = st.nextToken("\n\r");
				seggiDaAssegnare = formatter.parse(token).longValue();
				voti = new Elezioni(seggiDaAssegnare);
			} catch (ParseException | NumberFormatException | IndexOutOfBoundsException | NoSuchElementException e) {
				throw new BadFileFormatException(e);
			}
		} else
			throw new BadFileFormatException("Formato file errato - manca sezione SEGGI");

		try {
			if ((line = reader.readLine()) != null) {
				String[] righe = line.split(",");
				for (String riga: righe) {
					StringTokenizer tokenizer = new StringTokenizer(riga, ".0123456789");
					String nome = tokenizer.nextToken().trim();
					if (nome.isEmpty())	throw new BadFileFormatException();
					long numeroVoti = formatter.parse(tokenizer.nextToken("\n\r").trim()).longValue();
					voti.setVoti(new Partito(nome), numeroVoti);
				}
			}

		} catch (ParseException | NoSuchElementException | NumberFormatException e) {
			throw new BadFileFormatException(e);
		}

		return voti;
	}

	public static void main(String args[]) throws IOException, BadFileFormatException {
		VotiReader vReader = new MyVotiReader(new BufferedReader(new FileReader("Voti.txt")));
		System.out.println(vReader.getElezioni());
	}

	@Override
	public Elezioni getElezioni() {
		return elezioni;
	}

}
