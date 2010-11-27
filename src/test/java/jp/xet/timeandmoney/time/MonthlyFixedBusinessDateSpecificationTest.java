/*
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
package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import jp.xet.timeandmoney.time.MonthlyFixedBusinessDateSpecification.Shifter;

import org.junit.Test;

/**
 * {@link MonthlyFixedBusinessDateSpecification}のテストクラス。
 */
public class MonthlyFixedBusinessDateSpecificationTest {
	
	/**
	 * {@link Shifter#NEXT}によるストラテジのテスト。
	 * 
	 * <p>毎月11日（但し非営業日の場合は翌営業日）</p>
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_NextStrategy() throws Exception {
		BusinessCalendar cal = BusinessCalendarTest.japaneseBusinessCalendar();
		MonthlyFixedBusinessDateSpecification spec = new MonthlyFixedBusinessDateSpecification(
				DayOfMonth.valueOf(11), Shifter.NEXT, cal);
		
		CalendarInterval yearInterval = CalendarMonth.from(2010, 1).asYearInterval();
		
		Iterator<CalendarDate> itr = spec.iterateOver(yearInterval);
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 1, 12))); // 1/11 is Monday but Holiday 成人の日
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 2, 12))); // 2/11 is Tuesday but Holiday 建国記念日
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 3, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 4, 12))); // 4/11 is Sunday
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 5, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 6, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 7, 12))); // 7/11 is Sunday
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 8, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 9, 13))); // 9/11 is Saturday
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 10, 12))); // 10/11 is Monday but Holiday 体育の日
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 11, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 12, 13))); // 12/11 is Saturday
		assertThat(itr.hasNext(), is(false));
	}
	
	/**
	 * {@link Shifter#PREV}によるストラテジのテスト。
	 * 
	 * <p>毎月11日（但し非営業日の場合は前営業日）</p>
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_PrevStrategy() throws Exception {
		BusinessCalendar cal = BusinessCalendarTest.japaneseBusinessCalendar();
		MonthlyFixedBusinessDateSpecification spec = new MonthlyFixedBusinessDateSpecification(
				DayOfMonth.valueOf(11), Shifter.PREV, cal);
		
		CalendarInterval yearInterval = CalendarMonth.from(2010, 1).asYearInterval();
		
		Iterator<CalendarDate> itr = spec.iterateOver(yearInterval);
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 1, 8))); // 1/11 is Monday but Holiday 成人の日
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 2, 10))); // 2/11 is Tuesday but Holiday 建国記念日
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 3, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 4, 9))); // 4/11 is Sunday
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 5, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 6, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 7, 9))); // 7/11 is Sunday
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 8, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 9, 10))); // 9/11 is Saturday
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 10, 8))); // 10/11 is Monday but Holiday 体育の日
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 11, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.date(2010, 12, 10))); // 12/11 is Saturday
		assertThat(itr.hasNext(), is(false));
	}
	
	/**
	 * {@link MonthlyFixedBusinessDateSpecification#isSatisfiedBy(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_isSatisfiedBy() throws Exception {
		BusinessCalendar cal = BusinessCalendarTest.japaneseBusinessCalendar();
		MonthlyFixedBusinessDateSpecification spec = new MonthlyFixedBusinessDateSpecification(
				DayOfMonth.valueOf(11), Shifter.NEXT, cal);
		
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 2, 10)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 2, 11)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 2, 12)), is(true));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 2, 13)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 3, 10)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 3, 11)), is(true));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 3, 12)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 3, 13)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 9, 10)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 9, 11)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 9, 12)), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.date(2010, 9, 13)), is(true));
	}
}
