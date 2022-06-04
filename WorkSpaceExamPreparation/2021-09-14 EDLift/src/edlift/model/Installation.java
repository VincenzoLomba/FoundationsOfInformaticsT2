package edlift.model;

public class Installation {
	
	private String name;
	private Lift lift;
	
	public Installation(String name, Lift lift) {
		this.name = name;
		this.lift = lift;
	}

	@Override
	public String toString() { return name + ": " + lift.toString(); }
	public String getName() { return name; }
	public Lift getLift() { return lift; }	
}
