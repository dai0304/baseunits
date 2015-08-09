/*
 * Copyright 2010-2015 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.time;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * {@link MillisecOfSecond}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class MillisecOfSecondTest {
	
	static final MillisecOfSecond TEN = MillisecOfSecond.valueOf(10);
	
	static final MillisecOfSecond HUNDRED = MillisecOfSecond.valueOf(100);
	
	
	@Test
	public void test01_before_after() {
		assertThat(MillisecOfSecond.MIN.isAfter(TEN), is(false));
		assertThat(MillisecOfSecond.MIN.isAfter(HUNDRED), is(false));
		assertThat(MillisecOfSecond.MIN.isBefore(TEN), is(true));
		assertThat(MillisecOfSecond.MIN.isBefore(HUNDRED), is(true));
	}
	
	@Test
	public void test02_illegal() {
		try {
			MillisecOfSecond.valueOf(-1);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			MillisecOfSecond.valueOf(1000);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	@Test
	public void test03_equals() {
		assertThat(TEN.equals(TEN), is(true));
		assertThat(TEN.equals(null), is(false));
		assertThat(TEN.equals(HUNDRED), is(false));
		assertThat(TEN.equals(10), is(false));
		assertThat(MillisecOfSecond.valueOf(10).equals(TEN), is(true));
		assertThat(MillisecOfSecond.valueOf(100).equals(HUNDRED), is(true));
	}
}
