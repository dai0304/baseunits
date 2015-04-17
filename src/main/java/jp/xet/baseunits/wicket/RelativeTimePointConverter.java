/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2012/10/02
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
import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.formatter.RelativeTimePointFormatter;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.Args;

/**
 * Converts from Object to {@link Duration}.
 * 
 * @author daisuke
 * @since 2.7
 */
@SuppressWarnings("serial")
public class RelativeTimePointConverter extends AbstractConverter<TimePoint> {
	
	private final RelativeTimePointFormatter formatter;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param formatter 変換に用いるフォーマッタ
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.7
	 */
	public RelativeTimePointConverter(RelativeTimePointFormatter formatter) {
		Args.notNull(formatter, "formatter");
		this.formatter = formatter;
	}
	
	@Override
	public TimePoint convertToObject(String value, Locale locale) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String convertToString(TimePoint value, Locale locale) {
		return formatter.format(value, locale);
	}
	
	@Override
	protected Class<TimePoint> getTargetType() {
		return TimePoint.class;
	}
}
