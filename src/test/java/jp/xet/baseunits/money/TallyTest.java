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
import static org.junit.Assert.assertThat;
import jp.xet.baseunits.money.Money;
import jp.xet.baseunits.money.Tally;

import org.junit.Test;

/**
 * {@link Tally}のテストクラス。
 */
public class TallyTest {
	
	/**
	 * {@link Tally#net()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Net() throws Exception {
		Tally tally = new Tally(Money.dollars(55.34), Money.dollars(12.22), Money.dollars(-3.07));
		assertThat(tally.net(), is(Money.dollars(64.49)));
	}
}
