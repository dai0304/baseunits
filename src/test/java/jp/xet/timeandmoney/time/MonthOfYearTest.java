/*
 * Copyright 2010 Daisuke Miyamoto.
 * Created on 2010/11/25
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
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link MonthOfYear}のテストクラス。
 */
public class MonthOfYearTest {
	
	/**
	 * 基本的な機能テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_basic() throws Exception {
		assertThat(MonthOfYear.JAN.getLastDayOfThisMonth(2010), is(DayOfMonth.of(31)));
		
		assertThat(MonthOfYear.FEB.getLastDayOfThisMonth(2010), is(DayOfMonth.of(28)));
		assertThat(MonthOfYear.FEB.getLastDayOfThisMonth(2000), is(DayOfMonth.of(29)));
		assertThat(MonthOfYear.FEB.getLastDayOfThisMonth(2004), is(DayOfMonth.of(29)));
		assertThat(MonthOfYear.FEB.getLastDayOfThisMonth(1900), is(DayOfMonth.of(28)));
		
		assertThat(MonthOfYear.NOV.getLastDayOfThisMonth(2010), is(DayOfMonth.of(30)));
	}
	
}
