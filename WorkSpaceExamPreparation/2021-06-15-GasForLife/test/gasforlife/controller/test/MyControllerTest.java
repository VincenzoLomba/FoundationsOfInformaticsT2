package gasforlife.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gasforlife.controller.Controller;
import gasforlife.controller.MyController;
import gasforlife.model.Bill;
import gasforlife.model.Share;
import gasforlife.model.BillingFrequency;
import gasforlife.persistence.test.MyConsumptionReaderMock;
import gasforlife.persistence.test.MyFlatReaderMock;

public class MyControllerTest {
	private Controller controller;

	@BeforeEach
	void setUp() throws Exception {
//		Map<String, Flat> flats = new HashMap<>();
//		flats.put("1-1A", new Flat("1-1A", 100, "owner1"));
//		flats.put("1-1B", new Flat("1-1B", 100, "owner2"));
//		flats.put("1-2A", new Flat("1-2A", 80, "owner3"));
//		flats.put("1-2B", new Flat("1-2B", 80, "owner4"));
//		flats.put("1-3A", new Flat("1-3A", 80, "owner5"));
//		flats.put("1-3B", new Flat("1-3B", 80, "owner6"));
//		flats.put("1-4A", new Flat("1-4A", 90, "owner7"));
//		flats.put("1-4B", new Flat("1-4B", 90, "owner8"));
//		
//		Map<String, List<Double>> consumption = new HashMap<>();
//		Double d[] = {120.0,120.0,120.0,90.0,90.0,0.0,0.0,0.0,100.0,90.0,120.0,120.0};
//		List<Double> aL = new ArrayList<Double>(Arrays.asList(d));
//		consumption.put("1-1A", aL);
//		// costo 1027.5, consumo 970
//		
//		Double d1[] = {130.0,130.0,120.0,80.0,80.0,0.0,0.0,0.0,40.0,90.0,120.0,120.0};
//		aL = new ArrayList<Double>(Arrays.asList(d1));
//		consumption.put("1-1B", aL);
//		// costo 977.5, consumo 910
//		
//		Double d2[] = {100.0,100.0,100.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
//		aL = new ArrayList<Double>(Arrays.asList(d2));
//		consumption.put("1-2A", aL);
//		//costo 832.5, consumo 780
//		
//		Double d3[] = {100.0,100.0,100.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
//		aL = new ArrayList<Double>(Arrays.asList(d3));
//		consumption.put("1-2B", aL);
//		//costo 832.5, consumo 780
//		
//		Double d4[] = {100.0,100.0,100.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
//		aL = new ArrayList<Double>(Arrays.asList(d4));
//		consumption.put("1-3A", aL);
//		//costo 832.5, consumo 780
//		
//		Double d5[] = {100.0,100.0,100.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
//		aL = new ArrayList<Double>(Arrays.asList(d5));
//		consumption.put("1-3B", aL);
//		//costo 832.5, consumo 780
//		
//		Double d6[] = {100.0,100.0,90.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
//		aL = new ArrayList<Double>(Arrays.asList(d6));
//		consumption.put("1-4A", aL);
//		//costo 787.5, consumo 770
//		
//		Double d7[] = {100.0,100.0,90.0,80.0,80.0,0.0,0.0,0.0,50.0,90.0,90.0,90.0};
//		aL = new ArrayList<Double>(Arrays.asList(d7));
//		consumption.put("1-4B", aL);
//		//costo 787.5, consumo 770

		controller = new MyController(new MyFlatReaderMock(), new MyConsumptionReaderMock());
	}

	private Share extract(List<Share> result, String id) {
		for (Share elem : result) {
			if (id.equals(elem.getFlat().getId())) {
				return elem;
			}
		}
		throw new RuntimeException("....");
	}

