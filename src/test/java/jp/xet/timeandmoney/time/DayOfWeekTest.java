/*
 * Copyright 2010 Daisuke Miyamoto.
 * Created on 2010/11/27
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
package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.junit.Test;

/**
 * {@link DayOfWeek}のテストクラス。
 */
public class DayOfWeekTest {
	
	/**
	 * {@link DayOfWeek#valueOf(int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_valueOf() throws Exception {
		assertThat(DayOfWeek.valueOf(Calendar.FRIDAY), is(DayOfWeek.FRIDAY));
		assertThat(DayOfWeek.valueOf(Calendar.THURSDAY), is(DayOfWeek.THURSDAY));
		assertThat(DayOfWeek.valueOf(-999), is(nullValue()));
	}
	
	/**
	 * {@link DayOfWeek#breachEncapsulationOfValue()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_breachEncapsulationOfValue() throws Exception {
		assertThat(DayOfWeek.TUESDAY.breachEncapsulationOfValue(), is(Calendar.TUESDAY));
		assertThat(DayOfWeek.WEDNESDAY.breachEncapsulationOfValue(), is(Calendar.WEDNESDAY));
	}
}
