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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import jp.xet.baseunits.tests.SerializationTester;
import jp.xet.baseunits.time.TimeUnit;

import org.junit.Test;

/**
 * {@link TimeUnit}のテストクラス。
 */
public class TimeUnitTest {
	
	/**
	 * {@link TimeUnit}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(TimeUnit.month);
	}
	
	/**
	 * {@link TimeUnit#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_ToString() throws Exception {
		assertThat(TimeUnit.month.toString(), is("month"));
	}
	
	/**
	 * {@link TimeUnit#isConvertibleToMilliseconds()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_ConvertibleToMilliseconds() throws Exception {
		assertThat(TimeUnit.millisecond.isConvertibleToMilliseconds(), is(true));
		assertThat(TimeUnit.hour.isConvertibleToMilliseconds(), is(true));
		assertThat(TimeUnit.day.isConvertibleToMilliseconds(), is(true));
		assertThat(TimeUnit.week.isConvertibleToMilliseconds(), is(true));
		assertThat(TimeUnit.month.isConvertibleToMilliseconds(), is(false));
		assertThat(TimeUnit.year.isConvertibleToMilliseconds(), is(false));
	}
	
	/**
	 * {@link TimeUnit#compareTo(TimeUnit)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Comparison() throws Exception {
		assertThat(TimeUnit.hour.compareTo(TimeUnit.hour), is(0));
		assertThat(TimeUnit.hour.compareTo(TimeUnit.millisecond), is(greaterThan(0)));
		assertThat(TimeUnit.millisecond.compareTo(TimeUnit.hour), is(lessThan(0)));
		assertThat(TimeUnit.day.compareTo(TimeUnit.hour), is(greaterThan(0)));
		assertThat(TimeUnit.hour.compareTo(TimeUnit.day), is(lessThan(0)));
		
		assertThat(TimeUnit.month.compareTo(TimeUnit.day), is(greaterThan(0)));
		assertThat(TimeUnit.day.compareTo(TimeUnit.month), is(lessThan(0)));
		assertThat(TimeUnit.quarter.compareTo(TimeUnit.hour), is(greaterThan(0)));
		
		assertThat(TimeUnit.month.compareTo(TimeUnit.month), is(0));
		assertThat(TimeUnit.quarter.compareTo(TimeUnit.year), is(lessThan(0)));
		assertThat(TimeUnit.year.compareTo(TimeUnit.quarter), is(greaterThan(0)));
	}
	
	/**
	 * {@link TimeUnit#javaCalendarConstantForBaseType()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_JavaCalendarConstantForBaseType() throws Exception {
		assertThat(TimeUnit.millisecond.javaCalendarConstantForBaseType(), is(Calendar.MILLISECOND));
		assertThat(TimeUnit.hour.javaCalendarConstantForBaseType(), is(Calendar.MILLISECOND));
		assertThat(TimeUnit.day.javaCalendarConstantForBaseType(), is(Calendar.MILLISECOND));
		assertThat(TimeUnit.week.javaCalendarConstantForBaseType(), is(Calendar.MILLISECOND));
		assertThat(TimeUnit.month.javaCalendarConstantForBaseType(), is(Calendar.MONTH));
		assertThat(TimeUnit.quarter.javaCalendarConstantForBaseType(), is(Calendar.MONTH));
		assertThat(TimeUnit.year.javaCalendarConstantForBaseType(), is(Calendar.MONTH));
	}
	
	/**
	 * {@link TimeUnit#isConvertibleTo(TimeUnit)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_IsConvertableTo() throws Exception {
		assertThat(TimeUnit.hour.isConvertibleTo(TimeUnit.minute), is(true));
		assertThat(TimeUnit.minute.isConvertibleTo(TimeUnit.hour), is(true));
		assertThat(TimeUnit.year.isConvertibleTo(TimeUnit.month), is(true));
		assertThat(TimeUnit.month.isConvertibleTo(TimeUnit.year), is(true));
		assertThat(TimeUnit.month.isConvertibleTo(TimeUnit.hour), is(false));
		assertThat(TimeUnit.hour.isConvertibleTo(TimeUnit.month), is(false));
	}
	
	/**
	 * {@link TimeUnit#nextFinerUnit()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_NextFinerUnit() throws Exception {
		assertThat(TimeUnit.hour.nextFinerUnit(), is(TimeUnit.minute));
		assertThat(TimeUnit.quarter.nextFinerUnit(), is(TimeUnit.month));
	}
}
