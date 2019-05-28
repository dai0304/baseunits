/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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
package example.schedule;

import static jp.xet.baseunits.time.DayOfWeek.FRIDAY;
import static jp.xet.baseunits.time.DayOfWeek.MONDAY;
import static jp.xet.baseunits.time.DayOfWeek.WEDNESDAY;

import java.util.Iterator;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.time.CalendarMonth;
import jp.xet.baseunits.time.spec.DateSpecification;
import jp.xet.baseunits.time.spec.DateSpecifications;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example.
 */
@SuppressWarnings("javadoc")
public class RepeatScheduleExample {
	
	private static Logger logger = LoggerFactory.getLogger(RepeatScheduleExample.class);
	
	
	@Test
	public void example() {
		CalendarInterval interval = CalendarInterval.inclusive(
				CalendarDate.from(2011, 10, 1), CalendarDate.from(2011, 10, 31));
		
		DateSpecification spec = DateSpecifications.calendarInterval(interval);
		spec = spec.and(DateSpecifications.dayOfWeek(MONDAY, WEDNESDAY, FRIDAY));
		spec = spec.and(DateSpecifications.fixed(CalendarDate.from(2011, 10, 12)).not());
		
		Iterator<CalendarDate> itr = spec.iterateOver(CalendarMonth.from(2011, 10).asCalendarInterval());
		while (itr.hasNext()) {
			CalendarDate calendarDate = itr.next();
			logger.info("{} {}", calendarDate, calendarDate.dayOfWeek());
		}
	}
}
