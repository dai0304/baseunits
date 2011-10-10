/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ----
 * Copyright (c) 200X Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.money;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import jp.xet.baseunits.money.Allotment;
import jp.xet.baseunits.money.FanTally;
import jp.xet.baseunits.money.Money;
import jp.xet.baseunits.money.MoneyFan;

import org.junit.Test;

/**
 * {@link MoneyFan}のテストクラス。
 */
public class MoneyFanTest {
	
	/**
	 * Three roommates are sharing expenses in a house.
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_RoommateExample() throws Exception {
		//apportionment, assignment, attribution, post, assignation, allotment, slice
		Collection<Allotment<String>> c;
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Joe", Money.dollars(65.00)));
		MoneyFan<String> electricBill = new MoneyFan<String>(c);
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Mary", Money.dollars(650)));
		c.add(new Allotment<String>("Jill", Money.dollars(650)));
		c.add(new Allotment<String>("Joe", Money.dollars(650)));
		MoneyFan<String> rent = new MoneyFan<String>(c);
		
		assertThat(rent.total(), is(Money.dollars(1950)));
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Mary", Money.dollars(12)));
		c.add(new Allotment<String>("Jill", Money.dollars(344)));
		c.add(new Allotment<String>("Joe", Money.dollars(256)));
		MoneyFan<String> groceries = new MoneyFan<String>(c);
		
		assertThat(groceries.total(), is(Money.dollars(612)));
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Mary", Money.dollars(45.00)));
		MoneyFan<String> internetAccess = new MoneyFan<String>(c);
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Joe", Money.dollars(857.00)));
		MoneyFan<String> newSofa = new MoneyFan<String>(c);
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jill", Money.dollars(285.67)));
		c.add(new Allotment<String>("Joe", Money.dollars(-285.67)));
		MoneyFan<String> jillReimbursesJoeForSofa = new MoneyFan<String>(c);
		
		MoneyFan<String> netSofaContributions = newSofa.plus(jillReimbursesJoeForSofa);
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jill", Money.dollars(285.67)));
		c.add(new Allotment<String>("Joe", Money.dollars(571.33)));
		MoneyFan<String> expectedNetSofaContributions = new MoneyFan<String>(c);
		assertThat(netSofaContributions, is(expectedNetSofaContributions));
		
		Collection<MoneyFan<String>> c2 = new ArrayList<MoneyFan<String>>();
		c2.add(electricBill);
		c2.add(rent);
		c2.add(groceries);
		c2.add(internetAccess);
		c2.add(newSofa);
		c2.add(jillReimbursesJoeForSofa);
		FanTally<String> outlays = new FanTally<String>(c2);
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Mary", Money.dollars(707)));
		c.add(new Allotment<String>("Jill", Money.dollars(1279.67)));
		c.add(new Allotment<String>("Joe", Money.dollars(1542.33)));
		MoneyFan<String> expectedNet = new MoneyFan<String>(c);
		
		assertThat(outlays.net(), is(expectedNet));
		
		assertThat(outlays.total(), is(Money.dollars(3529.00)));
		
//        Apportionment<String> apportionment = new EqualApportionment<String> ("Joe", "Jill", "Mary");
//        MoneyFan<String> fairShares = apportionment.(outlays.total(), "Joe", "Jill", "Mary");
//        // The apportion method could have rounding rule options.
//        MoneyFan<String> owedToHouse = fairShares.minus(outlays);
		
	}
	
	/**
	 * {@link MoneyFan#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Equals() throws Exception {
		Collection<Allotment<String>> c;
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jack", Money.dollars(285.67)));
		c.add(new Allotment<String>("Jill", Money.dollars(-285.67)));
		MoneyFan<String> aFan = new MoneyFan<String>(c);
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jill", Money.dollars(-285.67)));
		c.add(new Allotment<String>("Jack", Money.dollars(285.67)));
		MoneyFan<String> anotherFan = new MoneyFan<String>(c);
		
		assertThat(anotherFan, is(aFan));
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jill", Money.dollars(-285.67)));
		c.add(new Allotment<String>("Jack", Money.dollars(285.68)));
		MoneyFan<String> yetAnotherFan = new MoneyFan<String>(c);
		
		assertThat(aFan.equals(yetAnotherFan), is(false));
	}
	
	/**
	 * {@link MoneyFan#plus(MoneyFan)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Plus() throws Exception {
		Collection<Allotment<String>> c;
		
		MoneyFan<String> jack17 = new MoneyFan<String>(new Allotment<String>("Jack", Money.dollars(17)));
		MoneyFan<String> jill13 = new MoneyFan<String>(new Allotment<String>("Jill", Money.dollars(13)));
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jack", Money.dollars(17)));
		c.add(new Allotment<String>("Jill", Money.dollars(13)));
		MoneyFan<String> jack17Jill13 = new MoneyFan<String>(c);
		assertThat(jack17.plus(jill13), is(jack17Jill13));
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jack", Money.dollars(34)));
		c.add(new Allotment<String>("Jill", Money.dollars(13)));
		MoneyFan<String> jack34Jill13 = new MoneyFan<String>(c);
		
		assertThat(jack17.plus(jack17Jill13), is(jack34Jill13));
		
		assertThat(jack17.plus(jack17Jill13), is(jack17Jill13.plus(jack17)));
	}
	
	/**
	 * {@link MoneyFan#negated()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Negation() throws Exception {
		Collection<Allotment<String>> c;
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jack", Money.dollars(285.67)));
		c.add(new Allotment<String>("Jill", Money.dollars(-285.67)));
		MoneyFan<String> positive = new MoneyFan<String>(c);
		
		c = new ArrayList<Allotment<String>>();
		c.add(new Allotment<String>("Jack", Money.dollars(-285.67)));
		c.add(new Allotment<String>("Jill", Money.dollars(285.67)));
		MoneyFan<String> negative = new MoneyFan<String>(c);
		
		assertThat(positive.negated(), is(negative));
	}
	
	/**
	 * {@link MoneyFan#minus(MoneyFan)}のテスト。
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
