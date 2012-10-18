/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
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
package jp.xet.baseunits.time;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * 1分間の中の特定の「秒」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、日付や時、分、ミリ秒以下（秒未満）の概念を持っていない。またタイムゾーンの概念もない。
 * また、このクラスは特定の瞬間をモデリングしたものではなく、その1秒間全ての範囲を表すクラスである。</p>
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class SecondOfMinute implements Comparable<SecondOfMinute>, Serializable {
	
	/**
	 * {@link SecondOfMinute}の値の最小値
	 * 
	 * @since 2.0
	 */
	public static final int MIN_VALUE = 0;
	
	/**
	 * {@link SecondOfMinute}の値の最大値
	 * 
	 * @since 2.0
	 */
	public static final int MAX_VALUE = 59;
	
	/**
	 * {@link SecondOfMinute}の最小値
	 * 
	 * @since 2.0
	 */
	public static final SecondOfMinute MIN = SecondOfMinute.valueOf(MIN_VALUE);
	
	/**
	 * {@link SecondOfMinute}の最大値
	 * 
	 * @since 2.0
	 */
	public static final SecondOfMinute MAX = SecondOfMinute.valueOf(MAX_VALUE);
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 秒をあらわす正数
	 * @return 秒（0〜59）
	 * @throws IllegalArgumentException 引数の値が0〜59の範囲ではない場合
	 * @since 2.0
	 */
	public static SecondOfMinute valueOf(int initial) {
		return new SecondOfMinute(initial);
	}
	
	
	/** 秒をあらわす正数 */
	final int value;
	
	
	SecondOfMinute(int initial) {
		if (initial < MIN_VALUE || initial > MAX_VALUE) {
			throw new IllegalArgumentException("Illegal value for second: " + initial
					+ ", please use a value between 0 and 59");
		}
		value = initial;
	}
	
	/**
	 * このオブジェクトの{@link #value}フィールド（秒をあらわす正数）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 秒をあらわす正数（0〜59）
	 * @since 2.0
	 */
	public int breachEncapsulationOfValue() {
		return value;
	}
	
	@Override
	public int compareTo(SecondOfMinute other) {
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
		SecondOfMinute other = (SecondOfMinute) obj;
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
	 * 同日同時同分において、このインスタンスが表す秒が、引数{@code another}で表される秒よりも未来かどうか調べる。
	 * 
	 * <p>等価である場合は{@code false}を返す。</p>
	 * 
	 * @param another 基準分
	 * @return 同日同時同分において、このインスタンスが表す秒が、引数{@code another}で表される秒よりも未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isAfter(SecondOfMinute another) {
		Preconditions.checkNotNull(another);
		return value > another.value;
	}
	
	/**
	 * 同日同時同分において、このインスタンスが表す秒が、引数{@code another}で表される秒よりも過去かどうか調べる。
	 * 
	 * <p>等価である場合は{@code false}を返す。</p>
	 * 
	 * @param another 基準分
	 * @return 同日同時同分において、このインスタンスが表す分が、引数{@code another}で表される時よりも過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public boolean isBefore(SecondOfMinute another) {
		Preconditions.checkNotNull(another);
		return value < another.value;
	}
	
	@Override
	public String toString() {
		return String.format("%02d", value);
	}
}
