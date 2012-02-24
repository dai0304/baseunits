/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2012/02/24
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
 */
package jp.xet.baseunits.time.spec;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;

import org.junit.Test;

/**
 * {@link FixedDateSpecification}のテストクラス。
 */
public class FixedDateSpecificationTest {
	
	/**
	 * {@link FixedDateSpecification#firstOccurrenceIn(CalendarInterval)}
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_basic() throws Exception {
		FixedDateSpecification spec = new FixedDateSpecification(CalendarDate.from(1978, 3, 4));
		
		assertThat(spec.getDate(), is(CalendarDate.from(1978, 3, 4)));
		assertThat(spec.isSatisfiedBy(CalendarDate.EPOCH_DATE), is(false));
		assertThat(spec.isSatisfiedBy(CalendarDate.from(1978, 3, 4)), is(true));
		assertThat(spec, hasToString(CalendarDate.from(1978, 3, 4).toString()));
		
		assertThat(spec.firstOccurrenceIn(CalendarInterval.year(2001)), is(nullValue()));
		assertThat(spec.firstOccurrenceIn(CalendarInterval.year(1970)), is(nullValue()));
		assertThat(spec.firstOccurrenceIn(CalendarInterval.year(1978)), is(CalendarDate.from(1978, 3, 4)));
		
		Iterator<CalendarDate> itr1 = spec.iterateOver(CalendarInterval.everPreceding(CalendarDate.EPOCH_DATE));
		assertThat(itr1.hasNext(), is(false));
		try {
			itr1.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		Iterator<CalendarDate> itr2 = spec.iterateOver(CalendarInterval.everFrom(CalendarDate.EPOCH_DATE));
		assertThat(itr2.hasNext(), is(true));
		assertThat(itr2.next(), is(CalendarDate.from(1978, 3, 4)));
		assertThat(itr2.hasNext(), is(false));
		try {
			itr2.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
	}
}
