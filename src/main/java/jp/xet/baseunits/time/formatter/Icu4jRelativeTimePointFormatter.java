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

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.TimeUnit;

import com.ibm.icu.impl.duration.BasicPeriodFormatterService;
import com.ibm.icu.impl.duration.DurationFormatter;
import com.ibm.icu.impl.duration.DurationFormatterFactory;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ICU4Jを利用した {@link RelativeTimePointFormatter} 実装クラス。
 * 
 * @since 2.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class Icu4jRelativeTimePointFormatter extends AbstractRelativeTimePointFormatter implements Serializable {
	
	private static Logger logger = LoggerFactory.getLogger(Icu4jRelativeTimePointFormatter.class);
	
	private static final BasicPeriodFormatterService SERVICE = BasicPeriodFormatterService.getInstance();
	
	FallbackConfig config;
	
	TimeZone timeZone;
	
	
	/**
	 * インスタンスを生成する。
	 */
	public Icu4jRelativeTimePointFormatter() {
		this(null, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param config
	 * @param timeZone タイムゾーン
	 */
	public Icu4jRelativeTimePointFormatter(FallbackConfig config, TimeZone timeZone) {
		Validate.isTrue((config == null && timeZone == null) || (config != null && timeZone != null));
		this.config = config;
		this.timeZone = timeZone;
	}
	
	@Override
	public String format(TimePoint target, TimePoint standard, Locale locale) {
		Validate.notNull(target);
		Validate.notNull(standard);
		Validate.notNull(locale);
		
		long t = target.toEpochMillisec();
		long s = standard.toEpochMillisec();
		long delta = t - s;
		
		if (config != null && timeZone != null && config.getLowerFallbackLimit() != null
				&& Math.abs(delta) < config.getLowerFallbackLimit().to(TimeUnit.millisecond)) {
			return config.getLowerFallbackFormatter().format(target, locale, timeZone);
		}
		if (config != null && timeZone != null && config.getUpperFallbackLimit() != null
				&& Math.abs(delta) >= config.getUpperFallbackLimit().to(TimeUnit.millisecond)) {
			return config.getUpperFallbackFormatter().format(target, locale, timeZone);
		}
		
		DurationFormatterFactory dff = SERVICE.newDurationFormatterFactory();
		if (SERVICE.getAvailableLocaleNames().contains(locale.getLanguage())) {
			dff.setLocale(locale.getLanguage());
		} else {
			logger.warn("Ignore unsupported locale by ICU4J: {}", locale.getLanguage());
			dff.setLocale("en");
		}
		
		DurationFormatter df = dff.getFormatter();
		String result = df.formatDurationFrom(delta, s);
		return result;
	}
}
