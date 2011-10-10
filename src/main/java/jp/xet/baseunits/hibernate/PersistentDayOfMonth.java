/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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

import java.sql.Types;

import jp.xet.baseunits.time.DayOfMonth;

import org.hibernate.type.StandardBasicTypes;

/**
 * {@link DayOfMonth}を1〜31の整数としてDBにデータを保存するHibernateユーザ型。
 * 
 * @version $Id$
 * @author daisuke
 * @since 1.2
 */
@SuppressWarnings("serial")
public class PersistentDayOfMonth extends AbstractBaseunitsType<DayOfMonth, Integer> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.INTEGER
	};
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.2
	 */
	public PersistentDayOfMonth() {
		super(StandardBasicTypes.INTEGER);
	}
	
	@Override
	public Class<DayOfMonth> returnedClass() {
		return DayOfMonth.class;
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected DayOfMonth fromNonNullInternalType(Integer value) {
		return DayOfMonth.valueOf(value);
	}
	
	@Override
	protected Integer toNonNullInternalType(DayOfMonth value) {
		return value.breachEncapsulationOfValue();
	}
}
