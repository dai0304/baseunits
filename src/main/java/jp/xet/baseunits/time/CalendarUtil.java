/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/12/26
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.xet.baseunits.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * BASEUNITS INTERNAL API.
 * 
 * <p>THIS CLASS IS NOT PART OF PUBLIC API.  DO NOT USE.</p>
 */
@SuppressWarnings("javadoc")
public class CalendarUtil {
	
	public static final TimeZone UTC = TimeZone.getTimeZone("Universal");
	
	
	public static Calendar newCalendar() {
		return newCalendar(UTC);
	}
	
	public static Calendar newCalendar(TimeZone zone) {
		Calendar calendar = Calendar.getInstance(zone);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTimeInMillis(0);
		return calendar;
	}
	
	public static SimpleDateFormat newSimpleDateFormat(String pattern, Locale locale, TimeZone zone) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, locale);
		df.setTimeZone(zone);
		df.setCalendar(newCalendar(zone));
		return df;
	}
	
	public static SimpleDateFormat newSimpleDateFormat(String pattern, TimeZone zone) {
		return newSimpleDateFormat(pattern, Locale.getDefault(), zone);
	}
	
	private CalendarUtil() {
	}
}
