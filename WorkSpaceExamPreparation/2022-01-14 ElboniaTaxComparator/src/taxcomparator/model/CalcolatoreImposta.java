package taxcomparator.model;

public interface CalcolatoreImposta {
	
	public double calcolaImposta(double reddito, Fasce fasce);
	public String getLog();

}
