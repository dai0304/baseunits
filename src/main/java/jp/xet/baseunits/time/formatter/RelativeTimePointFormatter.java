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
package jp.xet.baseunits.time.formatter;

import java.io.Serializable;
import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimePoint;

/**
 * 基準時刻からの相対時間を表示するフォーマッタ。
 * 
 * <p>「5分前」「3日後」など。</p>
 * 
 * @author daisuke
 * @since 2.0
 */
public interface RelativeTimePointFormatter {
	
	@SuppressWarnings("javadoc")
	String format(TimePoint target);
	
	@SuppressWarnings("javadoc")
	String format(TimePoint target, Locale locale);
	
	@SuppressWarnings("javadoc")
	String format(TimePoint target, TimePoint standard);
	
	/**
	 * 基準{@link TimePoint}からの相対時間を表示するフォーマッタ。
	 * 
	 * @param target 対象{@link TimePoint}
	 * @param standard 基準となる{@link TimePoint}
	 * @param locale ロケール
	 * @return 相対時間表現文字列
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	String format(TimePoint target, TimePoint standard, Locale locale);
	
	
	/**
	 * フォールバック設定クラス。
	 * 
	 * <p>{@link RelativeTimePointFormatter}は、主に「大まかな時刻」を表す役割があるが、
	 * 相対時間差があまりにも小さい、または大きい場合は正確な表記をすると、かえって違和感を感じる。
	 * 例えば「34ミリ秒前」や「105年4ヶ月22日9時間2分23秒後」など…。</p>
	 * 
	 * <p>このように時間差が一定より大きい、または小さい場合にフォーマッタの挙動を変える設定を、
	 * フォールバック設定と呼ぶ。</p>
	 * 
	 * @author daisuke
	 * @since 2.0
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
		 * @param lowerFallbackLimit フォールバック下限値
		 * @param lowerFallbackFormatter 下限を下回った場合のフォーマッタ
		 * @param upperFallbackLimit フォールバック上限値
		 * @param upperFallbackFormatter 上限を上回った場合のフォーマッタ
		 */
		public FallbackConfig(Duration lowerFallbackLimit, TimePointFormatter lowerFallbackFormatter,
				Duration upperFallbackLimit, TimePointFormatter upperFallbackFormatter) {
			this.lowerFallbackLimit = lowerFallbackLimit;
			this.lowerFallbackFormatter = lowerFallbackFormatter;
			this.upperFallbackLimit = upperFallbackLimit;
			this.upperFallbackFormatter = upperFallbackFormatter;
		}
		
		/**
		 * 下限を下回った場合のフォーマッタを取得する。
		 * 
		 * @return 下限を下回った場合のフォーマッタ
		 */
		public TimePointFormatter getLowerFallbackFormatter() {
			return lowerFallbackFormatter;
		}
		
		/**
		 * フォールバック下限値を返す。
		 * 
		 * @return フォールバック下限値
		 */
		public Duration getLowerFallbackLimit() {
			return lowerFallbackLimit;
		}
		
		/**
		 * 上限を上回った場合のフォーマッタを返す。
		 * 
		 * @return 上限を上回った場合のフォーマッタ
		 */
		public TimePointFormatter getUpperFallbackFormatter() {
			return upperFallbackFormatter;
		}
		
		/**
		 * フォールバック上限値を返す。
		 * 
		 * @return フォールバック上限値
		 */
		public Duration getUpperFallbackLimit() {
			return upperFallbackLimit;
		}
		
		/**
		 * フォールバック下限値を設定する。
		 * 
		 * @param lowerFallbackLimit フォールバック下限値
		 */
		public void setLowerFallbackLimit(Duration lowerFallbackLimit) {
			this.lowerFallbackLimit = lowerFallbackLimit;
		}
		
		/**
		 * 下限を下回った場合のフォーマッタを設定する。
		 * 
		 * @param lowerFallbackFormatter 下限を下回った場合のフォーマッタ
		 */
		public void setLowerFallbackString(TimePointFormatter lowerFallbackFormatter) {
			this.lowerFallbackFormatter = lowerFallbackFormatter;
		}
		
		/**
		 * フォールバック上限値を設定する。
		 * 
		 * @param upperFallbackLimit フォールバック上限値
		 */
		public void setUpperFallbackLimit(Duration upperFallbackLimit) {
			this.upperFallbackLimit = upperFallbackLimit;
		}
		
		/**
		 * 上限を上回った場合のフォーマッタを設定する。
		 * 
		 * @param upperFallbackFormatter 上限を上回った場合のフォーマッタ
		 */
		public void setUpperFallbackString(TimePointFormatter upperFallbackFormatter) {
			this.upperFallbackFormatter = upperFallbackFormatter;
		}
	}
}
