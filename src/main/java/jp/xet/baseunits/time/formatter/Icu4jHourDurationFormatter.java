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
package jp.xet.baseunits.time.formatter;

import java.io.Serializable;
import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import com.google.common.base.Preconditions;
import com.ibm.icu.text.TimeUnitFormat;
import com.ibm.icu.util.TimeUnitAmount;
import com.ibm.icu.util.ULocale;

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
	
	@Override
	public String format(Duration target, Locale locale) {
		Preconditions.checkNotNull(target);
		Preconditions.checkNotNull(locale);
		
		ULocale uLocale = ULocale.forLocale(locale);
		TimeUnitFormat format = new TimeUnitFormat(uLocale);
		
		long h = target.to(TimeUnit.hour);
		return format.format(new TimeUnitAmount(h, com.ibm.icu.util.TimeUnit.HOUR)).trim();
	}
}
