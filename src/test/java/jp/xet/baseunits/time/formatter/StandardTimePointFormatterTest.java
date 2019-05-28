/*
 * Copyright 2010-2019 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.time.formatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;

import org.junit.Test;

/**
 * {@link StandardTimePointFormatter}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class StandardTimePointFormatterTest {
	
	@Test
	public void test01_basic() {
		StandardTimePointFormatter formatter = new StandardTimePointFormatter();
		String formatted = formatter.format(TimePoint.EPOCH, Locale.JAPAN, TimeZone.getTimeZone("Japan"));
		assertThat(formatted, is("1970/01/01"));
	}
}
