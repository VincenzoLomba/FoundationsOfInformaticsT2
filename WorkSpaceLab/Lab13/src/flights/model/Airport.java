package flights.model;

public class Airport {
	private String code;
	private String name;
	private City city;

	public Airport(String code, String name, City city) {
		this.code = code;
		this.name = name;
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public City getCity() {
		return city;
	}

	@Override
	public int hashCode() {
		return (code + "##" + name).hashCode() ^ city.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Airport) {
			Airport airport = (Airport) o;
			return getCode().equals(airport.getCode())
					&& getName().equals(airport.getName())
					&& getCity().equals(airport.getCity());
		}
		return false;
	}

	@Override
	public String toString() {
		return getCity().getName() + " - " + getName() + "(" + getCode() + ")";
	}
}
