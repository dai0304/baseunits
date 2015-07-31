/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * 1年の中の特定の週を表すクラス。
 * 
 * <p>{@link CalendarWeek} が「2012年の第11週」等を表現するのに対して、
 * この型は年の概念を持たず、単に「第11週」等を表現する。</p>
 * 
 * <p>{@link java.util.Date}と異なり、暦日の概念を持っていない。またタイムゾーンの概念もない。</p>
 * 
 * @author daisuke
 * @since 2.0
 * @see CalendarWeek
 */
@SuppressWarnings("serial")
public final class WeekOfYear implements Comparable<WeekOfYear>, Serializable {
	
	/**
	 * {@link WeekOfYear}の値の最小値
	 * 
	 * @since 2.0
	 */
	public static final int MIN_VALUE = 1;
	
	/**
	 * {@link WeekOfYear}の値の最大値
	 * 
	 * @since 2.0
	 */
	public static final int MAX_VALUE = 53;
	
	/** 
	 * {@link WeekOfYear}の最小値
	 * 
	 * @since 2.0
	 */
	public static final WeekOfYear MIN = valueOf(MIN_VALUE);
	
	/**
	 * {@link WeekOfYear}の最大値
	 * 
	 * @since 2.0
	 */
	public static final WeekOfYear MAX = valueOf(MAX_VALUE);
	
	
	/**
	 * 指定した瞬間を表す、{@link WeekOfYear}を返す。
	 * 
	 * @param value 1年間の第1週から数えた週数
	 * @return {@link WeekOfYear}
	 * @throws IllegalArgumentException 引数{@code value}が1〜53の範囲ではない場合
	 * @since 2.0
	 */
	public static WeekOfYear valueOf(int value) {
		return new WeekOfYear(value);
	}
	
	
	final int value;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param value 週数
	 */
	WeekOfYear(int value) {
		Preconditions.checkArgument(value >= MIN_VALUE);
		Preconditions.checkArgument(value <= MAX_VALUE);
		this.value = value;
	}
	
	/**
	 * このオブジェクトの{@link #value}フィールドを返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 1年間の第1週から数えた週数
	 * @since 2.0
	 */
	public int breachEncapsulationOfNth() {
		return value;
	}
	
	@Override
	public int compareTo(WeekOfYear other) {
		return value < other.value ? -1 : (value == other.value ? 0 : 1);
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
		WeekOfYear other = (WeekOfYear) obj;
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
	 * この週が、{@code other}よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は{@code false}を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象週
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.0
	 */
	public boolean isAfter(WeekOfYear other) {
		if (other == null) {
			return false;
		}
		return value > other.value;
	}
	
	/**
	 * この週が、{@code other}よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は{@code false}を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象週
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.0
	 */
	public boolean isBefore(WeekOfYear other) {
		if (other == null) {
			return false;
		}
		return value < other.value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
