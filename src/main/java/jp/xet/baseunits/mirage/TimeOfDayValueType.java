/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/24
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
package jp.xet.baseunits.mirage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.TimeZone;

import jp.sf.amateras.mirage.type.ValueType;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.TimeOfDay;
import jp.xet.baseunits.time.TimePoint;

/**
 * {@link TimeOfDay}用{@link ValueType}実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
public class TimeOfDayValueType extends AbstractBaseunitsValueType {
	
	private static final CalendarDate EPOCH_DATE = CalendarDate.from(1970, 1, 1);
	
	
	@Override
	public Object get(Class<?> type, CallableStatement cs, int index) throws SQLException {
		Time time = cs.getTime(index);
		if (time == null) {
			return null;
		}
		long millisec = time.getTime();
		TimePoint tp = TimePoint.from(millisec);
		return tp.asTimeOfDay(TimeZone.getDefault());
	}
	
	@Override
	public Object get(Class<?> type, CallableStatement cs, String parameterName) throws SQLException {
		Time time = cs.getTime(parameterName);
		if (time == null) {
			return null;
		}
		long millisec = time.getTime();
		TimePoint tp = TimePoint.from(millisec);
		return tp.asTimeOfDay(TimeZone.getDefault());
	}
	
	@Override
	public Object get(Class<?> type, ResultSet rs, int index) throws SQLException {
		Time time = rs.getTime(index);
		if (time == null) {
			return null;
		}
		long millisec = time.getTime();
		TimePoint tp = TimePoint.from(millisec);
		return tp.asTimeOfDay(TimeZone.getDefault());
	}
	
	@Override
	public Object get(Class<?> type, ResultSet rs, String columnName) throws SQLException {
		Time time = rs.getTime(columnName);
		if (time == null) {
			return null;
		}
		long millisec = time.getTime();
		TimePoint tp = TimePoint.from(millisec);
		return tp.asTimeOfDay(TimeZone.getDefault());
	}
	
	@Override
	public Class<?> getJavaType(int sqlType) {
		return Time.class;
	}
	
	@Override
	public boolean isSupport(Class<?> type) {
		return TimeOfDay.class.isAssignableFrom(type);
	}
	
	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, int index) throws SQLException {
		cs.registerOutParameter(index, Types.TIME);
	}
	
	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, String parameterName) throws SQLException {
		cs.registerOutParameter(parameterName, Types.TIME);
	}
	
	@Override
	public void set(Class<?> type, PreparedStatement stmt, Object value, int index) throws SQLException {
		if (value == null) {
			stmt.setNull(index, Types.TIME);
		} else {
			long epochMillisec =
					((TimeOfDay) value).asTimePointGiven(EPOCH_DATE, TimeZone.getDefault()).toEpochMillisec();
			stmt.setTime(index, new Time(epochMillisec));
		}
	}
	
}
