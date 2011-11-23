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

import java.io.Serializable;
import java.util.Iterator;

import jp.xet.baseunits.intervals.Interval;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;

import com.google.common.collect.Iterators;

import org.apache.commons.lang.Validate;

/**
 * ある特定の期間中にマッチする日付仕様。
 * 
 * @since 2.0
 */
@SuppressWarnings("serial")
public final class CalendarIntervalSpecification extends AbstractDateSpecification implements Serializable {
	
	final CalendarInterval interval;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param interval 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	CalendarIntervalSpecification(CalendarInterval interval) {
		Validate.notNull(interval);
		this.interval = interval;
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Validate.notNull(interval);
		Validate.isTrue(interval.hasLowerLimit() || this.interval.hasLowerLimit());
		if (this.interval.intersects(interval) == false) {
			return null;
		}
		
		Interval<CalendarDate> intersect = this.interval.intersect(interval);
		return intersect.lowerLimit();
	}
	
	/**
	 * この仕様を満たす条件としての期間を返す。
	 * 
	 * @return この仕様を満たす条件としての期間
	 */
	public CalendarInterval getInterval() {
		return interval;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		return interval.includes(date);
	}
	
	@Override
	public Iterator<CalendarDate> iterateOver(final CalendarInterval interval) {
		if (firstOccurrenceIn(interval) == null) {
			return Iterators.emptyIterator();
		}
		Interval<CalendarDate> i = this.interval.intersect(interval);
		CalendarInterval intersect = CalendarInterval.inclusive(i.lowerLimit(), i.upperLimit());
		return intersect.daysIterator();
	}
	
	@Override
	public String toString() {
		return interval.toString();
	}
}
