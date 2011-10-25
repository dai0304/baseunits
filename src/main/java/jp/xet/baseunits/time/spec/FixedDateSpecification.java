/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
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
 * ----
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time.spec;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.util.ImmutableIterator;

import org.apache.commons.lang.Validate;

/**
 * ある特定の年月日を表す日付仕様。
 */
class FixedDateSpecification extends AbstractDateSpecivifation {
	
	final CalendarDate date;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param date 日付
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	FixedDateSpecification(CalendarDate date) {
		Validate.notNull(date);
		this.date = date;
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		if (interval.includes(date)) {
			return date;
		}
		return null;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		return date.equals(this.date);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Iterator<CalendarDate> iterateOver(CalendarInterval interval) {
		if (firstOccurrenceIn(interval) == null) {
			return Collections.EMPTY_LIST.iterator();
		}
		return new ImmutableIterator<CalendarDate>() {
			
			boolean end;
			
			
			@Override
			public boolean hasNext() {
				return end;
			}
			
			@Override
			public CalendarDate next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				end = true;
				return date;
			}
		};
	}
	
	@Override
	public String toString() {
		return date.toString();
	}
}
