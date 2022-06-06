package gasforlife.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class Bill {
	
	private BillingFrequency billFreq;  /* Indicativo se mensile o annuale. */
	private double consumption;			/* Consumo effettuato in m^3.	    */
	private double costm3;				/* Costo del Gas (in m^3).		    */
	private double extraCostm3;			/* Costo di Extra Gas (in m^3).     */
	private double fixedCost;			/* Costi fissi (gestione utenza).   */
	private Optional<Month> month;		/* Mese di riferimento SE mensile.  */
	private List<Share> quotes;			/* Lista delle quote.               */
	private double value;				/* Importo totale.                  */
	private double variableCost;        /* Costi variabili.                 */
	
	public Bill (BillingFrequency billFreq, double total, double fixedCost, double variableCost, double consumption, double costM3, double extraCostM3, Optional<Month> month) {
		
		if (billFreq == null || total < 0 || fixedCost < 0 || variableCost < 0 || consumption < 0 || costM3 < 0 || extraCostM3 < 0 || month == null)
			throw new IllegalArgumentException(
				"Nell'instanziare un oggetto di classe " + Bill.class.getSimpleName() + " sono stati passati al costruttore parametri nulli o minori di zero."
			);
		this.billFreq = billFreq;
		this.value = total;
		this.fixedCost = fixedCost;
		this.variableCost = variableCost;
		this.consumption = consumption;
		this.costm3 = costM3;
		this.extraCostm3 = extraCostM3;
		this.month = month;
		this.quotes = new ArrayList<>();
	}
	
	public boolean addShare (Share quote) { return quotes.add(quote); }
	
	public String getMonthAsString () {
		return month.isEmpty() ?
			"mese non presente" :
			LocalDate.now().withMonth(month.get().ordinal() + 1).format(DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ITALY))
		;
	}
	
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		if (billFreq == BillingFrequency.MONTHLY) stringBuilder.append("Bolletta Mensile"); else stringBuilder.append("Bolletta Annuale");
		if (month.isPresent()) stringBuilder.append(" di ").append(getMonthAsString()); 
		stringBuilder.append(System.lineSeparator()).append("- Importo totale: ").append(value).append(System.lineSeparator())
					 .append("- Costi fissi: ").append(fixedCost).append(System.lineSeparator())
					 .append("- Costi variabili: ").append(variableCost).append(System.lineSeparator())
					 .append("- Consumo totale (in m^3): ").append(consumption).append(System.lineSeparator())
					 .append("- Costo (al m^3): ").append(costm3).append(System.lineSeparator())
					 .append("- Costo extra (al m^3): ").append(extraCostm3)
		;
		if (quotes.size() == 0) return stringBuilder.append(System.lineSeparator()).append("Non vi sono quote.").toString();
		Collections.sort(quotes, Comparator.comparing(s -> s.getFlat().getId()));
		quotes.stream().forEach(q -> stringBuilder.append(System.lineSeparator()).append(q.toString()));
		return stringBuilder.toString();
	}

	public BillingFrequency getBillingFrequency() { return billFreq; }
	public double getConsumption() { return consumption; }
	public double getCostm3() { return costm3; }
	public double getExtraCostm3() { return extraCostm3; }
	public double getFixedCost() { return fixedCost; }
	public Optional<Month> getMonth() { return month; }
	public List<Share> getShares() { return quotes; }
	public double getValue() { return value; }
	public double getVariableCost() { return variableCost; }
}
