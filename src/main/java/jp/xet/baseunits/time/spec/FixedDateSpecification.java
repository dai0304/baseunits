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
package jp.xet.baseunits.time.spec;

import java.io.Serializable;
import java.util.Iterator;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;

/**
 * ある特定の唯一の暦日を表す暦日仕様実装クラス。
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public final class FixedDateSpecification extends AbstractDateSpecification implements Serializable {
	
	final CalendarDate date;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param date 暦日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	FixedDateSpecification(CalendarDate date) {
		Preconditions.checkNotNull(date);
		this.date = date;
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Preconditions.checkNotNull(interval);
		Preconditions.checkArgument(interval.hasLowerLimit());
		if (interval.includes(date)) {
			return date;
		}
		return null;
	}
	
	/**
	 * 仕様を満たす唯一の暦日を返す。
	 * 
	 * @return 暦日
	 * @since 1.0
	 */
	public CalendarDate getDate() {
		return date;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Preconditions.checkNotNull(date);
		return date.equals(this.date);
	}
	
	@Override
	public Iterator<CalendarDate> iterateOver(CalendarInterval interval) {
		if (interval.includes(date)) {
			return Iterators.singletonIterator(date);
		} else {
			return ImmutableSet.<CalendarDate> of().iterator();
		}
	}
	
	@Override
	public String toString() {
		return date.toString();
	}
}