	@Test
	void testOKMonth() {
		Bill bill = new Bill(BillingFrequency.MONTHLY, 985.0, 60.0, 925, 850.0, 1.00, 1.50, Optional.of(Month.JANUARY));
		controller.computeShare(bill);
		List<Share> result = bill.getShares();
		assertEquals(8, result.size());
		assertEquals(137.5, extract(result, "1-1A").getValue(), 0.001);
		assertEquals(152.5, extract(result, "1-1B").getValue(), 0.001);
		assertEquals(117.5, extract(result, "1-2A").getValue(), 0.001);
		assertEquals(117.5, extract(result, "1-2B").getValue(), 0.001);
		assertEquals(117.5, extract(result, "1-3A").getValue(), 0.001);
		assertEquals(117.5, extract(result, "1-3B").getValue(), 0.001);
		assertEquals(112.5, extract(result, "1-4A").getValue(), 0.001);
		assertEquals(112.5, extract(result, "1-4B").getValue(), 0.001);
		double tot = 0;
		for (Share q : result) {
			tot += q.getValue();
		}
		assertEquals(bill.getValue(), tot, 0.001);

		assertEquals(bill.getConsumption(), controller.getMonthlyTotalConsumption(0), 0.001);

		assertEquals(0, controller.getDiffCons(bill), 0.001);

	}

	@Test
	void testOK1Month() {
		Bill bill = new Bill(BillingFrequency.MONTHLY, 985.0, 60.0, 925, 900.0, 1.00, 1.50, Optional.of(Month.JANUARY));
		controller.computeShare(bill);
		assertEquals(50, controller.getDiffCons(bill), 0.001);
	}

	@Test
	void testOK2Month() {
		Bill bill = new Bill(BillingFrequency.MONTHLY, 985.0, 60.0, 925, 800.0, 1.00, 1.50, Optional.of(Month.JANUARY));
		controller.computeShare(bill);
		assertEquals(-50, controller.getDiffCons(bill), 0.001);
	}

	@Test
	void testOK3Month() {
		Bill bill = new Bill(BillingFrequency.MONTHLY, 1065.0, 60.0, 1005.0, 850.0, 1.00, 1.50, Optional.of(Month.JANUARY));
		controller.computeShare(bill);
		List<Share> result = bill.getShares();
		assertEquals(8, result.size());
		assertEquals(147.5, extract(result, "1-1A").getValue(), 0.001);
		assertEquals(162.5, extract(result, "1-1B").getValue(), 0.001);
		assertEquals(127.5, extract(result, "1-2A").getValue(), 0.001);
		assertEquals(127.5, extract(result, "1-2B").getValue(), 0.001);
		assertEquals(127.5, extract(result, "1-3A").getValue(), 0.001);
		assertEquals(127.5, extract(result, "1-3B").getValue(), 0.001);
		assertEquals(122.5, extract(result, "1-4A").getValue(), 0.001);
		assertEquals(122.5, extract(result, "1-4B").getValue(), 0.001);
		double tot = 0;
		for (Share q : result) {
			tot += q.getValue();
		}
		assertEquals(bill.getValue(), tot, 0.001);

	}

	@Test
	void testOK4Month() {
		Bill bill = new Bill(BillingFrequency.MONTHLY, 905.0, 60.0, 845, 850.0, 1.00, 1.50, Optional.of(Month.JANUARY));
		controller.computeShare(bill);
		List<Share> result = bill.getShares();
		assertEquals(8, result.size());
		assertEquals(127.5, extract(result, "1-1A").getValue(), 0.001);
		assertEquals(142.5, extract(result, "1-1B").getValue(), 0.001);
		assertEquals(107.5, extract(result, "1-2A").getValue(), 0.001);
		assertEquals(107.5, extract(result, "1-2B").getValue(), 0.001);
		assertEquals(107.5, extract(result, "1-3A").getValue(), 0.001);
		assertEquals(107.5, extract(result, "1-3B").getValue(), 0.001);
		assertEquals(102.5, extract(result, "1-4A").getValue(), 0.001);
		assertEquals(102.5, extract(result, "1-4B").getValue(), 0.001);
		double tot = 0;
		for (Share q : result) {
			tot += q.getValue();
		}
		assertEquals(bill.getValue(), tot, 0.001);
	}

