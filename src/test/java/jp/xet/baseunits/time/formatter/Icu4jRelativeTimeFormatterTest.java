/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/19
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
package jp.xet.baseunits.time.formatter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;

import org.junit.Test;

/**
 * TODO for daisuke
 */
@SuppressWarnings("javadoc")
public class Icu4jRelativeTimeFormatterTest {
	
	Icu4jRelativeTimePointFormatter f = new Icu4jRelativeTimePointFormatter();
	
	
	@Test
	public void test_basic() {
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 19, 8, 56, TimeZone.getTimeZone("JST")),
				Locale.JAPAN), is("今から1ミリ秒以内"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 19, 8, 56, TimeZone.getTimeZone("JST")),
				Locale.US), is("less than 1 millisecond from now"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 19, 7, 56, TimeZone.getTimeZone("JST")),
				Locale.JAPAN), is("今から1時間後"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 7, 56, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 19, 8, 56, TimeZone.getTimeZone("JST")),
				Locale.JAPAN), is("1時間前"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 19, 7, 50, TimeZone.getTimeZone("JST")),
				Locale.JAPAN), is("今から1時間後"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 18, 7, 50, TimeZone.getTimeZone("JST")),
				Locale.JAPAN), is("今から1日後"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 7, 49, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 18, 7, 50, TimeZone.getTimeZone("JST")),
				Locale.JAPAN), is("今から23時間後"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 18, 7, 50, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 19, 7, 49, TimeZone.getTimeZone("JST")),
				Locale.JAPAN), is("23時間前"));
	}
	
	@Test
	public void test_unknownlocale() {
		assertThat(f.format(
				TimePoint.at(2011, 11, 18, 7, 50, TimeZone.getTimeZone("JST")),
				TimePoint.at(2011, 11, 19, 7, 49, TimeZone.getTimeZone("JST")),
				new Locale("asdf")), is("23 hours ago"));
	}
}
