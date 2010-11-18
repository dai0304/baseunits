/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package example.manual;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import com.domainlanguage.base.Rounding;
import com.domainlanguage.money.Money;
import com.domainlanguage.money.MoneyTimeRate;
import com.domainlanguage.money.Proration;
import com.domainlanguage.time.Duration;

import junit.framework.TestCase;

public class MoneyLanguageExampleTest extends TestCase {
    private static final Currency CAD = Currency.getInstance("CAD");
    private static final double GST_RATE = 0.06;

    public void testMoneyExample() {
        Money baseAmount = Money.dollars(10.03);
        Money calc1 = baseAmount.times(2);
        Money calc2 = baseAmount.minus(Money.dollars(1.14));
        Money calc3 = baseAmount.plus(Money.dollars(2.08));
        
        assertEquals(Money.dollars(20.06), calc1);
        assertEquals(Money.dollars(8.89), calc2);
        assertEquals(Money.dollars(12.11), calc3);
        
        Money[] amounts = {calc1, calc2, calc3};
        assertEquals(Money.dollars(41.06), Money.sum(Arrays.asList(amounts)));        
    }

    private Money calculateGST(Money amount) {
        return amount.times(GST_RATE, Rounding.HALF_UP);
    }

    private BigDecimal calculateGST(BigDecimal amount) {
        BigDecimal rawResult = amount.multiply(new BigDecimal(Double.toString(GST_RATE)));
        return rawResult.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void testMoneyBigDecimalExample() {
        Money moneyResult = calculateGST(Money.valueOf(3.00, CAD));
        BigDecimal bigDecimalAmount = calculateGST(new BigDecimal("3.00"));
        
        assertEquals(Money.valueOf(0.18, CAD), moneyResult);
        assertEquals(new BigDecimal("0.18"), bigDecimalAmount);
    }
    
    public void testMoneyRateExample_1() {
        //My consulting rate is $400 per day
        MoneyTimeRate rate =Money.dollars(400.00).per(Duration.days(1));

        //For three days work I will earn $1200
        assertEquals(Money.dollars(1200.00), rate.over(Duration.days(3)));
    }

    public void testMoneyRateExample_2() {
        //Rate calculation with rounding
        MoneyTimeRate rate = new MoneyTimeRate(Money.euros(100.00), Duration.minutes(3));

        assertEquals(Money.euros(33.33), rate.over(Duration.minutes(1),     Rounding.DOWN));
    }
    
    public void testProration() {
        //My salary is defined as $80,000 per year. I'm paid weekly.
        //If I work 36 weeks of a year, how much will I earn?
        Proration proration = new Proration();
        
        //Salary rounds to 1538.46 per week.
        Money[] salaryPayments = proration.dividedEvenlyIntoParts(Money.dollars(80000.00), 52);
        assertEquals(Money.dollars(1538.47), salaryPayments[0]);
        assertEquals(Money.dollars(1538.47), salaryPayments[2]);
        assertEquals(Money.dollars(1538.47), salaryPayments[4]);
        assertEquals(Money.dollars(1538.47), salaryPayments[6]);
        assertEquals(Money.dollars(1538.46), salaryPayments[8]);
        assertEquals(Money.dollars(1538.46), salaryPayments[10]);
        assertEquals(Money.dollars(1538.46), salaryPayments[12]);

        Money totalSalaryEarned = proration.partOfWhole(Money.dollars(80000.00), 36, 52);
        //TODO Following currently fails. May be a problem in proration.
        //assertEquals(Money.dollars(55384.56), totalSalaryEarned);
    }

    
}
