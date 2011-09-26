/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2011/09/26
 * 
 * All rights reserved.
 */
package jp.tricreo.baseunits.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.type.SingleColumnType;
import org.hibernate.usertype.UserType;

/**
 * TODO for daisuke
 * 
 * @param <E> External Type
 * @param <I> Internal Type
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class AbstractBaseunitsType<E, I> implements UserType, Serializable {
	
	private final SingleColumnType<I> sct;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param sct 
	 */
	public AbstractBaseunitsType(SingleColumnType<I> sct) {
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
	@SuppressWarnings("deprecation")
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
	
	protected abstract E fromNonNullInternalType(I value);
	
	protected abstract I toNonNullInternalType(E value);
	
}
