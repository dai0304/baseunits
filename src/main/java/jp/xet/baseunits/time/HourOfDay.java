/*
 * Copyright 2010-2018 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.time;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * 1日の中の特定の「時」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、暦日や分以下（時未満）の概念を持っていない。またタイムゾーンの概念もない。
 * また、このクラスは特定の瞬間をモデリングしたものではなく、その1時間全ての範囲を表すクラスである。</p>
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public class HourOfDay implements Comparable<HourOfDay>, Serializable {
	
	/**
	 * {@link HourOfDay}の値の最小値
	 * 
	 * @since 2.0
	 */
	public static final int MIN_VALUE = 0;
	
	/**
	 * {@link HourOfDay}の値の最大値
	 * 
	 * @since 2.0
	 */
	public static final int MAX_VALUE = 23;
	
	/**
	 * {@link HourOfDay}の最小値
	 * 
	 * @since 2.0
	 */
	public static final HourOfDay MIN = HourOfDay.valueOf(MIN_VALUE);
	
	/**
	 * {@link HourOfDay}の最大値
	 * 
	 * @since 2.0
	 */
	public static final HourOfDay MAX = HourOfDay.valueOf(MAX_VALUE);
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 時をあらわす正数
	 * @return 時（0〜23）
	 * @throws IllegalArgumentException 引数の値が0〜23の範囲ではない場合
	 * @since 1.0
	 */
	public static HourOfDay valueOf(int initial) {
		return new HourOfDay(initial);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 時をあらわす正数
	 * @param meridian 午前/午後
	 * @return 時（0〜11）
	 * @throws IllegalArgumentException 引数{@code initial}の値が0〜11の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code amPm}の値が {@code "AM"} または {@code "PM"} ではない場合
	 * @since 2.0
	 */
	public static HourOfDay valueOf(int initial, Meridian meridian) {
		return new HourOfDay(convertTo24hour(initial, meridian));
	}
	
	/**
	 * 午前午後記号付き12時間制の時を24時間制の値に変換する。
	 * 
	 * @param hour 時（0〜11）
	 * @param meridian 午前午後
	 * @return 24時間制における時
	 * @throws IllegalArgumentException 引数{@code initial}の値が0〜11の範囲ではない場合
	 * @throws NullPointerException 引数{@code meridian}に{@code null}を与えた場合
	 * @since 2.0
	 */
	static int convertTo24hour(int hour, Meridian meridian) {
		Preconditions.checkNotNull(meridian);
		if (hour < MIN_VALUE || hour > 12) {
			throw new IllegalArgumentException("Illegal value for 12 hour: " + hour
					+ ", please use a value between 0 and 11");
		}
		int translatedAmPm = meridian == Meridian.AM ? 0 : 12;
		translatedAmPm -= (hour == 12) ? 12 : 0;
		return hour + translatedAmPm;
	}
	
	
	/** 時をあらわす正数 */
	final int value;
	
	
	private HourOfDay(int initial) {
		if (initial < MIN_VALUE || initial > MAX_VALUE) {
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
	 * @since 1.0
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
	 * 同暦日において、この時が、{@code other}よりも未来かどうか調べる。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象時
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isAfter(HourOfDay other) {
		if (other == null) {
			return false;
		}
		return value > other.value;
	}
	
	/**
	 * 同暦日において、この時が、{@code other}よりも過去かどうか調べる。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isBefore(HourOfDay other) {
		if (other == null) {
			return false;
		}
		return value < other.value;
	}
	
	@Override
	public String toString() {
		return String.format("%02d", value);
	}
	
	
	/** AM/PM enumeration. */
	public enum Meridian {
		/** 午前 */
		AM,
		
		/** 午後 */
		PM
	}
}
