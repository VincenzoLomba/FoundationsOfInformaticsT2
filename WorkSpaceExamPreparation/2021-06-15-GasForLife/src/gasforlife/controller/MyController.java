package gasforlife.controller;

import java.util.List;
import java.util.Map;

import gasforlife.model.Bill;
import gasforlife.model.BillingFrequency;
import gasforlife.model.Flat;
import gasforlife.model.Share;
import gasforlife.persistence.ConsumptionReader;
import gasforlife.persistence.FlatReader;

public class MyController implements Controller {
	
	private Map<String, List<Double>> gasConsumption;
	private Map<String, Flat> flats;

	public MyController(FlatReader flatReader, ConsumptionReader conReader) {
		this.gasConsumption = conReader.getItems();
		this.flats = flatReader.getItems();
	}

	@Override
	public void computeShare(Bill bill) {
		switch(bill.getBillingFrequency()) {
			case ANNUAL:  computeAnnualCost(bill); break;
			case MONTHLY: computeMonthlyCost(bill); break;
		}
	}

	private void computeAnnualCost(Bill bill) {
		double totalPrice = 0;
		for (String flat : flats.keySet()) {
			double price = 0;
			double totalCons = 0;
			for (int month = 0; month < 12; month++) {
				double realCons = gasConsumption.get(flat).get(month);
				price += this.getMonthlyCostForFlat(flats.get(flat), bill, realCons);
				totalCons += realCons;
			}
			totalPrice += price;
			price += bill.getFixedCost() / flats.size();
			bill.addShare(new Share(flats.get(flat), totalCons, price));
		}
		updateShare(bill, totalPrice);
	}

	private void updateShare(Bill bill, double totalPrice) {
		double update = (bill.getVariableCost() - totalPrice) / flats.size();
		for (Share q : bill.getShares()) {
			q.addCorrection(update);
		}
	}

	private void computeMonthlyCost(Bill bill) {
		
		double totalPrice = 0;
		if (bill.getMonth().isEmpty()) throw new IllegalArgumentException("Fornita al Controller bolletta mensile che non presenta indicazione del mese.");
		for (String flat : flats.keySet()) {
			double realCons = gasConsumption.get(flat).get(bill.getMonth().get().ordinal());
			double price = getMonthlyCostForFlat(flats.get(flat), bill, realCons);
			totalPrice += price;
			price += bill.getFixedCost() / flats.size();
			bill.addShare(new Share(flats.get(flat), realCons, price));
		}
		updateShare(bill, totalPrice);
	}
	
	private double getMonthlyCostForFlat(Flat flat, Bill bill, double realCons) {
		
		return realCons >= flat.getMaxConsumption() ?
			flat.getMaxConsumption() * bill.getCostm3() + (realCons - flat.getMaxConsumption()) * bill.getExtraCostm3()
			:
				realCons * bill.getCostm3()
		;
	}

	@Override
	public double getMonthlyTotalConsumption(int index) {
		double total = 0;
		for (String flat : flats.keySet()) {
			total += gasConsumption.get(flat).get(index);
		}
		return total;
	}

	@Override
	public double getAnnualTotalConsumption() {
		double total = 0;

		for (int i = 0; i < 12; i++) {
			total += getMonthlyTotalConsumption(i);
		}

		return total;
	}

	@Override
	public double getDiffCons(Bill bill) {
		
		if (bill.getBillingFrequency() == BillingFrequency.MONTHLY && bill.getMonth().isEmpty())
			throw new IllegalArgumentException("Fornita al Controller bolletta mensile che non presenta indicazione del mese."
		);
		return bill.getBillingFrequency() == BillingFrequency.MONTHLY ? 
			bill.getConsumption() - getMonthlyTotalConsumption(bill.getMonth().get().ordinal())
			:
			bill.getConsumption() - getAnnualTotalConsumption()
		;
	}
}
