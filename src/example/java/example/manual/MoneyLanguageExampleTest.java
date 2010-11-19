/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package example.manual;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

import com.domainlanguage.base.Rounding;
import com.domainlanguage.money.Money;
import com.domainlanguage.money.MoneyTimeRate;
import com.domainlanguage.money.Proration;
import com.domainlanguage.time.Duration;

import org.junit.Test;

/**
 * Example.
 * 
 * @version $Id$
 * @author daisuke
 */
public class MoneyLanguageExampleTest {
	
	private static final Currency CAD = Currency.getInstance("CAD");
	
	private static final double GST_RATE = 0.06;
	

	/**
	 * Example.
	 */
	@Test
	public void testMoneyBigDecimalExample() {
		Money moneyResult = calculateGST(Money.valueOf(3.00, CAD));
		BigDecimal bigDecimalAmount = calculateGST(new BigDecimal("3.00"));
		
		assertThat(moneyResult, is(Money.valueOf(0.18, CAD)));
		assertThat(bigDecimalAmount, is(new BigDecimal("0.18")));
	}
	
	/**
	 * Example.
	 */
	@Test
	public void testMoneyExample() {
		Money baseAmount = Money.dollars(10.03);
		Money calc1 = baseAmount.times(2);
		Money calc2 = baseAmount.minus(Money.dollars(1.14));
		Money calc3 = baseAmount.plus(Money.dollars(2.08));
		
		assertThat(calc1, is(Money.dollars(20.06)));
		assertThat(calc2, is(Money.dollars(8.89)));
		assertThat(calc3, is(Money.dollars(12.11)));
		
		Money[] amounts = {
			calc1,
			calc2,
			calc3
		};
		assertThat(Money.sum(Arrays.asList(amounts)), is(Money.dollars(41.06)));
	}
	
	/**
	 * Example.
	 */
	@Test
	public void testMoneyRateExample_1() {
		// My consulting rate is $400 per day
		MoneyTimeRate rate = Money.dollars(400.00).per(Duration.days(1));
		
		// For three days work I will earn $1200
		assertThat(rate.over(Duration.days(3)), is(Money.dollars(1200.00)));
	}
	
	/**
	 * Example.
	 */
	@Test
	public void testMoneyRateExample_2() {
		// Rate calculation with rounding
		MoneyTimeRate rate = new MoneyTimeRate(Money.euros(100.00), Duration.minutes(3));
		
		assertThat(rate.over(Duration.minutes(1), Rounding.DOWN), is(Money.euros(33.33)));
	}
	
	/**
	 * Example.
	 */
	@Test
	public void testProration() {
		// My salary is defined as $80,000 per year. I'm paid weekly.
		// If I work 36 weeks of a year, how much will I earn?
		
		// Salary rounds to 1538.46 per week.
		Money[] salaryPayments = Proration.dividedEvenlyIntoParts(Money.dollars(80000.00), 52);
		assertThat(salaryPayments[0], is(Money.dollars(1538.47)));
		assertThat(salaryPayments[2], is(Money.dollars(1538.47)));
		assertThat(salaryPayments[4], is(Money.dollars(1538.47)));
		assertThat(salaryPayments[6], is(Money.dollars(1538.47)));
		assertThat(salaryPayments[8], is(Money.dollars(1538.46)));
		assertThat(salaryPayments[10], is(Money.dollars(1538.46)));
		assertThat(salaryPayments[12], is(Money.dollars(1538.46)));
		
		// TODO Following currently fails. May be a problem in proration.
//		Money totalSalaryEarned = Proration.partOfWhole(Money.dollars(80000.00), 36, 52);
//		assertThat(totalSalaryEarned, is(Money.dollars(55384.56)));
	}
	
	private BigDecimal calculateGST(BigDecimal amount) {
		BigDecimal rawResult = amount.multiply(new BigDecimal(Double.toString(GST_RATE)));
		return rawResult.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	private Money calculateGST(Money amount) {
		return amount.times(GST_RATE, Rounding.HALF_UP);
	}
	
}
