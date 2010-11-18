/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.io.Serializable;
import java.util.Calendar;

import com.domainlanguage.base.Ratio;

/**
 * 時間量を表す値クラス。
 * 
 * @since TODO for daisuke
 * @version $Id$
 * @author daisuke
 */
public class Duration implements Comparable<Duration>, Serializable {
	
	private static final long serialVersionUID = -9033637462829975657L;
	
	/** ゼロ時間量 */
	public static final Duration NONE = milliseconds(0);
	

	/**
	 * {@code howMany}日間を表す時間量を返す。
	 * 
	 * @param howMany 日量
	 * @return 時間量
	 */
	public static Duration days(int howMany) {
		return Duration.of(howMany, TimeUnit.DAY);
	}
	
	/**
	 * 引数で表す時間量を返す。
	 * 
	 * @param days 日量
	 * @param hours 時量
	 * @param minutes 分量
	 * @param seconds 秒量
	 * @param milliseconds ミリ秒量
	 * @return 時間量
	 */
	public static Duration daysHoursMinutesSecondsMilliseconds(int days, int hours, int minutes, int seconds,
			long milliseconds) {
		Duration result = Duration.days(days);
		if (hours != 0) {
			result = result.plus(Duration.hours(hours));
		}
		if (minutes != 0) {
			result = result.plus(Duration.minutes(minutes));
		}
		if (seconds != 0) {
			result = result.plus(Duration.seconds(seconds));
		}
		if (milliseconds != 0) {
			result = result.plus(Duration.milliseconds(milliseconds));
		}
		return result;
	}
	
	/**
	 * {@code howMany}時間を表す時間量を返す。
	 * 
	 * @param howMany 時量
	 * @return 時間量
	 */
	public static Duration hours(int howMany) {
		return Duration.of(howMany, TimeUnit.HOUR);
	}
	
	/**
	 * {@code howMany}ミリ秒間を表す時間量を返す。
	 * 
	 * @param howMany ミリ秒量
	 * @return 時間量
	 */
	public static Duration milliseconds(long howMany) {
		return Duration.of(howMany, TimeUnit.MILLISECOND);
	}
	
	/**
	 * {@code howMany}分間を表す時間量を返す。
	 * 
	 * @param howMany 分量
	 * @return 時間量
	 */
	public static Duration minutes(int howMany) {
		return Duration.of(howMany, TimeUnit.MINUTE);
	}
	
	/**
	 * {@code howMany}ヶ月間を表す時間量を返す。
	 * 
	 * @param howMany 月量
	 * @return 時間量
	 */
	public static Duration months(int howMany) {
		return Duration.of(howMany, TimeUnit.MONTH);
	}
	
	/**
	 * {@code howMany}四半期間を表す時間量を返す。
	 * 
	 * @param howMany 四半期量
	 * @return 時間量
	 */
	public static Duration quarters(int howMany) {
		return Duration.of(howMany, TimeUnit.QUARTER);
	}
	
	/**
	 * {@code howMany}秒間を表す時間量を返す。
	 * 
	 * @param howMany 秒量
	 * @return 時間量
	 */
	public static Duration seconds(int howMany) {
		return Duration.of(howMany, TimeUnit.SECOND);
	}
	
	/**
	 * {@code howMany}週間を表す時間量を返す。
	 * 
	 * @param howMany 週量
	 * @return 時間量
	 */
	public static Duration weeks(int howMany) {
		return Duration.of(howMany, TimeUnit.WEEK);
	}
	
	/**
	 * {@code howMany}年間を表す時間量を返す。
	 * 
	 * @param howMany 年量
	 * @return 時間量
	 */
	public static Duration years(int howMany) {
		return Duration.of(howMany, TimeUnit.YEAR);
	}
	
	private static Duration of(long howMany, TimeUnit unit) {
		return new Duration(howMany, unit);
	}
	

	private long quantity;
	
	private TimeUnit unit;
	

