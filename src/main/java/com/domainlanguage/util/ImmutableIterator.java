/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.util;

import java.util.Iterator;

/**
 * 明示的に、対象のコレクションに対する操作ができないことを表す反復子。
 * 
 * @param <T> 要素の型
 * @author daisuke
 */
public abstract class ImmutableIterator<T> implements Iterator<T> {
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("sorry, no can do :-(");
	}
	
}
