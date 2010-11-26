/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.money;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

import jp.xet.timeandmoney.tests.SerializationTester;
import jp.xet.timeandmoney.util.Ratio;
import jp.xet.timeandmoney.util.Rounding;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link Money}のテストクラス。
 */
public class MoneyTest {
	
	private static Currency USD = Currency.getInstance("USD");
	
	private static Currency JPY = Currency.getInstance("JPY");
	
	private static Currency EUR = Currency.getInstance("EUR");
	
	private Money d15;
	
	private Money d2_51;
	
	private Money y50;
	
	private Money e2_51;
	
	private Money d100;
	
	private Money d0;
	
	private Money y0;
	

	/**
	 * テストを初期化する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Before
	public void setUp() throws Exception {
		d15 = Money.valueOf(new BigDecimal("15.0"), USD);
		d2_51 = Money.valueOf(new BigDecimal("2.51"), USD);
		e2_51 = Money.valueOf(new BigDecimal("2.51"), EUR);
		y50 = Money.valueOf(new BigDecimal("50"), JPY);
		d100 = Money.valueOf(new BigDecimal("100.0"), USD);
		d0 = Money.valueOf(BigDecimal.ZERO, USD);
		y0 = Money.valueOf(BigDecimal.ZERO, JPY);
	}
	
	/**
	 * {@link Money}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(d15);
	}
	
	/**
	 * {@link Money#valueOf(double, Currency)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_CreationFromDouble() throws Exception {
		assertThat(Money.valueOf(15.0, USD), is(d15));
		assertThat(Money.valueOf(2.51, USD), is(d2_51));
		assertThat(Money.valueOf(50.1, JPY), is(y50));
		assertThat(Money.valueOf(100, USD), is(d100));
	}
	
	/**
	 * 円単位 {@link Money} のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Yen() throws Exception {
		assertThat(y50.toString(), is("￥ 50"));
		Money y80 = Money.valueOf(new BigDecimal("80"), JPY);
		Money y30 = Money.valueOf(30, JPY);
		assertThat(y50.plus(y30), is(y80));
		assertThat("mult", y50.times(1.6), is(y80));
	}
	
	/**
	 * {@link Money#Money(BigDecimal, Currency)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Constructor() throws Exception {
		Money d69_99 = new Money(new BigDecimal("69.99"), USD);
		assertThat(d69_99.breachEncapsulationOfAmount(), is(new BigDecimal("69.99")));
		assertThat(d69_99.breachEncapsulationOfCurrency(), is(USD));
		try {
			new Money(new BigDecimal("69.999"), USD);
			fail("Money constructor shall never round, and shall not accept a value whose scale doesn't fit the Currency.");
		} catch (IllegalArgumentException correctResponse) {
		}
	}
	
	/**
	 * {@link Money#dividedBy(double)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_Divide() throws Exception {
		assertThat(d100.dividedBy(3), is(Money.dollars(33.33)));
		assertThat(d100.dividedBy(6), is(Money.dollars(16.67)));
	}
	
	/**
	 * {@link Money#times(double)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_Multiply() throws Exception {
		assertThat(d15.times(10), is(Money.dollars(150)));
		assertThat(d15.times(0.1), is(Money.dollars(1.5)));
		assertThat(d100.times(0.7), is(Money.dollars(70)));
	}
	
	/**
	 * {@link Money#times(double, Rounding)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_MultiplyRounding() throws Exception {
		assertThat(d100.times(0.66666667), is(Money.dollars(66.67)));
		assertThat(d100.times(0.66666667, Rounding.DOWN), is(Money.dollars(66.66)));
	}
	
	/**
	 * {@link Money#times(BigDecimal, Rounding)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_MultiplicationWithExplicitRounding() throws Exception {
		assertThat(d100.times(new BigDecimal("0.666666"), Rounding.HALF_EVEN), is(Money.dollars(66.67)));
		assertThat(d100.times(new BigDecimal("0.666666"), Rounding.DOWN), is(Money.dollars(66.66)));
		assertThat(d100.negated().times(new BigDecimal("0.666666"), Rounding.DOWN), is(Money.dollars(-66.66)));
	}
	
	/**
	 * {@link Money#minimumIncrement()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_MinimumIncrement() throws Exception {
		assertThat(d100.minimumIncrement(), is(Money.valueOf(0.01, USD)));
		assertThat(y50.minimumIncrement(), is(Money.valueOf(1, JPY)));
	}
	
	/**
	 * {@link Money#plus(Money)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_AdditionOfDifferentCurrencies() throws Exception {
		try {
			d15.plus(e2_51);
			fail("added different currencies");
		} catch (ClassCastException e) {
			// success
		}
		
		assertThat(d15.plus(d0), is(d15));
		assertThat(d15.plus(y0), is(d15));
		assertThat(d0.plus(y0), is(d0));
		assertThat(y0.plus(d0), is(y0));
	}
	
	/**
	 * {@link Money#dividedBy(Money)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_DivisionByMoney() throws Exception {
		assertThat(Money.dollars(5.00).dividedBy(Money.dollars(2.00)).decimalValue(1, Rounding.UNNECESSARY),
				is(new BigDecimal(2.50)));
		assertThat(Money.dollars(5.00).dividedBy(Money.dollars(4.00)).decimalValue(2, Rounding.UNNECESSARY),
				is(new BigDecimal(1.25)));
		assertThat(Money.dollars(5.00).dividedBy(Money.dollars(1.00)).decimalValue(0, Rounding.UNNECESSARY),
				is(new BigDecimal(5)));
		try {
			Money.dollars(5.00).dividedBy(Money.dollars(2.00)).decimalValue(0, Rounding.UNNECESSARY);
			fail("dividedBy(Money) does not allow rounding.");
		} catch (ArithmeticException e) {
			// success
		}
		try {
			Money.dollars(10.00).dividedBy(Money.dollars(3.00)).decimalValue(5, Rounding.UNNECESSARY);
			fail("dividedBy(Money) does not allow rounding.");
		} catch (ArithmeticException e) {
			// success
		}
	}
	
	/**
	 * {@link Money#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_CloseNumbersNotEqual() throws Exception {
		Money d2_51a = Money.dollars(2.515);
		Money d2_51b = Money.dollars(2.5149);
		assertThat(d2_51a.equals(d2_51b), is(false));
	}
	
	/**
	 * {@link Money#isGreaterThan(Money)}, {@link Money#isLessThan(Money)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_Compare() throws Exception {
		Money[] monies = new Money[] {
			Money.dollars(0),
			Money.dollars(10.1),
			Money.dollars(0),
			Money.dollars(9.99),
			Money.dollars(-9.00),
			Money.dollars(-10),
			Money.dollars(10),
		};
		
		Arrays.sort(monies);
		
		assertThat(monies[0], is(Money.dollars(-10)));
		assertThat(monies[1], is(Money.dollars(-9.00)));
		assertThat(monies[2], is(Money.dollars(0)));
		assertThat(monies[3], is(Money.dollars(0)));
		assertThat(monies[4], is(Money.dollars(9.99)));
		assertThat(monies[5], is(Money.dollars(10)));
		assertThat(monies[6], is(Money.dollars(10.1)));
		
		monies[3] = Money.euros(3);
		
		try {
			Arrays.sort(monies);
			fail();
		} catch (ClassCastException e) {
			// success
		}
	}
	
	/**
	 * {@link Money#isGreaterThan(Money)}, {@link Money#isLessThan(Money)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_Compare_2() throws Exception {
		assertThat(d15.isGreaterThan(d2_51), is(true));
		assertThat(d2_51.isLessThan(d15), is(true));
		assertThat(d15.isGreaterThan(d15), is(false));
		assertThat(d15.isLessThan(d15), is(false));
		try {
			d15.isGreaterThan(e2_51);
			fail();
		} catch (ClassCastException correctBehavior) {
			// success
		}
	}
	
	/**
	 * {@link Money#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_DifferentCurrencyNotEqual() throws Exception {
		assertThat(d2_51.equals(e2_51), is(false));
	}
	
	/**
	 * {@link Money#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_Equals() throws Exception {
		Money d2_51a = Money.dollars(2.51);
		assertThat(d2_51, is(d2_51a));
	}
	
	/**
	 * {@link Money#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_EqualsNull() throws Exception {
		Money d2_51a = Money.dollars(2.51);
		assertThat(d2_51a.equals(null), is(false));
	}
	
	/**
	 * {@link Money#hashCode()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_Hash() throws Exception {
		Money d2_51a = Money.dollars(2.51);
		assertThat(d2_51.hashCode(), is(d2_51a.hashCode()));
	}
	
	/**
	 * {@link Money#negated()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test19_Negation() throws Exception {
		assertThat(d15.negated(), is(Money.dollars(-15)));
		assertThat(e2_51.negated().negated(), is(e2_51));
	}
	
	/**
	 * {@link Money#isPositive()}, {@link Money#isNegative()}, {@link Money#isZero()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test20_PositiveNegative() throws Exception {
		assertThat(d15.isPositive(), is(true));
		assertThat(Money.dollars(-10).isNegative(), is(true));
		assertThat(Money.dollars(0).isPositive(), is(false));
		assertThat(Money.dollars(0).isNegative(), is(false));
		assertThat(Money.dollars(0).isZero(), is(true));
	}
	
	/**
	 * {@link Money#toString(Locale)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test21_Print() throws Exception {
		assertThat(d15.toString(Locale.US), is("$ 15.00"));
		assertThat(d15.toString(Locale.UK), is("USD 15.00"));
	}
	
	/**
		 * {@link Money}インスタンス生成時の丸めテスト。
		 * 
		 * @throws Exception 例外が発生した場合
		 */
	@Test
	public void test22_Round() throws Exception {
		Money dRounded = Money.dollars(1.2350);
		assertThat(dRounded, is(Money.dollars(1.24)));
	}
	
