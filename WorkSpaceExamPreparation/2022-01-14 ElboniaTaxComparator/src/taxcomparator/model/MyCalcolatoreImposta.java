package taxcomparator.model;

import java.text.DecimalFormat;

public class MyCalcolatoreImposta implements CalcolatoreImposta {

	private String log = null;
	
	public MyCalcolatoreImposta () {}
	
	private int determinaIndiceFasciaMax (double imponibile, Fasce fasce) {
		
		/* In order to determinate the correct  "Fascia" using the "imponibile" value, for each "Fascia": minimum value is excluded and maximum value is included. */
		for (int index = fasce.getFasce().size() - 1 ; index > 0 ; --index) {
			Fascia fascia = fasce.getFasce().get(index);
			if (imponibile > fascia.getMin() && imponibile <= fascia.getMax())
				return index;
		}
		return 0;
	}
	
	@Override
	public double calcolaImposta(double reddito, Fasce fasce) {
		
		DecimalFormat currencyFormatter = new DecimalFormat("¤ #,##0.##");
		double response = 0.0;
		
		StringBuilder log = new StringBuilder();
		log.append("Imponibile lordo = ").append(currencyFormatter.format(reddito))
		   .append(", no-tax area = ").append(currencyFormatter.format(fasce.getNoTaxArea()));
		
		double imponibileNR = reddito - fasce.getNoTaxArea();
		if (imponibileNR <= 0 ) {
			this.log = log.append(", imponibile restante = ").append(currencyFormatter.format(0)).toString();
			return 0.0;
		}
		log.append(", imponibile netto = ").append(currencyFormatter.format(imponibileNR)).append(System.lineSeparator());
		
		do {
			int index = determinaIndiceFasciaMax(imponibileNR, fasce);
			Fascia fascia = fasce.getFasce().get(index);
			log.append(fascia.toString()).append(System.lineSeparator());
			double imposta = (imponibileNR - fascia.getMin()) * fascia.getAliquota(); 
			response += imposta;
			log.append("Imponibile corrente = ").append(currencyFormatter.format(imponibileNR)).append(", imposta = ").append(currencyFormatter.format(imposta));
			imponibileNR = fascia.getMin();
			log.append(", imponibile restante = ").append(currencyFormatter.format(imponibileNR)).append(imponibileNR == 0 ? "" : System.lineSeparator());
			
		} while (imponibileNR > 0);
		this.log = log.toString();
		
		return response;
	}

	@Override
	public String getLog() { return log == null ? "" : log; }

}
