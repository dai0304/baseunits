/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/21
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
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarUtil;
import jp.xet.baseunits.time.TimePoint;

import com.google.common.base.Preconditions;

/**
 * A DateFormatter that formats the requested date fields.
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class StandardTimePointFormatter extends AbstractTimePointFormatter implements Serializable {
	
	private final String format;
	
	
	/**
	 * Constructs.
	 */
	public StandardTimePointFormatter() {
		this("yyyy/MM/dd");
	}
	
	/**
	 * Creates a new formatter.
	 * 
	 * @param format the format string
	 */
	public StandardTimePointFormatter(String format) {
		this.format = format;
	}
	
	@Override
	public String format(TimePoint target, Locale locale, TimeZone timeZone) {
		Preconditions.checkNotNull(target);
		Preconditions.checkNotNull(locale);
		Preconditions.checkNotNull(timeZone);
		
		SimpleDateFormat df = CalendarUtil.newSimpleDateFormat(format, locale, timeZone);
		return df.format(target.asJavaUtilDate());
	}
}
