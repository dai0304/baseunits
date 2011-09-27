/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/09/26
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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com)
 * This free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.tricreo.baseunits.hibernate;

import java.sql.Types;

import jp.tricreo.baseunits.time.TimeOfDay;

import org.hibernate.type.StandardBasicTypes;

/**
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class PersistentTimeOfDay extends AbstractBaseunitsType<TimeOfDay, Integer> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.INTEGER
	};
	
	
	/**
	 * インスタンスを生成する。
	 */
	public PersistentTimeOfDay() {
		super(StandardBasicTypes.INTEGER);
	}
	
	@Override
	public Class<TimeOfDay> returnedClass() {
		return TimeOfDay.class;
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected TimeOfDay fromNonNullInternalType(Integer value) {
		return TimeOfDay.from(value / 60, value % 60); // CHECKSTYLE IGNORE THIS LINE
	}
	
	@Override
	protected Integer toNonNullInternalType(TimeOfDay value) {
		int h = value.breachEncapsulationOfHour().breachEncapsulationOfValue();
		int m = value.breachEncapsulationOfMinute().breachEncapsulationOfValue();
		return (h * 60) + m; // CHECKSTYLE IGNORE THIS LINE
	}
}
