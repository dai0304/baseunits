/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/18
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
package jp.xet.baseunits.wicket;

import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.formatter.DurationFormatter;
import jp.xet.baseunits.time.formatter.Icu4jDurationFormatter;

import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * Converts from Object to {@link Duration}.
 * 
 * @author daisuke
 */
@SuppressWarnings("serial")
public class DurationConverter extends AbstractConverter<Duration> {
	
	DurationFormatter durationFormatter = new Icu4jDurationFormatter();
	
	
	@Override
	public Duration convertToObject(String value, Locale locale) {
		// TODO 対応できる？
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String convertToString(Duration value, Locale locale) {
		return durationFormatter.format(value, locale);
	}
	
	@Override
	protected Class<Duration> getTargetType() {
		return Duration.class;
	}
}
