/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.TimeZone;

import jp.sf.amateras.mirage.bean.PropertyDesc;
import jp.sf.amateras.mirage.type.ValueType;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarUtil;

/**
 * {@link CalendarDate}用{@link ValueType}実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
public class CalendarDateValueType extends AbstractBaseunitsValueType<CalendarDate> {
	
	private static final TimeZone UTC = TimeZone.getTimeZone("Universal");
	
	private static final Calendar CALENDAR = CalendarUtil.newCalendar(UTC);
	
	
	@Override
	public CalendarDate get(Class<? extends CalendarDate> type, CallableStatement cs, int index) throws SQLException {
		return get(cs.getDate(index, CALENDAR));
	}
	
	@Override
	public CalendarDate get(Class<? extends CalendarDate> type, CallableStatement cs, String parameterName)
			throws SQLException {
		return get(cs.getDate(parameterName, CALENDAR));
	}
	
	@Override
	public CalendarDate get(Class<? extends CalendarDate> type, ResultSet rs, int index) throws SQLException {
		return get(rs.getDate(index, CALENDAR));
	}
	
	@Override
	public CalendarDate get(Class<? extends CalendarDate> type, ResultSet rs, String columnName) throws SQLException {
		return get(rs.getDate(columnName, CALENDAR));
	}
	
	@Override
	public Class<?> getJavaType(int sqlType) {
		return CalendarDate.class;
	}
	
	@Override
	public boolean isSupport(Class<?> type, PropertyDesc propertyDesc) {
		return CalendarDate.class.isAssignableFrom(type);
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
	public void set(Class<? extends CalendarDate> type, PreparedStatement stmt, CalendarDate value, int index)
			throws SQLException {
		if (value == null) {
			stmt.setNull(index, Types.DATE);
		} else {
			long epochMillisec = value.startAsTimePoint(UTC).toEpochMillisec();
			stmt.setDate(index, new Date(epochMillisec), CALENDAR);
		}
	}
	
	private CalendarDate get(Date date) {
		if (date == null) {
			return null;
		}
		return CalendarDate.from(date, UTC);
	}
}
