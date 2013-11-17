/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ----
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.util;

import static org.junit.Assert.fail;

import java.util.Iterator;

import jp.xet.baseunits.util.ImmutableIterator;

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
