/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
 * Created on 2010/11/26
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
package jp.tricreo.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import jp.tricreo.baseunits.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link CalendarMinute}のテストクラス。
 */
public class CalendarMinuteTest {
	
	private CalendarMinute feb17_1_23 = CalendarMinute.from(2003, 2, 17, 1, 23);
	
	private CalendarMinute feb17_3_45 = CalendarMinute.from(2003, 2, 17, 3, 45);
	
	private CalendarMinute feb17_19_42 = CalendarMinute.from(2003, 2, 17, 19, 42);
	
	private CalendarMinute mar13_3_45 = CalendarMinute.from(2003, 3, 13, 3, 45);
	
	private TimeZone ct = TimeZone.getTimeZone("America/Chicago");
	
	
	/**
	 * {@link CalendarMinute}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(feb17_1_23);
	}
	
	/**
	 * {@link CalendarMinute#isBefore(CalendarMinute)} と {@link CalendarMinute#isAfter(CalendarMinute)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Comparison() throws Exception {
		assertThat(feb17_1_23.isBefore(feb17_1_23), is(false));
		assertThat(feb17_1_23.compareTo(feb17_1_23), is(0));
		
		assertThat(feb17_3_45.isBefore(feb17_3_45), is(false));
		assertThat(feb17_3_45.compareTo(feb17_3_45), is(0));
		
		assertThat(feb17_1_23.isBefore(feb17_3_45), is(true));
		assertThat(feb17_1_23.compareTo(feb17_3_45), is(lessThan(0)));
		
		assertThat(feb17_3_45.isBefore(feb17_1_23), is(false));
		assertThat(feb17_3_45.compareTo(feb17_1_23), is(greaterThan(0)));
		
		assertThat(feb17_1_23.isBefore(feb17_19_42), is(true));
		assertThat(feb17_19_42.isBefore(feb17_1_23), is(false));
		
		assertThat(feb17_1_23.isBefore(mar13_3_45), is(true));
		assertThat(feb17_1_23.compareTo(mar13_3_45), is(lessThan(0)));
		
		assertThat(mar13_3_45.isBefore(feb17_1_23), is(false));
		assertThat(mar13_3_45.isBefore(feb17_1_23), is(false));
		assertThat(feb17_1_23.isBefore(feb17_1_23), is(false));
		assertThat(feb17_1_23.isBefore(feb17_1_23), is(false));
		assertThat(feb17_1_23.isAfter(mar13_3_45), is(false));
		assertThat(mar13_3_45.isAfter(feb17_1_23), is(true));
		assertThat(feb17_1_23.isAfter(feb17_1_23), is(false));
	}
	
	/**
	 * {@link CalendarMinute#asTimePoint(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_StartAsTimePoint() throws Exception {
		TimePoint feb17_1_23AsCt = feb17_1_23.asTimePoint(ct);
		assertThat(feb17_1_23AsCt, is(TimePoint.at(2003, 2, 17, 1, 23, ct)));
	}
	
	/**
	 * {@link CalendarMinute#toString(String, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_FormattedString() throws Exception {
		TimeZone zone = TimeZone.getTimeZone("Universal");
		assertThat(feb17_1_23.toString("M/d/yyyy", zone), is("2/17/2003"));
		//Now a nonsense pattern, to make sure it isn't succeeding by accident.
		assertThat(feb17_1_23.toString("#d-yy/MM yyyy", zone), is("#17-03/02 2003"));
	}
	
	/**
	 * {@link CalendarMinute#parse(String, String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_FromFormattedString() throws Exception {
		assertThat(CalendarMinute.parse("2/17/2003 01:23", "M/d/yyyy HH:mm"), is(feb17_1_23));
		//Now a nonsense pattern, to make sure it isn't succeeding by accident.
		assertThat(CalendarMinute.parse("#17-03/02 2003, 01:23", "#d-yy/MM yyyy, HH:mm"), is(feb17_1_23));
	}
	
	/**
	 * {@link CalendarMinute#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_Equals() throws Exception {
		assertThat(feb17_1_23.equals(feb17_1_23), is(true));
		assertThat(feb17_1_23.equals(feb17_3_45), is(false));
		assertThat(feb17_1_23.equals(mar13_3_45), is(false));
		assertThat(feb17_1_23.equals(null), is(false));
		assertThat(new CalendarMinute(CalendarDate.from(2003, 2, 17),
				TimeOfDay.from(1, 23)).equals(feb17_1_23), is(true));
		assertThat(new CalendarMinute(CalendarDate.from(2003, 2, 17),
				TimeOfDay.from(1, 23)) {
			
			private static final long serialVersionUID = 8307944665463538049L;
		}.equals(feb17_1_23), is(false));
		
		assertThat(feb17_1_23.hashCode(), is(feb17_1_23.hashCode()));
		assertThat(feb17_1_23.hashCode(), is(not(feb17_3_45.hashCode())));
		assertThat(feb17_1_23.hashCode(), is(not(mar13_3_45.hashCode())));
		
	}
	
	/**
	 * {@link CalendarMinute#breachEncapsulationOfDate()}
	 * {@link CalendarMinute#breachEncapsulationOfTime()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_breachEncapsulationOf() throws Exception {
		assertThat(feb17_1_23.breachEncapsulationOfDate(), is(CalendarDate.from(2003, 2, 17)));
		assertThat(feb17_1_23.breachEncapsulationOfTime(), is(TimeOfDay.from(1, 23)));
	}
	
	/**
	 * {@link CalendarMinute#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_ToString() throws Exception {
		CalendarMinute date = CalendarMinute.from(2004, 5, 28, 23, 21);
		assertThat(date.toString(), is("2004-05-28 at 23:21"));
	}
	
}
