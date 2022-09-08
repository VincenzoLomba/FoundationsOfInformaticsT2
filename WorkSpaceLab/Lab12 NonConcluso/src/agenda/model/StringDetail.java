package agenda.model;

public abstract class StringDetail extends Detail {

	private String value;

	public String getValue() { return this.value; }
	public void setValue(String value) { this.value = value; }
	
	@Override
	public abstract String getName();

	@Override
	public String getValues() { return getValue(); }
}
