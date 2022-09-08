package agenda.model;

public class Address extends Detail {
	
	private String street;
	private String number;
	private String zipCode;
	private String city;
	private String state;

	@Override
	public String getName() {
		return "Address";
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	@Override
	public String getValues() {
		return getStreet() + ", " + getNumber() + " - " + getZipCode() + " " + getCity() + " - " + getState();
	}
}
