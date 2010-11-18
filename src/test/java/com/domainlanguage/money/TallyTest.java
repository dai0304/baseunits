package com.domainlanguage.money;

import junit.framework.TestCase;

public class TallyTest extends TestCase {
	
	public void testNet() {
		Tally tally = new Tally(Money.dollars(55.34), Money.dollars(12.22), Money.dollars(-3.07));
		
		assertEquals(Money.dollars(64.49), tally.net());
	}
}
