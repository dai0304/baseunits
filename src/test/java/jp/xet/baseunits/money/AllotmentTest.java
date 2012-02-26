/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Allotment}のテストクラス。
 */
public class AllotmentTest {
	
	/**
	 * 等価性検証。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Equals() throws Exception {
		Allotment<String> abc123dollars = new Allotment<String>("ABC", Money.dollars(1.23));
		assertThat(abc123dollars.equals(new Allotment<String>("ABC", Money.dollars(1.23))), is(true));
		assertThat(abc123dollars.equals(new Allotment<String>("ABC", Money.euros(1.23))), is(false));
		assertThat(abc123dollars.equals(new Allotment<String>("XYZ", Money.dollars(1.23))), is(false));
		assertThat(abc123dollars.equals(new Allotment<String>("ABC", Money.dollars(1.24))), is(false));
		assertThat(abc123dollars.equals(new Object()), is(false));
		assertThat(abc123dollars.equals(null), is(false));
		assertThat(abc123dollars.equals(abc123dollars), is(true));
	}
	
	/**
	 * {@link Allotment#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_toString() throws Exception {
		Allotment<String> abc123dollars = new Allotment<String>("ABC", Money.dollars(1.23));
		assertThat(abc123dollars, hasToString("ABC --> USD 1.23"));
	}
	
	/**
	 * 正負転換検証。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Negated() throws Exception {
		Allotment<String> abc123dollars = new Allotment<String>("ABC", Money.dollars(1.23));
		assertThat(abc123dollars.negated(), is(new Allotment<String>("ABC", Money.dollars(-1.23))));
	}
	
	/**
	 * {@link Allotment#breachEncapsulationOfAmount()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_toString() throws Exception {
		Allotment<String> abc123dollars = new Allotment<String>("ABC", Money.dollars(1.23));
		assertThat(abc123dollars.breachEncapsulationOfAmount(), is(Money.dollars(1.23)));
		assertThat(abc123dollars.breachEncapsulationOfEntity(), is("ABC"));
	}
}
