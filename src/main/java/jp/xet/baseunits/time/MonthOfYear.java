/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
 * Created on 2010/11/24
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
package jp.xet.baseunits.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 1年の中の特定の「月」を表す列挙型。
 * 
 * @author daisuke
 * @since 1.0
 */
public enum MonthOfYear {
	
	/** January */
	JAN(1, DayOfMonth.valueOf(31), Calendar.JANUARY),
	
	/** Feburary */
	FEB(2, null, Calendar.FEBRUARY) {
		
		@Override
		public DayOfMonth getLastDayOfThisMonth(int year) {
			GregorianCalendar calendar = new GregorianCalendar(year, 2, 1);
			return calendar.isLeapYear(year) ? DayOfMonth.valueOf(29) : DayOfMonth.valueOf(28); // CHECKSTYLE IGNORE THIS LINE
		}
	},
	
	/** March */
	MAR(3, DayOfMonth.valueOf(31), Calendar.MARCH),
	
	/** April */
	APR(4, DayOfMonth.valueOf(30), Calendar.APRIL),
	
	/** May */
	MAY(5, DayOfMonth.valueOf(31), Calendar.MAY),
	
	/** June */
	JUN(6, DayOfMonth.valueOf(30), Calendar.JUNE),
	
	/** July */
	JUL(7, DayOfMonth.valueOf(31), Calendar.JULY),
	
	/** August */
	AUG(8, DayOfMonth.valueOf(31), Calendar.AUGUST),
	
	/** September */
	SEP(9, DayOfMonth.valueOf(30), Calendar.SEPTEMBER),
	
	/** October */
	OCT(10, DayOfMonth.valueOf(31), Calendar.OCTOBER),
	
	/** November */
	NOV(11, DayOfMonth.valueOf(30), Calendar.NOVEMBER),
	
	/** December */
	DEC(12, DayOfMonth.valueOf(31), Calendar.DECEMBER);
	
	/**
	 * {@link Calendar}に定義する月をあらわす定数値から、インスタンスを取得する。
	 * 
	 * @param value {@link Calendar}に定義されている定数値
	 * @return {@link MonthOfYear}. 見つからなかった場合は {@code null}
	 */
	public static MonthOfYear calendarValueOf(int value) {
		for (MonthOfYear monthOfYear : values()) {
			if (monthOfYear.calendarValue == value) {
				return monthOfYear;
			}
		}
		return null;
	}
	
	/**
	 * 月数から、インスタンスを取得する。
	 * 
	 * @param value 月数（1〜12）
	 * @return {@link MonthOfYear}. 見つからなかった場合は {@code null}
	 * @since 1.0
	 */
	public static MonthOfYear valueOf(int value) {
		for (MonthOfYear monthOfYear : values()) {
			if (monthOfYear.value == value) {
				return monthOfYear;
			}
		}
		return null;
	}
	
	
	/** 1 based: January = 1, February = 2, ... */
	final int value;
	
	/** その月の最終日 */
	final DayOfMonth lastDayOfThisMonth;
	
	/** {@link Calendar}に定義する月をあらわす定数値 */
	final int calendarValue;
	
	
	MonthOfYear(int month, DayOfMonth lastDayOfThisMonth, int calendarValue) {
		value = month;
		this.lastDayOfThisMonth = lastDayOfThisMonth;
		this.calendarValue = calendarValue;
	}
	
	/**
	 * このオブジェクトの{@link #calendarValue}フィールド（{@link Calendar}に定義する月をあらわす定数値）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return {@link Calendar}に定義する月をあらわす定数値（{@link Calendar#JANUARY}〜{@link Calendar#DECEMBER}）
	 * @since 1.0
	 */
	public int breachEncapsulationOfCalendarValue() {
		return calendarValue;
	}
	
	/**
	 * このオブジェクトの{@link #value}フィールド（月をあらわす数 1〜12）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 月をあらわす数（1〜12）
	 * @since 1.0
	 */
	public int breachEncapsulationOfValue() {
		return value;
	}
	
	/**
	 * その月の最終日を取得する。
	 * 
	 * @param year 該当年. 2月の閏年判定に関わらない場合は、何でも良い。
	 * @return 最終日
	 * @since 2.0
	 */
	public DayOfMonth getLastDayOfThisMonth(int year) {
		return lastDayOfThisMonth;
	}
	
	/**
	 * 指定した日 {@code other} が、このオブジェクトが表現する日よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合と、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isAfter(MonthOfYear other) {
		if (other == null) {
			return false;
		}
		return isBefore(other) == false && equals(other) == false;
	}
	
//	public DayOfYear at(DayOfMonth month) {
//		// ...
//	}
	
	/**
	 * 指定した日 {@code other} が、このオブジェクトが表現する日よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合と、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isBefore(MonthOfYear other) {
		if (other == null) {
			return false;
		}
		return value < other.value;
	}
	
	/**
	 * 指定した年の、この月を表す年月を返す。
	 * 
	 * @param year 年
	 * @return 年月
	 * @since 1.0
	 */
	public CalendarMonth on(int year) {
		return CalendarMonth.from(year, this);
	}
}
