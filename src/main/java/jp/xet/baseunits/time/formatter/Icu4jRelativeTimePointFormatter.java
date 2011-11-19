/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/19
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

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;

import com.ibm.icu.impl.duration.BasicPeriodFormatterService;
import com.ibm.icu.impl.duration.DateFormatter;
import com.ibm.icu.impl.duration.DurationFormatter;
import com.ibm.icu.impl.duration.DurationFormatterFactory;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO for daisuke
 * 
 * @since 2.0
 * @version $Id$
 * @author daisuke
 */
public class Icu4jRelativeTimePointFormatter extends AbstractRelativeTimePointFormatter {
	
	private static Logger logger = LoggerFactory.getLogger(Icu4jRelativeTimePointFormatter.class);
	
	private static final BasicPeriodFormatterService SERVICE = BasicPeriodFormatterService.getInstance();
	
	
	@Override
	public String format(TimePoint target, TimePoint standard, Locale locale, FallbackConfig config) {
		Validate.notNull(target);
		Validate.notNull(standard);
		Validate.notNull(locale);
		
		long t = target.toEpochMillisec();
		long s = standard.toEpochMillisec();
		
		DurationFormatterFactory dff = SERVICE.newDurationFormatterFactory();
		if (SERVICE.getAvailableLocaleNames().contains(locale.getLanguage())) {
			dff.setLocale(locale.getLanguage());
		} else {
			logger.warn("Ignore unsupported locale by ICU4J: {}", locale.getLanguage());
			dff.setLocale("en");
		}
		if (config != null) {
			dff.setFallbackLimit(config.getFallbackLimit());
			dff.setFallback(new FixedDateFormatter(config.getFallbackString()));
		}
		DurationFormatter df = dff.getFormatter();
		String result = df.formatDurationFrom(t - s, s);
		return result;
	}
	
	
	private static class FixedDateFormatter implements DateFormatter {
		
		private final String fixed;
		
		
		FixedDateFormatter(String fixed) {
			this.fixed = fixed;
		}
		
		@Override
		public String format(Date date) {
			return format(date.getTime());
		}
		
		@Override
		public String format(long date) {
			return fixed;
		}
		
		@Override
		public DateFormatter withLocale(String localeName) {
			return this;
		}
		
		@Override
		public DateFormatter withTimeZone(TimeZone tz) {
			return this;
		}
	}
}
