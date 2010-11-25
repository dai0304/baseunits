/*
 * Copyright 2010 Daisuke Miyamoto.
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
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.time;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

/**
 * 1時間の中の特定の「分」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、日付や時、秒以下（分未満）の概念を持っていない。またタイムゾーンの概念もない。</p>
 */
@SuppressWarnings("serial")
public class MinuteOfHour implements Comparable<MinuteOfHour>, Serializable {
	
	private static final int MIN = 0;
	
	private static final int MAX = 59;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 分をあらわす正数
	 * @return 分（0〜59）
	 * @throws IllegalArgumentException 引数の値が0〜59の範囲ではない場合
	 */
	public static MinuteOfHour of(int initial) {
		return new MinuteOfHour(initial);
	}
	

	final int value;
	

	private MinuteOfHour(int initial) {
		if (initial < MIN || initial > MAX) {
			throw new IllegalArgumentException("Illegal value for minute: " + initial
					+ ", please use a value between 0 and 59");
		}
		value = initial;
	}
	
	@Override
	public int compareTo(MinuteOfHour o) {
		return Integer.valueOf(value).compareTo(o.value);
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
	 * 同時(hour)において、このインスタンスが表す分が、引数{@code another}で表される時よりも未来かどうか調べる。
	 * 
	 * <p>等価である場合は{@code false}を返す。</p>
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
	 * <p>等価である場合は{@code false}を返す。</p>
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
