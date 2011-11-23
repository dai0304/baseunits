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

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;

import org.apache.commons.lang.Validate;

/**
 * {@link DateSpecification}の論理和をとるクラス。
 * 
 * @since 2.0
 */
public final class OrDateSpecification extends AbstractDateSpecification {
	
	final DateSpecification left;
	
	final DateSpecification right;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param left left side Specification.
	 * @param right right side Specification.
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public OrDateSpecification(DateSpecification left, DateSpecification right) {
		Validate.notNull(left);
		Validate.notNull(right);
		this.left = left;
		this.right = right;
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		// TODO 自己嫌悪で死にそうなくらいダサい。refactor必須。
		CalendarDate first1 = null;
		boolean exception1 = false;
		CalendarDate first2 = null;
		boolean exception2 = false;
		
		try {
			first1 = left.firstOccurrenceIn(interval);
		} catch (IllegalArgumentException e) {
			exception1 = true;
		}
		
		try {
			first2 = right.firstOccurrenceIn(interval);
		} catch (IllegalArgumentException e) {
			exception2 = true;
		}
		
		if (exception1 && exception2) {
			throw new IllegalArgumentException();
		}
		
		if (first1 == null && first2 == null) {
			return null;
		}
		if (first1 == null) {
			return first2;
		}
		if (first2 == null) {
			return first1;
		}
		if (first1.isBefore(first2)) {
			return first1;
		}
		return first2;
	}
	
	/**
	 * 左辺値を返す。
	 * 
	 * @return 左辺値
	 */
	public DateSpecification getLeft() {
		return left;
	}
	
	/**
	 * 右辺値を返す。
	 * 
	 * @return 右辺値
	 */
	public DateSpecification getRight() {
		return right;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate t) {
		return left.isSatisfiedBy(t) || right.isSatisfiedBy(t);
	}
}