	public Duration(long quantity, TimeUnit unit) {
		assertQuantityPositiveOrZero(quantity);
		this.quantity = quantity;
		this.unit = unit;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	Duration() {
	}
	
	public CalendarDate addedTo(CalendarDate day) {
//		only valid for days and larger units
		if (unit.compareTo(TimeUnit.DAY) < 0) {
			return day;
		}
		Calendar calendar = day.asJavaCalendarUniversalZoneMidnight();
		if (unit.equals(TimeUnit.DAY)) {
			calendar.add(Calendar.DATE, (int) quantity);
		} else {
			addAmountToCalendar(inBaseUnits(), calendar);
		}
		return CalendarDate._from(calendar);
	}
	
	public TimePoint addedTo(TimePoint point) {
		return addAmountToTimePoint(inBaseUnits(), point);
	}
	
	@Override
	public int compareTo(Duration other) {
		assertConvertible(other);
		long difference = inBaseUnits() - other.inBaseUnits();
		if (difference > 0) {
			return 1;
		}
		if (difference < 0) {
			return -1;
		}
		return 0;
	}
	
	public Ratio dividedBy(Duration divisor) {
		assertConvertible(divisor);
		return Ratio.of(inBaseUnits(), divisor.inBaseUnits());
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Duration)) {
			return false;
		}
		Duration other = (Duration) object;
		if (!isConvertibleTo(other)) {
			return false;
		}
		return inBaseUnits() == other.inBaseUnits();
	}
	
	@Override
	public int hashCode() {
		return (int) quantity;
	}
	
	public Duration minus(Duration other) {
		assertConvertible(other);
		assertGreaterThanOrEqualTo(other);
		long newQuantity = inBaseUnits() - other.inBaseUnits();
		return new Duration(newQuantity, unit.baseUnit());
	}
	
	public TimeUnit normalizedUnit() {
		TimeUnit[] units = unit.descendingUnits();
		long baseAmount = inBaseUnits();
		for (TimeUnit aUnit : units) {
			long remainder = baseAmount % aUnit.getFactor();
			if (remainder == 0) {
				return aUnit;
			}
		}
		return null;
		
	}
	
	public Duration plus(Duration other) {
		assertConvertible(other);
		long newQuantity = inBaseUnits() + other.inBaseUnits();
		return new Duration(newQuantity, unit.baseUnit());
	}
	
	public TimeInterval preceding(TimePoint end) {
		return TimeInterval.preceding(end, this);
	}
	
	public CalendarInterval startingFrom(CalendarDate start) {
		return CalendarInterval.startingFrom(start, this);
	}
	
	public TimeInterval startingFrom(TimePoint start) {
		return TimeInterval.startingFrom(start, this);
	}
	
	public CalendarDate subtractedFrom(CalendarDate day) {
//		only valid for days and larger units
		if (unit.compareTo(TimeUnit.DAY) < 0) {
			return day;
		}
		Calendar calendar = day.asJavaCalendarUniversalZoneMidnight();
		if (unit.equals(TimeUnit.DAY)) {
			calendar.add(Calendar.DATE, -1 * (int) quantity);
		} else {
			subtractAmountFromCalendar(inBaseUnits(), calendar);
		}
		return CalendarDate._from(calendar);
	}
	
	public TimePoint subtractedFrom(TimePoint point) {
		return addAmountToTimePoint(-1 * inBaseUnits(), point);
	}
	
	public String toNormalizedString() {
		return toNormalizedString(unit.descendingUnits());
	}
	
	@Override
	public String toString() {
		return toNormalizedString(unit.descendingUnitsForDisplay());
	}
	
	void addAmountToCalendar(long amount, Calendar calendar) {
		if (unit.isConvertibleToMilliseconds()) {
			calendar.setTimeInMillis(calendar.getTimeInMillis() + amount);
		} else {
			assertAmountValid(amount);
			calendar.add(unit.javaCalendarConstantForBaseType(), (int) amount);
		}
	}
	
	TimePoint addAmountToTimePoint(long amount, TimePoint point) {
		if (unit.isConvertibleToMilliseconds()) {
			return TimePoint.from(amount + point.millisecondsFromEpoc);
		} else {
			Calendar calendar = point.asJavaCalendar();
			addAmountToCalendar(amount, calendar);
			return TimePoint.from(calendar);
		}
	}
	
	long inBaseUnits() {
		return quantity * unit.getFactor();
	}
	
	void subtractAmountFromCalendar(long amount, Calendar calendar) {
		addAmountToCalendar(-1 * amount, calendar);
	}
	
	private void assertAmountValid(long amount) {
		if (!(amount >= Integer.MIN_VALUE && amount <= Integer.MAX_VALUE)) {
			throw new IllegalArgumentException(amount + " is not valid");
		}
	}
	
	private void assertConvertible(Duration other) {
		if (!other.unit.isConvertibleTo(unit)) {
			throw new IllegalArgumentException(other.toString() + " is not convertible to: " + toString());
		}
	}
	
	private void assertGreaterThanOrEqualTo(Duration other) {
		if (compareTo(other) < 0) {
			throw new IllegalArgumentException(this + " is before " + other);
		}
	}
	
	private void assertQuantityPositiveOrZero(long quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity: " + quantity + " must be zero or positive");
		}
	}
	
	private boolean isConvertibleTo(Duration other) {
		return unit.isConvertibleTo(other.unit);
	}
	
	private String toNormalizedString(TimeUnit[] units) {
		StringBuffer buffer = new StringBuffer();
		long remainder = inBaseUnits();
		boolean first = true;
		for (TimeUnit aUnit : units) {
			long portion = remainder / aUnit.getFactor();
			if (portion > 0) {
				if (!first) {
					buffer.append(", ");
				} else {
					first = false;
				}
				buffer.append(aUnit.toString(portion));
			}
			remainder = remainder % aUnit.getFactor();
		}
		return buffer.toString();
	}
}
