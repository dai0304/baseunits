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
import java.text.ParseException;
import java.util.TimeZone;

import jp.sf.amateras.mirage.type.ValueType;
import jp.xet.baseunits.time.TimePointOfDay;

/**
 * {@link TimePointOfDay}用{@link ValueType}実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
public class TimePointOfDayValueType extends AbstractBaseunitsValueType<TimePointOfDay> {
	
	private static final String PATTERN = "HH:mm:ss";
	
	private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
	
	
	@Override
	public TimePointOfDay get(Class<? extends TimePointOfDay> type, CallableStatement cs, int index)
			throws SQLException {
		String time = cs.getString(index);
		if (time == null) {
			return null;
		}
		try {
			return TimePointOfDay.parse(time, PATTERN, UTC);
		} catch (ParseException e) {
			return null;
		}
	}
	
	@Override
	public TimePointOfDay get(Class<? extends TimePointOfDay> type, CallableStatement cs, String parameterName)
			throws SQLException {
		String time = cs.getString(parameterName);
		if (time == null) {
			return null;
		}
		try {
			return TimePointOfDay.parse(time, PATTERN, UTC);
		} catch (ParseException e) {
			return null;
		}
	}
	
	@Override
	public TimePointOfDay get(Class<? extends TimePointOfDay> type, ResultSet rs, int index) throws SQLException {
		String time = rs.getString(index);
		if (time == null) {
			return null;
		}
		try {
			return TimePointOfDay.parse(time, PATTERN, UTC);
		} catch (ParseException e) {
			return null;
		}
	}
	
	@Override
	public TimePointOfDay get(Class<? extends TimePointOfDay> type, ResultSet rs, String columnName)
			throws SQLException {
		String time = rs.getString(columnName);
		if (time == null) {
			return null;
		}
		try {
			return TimePointOfDay.parse(time, PATTERN, UTC);
		} catch (ParseException e) {
			return null;
		}
	}
	
	@Override
	public Class<?> getJavaType(int sqlType) {
		return Time.class;
	}
	
	@Override
	public boolean isSupport(Class<?> type) {
		return TimePointOfDay.class.isAssignableFrom(type);
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
	public void set(Class<? extends TimePointOfDay> type, PreparedStatement stmt, TimePointOfDay value, int index)
			throws SQLException {
		if (value == null) {
			stmt.setNull(index, Types.TIME);
		} else {
			stmt.setString(index, value.toString(PATTERN, UTC));
		}
	}
}
