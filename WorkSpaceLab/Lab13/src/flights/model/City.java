package flights.model;

import java.util.HashSet;
import java.util.Set;

public class City {
	private String code;
	private String name;
	private int timeZone;
	private HashSet<Airport> airports;

	public City(String code, String name, int timeZone) {
		this.code = code;
		this.name = name;
		this.timeZone = timeZone;
		this.airports = new HashSet<Airport>();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getTimeZone() {
		return timeZone;
	}

	public Set<Airport> getAirports() {
		return airports;
	}

	@Override
	public int hashCode() {
		return (code + "##" + name + timeZone).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof City) {
			City city = (City) o;
			return getCode().equals(city.getCode())
					&& getName().equals(city.getName())
					&& getTimeZone() == city.getTimeZone();
		}
		return false;
	}
}
