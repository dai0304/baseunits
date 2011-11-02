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

import org.apache.commons.lang.Validate;

/**
 * {@link DateSpecification}の論理積をとるクラス。
 * 
 * @since 2.0
 */
public class AndDateSpecification extends AbstractDateSpecification {
	
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
	public AndDateSpecification(DateSpecification left, DateSpecification right) {
		Validate.notNull(left);
		Validate.notNull(right);
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate t) {
		return left.isSatisfiedBy(t) && right.isSatisfiedBy(t);
	}
}
