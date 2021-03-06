package fondt2.tlc;

import java.util.Arrays;

import lombok.Getter;

public class PhonePlan {
	
	@Getter private String name;  /* See: https://projectlombok.org/features/GetterSetter */
	private Rate[] rates;
	
	public PhonePlan(String name, Rate[] rates) {
		
		this.name = name;
		this.rates = Arrays.copyOf(rates, rates.length);
	}
	
	public double getCallCost (PhoneCall call) {
		
		Rate rate;
		return (rate = getRate(call)) == null ? -1 : rate.getCallCost(call);
	}

	public boolean isValid () {
		
		/* The "name" is assumed correct */
		return rates != null
			&& rates.length > 0
			&& Arrays.stream(rates).allMatch(r -> r.isValid())
		;
	}
	
	private Rate getRate (PhoneCall phoneCall) {
		
		if (rates == null) return null;
		for (Rate rate : rates) if (rate.isApplicableTo(phoneCall.getDestNumber())) return rate;
		return null;
	}
}
