/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2011/09/26
 * 
 * All rights reserved.
 */
package jp.tricreo.baseunits.hibernate;

import java.sql.Types;
import java.util.Date;

import jp.tricreo.baseunits.time.TimePoint;

import org.hibernate.type.StandardBasicTypes;

/**
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class PersistentTimePoint extends AbstractBaseunitsType<TimePoint, Date> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.INTEGER
	};
	
	
	/**
	 * インスタンスを生成する。
	 */
	public PersistentTimePoint() {
		super(StandardBasicTypes.DATE);
	}
	
	@Override
	public Class<TimePoint> returnedClass() {
		return TimePoint.class;
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected TimePoint fromNonNullInternalType(Date value) {
		return TimePoint.from(value);
	}
	
	@Override
	protected Date toNonNullInternalType(TimePoint value) {
		return value.asJavaUtilDate();
	}
}
