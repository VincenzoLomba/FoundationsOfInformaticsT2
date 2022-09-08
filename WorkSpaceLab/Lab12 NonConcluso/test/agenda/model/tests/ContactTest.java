package agenda.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import agenda.model.Address;
import agenda.model.Contact;
import agenda.model.Detail;
import agenda.model.EMail;
import agenda.model.Phone;


public class ContactTest {
	Contact contact;
	
	@Test
	public void testContatto() {
		contact = new Contact("Roberta", "Calegari");
		assertEquals(contact.getName(),"Roberta");
		assertEquals(contact.getSurname(),"Calegari");
		assertEquals(contact.getDetailList().size(),0);
		
		contact.setName("Roberta Sofia");
		assertEquals(contact.getName(),"Roberta Sofia");
		contact.setSurname("CalegariXX");
		assertEquals(contact.getSurname(),"CalegariXX");
		
		List<Detail> detailList = contact.getDetailList();
		Phone p = new Phone();
		p.setDescription("Cell");
		p.setValue("333122334");
		detailList.add(p);
		assertEquals(contact.getDetailList().size(),1);
		assertEquals(((Phone)contact.getDetailList().get(0)).getName(),"Phone");
		assertEquals(((Phone)contact.getDetailList().get(0)).getDescription(),"Cell");
		assertEquals(((Phone)contact.getDetailList().get(0)).getValue(),"333122334");
		p = new Phone();
		p.setDescription("Home");
		p.setValue("0544881122");
		detailList.add(p);
		assertEquals(contact.getDetailList().size(),2);
		assertEquals(((Phone)contact.getDetailList().get(1)).getName(),"Phone");
		assertEquals(((Phone)contact.getDetailList().get(1)).getDescription(),"Home");
		assertEquals(((Phone)contact.getDetailList().get(1)).getValue(),"0544881122");
		
		EMail e = new EMail();
		e.setDescription("Job");
		e.setValue("roberta.calegari@unibo.it");
		detailList.add(e);
		assertEquals(contact.getDetailList().size(),3);
		assertEquals(((EMail)contact.getDetailList().get(2)).getName(),"EMail");
		assertEquals(((EMail)contact.getDetailList().get(2)).getDescription(),"Job");
		assertEquals(((EMail)contact.getDetailList().get(2)).getValue(),"roberta.calegari@unibo.it");
		
		e = new EMail();
		e.setDescription("Home");
		e.setValue("robbyXX@gmail.com");
		detailList.add(e);
		assertEquals(contact.getDetailList().size(),4);
		assertEquals(((EMail)contact.getDetailList().get(3)).getName(),"EMail");
		assertEquals(((EMail)contact.getDetailList().get(3)).getDescription(),"Home");
		assertEquals(((EMail)contact.getDetailList().get(3)).getValue(),"robbyXX@gmail.com");
		
		Address a = new Address();
		a.setDescription("Home");
		a.setCity("Bologna");
		a.setStreet("Giardini del Pesco");
		a.setNumber("7");
		a.setZipCode("40100");
		a.setState("Italia");
		detailList.add(a);
		assertEquals(contact.getDetailList().size(),5);
		assertEquals(((Address)contact.getDetailList().get(4)).getName(),"Address");
		assertEquals(((Address)contact.getDetailList().get(4)).getDescription(),"Home");
		assertEquals(((Address)contact.getDetailList().get(4)).getValues(),"Giardini del Pesco, 7 - 40100 Bologna - Italia");
		
		System.out.println(contact);
		String contattoStampato =  
				"Roberta Sofia CalegariXX\n" + 
				"Phone :: Cell" + "\n"+
				"333122334" + "\n"+
				"Phone :: Home" + "\n"+
				"0544881122" + "\n"+
				"EMail :: Job" + "\n"+
				"roberta.calegari@unibo.it" + "\n"+
				"EMail :: Home" + "\n"+
				"robbyXX@gmail.com" + "\n"+
				"Address :: Home" + "\n"+
				"Giardini del Pesco, 7 - 40100 Bologna - Italia";
		assertTrue(contact.toString().contains(contattoStampato));
		
	}	
	
	@Test
	public void testCompareTo() {
		contact = createRobertaCalegariFull();
		Contact contact1 = createRobertaCalegariFull();
		
		assertTrue(contact.compareTo(contact1)==0);
		
		contact1 = createAmbraMolesiniFull();
		//System.out.println(contact.compareTo(contact1));
		assertTrue(contact.compareTo(contact1)<0);
		
		Contact contact2 = createEnricoDentiFull();
		assertTrue(contact1.compareTo(contact2)>0);
		
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
