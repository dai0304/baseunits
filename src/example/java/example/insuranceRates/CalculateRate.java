/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package example.insuranceRates;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import jp.xet.timeandmoney.base.Ratio;
import jp.xet.timeandmoney.base.Rounding;
import jp.xet.timeandmoney.intervals.Interval;
import jp.xet.timeandmoney.intervals.IntervalMap;
import jp.xet.timeandmoney.intervals.LinearIntervalMap;
import jp.xet.timeandmoney.money.Money;
import jp.xet.timeandmoney.money.MoneyTimeRate;
import jp.xet.timeandmoney.money.Proration;
import jp.xet.timeandmoney.time.CalendarDate;
import jp.xet.timeandmoney.time.CalendarInterval;
import jp.xet.timeandmoney.time.Duration;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Example.
 * 
 * @version $Id$
 * @author daisuke
 */
public class CalculateRate {
	
	/** 契約日 */
	private static final CalendarDate policyEffectiveDate = CalendarDate.date(2004, 11, 7);
	

	/**
	 * Example.
	 */
	@Test
	@Ignore
	public void testLookUpMoreComplicated() {
//		BusinessCalendar paymentCalendar = new BusinessCalendar();
//		CalendarInterval paymentQuarter = paymentCalendar.currentQuarter();
//		
//		CalendarDate birthdate = null;
//		Duration age = birthdate.through(paymentQuarter.start()).length();
//		Rate rate = insuranceSchedule().get(age);
//		Money quarterlyPayment = rate.times(Duration.quarters(1));
//		CalendarDate effectiveDate = null;
//		CalendarInterval remainingQuarter = paymentQuarter.cropForwardFrom(effectiveDate);
//		BigDecimal ratio = remainingQuarter.duration().dividedBy(paymentQuarter);
//		Money firstPayment = quarterlyPayment.prorate(ratio);
	}
	
	/**
	 * Example.
	 */
	
	@Test
	public void testLookUpRate() {
		CalendarDate birthdate = CalendarDate.date(1963, 4, 6);
		Duration ageOnEffectiveDate = birthdate.through(policyEffectiveDate).lengthInMonths();
		Money monthlyPremium = insuranceSchedule().get(ageOnEffectiveDate);
		assertThat(monthlyPremium, is(Money.dollars(150.00)));
	}
	
	/**
	 * 初月の日割り計算の例。
	 */
	@Test
	public void testProrateFirstMonth() {
		// 月額 150.00 USD
		Money monthlyPremium = Money.dollars(150.00);
		
		// 契約月の残り期間
		CalendarInterval entireMonth = policyEffectiveDate.month();
		CalendarInterval remainderOfMonth = policyEffectiveDate.through(entireMonth.end());
		
		// 契約月の残り日数 ÷ 契約月の全日数
		Ratio partOfPayment = Ratio.of(remainderOfMonth.lengthInDaysInt(), entireMonth.lengthInDaysInt());
		
		// 切り捨てで日割り適用
		Money firstPayment = monthlyPremium.applying(partOfPayment, Rounding.DOWN);
		assertThat(firstPayment, is(Money.dollars(120.00)));
		
		// Alternative, equivalent calculation
		partOfPayment = remainderOfMonth.length().dividedBy(entireMonth.length());
		firstPayment = Proration.partOfWhole(monthlyPremium, partOfPayment);
		assertThat(firstPayment, is(Money.dollars(120.00)));
	}
	
	/**
	 * Example.
	 */
	@Test
	public void testQuarterlyPremiumPayment() {
		MoneyTimeRate premium = Money.dollars(150.00).per(Duration.months(1));
		Money quarterlyPayment = premium.over(Duration.quarters(1));
		assertThat(quarterlyPayment, is(Money.dollars(450.00)));
	}
	
	private IntervalMap<Duration, Money> insuranceSchedule() {
		Interval<Duration> age25_35 = Interval.over(Duration.years(25), true, Duration.years(35), false);
		Interval<Duration> age35_45 = Interval.over(Duration.years(35), true, Duration.years(45), false);
		Interval<Duration> age45_55 = Interval.over(Duration.years(45), true, Duration.years(55), false);
		Interval<Duration> age55_65 = Interval.over(Duration.years(55), true, Duration.years(65), false);
		
		IntervalMap<Duration, Money> schedule = new LinearIntervalMap<Duration, Money>();
		schedule.put(age25_35, Money.dollars(100.00));
		schedule.put(age35_45, Money.dollars(150.00));
		schedule.put(age45_55, Money.dollars(200.00));
		schedule.put(age55_65, Money.dollars(250.00));
		return schedule;
	}
	
}
