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
 * TODO for daisuke
 */
public class AndDateSpecification extends AbstractDateSpecification {
	
	private final DateSpecification spec1;
	
	private final DateSpecification spec2;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param spec1 left hand Specification.
	 * @param spec2 right hand Specification.
	 */
	public AndDateSpecification(DateSpecification spec1, DateSpecification spec2) {
		Validate.notNull(spec1);
		Validate.notNull(spec2);
		this.spec1 = spec1;
		this.spec2 = spec2;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate t) {
		return spec1.isSatisfiedBy(t) && spec2.isSatisfiedBy(t);
	}
}
