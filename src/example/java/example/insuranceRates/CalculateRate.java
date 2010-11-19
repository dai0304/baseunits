/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package example.insuranceRates;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.domainlanguage.base.Ratio;
import com.domainlanguage.base.Rounding;
import com.domainlanguage.intervals.Interval;
import com.domainlanguage.intervals.IntervalMap;
import com.domainlanguage.intervals.LinearIntervalMap;
import com.domainlanguage.money.Money;
import com.domainlanguage.money.MoneyTimeRate;
import com.domainlanguage.money.Proration;
import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.CalendarInterval;
import com.domainlanguage.time.Duration;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Example.
 * 
 * @version $Id$
 * @author daisuke
 */
public class CalculateRate {
	
	private static final CalendarDate policyEffectiveDate = CalendarDate.date(2004, 11, 7);
	

	/**
	 * Example.
	 */
	@Test
	@Ignore
	public void testLookUpMoreComplicated() {
//		BusinessCalendar paymentCalendar = null;
//		CalendarInterval paymentQuarter = paymentCalendar.currentQuarter();
//		
//		CalendarDate birthdate = null;
//		Duration age = birthdate.until(paymentQuarter.start()).duration();
//		Rate rate = insuranceSchedule.get(age);
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
	 * Example.
	 */
	@Test
	public void testProrateFirstMonth() {
		Money monthlyPremium = Money.dollars(150.00);
		CalendarInterval entireMonth = policyEffectiveDate.month();
		CalendarInterval remainderOfMonth = policyEffectiveDate.through(entireMonth.end());
		Ratio partOfPayment = Ratio.of(remainderOfMonth.lengthInDaysInt(), entireMonth.lengthInDaysInt());
		Money firstPayment = monthlyPremium.applying(partOfPayment, Rounding.DOWN);
		assertThat(firstPayment, is(Money.dollars(120.00)));
		
		//Alternative, equivalent calculation
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
		Money quarterlyPayment = premium.over(Duration.months(3));
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
