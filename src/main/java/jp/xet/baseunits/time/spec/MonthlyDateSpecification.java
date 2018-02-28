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
package jp.xet.baseunits.time.spec;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarMonth;

/**
 * 1ヶ月に一度だけマッチする暦日仕様インターフェイス。
 * 
 * @author daisuke
 * @since 2.0
 */
public interface MonthlyDateSpecification extends DateSpecification {
	
	/**
	 * 指定した暦月においてこの暦日仕様を満たす暦日を返す。
	 * 
	 * @param month 暦月
	 * @return {@link CalendarDate}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	CalendarDate ofYearMonth(CalendarMonth month);
	
}
