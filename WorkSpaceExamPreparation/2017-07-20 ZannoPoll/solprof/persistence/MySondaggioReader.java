package zannopoll.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import zannopoll.model.Intervista;
import zannopoll.model.Sesso;

public class MySondaggioReader implements SondaggioReader {

	public List<Intervista> leggiRisposte(Reader r) throws IOException, BadFileFormatException {
		if (r == null)
			throw new IllegalArgumentException("reader nullo");
		BufferedReader reader = new BufferedReader(r);

		String line = null;
		List<Intervista> list = new ArrayList<>();

		try {
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				String nome = tokenizer.nextToken().trim();
				if (nome.isEmpty())
					throw new BadFileFormatException();
				int eta = Integer.parseInt(tokenizer.nextToken().trim());
				String sex = tokenizer.nextToken().trim();
				if (!sex.equalsIgnoreCase("M") && !sex.equalsIgnoreCase("F"))
					throw new BadFileFormatException("sesso dev'essere M o F");
				list.add(new Intervista(nome, eta, (sex.equals("M") ? Sesso.MASCHIO : Sesso.FEMMINA)));
			}

		} catch (NoSuchElementException | NumberFormatException e) {
			throw new BadFileFormatException(e);
		}

		return list;
	}

	// quick test
	public static void main(String args[]) throws IOException, BadFileFormatException {
		try (Reader r = new FileReader("RisultatoSondaggio.txt")) {
			SondaggioReader vReader = new MySondaggioReader();
			System.out.println("reader aperto");
			List<Intervista> myList = vReader.leggiRisposte(r);
			System.out.println("risposte caricate: " + myList.size());
			System.out.println(myList.stream().limit(10).collect(Collectors.toList()));
		}
	}

}
