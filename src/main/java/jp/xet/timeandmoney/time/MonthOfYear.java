/*
 * Copyright 2010 Daisuke Miyamoto.
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
package jp.xet.timeandmoney.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 1年の中の特定の「月」を表す列挙型。
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
	 * {@link Calendar}に定義されている定数値から、インスタンスを取得する。
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
	 * {@link Calendar}に定義されている定数値から、インスタンスを取得する。
	 * 
	 * @param value 月数（1〜12）
	 * @return {@link MonthOfYear}. 見つからなかった場合は {@code null}
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
	private final DayOfMonth lastDay;
	
	private final int calendarValue;
	

	MonthOfYear(int month, DayOfMonth lastDay, int calendarValue) {
		value = month;
		this.lastDay = lastDay;
		this.calendarValue = calendarValue;
	}
	
	/**
	 * その月の最終日を取得する。
	 * 
	 * @param year 該当年. 2月の閏年判定に関わらない場合は、何でも良い。
	 * @return 最終日
	 */
	public DayOfMonth getLastDayOfThisMonth(int year) {
		return lastDay;
	}
	
	/**
	 * 指定した年の、この月を表す年月を返す。
	 * 
	 * @param year 年
	 * @return 年月
	 */
	public CalendarMonth in(int year) {
		return CalendarMonth.from(year, this);
	}
	
	/**
	 * 指定した日 {@code other} が、このオブジェクトが表現する日よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合と、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isAfter(MonthOfYear other) {
		if (other == null) {
			return false;
		}
		return isBefore(other) == false && equals(other) == false;
	}
	
	/**
	 * 指定した日 {@code other} が、このオブジェクトが表現する日よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合と、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isBefore(MonthOfYear other) {
		if (other == null) {
			return false;
		}
		return value < other.value;
	}
}
