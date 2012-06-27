/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2012/06/20
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
 * ICU4Jを利用した、時レベルの {@link DurationFormatter} 実装クラス。
 * 
 * <p>序算で発生した余りは切り捨てる。</p>
 * 
 * @author daisuke
 * @since 2.4
 * @deprecated use {@link DetailedDurationFormatter}
 */
@Deprecated
@SuppressWarnings("serial")
public class Icu4jHourDurationFormatter extends AbstractDurationFormatter implements Serializable {
	
	private static Logger logger = LoggerFactory.getLogger(Icu4jHourDurationFormatter.class);
	
	
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
		return format.format(new TimeUnitAmount(h, com.ibm.icu.util.TimeUnit.HOUR)).trim();
	}
}
