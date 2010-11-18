/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

/**
 * 現在時刻を返す責務を表すインターフェイス。
 * 
 * @version $Id$
 * @author daisuke
 */
public interface TimeSource {
	
	/**
	 * 現在時刻を返す。
	 * 
	 * @return 現在時刻
	 */
	TimePoint now();
	
}
