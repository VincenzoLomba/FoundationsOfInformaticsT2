package taxcomparator.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Fascia {
	
	private double da, a, aliquota;
	private NumberFormat percentageFormatter;
	private DecimalFormat currencyFormatter;

	public Fascia(double da, double a, double aliquota) {
		
		if (da < 0 || a < 0 || da >= a || aliquota < 0 || aliquota > 1)
			throw new IllegalArgumentException("parametri fascia errati");
		this.da = da;
		this.a = a;
		this.aliquota = aliquota;
		percentageFormatter = NumberFormat.getPercentInstance(Locale.ITALY);
		currencyFormatter = new DecimalFormat("¤ #,##0.##");
	}

	public Fascia(double da, double  aliquota) { this(da, Double.MAX_VALUE, aliquota); }

	public Fascia(double da, double  a, String aliquota) throws ParseException {
		
		percentageFormatter = NumberFormat.getPercentInstance(Locale.ITALY);
		currencyFormatter = new DecimalFormat("¤ #,##0.##");
		this.da = da;
		this.a = a;
		this.aliquota = percentageFormatter.parse(aliquota).doubleValue();
		if (da < 0 || a < 0 || da >= a || this.aliquota < 0 || this.aliquota > 1)
			throw new IllegalArgumentException("parametri fascia errati");
	}

	public Fascia(double da, String aliquota) throws ParseException {
		this(da, Double.MAX_VALUE, aliquota);
	}

	public double getMin() { return da; }
	public double getMax() { return a; }
	public double getAliquota() { return aliquota; }

	@Override
	public String toString() {
		return "Fascia " +  (
				a==Double.MAX_VALUE 
						? 
				"oltre " + currencyFormatter.format(da) + ", aliquota " + percentageFormatter.format(aliquota)
						:				
				"da " + currencyFormatter.format(da) + " a " + currencyFormatter.format(a) + ", aliquota " + percentageFormatter.format(aliquota)
				);
	}
}
