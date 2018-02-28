/*
 * Copyright 2010-2018 Miyamoto Daisuke.
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
package jp.xet.baseunits.external;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

/**
 * The purpose of these tests is to verify that {@link java.util.Calendar} is working like we
 * expect it to. We ran into a small problem with HOUR and HOUR_OF_DAY in
 * CalendarDateTest.testConversionToJavaUtil(). These tests are mainly for peace of mind.
 */
public class JavaUtilCalendarQuirksTest {
	
	static final TimeZone UTC = TimeZone.getTimeZone("Universal");
	
	
	/**
	 * {@link Calendar#set(int, int)}で {@link Calendar#HOUR} を設定した場合の挙動確認。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Hour() throws Exception {
		Calendar test = Calendar.getInstance(UTC);
		test.set(Calendar.YEAR, 1969);
		test.set(Calendar.MONTH, Calendar.JULY);
		test.set(Calendar.DATE, 20);
		test.set(Calendar.HOUR, 5);
		test.set(Calendar.AM_PM, Calendar.PM);
		
		assertThat(test.get(Calendar.YEAR), is(1969));
		assertThat(test.get(Calendar.MONTH), is(Calendar.JULY));
		assertThat(test.get(Calendar.DATE), is(20));
		assertThat(test.get(Calendar.HOUR), is(5));
		assertThat(test.get(Calendar.AM_PM), is(Calendar.PM));
		assertThat(test.get(Calendar.HOUR_OF_DAY), is(17));
	}
	
	/**
	 * {@link Calendar#set(int, int)}で {@link Calendar#HOUR_OF_DAY} を設定した場合の挙動確認。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_HourOfDay() throws Exception {
		Calendar test = Calendar.getInstance(UTC);
		test.set(Calendar.YEAR, 1969);
		test.set(Calendar.MONTH, Calendar.JULY);
		test.set(Calendar.DATE, 20);
		test.set(Calendar.HOUR_OF_DAY, 3);
		
		assertThat(test.get(Calendar.YEAR), is(1969));
		assertThat(test.get(Calendar.MONTH), is(Calendar.JULY));
		assertThat(test.get(Calendar.DATE), is(20));
		assertThat(test.get(Calendar.HOUR), is(3));
		assertThat(test.get(Calendar.AM_PM), is(Calendar.AM));
		assertThat(test.get(Calendar.HOUR_OF_DAY), is(3));
	}
}