	@Test
	void testOKAnnual() {
		Bill bill = new Bill(BillingFrequency.ANNUAL, 6910.0, 60.0, 6850.0, 6540, 1.00, 1.50, Optional.empty());
		controller.computeShare(bill);
		List<Share> result = bill.getShares();
		assertEquals(8, result.size());
		assertEquals(1027.5, extract(result, "1-1A").getValue(), 0.001);
		assertEquals(977.5, extract(result, "1-1B").getValue(), 0.001);
		assertEquals(832.5, extract(result, "1-2A").getValue(), 0.001);
		assertEquals(832.5, extract(result, "1-2B").getValue(), 0.001);
		assertEquals(832.5, extract(result, "1-3A").getValue(), 0.001);
		assertEquals(832.5, extract(result, "1-3B").getValue(), 0.001);
		assertEquals(787.5, extract(result, "1-4A").getValue(), 0.001);
		assertEquals(787.5, extract(result, "1-4B").getValue(), 0.001);
		double tot = 0;
		for (Share q : result) {
			tot += q.getValue();
		}
		assertEquals(bill.getValue(), tot, 0.001);
		assertEquals(bill.getConsumption(), controller.getAnnualTotalConsumption(), 0.001);
		assertEquals(0, controller.getDiffCons(bill), 0.001);

	}

	@Test
	void testOK1Annual() {
		Bill bill = new Bill(BillingFrequency.ANNUAL, 6910.0, 60.0, 6850.0, 6590, 1.00, 1.50, Optional.empty());
		controller.computeShare(bill);
		assertEquals(50, controller.getDiffCons(bill), 0.001);
	}

	@Test
	void testOK2Annual() {
		Bill bill = new Bill(BillingFrequency.ANNUAL, 6910.0, 60.0, 6850.0, 6490, 1.00, 1.50, Optional.empty());
		controller.computeShare(bill);
		assertEquals(-50, controller.getDiffCons(bill), 0.001);
	}

	@Test
	void testOK3Annual() {
		Bill bill = new Bill(BillingFrequency.ANNUAL, 7710.0, 60.0, 7650.0, 6540, 1.00, 1.50, Optional.empty());
		controller.computeShare(bill);
		List<Share> result = bill.getShares();
		assertEquals(8, result.size());
		assertEquals(1127.5, extract(result, "1-1A").getValue(), 0.001);
		assertEquals(1077.5, extract(result, "1-1B").getValue(), 0.001);
		assertEquals(932.5, extract(result, "1-2A").getValue(), 0.001);
		assertEquals(932.5, extract(result, "1-2B").getValue(), 0.001);
		assertEquals(932.5, extract(result, "1-3A").getValue(), 0.001);
		assertEquals(932.5, extract(result, "1-3B").getValue(), 0.001);
		assertEquals(887.5, extract(result, "1-4A").getValue(), 0.001);
		assertEquals(887.5, extract(result, "1-4B").getValue(), 0.001);
		double tot = 0;
		for (Share q : result) {
			tot += q.getValue();
		}
		assertEquals(bill.getValue(), tot, 0.001);
		assertEquals(bill.getConsumption(), controller.getAnnualTotalConsumption(), 0.001);
		assertEquals(0, controller.getDiffCons(bill), 0.001);

	}

	@Test
	void testOK4Annual() {
		Bill bill = new Bill(BillingFrequency.ANNUAL, 6110.0, 60.0, 6050.0, 6540, 1.00, 1.50, Optional.empty());
		controller.computeShare(bill);
		List<Share> result = bill.getShares();
		assertEquals(8, result.size());
		assertEquals(927.5, extract(result, "1-1A").getValue(), 0.001);
		assertEquals(877.5, extract(result, "1-1B").getValue(), 0.001);
		assertEquals(732.5, extract(result, "1-2A").getValue(), 0.001);
		assertEquals(732.5, extract(result, "1-2B").getValue(), 0.001);
		assertEquals(732.5, extract(result, "1-3A").getValue(), 0.001);
		assertEquals(732.5, extract(result, "1-3B").getValue(), 0.001);
		assertEquals(687.5, extract(result, "1-4A").getValue(), 0.001);
		assertEquals(687.5, extract(result, "1-4B").getValue(), 0.001);
		double tot = 0;
		for (Share q : result) {
			tot += q.getValue();
		}
		assertEquals(bill.getValue(), tot, 0.001);
		assertEquals(bill.getConsumption(), controller.getAnnualTotalConsumption(), 0.001);
		assertEquals(0, controller.getDiffCons(bill), 0.001);

	}

	@Test
	void testKO1() {
		Bill bill = new Bill(BillingFrequency.MONTHLY, 985.0, 60.0, 925, 850.0, 1.00, 1.50, Optional.empty());
		assertThrows(IllegalArgumentException.class, () -> {
			controller.computeShare(bill);
		});
	}

}
