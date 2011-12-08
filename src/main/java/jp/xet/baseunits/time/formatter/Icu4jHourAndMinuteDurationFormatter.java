/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/22
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

import java.io.Serializable;
import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import com.ibm.icu.text.TimeUnitFormat;
import com.ibm.icu.util.TimeUnitAmount;
import com.ibm.icu.util.ULocale;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO for daisuke
 */
@SuppressWarnings("serial")
public class Icu4jHourAndMinuteDurationFormatter extends AbstractDurationFormatter implements Serializable {
	
	private static Logger logger = LoggerFactory.getLogger(Icu4jHourAndMinuteDurationFormatter.class);
	
	
	@Override
	public String format(Duration target, Locale locale) {
		Validate.notNull(target);
		Validate.notNull(locale);
		
		ULocale uLocale = ULocale.forLocale(locale);
		if (logger.isTraceEnabled()) {
			logger.trace("convert Locale [{}] to ULocale[{}]", locale, uLocale);
		}
		TimeUnitFormat format = new TimeUnitFormat(uLocale);
		
		long h = target.to(TimeUnit.hour);
		long m = target.minus(Duration.hours(h)).to(TimeUnit.minute);
		
		StringBuilder sb = new StringBuilder();
		if (h != 0) {
			sb.append(format.format(new TimeUnitAmount(h, com.ibm.icu.util.TimeUnit.HOUR)));
		}
		sb.append(format.format(new TimeUnitAmount(m, com.ibm.icu.util.TimeUnit.MINUTE)));
		return sb.toString();
	}
}
