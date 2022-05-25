package flights.model;

public class Aircraft {
	private String code;
	private String description;
	private int seats;

	public Aircraft(String code, String description, int seats) {
		this.code = code;
		this.description = description;
		this.seats = seats;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public int getSeats() {
		return seats;
	}

	@Override
	public int hashCode() {
		return (code + "##" + description + "##" + seats).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Aircraft) {
			Aircraft aircraft = (Aircraft) o;
			return getCode().equals(aircraft.getCode())
					&& getDescription().equals(aircraft.getDescription())
					&& getSeats() == aircraft.getSeats();
		}
		return false;
	}
}
