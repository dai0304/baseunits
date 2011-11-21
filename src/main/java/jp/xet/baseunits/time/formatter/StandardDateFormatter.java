/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/21
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
package jp.xet.baseunits.time.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.ibm.icu.impl.duration.DateFormatter;
import com.ibm.icu.impl.duration.impl.Utils;

/**
 * A DateFormatter that formats the requested date fields.
 */
public class StandardDateFormatter implements DateFormatter {
	
	private final String format;
	
	private final String localeName;
	
	private final TimeZone timeZone;
	
	private final SimpleDateFormat df;
	
	
	public StandardDateFormatter() {
		this("yyyy/MM/dd", Locale.getDefault().toString(), TimeZone.getDefault());
	}
	
	/**
	 * Creates a new formatter that formats the requested 
	 * fields.  The formatter defaults to the current locale
	 * and time zone.
	 */
	public StandardDateFormatter(String format) {
		this(format, Locale.getDefault().toString(), TimeZone.getDefault());
	}
	
	/**
	 * Creates a new formatter that formats the requested 
	 * fields using the provided locale and time zone.
	 *
	 * @param localeName the locale to use
	 * @param timeZone the time zone to use
	 */
	public StandardDateFormatter(String format, String localeName, TimeZone timeZone) {
		this.format = format;
		this.localeName = localeName;
		this.timeZone = timeZone;
		
		Locale locale = Utils.localeFromString(localeName);
		df = new SimpleDateFormat(format, locale);
		df.setTimeZone(timeZone);
	}
	
	/**
	 * Returns a string representing the formatted date.
	 * @param date the date
	 */
	public String format(Date date) {
		return df.format(date);
	}
	
	/**
	 * Returns a string representing the formatted date.
	 * @param date the date in milliseconds
	 */
	public String format(long date) {
		return format(new Date(date));
	}
	
	/**
	 * Returns a version of this formatter customized to the provided locale.
	 */
	public DateFormatter withLocale(String locName) {
		if (locName.equals(localeName) == false) {
			return new StandardDateFormatter(format, locName, timeZone);
		}
		return this;
	}
	
	/**
	 * Returns a version of this formatter customized to the provided time zone.
	 */
	public DateFormatter withTimeZone(TimeZone tz) {
		if (tz.equals(timeZone) == false) {
			return new StandardDateFormatter(format, localeName, tz);
		}
		return this;
	}
}
