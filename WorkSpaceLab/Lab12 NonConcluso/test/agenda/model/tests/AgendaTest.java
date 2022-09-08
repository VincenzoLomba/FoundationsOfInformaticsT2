package agenda.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import agenda.model.Address;
import agenda.model.Agenda;
import agenda.model.Contact;
import agenda.model.Detail;
import agenda.model.EMail;
import agenda.model.Phone;


public class AgendaTest {
	Agenda agenda;

	@Test
	public void testCostruttoreDefault() {
		agenda = new Agenda();
		assertEquals(agenda.getContacts().size(),0);
	}	
	
	@Test
	public void testCostruttoreCollection() {
		ArrayList<Contact> al = new ArrayList<Contact>(); 
		Contact c1 = createRobertaCalegariFull();
		Contact c2 = createAmbraMolesiniFull();
		Contact c3 = createEnricoDentiFull();
		al.add(c1);
		al.add(c2);
		al.add(c3);
		agenda = new Agenda(al);
		assertEquals(agenda.getContacts().size(),3);
	}	

	@Test
	public void testadd() {
		agenda = new Agenda();
		assertEquals(agenda.getContacts().size(),0);
		Contact c1 = createRobertaCalegariFull();
		agenda.addContact(c1);
		assertEquals(agenda.getContacts().size(),1);
	}
	
	@Test
	public void testremove() {
		agenda = new Agenda();
		assertEquals(agenda.getContacts().size(),0);
		Contact c1 = createRobertaCalegariFull();
		Contact c2 = createAmbraMolesiniFull();
		agenda.addContact(c1);
		assertEquals(agenda.getContacts().size(),1);
		agenda.addContact(c2);
		assertEquals(agenda.getContacts().size(),2);
		agenda.removeContact(c1);
		assertEquals(agenda.getContacts().size(),1);
	}
	
	@Test
	public void testGetter() {
		agenda = new Agenda();
		assertEquals(agenda.getContacts().size(),0);
		Contact c1 = createRobertaCalegariFull();
		Contact c2 = createAmbraMolesiniFull();
		Contact c3 = createEnricoDentiFull();
		agenda.addContact(c1);
		agenda.addContact(c2);
		agenda.addContact(c3);
		
		Set<Contact> set =agenda.getContacts();
		assertEquals(set.size(),3);
		assertTrue(agenda.getContact(0).get().equals(c1));
		assertTrue(agenda.getContact(1).get().equals(c3));
		assertTrue(agenda.getContact(2).get().equals(c2));
		
		assertTrue(agenda.getContact("Roberta", "Calegari").get().equals(c1));
		assertTrue(agenda.getContact("Ambra", "Molesini").get().equals(c2));
		assertTrue(agenda.getContact("Enrico", "Denti").get().equals(c3));		
	}
	
	@Test
	public void testSearch() {
		agenda = new Agenda();
		assertEquals(agenda.getContacts().size(),0);
		Contact c2 = createAmbraMolesiniFull();
		Contact c3 = createEnricoDentiFull();
		
		Contact contact = new Contact("Antonella", "Molesini");
		List<Detail> detailList = contact.getDetailList();
		Phone p = new Phone();
		p.setDescription("Cell");
		p.setValue("34567829");
		detailList.add(p);
		p = new Phone();
		p.setDescription("Home");
		p.setValue("09876544373");
		detailList.add(p);

		EMail e = new EMail();
		e.setDescription("Job");
		e.setValue("antonella.molesini@unibo.it");
		detailList.add(e);
		e = new EMail();
		e.setDescription("Home");
		e.setValue("antoXX@gmail.com");
		detailList.add(e);

		Address a = new Address();
		a.setDescription("Home");
		a.setCity("Padova");
		a.setStreet("Giardini del Melo");
		a.setNumber("19");
		a.setZipCode("41562");
		a.setState("Italia");
		detailList.add(a);
		
		agenda.addContact(contact);
		agenda.addContact(c2);
		agenda.addContact(c3);
		
		assertEquals(agenda.searchContacts("Molesini").size(),2);	
		assertTrue(agenda.searchContacts("Molesini").contains(c2));
		assertTrue(agenda.searchContacts("Molesini").contains(contact));
		assertEquals(agenda.searchContacts("Denti").size(),1);	
		assertTrue(agenda.searchContacts("Denti").contains(c3));
	}
	
	private static Contact createRobertaCalegariFull() {
		Contact contact = new Contact("Roberta", "Calegari");
		List<Detail> detailList = contact.getDetailList();
		Phone p = new Phone();
		p.setDescription("Cell");
		p.setValue("333122334");
		detailList.add(p);
		p = new Phone();
		p.setDescription("Home");
		p.setValue("0544881122");
		detailList.add(p);

		EMail e = new EMail();
		e.setDescription("Job");
		e.setValue("roberta.calegari@unibo.it");
		detailList.add(e);
		e = new EMail();
		e.setDescription("Home");
		e.setValue("robbyXX@gmail.com");
		detailList.add(e);

		Address a = new Address();
		a.setDescription("Home");
		a.setCity("Bologna");
		a.setStreet("Giardini del Pesco");
		a.setNumber("7");
		a.setZipCode("40100");
		a.setState("Italia");
		detailList.add(a);
		
		return contact;
	}

	private static Contact createAmbraMolesiniFull() {
		Contact contact = new Contact("Ambra", "Molesini");
		List<Detail> detailList = contact.getDetailList();
		Phone p = new Phone();
		p.setDescription("Cell");
		p.setValue("333122334");
		detailList.add(p);
		p = new Phone();
		p.setDescription("Home");
		p.setValue("0544881122");
		detailList.add(p);

		EMail e = new EMail();
		e.setDescription("Job");
		e.setValue("ambra.molesini@unibo.it");
		detailList.add(e);
		e = new EMail();
		e.setDescription("Home");
		e.setValue("amberXX@gmail.com");
		detailList.add(e);

		Address a = new Address();
		a.setDescription("Home");
		a.setCity("Bologna");
		a.setStreet("Giardini del Melo");
		a.setNumber("12");
		a.setZipCode("40100");
		a.setState("Italia");
		detailList.add(a);
		
		return contact;
	}

	private static Contact createEnricoDentiFull() {
		Contact contact = new Contact("Enrico", "Denti");
		List<Detail> detailList = contact.getDetailList();
		Phone p = new Phone();
		p.setDescription("Cell");
		p.setValue("123456789");
		detailList.add(p);
		p = new Phone();
		p.setDescription("Home");
		p.setValue("0567840938302");
		detailList.add(p);

		EMail e = new EMail();
		e.setDescription("Job");
		e.setValue("enrico.denti@unibo.it");
		detailList.add(e);
		e = new EMail();
		e.setDescription("Home");
		e.setValue("enrico@gmail.com");
		detailList.add(e);

		Address a = new Address();
		a.setDescription("Home");
		a.setCity("Bologna");
		a.setStreet("Viale dell'Imperatore");
		a.setNumber("132");
		a.setZipCode("40100");
		a.setState("Italia");
		detailList.add(a);
		
		return contact;
	}

}


