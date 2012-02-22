/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
import java.sql.Types;

import jp.sf.amateras.mirage.type.ValueType;
import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

/**
 * {@link Duration}用{@link ValueType}実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
public class DurationSecValueType extends AbstractBaseunitsValueType<Duration> {
	
	@Override
	public Duration get(Class<? extends Duration> type, CallableStatement cs, int index) throws SQLException {
		long duration = cs.getLong(index);
		return Duration.seconds(duration);
	}
	
	@Override
	public Duration get(Class<? extends Duration> type, CallableStatement cs, String parameterName) throws SQLException {
		long duration = cs.getLong(parameterName);
		return Duration.seconds(duration);
	}
	
	@Override
	public Duration get(Class<? extends Duration> type, ResultSet rs, int index) throws SQLException {
		long duration = rs.getLong(index);
		return Duration.seconds(duration);
	}
	
	@Override
	public Duration get(Class<? extends Duration> type, ResultSet rs, String columnName) throws SQLException {
		long duration = rs.getLong(columnName);
		return Duration.seconds(duration);
	}
	
	@Override
	public Class<?> getJavaType(int sqlType) {
		return long.class;
	}
	
	@Override
	public boolean isSupport(Class<?> type) {
		return Duration.class.isAssignableFrom(type);
	}
	
	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, int index) throws SQLException {
		cs.registerOutParameter(index, Types.BIGINT);
	}
	
	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, String parameterName) throws SQLException {
		cs.registerOutParameter(parameterName, Types.BIGINT);
	}
	
	@Override
	public void set(Class<? extends Duration> type, PreparedStatement stmt, Duration value, int index)
			throws SQLException {
		if (value == null) {
			stmt.setNull(index, Types.BIGINT);
		} else {
			long sec = value.to(TimeUnit.second);
			stmt.setLong(index, sec);
		}
	}
}
