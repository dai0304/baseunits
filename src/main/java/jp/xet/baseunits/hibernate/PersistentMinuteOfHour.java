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

import jp.xet.baseunits.time.MinuteOfHour;

import org.hibernate.type.StandardBasicTypes;

/**
 * {@link MinuteOfHour}を0〜59の整数としてDBにデータを保存するHibernateユーザ型。
 * 
 * @author daisuke
 * @since 1.2
 */
@SuppressWarnings("serial")
public class PersistentMinuteOfHour extends AbstractBaseunitsType<MinuteOfHour, Integer> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.INTEGER
	};
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.2
	 */
	public PersistentMinuteOfHour() {
		super(StandardBasicTypes.INTEGER);
	}
	
	@Override
	public Class<MinuteOfHour> returnedClass() {
		return MinuteOfHour.class;
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected MinuteOfHour fromNonNullInternalType(Integer value) {
		return MinuteOfHour.valueOf(value);
	}
	
	@Override
	protected Integer toNonNullInternalType(MinuteOfHour value) {
		return value.breachEncapsulationOfValue();
	}
}
