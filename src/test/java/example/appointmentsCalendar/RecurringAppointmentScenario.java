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
package example.appointmentsCalendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeOfDay;
import jp.xet.baseunits.time.TimePoint;

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
		TimeOfDay scheduledMeetingTime = TimeOfDay.from(10, 0, 0, 0);
		CalendarDate dayOfMeeting = CalendarDate.from(2006, 4, 19);
		TimePoint meetingTimeThisDay = scheduledMeetingTime.asTimePointGiven(dayOfMeeting, HONOLULU_TIME);
		TimePoint sameMeetingTimeThisDay = dayOfMeeting.at(scheduledMeetingTime, HONOLULU_TIME);
		assertThat(sameMeetingTimeThisDay, is(meetingTimeThisDay));
		
		assertThat(meetingTimeThisDay, is(TimePoint.at(2006, 4, 19, 10, 0, 0, 0, HONOLULU_TIME)));
		
		// The expressions can be strung together.
		assertThat(meetingTimeThisDay.minus(Duration.minutes(5)),
				is(TimePoint.at(2006, 4, 19, 9, 55, 0, 0, HONOLULU_TIME)));
	}
}
