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
 * Copyright (c) 200X Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import java.util.Calendar;

/**
 * 1週間の中の特定の「曜日」を表す列挙型。
 * 
 * <p>タイムゾーンの概念はない。また、このクラスは特定の瞬間をモデリングしたものではなく、その日1日間全ての範囲を表すクラスである。</p>
 * 
 * @author daisuke
 * @since 1.0
 */
public enum DayOfWeek {
	
	/** 月曜日 */
	MONDAY(Calendar.MONDAY),
	
	/** 火曜日 */
	TUESDAY(Calendar.TUESDAY),
	
	/** 水曜日 */
	WEDNESDAY(Calendar.WEDNESDAY),
	
	/** 木曜日 */
	THURSDAY(Calendar.THURSDAY),
	
	/** 金曜日 */
	FRIDAY(Calendar.FRIDAY),
	
	/** 土曜日 */
	SATURDAY(Calendar.SATURDAY),
	
	/** 日曜日 */
	SUNDAY(Calendar.SUNDAY);
	
	/**
	 * {@link Calendar}に定義されている定数値から、{@link DayOfWeek}を探して返す。
	 * 
	 * @param value {@link Calendar}に定義されている定数値
	 * @return {@link DayOfWeek}. 見つからなかった場合は {@code null}
	 * @since 1.0
	 */
	public static DayOfWeek valueOf(int value) {
		for (DayOfWeek dayOfWeek : values()) {
			if (dayOfWeek.value == value) {
				return dayOfWeek;
			}
		}
		return null;
	}
	
	
	/** {@link Calendar}に定義する曜日をあらわす定数値 */
	final int value;
	
	/**
	 * 曜日の数
	 * 
	 * @since 2.0
	 */
	public static final int SIZE = values().length;
	
	
	DayOfWeek(int value) {
		this.value = value;
	}
	
	/**
	 * このオブジェクトの{@link #value}フィールド（{@link Calendar}に定義する曜日をあらわす定数値）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return {@link Calendar}に定義する曜日をあらわす定数値（{@link Calendar#SUNDAY}〜{@link Calendar#SATURDAY}）
	 * @since 1.0
	 */
	public int breachEncapsulationOfValue() {
		return value;
	}
	
	/**
	 * この曜日の翌日に当たる曜日を返す。
	 * 
	 * @return 翌日に当たる曜日
	 * @since 2.0
	 */
	public DayOfWeek nextDay() {
		return plusDays(1);
	}
	
	/**
	 * この曜日の指定日後に当たる曜日を返す。
	 * 
	 * @param increment 日数（負数も可）
	 * @return 曜日
	 * @since 2.0
	 */
	public DayOfWeek plusDays(int increment) {
		int o = ordinal() + increment;
		while (o < 0) {
			o += values().length;
		}
		return values()[o % values().length];
	}
	
	/**
	 * この曜日の前日に当たる曜日を返す。
	 * 
	 * @return 前日に当たる曜日
	 * @since 2.0
	 */
	public DayOfWeek prevDay() {
		return plusDays(-1);
	}
	
	/**
	 * 曜日を表す3文字の文字列を返す。
	 * 
	 * @return 3文字の文字列
	 * @since 2.0
	 */
	public String shortName() {
		return name().substring(0, 3);
	}
}
