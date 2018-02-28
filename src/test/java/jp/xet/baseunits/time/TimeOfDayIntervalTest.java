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
package jp.xet.baseunits.time;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * {@link TimeOfDayInterval}のテストクラス。
 */
public class TimeOfDayIntervalTest {
	
	/**
	 * {@link TimeOfDayInterval#start()}, {@link TimeOfDayInterval#end()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_start_end() throws Exception {
		TimeOfDayInterval work = TimeOfDayInterval.closed(TimeOfDay.from(10, 0), TimeOfDay.from(19, 30, 40));
		assertThat(work.start(), is(TimeOfDay.from(10, 0)));
		assertThat(work.end(), is(TimeOfDay.from(19, 30, 40)));
	}
	
	/**
	 * {@link TimeOfDayInterval#length()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_length() throws Exception {
		TimeOfDayInterval work = TimeOfDayInterval.closed(TimeOfDay.from(10, 0), TimeOfDay.from(19, 30, 40));
		assertThat(work.length(), is(Duration.hours(9).plus(Duration.minutes(30).plus(Duration.seconds(40)))));
	}
	
	/**
	 * {@link TimeOfDayInterval#intersect(TimeOfDayInterval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_intersect() throws Exception {
		TimeOfDayInterval a = TimeOfDayInterval.closed(TimeOfDay.from(13, 0), TimeOfDay.from(19, 30, 40));
		TimeOfDayInterval b = TimeOfDayInterval.preceding(TimeOfDay.from(18, 0), Duration.hours(8));
		TimeOfDayInterval intersect = a.intersect(b);
		assertThat(intersect, is(TimeOfDayInterval.closed(TimeOfDay.from(13, 0), TimeOfDay.from(18, 0))));
	}
	
	/**
	 * {@link TimeOfDayInterval#subintervalIterator(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_subintervalIterator() throws Exception {
		TimeOfDayInterval work = TimeOfDayInterval.closed(TimeOfDay.from(10, 0), TimeOfDay.from(19, 30, 40));
		Iterator<TimeOfDayInterval> itr = work.subintervalIterator(Duration.hours(3));
		
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(TimeOfDayInterval.closed(TimeOfDay.from(10, 0), TimeOfDay.from(13, 0))));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(TimeOfDayInterval.closed(TimeOfDay.from(13, 0), TimeOfDay.from(16, 0))));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(TimeOfDayInterval.closed(TimeOfDay.from(16, 0), TimeOfDay.from(19, 0))));
		assertThat(itr.hasNext(), is(false));
		
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * {@link TimeOfDayInterval#isBefore(TimeOfDay)}, {@link TimeOfDayInterval#isAfter(TimeOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_isBefore_isAfter() throws Exception {
		TimeOfDayInterval work = TimeOfDayInterval.closed(TimeOfDay.from(10, 0), TimeOfDay.from(19, 30, 40));
		
		assertThat(work.isBefore(TimeOfDay.from(2, 59)), is(false));
		assertThat(work.isBefore(TimeOfDay.NOON), is(false));
		assertThat(work.isBefore(TimeOfDay.from(22, 3)), is(true));
		
		assertThat(work.isAfter(TimeOfDay.from(2, 59)), is(true));
		assertThat(work.isAfter(TimeOfDay.NOON), is(false));
		assertThat(work.isAfter(TimeOfDay.from(22, 3)), is(false));
	}
}
