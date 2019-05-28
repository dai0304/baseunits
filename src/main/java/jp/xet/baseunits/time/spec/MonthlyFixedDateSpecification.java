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
package jp.xet.baseunits.time.spec;

import java.io.Serializable;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarMonth;
import jp.xet.baseunits.time.DayOfMonth;

import com.google.common.base.Preconditions;

/**
 * 毎月Y日にマッチする暦日仕様実装クラス。
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public final class MonthlyFixedDateSpecification extends AbstractMonthlyDateSpecification implements Serializable {
	
	final DayOfMonth day;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param day 日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	MonthlyFixedDateSpecification(DayOfMonth day) {
		Preconditions.checkNotNull(day);
		this.day = day;
	}
	
	/**
	 * この仕様を満たす条件としての日を返す。
	 * 
	 * @return この仕様を満たす条件としての日
	 */
	public DayOfMonth getDay() {
		return day;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Preconditions.checkNotNull(date);
		return day.equals(date.getDayOfMonth());
	}
	
	@Override
	public CalendarDate ofYearMonth(CalendarMonth month) {
		Preconditions.checkNotNull(month);
		return CalendarDate.from(month.getYear(), month.getMonthOfYear(), day);
	}
	
}
