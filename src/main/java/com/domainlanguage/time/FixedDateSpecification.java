/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import org.apache.commons.lang.Validate;

/**
 * X月Y日、を表す日付仕様。
 * 
 * @author daisuke
 */
class FixedDateSpecification extends AnnualDateSpecification {
	
	private int month;
	
	private int day;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	FixedDateSpecification() {
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 */
	FixedDateSpecification(int month, int day) {
		Validate.isTrue(1 <= month && month <= 12);
		Validate.isTrue(1 <= day && day <= 31); // CHECKSTYLE IGNORE THIS LINE
		this.month = month;
		this.day = day;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		return day == date.breachEncapsulationOfDay() && month == date.breachEncapsulationOfMonth();
	}
	
	@Override
	public CalendarDate ofYear(int year) {
		return CalendarDate.date(year, month, day);
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #day}
	 */
	@SuppressWarnings("unused")
	private int getForPersistentMapping_Day() { // CHECKSTYLE IGNORE THIS LINE
		return day;
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
	 * @param day {@link #day}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Day(int day) { // CHECKSTYLE IGNORE THIS LINE
		this.day = day;
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
	
}
