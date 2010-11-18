package com.domainlanguage.money;

import junit.framework.TestCase;

public class AllotmentTest extends TestCase {
	
	public void testEquals() {
		assertEquals(new Allotment<String>("ABC", Money.dollars(1.23)),
				new Allotment<String>("ABC", Money.dollars(1.23)));
		
		assertFalse(new Allotment<String>("ABC", Money.dollars(1.23)).equals(new Allotment<String>("ABC", Money
			.euros(1.23))));
		
		assertFalse(new Allotment<String>("ABC", Money.dollars(1.23)).equals(new Allotment<String>("XYZ", Money
			.dollars(1.23))));
		
		assertFalse(new Allotment<String>("ABC", Money.dollars(1.23)).equals(new Allotment<String>("ABC", Money
			.dollars(1.24))));
	}
	
	public void testNegated() {
		assertEquals(new Allotment<String>("ABC", Money.dollars(-1.23)),
				new Allotment<String>("ABC", Money.dollars(1.23)).negated());
	}
}
