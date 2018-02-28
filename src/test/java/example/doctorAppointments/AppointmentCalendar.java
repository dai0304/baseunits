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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.TimePointInterval;

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
		TimePointInterval day = calDate.asTimePointInterval(defaultZone);
		
		for (Appointment event : events) {
			if (event.getTimePointInterval().intersects(day)) {
				daysAppointments.add(event);
			}
		}
		return daysAppointments;
	}
	
}
