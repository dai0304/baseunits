package com.domainlanguage.money;

import junit.framework.TestCase;

public class MoneyFanTest extends TestCase {
	
	public void testEquals() {
		MoneyFan<String> aFan =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(285.67)), new Allotment<String>(
						"Jill", Money.dollars(-285.67)));
		
		MoneyFan<String> anotherFan =
				new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(-285.67)), new Allotment<String>(
						"Jack", Money.dollars(285.67)));
		
		assertEquals(aFan, anotherFan);
		
		MoneyFan<String> yetAnotherFan =
				new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(-285.67)), new Allotment<String>(
						"Jack", Money.dollars(285.68)));
		
		assertFalse(aFan.equals(yetAnotherFan));
	}
	
	public void testMinus() {
		MoneyFan<String> jack = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(285.67)));
		
		MoneyFan<String> lessJack = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(10.00)));
		
		MoneyFan<String> differenceJack = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(275.67)));
		
		assertEquals(differenceJack, jack.minus(lessJack));
		
		assertEquals(new MoneyFan<String>(), jack.minus(jack));
	}
	
	public void testNegation() {
		MoneyFan<String> positive =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(285.67)), new Allotment<String>(
						"Jill", Money.dollars(-285.67)));
		
		MoneyFan<String> negative =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(-285.67)), new Allotment<String>(
						"Jill", Money.dollars(285.67)));
		
		assertEquals(negative, positive.negated());
	}
	
	public void testPlus() {
		MoneyFan<String> jack17 = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(17)));
		MoneyFan<String> jill13 = new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(13)));
		MoneyFan<String> jack17Jill13 =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(17)), new Allotment<String>("Jill",
						Money.dollars(13)));
		assertEquals(jack17Jill13, jack17.plus(jill13));
		
		MoneyFan<String> jack34Jill13 =
				new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(34)), new Allotment<String>("Jill",
						Money.dollars(13)));
		assertEquals(jack34Jill13, jack17.plus(jack17Jill13));
		
		assertEquals(jack17Jill13.plus(jack17), jack17.plus(jack17Jill13));
	}
	
	/**
	 * Three roommates are sharing expenses in a house.
	 */
	public void testRoommateExample() {
		//apportionment, assignment, attribution, post, assignation, allotment, slice
		MoneyFan<String> electricBill = new MoneyFan<String>(new Allotment<String>("Joe", Money.dollars(65.00)));
		
		MoneyFan<String> rent =
				new MoneyFan<String>(new Allotment<String>("Mary", Money.dollars(650)), new Allotment<String>("Jill",
						Money.dollars(650)), new Allotment<String>("Joe", Money.dollars(650)));
		
		assertEquals(Money.dollars(1950), rent.total());
		
		MoneyFan<String> groceries =
				new MoneyFan<String>(new Allotment<String>("Mary", Money.dollars(12)), new Allotment<String>("Jill",
						Money.dollars(344)), new Allotment<String>("Joe", Money.dollars(256)));
		
		assertEquals(Money.dollars(612), groceries.total());
		
		MoneyFan<String> internetAccess = new MoneyFan<String>(new Allotment<String>("Mary", Money.dollars(45.00)));
		MoneyFan<String> newSofa = new MoneyFan<String>(new Allotment<String>("Joe", Money.dollars(857.00)));
		
		MoneyFan<String> jillReimbursesJoeForSofa =
				new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(285.67)), new Allotment<String>("Joe",
						Money.dollars(-285.67)));
		
		MoneyFan<String> netSofaContributions = newSofa.plus(jillReimbursesJoeForSofa);
		MoneyFan<String> expectedNetSofaContributions =
				new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(285.67)), new Allotment<String>("Joe",
						Money.dollars(571.33)));
		assertEquals(expectedNetSofaContributions, netSofaContributions);
		
		FanTally<String> outlays =
				new FanTally<String>(electricBill, rent, groceries, internetAccess, newSofa, jillReimbursesJoeForSofa);
		
		MoneyFan<String> expectedNet =
				new MoneyFan<String>(new Allotment<String>("Mary", Money.dollars(707)), new Allotment<String>("Jill",
						Money.dollars(1279.67)), new Allotment<String>("Joe", Money.dollars(1542.33)));
		
		assertEquals(expectedNet, outlays.net());
		
		assertEquals(Money.dollars(3529.00), outlays.total());
		
//        Apportionment<String> apportionment = new EqualApportionment<String> ("Joe", "Jill", "Mary");
//        MoneyFan<String> fairShares = apportionment.(outlays.total(), "Joe", "Jill", "Mary");
//        // The apportion method could have rounding rule options.
//        MoneyFan<String> owedToHouse = fairShares.minus(outlays);
		
	}
	
}
