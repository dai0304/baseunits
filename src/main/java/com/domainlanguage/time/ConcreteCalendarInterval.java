/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.TimeZone;

import com.domainlanguage.intervals.Interval;

@SuppressWarnings("serial")
class ConcreteCalendarInterval extends CalendarInterval {
	
	/**
	 * 開始日と終了日より、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param start 開始日
	 * @param end 終了日
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 */
	static ConcreteCalendarInterval from(CalendarDate start, CalendarDate end) {
		return new ConcreteCalendarInterval(start, end);
	}
	
	private static void assertStartIsBeforeEnd(CalendarDate start, CalendarDate end) {
		if (start != null && end != null && start.compareTo(end) > 0) {
			throw new IllegalArgumentException(start + " is not before or equal to " + end);
		}
	}
	

	/** 開始日（閉じた限界） */
	private CalendarDate start;
	
	/** 終了日（開いた限界） */
	private CalendarDate end;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	ConcreteCalendarInterval() {
	}
	
	ConcreteCalendarInterval(CalendarDate start, CalendarDate end) {
		assertStartIsBeforeEnd(start, end);
		this.start = start;
		this.end = end;
	}
	
	@Override
	public TimeInterval asTimeInterval(TimeZone zone) {
		TimePoint startPoint = start.asTimeInterval(zone).start();
		TimePoint endPoint = end.asTimeInterval(zone).end();
		return TimeInterval.over(startPoint, endPoint);
	}
	
	@Override
	public int compareTo(Interval<CalendarDate> other) {
		if (other == null) {
			throw new NullPointerException();
		}
		
		// 上限限界優先ロジック
		if (isEmpty() && other.isEmpty()) {
			assert equals(other);
			return 0;
		} else if (isEmpty()) {
			assert equals(other) == false;
			return -1;
		} else if (other.isEmpty()) {
			assert equals(other) == false;
			return 1;
		}
		
		int upperComparance = end.compareTo(other.upperLimit());
		int lowerComparance = start.compareTo(other.lowerLimit());
		return upperComparance != 0 ? upperComparance : (lowerComparance * -1);
		
		// 下限限界優先ロジック
//		if (isEmpty() && other.isEmpty()) {
//			assert equals(other);
//			return 0;
//		} else if (isEmpty()) {
//			assert equals(other) == false;
//			return 1;
//		} else if (other.isEmpty()) {
//			assert equals(other) == false;
//			return -1;
//		}
//		
//		int upperComparance = upperLimitObject.compareTo(other.upperLimitObject);
//		int lowerComparance = lowerLimitObject.compareTo(other.lowerLimitObject);
//		return lowerComparance != 0 ? lowerComparance : (upperComparance * -1);
	}
	
	@Override
	public CalendarDate lowerLimit() {
		return start;
	}
	
	@Override
	public CalendarDate upperLimit() {
		return end;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #end}
	 */
	@SuppressWarnings("unused")
	private CalendarDate getForPersistentMapping_End() { // CHECKSTYLE IGNORE THIS LINE
		return end;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #start}
	 */
	@SuppressWarnings("unused")
	private CalendarDate getForPersistentMapping_Start() { // CHECKSTYLE IGNORE THIS LINE
		return start;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param end {@link #end}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_End(CalendarDate end) { // CHECKSTYLE IGNORE THIS LINE
		this.end = end;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param start {@link #start}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Start(CalendarDate start) { // CHECKSTYLE IGNORE THIS LINE
		this.start = start;
	}
	
}
