/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package example.doctorAppointments;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import jp.xet.timeandmoney.time.CalendarDate;
import jp.xet.timeandmoney.time.TimeInterval;


class AppointmentCalendar {
	
	TimeZone defaultZone;
	
	Set<Appointment> events = new HashSet<Appointment>();
	

	AppointmentCalendar(TimeZone zone) {
		defaultZone = zone;
	}
	
	void add(Appointment anEvent) {
		events.add(anEvent);
	}
	
	List<Appointment> dailyScheduleFor(CalendarDate calDate) {
		List<Appointment> daysAppointments = new ArrayList<Appointment>();
		TimeInterval day = calDate.asTimeInterval(defaultZone);
		
		for (Appointment event : events) {
			if (event.getTimeInterval().intersects(day)) {
				daysAppointments.add(event);
			}
		}
		return daysAppointments;
	}
	
}
