/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package example.doctorAppointments;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import jp.xet.timeandmoney.time.CalendarDate;
import jp.xet.timeandmoney.time.Duration;
import jp.xet.timeandmoney.time.TimeInterval;
import jp.xet.timeandmoney.time.TimePoint;

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
		
		TimeInterval shortTime = TimeInterval.startingFrom(jun7at10, Duration.hours(3));
		Appointment shortEvent = new Appointment(shortTime);
		
		TimePoint jun9at13 = TimePoint.at(2004, 6, 9, 13, 0, 0, 0, pt);
		
		TimeInterval longTime = TimeInterval.over(jun7at10, jun9at13);
		Appointment longEvent = new Appointment(longTime);
		
		AppointmentCalendar cal = new AppointmentCalendar(pt);
		cal.add(shortEvent);
		cal.add(longEvent);
		
		assertThat(cal.dailyScheduleFor(CalendarDate.date(2004, 6, 6)).size(), is(0));
		assertThat(cal.dailyScheduleFor(CalendarDate.date(2004, 6, 7)).size(), is(2));
		assertThat(cal.dailyScheduleFor(CalendarDate.date(2004, 6, 8)).size(), is(1));
		assertThat(cal.dailyScheduleFor(CalendarDate.date(2004, 6, 9)).size(), is(1));
		assertThat(cal.dailyScheduleFor(CalendarDate.date(2004, 6, 10)).size(), is(0));
	}
	
}
