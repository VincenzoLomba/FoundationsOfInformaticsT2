package gasforlife.controller;

import gasforlife.model.Bill;

public interface Controller {
	
	void computeShare(Bill bill);
	double getMonthlyTotalConsumption(int index);
	double getAnnualTotalConsumption();
	double getDiffCons(Bill bill);
}
