/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/25
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
 */
package jp.xet.baseunits.time.spec;

import java.util.Iterator;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.util.spec.Specification;

/**
 * 日付仕様を表すインターフェイス。
 * 
 * @author daisuke
 * @since 2.0
 */
public interface DateSpecification extends Specification<CalendarDate> {
	
	/**
	 * Create a new specification that is the AND operation of {@code this} specification and another specification.
	 * 
	 * @param specification Specification to AND.
	 * @return A new specification.
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	DateSpecification and(DateSpecification specification);
	
	/**
	 * 指定した期間の中で、この日付仕様を満たす最初の日付を返す。
	 * 
	 * @param interval 期間
	 * @return 年月日。但し、仕様を満たす日がなかった場合は{@code null}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数{@code interval}に下側限界（開始日）が必要なロジックで、かつ下側限界を持たない場合
	 * @since 1.0
	 */
	CalendarDate firstOccurrenceIn(CalendarInterval interval);
	
	/**
	 * 与えた日付が、この日付仕様を満たすかどうか検証する。
	 * 
	 * @param date 検証対象の日付
	 * @return 仕様を満たす場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	@Override
	boolean isSatisfiedBy(CalendarDate date);
	
	/**
	 * 指定した期間の中で、この日付仕様を満たす年月日を順次取得する反復子を返す。
	 * 
	 * @param interval 期間
	 * @return 反復子
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	Iterator<CalendarDate> iterateOver(CalendarInterval interval);
	
	/**
	 * 指定した期間の中で、この日付仕様を満たす最後の日付を返す。
	 * 
	 * @param interval 期間
	 * @return 最後。但し、仕様を満たす日がなかった場合は{@code null}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数{@code interval}に上側限界（終了日）が必要なロジックで、かつ上側限界を持たない場合
	 * @since 2.0
	 */
	CalendarDate lastOccurrenceIn(CalendarInterval interval);
	
	@Override
	DateSpecification not();
	
	/**
	 * Create a new specification that is the OR operation of {@code this} specification and another specification.
	 * 
	 * @param specification Specification to OR.
	 * @return A new specification.
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	DateSpecification or(DateSpecification specification);
}
