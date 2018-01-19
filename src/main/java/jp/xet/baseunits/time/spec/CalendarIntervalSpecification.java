/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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
package jp.xet.baseunits.time.spec;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;

import com.google.common.base.Preconditions;

import jp.xet.baseunits.intervals.Interval;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;

/**
 * ある特定の期間中にマッチする暦日仕様実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public final class CalendarIntervalSpecification extends AbstractDateSpecification implements Serializable {
	
	final CalendarInterval interval;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param interval 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	CalendarIntervalSpecification(CalendarInterval interval) {
		Preconditions.checkNotNull(interval);
		this.interval = interval;
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Preconditions.checkNotNull(interval);
		Preconditions.checkArgument(interval.hasLowerLimit() || this.interval.hasLowerLimit());
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
		Preconditions.checkNotNull(date);
		return interval.includes(date);
	}
	
	@Override
	public Iterator<CalendarDate> iterateOver(final CalendarInterval interval) {
		Interval<CalendarDate> i = this.interval.intersect(interval);
		if (i.isEmpty()) {
			return Collections.emptyIterator();
		}
		CalendarInterval intersect = CalendarInterval.inclusive(i.lowerLimit(), i.upperLimit());
		return intersect.daysIterator();
	}
	
	@Override
	public String toString() {
		return interval.toString();
	}
}
