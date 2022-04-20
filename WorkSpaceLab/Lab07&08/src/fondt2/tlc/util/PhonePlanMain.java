package fondt2.tlc.util;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Locale;

import fondt2.tlc.*;


public class PhonePlanMain {
	public static PhonePlan createPlan() {
		DayOfWeek[] workWeek = new DayOfWeek[] { DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
				DayOfWeek.FRIDAY };
		DayOfWeek[] weekEnd = new DayOfWeek[] { DayOfWeek.SATURDAY,
				DayOfWeek.SUNDAY };
		Band[] timBands = new Band[] {
				new Band(LocalTime.MIN, LocalTime.MAX, weekEnd,
						0.10),
				new Band(LocalTime.MIN, LocalTime.of(8, 0).minusNanos(1), workWeek,
						0.10),
				new Band(LocalTime.of(8, 0), LocalTime.of(18, 0).minusNanos(1),
						workWeek, 0.20),
				new Band(LocalTime.of(18, 0), LocalTime.MAX,
						workWeek, 0.10) };
		Rate tim = new Rate("TIM", timBands, 1000, 10, "+39339");

		Band[] vodafoneBands = new Band[] {
				new Band(LocalTime.MIN, LocalTime.MAX, weekEnd,
						0.15),
				new Band(LocalTime.MIN, LocalTime.of(8, 0).minusNanos(1), workWeek,
						0.15),
				new Band(LocalTime.of(8, 0), LocalTime.of(18, 0).minusNanos(1),
						workWeek, 0.30),
				new Band(LocalTime.of(18, 0), LocalTime.MAX,
						workWeek, 0.15) };
		Rate vodafone = new Rate("Vodafone", vodafoneBands, 1000, 10, "+39349");

		return new PhonePlan("SuperConveniente", new Rate[] { tim, vodafone });
	}

	public static PhoneCall[] getTestPhoneCalls() {
		LocalDateTime start = LocalDateTime.of(2019, Month.FEBRUARY, 10, 15, 21, 21);
		LocalDateTime end = LocalDateTime.of(2019, Month.FEBRUARY, 10, 15, 25, 23);
		PhoneCall call1 = new PhoneCall(start, end, "+39339123455679");

		start = LocalDateTime.of(2019, Month.FEBRUARY, 10, 16, 35, 55);
		end = LocalDateTime.of(2019, Month.FEBRUARY, 10, 16, 41, 23);
		PhoneCall call2 = new PhoneCall(start, end, "+39349987654321");

		start = LocalDateTime.of(2019, Month.FEBRUARY, 10, 17, 21, 21);
		end = LocalDateTime.of(2019, Month.FEBRUARY, 10, 18, 25, 23);
		PhoneCall call3 = new PhoneCall(start, end, "+39339123455679");

		return new PhoneCall[] { call1, call2, call3 };
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.ITALY);
		PhonePlan plan = createPlan();
		System.out.println("Check of Plan: " + plan.isValid());
		PhoneCall[] calls = getTestPhoneCalls();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		for (PhoneCall call : calls) {
			System.out.print(call);
			System.out.print(" --- ");
			System.out.println(formatter.format(plan.getCallCost(call) / 100));
		}
	}

}
