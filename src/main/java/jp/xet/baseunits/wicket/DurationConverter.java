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
import jp.xet.baseunits.time.formatter.Icu4jDurationFormatter;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.Args;

/**
 * Converts from Object to {@link Duration}.
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class DurationConverter extends AbstractConverter<Duration> {
	
	private final DurationFormatter durationFormatter;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 2.0
	 */
	public DurationConverter() {
		this(new Icu4jDurationFormatter());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param durationFormatter {@link DurationFormatter}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.4
	 */
	public DurationConverter(DurationFormatter durationFormatter) {
		Args.notNull(durationFormatter, "durationFormatter");
		this.durationFormatter = durationFormatter;
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
