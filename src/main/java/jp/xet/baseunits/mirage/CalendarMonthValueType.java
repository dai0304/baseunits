/*
 * Copyright 2010-2018 Miyamoto Daisuke.
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
package jp.xet.baseunits.mirage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.TimeZone;

import com.miragesql.miragesql.bean.PropertyDesc;
import com.miragesql.miragesql.type.ValueType;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarMonth;

/**
 * {@link CalendarMonth}用{@link ValueType}実装クラス。
 * 
 * @author daisuke
 * @since 2.4
 */
public class CalendarMonthValueType extends AbstractBaseunitsValueType<CalendarMonth> {
	
	@Override
	public CalendarMonth get(Class<? extends CalendarMonth> type, CallableStatement cs, int index) throws SQLException {
		java.sql.Date date = cs.getDate(index);
		if (date == null) {
			return null;
		}
		return CalendarDate.from(date, TimeZone.getDefault()).asCalendarMonth();
	}
	
	@Override
	public CalendarMonth get(Class<? extends CalendarMonth> type, CallableStatement cs, String parameterName)
			throws SQLException {
		java.sql.Date date = cs.getDate(parameterName);
		if (date == null) {
			return null;
		}
		return CalendarDate.from(date, TimeZone.getDefault()).asCalendarMonth();
	}
	
	@Override
	public CalendarMonth get(Class<? extends CalendarMonth> type, ResultSet rs, int index) throws SQLException {
		java.sql.Date date = rs.getDate(index);
		if (date == null) {
			return null;
		}
		return CalendarDate.from(date, TimeZone.getDefault()).asCalendarMonth();
	}
	
	@Override
	public CalendarMonth get(Class<? extends CalendarMonth> type, ResultSet rs, String columnName) throws SQLException {
		java.sql.Date date = rs.getDate(columnName);
		if (date == null) {
			return null;
		}
		return CalendarDate.from(date, TimeZone.getDefault()).asCalendarMonth();
	}
	
	@Override
	public Class<?> getJavaType(int sqlType) {
		return java.sql.Date.class;
	}
	
	@Override
	public boolean isSupport(Class<?> type, PropertyDesc propertyDesc) {
		return CalendarMonth.class.isAssignableFrom(type);
	}
	
	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, int index) throws SQLException {
		cs.registerOutParameter(index, Types.DATE);
	}
	
	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, String parameterName) throws SQLException {
		cs.registerOutParameter(parameterName, Types.DATE);
	}
	
	@Override
	public void set(Class<? extends CalendarMonth> type, PreparedStatement stmt, CalendarMonth value, int index)
			throws SQLException {
		if (value == null) {
			stmt.setNull(index, Types.DATE);
		} else {
//			long epochMillisec = value.startAsTimePoint(TimeZone.getDefault()).toEpochMillisec();
//			stmt.setDate(index, new java.sql.Date(epochMillisec));
			stmt.setString(index, value.toString("yyyy-MM-01"));
		}
	}
}
