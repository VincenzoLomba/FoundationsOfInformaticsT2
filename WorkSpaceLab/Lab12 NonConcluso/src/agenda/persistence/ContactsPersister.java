package agenda.persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import agenda.model.Contact;

public interface ContactsPersister {
	
	List<Contact> load(Reader reader) throws IOException, BadFileFormatException;
	void save(List<Contact> contacts, Writer writer) throws IOException;
}
