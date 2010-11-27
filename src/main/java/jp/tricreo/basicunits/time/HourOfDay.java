/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
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
 * ----
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.tricreo.basicunits.time;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

/**
 * 1日の中の特定の「時」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、日付や分以下（時未満）の概念を持っていない。またタイムゾーンの概念もない。</p>
 */
@SuppressWarnings("serial")
public class HourOfDay implements Comparable<HourOfDay>, Serializable {
	
	private static final int MIN = 0;
	
	private static final int MAX = 23;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 時をあらわす正数
	 * @return 時（0〜23）
	 * @throws IllegalArgumentException 引数の値が0〜23の範囲ではない場合
	 */
	public static HourOfDay valueOf(int initial) {
		return new HourOfDay(initial);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 時をあらわす正数
	 * @param amPm 午前午後を表す文字列
	 * @return 時（0〜11）
	 * @throws IllegalArgumentException 引数{@code initial}の値が0〜11の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code amPm}の値が {@code "AM"} または {@code "PM"} ではない場合
	 */
	public static HourOfDay valueOf(int initial, String amPm) {
		return new HourOfDay(convertTo24hour(initial, amPm));
	}
	
	/**
	 * 午前午後記号付き12時間制の時を24時間制の値に変換する。
	 * 
	 * @param hour 時（0〜11）
	 * @param amPm 午前午後を表す文字列
	 * @return 24時間制における時
	 * @throws IllegalArgumentException 引数{@code initial}の値が0〜11の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code amPm}の値が {@code "AM"} または {@code "PM"} ではない場合
	 */
	static int convertTo24hour(int hour, String amPm) {
		if (("AM".equalsIgnoreCase(amPm) || "PM".equalsIgnoreCase(amPm)) == false) {
			throw new IllegalArgumentException("AM PM indicator invalid: " + amPm + ", please use AM or PM");
		}
		if (hour < MIN || hour > 12) {
			throw new IllegalArgumentException("Illegal value for 12 hour: " + hour
					+ ", please use a value between 0 and 11");
		}
		int translatedAmPm = "AM".equalsIgnoreCase(amPm) ? 0 : 12;
		translatedAmPm -= (hour == 12) ? 12 : 0;
		return hour + translatedAmPm;
	}
	

	/** 時をあらわす正数 */
	final int value;
	

	private HourOfDay(int initial) {
		if (initial < MIN || initial > MAX) {
			throw new IllegalArgumentException("Illegal value for 24 hour: " + initial
					+ ", please use a value between 0 and 23");
		}
		value = initial;
	}
	
	/**
	 * このオブジェクトの{@link #value}フィールド（時をあらわす正数）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 時をあらわす正数（0〜23）
	 */
	public int breachEncapsulationOfValue() {
		return value;
	}
	
	@Override
	public int compareTo(HourOfDay other) {
		return value - other.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		HourOfDay other = (HourOfDay) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return value;
	}
	
	/**
	 * 同日において、このインスタンスが表す時が、引数{@code another}で表される時よりも未来かどうか調べる。
	 * 
	 * @param another 基準時
	 * @return 同日において、このインスタンスが表す時が、引数{@code another}で表される時よりも未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isAfter(HourOfDay another) {
		Validate.notNull(another);
		return value > another.value;
	}
	
	/**
	 * 同日において、このインスタンスが表す時が、引数{@code another}で表される時よりも過去かどうか調べる。
	 * 
	 * @param another 基準時
	 * @return 同日において、このインスタンスが表す時が、引数{@code another}で表される時よりも過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isBefore(HourOfDay another) {
		Validate.notNull(another);
		return value < another.value;
	}
	
	@Override
	public String toString() {
		if (value < 10) {
			return "0" + String.valueOf(value);
		}
		return String.valueOf(value);
	}
}
