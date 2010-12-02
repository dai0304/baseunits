/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
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
package jp.tricreo.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * {@link MonthlyFixedDateSpecification}のテストクラス。
 */
public class MonthlyFixedDateSpecificationTest {
	
	/**
	 * {@link MonthlyFixedBusinessDateSpecification#firstOccurrenceIn(CalendarInterval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_firstOccurrenceIn() throws Exception {
		DateSpecification fixed = DateSpecification.fixed(19);
		CalendarInterval nov2010 = CalendarInterval.month(CalendarMonth.from(2010, 11));
		assertThat(fixed.firstOccurrenceIn(nov2010), is(CalendarDate.from(2010, 11, 19)));
		
		CalendarInterval laterNovToDec2010 = CalendarInterval.inclusive(
				CalendarDate.from(2010, 11, 25), CalendarDate.from(2010, 12, 31));
		assertThat(fixed.firstOccurrenceIn(laterNovToDec2010), is(CalendarDate.from(2010, 12, 19)));
		
		CalendarInterval noHit = CalendarInterval.inclusive(
				CalendarDate.from(2010, 11, 25), CalendarDate.from(2010, 12, 10));
		assertThat(fixed.firstOccurrenceIn(noHit), is(nullValue()));
		
	}
	
	/**
	 * {@link MonthlyFixedDateSpecification#iterateOver(CalendarInterval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_iterateOver() throws Exception {
		CalendarInterval y2010 = CalendarInterval.year(2010);
		DateSpecification fixed = DateSpecification.fixed(27);
		
		Iterator<CalendarDate> itr = fixed.iterateOver(y2010);
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 1, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 2, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 3, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 4, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 5, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 6, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 7, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 8, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 9, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 10, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 11, 27)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2010, 12, 27)));
		assertThat(itr.hasNext(), is(false));
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
	}
}
