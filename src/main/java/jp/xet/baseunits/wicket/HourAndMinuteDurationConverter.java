/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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
package jp.xet.baseunits.wicket;

import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.formatter.DurationFormatter;
import jp.xet.baseunits.time.formatter.Icu4jHourAndMinuteDurationFormatter;

import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * Converts from Object to {@link Duration}.
 * 
 * @author daisuke
 * @since 2.0
 * @deprecated use {@link DurationConverter}
 */
@Deprecated
@SuppressWarnings("serial")
public class HourAndMinuteDurationConverter extends AbstractConverter<Duration> {
	
	private final DurationFormatter durationFormatter;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 2.0
	 */
	public HourAndMinuteDurationConverter() {
		this(true);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param displayZeroMinute 末尾の値0部を表示する場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.4
	 */
	public HourAndMinuteDurationConverter(boolean displayZeroMinute) {
		durationFormatter = new Icu4jHourAndMinuteDurationFormatter(displayZeroMinute);
	}
	
	@Override
	public Duration convertToObject(String value, Locale locale) {
		// TODO 対応できる？
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String convertToString(Duration value, Locale locale) {
		return value == null ? null : durationFormatter.format(value, locale);
	}
	
	@Override
	protected Class<Duration> getTargetType() {
		return Duration.class;
	}
}
