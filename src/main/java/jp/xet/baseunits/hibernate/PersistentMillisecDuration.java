/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
package jp.xet.baseunits.hibernate;

import java.math.BigInteger;
import java.sql.Types;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import org.hibernate.type.StandardBasicTypes;

/**
 * {@link Duration}をミリ秒単位の整数としてDBにデータを保存するHibernateユーザ型。
 * 
 * @author daisuke
 * @since 1.2
 */
@SuppressWarnings("serial")
public class PersistentMillisecDuration extends AbstractBaseunitsType<Duration, BigInteger> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.BIGINT
	};
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.2
	 */
	public PersistentMillisecDuration() {
		super(StandardBasicTypes.BIG_INTEGER);
	}
	
	@Override
	public Class<Duration> returnedClass() {
		return Duration.class;
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected Duration fromNonNullInternalType(BigInteger s) {
		return Duration.milliseconds(s.longValue());
	}
	
	@Override
	protected BigInteger toNonNullInternalType(Duration value) {
		return BigInteger.valueOf(value.to(TimeUnit.millisecond));
	}
}
