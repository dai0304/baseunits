/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/25
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
package jp.xet.baseunits.time.spec;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.time.DayOfWeek;

import com.google.common.collect.Iterators;

import org.junit.Test;

/**
 * {@link DayOfWeeksSpecification}のテストクラス。
 */
public class DayOfWeeksSpecificationTest {
	
	/** 月曜にマッチするspec */
	private static final DayOfWeeksSpecification MON = new DayOfWeeksSpecification(DayOfWeek.MONDAY);
	
	/** 月水金にマッチするspec */
	private static final DayOfWeeksSpecification MWF = new DayOfWeeksSpecification(
			DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
	
	/** Mon */
	private static final CalendarDate D2011_10_24 = CalendarDate.from(2011, 10, 24);
	
	/** Tue */
	private static final CalendarDate D2011_10_25 = CalendarDate.from(2011, 10, 25);
	
	/** Wed */
	private static final CalendarDate D2011_10_26 = CalendarDate.from(2011, 10, 26);
	
	/** Fri */
	private static final CalendarDate D2011_10_28 = CalendarDate.from(2011, 10, 28);
	
	/** Sat */
	private static final CalendarDate D2011_10_29 = CalendarDate.from(2011, 10, 29);
	
	/** Sun */
	private static final CalendarDate D2011_10_30 = CalendarDate.from(2011, 10, 30);
	
	/** Mon */
	private static final CalendarDate D2011_10_31 = CalendarDate.from(2011, 10, 31);
	
	
	@Test
	@SuppressWarnings("javadoc")
	public void test_basic() {
		// 月曜日だけtrueを返す
		assertThat(MON.isSatisfiedBy(D2011_10_24), is(true));
		assertThat(MON.isSatisfiedBy(D2011_10_25), is(false));
		assertThat(MON.isSatisfiedBy(D2011_10_26), is(false));
		assertThat(MON.isSatisfiedBy(D2011_10_28), is(false));
		assertThat(MON.isSatisfiedBy(D2011_10_30), is(false));
		assertThat(MON.isSatisfiedBy(D2011_10_31), is(true));
		
		// 期間中の初出の月曜日を返す
		assertThat(MON.firstOccurrenceIn(CalendarInterval.inclusive(D2011_10_24, D2011_10_31)), is(D2011_10_24));
		assertThat(MON.firstOccurrenceIn(CalendarInterval.inclusive(D2011_10_25, D2011_10_31)), is(D2011_10_31));
		assertThat(MON.firstOccurrenceIn(CalendarInterval.inclusive(D2011_10_30, D2011_10_31)), is(D2011_10_31));
		
		// 期間中に月曜日がなかったらnullを返す
		assertThat(MON.firstOccurrenceIn(CalendarInterval.inclusive(D2011_10_25, D2011_10_30)), is(nullValue()));
		
		// 期間中の月曜日を順次返す
		CalendarDate[] array = Iterators.toArray(
				MON.iterateOver(CalendarInterval.inclusive(D2011_10_24, D2011_10_31)), CalendarDate.class);
		assertThat(array.length, is(2));
		assertThat(array[0], is(D2011_10_24));
		assertThat(array[1], is(D2011_10_31));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void test_noLowerLimit() {
		// 下側限界のない期間の場合は例外
		try {
			MON.iterateOver(CalendarInterval.inclusive(null, D2011_10_31));
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void test_noUpperLimit() {
		// 上側限界がなくても順次月曜日を返すこと
		Iterator<CalendarDate> itr = MON.iterateOver(CalendarInterval.inclusive(D2011_10_24, null));
		CalendarDate mon = D2011_10_24;
		for (int i = 0; i < 100; i++) {
			assertThat(mon.dayOfWeek(), is(DayOfWeek.MONDAY));
			assertThat(itr.next(), is(mon));
			mon = mon.plusDays(DayOfWeek.SIZE);
		}
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void test_plural() {
		// 月水金だけtrueを返す
		assertThat(MWF.isSatisfiedBy(D2011_10_24), is(true));
		assertThat(MWF.isSatisfiedBy(D2011_10_25), is(false));
		assertThat(MWF.isSatisfiedBy(D2011_10_26), is(true));
		assertThat(MWF.isSatisfiedBy(D2011_10_30), is(false));
		assertThat(MWF.isSatisfiedBy(D2011_10_28), is(true));
		assertThat(MWF.isSatisfiedBy(D2011_10_31), is(true));
		
		// 期間中の初出の月水金を返す
		assertThat(MWF.firstOccurrenceIn(CalendarInterval.inclusive(D2011_10_24, D2011_10_31)), is(D2011_10_24));
		assertThat(MWF.firstOccurrenceIn(CalendarInterval.inclusive(D2011_10_25, D2011_10_31)), is(D2011_10_26));
		assertThat(MWF.firstOccurrenceIn(CalendarInterval.inclusive(D2011_10_30, D2011_10_31)), is(D2011_10_31));
		
		// 期間中に月水金がなかったらnullを返す
		assertThat(MWF.firstOccurrenceIn(CalendarInterval.inclusive(D2011_10_29, D2011_10_30)), is(nullValue()));
		
		// 期間中の月水金を順次返す
		CalendarDate[] array = Iterators.toArray(
				MWF.iterateOver(CalendarInterval.inclusive(D2011_10_24, D2011_10_31)), CalendarDate.class);
		assertThat(array.length, is(4));
		assertThat(array[0], is(D2011_10_24));
		assertThat(array[1], is(D2011_10_26));
		assertThat(array[2], is(D2011_10_28));
		assertThat(array[3], is(D2011_10_31));
	}
}
