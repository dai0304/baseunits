/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.Iterator;

/**
 * 日付の仕様を表現するオブジェクト。DDDのSpecificationパターンオブジェクト。
 * 
 * @author daisuke
 */
public abstract class DateSpecification {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param month
	 * @param day
	 * @return
	 */
	public static DateSpecification fixed(int month, int day) {
		return new FixedDateSpecification(month, day);
	}
	
	/**
	 * @param month
	 * @param dayOfWeek
	 * @param n
	 * @return
	 * @throws IllegalArgumentException 引数dayOfWeekに{@code null}を与えた場合
	 */
	public static DateSpecification nthOccuranceOfWeekdayInMonth(int month, DayOfWeek dayOfWeek, int n) {
		return new FloatingDateSpecification(month, dayOfWeek, n);
	}
	
	/**
	 * 与えた日付が、この日付仕様を満たすかどうか検証する。
	 * 
	 * @param date 検証対象の日付
	 * @return 仕様を満たす場合は{@code true}、そうでない場合は{@code false}
	 */
	public abstract boolean isSatisfiedBy(CalendarDate date);
	
	public abstract CalendarDate firstOccurrenceIn(CalendarInterval interval);
	
	public abstract Iterator<CalendarDate> iterateOver(CalendarInterval interval);
	
}
