/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

/**
 * 特定の「分」を表すクラス。
 * 
 * @author daisuke
 */
public class MinuteOfHour {
	
	private static final int MIN = 0;
	
	private static final int MAX = 59;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 分をあらわす正数
	 * @return 分
	 * @throws IllegalArgumentException 引数の値が0〜59の範囲ではない場合
	 */
	public static MinuteOfHour of(int initial) {
		return new MinuteOfHour(initial);
	}
	
	@SuppressWarnings("unused")
	private static Class<?> getPrimitivePersistenceMappingType() {
		return Integer.TYPE;
	}
	

	final int value;
	

	private MinuteOfHour(int initial) {
		if (initial < MIN || initial > MAX) {
			throw new IllegalArgumentException("Illegal value for minute: " + initial
					+ ", please use a value between 0 and 59");
		}
		value = initial;
	}
	
	public boolean equals(MinuteOfHour another) {
		return value == another.value;
	}
	
	@Override
	public boolean equals(Object another) {
		if (!(another instanceof MinuteOfHour)) {
			return false;
		}
		return equals((MinuteOfHour) another);
	}
	
	@Override
	public int hashCode() {
		return value;
	}
	
	public boolean isAfter(MinuteOfHour another) {
		return value > another.value;
	}
	
	public boolean isBefore(MinuteOfHour another) {
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
