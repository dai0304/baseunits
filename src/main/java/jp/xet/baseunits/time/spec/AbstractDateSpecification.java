/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/25
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
package jp.xet.baseunits.time.spec;

import java.util.Iterator;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.util.ImmutableIterator;
import jp.xet.baseunits.util.spec.AbstractSpecification;

import org.apache.commons.lang.Validate;

/**
 * 日付仕様の骨格実装クラス。
 * 
 * @since 2.0
 */
public abstract class AbstractDateSpecification extends AbstractSpecification<CalendarDate> implements
		DateSpecification {
	
	@Override
	public DateSpecification and(DateSpecification specification) {
		return new AndDateSpecification(this, specification);
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Validate.notNull(interval);
		Validate.isTrue(interval.hasLowerLimit());
		Iterator<CalendarDate> itr = interval.daysIterator();
		while (itr.hasNext()) {
			CalendarDate date = itr.next();
			if (isSatisfiedBy(date)) {
				return date;
			}
		}
		return null;
	}
	
	@Override
	public Iterator<CalendarDate> iterateOver(final CalendarInterval interval) {
		Validate.notNull(interval);
		return new ImmutableIterator<CalendarDate>() {
			
			CalendarDate next = firstOccurrenceIn(interval);
			
			
			@Override
			public boolean hasNext() {
				return next != null;
			}
			
			@Override
			public CalendarDate next() {
				CalendarDate current = next;
				
				do {
					next = next.nextDay();
				} while (isSatisfiedBy(next) == false && interval.includes(next));
				
				if (interval.includes(next) == false) {
					next = null;
				}
				
				return current;
			}
		};
	}
	
	@Override
	public DateSpecification not() {
		if (this instanceof NotDateSpecification) {
			NotDateSpecification not = (NotDateSpecification) this;
			return not.spec1;
		}
		return new NotDateSpecification(this);
	}
	
	@Override
	public DateSpecification or(DateSpecification specification) {
		return new OrDateSpecification(this, specification);
	}
}
