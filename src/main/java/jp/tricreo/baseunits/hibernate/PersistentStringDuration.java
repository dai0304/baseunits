/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2011/09/26
 * 
 * All rights reserved.
 */
package jp.tricreo.baseunits.hibernate;

import jp.tricreo.baseunits.time.Duration;
import jp.tricreo.baseunits.time.TimeUnit;

/**
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class PersistentStringDuration extends AbstractStringBasedBaseunitsType<Duration> {
	
	@Override
	public Class<Duration> returnedClass() {
		return Duration.class;
	}
	
	@Override
	protected Duration fromNonNullInternalType(String s) {
		String[] split = s.split(" ", 2);
		return new Duration(Long.parseLong(split[0]), TimeUnit.valueOf(split[1]));
	}
	
	@Override
	protected String toNonNullInternalType(Duration value) {
		return String.format("%s %s", value.breachEncapsulationOfQuantity(), value.breachEncapsulationOfUnit());
	}
	
}
