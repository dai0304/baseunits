/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.util;

import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;

/**
 * {@link ImmutableIterator}のテストクラス。
 */
public class ImmutableIteratorTest {
	
	/**
	 * {@link ImmutableIterator}に対して {@link Iterator#remove()}ができないことを確認する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Remove() throws Exception {
		Iterator<Void> iterator = new ImmutableIterator<Void>() {
			
			@Override
			public boolean hasNext() {
				return true;
			}
			
			@Override
			public Void next() {
				return null;
			}
		};
		try {
			iterator.remove();
			fail("remove is unsupported");
		} catch (UnsupportedOperationException expected) {
			// success
		}
	}
	
}
