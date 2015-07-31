/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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

import com.google.common.base.Preconditions;

/**
 * {@link DateSpecification}用のNOT（否定）ラッパークラス。
 * 
 * @author daisuke
 * @since 2.0
 */
public final class NotDateSpecification extends AbstractDateSpecification {
	
	final DateSpecification spec;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param spec Specification instance to not.
	 * @since 2.0
	 */
	public NotDateSpecification(DateSpecification spec) {
		Preconditions.checkNotNull(spec);
		this.spec = spec;
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Preconditions.checkNotNull(interval);
		
		if (interval.hasLowerLimit()) {
			Iterator<CalendarDate> itr = interval.daysIterator();
			while (itr.hasNext()) {
				CalendarDate date = itr.next();
				if (isSatisfiedBy(date)) {
					return date;
				}
			}
		} else {
			// TODO ここ、算出できるか…？
			throw new RuntimeException("not implemented");
		}
		return null;
	}
	
	/**
	 * NOT（否定）の判定基礎となる{@link DateSpecification}を返す。
	 * 
	 * @return NOT（否定）の判定基礎となる{@link DateSpecification}
	 */
	public DateSpecification getSpec() {
		return spec;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate t) {
		return spec.isSatisfiedBy(t) == false;
	}
}
