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
package jp.xet.baseunits.hibernate;

import java.sql.Types;

import jp.xet.baseunits.time.TimeOfDay;

import org.hibernate.type.StandardBasicTypes;

/**
 * {@link TimeOfDay}を午前0時からの経過分数としてDBにデータを保存するHibernateユーザ型。
 * 
 * @author daisuke
 * @since 1.2
 */
@SuppressWarnings("serial")
public class PersistentMinutesTimeOfDay extends AbstractBaseunitsType<TimeOfDay, Integer> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.INTEGER
	};
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.2
	 */
	public PersistentMinutesTimeOfDay() {
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
		return TimeOfDay.from(value / 60, value % 60, 0, 0); // CHECKSTYLE IGNORE THIS LINE
	}
	
	@Override
	protected Integer toNonNullInternalType(TimeOfDay value) {
		int h = value.breachEncapsulationOfHour().breachEncapsulationOfValue();
		int m = value.breachEncapsulationOfMinute().breachEncapsulationOfValue();
		return (h * 60) + m; // CHECKSTYLE IGNORE THIS LINE
	}
}
