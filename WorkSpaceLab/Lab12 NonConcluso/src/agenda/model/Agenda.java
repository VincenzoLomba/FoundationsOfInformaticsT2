package agenda.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Agenda {
	
	private SortedSet<Contact> contactSet;
	
	public Agenda () { this.contactSet = new TreeSet<>(); }
	public Agenda (Collection<Contact> contacts) { this.contactSet = new TreeSet<>(contacts); }
	
	public void addContact (Contact contact) { contactSet.add(contact); }
	public void removeContact (Contact contact) { contactSet.remove(contact); }
	
	public Set<Contact> getContacts () { return new HashSet<>(contactSet); }
	
	public Optional<Contact> getContact (String name, String surname) {
		return contactSet.stream().filter(c -> c.getName().equals(name) && c.getSurname().equals(surname)).findFirst();
	}
	
	public Optional<Contact> getContact (int index) {
		
		ArrayList<Contact> al = new ArrayList<>(contactSet);
		return index < 0 || index >= al.size() ? Optional.empty() : Optional.of(al.get(index));
	}
	
	public SortedSet<Contact> searchContacts (String surname) {
		
		return new TreeSet<>(contactSet.stream().filter(c -> c.getSurname().equals(surname)).toList());
	}
}
