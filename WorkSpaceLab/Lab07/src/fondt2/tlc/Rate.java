package fondt2.tlc;

import java.util.Arrays;

import lombok.Getter;

public class Rate {
	
	@Getter private String name;
	@Getter private Band[] bands;
	private int intervallInMills;
	private double startCallCast;
	private String numberRoot;
	
	public Rate(String name, Band[] bands, int intervallInMills, double startCallCast, String numberRoot) {
		
		this.name = name;
		this.bands = Arrays.copyOf(bands, bands.length);
		this.intervallInMills = intervallInMills;
		this.startCallCast = startCallCast;
		this.numberRoot = numberRoot;
	}
	
	public boolean isApplicableTo (String destinationNumber) { return destinationNumber.startsWith(numberRoot); }
	
	public boolean isValid () { return true; }
	
	public double getCallCost (PhoneCall call) { return 0.0; }

}
