/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package example.doctorAppointments;

import jp.tricreo.basicunits.time.TimeInterval;

class Appointment {
	
	private TimeInterval timeInterval;
	

	public Appointment(TimeInterval interval) {
		timeInterval = interval;
	}
	
	public TimeInterval getTimeInterval() {
		return timeInterval;
	}
}
