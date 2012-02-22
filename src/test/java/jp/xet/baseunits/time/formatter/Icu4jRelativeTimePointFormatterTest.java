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

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.formatter.RelativeTimePointFormatter.FallbackConfig;

import org.junit.Test;

/**
 * {@link Icu4jRelativeTimePointFormatter}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class Icu4jRelativeTimePointFormatterTest {
	
	private static final TimeZone JST = TimeZone.getTimeZone("JST");
	
	private static final FallbackConfig config = new FallbackConfig(
			Duration.seconds(30), new FixedTimePointFormatter("たった今"),
			Duration.days(1), new StandardTimePointFormatter("yyyy/MM/dd HH:mm:ss"));
	
	Icu4jRelativeTimePointFormatter f = new Icu4jRelativeTimePointFormatter();
	
	Icu4jRelativeTimePointFormatter f2 = new Icu4jRelativeTimePointFormatter(config, JST);
	
	
	@Test
	public void test_basic() {
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, JST),
				TimePoint.at(2011, 11, 19, 8, 56, JST),
				Locale.JAPAN), is("今から1ミリ秒以内"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, JST),
				TimePoint.at(2011, 11, 19, 8, 56, JST),
				Locale.US), is("less than 1 millisecond from now"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, JST),
				TimePoint.at(2011, 11, 19, 7, 56, JST),
				Locale.JAPAN), is("今から1時間後"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 7, 56, JST),
				TimePoint.at(2011, 11, 19, 8, 56, JST),
				Locale.JAPAN), is("1時間前"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, JST),
				TimePoint.at(2011, 11, 19, 7, 50, JST),
				Locale.JAPAN), is("今から1時間後"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 8, 56, JST),
				TimePoint.at(2011, 11, 18, 7, 50, JST),
				Locale.JAPAN), is("今から1日後"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 19, 7, 49, JST),
				TimePoint.at(2011, 11, 18, 7, 50, JST),
				Locale.JAPAN), is("今から23時間後"));
		assertThat(f.format(
				TimePoint.at(2011, 11, 18, 7, 50, JST),
				TimePoint.at(2011, 11, 19, 7, 49, JST),
				Locale.JAPAN), is("23時間前"));
	}
	
	@Test
	public void test_fallback() {
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 21, 8, 56, 0, JST),
				Locale.JAPAN), is("2011/11/19 08:56:00"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 20, 8, 56, 0, JST),
				Locale.JAPAN), is("2011/11/19 08:56:00"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 20, 8, 55, 59, 999, JST),
				Locale.JAPAN), is("24時間前"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 9, 56, 0, JST),
				Locale.JAPAN), is("1時間前"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 31, JST),
				Locale.JAPAN), is("31秒前"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 30, JST),
				Locale.JAPAN), is("30秒前"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 29, JST),
				Locale.JAPAN), is("たった今"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 10, JST),
				Locale.JAPAN), is("たった今"));
		
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("たった今"));
		
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 10, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("たった今"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 29, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("たった今"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 30, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("今から30秒後"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 8, 56, 31, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("今から31秒後"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 9, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("今から1時間後"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 19, 9, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("今から1時間後"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 20, 8, 55, 59, 999, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("今から24時間後"));
		assertThat(f2.format(
				TimePoint.at(2011, 11, 20, 8, 56, 0, JST),
				TimePoint.at(2011, 11, 19, 8, 56, 0, JST),
				Locale.JAPAN), is("2011/11/20 08:56:00"));
	}
	
	@Test
	public void test_unknownlocale() {
		assertThat(f.format(
				TimePoint.at(2011, 11, 18, 7, 50, JST),
				TimePoint.at(2011, 11, 19, 7, 49, JST),
				new Locale("asdf")), is("23 hours ago"));
	}
}
