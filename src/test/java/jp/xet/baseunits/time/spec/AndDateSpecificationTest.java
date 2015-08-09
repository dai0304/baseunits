/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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
package jp.xet.baseunits.time.spec;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.time.DayOfWeek;

import org.junit.Ignore;
import org.junit.Test;

/**
 * {@link AndDateSpecification}のテストクラス。
 */
public class AndDateSpecificationTest {
	
	/**
	 * 無限ループする。。。
	 * 
	 * @see <a href="http://dragon.xet.jp/jira/browse/BU-5">BU-5</a>
	 */
	@Ignore("BU-5")
	@Test(timeout = 5000L)
	public void test() {
		CalendarInterval interval =
				CalendarInterval.inclusive(CalendarDate.from(2012, 3, 12), CalendarDate.from(2012, 3, 25));
		DateSpecification intervalSpec = DateSpecifications.calendarInterval(interval);
		DateSpecification dayOfWeekSpec = DateSpecifications.dayOfWeek(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
		DateSpecification andSpec = new AndDateSpecification(intervalSpec, dayOfWeekSpec);
		
		CalendarInterval investigateInterval = CalendarInterval.everFrom(CalendarDate.from(2012, 3, 23));
		assertThat(andSpec.firstOccurrenceIn(investigateInterval), is(nullValue()));
	}
}
