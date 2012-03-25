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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;

import org.junit.Test;

/**
 * {@link OrDateSpecification}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class OrDateSpecificationTest {
	
	@Test
	public void test01_() {
		DateSpecification spec1 = DateSpecifications.calendarInterval(CalendarInterval.year(2011));
		DateSpecification spec2 = DateSpecifications.calendarInterval(CalendarInterval.year(2013));
		
		OrDateSpecification or = new OrDateSpecification(spec1, spec2);
		
		assertThat(or.getLeft(), is(spec1));
		assertThat(or.getRight(), is(spec2));
		
		assertThat(or.isSatisfiedBy(CalendarDate.EPOCH_DATE), is(false));
		assertThat(or.isSatisfiedBy(CalendarDate.from(2011, 3, 5)), is(true));
		assertThat(or.isSatisfiedBy(CalendarDate.from(2012, 3, 5)), is(false));
		assertThat(or.isSatisfiedBy(CalendarDate.from(2013, 3, 5)), is(true));
		
		CalendarDate firstOccurrence =
				or.firstOccurrenceIn(CalendarInterval.inclusive(CalendarDate.from(2012, 6, 1),
						CalendarDate.from(2013, 6, 1)));
		assertThat(firstOccurrence, is(CalendarDate.from(2013, 1, 1)));
	}
	
	@Test
	public void test02_() {
		DateSpecification spec1 =
				DateSpecifications.calendarInterval(CalendarInterval.everPreceding(CalendarDate.EPOCH_DATE));
		DateSpecification spec2 = DateSpecifications.calendarInterval(CalendarInterval.year(2013));
		
		OrDateSpecification or = new OrDateSpecification(spec1, spec2);
		
		assertThat(or.getLeft(), is(spec1));
		assertThat(or.getRight(), is(spec2));
		
		assertThat(or.isSatisfiedBy(CalendarDate.from(1860, 1, 1)), is(true));
		assertThat(or.isSatisfiedBy(CalendarDate.from(1960, 1, 1)), is(true));
		assertThat(or.isSatisfiedBy(CalendarDate.from(1980, 1, 1)), is(false));
		assertThat(or.isSatisfiedBy(CalendarDate.from(2011, 3, 5)), is(false));
		assertThat(or.isSatisfiedBy(CalendarDate.from(2012, 3, 5)), is(false));
		assertThat(or.isSatisfiedBy(CalendarDate.from(2013, 3, 5)), is(true));
		assertThat(or.isSatisfiedBy(CalendarDate.from(2014, 3, 5)), is(false));
		
//		try {
//			CalendarDate date = or.firstOccurrenceIn(CalendarInterval.everPreceding(CalendarDate.from(2012, 6, 1)));
//			fail(date.toString());
//		} catch (IllegalArgumentException e) {
//		}
	}
}
