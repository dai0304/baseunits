/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

import org.apache.commons.lang.Validate;

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
	
	/**
	 * 同時(hour)において、このインスタンスが表す分が、引数{@code another}で表される時よりも未来かどうか調べる。
	 * 
	 * @param another 基準分
	 * @return 同日において、このインスタンスが表す分が、引数{@code another}で表される時よりも未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isAfter(MinuteOfHour another) {
		Validate.notNull(another);
		return value > another.value;
	}
	
	/**
	 * 同時(hour)において、このインスタンスが表す分が、引数{@code another}で表される時よりも過去かどうか調べる。
	 * 
	 * @param another 基準分
	 * @return 同日において、このインスタンスが表す分が、引数{@code another}で表される時よりも過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isBefore(MinuteOfHour another) {
		Validate.notNull(another);
		return value < another.value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	/**
	 * 分をあらわす正数を取得する。
	 * 
	 * @return 分をあらわす正数
	 */
	public int value() {
		return value;
	}
}
