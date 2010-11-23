/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.time;

import java.util.Iterator;

/**
 * 日付の仕様を表現するオブジェクト。DDDのSpecificationパターンオブジェクト。
 * 
 * @author daisuke
 */
public abstract class DateSpecification {
	
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
		return new FixedDateSpecification(month, day);
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
		return new FloatingDateSpecification(month, dayOfWeek, n);
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
	 */
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
