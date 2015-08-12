/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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
package jp.xet.baseunits.hibernate;

import java.sql.Types;

import org.hibernate.type.StandardBasicTypes;

/**
 * 文字列型としてDBにデータを保存するHibernateユーザ型。
 * 
 * @param <T> User defined type
 * @author daisuke
 * @since 1.2
 */
@SuppressWarnings("serial")
public abstract class AbstractStringBasedBaseunitsType<T> extends AbstractBaseunitsType<T, String> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.VARCHAR
	};
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.2
	 */
	public AbstractStringBasedBaseunitsType() {
		super(StandardBasicTypes.STRING);
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected abstract T fromNonNullInternalType(String s);
	
	@Override
	protected abstract String toNonNullInternalType(T value);
}
