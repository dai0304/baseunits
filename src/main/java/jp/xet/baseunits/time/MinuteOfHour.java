/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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

/**
 * 1時間の中の特定の「分」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、暦日や時、秒以下（分未満）の概念を持っていない。またタイムゾーンの概念もない。
 * また、このクラスは特定の瞬間をモデリングしたものではなく、その1分間全ての範囲を表すクラスである。</p>
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public class MinuteOfHour implements Comparable<MinuteOfHour>, Serializable {
	
	/**
	 * {@link MinuteOfHour}の値の最小値
	 * 
	 * @since 2.0
	 */
	public static final int MIN_VALUE = 0;
	
	/**
	 * {@link MinuteOfHour}の値の最大値
	 * 
	 * @since 2.0
	 */
	public static final int MAX_VALUE = 59;
	
	/**
	 * {@link MinuteOfHour}の最小値
	 * 
	 * @since 2.0
	 */
	public static final MinuteOfHour MIN = MinuteOfHour.valueOf(MIN_VALUE);
	
	/**
	 * {@link MinuteOfHour}の最大値
	 * 
	 * @since 2.0
	 */
	public static final MinuteOfHour MAX = MinuteOfHour.valueOf(MAX_VALUE);
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 分をあらわす正数
	 * @return 分（0〜59）
	 * @throws IllegalArgumentException 引数の値が0〜59の範囲ではない場合
	 * @since 1.0
	 */
	public static MinuteOfHour valueOf(int initial) {
		return new MinuteOfHour(initial);
	}
	
	
	/** 分をあらわす正数 */
	final int value;
	
	
	MinuteOfHour(int initial) {
		if (initial < MIN_VALUE || initial > MAX_VALUE) {
			throw new IllegalArgumentException("Illegal value for minute: " + initial
					+ ", please use a value between 0 and 59");
		}
		value = initial;
	}
	
	/**
	 * このオブジェクトの{@link #value}フィールド（分をあらわす正数）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 分をあらわす正数（0〜59）
	 * @since 1.0
	 */
	public int breachEncapsulationOfValue() {
		return value;
	}
	
	@Override
	public int compareTo(MinuteOfHour other) {
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
		MinuteOfHour other = (MinuteOfHour) obj;
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
	 * 同日同時において、この分が、引数{@code other}よりも未来かどうか調べる。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象分
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isAfter(MinuteOfHour other) {
		if (other == null) {
			return false;
		}
		return value > other.value;
	}
	
	/**
	 * 同日同時において、この分が、引数{@code another}よりも過去かどうか調べる。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象分
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isBefore(MinuteOfHour other) {
		if (other == null) {
			return false;
		}
		return value < other.value;
	}
	
	@Override
	public String toString() {
		return String.format("%02d", value);
	}
}
