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
import java.util.Date;
import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.TimePoint;

import org.hibernate.type.StandardBasicTypes;

/**
 * {@link CalendarDate}を {@link Date}としてDBにデータを保存するHibernateユーザ型。
 * 
 * @author daisuke
 * @since 1.2
 */
@SuppressWarnings("serial")
public class PersistentCalendarDate extends AbstractBaseunitsType<CalendarDate, Date> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.DATE
	};
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.2
	 */
	public PersistentCalendarDate() {
		super(StandardBasicTypes.DATE);
	}
	
	@Override
	public Class<CalendarDate> returnedClass() {
		return CalendarDate.class;
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected CalendarDate fromNonNullInternalType(Date value) {
		return CalendarDate.from(TimePoint.from(value), TimeZone.getDefault());
	}
	
	@Override
	protected Date toNonNullInternalType(CalendarDate value) {
		return value.startAsTimePoint(TimeZone.getDefault()).asJavaUtilDate();
	}
}
