package agenda.model;

public abstract class Detail{

	private String description;

	public abstract String getName();
	public abstract String getValues();

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	@Override
	public String toString() { return getName() + " :: " + getDescription() + "\n" + getValues(); }
}
