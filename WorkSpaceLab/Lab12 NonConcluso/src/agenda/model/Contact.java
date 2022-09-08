package agenda.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contact implements Comparable<Contact>{

	private List<Detail> detailList;
	private String name;
	private String surname;
	
	public Contact (String name, String surname) {
		
		this.name = name;
		this.surname = surname;
		detailList = new ArrayList<>();
	}
	
	public Contact (String name, String surname, List<Detail> detailList) {
		
		this.name = name;
		this.surname = surname;
		this.detailList = detailList;
	}
	
	public String toString () {
		
		return new StringBuilder(name).append(" ").append(surname)
			.append(
				detailList.stream().map(d -> d.toString()).reduce("", (s1, s2) -> new StringBuilder().append(s1).append("\n").append(s2).toString())
			).toString();
	}
	
	@Override
	public int hashCode() { return Objects.hash(detailList, name, surname); }

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Contact other = (Contact) obj;
		return Objects.equals(detailList, other.detailList) && Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getSurname() { return surname; }
	public void setSurname(String surname) { this.surname = surname; }
	public List<Detail> getDetailList() { return detailList; }

	@Override
	public int compareTo(Contact c) {
		
		int response = surname.compareTo(c.getSurname());
		return response == 0 ? name.compareTo(c.getName()) : response;
	}

}
