/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 時間単位を表す値クラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class TimeUnit implements Comparable<TimeUnit>, Serializable {
	
	private static final long serialVersionUID = -6356796143887842261L;
	
	/** ミリ秒 */
	public static final TimeUnit MILLISECOND = new TimeUnit(Type.millisecond, Type.millisecond,
			TimeUnitConversionFactor.one);
	
	/** 秒 */
	public static final TimeUnit SECOND = new TimeUnit(Type.second, Type.millisecond,
			TimeUnitConversionFactor.millisecondsPerSecond);
	
	/** 分 */
	public static final TimeUnit MINUTE = new TimeUnit(Type.minute, Type.millisecond,
			TimeUnitConversionFactor.millisecondsPerMinute);
	
	/** 時 */
	public static final TimeUnit HOUR = new TimeUnit(Type.hour, Type.millisecond,
			TimeUnitConversionFactor.millisecondsPerHour);
	
	/** 日 */
	public static final TimeUnit DAY = new TimeUnit(Type.day, Type.millisecond,
			TimeUnitConversionFactor.millisecondsPerDay);
	
	/** 週 */
	public static final TimeUnit WEEK = new TimeUnit(Type.week, Type.millisecond,
			TimeUnitConversionFactor.millisecondsPerWeek);
	
	public static final TimeUnit[] descendingMillisecondBased = {
		WEEK,
		DAY,
		HOUR,
		MINUTE,
		SECOND,
		MILLISECOND
	};
	
	public static final TimeUnit[] descendingMillisecondBasedForDisplay = {
		DAY,
		HOUR,
		MINUTE,
		SECOND,
		MILLISECOND
	};
	
	/** 月 */
	public static final TimeUnit MONTH = new TimeUnit(Type.month, Type.month, TimeUnitConversionFactor.one);
	
	/** 四半期 */
	public static final TimeUnit QUARTER = new TimeUnit(Type.quarter, Type.month,
			TimeUnitConversionFactor.monthsPerQuarter);
	
	/** 年 */
	public static final TimeUnit YEAR = new TimeUnit(Type.year, Type.month, TimeUnitConversionFactor.monthsPerYear);
	
	public static final TimeUnit[] descendingMonthBased = {
		YEAR,
		QUARTER,
		MONTH
	};
	
	public static final TimeUnit[] descendingMonthBasedForDisplay = {
		YEAR,
		MONTH
	};
	
	private Type type;
	
	private Type baseType;
	
	private int factor;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	TimeUnit() {
	}
	
	private TimeUnit(Type type, Type baseType, TimeUnitConversionFactor factor) {
		this.type = type;
		this.baseType = baseType;
		this.factor = factor.value;
	}
	
	@Override
	public int compareTo(TimeUnit other) {
		if (other.baseType.equals(baseType)) {
			return factor - other.factor;
		}
		if (baseType.equals(Type.month)) {
			return 1;
		}
		return -1;
	}
	
	@Override
	public boolean equals(Object object) {
		//revisit: maybe use: Reflection.equalsOverClassAndNull(this, other)
		if (object == null || !(object instanceof TimeUnit)) {
			return false;
		}
		TimeUnit other = (TimeUnit) object;
		return baseType.equals(other.baseType) && factor == other.factor && type.equals(other.type);
	}
	
	@Override
	public int hashCode() {
		return factor + baseType.hashCode() + type.hashCode();
	}
	
	/**
	 * この時間単位は、与えた時間単位{@code other}に換算可能かどうか調べる。
	 * 
	 * <p>例えば、秒と分は相互変換可能であるが、日と月は変換不可能である。
	 * なぜなら、1ヶ月は28〜31日の可能性があるから。</p>
	 * 
	 * @param other 変換対象時間単位
	 * @return 換算可能な場合は{@code true}、そうでない場合は{@code false}
	 */
	public boolean isConvertibleTo(TimeUnit other) {
		return baseType.equals(other.baseType);
	}
	
	/**
	 * この時間単位は、ミリ秒単位に換算可能かどうか調べる。
	 * 
	 * @return 換算可能な場合は{@code true}、そうでない場合は{@code false}
	 * @see #isConvertibleTo(TimeUnit)
	 */
	public boolean isConvertibleToMilliseconds() {
		return isConvertibleTo(MILLISECOND);
	}
	
	@Override
	public String toString() {
		return type.name;
	}
	
	TimeUnit baseUnit() {
		return baseType.equals(Type.millisecond) ? MILLISECOND : MONTH;
	}
	
	TimeUnit[] descendingUnits() {
		return isConvertibleToMilliseconds() ? descendingMillisecondBased : descendingMonthBased;
	}
	
	TimeUnit[] descendingUnitsForDisplay() {
		return isConvertibleToMilliseconds() ? descendingMillisecondBasedForDisplay : descendingMonthBasedForDisplay;
	}
	
	int getFactor() {
		return factor;
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
		buffer.append(type.name);
		buffer.append(quantity == 1 ? "" : "s");
		return buffer.toString();
	}
	

	enum Type {
		
		millisecond("millisecond"),

		second("second"),

		minute("minute"),

		hour("hour"),

		day("day"),

		week("week"),

		month("month"),

		quarter("quarter"),

		year("year");
		
		private final String name;
		

		Type(String name) {
			this.name = name;
		}
	}
}
