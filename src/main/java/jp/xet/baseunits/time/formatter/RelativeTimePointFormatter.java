/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/19
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
package jp.xet.baseunits.time.formatter;

import java.io.Serializable;
import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimePoint;

/**
 * 指定した時刻からの相対時間を表示するフォーマッタ。
 * 
 * <p>「5分前」「3日後」など。</p>
 * 
 * @since 2.0
 * @version $Id$
 * @author daisuke
 */
public interface RelativeTimePointFormatter {
	
	@SuppressWarnings("javadoc")
	String format(TimePoint target);
	
	@SuppressWarnings("javadoc")
	String format(TimePoint target, Locale locale);
	
	@SuppressWarnings("javadoc")
	String format(TimePoint target, TimePoint standard);
	
	/**
	 * 基準時刻からの相対時間を表示するフォーマッタ。
	 * 
	 * @param target 対象時刻
	 * @param standard 基準となる時刻
	 * @param locale ロケール
	 * @return 相対時間表現文字列
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	String format(TimePoint target, TimePoint standard, Locale locale);
	
	
	/**
	 * TODO for daisuke
	 * 
	 * @since 2.0
	 * @version $Id$
	 * @author daisuke
	 */
	@SuppressWarnings("serial")
	public class FallbackConfig implements Serializable {
		
		private Duration lowerFallbackLimit;
		
		private TimePointFormatter lowerFallbackFormatter;
		
		private Duration upperFallbackLimit;
		
		private TimePointFormatter upperFallbackFormatter;
		
		
		/**
		 * インスタンスを生成する。
		 * 
		 * @param lowerFallbackLimit
		 * @param lowerFallbackFormatter
		 * @param upperFallbackLimit
		 * @param upperFallbackFormatter
		 */
		public FallbackConfig(Duration lowerFallbackLimit, TimePointFormatter lowerFallbackFormatter,
				Duration upperFallbackLimit, TimePointFormatter upperFallbackFormatter) {
			this.lowerFallbackLimit = lowerFallbackLimit;
			this.lowerFallbackFormatter = lowerFallbackFormatter;
			this.upperFallbackLimit = upperFallbackLimit;
			this.upperFallbackFormatter = upperFallbackFormatter;
		}
		
		public TimePointFormatter getLowerFallbackFormatter() {
			return lowerFallbackFormatter;
		}
		
		public Duration getLowerFallbackLimit() {
			return lowerFallbackLimit;
		}
		
		public TimePointFormatter getUpperFallbackFormatter() {
			return upperFallbackFormatter;
		}
		
		public Duration getUpperFallbackLimit() {
			return upperFallbackLimit;
		}
		
		public void setLowerFallbackLimit(Duration lowerFallbackLimit) {
			this.lowerFallbackLimit = lowerFallbackLimit;
		}
		
		public void setLowerFallbackString(TimePointFormatter lowerFallbackFormatter) {
			this.lowerFallbackFormatter = lowerFallbackFormatter;
		}
		
		public void setUpperFallbackLimit(Duration upperFallbackLimit) {
			this.upperFallbackLimit = upperFallbackLimit;
		}
		
		public void setUpperFallbackString(TimePointFormatter upperFallbackFormatter) {
			this.upperFallbackFormatter = upperFallbackFormatter;
		}
	}
}
