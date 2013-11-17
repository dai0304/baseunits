/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 */
package jp.xet.baseunits.hibernate;

import java.sql.Types;
import java.util.Date;
import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.TimeOfDay;
import jp.xet.baseunits.time.TimePoint;

import org.hibernate.type.StandardBasicTypes;

/**
 * {@link TimeOfDay}を午前0時からの経過分数としてDBにデータを保存するHibernateユーザ型。
 * 
 * @author daisuke
 * @since 1.2
 */
@SuppressWarnings("serial")
public class PersistentTimeOfDay extends AbstractBaseunitsType<TimeOfDay, Date> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.TIME
	};
	
	private static final CalendarDate EPOCH_DATE = CalendarDate.from(1970, 1, 1);
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.2
	 */
	public PersistentTimeOfDay() {
		super(StandardBasicTypes.TIME);
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
	protected TimeOfDay fromNonNullInternalType(Date value) {
		TimePoint tp = TimePoint.from(value);
		TimeOfDay tod = tp.asTimeOfDay(TimeZone.getDefault());
		return tod;
	}
	
	@Override
	protected Date toNonNullInternalType(TimeOfDay value) {
		TimePoint tp = value.asTimePointGiven(EPOCH_DATE, TimeZone.getDefault());
		Date date = tp.asJavaUtilDate();
		return date;
	}
}