	/**
	 * {@link Money#minus(Money)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test23_Subtraction() throws Exception {
		assertThat(d15.minus(d2_51), is(Money.dollars(12.49)));
	}
	
	/**
	 * {@link Money#applying(Ratio, int, Rounding)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test24_ApplyRatio() throws Exception {
		Ratio oneThird = Ratio.of(1, 3);
		Money result = Money.dollars(100).applying(oneThird, 1, Rounding.UP);
		assertThat(result, is(Money.dollars(33.40)));
	}
	
	/**
	 * {@link Money#incremented()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test25_Incremented() throws Exception {
		assertThat(d2_51.incremented(), is(Money.dollars(2.52)));
		assertThat(y50.incremented(), is(Money.valueOf(51, JPY)));
	}
	
	/**
	 * 最小単位以下の金額（例えば日本で言う「銭」）に関するテスト。現在この仕様は存在しない。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test26_FractionalPennies() throws Exception {
//        CurrencyPolicy(USD, 0.0025); 
//        Smallest unit.unit Any Money based on this CurrencyPolicy must be some multiple of the
//        smallest unit. "Scale" is insufficient, because the limit is not always a number of demial places.
//        Money someFee = Money.dollars(0.0025);
//        Money wholeMoney = someFee.times(4);
//        assertThat(wholeMoney, is(Money.dollars(0.01)));
	}
	
//	/**
//	 * TODO: Formatted printing of Money
//	 * 
//	 * @throws Exception 例外が発生した場合
//	 */
//	@Test
//	public void test27_LocalPrinting() {
//		assertThat(d15.localString(), is("$15.00"));
//		assertThat(m2_51.localString(), is("2,51 DM"));
//	}
	
}
