package gasforlife.model.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Month;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import gasforlife.model.Bill;
import gasforlife.model.BillingFrequency;
import gasforlife.model.Flat;
import gasforlife.model.Share;

public class BillTest {
	@Test
	public void testOK_1() {
		Bill bill = new Bill(BillingFrequency.ANNUAL, 1d, 2d, 3d, 4d, 5d, 6d, Optional.of(Month.MARCH));
		Assert.assertEquals(BillingFrequency.ANNUAL, bill.getBillingFrequency());
		Assert.assertEquals(1d, bill.getValue(), 0.01);
		Assert.assertEquals(2d, bill.getFixedCost(), 0.01);
		Assert.assertEquals(3d, bill.getVariableCost(), 0.01);
		Assert.assertEquals(4d, bill.getConsumption(), 0.01);
		Assert.assertEquals(5d, bill.getCostm3(), 0.01);
		Assert.assertEquals(6d, bill.getExtraCostm3(), 0.01);
		Assert.assertEquals(Month.MARCH, bill.getMonth().get());
	}

	@Test
	public void testOK_2() {
		Bill bill = new Bill(BillingFrequency.MONTHLY, 1d, 2d, 3d, 4d, 5d, 6d, Optional.empty());
		Assert.assertEquals(BillingFrequency.MONTHLY, bill.getBillingFrequency());
		Assert.assertEquals(1d, bill.getValue(), 0.01);
		Assert.assertEquals(2d, bill.getFixedCost(), 0.01);
		Assert.assertEquals(3d, bill.getVariableCost(), 0.01);
		Assert.assertEquals(4d, bill.getConsumption(), 0.01);
		Assert.assertEquals(5d, bill.getCostm3(), 0.01);
		Assert.assertEquals(6d, bill.getExtraCostm3(), 0.01);
		Assert.assertTrue(bill.getMonth().isEmpty());
	}

	@Test
	public void testShares() {
		Bill bill = new Bill(BillingFrequency.MONTHLY, 1d, 2d, 3d, 4d, 5d, 6d, Optional.empty());
		Assert.assertEquals(0, bill.getShares().size());
		Share q1 = new Share(new Flat("1", 100d, "Test"), 0, 0);
		bill.addShare(q1);
		Assert.assertEquals(1, bill.getShares().size());
		Assert.assertEquals(q1, bill.getShares().get(0));
	}

	@Test
	public void testKO_1() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			new Bill(null, 1d, 1d, 1d, 1d, 1d, 1d, Optional.empty());
		});
	}

	@Test
	public void testKO_2() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			new Bill(BillingFrequency.ANNUAL, -1d, 1d, 1d, 1d, 1d, 1d, Optional.empty());
		});
	}

	@Test
	public void testKO_3() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			new Bill(BillingFrequency.ANNUAL, 1d, -1d, 1d, 1d, 1d, 1d, Optional.empty());
		});
	}

	@Test
	public void testKO_4() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			new Bill(BillingFrequency.ANNUAL, 1d, 1d, -1d, 1d, 1d, 1d, Optional.empty());
		});
	}

	@Test
	public void testKO_5() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			new Bill(BillingFrequency.ANNUAL, 1d, 1d, 1d, -1d, 1d, 1d, Optional.empty());
		});
	}

	@Test
	public void testKO_6() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			new Bill(BillingFrequency.ANNUAL, 1d, 1d, 1d, 1d, -1d, 1d, Optional.empty());
		});
	}

	@Test
	public void testKO_7() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			new Bill(BillingFrequency.ANNUAL, 1d, 1d, 1d, 1d, 1d, -1d, Optional.empty());
		});
	}

	@Test
	public void testKO_8() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			new Bill(BillingFrequency.ANNUAL, 1d, 1d, 1d, 1d, 1d, 1d, null);
		});
	}

}
