/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package example.appointmentsCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import jp.tricreo.basicunits.time.CalendarDate;
import jp.tricreo.basicunits.time.CalendarMinute;
import jp.tricreo.basicunits.time.Duration;
import jp.tricreo.basicunits.time.TimeOfDay;
import jp.tricreo.basicunits.time.TimePoint;

import org.junit.Test;

/**
 * Example.
 */
public class RecurringAppointmentScenario {
	
	private static final TimeZone HONOLULU_TIME = TimeZone.getTimeZone("Pacific/Honolulu");
	

	/**
	 * Example.
	 * 
	 * Daily stand-up meeting at 10:00am each work day. (We work in Honolulu, of course.)
	 * Notify 5 minutes before meeting starts.
	 * Derive the TimePoint at which I should notify on April 19 2006.
	 */
	@Test
	public void testDailyMeetingAlert() {
		TimeOfDay scheduledMeetingTime = TimeOfDay.from(10, 0);
		CalendarDate dayOfMeeting = CalendarDate.from(2006, 4, 19);
		CalendarMinute meetingTimeThisDay = scheduledMeetingTime.on(dayOfMeeting);
		CalendarMinute sameMeetingTimeThisDay = dayOfMeeting.at(scheduledMeetingTime);
		assertThat(sameMeetingTimeThisDay, is(meetingTimeThisDay));
		TimePoint meetingTimePoint = meetingTimeThisDay.asTimePoint(HONOLULU_TIME);
		assertThat(meetingTimePoint, is(TimePoint.at(2006, 4, 19, 10, 0, 0, 0, HONOLULU_TIME)));
		
		// The expressions can be strung together.
		assertThat(meetingTimeThisDay.asTimePoint(HONOLULU_TIME).minus(Duration.minutes(5)),
				is(TimePoint.at(2006, 4, 19, 9, 55, 0, 0, HONOLULU_TIME)));
	}
}
