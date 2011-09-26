/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2011/09/26
 * 
 * All rights reserved.
 */
package jp.tricreo.baseunits.hibernate;

import java.sql.Types;
import java.util.Date;
import java.util.TimeZone;

import jp.tricreo.baseunits.time.CalendarDate;
import jp.tricreo.baseunits.time.TimePoint;

import org.hibernate.type.StandardBasicTypes;

/**
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class PersistentCalendarDate extends AbstractBaseunitsType<CalendarDate, Date> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.INTEGER
	};
	
	
	/**
	 * インスタンスを生成する。
	 */
	public PersistentCalendarDate() {
		super(StandardBasicTypes.DATE);
	}
	
	@Override
	public Class<CalendarDate> returnedClass() {
		return CalendarDate.class;
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected CalendarDate fromNonNullInternalType(Date value) {
		return CalendarDate.from(TimePoint.from(value), TimeZone.getDefault());
	}
	
	@Override
	protected Date toNonNullInternalType(CalendarDate value) {
		return value.startAsTimePoint(TimeZone.getDefault()).asJavaUtilDate();
	}
}
