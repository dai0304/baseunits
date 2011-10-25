/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
package jp.xet.baseunits.time.spec;

import java.util.Collections;
import java.util.Iterator;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.time.DayOfMonth;
import jp.xet.baseunits.time.DayOfWeek;
import jp.xet.baseunits.time.MonthOfYear;

import org.apache.commons.lang.Validate;

/**
 * 日付の仕様を表現するオブジェクト。
 * 
 * @since 2.0
 */
public final class DateSpecifications {
	
	/**
	 * 特定のある1日だけにマッチする日付仕様のインスタンスを返す。
	 * 
	 * <p>毎月31日を指定した場合、該当月に31日が存在しなければ、その月にはヒットしない。</p>
	 * 
	 * @param date マッチする日
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static DateSpecification fixed(CalendarDate date) {
		return new FixedDateSpecification(date);
	}
	
	/**
	 * 日付仕様「毎月{@code day}日」のインスタンスを生成する。
	 * 
	 * <p>毎月31日を指定した場合、該当月に31日が存在しなければ、その月にはヒットしない。</p>
	 * 
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @return 日付仕様
	 * @since 1.0
	 */
	public static DateSpecification fixed(int day) {
		return new MonthlyFixedDateSpecification(DayOfMonth.valueOf(day));
	}
	
	/**
	 * 日付仕様「毎年{@code month}月{@code day}日」のインスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @return 日付仕様
	 * @since 1.0
	 */
	public static DateSpecification fixed(int month, int day) {
		return new AnnualFixedDateSpecification(MonthOfYear.valueOf(month), DayOfMonth.valueOf(day));
	}
	
	/**
	 * どの日付にもマッチしない日付仕様を返す。
	 * 
	 * @return 日付仕様
	 * @since 1.0
	 */
	public static DateSpecification never() {
		return new AbstractDateSpecivifation() {
			
			@Override
			public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
				Validate.notNull(interval);
				return null;
			}
			
			@Override
			public boolean isSatisfiedBy(CalendarDate date) {
				Validate.notNull(date);
				return false;
			}
			
			@Override
			@SuppressWarnings("unchecked")
			public Iterator<CalendarDate> iterateOver(CalendarInterval interval) {
				return Collections.EMPTY_LIST.iterator();
			}
			
		};
	}
	
	/**
	 * 日付仕様「毎月第{@code n}◎曜日仕様」のインスタンスを生成する。
	 * 
	 * @param dayOfWeek 曜日◎
	 * @param n 周回数（1〜5）
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数dayOfWeekに{@code null}を与えた場合
	 * @since 1.0
	 */
	public static DateSpecification nthOccuranceOfWeekdayInEveryMonth(DayOfWeek dayOfWeek, int n) {
		return new MonthlyFloatingDateSpecification(dayOfWeek, n);
	}
	
	/**
	 * 日付仕様「{@code month}月の第{@code n}◎曜日仕様のインスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param dayOfWeek 曜日◎
	 * @param n 周回数（1〜5）
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数dayOfWeekに{@code null}を与えた場合
	 * @since 1.0
	 */
	public static DateSpecification nthOccuranceOfWeekdayInMonth(int month, DayOfWeek dayOfWeek, int n) {
		return new AnnualFloatingDateSpecification(month, dayOfWeek, n);
	}
	
	private DateSpecifications() {
	}
}
