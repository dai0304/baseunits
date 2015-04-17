/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/23
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
 */
package jp.xet.baseunits.time;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * {@link SecondOfMinute}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class SecondOfMinuteTest {
	
	static final SecondOfMinute TEN = SecondOfMinute.valueOf(10);
	
	static final SecondOfMinute TWENTY = SecondOfMinute.valueOf(20);
	
	
	@Test
	public void test01_before_after() {
		assertThat(SecondOfMinute.MIN.isAfter(TEN), is(false));
		assertThat(SecondOfMinute.MIN.isAfter(TWENTY), is(false));
		assertThat(SecondOfMinute.MIN.isBefore(TEN), is(true));
		assertThat(SecondOfMinute.MIN.isBefore(TWENTY), is(true));
	}
	
	@Test
	public void test02_illegal() {
		try {
			SecondOfMinute.valueOf(-1);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			SecondOfMinute.valueOf(60);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	@Test
	public void test03_equals() {
		assertThat(TEN.equals(TEN), is(true));
		assertThat(TEN.equals(null), is(false));
		assertThat(TEN.equals(TWENTY), is(false));
		assertThat(TEN.equals(10), is(false));
		assertThat(SecondOfMinute.valueOf(10).equals(TEN), is(true));
		assertThat(SecondOfMinute.valueOf(20).equals(TWENTY), is(true));
	}
}
