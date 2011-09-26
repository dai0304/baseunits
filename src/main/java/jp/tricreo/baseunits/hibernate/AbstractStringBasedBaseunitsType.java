/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2011/09/26
 * 
 * All rights reserved.
 */
package jp.tricreo.baseunits.hibernate;

import java.sql.Types;

import org.hibernate.type.StandardBasicTypes;

/**
 * TODO for daisuke
 * 
 * @param <T> User defined type
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class AbstractStringBasedBaseunitsType<T> extends AbstractBaseunitsType<T, String> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.VARCHAR
	};
	
	
	/**
	 * インスタンスを生成する。
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
