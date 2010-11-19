/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import com.domainlanguage.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link TimeUnit}のテストクラス。
 * @author daisuke
 */
public class TimeUnitTest {
	
	/**
	 * 永続化マッピングテスト用の {@link TimeUnit} を返す。
	 * 
	 * @return 永続化マッピングテスト用の {@link TimeUnit}
	 */
	public static TimeUnit exampleForPersistentMappingTesting() {
		return TimeUnit.second;
	}
	
	/**
	 * 永続化マッピングテスト用の {@link TimeUnit.Type} を返す。
	 * 
	 * @return 永続化マッピングテスト用の {@link TimeUnit.Type}
	 */
	public static TimeUnit.Type exampleTypeForPersistentMappingTesting() {
		return TimeUnit.Type.hour;
	}
	
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
