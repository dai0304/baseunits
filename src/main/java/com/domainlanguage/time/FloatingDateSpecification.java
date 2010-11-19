/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import org.apache.commons.lang.Validate;

/**
 * X月の第Y◎曜日、を表す日付仕様。
 * 
 * @author daisuke
 */
class FloatingDateSpecification extends AnnualDateSpecification {
	
	private int month;
	
	private DayOfWeek dayOfWeek;
	
	private int occurrence;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	FloatingDateSpecification() {
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param dayOfWeek 曜日
	 * @param occurrence 周回数（1〜5）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code dayOfWeek}に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数{@code occurrence}が1〜5の範囲ではない場合
	 */
	FloatingDateSpecification(int month, DayOfWeek dayOfWeek, int occurrence) {
		Validate.isTrue(1 <= month && month <= 12);
		Validate.notNull(dayOfWeek);
		Validate.isTrue(1 <= occurrence && occurrence <= 5);
		this.month = month;
		this.dayOfWeek = dayOfWeek;
		this.occurrence = occurrence;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		return ofYear(date.breachEncapsulationOfYear()).equals(date);
	}
	
	@Override
	public CalendarDate ofYear(int year) {
		CalendarDate firstOfMonth = CalendarDate.date(year, month, 1);
		int dayOfWeekOffset = dayOfWeek.value - firstOfMonth.dayOfWeek().value;
		int dateOfFirstOccurrenceOfDayOfWeek = dayOfWeekOffset + (dayOfWeekOffset < 0 ? 8 : 1);
		int date = ((occurrence - 1) * 7) + dateOfFirstOccurrenceOfDayOfWeek;
		return CalendarDate.date(year, month, date);
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #dayOfWeek}
	 */
	@SuppressWarnings("unused")
	private DayOfWeek getForPersistentMapping_DayOfWeek() { // CHECKSTYLE IGNORE THIS LINE
		return dayOfWeek;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #month}
	 */
	@SuppressWarnings("unused")
	private int getForPersistentMapping_Month() { // CHECKSTYLE IGNORE THIS LINE
		return month;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #occurrence}
	 */
	@SuppressWarnings("unused")
	private int getForPersistentMapping_Occurrence() { // CHECKSTYLE IGNORE THIS LINE
		return occurrence;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param dayOfWeek {@link #dayOfWeek}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_DayOfWeek(DayOfWeek dayOfWeek) { // CHECKSTYLE IGNORE THIS LINE
		this.dayOfWeek = dayOfWeek;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param month {@link #month}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Month(int month) { // CHECKSTYLE IGNORE THIS LINE
		this.month = month;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param occurrence {@link #occurrence}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Occurrence(int occurrence) { // CHECKSTYLE IGNORE THIS LINE
		this.occurrence = occurrence;
	}
}
