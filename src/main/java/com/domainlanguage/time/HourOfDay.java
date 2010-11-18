/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

/**
 * 特定の「時」を表すクラス。
 * 
 * @author daisuke
 */
public class HourOfDay {
	
	private static final int MIN = 0;
	
	private static final int MAX = 23;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 時をあらわす正数
	 * @return 時
	 * @throws IllegalArgumentException 引数の値が0〜23の範囲ではない場合
	 */
	public static HourOfDay of(int initial) {
		return new HourOfDay(initial);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 時をあらわす正数
	 * @param am_pm 午前午後を表す文字列
	 * @return 時
	 * @throws IllegalArgumentException 引数initialの値が0〜11の範囲ではない場合
	 * @throws IllegalArgumentException 引数am_pmの値が AM, PM ではない場合
	 */
	public static HourOfDay value(int initial, String am_pm) {
		return HourOfDay.of(convertTo24hour(initial, am_pm));
	}
	
	private static int convertTo24hour(int hour, String am_pm) {
		if (!("AM".equalsIgnoreCase(am_pm) || "PM".equalsIgnoreCase(am_pm))) {
			throw new IllegalArgumentException("AM PM indicator invalid: " + am_pm + ", please use AM or PM");
		}
		if (hour < MIN | hour > 12) {
			throw new IllegalArgumentException("Illegal value for 12 hour: " + hour
					+ ", please use a value between 0 and 11");
		}
		int translatedAmPm = "AM".equalsIgnoreCase(am_pm) ? 0 : 12;
		translatedAmPm -= (hour == 12) ? 12 : 0;
		return hour + translatedAmPm;
	}
	
	@SuppressWarnings("unused")
	private static Class<?> getPrimitivePersistenceMappingType() {
		return Integer.TYPE;
	}
	

	final int value;
	

	private HourOfDay(int initial) {
		if (initial < MIN || initial > MAX) {
			throw new IllegalArgumentException("Illegal value for 24 hour: " + initial
					+ ", please use a value between 0 and 23");
		}
		value = initial;
	}
	
	public boolean equals(HourOfDay another) {
		return value == another.value;
	}
	
	@Override
	public boolean equals(Object another) {
		if (another instanceof HourOfDay == false) {
			return false;
		}
		return equals((HourOfDay) another);
	}
	
	@Override
	public int hashCode() {
		return value;
	}
	
	public boolean isAfter(HourOfDay another) {
		return value > another.value;
	}
	
	public boolean isBefore(HourOfDay another) {
		return value < another.value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	public int value() {
		return value;
	}
}
