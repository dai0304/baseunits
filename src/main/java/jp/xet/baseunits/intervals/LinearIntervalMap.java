/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.intervals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;

/**
 * 線形{@link IntervalMap}実装クラス。
 * 
 * @param <K> キーとなる区間が表現する型
 * @param <V> 値の型
 * @since 1.0
 */
@SuppressWarnings("serial")
public class LinearIntervalMap<K extends Comparable<K> & Serializable, V> implements IntervalMap<K, V>, Serializable {
	
	final Map<Interval<K>, V> keyValues;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.0
	 */
	public LinearIntervalMap() {
		keyValues = new HashMap<Interval<K>, V>();
	}
	
	@Override
	public boolean containsIntersectingKey(Interval<K> otherInterval) {
		Validate.notNull(otherInterval);
		return intersectingKeys(otherInterval).isEmpty() == false;
	}
	
	@Override
	public boolean containsKey(K key) {
		return findKeyIntervalContaining(key) != null;
	}
	
	@Override
	public V get(K key) {
		Interval<K> keyInterval = findKeyIntervalContaining(key);
		//		if (keyInterval == null) return null;
		return keyValues.get(keyInterval);
	}
	
	@Override
	public void put(Interval<K> keyInterval, V value) {
		Validate.notNull(keyInterval);
		remove(keyInterval);
		keyValues.put(keyInterval, value);
	}
	
	@Override
	public void remove(Interval<K> keyInterval) {
		Validate.notNull(keyInterval);
		List<Interval<K>> intervalSequence = intersectingKeys(keyInterval);
		for (Interval<K> oldInterval : intervalSequence) {
			V oldValue = keyValues.get(oldInterval);
			keyValues.remove(oldInterval);
			List<Interval<K>> complementIntervalSequence = keyInterval.complementRelativeTo(oldInterval);
			directPut(complementIntervalSequence, oldValue);
		}
	}
	
	@Override
	public String toString() {
		return keyValues.toString();
	}
	
	private void directPut(List<Interval<K>> intervalSequence, V value) {
		Validate.noNullElements(intervalSequence);
		for (Interval<K> interval : intervalSequence) {
			keyValues.put(interval, value);
		}
	}
	
	private Interval<K> findKeyIntervalContaining(K key) {
		if (key == null) {
			return null;
		}
		Set<Interval<K>> keySet = keyValues.keySet();
		for (Interval<K> interval : keySet) {
			if (interval.includes(key)) {
				return interval;
			}
		}
		return null;
	}
	
	/**
	 * この写像が保持するキーとしての区間のうち、指定した区間 {@code otherInterval}と共通部分を持つ
	 * 区間の列を取得する。
	 * 
	 * <p>戻り値の列は、区間の自然順にソートされている。</p>
	 * 
	 * @param otherInterval 対象区間
	 * @return 指定した区間と共通部分を持つ区間の列
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	private List<Interval<K>> intersectingKeys(Interval<K> otherInterval) {
		Validate.notNull(otherInterval);
		List<Interval<K>> intervalSequence = new ArrayList<Interval<K>>();
		for (Interval<K> keyInterval : keyValues.keySet()) {
			if (keyInterval.intersects(otherInterval)) {
				intervalSequence.add(keyInterval);
			}
		}
		return intervalSequence;
	}
}
