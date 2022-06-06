package gasforlife.model;

import java.text.NumberFormat;

public class Share {
	
	private Flat flat;
	private double consumption;
	private double value;

	public Share(Flat flat, double consumption, double value) {
	    this.flat = flat;
		this.consumption = consumption;
		this.value = value;
	}
	
	public Flat getFlat() { return flat; }
	public double getConsumption() { return consumption; }
	public double getValue() { return value; }

	public void addCorrection(double corr) { value+=corr; }

	@Override
	public String toString() {
		NumberFormat fN = NumberFormat.getNumberInstance();
		fN.setMaximumFractionDigits(2);
		return "Quota dell' " + flat.toString() + 
				", consumo= " + fN.format(consumption) + 
				", valore della quota= " + fN.format(value);
	}


}
