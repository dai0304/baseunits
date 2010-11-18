/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.Calendar;

/**
 * 時間の単位を表す列挙型。
 * 
 * @author daisuke
 */
public enum TimeUnit {
	
	/** ミリ秒単位 */
	millisecond(Type.millisecond, Type.millisecond, TimeUnitConversionFactor.identical),

	/** 秒単位 */
	second(Type.second, Type.millisecond, TimeUnitConversionFactor.millisecondsPerSecond),

	/** 分単位 */
	minute(Type.minute, Type.millisecond, TimeUnitConversionFactor.millisecondsPerMinute),

	/** 時単位 */
	hour(Type.hour, Type.millisecond, TimeUnitConversionFactor.millisecondsPerHour),

	/** 日単位 */
	day(Type.day, Type.millisecond, TimeUnitConversionFactor.millisecondsPerDay),

	/** 週単位 */
	week(Type.week, Type.millisecond, TimeUnitConversionFactor.millisecondsPerWeek),

	/** 月単位 */
	month(Type.month, Type.month, TimeUnitConversionFactor.identical),

	/** 四半期単位 */
	quarter(Type.quarter, Type.month, TimeUnitConversionFactor.monthsPerQuarter),

	/** 年単位 */
	year(Type.year, Type.month, TimeUnitConversionFactor.monthsPerYear);
	
	private static final TimeUnit[] DESCENDING_MS_BASED = {
		week,
		day,
		hour,
		minute,
		second,
		millisecond
	};
	
	private static final TimeUnit[] DESCENDING_MS_BASED_FOR_DISPLAY = {
		day,
		hour,
		minute,
		second,
		millisecond
	};
	
	private static final TimeUnit[] DESCENDING_MONTH_BASED = {
		year,
		quarter,
		month
	};
	
	private static final TimeUnit[] DESCENDING_MONTH_BASED_FOR_DISPLAY = {
		year,
		month
	};
	

	static TimeUnit exampleForPersistentMappingTesting() {
		return second;
	}
	
	static Type exampleTypeForPersistentMappingTesting() {
		return Type.hour;
	}
	

	private final Type type;
	
	private final Type baseType;
	
	private final TimeUnitConversionFactor factor;
	

	TimeUnit(Type type, Type baseType, TimeUnitConversionFactor factor) {
		this.type = type;
		this.baseType = baseType;
		this.factor = factor;
	}
	
	/**
	 * この単位で表される値を、指定した単位に変換できるかどうかを検証する。
	 * 
	 * <p>例えば、分単位はミリ秒単位に変換できるが、四半期単位は（一ヶ月の長さが毎月異なるため）日単位に変換できない。</p>
	 * 
	 * @param other 変換先単位
	 * @return 変換できる場合は{@code true}、そうでない場合は{@code false}
	 */
	public boolean isConvertibleTo(TimeUnit other) {
		return baseType.equals(other.baseType);
	}
	
//    public int compareTo(TimeUnit other) {
//		if (other == null) {
//			return -1;
//		}
//        if (other.baseType.equals(baseType)) {
//            return factor.value - other.factor.value;
//        }
//        if (baseType.equals(Type.month)) {
//            return 1;
//        }
//        return -1;
//    }
	
	/**
	 * この単位で表される値を、ミリ秒単位に変換できるかどうかを検証する。
	 * 
	 * <p>例えば、分単位はミリ秒単位に変換できるが、四半期単位は（一ヶ月の長さが毎月異なるため）ミリ秒単位に変換できない。</p>
	 * 
	 * @return 変換できる場合は{@code true}、そうでない場合は{@code false}
	 */
	public boolean isConvertibleToMilliseconds() {
		return isConvertibleTo(millisecond);
	}
	
	@Override
	public String toString() {
		return type.name();
	}
	
	/**
	 * この単位の計数の基数とすることができる最小の単位を取得する。
	 * 
	 * <p>例えば、分単位はミリ秒単位で計数できるが、四半期単位は（一ヶ月の長さが毎月異なるため）月単位までしか計数できない。</p>
	 * 
	 * @return この単位の計数の基数とすることができる最小の単位
	 */
	TimeUnit baseUnit() {
		return baseType.equals(Type.millisecond) ? millisecond : month;
	}
	
	/**
	 * この単位から変換可能な全ての単位を含み、大きい単位から降順にソートした配列を取得する。
	 * 
	 * @return この単位から変換可能な全ての単位を含み、大きい単位から降順にソートした配列
	 */
	TimeUnit[] descendingUnits() {
		return isConvertibleToMilliseconds() ? DESCENDING_MS_BASED : DESCENDING_MONTH_BASED;
	}
	
	/**
	 * この単位から変換可能な単位のうち、しばしば表示に利用する単位を、大きい単位から降順にソートした配列を取得する。
	 * 
	 * @return この単位から変換可能な全ての単位のうち、しばしば表示に利用する単位を、大きい単位から降順にソートした配列
	 */
	TimeUnit[] descendingUnitsForDisplay() {
		return isConvertibleToMilliseconds() ? DESCENDING_MS_BASED_FOR_DISPLAY : DESCENDING_MONTH_BASED_FOR_DISPLAY;
	}
	
	int getFactor() {
		return factor.value;
	}
	
	int javaCalendarConstantForBaseType() {
		if (baseType.equals(Type.millisecond)) {
			return Calendar.MILLISECOND;
		}
		if (baseType.equals(Type.month)) {
			return Calendar.MONTH;
		}
		return 0;
	}
	
	/**
	 * この単位から変換可能な単位のうち、現在の単位より一つ小さい単位を取得する。
	 * 
	 * @return この単位から変換可能な単位のうち、現在の単位より一つ小さい単位
	 */
	TimeUnit nextFinerUnit() {
		TimeUnit[] descending = descendingUnits();
		int index = -1;
		for (int i = 0; i < descending.length; i++) {
			if (descending[i].equals(this)) {
				index = i;
			}
		}
		if (index == descending.length - 1) {
			return null;
		}
		return descending[index + 1];
	}
	
	String toString(long quantity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(quantity);
		buffer.append(" ");
		buffer.append(type.name());
		buffer.append(quantity == 1 ? "" : "s");
		return buffer.toString();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * 
	 * @return {@link #baseType}
	 */
	@SuppressWarnings("unused")
	private Type getForPersistentMapping_BaseType() { // CHECKSTYLE IGNORE THIS LINE
		return baseType;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * 
	 * @return {@link #factor}
	 */
	@SuppressWarnings("unused")
	private TimeUnitConversionFactor getForPersistentMapping_Factor() { // CHECKSTYLE IGNORE THIS LINE
		return factor;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * 
	 * @return {@link #type}
	 */
	@SuppressWarnings("unused")
	private Type getForPersistentMapping_Type() { // CHECKSTYLE IGNORE THIS LINE
		return type;
	}
	

	static enum Type {
		millisecond, second, minute, hour, day, week, month, quarter, year;
	}
}
