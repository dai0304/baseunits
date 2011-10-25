/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package example.doctorAppointments;

import jp.xet.baseunits.time.TimePointInterval;

class Appointment {
	
	private TimePointInterval timePointInterval;
	
	
	public Appointment(TimePointInterval interval) {
		timePointInterval = interval;
	}
	
	public TimePointInterval getTimePointInterval() {
		return timePointInterval;
	}
}
