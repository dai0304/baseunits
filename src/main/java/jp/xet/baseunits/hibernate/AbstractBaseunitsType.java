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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.base.Preconditions;

import org.hibernate.HibernateException;
import org.hibernate.type.SingleColumnType;
import org.hibernate.usertype.UserType;

/**
 * Baseunitsの型をHibernate用ユーザ定義型として利用するための定義の抽象骨格実装クラス。
 * 
 * @param <E> External Type（Baseunitsの型）
 * @param <I> Internal Type（DBに保存できる型）
 * @author daisuke
 * @since 1.2
 */
@SuppressWarnings("serial")
public abstract class AbstractBaseunitsType<E, I> implements UserType, Serializable {
	
	private final SingleColumnType<I> sct;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param sct {@link SingleColumnType}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.2
	 */
	public AbstractBaseunitsType(SingleColumnType<I> sct) {
		Preconditions.checkNotNull(sct);
		this.sct = sct;
	}
	
	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}
	
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}
	
	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}
	
	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		return x.equals(y);
	}
	
	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		I s = sct.nullSafeGet(rs, names[0]);
		if (s == null) {
			return null;
		}
		
		return fromNonNullInternalType(s);
	}
	
	@Override
	@SuppressWarnings({
		"deprecation",
		"unchecked"
	})
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			sct.nullSafeSet(st, null, index);
		} else {
			sct.nullSafeSet(st, toNonNullInternalType((E) value), index);
		}
	}
	
	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
	
	@Override
	public abstract Class<E> returnedClass();
	
	/**
	 * baseunits型をDB型に変換する。
	 * 
	 * @param value baseunits型
	 * @return DB型
	 * @since 1.2
	 */
	protected abstract E fromNonNullInternalType(I value);
	
	/**
	 * DB型をbaseunits型に変換する。
	 * 
	 * @param value DB型
	 * @return baseunits型
	 * @since 1.2
	 */
	protected abstract I toNonNullInternalType(E value);
}
