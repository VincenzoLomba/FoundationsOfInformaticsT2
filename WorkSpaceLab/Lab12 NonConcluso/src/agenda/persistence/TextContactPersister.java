package agenda.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import agenda.model.Contact;
import agenda.model.Detail;

public class TextContactPersister {

	private static final String SEPARATOR = ";";
	private static final String startContact = "StartContact";
	private static final String endContact = "EndContact";
	
	public List<Contact> load (Reader reader) throws IOException, BadFileFormatException {
		
		try (BufferedReader bufferedReader = new BufferedReader(reader)) {
			List<Contact> response = new ArrayList<>();
			Optional<Contact> c;
			while ((c = readContact(bufferedReader)).isPresent()) response.add(c.get());
			return response;
		}
	}
	
	private Optional<Contact> readContact (BufferedReader bufferedReader) throws IOException, BadFileFormatException {
		
		StringTokenizer stk;
		try { stk = new StringTokenizer(readLineSkippingEmpty(bufferedReader), SEPARATOR); } catch (BadFileFormatException bffe) { return Optional.empty(); }
		if (stk.countTokens() != 1 || !stk.nextToken().equals(startContact))
			throw new BadFileFormatException("Le informazioni relative ad ogni contatto devono iniziare con una riga \"" + startContact + "\"");
		stk = new StringTokenizer(readLineSkippingEmpty(bufferedReader), SEPARATOR);
		if (stk.countTokens() != 2)
			throw new BadFileFormatException("Le informazioni relative ad ogni contatto devono presentare in seconda riga un nome e un cognome separati da \"" + SEPARATOR + "\"");
		return Optional.of(new Contact(stk.nextToken().trim(), stk.nextToken().trim(), readDetails(bufferedReader)));
	}
	
	private List<Detail> readDetails (BufferedReader bufferedReader) throws IOException, BadFileFormatException {
		
		List<Detail> response = new ArrayList<>();
		String line;
		while (!(line = readLineSkippingEmpty(bufferedReader).trim()).equals(endContact)) {
			StringTokenizer stk = new StringTokenizer(line, SEPARATOR);
			DetailPersister dp = DetailPersister.of(stk.nextToken().trim());
			if (dp == null) throw new BadFileFormatException("Incontrato formato errato in una delle righe descrittive dei dettagli di un contatto.");
			response.add(dp.load(stk));
		}
		
		return response;
	}
	
	private String readLineSkippingEmpty (BufferedReader bufferedReader) throws IOException, BadFileFormatException {
		
		String line;
		do { line = bufferedReader.readLine(); } while (line != null && line.isBlank());
		if (line == null) throw new BadFileFormatException("Le informazioni relative ad ogni contatto devono terminare con una riga \"" + endContact + "\"");
		return line;
	}
	
	public void save (List<Contact> contacts, Writer writer) throws IOException, BadFileFormatException { for (Contact contact : contacts) saveContact(contact, writer); }
	
	private void saveContact (Contact contact, Writer writer) throws IOException, BadFileFormatException {
		
		if (contact.getName() == null || contact.getName().isBlank() || contact.getSurname() == null || contact.getSurname().isBlank())
			throw new BadFileFormatException("Impossibile salvare su file un contatto dove nome o cognome sono assenti.");
		StringBuilder stringBuilder = new StringBuilder(startContact).append("\n");
		stringBuilder.append(contact.getName()).append(";").append(contact.getSurname()).append("\n");
		saveDetails(contact.getDetailList(), stringBuilder);
		stringBuilder.append(endContact).append("\n");
		new PrintWriter(writer).print(stringBuilder.toString());
	}
	
	private void saveDetails (List<Detail> details, StringBuilder stringBuilder) {
		
		for (Detail detail : details) DetailPersister.of(detail.getName()).save(detail, stringBuilder); /* detail.getName() cannot return null. */
	}
	
}