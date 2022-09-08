package agenda.persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import agenda.model.Agenda;
import agenda.model.Contact;

public class PersistenceManager {
	
	private Agenda agenda;
	private String fileName;
	private boolean isDirty = false;
	private ContactsPersister contactsPersister;

	public PersistenceManager(Agenda agenda, ContactsPersister contactsPersister) {
		
		this.agenda = agenda;
		this.contactsPersister = contactsPersister;
	}

	public void save(String fileName) throws IOException, BadFileFormatException {
		
		if (fileName == null || fileName.equals("")) fileName = "agenda.txt";

		try (Writer writer = new FileWriter(fileName)) {
			List<Contact> contacts = new ArrayList<>(agenda.getContacts());
			contactsPersister.save(contacts, writer);
		}

		this.fileName = fileName;
		isDirty = false;
	}

	public void load(String fileName) throws FileNotFoundException, IOException, BadFileFormatException {
		
		try (Reader reader = new FileReader(fileName)) {
			List<Contact> contacts = contactsPersister.load(reader);
			agenda = new Agenda(contacts);
		}
		isDirty = false;
		this.fileName = fileName;
	}

	public Agenda getAgenda() { return agenda; }
	public String getFileName() { return fileName; }
	public boolean isDirty() { return isDirty; }
	public void setDirty() { isDirty = true; }
}
