/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2011/09/26
 * 
 * All rights reserved.
 */
package jp.tricreo.baseunits.hibernate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Types;

import jp.tricreo.baseunits.time.Duration;
import jp.tricreo.baseunits.util.Ratio;

import org.hibernate.type.StandardBasicTypes;

/**
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class PersistentMinuteDuration extends AbstractBaseunitsType<Duration, Integer> {
	
	private static final int[] SQL_TYPES = new int[] {
		Types.INTEGER
	};
	
	
	/**
	 * インスタンスを生成する。
	 */
	public PersistentMinuteDuration() {
		super(StandardBasicTypes.INTEGER);
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
	protected Duration fromNonNullInternalType(Integer s) {
		return Duration.minutes(s);
	}
	
	@Override
	protected Integer toNonNullInternalType(Duration value) {
		Ratio dividedBy = value.dividedBy(Duration.minutes(1));
		BigDecimal decimalValue = dividedBy.decimalValue(0, RoundingMode.DOWN);
		return decimalValue.intValue();
	}
}
