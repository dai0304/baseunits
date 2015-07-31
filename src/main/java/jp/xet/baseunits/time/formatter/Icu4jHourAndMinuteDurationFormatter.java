/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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

import com.google.common.base.Preconditions;
import com.ibm.icu.text.TimeUnitFormat;
import com.ibm.icu.util.TimeUnitAmount;
import com.ibm.icu.util.ULocale;

/**
 * ICU4Jを利用した、時・分レベルの {@link DurationFormatter} 実装クラス。
 * 
 * <p>序算で発生した余りは切り捨てる。</p>
 * 
 * @author daisuke
 * @since 2.0
 * @deprecated use {@link DetailedDurationFormatter}
 */
@Deprecated
@SuppressWarnings("serial")
public class Icu4jHourAndMinuteDurationFormatter extends AbstractDurationFormatter implements Serializable {
	
	private final boolean displayZeroMinute;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 2.0
	 */
	public Icu4jHourAndMinuteDurationFormatter() {
		this(true);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param displayZeroMinute "0分"を表示する場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.3
	 */
	public Icu4jHourAndMinuteDurationFormatter(boolean displayZeroMinute) {
		this.displayZeroMinute = displayZeroMinute;
	}
	
	@Override
	public String format(Duration target, Locale locale) {
		Preconditions.checkNotNull(target);
		Preconditions.checkNotNull(locale);
		
		ULocale uLocale = ULocale.forLocale(locale);
		TimeUnitFormat format = new TimeUnitFormat(uLocale);
		
		long h = target.to(TimeUnit.hour);
		long m = target.minus(Duration.hours(h)).to(TimeUnit.minute);
		
		StringBuilder sb = new StringBuilder();
		if (h != 0) {
			sb.append(format.format(new TimeUnitAmount(h, com.ibm.icu.util.TimeUnit.HOUR))).append(' ');
		}
		if (displayZeroMinute || (h == 0 || m != 0)) {
			sb.append(format.format(new TimeUnitAmount(m, com.ibm.icu.util.TimeUnit.MINUTE)));
		}
		return sb.toString().trim();
	}
}
