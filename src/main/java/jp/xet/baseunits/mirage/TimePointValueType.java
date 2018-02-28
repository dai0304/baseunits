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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.TimeZone;

import jp.sf.amateras.mirage.bean.PropertyDesc;
import jp.sf.amateras.mirage.type.ValueType;
import jp.xet.baseunits.time.CalendarUtil;
import jp.xet.baseunits.time.TimePoint;

/**
 * {@link TimePoint}用{@link ValueType}実装クラス。
 * 
 * <p>MySQLにおいては、JDBC URLにおいて{@code useLegacyDatetimeCode=false}オプションを指定しなければ、
 * DB上に保存されるデータにズレが発生する。常に同じタイムゾーンからアクセスしていれば問題は顕在化しないが、
 * 複数のタイムゾーンから同じデータにアクセスした際に時間がずれる。</p>
 * 
 * <p>see http://bugs.mysql.com/bug.php?id=15604</p>
 * 
 * @author daisuke
 * @since 2.0
 */
public class TimePointValueType extends AbstractBaseunitsValueType<TimePoint> {
	
	private static final TimeZone UTC = TimeZone.getTimeZone("Universal");
	
	private static final Calendar CALENDAR = CalendarUtil.newCalendar(UTC);
	
	
	@Override
	public TimePoint get(Class<? extends TimePoint> type, CallableStatement cs, int index) throws SQLException {
		Timestamp date = cs.getTimestamp(index, CALENDAR);
		if (date == null) {
			return null;
		}
		return TimePoint.from(date);
	}
	
	@Override
	public TimePoint get(Class<? extends TimePoint> type, CallableStatement cs, String parameterName)
			throws SQLException {
		Timestamp date = cs.getTimestamp(parameterName, CALENDAR);
		if (date == null) {
			return null;
		}
		return TimePoint.from(date);
	}
	
	@Override
	public TimePoint get(Class<? extends TimePoint> type, ResultSet rs, int index) throws SQLException {
		Timestamp date = rs.getTimestamp(index, CALENDAR);
		if (date == null) {
			return null;
		}
		return TimePoint.from(date);
	}
	
	@Override
	public TimePoint get(Class<? extends TimePoint> type, ResultSet rs, String columnName) throws SQLException {
		Timestamp date = rs.getTimestamp(columnName, CALENDAR);
		if (date == null) {
			return null;
		}
		return TimePoint.from(date);
	}
	
	@Override
	public Class<?> getJavaType(int sqlType) {
		return Timestamp.class;
	}
	
	@Override
	public boolean isSupport(Class<?> type, PropertyDesc propertyDesc) {
		return TimePoint.class.isAssignableFrom(type);
	}
	
	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, int index) throws SQLException {
		cs.registerOutParameter(index, Types.TIMESTAMP);
	}
	
	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, String parameterName) throws SQLException {
		cs.registerOutParameter(parameterName, Types.TIMESTAMP);
	}
	
	@Override
	public void set(Class<? extends TimePoint> type, PreparedStatement stmt, TimePoint value, int index)
			throws SQLException {
		if (value == null) {
			stmt.setNull(index, Types.TIMESTAMP);
		} else {
			long epochMillisec = value.toEpochMillisec();
			stmt.setTimestamp(index, new Timestamp(epochMillisec), CALENDAR);
		}
	}
}
