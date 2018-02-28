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
package example.doctorAppointments;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.TimePointInterval;

import org.junit.Test;

/**
 * Example.
 */
public class AppointmentCalendarTest {
	
	/**
	 * Example.
	 */
	@Test
	public void testEventsForDate() {
		TimeZone pt = TimeZone.getTimeZone("America/Los_Angeles");
		
		TimePoint jun7at10 = TimePoint.at(2004, 6, 7, 10, 0, 0, 0, pt);
		TimePointInterval shortTime = TimePointInterval.startingFrom(jun7at10, Duration.hours(3));
		Appointment shortEvent = new Appointment(shortTime);
		
		TimePoint jun9at13 = TimePoint.at(2004, 6, 9, 13, 0, 0, 0, pt);
		TimePointInterval longTime = TimePointInterval.over(jun7at10, jun9at13);
		Appointment longEvent = new Appointment(longTime);
		
		AppointmentCalendar cal = new AppointmentCalendar(pt);
		cal.add(shortEvent);
		cal.add(longEvent);
		
		assertThat(cal.dailyScheduleFor(CalendarDate.from(2004, 6, 6)).size(), is(0));
		assertThat(cal.dailyScheduleFor(CalendarDate.from(2004, 6, 7)).size(), is(2));
		assertThat(cal.dailyScheduleFor(CalendarDate.from(2004, 6, 8)).size(), is(1));
		assertThat(cal.dailyScheduleFor(CalendarDate.from(2004, 6, 9)).size(), is(1));
		assertThat(cal.dailyScheduleFor(CalendarDate.from(2004, 6, 10)).size(), is(0));
	}
	
}
