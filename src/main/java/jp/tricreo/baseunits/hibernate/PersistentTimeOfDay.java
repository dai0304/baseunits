/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2011/09/26
 * 
 * All rights reserved.
 */
package jp.tricreo.baseunits.hibernate;

import java.sql.Types;

import jp.tricreo.baseunits.time.TimeOfDay;

import org.hibernate.type.StandardBasicTypes;

/**
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class PersistentTimeOfDay extends AbstractBaseunitsType<TimeOfDay, Integer> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.INTEGER
	};
	
	
	/**
	 * インスタンスを生成する。
	 */
	public PersistentTimeOfDay() {
		super(StandardBasicTypes.INTEGER);
	}
	
	@Override
	public Class<TimeOfDay> returnedClass() {
		return TimeOfDay.class;
	}
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	protected TimeOfDay fromNonNullInternalType(Integer value) {
		return TimeOfDay.from(value / 60, value % 60); // CHECKSTYLE IGNORE THIS LINE
	}
	
	@Override
	protected Integer toNonNullInternalType(TimeOfDay value) {
		int h = value.breachEncapsulationOfHour().breachEncapsulationOfValue();
		int m = value.breachEncapsulationOfMinute().breachEncapsulationOfValue();
		return (h * 60) + m; // CHECKSTYLE IGNORE THIS LINE
	}
}
