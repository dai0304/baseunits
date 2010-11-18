/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.util;

import java.util.Iterator;

import junit.framework.TestCase;

public class ImmutableIteratorTest extends TestCase {
	
	public void testRemove() {
		Iterator iterator = new ImmutableIterator() {
			
			@Override
			public boolean hasNext() {
				return true;
			}
			
			@Override
			public Object next() {
				return null;
			}
		};
		try {
			iterator.remove();
			fail("remove is unsupported");
		} catch (UnsupportedOperationException expected) {
		}
	}
	
}
