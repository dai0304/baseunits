/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
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
package jp.tricreo.baseunits.time;

import java.util.Collections;
import java.util.Iterator;

import jp.tricreo.baseunits.util.spec.AbstractSpecification;

import org.apache.commons.lang.Validate;

/**
 * 日付の仕様を表現するオブジェクト。
 */
public abstract class DateSpecification extends AbstractSpecification<CalendarDate> {
	
	/**
	 * 特定のある1日だけにマッチする日付仕様のインスタンスを返す。
	 * 
	 * <p>毎月31日を指定した場合、該当月に31日が存在しなければ、その月にはヒットしない。</p>
	 * 
	 * @param date マッチする日
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
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
	 */
	public static DateSpecification fixed(int day) {
		return new MonthlyFixedDateSpecification(DayOfMonth.valueOf(day));
	}
	
	/**
	 * 日付仕様のインスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @return 日付仕様
	 */
	public static DateSpecification fixed(int month, int day) {
		return new AnnualFixedDateSpecification(MonthOfYear.valueOf(month), DayOfMonth.valueOf(day));
	}
	
	/**
	 * どの日付にもマッチしない日付仕様を返す。
	 * 
	 * @return 日付仕様
	 */
	public static DateSpecification never() {
		return new DateSpecification() {
			
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
	 * 毎月第Y◎曜日仕様のインスタンスを生成する。
	 * 
	 * @param dayOfWeek 曜日
	 * @param n 周回数（1〜5）
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数dayOfWeekに{@code null}を与えた場合
	 */
	public static DateSpecification nthOccuranceOfWeekdayInEveryMonth(DayOfWeek dayOfWeek, int n) {
		return new MonthlyFloatingDateSpecification(dayOfWeek, n);
	}
	
	/**
	 * X月の第Y◎曜日仕様のインスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param dayOfWeek 曜日
	 * @param n 周回数（1〜5）
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数dayOfWeekに{@code null}を与えた場合
	 */
	public static DateSpecification nthOccuranceOfWeekdayInMonth(int month, DayOfWeek dayOfWeek, int n) {
		return new AnnualFloatingDateSpecification(month, dayOfWeek, n);
	}
	
	/**
	 * 指定した期間の中で、この日付仕様を満たす最初の年月日を返す。
	 * 
	 * @param interval 期間
	 * @return 年月日。但し、仕様を満たす日がなかった場合は{@code null}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public abstract CalendarDate firstOccurrenceIn(CalendarInterval interval);
	
	/**
	 * 与えた日付が、この日付仕様を満たすかどうか検証する。
	 * 
	 * @param date 検証対象の日付
	 * @return 仕様を満たす場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	@Override
	public abstract boolean isSatisfiedBy(CalendarDate date);
	
	/**
	 * 指定した期間の中で、この日付仕様を満たす年月日を順次取得する反復子を返す。
	 * 
	 * @param interval 期間
	 * @return 反復子
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public abstract Iterator<CalendarDate> iterateOver(CalendarInterval interval);
	
}
