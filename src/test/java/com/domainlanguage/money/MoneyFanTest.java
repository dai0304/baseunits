package com.domainlanguage.money;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link MoneyFan}のテストクラス。
 * 
 * @author daisuke
 */
public class MoneyFanTest {
	
	/**
	 * Three roommates are sharing expenses in a house.
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_RoommateExample() throws Exception {
		//apportionment, assignment, attribution, post, assignation, allotment, slice
		MoneyFan<String> electricBill = new MoneyFan<String>(new Allotment<String>("Joe", Money.dollars(65.00)));
		
		MoneyFan<String> rent =
				new MoneyFan<String>(new Allotment<String>("Mary", Money.dollars(650)), new Allotment<String>("Jill",
						Money.dollars(650)), new Allotment<String>("Joe", Money.dollars(650)));
		
		assertThat(rent.total(), is(Money.dollars(1950)));
		
		MoneyFan<String> groceries =
				new MoneyFan<String>(new Allotment<String>("Mary", Money.dollars(12)), new Allotment<String>("Jill",
						Money.dollars(344)), new Allotment<String>("Joe", Money.dollars(256)));
		
		assertThat(groceries.total(), is(Money.dollars(612)));
		
		MoneyFan<String> internetAccess = new MoneyFan<String>(new Allotment<String>("Mary", Money.dollars(45.00)));
		MoneyFan<String> newSofa = new MoneyFan<String>(new Allotment<String>("Joe", Money.dollars(857.00)));
		
		MoneyFan<String> jillReimbursesJoeForSofa =
				new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(285.67)), new Allotment<String>("Joe",
						Money.dollars(-285.67)));
		
		MoneyFan<String> netSofaContributions = newSofa.plus(jillReimbursesJoeForSofa);
		MoneyFan<String> expectedNetSofaContributions =
				new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(285.67)), new Allotment<String>("Joe",
						Money.dollars(571.33)));
		assertThat(netSofaContributions, is(expectedNetSofaContributions));
		
		FanTally<String> outlays =
				new FanTally<String>(electricBill, rent, groceries, internetAccess, newSofa, jillReimbursesJoeForSofa);
		
		MoneyFan<String> expectedNet =
				new MoneyFan<String>(new Allotment<String>("Mary", Money.dollars(707)), new Allotment<String>("Jill",
						Money.dollars(1279.67)), new Allotment<String>("Joe", Money.dollars(1542.33)));
		
		assertThat(outlays.net(), is(expectedNet));
		
		assertThat(outlays.total(), is(Money.dollars(3529.00)));
		
//        Apportionment<String> apportionment = new EqualApportionment<String> ("Joe", "Jill", "Mary");
//        MoneyFan<String> fairShares = apportionment.(outlays.total(), "Joe", "Jill", "Mary");
//        // The apportion method could have rounding rule options.
//        MoneyFan<String> owedToHouse = fairShares.minus(outlays);
		
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Equals() throws Exception {
		MoneyFan<String> aFan =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(285.67)), new Allotment<String>(
						"Jill", Money.dollars(-285.67)));
		
		MoneyFan<String> anotherFan =
				new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(-285.67)), new Allotment<String>(
						"Jack", Money.dollars(285.67)));
		
		assertThat(anotherFan, is(aFan));
		
		MoneyFan<String> yetAnotherFan =
				new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(-285.67)), new Allotment<String>(
						"Jack", Money.dollars(285.68)));
		
		assertThat(aFan.equals(yetAnotherFan), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Plus() throws Exception {
		MoneyFan<String> jack17 = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(17)));
		MoneyFan<String> jill13 = new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(13)));
		MoneyFan<String> jack17Jill13 =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(17)), new Allotment<String>("Jill",
						Money.dollars(13)));
		assertThat(jack17.plus(jill13), is(jack17Jill13));
		
		MoneyFan<String> jack34Jill13 =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(34)), new Allotment<String>("Jill",
						Money.dollars(13)));
		assertThat(jack17.plus(jack17Jill13), is(jack34Jill13));
		
		assertThat(jack17.plus(jack17Jill13), is(jack17Jill13.plus(jack17)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Negation() throws Exception {
		MoneyFan<String> positive =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(285.67)), new Allotment<String>(
						"Jill", Money.dollars(-285.67)));
		
		MoneyFan<String> negative =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(-285.67)), new Allotment<String>(
						"Jill", Money.dollars(285.67)));
		
		assertThat(positive.negated(), is(negative));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_Minus() throws Exception {
		MoneyFan<String> jack = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(285.67)));
		
		MoneyFan<String> lessJack = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(10.00)));
		
		MoneyFan<String> differenceJack = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(275.67)));
		
		assertThat(jack.minus(lessJack), is(differenceJack));
		
		assertThat(jack.minus(jack), is(new MoneyFan<String>()));
	}
	
}
